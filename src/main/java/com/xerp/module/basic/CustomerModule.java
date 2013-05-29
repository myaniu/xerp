package com.xerp.module.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.service.IdEntityService;

import com.nutzside.common.util.AjaxUtil;
import com.xerp.domain.basic.Customer;

@At("/erp/basic/customer")
@IocBean(fields = { "dao" })
public class CustomerModule extends IdEntityService<Customer> {
	/**
	 * 跳转到添加页面
	 */
	@At
	@Ok("httl:erp.basic.customer_input")
	public Customer addUi() {
		return new Customer();
	}

	/**
	 * 跳转到修改页面
	 */
	@At
	@Ok("httl:erp.basic.customer_input")
	public Customer editUi(@Param("..") Customer obj) {
		return dao().fetchLinks(dao().fetch(obj), null);
	}

	/**
	 * 跳转到查看页面
	 */
	@At
	@Ok("httl:erp.basic.customer_view")
	public Customer view(@Param("..") Customer obj) {
		return dao().fetch(obj);
	}

	/**
	 * 跳转到高级查询页面
	 */
	@At
	@Ok("httl:erp.basic.customer_query")
	public void queryUi() {
	}

	/**
	 * 分页查询
	 * 
	 * @param pageNum
	 *            第几页
	 * @param numPerPage
	 *            每页显示多少条
	 * @return
	 */
	@At
	@Ok("httl:erp.basic.customer_list")
	public Map<String, Object> list(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage, @Param("..") Customer obj) {

		return pagerlist(pageNum, numPerPage, obj);
	}

	@At
	@Ok("httl:erp.basic.customer_list_oneLookup")
	public Map<String, Object> listoneLookup(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage, @Param("..") Customer obj) {

		return pagerlist(pageNum, numPerPage, obj);
	}

	
	
	public Map<String, Object> pagerlist(int pageNum, int numPerPage,
			Customer obj) {

		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = dao().createPager((pageNum < 1) ? 1 : pageNum,
				(numPerPage < 1) ? 20 : numPerPage);
		List<Customer> uiList = dao().fetchLinks(
				query(bulidQureyCnd(obj), pager), null);
		if (pager != null) {
			pager.setRecordCount(dao()
					.count(Customer.class, bulidQureyCnd(obj)));
			map.put("pager", pager);
		}
		map.put("o", obj);
		map.put("pagerlist", uiList);
		return map;
	}

	
	
	/**
	 * 新增-
	 * 
	 * @return
	 */
	@At
	public Object add(@Param("..") Customer obj) {
		try {
			// 设置id

			dao().insert(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK);
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}

	/**
	 * 删除-
	 * 
	 * @return
	 */
	@At
	public Object delete(@Param("..") Customer obj) {
		try {
			dao().delete(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK);
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}

	/**
	 * 根据ids删除数据信息
	 * 
	 * @param ids
	 * @param ioc
	 * @return
	 */
	@At
	public Object delByIds(@Param("ids") String ids) {
		try {
			Sql sql = Sqls.create("delete from ERP_Customer where id in(" + ids
					+ ")");
			dao().execute(sql);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK);
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}

	/**
	 * 更新-产品设置
	 * 
	 * @return
	 */
	@At
	public Object update(@Param("..") Customer obj) {
		try {
			dao().update(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK);
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}

	/**
	 * 构建查询条件
	 * 
	 * @param obj
	 * @return
	 */
	private Cnd bulidQureyCnd(Customer obj) {
		Cnd cnd = null;
		if (obj != null) {
			cnd = Cnd.where("1", "=", 1);
			if (!Strings.isEmpty(obj.getName()))
				cnd.and("name", "=", obj.getName());

		}
		return cnd;
	}
}
