<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ホーム</title>
</head>
<body>

	<%@ include file="/common/header.jsp"%>
	<h1>ホーム</h1>

	<h2>借出</h2>
	<form action="/mywebbook/rentals/new" method="post">

		資料ID <input name="bookCopyId" type="text" /> 会員ID <input
			name="memberId" type="text" /><input type="submit" value="借出" />
	</form>
	<h2>返却</h2>
	<form action="/mywebbook/rentals/set_returned" method="post">
		資料ID <input name="bookCopyId" type="text" /> <input type="submit"
			value="返却" />
	</form>

	<h2>会員管理</h2>
	<a href="/mywebbook/members">会員一覧</a>
	<h2>図書管理</h2>
	<a href="/mywebbook/books">図書一覧</a>
</body>
</html>