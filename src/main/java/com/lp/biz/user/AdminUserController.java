package com.lp.biz.user;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.lp.common.AppConst;
import com.lp.common.CommonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;

public class AdminUserController extends Controller {

	@RequiresRoles("admin")
	public void index() {
		Page<User> page = User.me.paginate(getParaToInt(0, 1), AppConst.BACKEND_PAGE_SIZE);
		setAttr("page", page);
		setAttr("actionUrl", "/admin/user/");
	}

	@RequiresRoles("admin")
	public void add() {

	}

	@RequiresRoles("admin")
	public void save() {
		getModel(User.class).set("password", CommonUtils.encryptSHA1("1")).save();
		redirect("/admin/user");
	}

	@RequiresRoles("admin")
	public void edit() {
		setAttr("user", User.me.findById(getParaToInt()));
	}

	@RequiresRoles("admin")
	public void update() {
		getModel(User.class).update();
		redirect("/admin/user");
	}

	@RequiresRoles("admin")
	public void delete() {
		User.me.deleteById(getParaToInt());
		redirect("/admin/user");
	}

	@ActionKey("/admin/login")
	@Before(AdminLoginValidator.class)
	public void login() {
		//get方式访问，进入登录页
		String method = getRequest().getMethod();
		if (method.toLowerCase().equals("get")) {
			render("login.html");
			return;
		}
		
		//用户名、密码验证
		String name = getPara("name");
		String password = getPara("password");
		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name,
					CommonUtils.encryptSHA1(password));
			subject.login(usernamePasswordToken);

			User user = (User)subject.getPrincipal();
			setSessionAttr(AppConst.ADMIN_USER_SESSION_NAME, user);

			String referrer = getSessionAttr(AppConst.REFERRER);
			if (null != referrer) {
				removeSessionAttr(AppConst.REFERRER);
				redirect(referrer);
			} else {
				redirect("/admin/info");
			}
		} catch (AuthenticationException e) {
			e.printStackTrace();
			setAttr("error", "用户名或密码错误");
			render("/admin/login.html");
		}

	}
	
	@ActionKey("/admin/logout")
	public void logout() {
		SecurityUtils.getSubject().logout();
		removeSessionAttr(AppConst.ADMIN_USER_SESSION_NAME);
		render("/admin/login.html");
	}
}
