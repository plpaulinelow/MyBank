<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyBank > Transfer </title>
</head>
<body>
	<form method="post" action="transferPage">
		<input type="hidden" name="constantsId" value="${constantsId}" />
		<input type="hidden" name="username" value="${username}" />
		<input type="hidden" name="accountId" value="${accountId}" />
	
		<p>Third Party Account Id</p>
		<input type="number" step="0" value="" placeholder="Enter Third Party Account Id" min="0" name="transferAccountId"></input>
		<p>Amount</p>
		<input type="number" step="0.01" value="0.00" placeholder="0.00" min="0" name="transferAmount"></input>
		<input type="submit" name="transfer" value="Confirm">
		<input type="submit" name="homePage" value="Back to Home Page" />
	</form>
</body>
<script>
    history.forward();
</script>
</html>