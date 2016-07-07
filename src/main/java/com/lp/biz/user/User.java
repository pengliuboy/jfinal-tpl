// ======================================
// Project Name:jfinal-tpl
// Package Name:com.lp.biz.user
// File Name:User.java
// Create Date:2016年07月04日  16:44
// ======================================
package com.lp.biz.user;

import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 刘鹏
 * @version 2016年07月04日  16:44
 */
public class User extends Model<User> {

  private static final long serialVersionUID = -9097274209755329843L;

  public static final User me = new User();

  public Page<User> paginate(int pageNumber, int pageSize) {
    Page<User> page = super.paginate(pageNumber, pageSize, "select *", "FROM user");
    page.getList().forEach(user -> user.put("role", findRoles("id", user.getInt("id"))));
    return page;
  }

  public Set<String> findRoles(String field, Object value) {
    StringBuilder sql = new StringBuilder(128);
    sql.append(" SELECT r.role userRole ");
    sql.append(" FROM user u LEFT JOIN user_role ur ON u.id = ur.user_id ");
    sql.append(" LEFT JOIN role r ON ur.role_id = r.id ");
    sql.append(" WHERE u." + field + " = ? AND role IS NOT NULL");
    List<User> users = find(sql.toString(), value);
    if (users == null || users.isEmpty()) {
      return null;
    }

    Set<String> roles = new HashSet<>();
    users.forEach(u -> roles.add(u.getStr("userRole")));
    return roles;
  }

  public static void main(String[] args) {
    List<User> users = Lists.newArrayList(
        new User().put("name","admin").put("role", "admin"),
        new User().put("name","admin").put("role", "front"),
        new User().put("name","leader").put("role", "leader"),
        new User().put("name","leader").put("role", "programmer")
    );


    Map<String, List<String>> rst =
    users.stream().collect(Collectors.groupingBy(
        user -> user.getStr("name"),
        Collectors.mapping(user -> user.getStr("role"), Collectors.toList())
    ));
    rst.forEach((key, value) -> {
      System.out.print("key:" + key + "\nValues:");
      value.forEach(item -> System.out.print(item + "\t"));
      System.out.println();
    });

    Map<String, List<User>> r =
    users.stream().collect(Collectors.groupingBy(
        user -> user.getStr("name"),
        Collectors.toList()
    ));
  }

}
