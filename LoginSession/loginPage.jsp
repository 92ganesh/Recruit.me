<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	</head>
	
	<body>
		<!-- login form -->
		<form action="loginServlet" method="post" >
			<label>
				Username:
			</label>
			<center>
				<input type="text" name="uname">
			</center>
			
			<label>
				OTP:
			</label>
			<center>
				<input type="password" name="pwd">
			</center>
			
			<input type="submit" value="Verify" name="otpVerify">	
		</form>
	</body>

</html>