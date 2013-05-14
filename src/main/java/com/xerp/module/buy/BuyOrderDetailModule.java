package com.xerp.module.buy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.service.IdEntityService;

import com.nutzside.common.util.AjaxUtil;
import com.nutzside.common.util.UUIDUtil;
import com.nutzside.system.service.UserService;
import com.xerp.domain.buy.BuyOrder;
import com.xerp.domain.buy.BuyOrderDetail;
import com.xerp.service.buy.BuyOrderService;
@At("/erp/buy/buyorderdetail")
@IocBean(fields = { "dao" })
public class BuyOrderDetailModule extends IdEntityService<BuyOrderDetail>{
	
	@Inject
	BuyOrderService buyOrderService;
	@Inject
	UserService userService;
	
	/**
	 * 跳转到添加页面
	 */
	@At
	@Ok("httl:erp.buy.buyorderdetail_input")
	public void addUi() {
	}

	@At
	@Ok("httl:erp.buy.buyorderdetail_add")
	public void addproUi() {
	}
	/**
	 * 跳转到修改页面
	 */
	@At
	@Ok("httl:erp.buy.buyorderdetail_view")
	public BuyOrderDetail editUi(@Param("..") BuyOrderDetail obj) {
		return dao().fetchLinks(dao().fetch(obj), null);
	}

	/**
	 * 跳转到查看页面
	 */
	@At
	@Ok("httl:erp.buy.buyorderdetail_view")
	public BuyOrderDetail view(@Param("..") BuyOrderDetail obj) {
		return dao().fetchLinks(dao().fetch(obj), null);
	}

	/**
	 * 跳转到高级查询页面
	 */
	@At
	@Ok("httl:erp.buy.buyorderdetail_query")
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
	@Ok("httl:erp.buy.buyorderdetail_list")
	public Map<String, Object> list(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage,
			@Param("..") BuyOrderDetail obj) {

		return pagerlist(pageNum, numPerPage, obj);
	}

	@At
	@Ok("httl:erp.buy.buyorderdetail_list_oneLookup")
	public Map<String, Object> listoneLookup(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage,
			@Param("..") BuyOrderDetail obj) {

		return pagerlist(pageNum, numPerPage, obj);
	}

	public Map<String, Object> pagerlist(int pageNum, int numPerPage,
			BuyOrderDetail obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = dao().createPager((pageNum < 1) ? 1 : pageNum,
				(numPerPage < 1) ? 20 : numPerPage);
		List<BuyOrderDetail> uiList = dao().fetchLinks(
				query(bulidQureyCnd(obj), pager), null);
		if (pager != null) {
			pager.setRecordCount(dao().count(BuyOrderDetail.class,
					bulidQureyCnd(obj)));
			map.put("pager", pager);
		}
		map.put("o", obj);
		map.put("pagerlist", uiList);
		return map;
	}



	
	@At
	public Object add(@Param("::items") List<BuyOrderDetail> obj,
			@Param("..") BuyOrder ap) {

		try {
			ap.setOrderCode(UUIDUtil.generateSequenceNo());
           
			for (BuyOrderDetail apd : obj) {

				apd.setOrderid(ap.getId());
				if (apd.getPurchasePlanDetail()!= null) {
					apd.setPurchasePlanDetailid(apd.getPurchasePlanDetail().getId());
					apd.setProductid(apd.getPurchasePlanDetail().getProductid());
				} else if (apd.getProduct() != null) {

					apd.setProductid(apd.getProduct().getId());
					
				}
				apd.setSupplierid(apd.getSupplier().getId());
				dao().insert(apd);
			}
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK, "BuyOrderDetail");
		} catch (Throwable e) {

			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}
	/**
	 * 删除-产品设置
	 * 
	 * @return
	 */
	@At
	public Object delete(@Param("..") BuyOrderDetail obj) {
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
			Sql sql = Sqls.create("delete from ERP_BuyOrderDetail where id in("
					+ ids + ")");
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
	public Object update(@Param("..") BuyOrderDetail obj) {
		try {
			dao().update(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK, "BuyOrderDetail");
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
	private Cnd bulidQureyCnd(BuyOrderDetail obj) {
		Cnd cnd = null;
		if (obj != null) {
			cnd = Cnd.where("1", "=", 1);
			if (!Strings.isEmpty(obj.getName()))
				cnd.and("name", "=", obj.getName());

		}
		return cnd;
	}
}
