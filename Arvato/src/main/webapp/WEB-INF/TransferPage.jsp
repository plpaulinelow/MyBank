<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyBank > Transfer</title>
</head>
<body>
	<form method="post" action="transferPage">
		<input type="hidden" name="accountId" value = "${accountId}"/>
		<input type="hidden" name="constantsId" value="${constantsId}" />
		<input type="hidden" name="username" value="${username}" />
		
		<p>Account Id</p>
		<input type="number" step="0" value="" min="0" name="transferAccountId" placeholder="Enter Account Id"></input>
		<p>Transfer Amount (RM) :</p>
		<input type="number" step="0.01" value="0.00" placeholder="0.00" min="0" name="transferAmount"></input>
		<input type="submit" value="Confirm">
	</form>
</body>
</html>