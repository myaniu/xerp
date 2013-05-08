package com.xerp.module.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.service.IdEntityService;

import com.nutzside.common.util.DwzUtil;
import com.xerp.domain.store.StockPile;

@At("/erp/store/stockpile")
@IocBean(fields = { "dao" })
public class StockPileModule extends IdEntityService<StockPile> {
	/**
	 * 跳转到添加页面
	 */
	@At
	@Ok("jsp:erp.store.stockpile_input")
	public void addUi() {
	}

	/**
	 * 跳转到修改页面
	 */
	@At
	@Ok("jsp:erp.store.stockpile_input")
	public StockPile editUi(@Param("..") StockPile obj) {
		return dao().fetchLinks(dao().fetch(obj), null);
	}

	/**
	 * 跳转到查看页面
	 */
	@At
	@Ok("jsp:erp.store.stockpile_view")
	public StockPile view(@Param("..") StockPile obj) {
		return dao().fetch(obj);
	}

	/**
	 * 跳转到高级查询页面
	 */
	@At
	@Ok("httl:erp.store.stockpile_query")
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
	@Ok("httl:erp.store.stockpile_list")
	public Map<String, Object> list(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage, @Param("..") StockPile obj) {

		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = dao().createPager((pageNum < 1) ? 1 : pageNum,
				(numPerPage < 1) ? 20 : numPerPage);
		List<StockPile> uiList = dao().fetchLinks(
				query(bulidQureyCnd(obj), pager), null);
		if (pager != null) {
			pager.setRecordCount(dao().count(StockPile.class,
					bulidQureyCnd(obj)));
			map.put("pager", pager);
		}
		map.put("o", obj);
		map.put("pagerlist", uiList);
		return map;
	}

	/**
	 * 新增-产品设置
	 * 
	 * @return
	 */
	@At
	public Object add(@Param("..") StockPile obj) {
		try {
			// 设置id

			dao().insert(obj);
			return DwzUtil.dialogAjaxDone(DwzUtil.OK, "StockPile");
		} catch (Throwable e) {

			return DwzUtil.dialogAjaxDone(DwzUtil.FAIL);
		}
	}

	/**
	 * 删除-产品设置
	 * 
	 * @return
	 */
	@At
	public Object delete(@Param("..") StockPile obj) {
		try {
			dao().delete(obj);
			return DwzUtil.dialogAjaxDone(DwzUtil.OK);
		} catch (Throwable e) {

			return DwzUtil.dialogAjaxDone(DwzUtil.FAIL);
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
			Sql sql = Sqls.create("delete from ERP_StockPile where id in("
					+ ids + ")");
			dao().execute(sql);
			return DwzUtil.dialogAjaxDone(DwzUtil.OK);
		} catch (Throwable e) {

			return DwzUtil.dialogAjaxDone(DwzUtil.FAIL);
		}
	}

	/**
	 * 更新-产品设置
	 * 
	 * @return
	 */
	@At
	public Object update(@Param("..") StockPile obj) {
		try {
			dao().update(obj);
			return DwzUtil.dialogAjaxDone(DwzUtil.OK, "stockpile");
		} catch (Throwable e) {

			return DwzUtil.dialogAjaxDone(DwzUtil.FAIL);
		}
	}

	/**
	 * 构建查询条件
	 * 
	 * @param obj
	 * @return
	 */
	private Cnd bulidQureyCnd(StockPile obj) {
		Cnd cnd = null;
		if (obj != null) {
			cnd = Cnd.where("1", "=", 1);
			

		}
		return cnd;
	}
}
