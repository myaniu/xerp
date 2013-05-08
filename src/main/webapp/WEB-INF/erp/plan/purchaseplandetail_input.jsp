<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<h2 class="contentTitle">申请单</h2>
<form action="${base}/erp/plan/purchaseplandetail/add" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
<div class="pageContent">
	<div class="pageFormContent" layoutH="97">
		
		<dl class="nowrap">
			<dt>申请类型：</dt>
			<dd>
				 <select name="type" class="required" >
                  <option value="1" >采购</option>
                  <option value="2" >调配</option>
                 </select>
			</dd>
		</dl>

		<dl class="nowrap">
			<dt>申请原因：</dt>
			<dd>
			  <textarea name="reason" cols="25" rows="4" ></textarea>
			</dd>
		</dl>
		
	
	
		
		<div class="divider"></div>
		<h3 class="contentTitle">填写申请产品</h3>
		<div class="tabs">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li class="selected"><a href="javascript:void(0)"><span>申请单明细</span></a></li>
					</ul>
				</div>
			</div>
			<div class="tabsContent" style="height: 260px;">
				
				<div>
					<table class="list nowrap itemDetail" addButton="新建" width="100%">
						<thead>
							<tr>
								<th type="number" name="items[#index#].Quantity"  fieldClass="required">商品数量</th>
								<th type="number" name="items[#index#].Price" >参考价格</th>
								<th type="number" name="items[#index#].applyProductDetail.productid"  readonly>产品内部编号</th>
                                <th type="lookup" id="items[#index#].applyProductDetail.id"  name="items[#index#].applyProductDetail.code" lookupGroup="items[#index#].applyProductDetail" lookupUrl="${base}/erp/apply/applyproductdetail/listoneLookup"   fieldClass="required" readonly>申请单编号</th>
                                <th type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
				
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
		
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>
</form>
