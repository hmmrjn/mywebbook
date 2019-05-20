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
				<td><input type="text" name="isbn" value="${book.isbn}"
					required pattern="\d{3}-\d-\d{2}-\d{6}-\d" /></td>
				<td>例: 978-4-00-310101-8。ハイフン(-)を入れて入力してください。</td>
			</tr>
			<tr>
				<td>書名</td>
				<td><input type="text" name="name" value="${book.name}"
					required /></td>
			</tr>
			<tr>
				<td>カテゴリー</td>
				<td><select name="categoryId" required>
						<c:forEach items="${categories}" var="category">
							<option value="${category.id}">${category.name}</option>
						</c:forEach>
				</select> <a href="/mywebbook/book/categories/new">新規</a></td>
			</tr>
			<tr>
				<td>著者</td>
				<td><input type="text" name="author" value="${book.author}"
					required></td>
			</tr>
			<tr>
				<td>出版元</td>
				<td><select name="publisherId" required>
						<c:forEach items="${publishers}" var="publisher">
							<option value="${publisher.id}">${publisher.name}</option>
						</c:forEach>
				</select> <a href="/mywebbook/book/publishers/new">新規</a></td>
			</tr>
			<tr>
				<td>発売日</td>
				<c:set var="dateName" value="releasedAt" />
				<td><%@ include file="/common/datepicker.jsp"%></td>
			</tr>
			<tr>
				<td>入荷数</td>
				<td><input type="number" name="copiesNum" value="1" required/></td>
			</tr>
		</table>
		<input type="submit" value="登録" />
	</form>
</body>
</html>