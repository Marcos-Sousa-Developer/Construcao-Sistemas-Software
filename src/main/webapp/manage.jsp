<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="java.util.*"%> 
<%@ page import="persist.*" %>
<%@ page import="domain.*" %>
		
<% 
	ChessGameDM cgdm = ChessGameDM.getInstance();
	List<ChessGame> games = cgdm.chessGamesList();  
	
	ChessPlayerDM cpdm = ChessPlayerDM.getInstance();
	List<ChessPlayer> players = cpdm.chessPlayersList(); 
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage</title>
</head>
<body> 
	<h1>Manage Database</h1>
	<div>
		<h2>All ChessGames</h2>
				
		<% if(games.size() == 0) {%> 
			<div><strong>There are no games yet! </strong></div>
		<%} else {%>
		<table>
			<tr>
				<th>ID</th>
				<th>Game</th>
			</tr>
			<%for(ChessGame game : games) {%>  		
				<tr>
					<td><%= game.getId() %> </td>
					<td><%=game.getBlack().getName()%>
								vs 
						<%=game.getWhite().getName()%>				
					</td>			 
				</tr> 
			<%} %>
		
		<% }%>
		
		</table>
	
	</div> 
	
	<div>
	
		<h2>All Existing Players</h2>
		
		<% if(players.size() == 0) {%> 
		
			 <div><strong>There are no players yet! </strong></div>
		
		<%} else {%>
			<table>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Email</th>
				</tr>
			<%for(ChessPlayer player : players) {%>  
			
				<tr>
					<td><%= player.getId()%></td>
					<td><%=player.getName()%></td>
					<td><%=player.getEmail()%></td>			 	 
				</tr> 
			
			<%} %>
		
		<% }%>
	
		</table>
	</div> 
	
	<%@ include file="components/footer.jsp" %>
	
</body>
</html>