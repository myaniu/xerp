package com.nutzside.system.module;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.nutzside.system.domain.Role;
import com.nutzside.system.service.RoleService;

@IocBean
@At("/system/role")
public class RoleModule {

	@Inject
	private RoleService roleService;

	@At
	@RequiresPermissions("role:read:*")
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch,
			@Param("..") Role roleSearch) {
		return roleService.getGridData(jqReq, isSearch, roleSearch);
	}

	@At
	@RequiresPermissions("role:create, delete, update:*")
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Role role) {
		return roleService.CUDRole(oper, ids, role);
	}

	@At
	@RequiresPermissions("role:assign_permission:*")
	public ResponseData assignPermission(@Param("roleId") String roleId,
			@Param("checkedPermissions[]") String[] checkedPermissions,
			@Param("unCheckedPermissions[]") String[] unCheckedPermissions) {
		return roleService.updateRolePermissions(roleId, checkedPermissions, unCheckedPermissions);
	}

	@At("/getAllRoleMap/*")
	@RequiresPermissions("role:read:*")
	public ResponseData getAllRoleMap(Boolean isOrgaRela) {
		return roleService.getAllRoleMap(isOrgaRela);
	}

	/**
	 * 获取指定用户的岗位列表
	 * @param jqReq
	 * @param organizationId
	 * @param userId
	 * @return
	 */
	@At
	@RequiresPermissions("user:read_post:*")
	public ResponseData getUserPostGridData(@Param("..") JqgridReqData jqReq,
			@Param("organizationId") Long organizationId, @Param("userId") Long userId) {
		return roleService.getUserPostGridData(jqReq, organizationId, userId);
	}
	
	
	@At
	@Ok("httl:system.role_manage")
	@RequiresPermissions("httl:read:role_manage")
	public void role_manage() {
	}
}