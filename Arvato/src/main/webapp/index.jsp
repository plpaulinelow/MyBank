<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyBank > Login</title>
</head>
<body>
	<form method="post" action="loginPage">
		<p>Username</p>
		<input type="text" name="username" placeholder="Enter Username"></input>
		<p>Password</p>
		<input type="password" name="password" placeholder="Enter Password"></input>
		<input type="submit" value="Sign in">
	</form>
</body>
<script>
    history.forward();
</script>
</html>
