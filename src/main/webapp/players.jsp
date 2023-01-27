<%@ page import="domain.ChessPlayer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="persist.ChessPlayerDM" %>
<%@ page import="java.util.Optional" %>
<%@ page import="domain.ChessGame" %>
<%@ page import="java.util.Date" %>
<%@ page import="persist.ChessGameDM" %>
<%@ page import="persist.ChessPlayerDM" %>
<%@ page import="domain.Color" %>
<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="utils/auth.jsp" %>

<%
// GET = recebe "name" e redirecciona para play ou mostra a pagina para escolher jogador (se varios e faz POST do "id")
// POST = redireciona para play com "id"
if(request.getMethod().equals("POST")) {
    ChessPlayerDM dm = ChessPlayerDM.getInstance();
    ChessGameDM gameDM = ChessGameDM.getInstance();
    int id = Integer.valueOf(request.getParameter("id"));
    //check if player is different from authenticated player and if player exists
    if(id == authPlayer.getId() || dm.findByID(id) == null) {
        response.sendRedirect("players.jsp");
        return;
    }
    // pick color at random
    Random random = new Random();
    ChessGame game;
    if(random.nextBoolean())
        game = new ChessGame(authPlayer, dm.findByID(id), new Date());
    else
        game = new ChessGame(dm.findByID(id), authPlayer, new Date());
    // game.loadBoard();
    
    int gameId = gameDM.insert(game);
    response.sendRedirect("play.jsp?id=" + gameId);
    return;
}
%>

<html>
<head>
    <title>Players</title>

    <%
        ChessPlayerDM dm = ChessPlayerDM.getInstance();
        String email = request.getParameter("email");
        
        String adversary = request.getParameter("name");
        List<ChessPlayer> players = dm.findByName(adversary);
        
        ChessPlayerDM playerDM = ChessPlayerDM.getInstance();
        Optional<ChessPlayer> advPlayer = playerDM.findByEmail(email);
    
        // check if there is no player or if the player is the same as the authenticated player
        players.removeIf(player -> player.equals(authPlayer));



        if (players.size() == 0) {
        
            // Redirect to user already exists page
            response.sendRedirect("noOpponentFound.jsp?name=" + adversary);
            return;




        // Automatically start the game if there is only one player
        } else if (players.size() == 1) {
            ChessGameDM gameDM = ChessGameDM.getInstance();

            // pick color at random
            Random random = new Random();
            ChessGame game;
            if(random.nextBoolean())
                game = new ChessGame(authPlayer, players.get(0), new Date());
            else
            game = new ChessGame(players.get(0), authPlayer, new Date());
            // game.loadBoard();
            
            int gameId = gameDM.insert(game);
            response.sendRedirect("play.jsp?id=" + gameId);
            return;
        }else{
            out.print("<table>");
            
            out.print("<tr>");
                out.print("<th>" +  "Name" + "</th>");
                out.print("<th>" +  "Email" + "</th>");
                out.print("<th>" +  "Choice" + "</th>");
            out.print("</tr>");
            for (ChessPlayer player: players) {
                

                out.print("<tr>");
                    out.print("<td>" + player.getName() + "</td>");
                    out.print("<td>" + player.getEmail() + "</td>");
                    out.print("<td>");
                        out.print("<form action='players.jsp' id='choosePlayer' method='post'>");
                        out.print("<input type='hidden' name='id' id='id' value='" + player.getId() + "' required>");

                        
                        out.print("<input type='submit' value='Play'>");
                        out.print("</form>");
                        out.print("</td>");

                out.print("</tr>");
            }
            out.print("</table>");
        }
    %>
</head>
<body>

</body>
</html>

