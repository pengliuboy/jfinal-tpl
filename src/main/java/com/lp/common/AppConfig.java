package com.lp.common;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.i18n.I18nInterceptor;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.tx.TxByMethods;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.shiro.ShiroInterceptor;
import com.jfinal.plugin.shiro.ShiroPlugin;
import com.lp.biz.attachment.Attachment;
import com.lp.biz.attachment.AttachmentController;
import com.lp.biz.index.AdminIndexController;
import com.lp.biz.index.IndexController;
import com.lp.biz.info.AdminInfoController;
import com.lp.biz.info.Info;
import com.lp.biz.info.InfoController;
import com.lp.biz.shiro.AdminPermissionController;
import com.lp.biz.shiro.AdminRoleController;
import com.lp.biz.shiro.Permission;
import com.lp.biz.shiro.Role;
import com.lp.biz.user.AdminUserController;
import com.lp.biz.user.User;
import org.beetl.ext.MyBeetlRenderFactory;

public class AppConfig extends JFinalConfig {

	private Routes routes;

	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("config.properties");
		me.setDevMode(prop.getBoolean("devMode"));
		me.setMainRenderFactory(new MyBeetlRenderFactory());

		me.setErrorView(401, "/401.html");
		me.setErrorView(403, "/403.html");
		me.setError404View("/404.html");
		me.setError500View("/500.html");
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/", IndexController.class);
		me.add("/info", InfoController.class);
		me.add("/atta", AttachmentController.class);

		me.add("/admin", AdminIndexController.class);
		me.add("/admin/info", AdminInfoController.class);
		me.add("/admin/user", AdminUserController.class);
		me.add("/admin/role", AdminRoleController.class);
		me.add("/admin/permission", AdminPermissionController.class);

		this.routes = me;
	}

	@Override
	public void configPlugin(Plugins me) {
		DruidPlugin druidPlugin = new DruidPlugin(prop.get("jdbcUrl"), prop.get("db.user"), prop.get("db.password"));
		druidPlugin.set(prop.getInt("db.inital.size"), prop.getInt("db.min.idle"), prop.getInt("db.max.active"));
		me.add(druidPlugin);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setDialect(new MysqlDialect());
		arp.setShowSql(prop.getBoolean("db.showsql"));
		arp.setDevMode(prop.getBoolean("devMode"));

		arp.addMapping("attachment", Attachment.class);
		arp.addMapping("info", Info.class);
		arp.addMapping("user", User.class);
		arp.addMapping("permission", Permission.class);
		arp.addMapping("role", Role.class);
		me.add(arp);
		
		//缓存插件
		me.add(new EhCachePlugin());
		
		//redis demo，需要开启redis服务
		//RedisPlugin rp = new RedisPlugin("centos", "localhost", 6379);
		//me.add(rp);
		
		//定时任务demo
		//SchedulerPlugin sp = new SchedulerPlugin("job.properties");
		//me.add(sp);

		ShiroPlugin shiroPlugin = new ShiroPlugin(routes);
		shiroPlugin.setUnauthorizedUrl("/401.html");
		shiroPlugin.setLoginUrl("/admin/login");
		me.add(shiroPlugin);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new TxByMethods("save", "update", "delete"));	//事务拦截器
		me.add(new I18nInterceptor());							//国际化拦截器
		me.add(new ShiroInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		//me.add(new RewriteHandler());
	}
	
	public static void main(String[] args) {
		JFinal.start("D:/IdeaProjects/jfinal-tpl/out/artifacts/jfinal-tpl/exploded/jfinal-tpl-1.0-SNAPSHOT.war", 80, "/", 5);
	}

}
