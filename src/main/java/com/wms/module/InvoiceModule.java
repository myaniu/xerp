package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.Invoice;
import com.wms.service.InvoiceService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/invoice")
public class InvoiceModule {
	
	@Inject
	private InvoiceService invoiceService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") Invoice invoiceSearch) {
		return invoiceService.getGridData(jqReq, isSearch, invoiceSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Invoice invoice) {
		return invoiceService.CUDInvoice(oper, ids, invoice);
	}
	
	@At
	@Ok("httl:wms.invoice_manager")
	@RequiresPermissions("httl:read:invoice_manager")
	public void invoice_manager() {
	}
	
}