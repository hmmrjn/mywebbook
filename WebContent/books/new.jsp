<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規図書</title>
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<h1>新規図書</h1>
	<form action="/mywebbook/books/new" method="post">
		<table border="1">
			<tr>
				<td>ISBN</td>
				<td><input type="text" name="isbn" value="${book.isbn}" /></td>
			</tr>
			<tr>
				<td>書名</td>
				<td><input type="text" name="name" value="${book.name}" /></td>
			</tr>
			<tr>
				<td>カテゴリー</td>
				<td><select name="category">
						<c:forEach items="${categories}" var="category">
							<option value="${category}">${category}</option>
						</c:forEach>
				</select> <a href="/mywebbook/book/categories/new">新規</a></td>
			</tr>
			<tr>
				<td>著者</td>
				<td><input type="text" name="author" value="${book.author}"></td>
			</tr>
			<tr>
				<td>出版元</td>
				<td><select name="publisher">
						<c:forEach items="${publishers}" var="publisher">
							<option value="${publisher}">${publisher}</option>
						</c:forEach>
				</select> <a href="/mywebbook/book/publishers/new">新規</a></td>
			</tr>
			<tr>
				<td>入荷数</td>
				<td><input type="number" name="copiesNum" /></td>
			</tr>
		</table>
		<input type="submit" value="登録" />
	</form>
</body>
</html>