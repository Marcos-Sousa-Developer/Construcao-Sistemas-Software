<%-- REQUIRES gameId int --%>
<%--
  Created by IntelliJ IDEA.
  User: lucks
  Date: 1/14/23
  Time: 8:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%!
    private Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }
%>
<%
    // Check if the game is over
    Cookie visualizationCookie = getCookie(request, "visualization");
    String visualization = visualizationCookie == null ? "text" : visualizationCookie.getValue();

    // Change the game mode
    if (request.getMethod().equals("POST") && request.getParameter("visualization") != null) {
        visualization = request.getParameter("visualization");
        response.addCookie(new Cookie("visualization", visualization));
    }
%>

<div id="visualization">
    <p>Choose game visualization:</p>
    <form method="post">
        <input type="hidden" name="id" value="<%= gameId %>">
        <input type="radio" id="text" name="visualization" value="text" <% if (visualization.equals("text")) { %> checked <% } %>>
        <label for="text">Text</label><br>
        <input type="radio" id="image" name="visualization" value="image" <% if (visualization.equals("image")) { %> checked <% } %>>
        <label for="image">Image</label><br>
        <input type="radio" id="twix" name="visualization" value="twix" <% if (visualization.equals("twix")) { %> checked <% } %>>
        <label for="twix">Twix & Friends</label><br>
        <input type="submit" value="Submit">
    </form>
</div>

<style>
    #visualization {
        position: absolute;
        top: 0;
        right: 0;
        width: 200px;
        background-color: #f1f1f1;
        padding: 20px;
    }
</style>