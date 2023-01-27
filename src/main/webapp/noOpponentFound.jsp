<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="persist.ChessPlayerDM" %>
<%@ page import="domain.ChessPlayer" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>No opponent found</title>
</head>
<body>
<%@ include file="components/header.jsp" %>
<%@ include file="utils/auth.jsp" %>

<h3><%= "Player: " + authPlayer.getName()%></h3>

<h2>No user with name <%= request.getParameter("name") %> were found.</h2>


<p><a href="games.jsp">Try Again</a></p>

<%@ include file="components/footer.jsp" %>
</body>
</html>