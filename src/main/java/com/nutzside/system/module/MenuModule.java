package com.nutzside.system.module;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.service.IdEntityService;

import com.nutzside.common.util.AjaxUtil;
import com.nutzside.system.domain.Menu;
import com.nutzside.system.service.MenuService;

@At("/system/menu")
@IocBean(fields = { "dao" })
public class MenuModule extends IdEntityService<Menu> {

	@Inject
	MenuService menuService;

	@At
	@Ok("httl:system.menu_treeLookup")
	@RequiresPermissions("Menu:read:*")
	public Map<String, Object> treeLookup() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagerlist", menuService.list());
		return map;
	}

	@At
	@Ok("httl:system.menu_list")
	@RequiresPermissions("Menu:read:*")
	public Map<String, Object> Pagerlist(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage, @Param("..") Menu obj) {

		return menuService.Pagerlist(pageNum, numPerPage, obj);
	}

	@At
	@RequiresPermissions("Menu:indert:*")
	public Object add(@Param("::district.") Menu obj) {
		try {
			 if (obj.getParentId()==null){
				 obj.setParentId(0L);
			 }
			menuService.insert(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK, "menu");
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}
	
	@At
	@RequiresPermissions("Menu:update:*")
	public Object update(@Param("::district.") Menu obj) {
		try {
			 if (obj.getParentId()==null){
				 obj.setParentId(0L);
			 }
			menuService.update(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK, "menu");
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}

	/**
	 * 跳转到添加页面
	 */
	@At
	@Ok("httl:system.menu_add")
	public void addUi() {
	}
	/**
	 * 跳转到添加页面
	 */
	@At
	@Ok("jsp:system.menu_role")
	@RequiresPermissions("Menu:read:*")
	public Map<String, Object> p_menu_role() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("treeList", menuService.list());
		return map;
	}
	/**
	 * 跳转到高级查询页面
	 */
	@At
	@Ok("httl:system.menu_query")
	public void queryUi() {
	}

	/**
	 * 跳转到查看页面
	 */
	@At
	@Ok("httl:system.menu_view")
	public Menu view(@Param("id") Long id) {
		return menuService.view(id);
	}

	/**
	 * 删除-
	 * 
	 * @return
	 */
	@At
	public Object delete(@Param("..") Menu obj) {
		try {
			dao().delete(obj);
			return AjaxUtil.dialogAjaxDonenoclose(AjaxUtil.OK, "menu");
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}
}
