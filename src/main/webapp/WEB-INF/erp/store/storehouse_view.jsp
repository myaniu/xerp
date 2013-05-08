<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h2 class="contentTitle">查看仓库页面</h2>
<div class="pageContent">
	
	<div class="viewInfo" layoutH="97">
		
		<dl>
			<dt>名称：</dt><dd>${obj.name}</dd>
		</dl>
		<dl>
			<dt>地址：</dt><dd>${obj.address}</dd>
		</dl>
		
		<dl>
			<dt>组织机构：</dt><dd>${obj.status eq 0 ? '未使用' : '在使用中'}</dd>
		</dl>
		

		<div class="divider"></div>
			</div>

	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button"><hi:text key="关闭"/></button></div></div></li>
		</ul>
	</div>
</div>
