<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page session="true" %>
<html>
<head>
<meta charset="UTF-8">
<title>MyBank > HomePage</title>
</head>
<body>
	<p>Welcome, ${username} </p>
	<p>Account number: ${accountId} </p>
	
<form action="homePage" method="post">
	<input type="hidden" name="accountId" value = "${accountId}"/>
	<input type="hidden" name="constantsId" value="${constantsId}" />
	<input type="hidden" name="username" value="${username}" />
	
    <input type="submit" name="checkBalance" value="Check Balance" />
    <input type="submit" name="deposit" value="Deposit" />
    <input type="submit" name="withdrawal" value="Withdrawal" />
    <input type="submit" name="transfer" value="Transfer" />
    <input type="submit" name="logout" value="Logout" />
</form>
	
</body>
<script>
    history.forward();
</script>
</html>