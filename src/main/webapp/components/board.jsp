<%-- REQUIRES visualization String, game ChessGame, moves List<ChessMove> --%>
<%--
  Created by IntelliJ IDEA.
  User: lucks
  Date: 1/7/23
  Time: 9:29 PM
  To change this template use File | Settings | File Templates.
--%>
<style>
  #gameWrapper {
    display: flex;
    flex-direction: row;
  }
</style>

<div id="gameWrapper">
  <div id="game">
    <h3>Board</h3>
    <% if (visualization.equals("text")) { %>
<pre><%= game.getBoard().toString() %></pre>
    <% } else { %>
      <%@ include file="playImage.jsp" %>
      <% if (visualization.equals("twix")) { %>
        <%@ include file="playImageTwix.jsp" %>
      <% } %>
    <% } %>
  </div>

  <div id="moves">
    <h3>Moves</h3>
    <% if (moves.isEmpty()) { %>
      <p>No moves to show.</p>
    <% } else { %>
      <!-- Make an ordered list of the moves. There should be two moves per line-->
      <ol>
        <% int i=0; %>
        <% for (ChessMove move : moves) { %>
          <% if (i % 2 == 0) { %>
            <li>
          <% } %>

          <%= move.toString() %>

          <% if (i % 2 == 1) { %>
            </li>
          <% } %>

          <% i++; %>
        <% } %>
      </ol>
    <% } %>
  </div>
</div>