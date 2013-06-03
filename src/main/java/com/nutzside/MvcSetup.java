package com.nutzside;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.nutz.resource.Scans;

public class MvcSetup implements Setup {
	@Override
	public void init(NutConfig config) {

		Dao dao = config.getIoc().get(Dao.class);

		// 批量建表
		for (Class<?> klass : Scans.me().scanPackage(
				"com.nutzside.system.domain")) {
			if (klass.getAnnotation(Table.class) != null) {
				dao.create(klass, true);
			}
		}
		
		// 添加默认记录
		FileSqlManager fm = new FileSqlManager("init_system_h2.sql");
		List<Sql> sqlList = fm.createCombo(fm.keys());
		dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
		// 若必要的数据表不存在，则初始化数据库
		/*createPermission(dao, "*:*:*", "全部权限");
		createPermission(dao, "*:read:*", "读取权限");
		createPermission(dao, "user:read:*", "对用户的浏览");
		createPermission(dao, "user:read,update:*", "对用户的浏览和编辑");
		createPermission(dao, "user:*:*", "对用户的任意操作");
		createPermission(dao, "user:roleAssign:*", "对用户分配角色");
		createPermission(dao, "role:*:*", "对角色的任意操作");
		createPermission(dao, "permission:*:*", "对权限的任意操作");
		createPermission(dao, "role:PermissionAssign:*", "对角色分配权限");

		createRole(dao, "admin", "超级管理员：拥有全部权限的角色", new Integer[] { 1 });
		createRole(dao, "viewer", "审阅者：拥有任何对象的浏览权限的角色", new Integer[] { 2 });
		createRole(dao, "user-superadmin", "用户超级管理员：拥有对用户的任意操作权限的角色",
				new Integer[] { 5 });
		createRole(dao, "user-admin", "用户管理员：拥有对用户的浏览、增加和编辑(不包括删除)权限的角色",
				new Integer[] { 4 });
		createRole(dao, "security-admin",
				"安全管理员：拥有对角色和权限的任意操作，对用户分配角色及对角色分配权限的权限", new Integer[] {
						7, 8, 9 });

		 createUser(dao, "1000", "admin", new Integer[] { 1,2,3,4,5 });
		  createUser(dao, "1001", "jack", new Integer[] { 2, 3 });
		  createUser(dao, "1002", "kate", new Integer[] { 4 }); createUser(dao,
		  "1003", "sawyer", new Integer[] { 2 }); createUser(dao, "1004",
		  "john", new Integer[] { 2 }); createUser(dao, "1005", "ben", new
		  Integer[] { 5 });
		*/
		// 初始化的数据表
		// MvcSetupDefaultHandler.dolpTableInit();

	}



	@Override
	public void destroy(NutConfig arg0) {
		// TODO Auto-generated method stub

	}

}