package com.lp.biz.info;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.LinkedList;
import java.util.List;

public class Info extends Model<Info> {

	private static final long serialVersionUID = 2078383679487750106L;
	
	private final int ROWS_PER_TIME_EXPORT = 500;
	
	public static final Info me = new Info();
	
	public Page<Info> paginate(int pageNumber, int pageSize) {
		return super.paginate(pageNumber, pageSize, "select info.*, atta.name attaName", "from info left join attachment atta on info.thumbnailAttaId = atta.id order by updateTime DESC");
	}
	
	public List<Info> getExportData() {
		List<Info> data = new LinkedList<>();
		long ct = count("from info", new Object[0]);
		long times = ct / ROWS_PER_TIME_EXPORT;
		if (ct % ROWS_PER_TIME_EXPORT != 0) {
			times += 1;
		}
		for (int i = 0; i < times; i++) {
			List<Info> items = find("select title, author, origin, createTime from info limit ?,?", i * ROWS_PER_TIME_EXPORT, ROWS_PER_TIME_EXPORT);
			data.addAll(items);
		}
		return data;
	}
	
}
