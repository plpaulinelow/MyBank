<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyBank > Withdrawal</title>
</head>
<body>
	<form method="post" action="withdrawalPage">
		<input type="hidden" name="accountId" value = "${accountId}"/>
		<input type="hidden" name="constantsId" value="${constantsId}" />
		<input type="hidden" name="username" value="${username}" />
		
		<p>Withdrawal Amount (RM): </p>
		<input type="number" step="0.01" value="0.00" placeholder="0.00" min="0" name="withdrawalAmount"></input>
		<input type="submit" name=withdraw value="Confirm">
		<input type="submit" name="homePage" value="Back to Home Page" />
	</form>
</body>
<script>
    history.forward();
</script>
</html>