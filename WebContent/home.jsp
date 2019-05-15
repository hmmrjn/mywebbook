<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ホーム</title>
</head>
<body>
<h1>ホーム</h1>

<div class="message">${message}</div>

<h2>借出・返却</h2>
<form action="/mywebbook/judgeRentalOrReturn" method="post">

会員ID <input name="memberId" type="text" />
書籍ID <input name="bookCopyId" type="text" />

<input type="submit" value="送信"/>
</form>

<h2>会員管理</h2>
<a href="/mywebbook/members">会員一覧</a>
<h2>書籍管理</h2>
<a href="/mywebbook/books">書籍一覧</a>
</body>
</html>