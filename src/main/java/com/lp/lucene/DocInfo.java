package com.lp.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;

import com.jfinal.plugin.activerecord.Page;
import com.lp.biz.info.Info;
import com.lp.common.AppConst;

public class DocInfo extends DocBase {
	
	private static final Logger LOG = Logger.getLogger(DocInfo.class);
	
	public static final DocInfo me = new DocInfo();

	private static String indexPath;
	
	//需要索引的字段
	private static final String[] fieldNames = { 
		"id", "title", "digest", "content", "createTime", "updateTime"
	};
	
	@Override
	public void run() {
		try {
			indexAll();
		} catch (Exception e) {
			LOG.error("初始化Info Table索引出错，", e);
		}
	}
	
	public void indexAll() throws IOException {
		LOG.info("Info table 索引开始......");
		Long startTime = System.currentTimeMillis();
		
		int batchCount = getBatchCount("from info", 200);
		for (int i = 0; i < batchCount; i++) {
			LOG.info("索引批次：" + i);
			IndexWriter ramIndexWriter = getRamIndexWriter();//调用RAM写
			List<Info> list = Info.me.find("select * from info limit ?,?", i * 200, (i + 1) * 200);
			for (Info info : list) {
				addDoc(ramIndexWriter, info, fieldNames);
			}
			ramToDisk("Create");
		}
		long endTime = System.currentTimeMillis();
		LOG.info("Info table 索引结束，耗时：" + (endTime - startTime));
	}
	
	public void add(Info info) {
		addDoc(info, fieldNames);
	}
	
	public void update(Info info) {
		updateDoc(info, fieldNames);
	}
	
	public void delete(String id) {
		deleteDoc(id);
	}
	
	public Page<Info> search(String keyword, int pageNumber) throws IOException, ParseException {
		String[] queryFields = new String[]{"title", "content"};
		
		QueryParser queryParser = new MultiFieldQueryParser(queryFields, analyzer);
    	
    	int wantNo = pageNumber * AppConst.LUCENE_SEARCH_SIZE;
    	TopScoreDocCollector res = TopScoreDocCollector.create(wantNo, false);
    	
    	getSearcher();
    	searcher.search(queryParser.parse(keyword), res);
    	int totalRow = res.getTotalHits();
    	
    	if (totalRow == 0) {
			return new Page<Info>(new ArrayList<Info>(0), pageNumber, AppConst.LUCENE_SEARCH_SIZE, 0, 0);
		}
    	
    	TopDocs topDocs = res.topDocs((pageNumber - 1) * AppConst.LUCENE_SEARCH_SIZE);
		ScoreDoc[] docs = topDocs.scoreDocs;
    	List<Info> list = new LinkedList<Info>();
    	for (ScoreDoc scoreDoc : docs) {
    		Document doc = searcher.doc(scoreDoc.doc);
    		Info info = parseDocToInfo(doc);
    		list.add(info);
    	}
    	
    	int totalPage = totalRow / AppConst.LUCENE_SEARCH_SIZE;
    	if (totalRow % AppConst.LUCENE_SEARCH_SIZE == 0) {
    		totalPage += 1;
    	}
    	return new Page<Info>(list, pageNumber, AppConst.LUCENE_SEARCH_SIZE, totalPage, totalRow);
	}
	
	private Info parseDocToInfo(Document doc) {
		Info info = new Info();
		for (String field : fieldNames) {
			if (field.endsWith("Time")) {
				String time = doc.get(field);
				if (null != time && !time.isEmpty()) {
					try {
						info.set("createTime", sdf.parse(time));
					} catch (java.text.ParseException e) {
						LOG.error("Date类型转换出错，", e);
					}
				}
			} else {
				info.set(field, doc.get(field));
			}
		}
		return info;
	}
	
	public Info search(String searchKeyWords) {
		Info info = new Info();
		try {
			String[] queryFields = new String[]{"title", "content"};
			
			QueryParser queryParser = new MultiFieldQueryParser(queryFields, analyzer);
            queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
            
        	Query query = queryParser.parse(searchKeyWords);
            
        	searcher = getSearcher();
			TopDocs topDocs = searcher.search(query, 1);
			
            int length = topDocs.totalHits;//当前页有多少条记录
            if(length > 0){
            	ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            	Document doc = searcher.doc(scoreDocs[0].doc);
            	info.set("id", doc.get("id"));
            	info.set("title", doc.get("title"));
            	info.set("content", doc.get("content"));
            	info.set("createTime", sdf.parse(doc.get("createTime")));
            }
            	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	@Override
	protected String getIndexPath() {
		if(null == indexPath) {
//			indexPath = PropKit.get("lucene.index.path", "E:/lucenen_home/tpl");
			indexPath = "E:/lucenen_home/tpl";
		}
		return indexPath;
	}

}
