<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="persist.ChessPlayerDM" %>
<%@ page import="domain.ChessPlayer" %>
<%@ page import="java.util.Optional" %>





<%

    // Remove player from session
    session.removeAttribute("player");


    // Redirect to login page
    response.sendRedirect("login");
%>



