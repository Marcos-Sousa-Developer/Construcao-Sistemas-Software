<%@ page import="domain.ChessPlayer" %><%--
  Created by IntelliJ IDEA.
  User: lucks
  Date: 1/1/23
  Time: 11:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%
    ChessPlayer authPlayer = (ChessPlayer) session.getAttribute("player");
    if (authPlayer == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>