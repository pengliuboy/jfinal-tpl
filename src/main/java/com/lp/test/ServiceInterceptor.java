package com.lp.test;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class ServiceInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		System.out.println("Invoke biz interceptor");
	}

}
