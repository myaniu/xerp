package com.wms.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import com.wms.domain.Payment;
import com.wms.service.PaymentService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/wms/payment")
public class PaymentModule {
	
	@Inject
	private PaymentService paymentService;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") Payment paymentSearch) {
		return paymentService.getGridData(jqReq, isSearch, paymentSearch);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") Payment payment) {
		return paymentService.CUDPayment(oper, ids, payment);
	}
	
	@At
	@Ok("httl:wms.payment_manager")
	@RequiresPermissions("httl:read:payment_manager")
	public void payment_manager() {
	}
	
}