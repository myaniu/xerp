package com.xerp.module.basic;

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
import com.xerp.domain.basic.ProdCategory;
import com.xerp.service.basic.ProdCategoryService;

@At("/erp/basic/prodcategory")
@IocBean(fields = { "dao" })
public class ProdCategoryModule extends IdEntityService<ProdCategory> {

	@Inject
	ProdCategoryService prodCategoryService;

	@At
	@Ok("httl:erp.basic.prodcategory_treeLookup")
	@RequiresPermissions("prodcategory:read:*")
	public Map<String, Object> treeLookup() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pagerlist", prodCategoryService.list());
		return map;
	}

	@At
	@Ok("httl:erp.basic.prodcategory_list")
	@RequiresPermissions("prodcategory:read:*")
	public Map<String, Object> Pagerlist(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage, @Param("..") ProdCategory obj) {

		return prodCategoryService.Pagerlist(pageNum, numPerPage, obj);
	}

	@At
	@RequiresPermissions("prodCategory:indert:*")
	public Object add(@Param("::district.") ProdCategory obj) {
		try {
			prodCategoryService.insert(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK, "prodcategory");
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}
	
	@At
	@RequiresPermissions("prodCategory:update:*")
	public Object update(@Param("::district.") ProdCategory obj) {
		try {
			prodCategoryService.update(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK, "prodcategory");
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}

	/**
	 * 跳转到添加页面
	 */
	@At
	@Ok("httl:erp.basic.prodcategory_add")
	public void addUi() {
	}

	/**
	 * 跳转到高级查询页面
	 */
	@At
	@Ok("httl:erp.basic.prodcategory_query")
	public void queryUi() {
	}

	/**
	 * 跳转到查看页面
	 */
	@At
	@Ok("httl:erp.basic.prodcategory_view")
	public ProdCategory view(@Param("id") Long id) {
		return prodCategoryService.view(id);
	}

	/**
	 * 删除-
	 * 
	 * @return
	 */
	@At
	public Object delete(@Param("..") ProdCategory obj) {
		try {
			dao().delete(obj);
			return AjaxUtil.dialogAjaxDonenoclose(AjaxUtil.OK, "prodcategory");
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}
}
