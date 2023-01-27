<%@ page import="persist.ChessPlayerDM" %>
<%@ page import="domain.ChessPlayer" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>

	<%!
		public boolean isEmailValid(String email) {
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
								"[a-zA-Z0-9_+&*-]+)*@" +
								"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
								"A-Z]{2,7}$";

			Pattern pat = Pattern.compile(emailRegex);
			if (email == null)
				return false;
			return pat.matcher(email).matches();
		}
	%>
	<%
		if (request.getMethod().equals("POST")) {
			String email = request.getParameter("email");
			if (email == null || !isEmailValid(email)) {
				// We redirect so it's a GET request, preventing the user from resubmitting the form on refresh
				response.sendRedirect("login.jsp");
				return;
			}

			ChessPlayerDM playerDM = ChessPlayerDM.getInstance();
			Optional<ChessPlayer> player = playerDM.findByEmail(email);

			if (player.isEmpty()) {
				// Redirect to user already exists page
				response.sendRedirect("userNotFound.jsp?email=" + email);
			} else {
				session.setAttribute("player", player.get());

				// Redirect to games list page
				response.sendRedirect("games.jsp");
			}
		}
	%>
</head>
<body>
<%@ include file="components/header.jsp" %>

<div>
	<h2>ChessBook Login</h2>

	<form action="login" id="loginForm" method="post" onsubmit="return continueOrNot()">
		<label for="inputName">Name: </label>
		<input type="text" name="name" id="inputName"> <br>
		<label for="inputEmail">Email: </label>
		<input type="text" name="email" id="inputEmail" required> <br><br>
		<input type="submit" value="Submit">
	</form> 

	<p>or</p>

	<a href="register">Register</a>
</div>
<br>



<script>
function validateEmail(email) { 
    var re = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;
    return re.test(email);
 } 

 function validateName(name) { 
    var re = /^[a-zA-Z]+$/;
    return re.test(name);
 } 

 function continueOrNot() {
	 if (!validateName(document.getElementById('inputName').value)) {
		 alert("Name must contain only letters");
		 return false;
	 }

	 if (!validateEmail(document.getElementById('inputEmail').value)) {
		 alert("Email is not valid");
		 return false;
	 }

	 return true;
 }
</script>
<%@ include file="components/footer.jsp" %>
</body>
</html>
