package com.lp.biz.attachment;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttachmentController extends Controller {
	
	/**
	 * 上传并返回附件ID及URL
	 */
	public void upload() {
		UploadFile file = getFile();
		String fileName = file.getFileName();
		String fileSuffix = getSuffix(fileName);
		String module = getPara(0);
		String category = getPara(1);
		
		String id = AttachmentService.me.uploadFile(file, module, category);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("id", id);
		json.put("url", "/upload/" + category + "/" + module + "/" + id + fileSuffix);
		json.put("name", fileName);
		
		renderJson(json);
	}
	
	/**
	 * 返回给定ID的附件的访问URL
	 */
	public void show() {
		String ids = getPara();
		if (null == ids) {
			ids = getPara("ids");
		}
		
		List<Map<String, Object>> json = new ArrayList<Map<String,Object>>();
		List<Attachment> list = Attachment.me.findAttas(ids);
		
		//转换成JsonArray
		String url = null;
		for (Attachment atta : list) {
			url = getAttaURL(atta);
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("id", atta.getStr("id"));
			item.put("url", url);
			item.put("name", atta.getStr("name"));
			json.add(item);
		}
		renderJson(json);
	}
	
	private String getAttaURL(Attachment atta) {
		StringBuffer path = new StringBuffer();
		path.append("/upload/");
		path.append(atta.getStr("category"));
		path.append("/");
		path.append(atta.getStr("module"));
		path.append("/");
		path.append(atta.getStr("id"));
		path.append(getSuffix(atta.getStr("name")));
		return path.toString();
	}
	
	private String getSuffix(String name) {
		return name.replaceAll(".*(\\..*)", "$1");
	}

}
