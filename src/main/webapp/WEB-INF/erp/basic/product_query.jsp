<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="page">
	<div class="pageContent">
		<form method="post" action="${base}/erp/basic/product/list" class="pageForm" onsubmit="return navTabSearch(this, 'product');">
			<div class="pageFormContent" layoutH="58">
				<div class="divider">divider</div>
		        <div>
				<label>编号：</label>
				<input type="text" name="code" value="${obj.o.code}" size="25" />
				<span class="inputInfo">编号</span>
				</div>
		        <div>
				<label>名称：</label>
				<input type="text" name="name" value="${obj.o.name}" size="25" />
				<span class="inputInfo">名称</span>
				</div>
		        <div>
				<label>规格：</label>
				<input type="text" name="specification" value="${obj.o.specification}" size="25" />
				<span class="inputInfo">规格</span>
				</div>
		        <div>
				<label>单位：</label>
				<input type="text" name="unit" value="${obj.o.unit}" size="25" />
				<span class="inputInfo">单位</span>
				</div>
		        <div>
			</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">开始检索</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="reset">清空重输</button></div></div></li>
				</ul>
			</div>
		</form>
	</div>
</div>