package com.lp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.lp.bean.UserBean;
import com.lp.common.AppConst;
import com.lp.common.HttpUtils;
import com.lp.common.JsonDecorator;

public class LoginInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		Controller controller = ai.getController();
		HttpSession session = controller.getSession();
		if (null != session && session.getAttribute(AppConst.USER_SESSION_NAME) != null) {
			ai.invoke();
			return;
		}
		
		String userInfo = controller.getCookie(AppConst.USER_COOKIE_NAME);
		if (null != userInfo && !userInfo.isEmpty()) {
			//Parse string to user bean
			UserBean ub = JSONObject.parseObject(userInfo, UserBean.class);
			session.setAttribute(AppConst.USER_SESSION_NAME, ub);
			ai.invoke();
			return;
		}
		
		//记录跳转前的URL
		HttpServletRequest request = controller.getRequest();
		String xrw = request.getHeader("x-requested-with");
		if (xrw == null || !xrw.equals("XMLHttpRequest")) {
			String referer = HttpUtils.getReferrer(request);
			session.setAttribute(AppConst.REFERRER, referer);
			controller.redirect("/");
		} else {
			JSONObject json = JsonDecorator.fail("需要登录才能操作。");
			controller.renderJson(json);
		}
	}

}
