// ======================================
// Project Name:jfinal-tpl
// Package Name:com.lp.biz.user
// File Name:Permission.java
// Create Date:2016年07月04日  17:09
// ======================================
package com.lp.biz.shiro;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * @author 刘鹏
 * @version 2016年07月04日  17:09
 */
public class Permission extends Model<Permission> {

  private static final Logger LOGGER = Logger.getLogger(Permission.class);

  public static final Permission me = new Permission();

  public Page<Permission> paginate(int pageNumber, int pageSize) {
    return super.paginate(pageNumber, pageSize, "select *", "from permission");
  }

  public void deletePermission(int permissionId) {
    deleteById(permissionId);
    Db.update("delete from role_permission where per_id = ? ", permissionId);
  }

  public List<Permission> list(Integer roleId) {
    List<Permission> assignedPermissions = find("SELECT * FROM permission WHERE id IN ( SELECT per_id FROM role_permission WHERE role_id = ?)", roleId);
    assignedPermissions.forEach(permission -> permission.put("checked", true));

    List<Permission> notAssignedPermissions = find("SELECT * FROM permission WHERE id NOT IN ( SELECT per_id FROM role_permission WHERE role_id = ?)", roleId);

    List<Permission> allPermissions = new ArrayList<>();
    allPermissions.addAll(assignedPermissions);
    allPermissions.addAll(notAssignedPermissions);

    return allPermissions;
  }

  /**
   * 根据角色字段获取权限集合
   * @param field name:角色名称 id:角色ID
   * @param value 值
   * @return
   */
  public Set<String> findPermissionsOfRole(String field, Object value) {
    StringBuilder sql = new StringBuilder(256);
    sql.append(" SELECT p.permission FROM permission p where p.id in ");
    sql.append(" ( SELECT rp.per_id FROM role r LEFT JOIN role_permission rp ");
    sql.append("   ON r.id = rp.role_id WHERE r." + field + " = ? ) ");
    List<Permission> list = find(sql.toString(), value);
    if (null == list || list.isEmpty()) {
      return null;
    }
    Set<String> permissions = new HashSet<>();
    list.forEach(p -> permissions.add(p.getStr("permission")));
    return permissions;
  }
  public boolean updatePermsOfRole(Collection<Integer> permsId, Integer roleId) {
    boolean success = true;
    try {
      // 清除所有权限
      Db.update("delete from role_permission where role_id = ?", roleId);
      // 添加新权限
      if (null != permsId) {
        permsId.forEach(id -> Db.save("role_permission", new Record().set("role_id", roleId).set("per_id", id)));
      }
    } catch (Exception e) {
      LOGGER.error(e);
      success = false;
    }
    return success;
  }

  /**
   * 根据用户字段获取权限集合
   * @param field name:用户名 id:用户Id
   * @param value 值
   * @return
   */
  public Set<String> findPermissionsOfUser(String field, Object value) {
    StringBuilder sql = new StringBuilder(256);
    sql.append(" SELECT p.permission FROM permission p where p.id in ");
    sql.append(" ( SELECT rp.per_id FROM ");
    sql.append("  ( SELECT ur.role_id FROM user u LEFT JOIN user_role ur ON u.id = ur.user_id WHERE u." + field + " = ?) ");
    sql.append("  ur LEFT JOIN role_permission rp ON ur.role_id = rp.role_id ");
    sql.append(" ) ");
    List<Permission> list = find(sql.toString(), value);
    Set<String> permissions = new HashSet<>();
    list.forEach(p -> permissions.add(p.getStr("permission")));
    return permissions;
  }

}
