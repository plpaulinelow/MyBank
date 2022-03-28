<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyBank > Check Balance</title>
</head>
<body>
	<p>Balance (RM): ${balance}</p>
	<form action="checkBalancePage" method="post">
		<input type="hidden" name="constantsId" value="${constantsId}" />
		<input type="hidden" name="username" value="${username}" />
		<input type="hidden" name="accountId" value="${accountId}" />
		<input type="submit" name="homePage" value="Back to Home Page" />
	</form>
</body>
<script>
    history.forward();
</script>
</html>