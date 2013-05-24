<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach var="cur" items="${treeList}" varStatus="vs">
  <c:set var="index" value="${index + 1}" scope="request" /><!-- 每一次循环，index+1 -->
  <tr>
    <td align="center" nowrap="nowrap">${index}</td>
    <td align="center" nowrap="nowrap">${cur.name}</td>
  </tr>
  <c:if test="${cur.children}"><!-- 如果有childen -->
    <c:set var="level" value="${level + 1}" scope="request" /><!-- 循环一次子列表，level+1 -->
    <c:set var="treeList" value="${cur.children}" scope="request" /><!-- 注意此处，子列表覆盖treeList，在request作用域 -->
    <c:import url="menu_role.jsp" /><!-- 这就是递归了 -->
  </c:if>
</c:forEach>
<c:set var="level" value="${level - 1}" scope="request" /><!-- 退出时，level-1 -->