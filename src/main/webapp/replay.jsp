<%@ page import="domain.ChessGame" %>
<%@ page import="persist.ChessGameDM" %>
<%@ page import="java.util.Optional" %>
<%@ page import="domain.ChessPlayer" %>
<%@ page import="domain.ChessMove" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: lucks
  Date: 1/7/23
  Time: 8:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.3.slim.min.js" integrity="sha256-ZwqZIVdD3iXNyGHbSYdsmWP//UBokj2FHAxKuSBKDSo=" crossorigin="anonymous"></script>

    <%@ include file="utils/auth.jsp" %>

    <%
        // Load a game: GET with an ID
        ChessGameDM gameDM = ChessGameDM.getInstance();
        String id = request.getParameter("id");
        int gameId = Integer.parseInt(id);
        ChessGame game = (ChessGame) session.getAttribute("reviewGame");

        Optional<ChessGame> optionalStoredGame = gameDM.find(id != null ? gameId : game.getId());
        if (optionalStoredGame.isEmpty()) {
            response.sendRedirect("games.jsp");
            return;
        }

        ChessGame actualGame = optionalStoredGame.get();
        List<ChessMove> moves = actualGame.getMoves();

        if (game == null || !game.equals(actualGame)) {
            // Loading a new game
            game = actualGame;
            game.loadBoard();
            game.replayGame();

            session.setAttribute("reviewGame", game);
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

        boolean canRewind = game.getMoves().size() > 0;
        boolean canForward = game.getMoves().size() < actualGame.getMoves().size();

        if (request.getMethod().equals("POST")) {
            // Rewind or forward the game
            String action = request.getParameter("action");
            if (action != null) {
                if (canRewind && action.equals("start")) {
                    game.getMoves().clear();
                    game.loadBoard();
                } else if (canRewind && action.equals("rewind")) {
                    game.undoMove();
                } else if (canForward && action.equals("forward")) {
                    // Get the next move from the actual game
                    ChessMove nextMove = actualGame.getMoves().get(game.getMoves().size());
                    game.addMove(nextMove);
                } else if (canForward && action.equals("end")) {
                    game.getMoves().clear();
                    game.getMoves().addAll(actualGame.getMoves());
                    game.loadBoard();
                }
            }

            // Update rewind and forward values in case a move was made
            canRewind = game.getMoves().size() > 0;
            canForward = game.getMoves().size() < actualGame.getMoves().size();
        }

    %>
    <title>Review game vs <%= adversary.getName() %></title>
</head>
<body>
    <%@ include file="components/header.jsp" %>

    <%@ include file="components/visualization.jsp" %>

    <h2>Review game vs <%= adversary.getName() %></h2>
    <%@ include file="components/board.jsp" %>

    <h3>Actions</h3>
    <form method="post">
        <input type="submit" name="action" value="start" <%= canRewind ? "" : "disabled" %> />
        <input type="submit" name="action" value="rewind" <%= canRewind ? "" : "disabled" %> />
        <input type="submit" name="action" value="forward" <%= canForward ? "" : "disabled" %> />
        <input type="submit" name="action" value="end" <%= canForward ? "" : "disabled" %> />
    </form>

    <%@ include file="components/footer.jsp" %>
</body>
</html>
