package com.lp.biz.attachment;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.lp.common.CommonUtils;
import com.jfinal.upload.UploadFile;

public class AttachmentService {

	public static final AttachmentService me = new AttachmentService();

	/**
	 * 上传文件
	 * @param file		uploadFile
	 * @param module	模块，如news/cases/doctor/
	 * @param category	分类，如缩略图thumbnail/头像avatar/幻灯片ppt
	 * @return			附件主键
	 */
	public String uploadFile(UploadFile file, String module, String category) {
		String attachmentId = CommonUtils.getUUID();
		File f = file.getFile();
		
		new Attachment().set("id", attachmentId)
				.set("name", file.getOriginalFileName())
				.set("size", f.length())
				.set("content_type", file.getContentType())
				.set("module", module)
				.set("category", category)
				.set("upload_time", new Date())
				.save();
		try {
			//重命名
			String dirPath = file.getUploadPath();
			File renamedFile = new File(dirPath + File.separator + attachmentId + file.getOriginalFileName().replaceAll(".*(\\..*)", "$1"));
			FileUtils.moveFile(f, renamedFile);
			//PPT需要转换成图片，拷贝一份到转换目录(/upload/trans/ppt)
			if (category.equals("ppt")) {
				FileUtils.copyFileToDirectory(renamedFile, new File(dirPath + "trans/ppt/"), true);
			}
			//拷贝到对应的目录下
			FileUtils.moveToDirectory(renamedFile, new File(dirPath + File.separator + category + File.separator + module), true);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return attachmentId;
	}
}
