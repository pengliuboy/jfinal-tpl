package com.lp.test;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

public class Service {

	@Before(Tx.class)
	public void save() {
		//do sth in transaction
	}
	
	@Before(Tx.class)
	public void delete() {
		//do sth in transaction
	}
}
