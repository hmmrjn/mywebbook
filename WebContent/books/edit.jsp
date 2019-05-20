<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>図書編集</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<h1>図書編集</h1>
	<form action="/mywebbook/books/edit" method="post">
		<table border="1">
			<tr>
				<td>書名</td>
				<td><input type="text" name="name" value="${book.name}" /></td>
			</tr>
			<tr>
				<td>カテゴリー</td>
				<td><select name="categoryId">
						<c:forEach items="${categories}" var="category">
							<option value="${category.id}">${category.name}</option>
						</c:forEach>
				</select> <a href="/mywebbook/book/categories/new">新規</a></td>
			</tr>
			<tr>
				<td>著者</td>
				<td><input type="text" name="author" value="${book.author}"></td>
			</tr>
			<tr>
				<td>出版元</td>
				<td><select name="publisherId">
						<c:forEach items="${publishers}" var="publisher">
							<option value="${publisher.id}">${publisher.name}</option>
						</c:forEach>
				</select> <a href="/mywebbook/book/publishers/new">新規</a></td>
			</tr>
			<tr>
				<td>発売日</td>
				<c:set var="dateName" value="releasedAt" />
				<c:set var="defaultDate" value="${book.releasedAt}" />
				<td><%@ include file="/common/datepicker.jsp"%></td>
			</tr>
		</table>
		<input type="hidden" name="isbn" value="${book.isbn}" /> <input
			type="submit" value="送信" />
	</form>
</body>
</html>