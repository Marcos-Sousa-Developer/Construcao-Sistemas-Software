<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: lucks
  Date: 1/1/23
  Time: 11:31 AM
  To change this template use File | Settings | File Templates.
--%>

<p><%= new Date() %></p>

<p>
    <% if (session.getAttribute("player") != null) { %>
        <a href="logout.jsp">Logout</a> &bullet;
        <a href="games.jsp">My Games</a> &bullet;
    <% } %>
    <a href="manage.jsp">ManageDB</a>
</p>
