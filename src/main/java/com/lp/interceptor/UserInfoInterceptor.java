package com.lp.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.lp.bean.UserBean;
import com.lp.common.AppConst;

public class UserInfoInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		Controller c = ai.getController();
		
		UserBean user = c.getSessionAttr(AppConst.USER_SESSION_NAME);
		if (user == null) {
			String userInfoStr = c.getCookie(AppConst.USER_COOKIE_NAME);
			if (userInfoStr != null) {
				UserBean userInfo = JSONObject.parseObject(userInfoStr, UserBean.class);
				if (null != userInfo) {
					c.setSessionAttr(AppConst.USER_SESSION_NAME, userInfo);
				} else {
					c.removeCookie(AppConst.USER_COOKIE_NAME);
				}
			}
		}
		
		ai.invoke();
	}

}
