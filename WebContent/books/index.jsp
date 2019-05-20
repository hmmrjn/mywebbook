<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>図書一覧</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<h1>図書一覧</h1>
	<a href="/mywebbook/books/new">新規図書</a>
	<table border="1">
		<tr>
			<th>No</th>
			<th>ISBN</th>
			<th>書名</th>
			<th>カテゴリー</th>
			<th>著者</th>
			<th>出版元</th>
			<th>発売日</th>
			<th>資料数</th>
			<th colspan="2">操作</th>
		</tr>
		<c:forEach items="${books}" var="book" varStatus="s">
			<tr>
				<td>${s.count}</td>
				<td>${book.isbn}</td>
				<td>${book.name}</td>
				<td>${book.category.name}</td>
				<td>${book.author}</td>
				<td>${book.publisher.name}</td>
				<td>${book.releasedAt}</td>
				<td>${fn:length(book.copies)}</td>
				<td><a href="/mywebbook/books/show?isbn=${book.isbn}">詳細</a></td>
				<td><a href="/mywebbook/books/edit?isbn=${book.isbn}">編集</a></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>