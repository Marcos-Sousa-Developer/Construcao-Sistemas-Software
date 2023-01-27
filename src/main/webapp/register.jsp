<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*" import="java.io.*" %>
<%@ page import="domain.ChessPlayer" %>
<%@ page import="persist.ChessPlayerDM" %>
<%@ page import="java.util.regex.Pattern" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Register</title>

	<%!
		public boolean isNameValid(String name) {
			return name.matches("^[a-zA-Z]+$");
		}

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
			String name = request.getParameter("name");
			String email = request.getParameter("email");

			if (name == null || !isNameValid(name) || email == null || !isEmailValid(email)) {
				response.sendRedirect("register.jsp");
				return;
			}

			ChessPlayerDM playerDM = ChessPlayerDM.getInstance();
			Optional<ChessPlayer> existingPlayer = playerDM.findByEmail(email);

			if (existingPlayer.isPresent()) {
				// Redirect to player already exists page
				response.sendRedirect("userAlreadyRegistered.jsp?email=" + email);
			} else {
				// Register player
				ChessPlayer player = new ChessPlayer(name, email);
				playerDM.insert(player);

				// Redirect to player registered page
				response.sendRedirect("userRegistered.jsp?email=" + email);
			}
		}
	%>
</head>
<body>
<%@ include file="components/header.jsp" %>

	<div>
		<h2>ChessBook Register</h2>
		<form action="register" id="registerForm" method="post" onsubmit="return continueOrNot()">
			<label for="inputName">Name: </label>
			<input type="text" name="name" id="inputName"> <br>
			<label for="inputEmail">Email: </label> 
			<input type="text" name="email" id="inputEmail"> <br>
		</form> <br>
		
		<button type="submit" form="registerForm">Submit</button> <br> <br>
	</div>

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