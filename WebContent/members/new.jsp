<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<td><%@ include file="/common/datepicker.jsp" %></td>
			</tr>
		</table>
		<input type="submit" value="登録" />
	</form>

</body>
</html>