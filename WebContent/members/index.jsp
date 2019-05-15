<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員一覧</title>
</head>
<body>
	<h1>会員一覧</h1>
	<a href="/mywebbook/members/new">新規会員</a>
	<table>
		<tr>
			<th>ID</th>
			<th>氏名</th>
			<th>郵便番号</th>
			<th>住所</th>
			<th>電話番号</th>
			<th>メール</th>
			<th>誕生日</th>
			<th>入会日</th>
			<th>退会日</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${members}" var="member">
			<tr>
				<td>${member.id}</td>
				<td>${member.familyName}${" "}${member.givenName}</td>
				<td>${member.postalCode}</td>
				<td>${member.address}</td>
				<td>${member.tel}</td>
				<td>${member.email}</td>
				<td>${member.birthday}</td>
				<td>${member.subscribedAt}</td>
				<td>${member.unsubscribedAt}</td>
				<td><a href="/mywebbook/members/edit?id=${member.id}">編集</a></td>
				<td><a href="/mywebbook/members/delete?id=${member.id}">削除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>