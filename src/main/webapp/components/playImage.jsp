<%-- REQUIRES visualization String, game ChessGame --%>
<%@ page import="domain.ChessPiece" %>
<%@ page import="domain.ChessPosition" %><%--
  Created by IntelliJ IDEA.
  User: lucks
  Date: 1/2/23
  Time: 12:34 AM
  To change this template use File | Settings | File Templates.
--%>
<div id="board">
    <!-- Inject -->
</div>

<script>
    const $board = $("#board");
    const size = 8;
    for (let row = size-1; row >= 0; row--) {
        const $row = $('<div id="row_' + (row + 1) + '"></div>');
        for (let col = 0; col < size; col++) {
            const colName = String.fromCharCode('a'.charCodeAt(0) + col);
            const squareId = col + (row % 2);

            const $square = $('<div id="square_' + colName+ '' + (row + 1) + '" class="square color_' + squareId % 2 + '"></div>');
            $row.append($square);
        }
        $board.append($row);
    }

    // After the image boards are built, add the pieces to it
    <% if (!visualization.equals("text")) { %>
        <% for (int row = 0; row < game.getBoard().getSize(); row++) { %>
            <% for (int col = 0; col < game.getBoard().getSize(); col++) { %>
                <% ChessPiece piece = game.getBoard().get(row, col); %>
                <% if (piece != null) { %>
                    <% ChessPosition pos = new ChessPosition(row, col); %>
                    $("#square_<%= pos.toString() %>").toggleClass("<%= piece.getColor() %>");
                    $("#square_<%= pos.toString() %>").toggleClass("<%= piece.getChessPieceKind() %>");
                <% } %>
            <% } %>
        <% } %>
    <% } %>
</script>

<style>
    #board {
        width: 500px;
        height: 500px;
        border: 1px solid black;
        margin: 15px 0;
    }

    .square {
        width: calc(500px / 8);
        height: calc(500px / 8);
        float: left;
    }

    .color_0 {
        background-color: #69a052;
    }

    .color_1 {
        background-color: #ebf3d0;
    }

    #row_1 > .square {
        position: relative;
    }

    #row_1 > .square::after {
        content: attr(id);
        position: absolute;
        bottom: -20px;
        left: 0;
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 1em;
    }

    #row_1 > .square:first-child::after {
        content: "a";
    }

    #row_1 > .square:nth-child(2)::after {
        content: "b";
    }

    #row_1 > .square:nth-child(3)::after {
        content: "c";
    }

    #row_1 > .square:nth-child(4)::after {
        content: "d";
    }

    #row_1 > .square:nth-child(5)::after {
        content: "e";
    }

    #row_1 > .square:nth-child(6)::after {
        content: "f";
    }

    #row_1 > .square:nth-child(7)::after {
        content: "g";
    }

    #row_1 > .square:nth-child(8)::after {
        content: "h";
    }

    #row_1 > .square:nth-child(9)::after {
        content: "i";
    }

    /* add the number overlays to the first cell of each column */
    #row_1 > .square:first-child::before {
        content: "1";
    }

    #row_2 > .square:first-child::before {
        content: "2";
    }

    #row_3 > .square:first-child::before {
        content: "3";
    }

    #row_4 > .square:first-child::before {
        content: "4";
    }

    #row_5 > .square:first-child::before {
        content: "5";
    }

    #row_6 > .square:first-child::before {
        content: "6";
    }

    #row_7 > .square:first-child::before {
        content: "7";
    }

    #row_8 > .square:first-child::before {
        content: "8";
    }

    /* The pieces */
    .black.king {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/f/f0/Chess_kdt45.svg");
        background-size: cover;
    }

    .black.queen {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/4/47/Chess_qdt45.svg");
        background-size: cover;
    }

    .black.rook {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/f/ff/Chess_rdt45.svg");
        background-size: cover;
    }

    .black.bishop {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/9/98/Chess_bdt45.svg");
        background-size: cover;
    }

    .black.knight {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/e/ef/Chess_ndt45.svg");
        background-size: cover;
    }

    .black.pawn {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/c/c7/Chess_pdt45.svg");
        background-size: cover;
    }

    .white.king {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/4/42/Chess_klt45.svg");
        background-size: cover;
    }

    .white.queen {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/1/15/Chess_qlt45.svg");
        background-size: cover;
    }

    .white.rook {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/7/72/Chess_rlt45.svg");
        background-size: cover;
    }

    .white.bishop {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/b/b1/Chess_blt45.svg");
        background-size: cover;
    }

    .white.knight {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/7/70/Chess_nlt45.svg");
        background-size: cover;
    }

    .white.pawn {
        background-image: url("https://upload.wikimedia.org/wikipedia/commons/4/45/Chess_plt45.svg");
        background-size: cover;
    }
</style>