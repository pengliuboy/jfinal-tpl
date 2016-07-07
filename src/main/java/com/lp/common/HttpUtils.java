package com.lp.common;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

	public static String getReferrer(HttpServletRequest request) {
		String uri = request.getRequestURI();
		
		String queryStr = request.getQueryString();
		if (null != queryStr) {
			uri += queryStr;
		} else {
			Map<String, String[]> paramMaps = request.getParameterMap();
			if (null != paramMaps && !paramMaps.isEmpty()) {
				StringBuffer params = new StringBuffer();
				Set<Entry<String, String[]>> entries = paramMaps.entrySet();
				for (Entry<String, String[]> entry : entries) {
					params.append(entry.getKey());
					params.append("=");
					params.append(entry.getValue()[0]);
					params.append("&");
				}
				uri += params.subSequence(0, params.length() - 1);
			}
		}
		
		return uri;
	}
}
