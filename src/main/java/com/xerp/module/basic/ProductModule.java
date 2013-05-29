package com.xerp.module.basic;

import java.util.Map;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.service.IdEntityService;

import com.nutzside.common.util.DateUtil;
import com.nutzside.common.util.AjaxUtil;
import com.nutzside.common.util.WebUtil;
import com.nutzside.system.domain.Organization;
import com.xerp.domain.basic.ProdCategory;
import com.xerp.domain.basic.Product;
import com.xerp.service.basic.ProductService;

/**
* 产品设置 Module<br>
* 表名：ERP_PRODUCT<br>
* @author 
* @date 2011-11-22
* @version 1.0
*/
@At("/erp/basic/product")
@IocBean(fields={"dao"})
public class ProductModule  extends IdEntityService<Product>{

    private static final Log log = Logs.get();
    @Inject
    ProductService productService;
    /**
     * 跳转到添加页面-产品设置
     */
    @At
	@Ok("httl:erp.basic.product_input")
    public Product addUi(){  
    	
    	return new Product();
    }
    /**
     * 跳转到修改页面-产品设置
     */
    @At
    @Ok("httl:erp.basic.product_input")
    public Product editUi(@Param("..") Product obj){
    	return dao().fetchLinks(dao().fetch(obj),null);
    }
    /**
     * 跳转到查看页面-产品设置
     */
    @At
	@Ok("httl:erp.basic.product_view")
    public Product view(@Param("..") Product obj){
    	return dao().fetch(obj);
    }
    /**
     * 跳转到高级查询页面-产品设置
     */
    @At
	@Ok("httl:erp.basic.product_query")
    public void queryUi(){    	
    }
	/**
	 * 分页查询-产品设置
	 * @param pageNum 第几页
	 * @param numPerPage  每页显示多少条
	 * @return
	 */
	@At
	@Ok("httl:erp.basic.product_list")
	public Map<String, Object> list(@Param("pageNum") int pageNum ,@Param("numPerPage") int numPerPage,@Param("..") Product obj){
		return productService.Pagerlist(pageNum, numPerPage, obj);
	}
	@At
	@Ok("httl:erp.basic.product_list_oneLookup")
	public Map<String, Object> list_oneLookup(@Param("pageNum") int pageNum ,@Param("numPerPage") int numPerPage,@Param("..") Product obj){
		return productService.Pagerlist(pageNum, numPerPage, obj);
	}
	/**
	 * 新增-产品设置
	 * @return
	 */
	@At
	public Object add(@Param("::product.") Product obj,@Param("::org.") Organization Org,@Param("::district.") ProdCategory sort ){
		try{
			
			//设置创建人
			obj.setCreateUser(WebUtil.getLoginUser());
			//设置创建时间
			obj.setCreateDate(DateUtil.getCurrentDate());
			//设置修改人
			obj.setModifyUser(WebUtil.getLoginUser());
			//设置修改时间
			obj.setModifyDate(DateUtil.getCurrentDate());
			obj.setOrgid(Org.getId());
			obj.setSortid(sort.parentId);
			dao().insert(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK);
		}catch (Throwable e) {
			if (log.isDebugEnabled())
				log.debug("E!!",e);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}
	/**
	 * 删除-产品设置
	 * @return
	 */
	@At
	public Object delete(@Param("..") Product obj){
		try{
			dao().delete(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK);
		}catch (Throwable e) {
			if (log.isDebugEnabled())
				log.debug("E!!",e);
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
		try{		
			Sql sql = Sqls.create("delete from HR_PRODUCT where id in("+ids+")");
			dao().execute(sql);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK);
		}catch (Throwable e) {
			if (log.isDebugEnabled())
				log.debug("E!!",e);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}
	/**
	 * 更新-产品设置
	 * @return
	 */
	@At
	public Object update(@Param("..") Product obj){
		try{
			Product pro=dao().fetch(obj);
			//设置创建人
			obj.setCreateUser(pro.getCreateUser());
			//设置创建时间
			obj.setCreateDate(pro.getCreateDate());
			//设置修改人
			obj.setModifyUser(pro.getModifyUser());
			//设置修改时间
			obj.setModifyDate(pro.getModifyDate());
			dao().update(obj);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.OK);
		}catch (Throwable e) {
			if (log.isDebugEnabled())
				log.debug("E!!",e);
			return AjaxUtil.dialogAjaxDone(AjaxUtil.FAIL);
		}
	}
	
}