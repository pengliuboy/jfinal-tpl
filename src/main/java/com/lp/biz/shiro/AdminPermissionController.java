// ======================================
// Project Name:jfinal-tpl
// Package Name:com.lp.biz.shiro
// File Name:AdminPermissionController.java
// Create Date:2016年07月06日  9:45
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
 * @version 2016年07月06日  9:45
 */
@RequiresRoles("admin")
public class AdminPermissionController extends Controller {

  public void index() {
    Page<Permission> page = Permission.me.paginate(getParaToInt(0, 1), AppConst.BACKEND_PAGE_SIZE);
    setAttr("page", page);
    setAttr("actionUrl", "/admin/permission/");
  }

  public void add() {

  }

  public void save() {
    getModel(Permission.class).save();
    redirect("/admin/permission");
  }

  public void edit() {
    setAttr("permission", Permission.me.findById(getParaToInt()));
  }

  public void update() {
    getModel(Permission.class).update();
    redirect("/admin/permission");
  }

  public void delete() {
    Permission.me.deletePermission(getParaToInt());
    redirect("/admin/permission");
  }

  /**
   * JSON for User to display roles by Ajax
   */
  public void list() {
    List<Permission> permissions = Permission.me.list(getParaToInt("roleId"));
    List<Map<String, Object>> jsonArray = new ArrayList<>();
    for (Permission permission : permissions) {
      Map<String, Object> item = new HashMap<>();
      item.put("id", permission.getInt("id"));
      item.put("permission", permission.getStr("permission"));
      item.put("checked", permission.getBoolean("checked"));
      jsonArray.add(item);
    }
    renderJson(jsonArray);
  }

  public void updatePermsOfRole() {
    Integer[] perms = getParaValuesToInt("perms");
    Integer roleId = getParaToInt("roleId");
    Map<String, Object> json = new HashMap<>();
    json.put("success",
        Permission.me.updatePermsOfRole(null == perms ? null : Arrays.asList(perms), roleId));
    renderJson(json);
  }

}
