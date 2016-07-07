package com.lp.common;

import com.alibaba.fastjson.JSONObject;

public class JsonDecorator {

	public static final JSONObject create() {
		return new JSONObject();
	}
	
	public static final JSONObject success() {
		JSONObject json = create();
		json.put("success", true);
		return json;
	}
	
	public static final JSONObject success(String msg) {
		JSONObject json = success();
		json.put("msg", msg);
		return json;
	}
	
	public static final JSONObject fail() {
		JSONObject json = create();
		json.put("success", false);
		return json;
	}
	
	public static final JSONObject fail(String msg) {
		JSONObject json = fail();
		json.put("msg", msg);
		return json;
	}
	
}
