<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="persist.ChessGameDM" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="java.util.Date" %>
<%@ page import="persist.ChessPlayerDM" %>
<%@ page import="java.util.Optional" %>

<%@ page import="domain.*" %>
<%@ page import="java.util.*" %>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Games</title>
</head>
<body>


<%@ include file="components/header.jsp" %>
<%@ include file="utils/auth.jsp" %>

<h3><%= authPlayer.getName()%></h3>

<%
	// create list of games from the player
	ChessGameDM gameDM = ChessGameDM.getInstance();
	List<ChessGame> games = gameDM.findByPlayerName(authPlayer.getName());

	// create set of games 
	games = new ArrayList<ChessGame>(new HashSet<ChessGame>(games));

	
	// print table of current games of the player
	// print games size
	out.print("<h2>Current Games</h2>");
	out.print("<p>Number of games: " + games.size() + "</p>");
	if (games.size() > 0) {
		out.print("<table>");
			out.print("<tr>");
				out.print("<th>-</th>");
				out.print("<th>White</th>");
				out.print("<th>Time</th>");
				out.print("<th>Black</th>");
				out.print("<th>Last move</th>");
				out.print("<th>--</th>");
				out.print("</tr>");
				for (ChessGame game : games) {
					out.print("<tr>");
					out.print("<td>" + game.getId() + "</td>");
		
					
					
					out.print("<td>" + game.getWhite() + "</td>");
					// print total time from white player + black player
					int tt = game.totalTime(Color.WHITE) + game.totalTime(Color.BLACK);
					String totalTime = String.format("%02d:%02d:%02d", tt/3600, (tt%3600)/60, tt%60);
					out.print("<td>" + totalTime + "</td>");
					out.print("<td>" + game.getBlack() + "</td>");
		
					// print color of the piece who made the last move
					// if game is over
					if (game.isOver()) {
						// if resigned color is white 
						if (game.getResignColor() == Color.WHITE) {
							out.print("<td>" + game.getWhite() + " resigned</td>");
						} else if (game.getResignColor() == Color.BLACK) {
							out.print("<td>" + game.getBlack() + " resigned</td>");
						}
						// if winner color is white
						else if (game.getWinnerColor() == Color.WHITE) {
							out.print("<td>" + game.getWhite() + " won</td>");
						} else if (game.getWinnerColor() == Color.BLACK) {
							out.print("<td>" + game.getBlack() + " won</td>");
						} else if (game.getOutcome() == GameOutcome.STALEMATE) { 
							// get color from piece who made the last move
							if (game.getLastMove().getPiece().getColor() == Color.WHITE) {
								out.print("<td>" + "Draw-> " + game.getWhite() + " cornered " + game.getBlack() + "</td>");
							} else {
								out.print("<td>" + "Draw-> " + game.getBlack() + " cornered " + game.getWhite() + "</td>");
							}
						}
							
						
						//print form replay
						out.print("<td>");
							out.print("<form action='replay.jsp' id='chooseGame' method='get'>");
							out.print("<input type='hidden' name='id' id='id' value='" + game.getId() + "'>");
							out.print("<input type='submit' value='Replay'>");
							out.print("</form>");
							out.print("</td>");
					} else {
						out.print("<td>" + game.getCurrentColor() + "</td>");
						// if it's player turn print play, else print Date of last move
						if (game.getPlayerFromColor(game.getCurrentColor()).equals(authPlayer)) {
							out.print("<td>");
							out.print("<form action='play.jsp' id='chooseGame' method='get'>");
							out.print("<input type='hidden' name='id' id='id' value='" + game.getId() + "'>");
							out.print("<input type='submit' value='Play'>");
							out.print("</form>");
							out.print("</td>");
						} else {
							
							// TODO: print the date of the last move using getLastDate() method
							if(game.getLastDate() != null) {
								out.print("<td>" + game.getLastDate() + "</td>");
							
							} else {
								out.print("<td>WAITING</td>");
							}
						}
					}
		
					out.print("</tr>");
				}
				out.print("</table>");
					
		
	} 
	else {
		out.print("<p>You don't have any games yet.</p>");
	}
	

%>

<div>
	
	<h2>Start a new game</h2>
	<p>Enter the name of your opponent:</p>
	<form action="players.jsp" id="newGameForm" method="get">
		<label for="inputName">Name: </label>
		<input type="text" name="name" id="inputName" required> 
		<input type="submit" value="Play">
	</form>
</div>


<%@ include file="components/footer.jsp" %>


</body>
</html>
