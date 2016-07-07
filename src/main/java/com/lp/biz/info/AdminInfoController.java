package com.lp.biz.info;

import com.jfinal.core.Controller;
import com.jfinal.ext.render.excel.PoiRender;
import com.jfinal.plugin.activerecord.Page;
import com.lp.common.AppConst;
import com.lp.common.CommonUtils;
import com.lp.lucene.DocInfo;
import java.util.Date;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

//@Before(AdminInterceptor.class)
public class AdminInfoController extends Controller {

	@RequiresPermissions("info:index")
	public void index() {
		Page<Info> page = Info.me.paginate(getParaToInt(0, 1), AppConst.BACKEND_PAGE_SIZE);
		setAttr("page", page);
		setAttr("actionUrl", "/admin/info/");
	}

	@RequiresPermissions("info:add")
	public void add() {
		
	}

	@RequiresPermissions("info:add")
	public void save() {
		Info info = getModel(Info.class).set("id", CommonUtils.getUUID())
			.set("createTime", new Date())
			.set("updateTime", new Date());
		info.save();
		
		//Add to index
		DocInfo.me.add(info);
		
		redirect("/admin/info");
	}

	@RequiresPermissions("info:edit")
	public void edit() {
		Info info = Info.me.findById(getPara());
		setAttr("info", info);
	}

	@RequiresPermissions("info:edit")
	public void update() {
		Info info = getModel(Info.class).set("updateTime", new Date());
		info.update();
		
		//Update index
		DocInfo.me.update(info);
		
		redirect("/admin/info");
	}

	@RequiresPermissions("info:delete")
	public void delete() {
		String id = getPara();
		Info.me.deleteById(id);
		
		//Delete from index
		DocInfo.me.delete(id);
		
		redirect("/admin/info");
	}

	@RequiresRoles("admin")
	public void export() {
		List<Info> data = Info.me.getExportData();
		render(PoiRender.me(data)
				.fileName("info.xlsx")
				.sheetName("data")
				.headers(new String[] {"标题", "作者", "来源", "创建时间"})
				.columns("title", "author", "origin", "createTime"));
	}
}
