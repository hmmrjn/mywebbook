<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>借出一覧</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<h1>借出中一覧</h1>
	<table border="1">
		<tr>
			<th>会員</th>
			<th>資料ID</th>
			<th>借出日時</th>
			<th>返却期日</th>
			<th>返却日</th>
		</tr>
		<c:forEach items="${rentalsNotReturned}" var="rental">
			<tr>
				<td>${rental.member.familyName}${rental.member.givenName}</td>
				<td>${rental.bookCopy.id}</td>
				<td>${rental.rentedAt}</td>
				<td>${rental.returnBy}</td>
				<td>${rental.returnedAt}</td>
			</tr>
		</c:forEach>
	</table>
	<h1>借出履歴一覧</h1>
	<table border="1">
		<tr>
			<th>会員</th>
			<th>資料ID</th>
			<th>借出日時</th>
			<th>返却期日</th>
			<th>返却日</th>
		</tr>
		<c:forEach items="${rentalsReturned}" var="rental">
			<tr>
				<td>${rental.member.familyName}${rental.member.givenName}</td>
				<td>${rental.bookCopy.id}</td>
				<td>${rental.rentedAt}</td>
				<td>${rental.returnBy}</td>
				<td>${rental.returnedAt}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>