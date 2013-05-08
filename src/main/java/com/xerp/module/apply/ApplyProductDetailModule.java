package com.xerp.module.apply;

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
import com.xerp.domain.apply.ApplyProduct;
import com.xerp.domain.apply.ApplyProductDetail;
import com.xerp.service.apple.ApplyProductService;
@At("/erp/apply/applyproductdetail")
@IocBean(fields = { "dao" })
public class ApplyProductDetailModule extends IdEntityService<ApplyProductDetail> {
	
	@Inject
	ApplyProductService applyProductService;
	@Inject
	UserService userService;
	/**
	 * 跳转到添加页面
	 */
	@At
	@Ok("jsp:erp.apply.applyproductdetail_input")
	public void addUi() {
	}
	

	/**
	 * 跳转到修改页面
	 */
	@At
	@Ok("httl:erp.apply.applyproductdetail_view")
	public ApplyProductDetail editUi(@Param("..") ApplyProductDetail obj) {
		return dao().fetchLinks(dao().fetch(obj), null);
	}

	/**
	 * 跳转到查看页面
	 */
	@At
	@Ok("httl:erp.apply.applyproductdetail_view")
	public ApplyProductDetail view(@Param("..") ApplyProductDetail obj) {
		return dao().fetch(obj);
	}

	/**
	 * 跳转到高级查询页面
	 */
	@At
	@Ok("jsp:erp.apply.applyproductdetail_query")
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
	@Ok("httl:erp.apply.applyproductdetail_list")
	public Map<String, Object> list(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage, @Param("..") ApplyProductDetail obj) {

		return pagerlist(pageNum, numPerPage, obj);
	}
	
	@At
	@Ok("httl:erp.apply.applyproductdetail_list_oneLookup")
	public Map<String, Object> listoneLookup(@Param("pageNum") int pageNum,
			@Param("numPerPage") int numPerPage, @Param("..") ApplyProductDetail obj) {

		return pagerlist(pageNum, numPerPage, obj);
	}

	
	public Map<String, Object> pagerlist(int pageNum, int numPerPage,
			ApplyProductDetail obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Pager pager = dao().createPager((pageNum < 1) ? 1 : pageNum,
				(numPerPage < 1) ? 20 : numPerPage);
		List<ApplyProductDetail> uiList = dao().fetchLinks(
				query(bulidQureyCnd(obj), pager), null);
		if (pager != null) {
			pager.setRecordCount(dao().count(ApplyProductDetail.class,
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
	public Object add(@Param("::items") List<ApplyProductDetail> obj,@Param("..") ApplyProduct  ap) {
		
		try {
			ap.setCode(UUIDUtil.generateSequenceNo());
			ap.setApplyDate(DateUtil.getNowDate());
			ap.setUserId(userService.getuser().getId());
			applyProductService.dao().insert(ap);
			
		for (ApplyProductDetail apd:obj){
			
	      
			apd.setApplyid(ap.getId());
			apd.setProductid(apd.getProduct().getId());
			apd.setStatus(1);
			dao().insert(apd);
			}
		       return DwzUtil.dialogAjaxDone(DwzUtil.OK, "applyproductdetail");
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
	public Object delete(@Param("..") ApplyProductDetail obj) {
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
			Sql sql = Sqls.create("delete from ERP_ApplyProductDetail where id in("
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
	public Object update(@Param("..") ApplyProductDetail obj) {
		try {
			dao().update(obj);
			return DwzUtil.dialogAjaxDone(DwzUtil.OK, "applyproductdetail");
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
	private Cnd bulidQureyCnd(ApplyProductDetail obj) {
		Cnd cnd = null;
		if (obj != null) {
			cnd = Cnd.where("1", "=", 1);
			//System.out.println(obj.toString());
			 if(obj.getProduct()!=null)
					cnd.and("productid", "=", obj.getProduct().getId());
		}
		
		return cnd;
	}
}
