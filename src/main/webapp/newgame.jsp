<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="persist.ChessPlayerDM" %>
<%@ page import="domain.ChessPlayer" %>
<%@ page import="java.util.Optional" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>New Game</title>
</head>
<body>


<%@ include file="components/header.jsp" %>

<%
    ChessPlayer authPlayer = (ChessPlayer) session.getAttribute("player");
    String adversary = request.getParameter("name");

    String email = request.getParameter("email");
    ChessPlayerDM playerDM = ChessPlayerDM.getInstance();
    Optional<ChessPlayer> advPlayer = playerDM.findByEmail(email);

    if (advPlayer.isEmpty() || advPlayer.get().equals(authPlayer)) {
        
        // Redirect to user already exists page
        response.sendRedirect("noOpponentFound.jsp?name=" + adversary);
    } else {

        response.sendRedirect("players.jsp");
    }    
%>

<%@ include file="components/footer.jsp" %>



</body>
</html>