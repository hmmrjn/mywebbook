<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>図書詳細</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<h1>${book.name}</h1>
	<a href="/mywebbook/books/edit?isbn=${book.isbn}">編集</a>
	<table border="1">
		<tr>
			<td>ISBN</td>
			<td>${book.isbn}</td>
		</tr>
		<tr>
			<td>書名</td>
			<td>${book.name}</td>
		</tr>
		<tr>
			<td>カテゴリー</td>
			<td>${book.category.name}</td>
		</tr>
		<tr>
			<td>著者</td>
			<td>${book.author}</td>
		</tr>
		<tr>
			<td>出版元</td>
			<td>${book.publisher.name}</td>
		</tr>
		<tr>
			<td>発売日</td>
			<td>${book.releasedAt}</td>
		</tr>
	</table>

	<p>この図書は${fn:length(book.copies)}冊あります。</p>

	<table border="1">
		<tr>
			<th>No</th>
			<th>資料ID</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${book.copies}" var="copy" varStatus="s">
			<tr>
				<td>${s.count}</td>
				<td>${copy.id}</td>
				<td><a href="/mywebbook/bookcopies/discard?id=${copy.id}">破棄</a></td>
			</tr>
		</c:forEach>
	</table>
	<a href="books/copies/new">資料を追加</a>
</body>
</html>