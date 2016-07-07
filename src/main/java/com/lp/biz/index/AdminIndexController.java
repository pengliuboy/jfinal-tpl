package com.lp.biz.index;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.lp.interceptor.AdminInterceptor;

@Before(AdminInterceptor.class)
public class AdminIndexController extends Controller {

	public void index() {
		redirect("/admin/info");
	}
	
}
