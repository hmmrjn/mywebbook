<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date, java.text.DateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員情報編集</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<h1>会員情報編集</h1>
	<form action="/mywebbook/members/edit" method="post">
		<table>
			<tr>
				<td>姓</td>
				<td><input name="familyName" type="text"
					value="${member.familyName}" required /></td>
			</tr>
			<tr>
				<td>名</td>
				<td><input name="givenName" type="text"
					value="${member.givenName}" required /></td>
			</tr>
			<tr>
				<td>郵便番号</td>
				<td><input name="postalCode" type="text"
					value="${member.postalCode}" required /></td>
			</tr>
			<tr>
				<td>住所</td>
				<td><input name="address" type="text" value="${member.address}"
					required /></td>
			</tr>
			<tr>
				<td>電話番号</td>
				<td><input name="tel" type="text" value="${member.tel}"
					required /></td>
			</tr>
			<tr>
				<td>メールアドレス</td>
				<td><input name="email" type="email" value="${member.email}"
					required /></td>
			</tr>
			<tr>
				<td>生年月日</td>
				<jsp:useBean id="now" class="java.util.Date" />
				<fmt:formatDate var="currentYear" value="${now}" pattern="yyyy" />
				<fmt:formatDate var="memberBirthdayYear" value="${member.birthday}"
					pattern="yyyy" />
				<fmt:formatDate var="memberBirthdayMonth" value="${member.birthday}"
					pattern="M" />
				<fmt:formatDate var="memberBirthdayDay" value="${member.birthday}"
					pattern="d" />
				<td><select name="birthdayYear" required>
						<option value="">年</option>
						<c:forEach var="i" begin="0" end="150" step="1">
							<c:set var="y" value="${currentYear - i}" />
							<c:choose>
								<c:when test="${y == memberBirthdayYear}">
									<option value="${y}" selected>${y}</option>
								</c:when>
								<c:when test="${y != memberBirthdayYear}">
									<option value="${y}">${y}</option>
								</c:when>
							</c:choose>
						</c:forEach>
				</select> <select name="birthdayMonth" required>
						<option value="">月</option>
						<c:forEach var="m" begin="1" end="12" step="1">
							<c:choose>
								<c:when test="${m == memberBirthdayMonth}">
									<option value="${m}" selected>${m}</option>
								</c:when>
								<c:when test="${m != memberBirthdayMonth}">
									<option value="${m}">${m}</option>
								</c:when>
							</c:choose>
						</c:forEach>
				</select> <select name="birthdayDay" required>
						<option value="">日</option>
						<c:forEach var="d" begin="1" end="31" step="1">
							<c:choose>
								<c:when test="${d == memberBirthdayDay}">
									<option value="${d}" selected>${d}</option>
								</c:when>
								<c:when test="${d != memberBirthdayDay}">
									<option value="${d}">${d}</option>
								</c:when>
							</c:choose>
						</c:forEach>
				</select></td>
			</tr>

		</table>
		<input type="hidden" name="id" value="${member.id}" /> <input
			type="submit" value="更新" />
	</form>
</body>
</html>