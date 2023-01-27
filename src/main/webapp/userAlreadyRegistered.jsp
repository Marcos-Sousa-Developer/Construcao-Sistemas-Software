<%--
  Created by IntelliJ IDEA.
  User: lucks
  Date: 1/1/23
  Time: 11:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Already Registered</title>
</head>
<body>
<%@ include file="components/header.jsp" %>

<h2>Registration Failed !</h2>

<h3>User with email <%= request.getParameter("email") %> is already registered.</h3>

<p><a href="register">Try Again</a> or <a href="login">Login</a></p>

<%@ include file="components/footer.jsp" %>
</body>
</html>
