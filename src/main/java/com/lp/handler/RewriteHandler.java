package com.lp.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class RewriteHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		int dotIndex = target.indexOf(".html");
		if (dotIndex != -1) {
			target = target.substring(0, dotIndex);
		}
		next.handle(target, request, response, isHandled);
	}

}
