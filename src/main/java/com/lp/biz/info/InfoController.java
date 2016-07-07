package com.lp.biz.info;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.lp.common.AppConst;
import com.lp.lucene.DocInfo;
import java.io.IOException;
import org.apache.lucene.queryparser.classic.ParseException;

public class InfoController extends Controller {

	@Before(CacheInterceptor.class)
	public void index() {
		Page<Info> page = Info.me.paginate(getParaToInt(0, 1), AppConst.FRONT_PAGE_SIZE);
		setAttr("page", page);
		render("index.html");
	}
	
	public void search() {
		try {
			//Lucene检索
			Page<Info> page = DocInfo.me.search(getPara("keyword"), getParaToInt("pageNumber", 1));
			setAttr("page", page);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		render("index.html");
	}
}
