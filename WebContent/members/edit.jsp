<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					value="${member.postalCode}" required pattern="\d{3}-\d{4}" /></td>
				<td>(000-0000。ハイフン(-)を入れて入力ください。)</td>
			</tr>
			<tr>
				<td>住所</td>
				<td><input name="address" type="text" value="${member.address}"
					required /></td>
			</tr>
			<tr>
				<td>電話番号</td>
				<td><input name="tel" type="text" value="${member.tel}"
					required pattern="\d{4}-\d{2}-\d{4}|\d{3}-\d{4}-\d{4}" /></td>
				<td>(0000-00-0000、もしくは、000-0000-0000。ハイフン(-)を入れて入力ください。)</td>
			</tr>
			<tr>
				<td>メールアドレス</td>
				<td><input name="email" type="email" value="${member.email}"
					required /></td>
			</tr>
			<tr>
				<td>生年月日</td>
				<c:set var="dateName" value="birthday" />
				<c:set var="defaultDate" value="${member.birthday}" />
				<td><%@ include file="/common/datepicker.jsp"%></td>
			</tr>

		</table>
		<input type="hidden" name="id" value="${member.id}" /> <input
			type="submit" value="更新" />
	</form>
</body>
</html>