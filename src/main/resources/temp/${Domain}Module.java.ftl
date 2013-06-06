package ${package}.module;

import com.nutzside.common.domain.ResponseData;
import com.nutzside.common.domain.jqgrid.JqgridReqData;
import ${domainPackage}.${Domain};
import ${package}.service.${Domain}Service;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

@IocBean
@At("/${requestPath}/${Domain?uncap_first}")
public class ${Domain}Module {
	
	@Inject
	private ${Domain}Service ${Domain?uncap_first}Service;

	@At
	public ResponseData getGridData(@Param("..") JqgridReqData jqReq, @Param("_search") Boolean isSearch, @Param("..") ${Domain} ${Domain?uncap_first}Search) {
		return ${Domain?uncap_first}Service.getGridData(jqReq, isSearch, ${Domain?uncap_first}Search);
	}

	@At
	public ResponseData editRow(@Param("oper") String oper, @Param("ids") String ids, @Param("..") ${Domain} ${Domain?uncap_first}) {
		return ${Domain?uncap_first}Service.CUD${Domain}(oper, ids, ${Domain?uncap_first});
	}
	
	@At
	@Ok("httl:${requestPath}.${Domain?lower_case}_manager")
	@RequiresPermissions("httl:read:${Domain?lower_case}_manager")
	public void ${Domain?lower_case}_manager() {
	}
	
}