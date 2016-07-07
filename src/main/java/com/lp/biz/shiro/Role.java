// ======================================
// Project Name:jfinal-tpl
// Package Name:com.lp.biz.shiro
// File Name:Role.java
// Create Date:2016年07月05日  11:07
// ======================================
package com.lp.biz.shiro;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author 刘鹏
 * @version 2016年07月05日  11:07
 */
public class Role extends Model<Role> {

  private static final long serialVersionUID = -7293941035613419006L;

  private static final Logger LOGGER = Logger.getLogger(Role.class);

  public static Role me = new Role();

  public void deleteRole(Integer roleId) {
    deleteById(roleId);
    Db.update("delete from user_role where role_id = ? ", roleId);
  }

  public Page<Role> paginate(int pageNumber, int pageSize) {
    Page<Role> page = super.paginate(pageNumber, pageSize, "select *", "from role");
    page.getList().forEach(role ->
        role.put("permission", Permission.me.findPermissionsOfRole("id", role.getInt("id"))));
    return page;
  }

  public List<Role> list(Integer userId) {
    List<Role> assignedRoles = find("SELECT * FROM role WHERE id IN (select role_id from user_role where user_id = ?)", userId);
    assignedRoles.stream().forEach(r -> r.put("checked", true));

    List<Role> notAssignedRoles = find("SELECT * FROM role WHERE id NOT IN (select role_id from user_role where user_id = ?)", userId);

    List<Role> allRoles = new ArrayList<>();
    allRoles.addAll(assignedRoles);
    allRoles.addAll(notAssignedRoles);

    return allRoles;
  }

  /**
   * 更新用户角色
   * @param rolesId
   * @param userId
   */
  public boolean updateRolesOfUser(Collection<Integer> rolesId, Integer userId) {
    boolean success = true;
    try {
      // 清除所有角色
      Db.update("delete from user_role where user_id = ?", userId);
      // 添加新角色
      if (null != rolesId) {
        rolesId.forEach(roleId -> Db.save("user_role",new Record().set("user_id", userId).set("role_id", roleId)));
      }
    } catch (Exception e) {
      LOGGER.error(e);
      success = false;
    }
    return success;
  }

}
