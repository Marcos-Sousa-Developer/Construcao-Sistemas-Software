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
    <title>User Not Found</title>
</head>
<body>
<%@ include file="components/header.jsp" %>

<h2>Login Failed !</h2>

<h3>The user <%= request.getParameter("email") %> was not found.</h3>

<p>You may:</p>
<p><a href="login">Try Again</a> or <a href="register">Register</a></p>

<%@ include file="components/footer.jsp" %>
</body>
</html>
