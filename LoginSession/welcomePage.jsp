<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	</head>
	<body>
	
		<%
			//This will help for page to loose all the cache when user logout(When user logout and press back button, should not redirect to welcomePage)
			
			response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");// used if you have Http version is 1.1
			response.setHeader("pragma","no-cache");// used if you have Http version is 1.1
			response.setHeader("Expires","0");// used if you have proxy server
			
			
			//When any one or both the fields are not specified it will redirect user to loginPage
			if((session.getAttribute("username")==null)&&(session.getAttribute("OTP")==null))
			{
				response.sendRedirect("loginPage.jsp");
			}
			
		%>
		
		Welcome ${username}, to our Recruiting website
		<br>
		
		<!-- Setting form to redirect to loginPage when user clicks the logout button -->
		<form action="logout" method="post">
			<input type="submit" value="logout">
		
	</body>
</html>