package com.lp.interceptor;

import java.util.List;

import com.jfinal.aop.Invocation;
import com.jfinal.aop.PrototypeInterceptor;
import com.jfinal.core.Controller;

public class Condition extends PrototypeInterceptor {

	private int pageNumber;
	
	private String name;
	
	private Integer age;
	
	//TODO:other params
	
	@Override
	public void doIntercept(Invocation ai) {
		Controller c = ai.getController();
		pageNumber = c.getParaToInt(0, 1);
		//TODO:
		name = c.getPara("name");
		age = c.getParaToInt("age");
		c.setAttr("condition", this);
		
		ai.invoke();
	}
	
	public void buildSql(StringBuffer sql, List<Object> params) {
		sql.append(" from user where 1 = 1 ");
		if (null != name) {
			sql.append(" and name = ? ");
			params.add(name);
		}
		if (null != age) {
			sql.append(" and age = ? ");
			params.add(age);
		}
		
		//TODO:other params
		
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

}
