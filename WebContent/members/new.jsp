<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date, java.text.DateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<h1>新規会員登録</h1>
	<form action="/mywebbook/members/new" method="post">
		<table>
			<tr>
				<td>姓</td>
				<td><input name="familyName" type="text" value="hoge" required /></td>
			</tr>
			<tr>
				<td>名</td>
				<td><input name="givenName" type="text" value="hoge" required /></td>
			</tr>
			<tr>
				<td>郵便番号</td>
				<td><input name="postalCode" type="text" value="hoge" required /></td>
			</tr>
			<tr>
				<td>住所</td>
				<td><input name="address" type="text" value="hoge" required /></td>
			</tr>
			<tr>
				<td>電話番号</td>
				<td><input name="tel" type="text" value="hoge" required /></td>
			</tr>
			<tr>
				<td>メールアドレス</td>
				<td><input name="email" type="email" value="hoge@hoge.com"
					required /></td>
			</tr>
			<tr>
				<td>生年月日</td>
				<jsp:useBean id="now" class="java.util.Date" />
				<fmt:formatDate var="currentYear" value="${now}" pattern="yyyy" />
				<td><select name="birthdayYear" required>
						<option value="">年</option>
						<c:forEach var="i" begin="0" end="150" step="1">
							<c:set var="y" value="${currentYear - i}" />
							<option value="${y}">${y}</option>
						</c:forEach>
				</select> <select name="birthdayMonth" required>
						<option value="">月</option>
						<c:forEach var="m" begin="1" end="12" step="1">
							<option value="${m}">${m}</option>
						</c:forEach>
				</select> <select name="birthdayDay" required>
						<option value="">日</option>
						<c:forEach var="d" begin="1" end="31" step="1">
							<option value="${d}">${d}</option>
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<input type="submit" value="登録" />
	</form>

</body>
</html>