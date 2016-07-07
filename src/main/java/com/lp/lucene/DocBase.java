package com.lp.lucene;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public abstract class DocBase implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(DocBase.class);
	
	protected static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	protected static final Analyzer analyzer = new IKAnalyzer();//分词器
	
	private static Directory diskDir;
	private static IndexWriter diskIndexWriter;
	
	private static Directory ramDir;
	private static IndexWriter ramIndexWriter;
	
	private static IndexReader reader;
	protected static IndexSearcher searcher;
	
	/**
	 * 获取索引路径
	 * @return
	 */
	protected abstract String getIndexPath();
	
	/**
	 * 获取索引目录：磁盘
	 * @return
	 */
	protected Directory getDiskDir() {
		if (null == diskDir) {
			try {
				diskDir = FSDirectory.open(new File(getIndexPath()));
			} catch (IOException e) {
				LOG.error("getDiskDir error,", e);
				throw new RuntimeException("打开索引目录失败！");
			}
		}
		return diskDir;
	};
	
	/**
	 * 获取索引读写对象，默认为Append模式
	 * @return
	 */
	protected IndexWriter getDiskIndexWriter() {
		return getDiskIndexWriter(null);
	}
	
	/**
	 * 获取索引读写对象
	 * @param openMode	索引打开模式：Create / Append
	 * @return
	 */
	protected IndexWriter getDiskIndexWriter(String openMode) {
		getDiskDir();
		if (null == diskIndexWriter) {
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);// 索引分词配置
			if (openMode == null || openMode.equalsIgnoreCase("APPEND")) {
				indexWriterConfig.setOpenMode(OpenMode.APPEND);
			} else {
				indexWriterConfig.setOpenMode(OpenMode.CREATE);
			}
			try {
				diskIndexWriter = new IndexWriter(diskDir, indexWriterConfig);
			} catch (IOException e) {
				LOG.error("getDiskIndexWriter error,", e);
				throw new RuntimeException("获取索引写对象失败！");
			}
		}
		return diskIndexWriter;
	};

	/**
	 * 获取索引目录：内存
	 * @return
	 */
	protected Directory getRamDir() {
		getDiskDir();
		if (null == ramDir){
			try {
				ramDir = new RAMDirectory(diskDir, new IOContext());
			} catch (IOException e) {
				LOG.error("getRamDir error,", e);
				throw new RuntimeException("获取内存索引目录失败！");
			}
		}
		return ramDir;
	};

	/**
	 * 获取索引读写对象：内存
	 * @return
	 */
	protected IndexWriter getRamIndexWriter() {
		getRamDir();
		if(null == ramIndexWriter){
			IndexWriterConfig ramConfig = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
			ramConfig.setOpenMode(OpenMode.CREATE);
			try {
				ramIndexWriter = new IndexWriter(ramDir, ramConfig);
			} catch (IOException e) {
				LOG.error("getRamIndexWriter error,", e);
				throw new RuntimeException("获取内存索引写对象失败！");
			}
		}
		return ramIndexWriter;
	};
	
	/**
	 * 内存索引转磁盘
	 * @param openMode	磁盘索引打开模式：Create / Append
	 */
	protected void ramToDisk(String openMode) {
		try {
			ramIndexWriter.close();
			ramIndexWriter = null;
			// 2.添加内存目录内容到磁盘读写
			getDiskIndexWriter(openMode);
			diskIndexWriter.addIndexes(ramDir);
			// 3.保存提交
			diskIndexWriter.forceMerge(200);//对索引文件进行优化，从而减少IO操作  
			diskIndexWriter.commit();  
		} catch (Exception e) {
			LOG.error("ramToDisk error,", e);
			throw new RuntimeException("内存索引转磁盘失败！");
		}
	};

	/**
	 * 获取Reader
	 * @return
	 */
	protected IndexReader getReader() {
		try {
			getDiskDir();
			if(null == reader){
				reader = DirectoryReader.open(diskDir);// 查询目标DISK：diskDir，RAM：ramDir
			}
		} catch (Exception e) {
			LOG.error("getReader error,", e);
			throw new RuntimeException("获取索引写对象失败!!!");
		}
		return reader;
	};

	/**
	 * 获取Searcher
	 * @return
	 */
	protected IndexSearcher getSearcher() {
		try {
			getReader();
			if(null == searcher){
				searcher = new IndexSearcher(reader);
			}
		} catch (Exception e) {
			LOG.error("getSearcher error,", e);
			throw new RuntimeException("获取searcher对象失败!!!");
		}
		return searcher;
	};
	
	/**
	 * 将Model内容添加到Lucene的Document中
	 * @param model
	 * @param fields
	 */
	protected <T extends Model<?>> void addDoc(IndexWriter indexWriter, T model, String[] fields) {
		try {
			Document doc = modelToDoc(model, fields);
			indexWriter.addDocument(doc);
		} catch (IOException e) {
			LOG.error("添加Model对象索引失败，", e);
		}
	}
	
	protected <T extends Model<?>> void addDoc(T model, String[] fields) {
		try {
			Document doc = modelToDoc(model, fields);
			getDiskIndexWriter();
			diskIndexWriter.addDocument(doc);
			diskIndexWriter.forceMerge(1);
			diskIndexWriter.commit();
			resetSearcher();
		} catch (IOException e) {
			LOG.error("添加Model对象索引失败，", e);
		}
	}
	
	/**
	 * 更新Model内容到Lucene索引
	 * @param model
	 * @param fields	需要索引的字段名，和DB保持一致
	 * @throws IOException
	 */
	protected <T extends Model<?>> void updateDoc(T model, String[] fields) {
		try {
			Document doc = modelToDoc(model, fields);
			getDiskIndexWriter();
			diskIndexWriter.updateDocument(new Term("id", model.getStr("id")), doc);
			diskIndexWriter.forceMerge(1);
			diskIndexWriter.commit();
			resetSearcher();
		} catch (IOException e) {
			LOG.error("更新Model对象索引失败，", e);
		}
	}
	
	/**
	 * 删除索引
	 * @param id	Model的ID，也是Doc的索引ID
	 * @throws IOException
	 */
	protected void deleteDoc(String id) {
		getDiskIndexWriter();
		try {
			diskIndexWriter.deleteDocuments(new Term("id", id));
			diskIndexWriter.commit();
			resetSearcher();
		} catch (IOException e) {
			LOG.error("删除Model对象索引失败，", e);
		}
	}
	
	public void resetSearcher() throws IOException {
		searcher = null;
		if (null != reader) {
			reader.close();
			reader = null;
		}
	}
	
	/**
	 * Model的内容处理成Lucene的Document，默认都处理成Text，特殊处理Date类型的值
	 * @param model
	 * @param fields	需要转换的字段名
	 * @return
	 */
	private <T extends Model<?>> Document modelToDoc(T model, String[] fields) {
		Document doc = new Document();
		for (String field : fields) {
			Object fieldValue = model.get(field);
			if (null == fieldValue) {
				continue;
			}
			String fieldType = fieldValue.getClass().getSimpleName();
			if (fieldType.equals("Date")) {
				doc.add(new StringField(field, sdf.format(fieldValue), Store.YES));
			} else {
				if (field.equals("id")) {
					doc.add(new StringField(field, fieldValue.toString(), Store.YES));
				} else {
					doc.add(new TextField(field, Jsoup.clean(fieldValue.toString(), Whitelist.simpleText()) , Store.YES));
				}
			}
		}
		return doc;
	}
	
	/**
	 * 取得批处理的count
	 * @param sql
	 * @param batchSize
	 * @return
	 */
	protected int getBatchCount(String sql, int batchSize) {
		int count = Db.queryLong(" select count(*) " + sql).intValue();
		int batchCount = count / batchSize;
		return count % batchSize == 0 ? batchCount : batchCount + 1;
	}
	
	/**
	 * 关闭资源
	 */
	public void close() {
		if(null != searcher){
			searcher = null;
		}

		try {
			if(null != reader){
				reader.close();
				reader = null;
			}
			if(null != ramIndexWriter) {
				ramIndexWriter.close();
				ramIndexWriter = null;
			}
			if(null != ramDir){
				ramDir.close();
				ramDir = null;
			}
			if (null != diskIndexWriter) {
				diskIndexWriter.close();
				diskIndexWriter = null;
			}
			if (null != diskDir) {
				diskDir.close();
				diskDir = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
