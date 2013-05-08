package com.xerp.module.plan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.service.IdEntityService;

import com.nutzside.common.util.DateUtil;
import com.nutzside.common.util.DwzUtil;
import com.nutzside.common.util.UUIDUtil;
import com.nutzside.system.service.UserService;
import com.xerp.domain.plan.PurchasePlan;
import com.xerp.domain.plan.PurchasePlanDetail;
import com.xerp.service.plan.PurchasePlanService;

@At("/erp/plan/purchaseplandetail")
@IocBean(fields = { "dao" })
public class PurchasePlanDetailModule extends
		IdEntityService<PurchasePlanDetail> {
	@Inject
	PurchasePlanService purchasePlanService;
	@Inject
	UserService userService;

	/*
	 * 跳转到添加页面
	 */
	@At
	@Ok("jsp:erp.plan.purchaseplandetail_input")
	public void addUi() {
	}

	@At
	@Ok("jsp:erp.plan.purchaseplandetail_add")
	public void addproUi() {
	}

	/**
	 * 跳转到修改页面
	 */
	@At
	@Ok("httl:erp.plan.purchaseplandetail_view")
	public PurchasePlanDetail editUi(@Param("..") PurchasePlanDetail obj) {
		return dao().fetchLinks(dao().fetch(obj), null);
	}

	/**
	 * 跳转到查看页面
	 */
	@At
	@Ok("httl:erp.plan.purchaseplandetail_view")
	public PurchasePlanDetail view(@Param("..") PurchasePlanDetail obj) {
		return dao().fetch(obj);
	}

	/**
	 * 跳转到高级查询页面
	 */
	@At
	@Ok("jsp:erp.plan.purchaseplandetail_query")
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
	@Ok("httl:erp.plan.purchaseplandetail_list")
	public Map<String, Object> list(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage,
			@Param("..") PurchasePlanDetail obj) {

		return pagerlist(pageNum, numPerPage, obj);
	}

	@At
	@Ok("httl:erp.plan.purchaseplandetail_list_oneLookup")
	public Map<String, Object> listoneLookup(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage,
			@Param("..") PurchasePlanDetail obj) {

		return pagerlist(pageNum, numPerPage, obj);
	}

	public Map<String, Object> pagerlist(int pageNum, int numPerPage,
			PurchasePlanDetail obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = dao().createPager((pageNum < 1) ? 1 : pageNum,
				(numPerPage < 1) ? 20 : numPerPage);
		List<PurchasePlanDetail> uiList = dao().fetchLinks(
				query(bulidQureyCnd(obj), pager), null);
		if (pager != null) {
			pager.setRecordCount(dao().count(PurchasePlanDetail.class,
					bulidQureyCnd(obj)));
			map.put("pager", pager);
		}
		map.put("o", obj);
		map.put("pagerlist", uiList);
		return map;
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	@At
	public Object add(@Param("::items") List<PurchasePlanDetail> obj,
			@Param("..") PurchasePlan ap) {

		try {
			ap.setCode(UUIDUtil.generateSequenceNo());
			ap.setPlanDate(DateUtil.getNowDate());
			ap.setUserId(userService.getuser().getId());
			purchasePlanService.dao().insert(ap);

			for (PurchasePlanDetail apd : obj) {

				apd.setPurchasePlanid(ap.getId());
				if (apd.getApplyProductDetail()!= null) {
					apd.setApplyProductDetailid(apd.getApplyProductDetail()
							.getId());
					apd.setProductid(apd.getApplyProductDetail().getProductid());
				} else if (apd.getProduct() != null) {

					apd.setProductid(apd.getProduct().getId());
				}
				apd.setStatus(1);
				dao().insert(apd);
			}
			return DwzUtil.dialogAjaxDone(DwzUtil.OK, "purchaseplandetail");
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
	public Object delete(@Param("..") PurchasePlanDetail obj) {
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
			Sql sql = Sqls
					.create("delete from ERP_PurchasePlanDetail where id in("
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
	public Object update(@Param("..") PurchasePlanDetail obj) {
		try {
			dao().update(obj);
			return DwzUtil.dialogAjaxDone(DwzUtil.OK, "purchaseplandetail");
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
	private Cnd bulidQureyCnd(PurchasePlanDetail obj) {
		Cnd cnd = null;
		if (obj != null) {
			cnd = Cnd.where("1", "=", 1);
		}
		return cnd;
	}
}
