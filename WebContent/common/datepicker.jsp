<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date, java.text.DateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--
必要なパラメータ :
1. dateName (任意): ex. birthday, releasedAt <select name="[ここ]Year|Month|Day" >
2. defaultDate (任意): 初期値
 --%>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="currentYear" value="${now}" pattern="yyyy" />
<fmt:formatDate var="defaultYear" value="${defaultDate}" pattern="yyyy" />
<fmt:formatDate var="defaultMonth" value="${defaultDate}" pattern="M" />
<fmt:formatDate var="defaultDay" value="${defaultDate}" pattern="d" />

<select name="${dateName}Year" required>
	<option value="">年</option>
	<c:forEach var="i" begin="0" end="150" step="1">
		<c:set var="y" value="${currentYear - i}" />
		<c:choose>
			<c:when test="${y == defaultYear}">
				<option value="${y}" selected>${y}</option>
			</c:when>
			<c:when test="${y != defaultYear}">
				<option value="${y}">${y}</option>
			</c:when>
		</c:choose>
	</c:forEach>
</select>
<select name="${dateName}Month" required>
	<option value="">月</option>
	<c:forEach var="m" begin="1" end="12" step="1">
		<c:choose>
			<c:when test="${m == defaultMonth}">
				<option value="${m}" selected>${m}</option>
			</c:when>
			<c:when test="${m != defaultMonth}">
				<option value="${m}">${m}</option>
			</c:when>
		</c:choose>
	</c:forEach>
</select>
<select name="${dateName}Day" required>
	<option value="">日</option>
	<c:forEach var="d" begin="1" end="31" step="1">
		<c:choose>
			<c:when test="${d == defaultDay}">
				<option value="${d}" selected>${d}</option>
			</c:when>
			<c:when test="${d != defaultDay}">
				<option value="${d}">${d}</option>
			</c:when>
		</c:choose>
	</c:forEach>
</select>