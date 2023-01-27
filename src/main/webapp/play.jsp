<%@ page import="persist.ChessGameDM" %>
<%@ page import="java.util.Optional" %>
<%@ page import="domain.*" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: lucks
  Date: 1/1/23
  Time: 9:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.3.slim.min.js" integrity="sha256-ZwqZIVdD3iXNyGHbSYdsmWP//UBokj2FHAxKuSBKDSo=" crossorigin="anonymous"></script>

    <%@ include file="utils/auth.jsp" %>

    <%!
        private ChessPosition getPosition(String rawPos) {
            String lowerCasePos = rawPos.toLowerCase();
            char col = lowerCasePos.charAt(0);
            char row = lowerCasePos.charAt(1);

            return new ChessPosition(row - '1', col - 'a');
        }
    %>
    <%
        ChessGameDM gameDM = ChessGameDM.getInstance();

        // We need to know the game id. Receive it from query string
        String gameIdRaw = request.getParameter("id");
        if (gameIdRaw == null) {
            response.sendRedirect("games.jsp");
            return;
        }

        int gameId = Integer.parseInt(gameIdRaw);
        Optional<ChessGame> optionalGame = gameDM.find(gameId);

        if (optionalGame.isEmpty()) {
            response.sendRedirect("games.jsp");
            return;
        }

        ChessGame game = optionalGame.get();
        game.loadBoard();

        // moves is used by components/board.jsp
        List<ChessMove> moves = game.getMoves();

        if(game.isOver()) {
            response.sendRedirect("gameOver.jsp?id=" + gameId);
            return;
        }

        ChessPlayer adversary = game.getBlack();
        if (adversary.equals(authPlayer)) {
            adversary = game.getWhite();
        } else {
            // Make sure one of the players is the authenticated player
            if (!game.getWhite().equals(authPlayer)) {
                response.sendRedirect("games.jsp");
                return;
            }
        }

        String err = null;
        // Make a move. Validate it on the server side.
        if (request.getMethod().equals("POST") && game.getPlayerFromColor(game.getCurrentColor()).equals(authPlayer) && request.getParameter("move") != null) {
            String rawMove = request.getParameter("move");
            if (rawMove.equals("resign")) {
                game.resign();
            } else {
                int spaceIndex = rawMove.indexOf(" ");

                try {
                    ChessPosition from = getPosition(rawMove.substring(0, spaceIndex));
                    if (!from.isValid(game.getBoard())) {
                        throw new IllegalArgumentException("Invalid origin position");
                    }

                    ChessPosition to = getPosition(rawMove.substring(spaceIndex + 1));
                    if (!to.isValid(game.getBoard())) {
                        throw new IllegalArgumentException("Invalid destination position");
                    }

                    ChessPiece piece = game.getBoard().get(from.getRow(), from.getCol());
                    ChessMove move = new ChessMove(piece, from, to);
                    game.addMove(move);

                    // Persist the move, i.e. the updated game
                    gameDM.update(game);

                    // Redirect to the same page to avoid resubmitting the form
                    response.sendRedirect("play.jsp?id=" + gameId);
                    return;
                } catch (Exception e) {
                    err = "Invalid move: " + e.getMessage();
                }
            }
        }
    %>

    <title>Game vs <%= adversary.getName() %> </title>
</head>
<body>
<%@ include file="components/header.jsp" %>

<%@ include file="components/visualization.jsp" %>

<h2><%= authPlayer.getName() %></h2>

<p><%= game.getWhite().getName() %> (white) vs <%= game.getBlack().getName()%> (black)</p>

<p>Game duration: <span id="duration"></span></p>

<%@ include file="components/board.jsp" %>

<script>
    const since = new Date(<%= game.getStartDate().getTime() %>);
    const $time = $("#duration");
    function updateTime() {
        const now = new Date();
        const diff = now.getTime() - since.getTime();
        const seconds = Math.floor(diff / 1000);
        const minutes = Math.floor(seconds / 60);
        const hours = Math.floor(minutes / 60);

        const secondsStr = seconds % 60;
        const minutesStr = minutes % 60;
        const hoursStr = hours % 60;

        // Display the time elapsed with leading zeros
        const formatTime = (time) => time.toString().padStart(2, "0");
        $time.text(formatTime(hoursStr) + ':' + formatTime(minutesStr) + ':' + formatTime(secondsStr));
    }

    $(document).ready(function() {
        // Update the current time every second
        updateTime();
        setInterval(updateTime, 1000);
    });
</script>

<%-- Request move or tell player to wait --%>
<% if (game.getPlayerFromColor(game.getCurrentColor()).equals(authPlayer)) { %>
    <form action="play.jsp" id="playForm" method="post">
        <input type="hidden" name="id" value="<%= gameId %>">
        <label for="move">Your move: </label>
        <input type="text" name="move" id="move">
        <input type="submit" value="Submit">
    </form>

    <%-- Print error if there's one --%>
    <% if (err != null) { %>
        <p style="color: red"><%= err %></p>
    <% } %>
<% } else { %>
    <p>It's <%= adversary.getName() %>'s turn.</p>
<% } %>

<%@ include file="components/footer.jsp" %>
</body>
</html>
