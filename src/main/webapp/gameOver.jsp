<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="persist.*" %>
<%@ page import="domain.*" %>
<%@ page import="java.util.Optional" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Game Over</title>
</head>
<body> 
<%@ include file="utils/auth.jsp" %>

	<%
		String gameRawId = request.getParameter("id");
		if (gameRawId == null) {
			response.sendRedirect("games.jsp");
			return;
		}

		int gameId = Integer.parseInt(gameRawId);

		ChessGameDM gameDM = ChessGameDM.getInstance();
		Optional<ChessGame> optionalGame = gameDM.find(gameId);

		if (optionalGame.isEmpty()) {
			response.sendRedirect("games.jsp");
			return;
		}

		ChessGame game = optionalGame.get();
		if (!game.isOver()) {
			// Game didn't end so let the player play
			response.sendRedirect("game.jsp?id=" + gameId);
			return;
		}

		// Get adversary
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
	%>
</head>
<body>
	<%@ include file="components/header.jsp" %>

	<h2>Game with <%= adversary.getName() %> has ended</h2>

	<% if (game.getPlayerFromColor(game.getWinnerColor()).equals(authPlayer)) { %>
		<h3>CONGRATULATIONS YOU WON! :)</h3>
	<% } else { %>
		<h3>SORRY YOU LOST! :(</h3>
	<% } %>

	<p>You played <%= game.getColorFromPlayer(authPlayer).toString() %>, while <strong><%= adversary.getName() %></strong> played <%= game.getColorFromPlayer(adversary).toString() %>.</p>
	<p>There were <%= game.getMoves().size() %> moves.</p>

	<p>
		The game ended because
		<% if (game.getOutcome() == GameOutcome.CHECKMATE) { %>
			<%= game.getWinnerColor() %> delivered checkmate.
		<% } else if (game.getOutcome() == GameOutcome.STALEMATE) { %>
			<%= game.getWinnerColor() %> was stalemated.
		<% } else if (game.getOutcome() == GameOutcome.RESIGN) { %>
			<%= game.getWinnerColor().opposite() %> resigned.
		<% } else if (game.getOutcome() == GameOutcome.STALEMATE) { %>
				<% if (game.getLastMove().getPiece().getColor() == Color.WHITE) { %>
					Draw-> <%= game.getWhite() %> cornered <%= game.getBlack() %>
				<% } else { %>
					Draw-> <%= game.getBlack() %> cornered <%= game.getWhite() %>
				<% } %>
			<%} %>
	</p>

	<%@ include file="components/footer.jsp" %>
</body>
</html>