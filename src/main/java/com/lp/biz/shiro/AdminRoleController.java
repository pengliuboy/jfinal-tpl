// ======================================
// Project Name:jfinal-tpl
// Package Name:com.lp.biz.shiro
// File Name:AdminRoleController.java
// Create Date:2016年07月05日  11:03
// ======================================
package com.lp.biz.shiro;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.lp.common.AppConst;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresRoles;

/**
 * @author 刘鹏
 * @version 2016年07月05日  11:03
 */
@RequiresRoles("admin")
public class AdminRoleController extends Controller {

  public void index() {
    Page<Role> page = Role.me.paginate(getParaToInt(0, 1), AppConst.BACKEND_PAGE_SIZE);
    setAttr("page", page);
    setAttr("actionUrl", "/admin/role/");
  }

  public void add() {

  }

  public void save() {
    getModel(Role.class).save();
    redirect("/admin/role");
  }

  public void edit() {
    setAttr("role", Role.me.findById(getParaToInt()));
  }

  public void update() {
    getModel(Role.class).update();
    redirect("/admin/role");
  }

  public void delete() {
    Role.me.deleteRole(getParaToInt());
    redirect("/admin/role");
  }

  /**
   * JSON for User to display roles by Ajax
   */
  public void list() {
    List<Role> roles = Role.me.list(getParaToInt("userId"));
    List<Map<String, Object>> jsonArray = new ArrayList<>();
    for (Role role : roles) {
      Map<String, Object> item = new HashMap<>();
      item.put("id", role.getInt("id"));
      item.put("role", role.getStr("role"));
      item.put("checked", role.getBoolean("checked"));
      jsonArray.add(item);
    }

    renderJson(jsonArray);
  }

  /**
   * JSON for update user's roles by Ajax
   */
  public void updateRolesOfUser() {
    Integer[] roles = getParaValuesToInt("roles");
    Integer userId = getParaToInt("userId");
    Map<String, Object> json = new HashMap<>();
    json.put("success",
        Role.me.updateRolesOfUser(null == roles ? null : Arrays.asList(roles), userId));
    renderJson(json);
  }
}
