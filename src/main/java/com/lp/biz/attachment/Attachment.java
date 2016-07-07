package com.lp.biz.attachment;

import com.jfinal.plugin.activerecord.Model;
import java.util.List;

//@TableBind(tableName="attachment")
public class Attachment extends Model<Attachment> {

	private static final long serialVersionUID = 3827876634977354068L;

	public static final Attachment me = new Attachment();
	
	public List<Attachment> findAttas(String ids) {
		String[] idArr = ids.split(",");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from attachment where id in ( ");
		for (int index = 0; index < idArr.length; index++) {
			sql.append(" ? ");
			if (index != idArr.length - 1) {
				sql.append(" , ");
			}
		}
		sql.append(" ) ");
		
		return find(sql.toString(), (Object[])idArr);
	}
}
