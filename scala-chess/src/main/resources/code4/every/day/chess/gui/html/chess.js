var BOARD_WIDTH = 8;
var BOARD_HEIGHT = 8;
var TILE_SIZE = 100;

function initialBoardHighlight() {
    return [
        [" ", " ", " ", " ", " ", " ", " ", " "],
        [" ", " ", " ", " ", " ", " ", " ", " "],
        [" ", " ", " ", " ", " ", " ", " ", " "],
        [" ", " ", " ", " ", " ", " ", " ", " "],
        [" ", " ", " ", " ", " ", " ", " ", " "],
        [" ", " ", " ", " ", " ", " ", " ", " "],
        [" ", " ", " ", " ", " ", " ", " ", " "],
        [" ", " ", " ", " ", " ", " ", " ", " "]
    ]
}
function initialState() {
    return [
        ["r", "p", " ", " ", " ", " ", "P", "R"],
        ["n", "p", " ", " ", " ", " ", "P", "N"],
        ["b", "p", " ", " ", " ", " ", "P", "B"],
        ["q", "p", " ", " ", " ", " ", "P", "Q"],
        ["k", "p", " ", " ", " ", " ", "P", "K"],
        ["b", "p", " ", " ", " ", " ", "P", "B"],
        ["n", "p", " ", " ", " ", " ", "P", "N"],
        ["r", "p", " ", " ", " ", " ", "P", "R"]
    ]
}

var currentState = {
    boardHighlight: initialBoardHighlight(),
    board: initialState(),
    currentMoveColor: "white",
    move_from_x: undefined,
    move_from_y: undefined
};

function getFigureImage(figure) {
    switch (figure) {
        case "K": return "&#9812";
        case "Q": return "&#9813";
        case "R": return "&#9814";
        case "B": return "&#9815";
        case "N": return "&#9816";
        case "P": return "&#9817";

        case "k": return "&#9818";
        case "q": return "&#9819";
        case "r": return "&#9820";
        case "b": return "&#9821";
        case "n": return "&#9822";
        case "p": return "&#9823";

        default: return" ";
    }
}

function showBoardState() {
    var table = "<table border='2'>";

    for (var i = 0; i < BOARD_HEIGHT; i++) {
        table += "<tr>";
        table += "<td style='width: 30px; text-align: center'>" + (BOARD_WIDTH - i) + "</td>";
        for (var j = 0; j < BOARD_HEIGHT; j++) {
            var color = (i + j ) % 2 === 0 ? "#abcdef" : "white";
            if (currentState.boardHighlight[i][j] !== " ")
                color = currentState.boardHighlight[i][j] === "1" ? "#aaffaa" : "#ffaaaa";

            table += "<td " +
                "style='background-color: "+color+"; width: " + TILE_SIZE + "px; height: "+ TILE_SIZE+"px; font-size: 60px; text-align: center' " +
                "onclick=cellOnBoardClicked(" + i + "," + j + ")>" +
                getFigureImage(currentState.board[j][i]) +
                "</td>"
        }

        table += "</tr>";
    }

    table += "<tr><td></td>";
    for (var k = 1; k <= BOARD_WIDTH; k++) {
        table += "<td style='text-align: center'>" + String.fromCharCode(64 +k) + "</td>";
    }
    table += "</tr>";

    table += "</table>";

    return table;
}

function cellOnBoardClicked(x, y) {
    console.log(x, y);
    if (isMoveClick(x, y)){
        var from_x = this.currentState.move_from_x;
        var from_y = this.currentState.move_from_y;

        if (!isValidDestinationMove(from_x, from_y, x, y))
            return;

        makeMove(from_x, from_y, x, y);

        var currentColor = this.currentState.currentMoveColor;
        this.currentState.currentMoveColor = currentColor === "white" ? "black" : "white";

        clearMoveStartPosition();
        clearHighlightPositions();
        showChessBoard();
    } else {
        if (!isFigureExist(x, y))
            return;

        if (!isSameColor(x, y))
            return;

        var figure = getFigure(x, y);
        highlightCellsForFigure(figure, x, y);

        this.currentState.move_from_x = x;
        this.currentState.move_from_y = y;
    }
}

function clearHighlightPositions() {
    this.currentState.boardHighlight = initialBoardHighlight();
}

function makeMove(from_x, from_y, to_x, to_y) {
    var figure = getFigure(from_x, from_y);

    this.currentState.board[from_y][from_x] = " ";
    this.currentState.board[to_y][to_x] = figure;
}

function isAvailablePosition(x, y) {
    return x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT;
}

function isValidDestinationMove(from_x, from_y, to_x, to_y) {
    if (!isAvailablePosition(from_x, from_y) || !isAvailablePosition(to_x, to_y))
        return false;

    if(Math.abs(from_x - to_x) === 0 && Math.abs(from_y - to_y) === 0)
        return false;

    if (!isFigureExist(from_x, from_y))
        return false;

    var figure = getFigure(from_x, from_y);

    if (isFigureExist(to_x, to_y) &&
        getFigureColor(getFigure(to_x, to_y)) ===
        getFigureColor(figure))
        return false;

    switch (figure.toUpperCase()) {
        case "N": return isValidDestinationKnightMove(from_x, from_y, to_x, to_y);
        case "K": return isValidDestinationKingMove(from_x, from_y, to_x, to_y);
        case "R": return isValidDestinationRockMove(from_x, from_y, to_x, to_y);
        case "B": return isValidDestinationBishopMove(from_x, from_y, to_x, to_y);
        case "Q": return isValidDestinationQueenMove(from_x, from_y, to_x, to_y);
        case "P": return isValidDestinationPawnMove(from_x, from_y, to_x, to_y);
    }

    return true;
}

function isValidDestinationQueenMove(from_x, from_y, to_x, to_y) {
    return isValidDestinationBishopMove(from_x, from_y, to_x, to_y) ||
        isValidDestinationRockMove(from_x, from_y, to_x, to_y);
}

function isValidDestinationRockMove(from_x, from_y, to_x, to_y) {
    if(Math.abs(from_x - to_x) !== 0 && Math.abs(from_y - to_y) !== 0)
        return false;

    if (from_x === to_x){
        for (var i = Math.min(from_y, to_y) + 1; i < Math.max(from_y, to_y); i++)
            if (isFigureExist(from_x, i))
                return false;
    }
    if (from_y === to_y){
        for (var j = Math.min(from_x, to_x) + 1; j < Math.max(from_x, to_x); j++)
            if (isFigureExist(j, from_y))
                return false;
    }

    return true;
}

function isValidDestinationBishopMove(from_x, from_y, to_x, to_y) {
    if (((to_x - from_x) !== (to_y - from_y)) &&
        ((to_x - from_x) !== (from_y - to_y)))
        return false;

    var deltaX = Math.sign((to_x - from_x));
    var deltaY = Math.sign((to_y - from_y));

    do {
        from_x += deltaX;
        from_y += deltaY;

        if(from_x === to_x && from_y === to_y && !isSameColor(from_x, from_y))
            return true;
        if (!isAvailablePosition(from_x, from_y))
            return true;
        if (isFigureExist(from_x, from_y))
            return false;
    } while (isAvailablePosition(from_x, from_y) && from_x !== to_x &&  from_y !== to_y);

    return true;
}

function isValidDestinationKnightMove(from_x, from_y, to_x, to_y) {
    return (Math.abs(from_x - to_x) === 2 && Math.abs(from_y - to_y)=== 1) ||
        Math.abs(from_x - to_x) === 1 && Math.abs(from_y - to_y) === 2;
}

function isValidDestinationKingMove(from_x, from_y, to_x, to_y) {
    return Math.abs(from_x - to_x) <= 1 &&  Math.abs(from_y - to_y) <= 1;
}

function isValidDestinationPawnMove(from_x, from_y, to_x, to_y) {
    if (Math.abs(from_x - to_x) > 2)
        return false;

    return getFigureColor(getFigure(from_x, from_y)) === "white" ?
        isValidDestinationWhitePawnMove(from_x, from_y, to_x, to_y) :
        isValidDestinationBlackPawnMove(from_x, from_y, to_x, to_y);
}

function isValidDestinationWhitePawnMove(from_x, from_y, to_x, to_y) {
    if (from_x === 6 && Math.abs(from_x - to_x) === 2 && from_y === to_y)
        return true;

    if (from_x < to_x)
        return false;

    if (Math.abs(from_y - to_y) === 1 && isFigureExist(to_x, to_y) && !isSameColor(to_x, to_y))
        return true;

    if (from_y !== to_y)
        return false;

    if (isFigureExist(to_x, to_y))
        return false;

    return Math.abs(from_x - to_x) === 1;
}

function isValidDestinationBlackPawnMove(from_x, from_y, to_x, to_y) {
    if (from_x === 1 && Math.abs(from_x - to_x) === 2 && from_y === to_y)
        return true;

    if (from_x > to_x)
        return false;

    if (Math.abs(from_y - to_y) === 1 && isFigureExist(to_x, to_y) && !isSameColor(to_x, to_y))
        return true;

    if (from_y !== to_y)
        return false;

    if (isFigureExist(to_x, to_y))
        return false;

    return Math.abs(from_x - to_x) === 1;
}

function clearMoveStartPosition() {
    this.currentState.move_from_x = null;
    this.currentState.move_from_y = null;
}

function isMoveClick(x, y) {
    if (isFigureExist(x, y) && isSameColor(x, y))
        return false;

    return isFigureExist(this.currentState.move_from_x, this.currentState.move_from_y);
}

function highlightCellsForFigure(figure, x, y) {
    currentState.boardHighlight = initialBoardHighlight();

    switch (figure.toUpperCase()) {
        case "N": highlightCellsForKnight(x, y); break;
        case "R": highlightCellsForRock(x, y); break;
        case "B": highlightCellsForBishop(x, y); break;
        case "Q": highlightCellsForQueen(x, y); break;
        case "K": highlightCellsForKing(x, y); break;
        case "P": highlightCellsForPawn(x, y); break;
    }

    showChessBoard();
}

function highlightCellsForKnight(x, y) {
    currentState.boardHighlight[x][y] = "1";

    if (isValidDestinationMove(x, y, x - 2, y - 1))
        currentState.boardHighlight[x - 2][y - 1] = "2";
    if (isValidDestinationMove(x, y, x - 2, y + 1))
        currentState.boardHighlight[x - 2][y + 1] = "2";
    if (isValidDestinationMove(x, y, x - 1, y + 2))
        currentState.boardHighlight[x - 1][y + 2] = "2";
    if (isValidDestinationMove(x, y, x - 1, y - 2))
        currentState.boardHighlight[x - 1][y - 2] = "2";
    if (isValidDestinationMove(x, y, x + 2, y + 1))
        currentState.boardHighlight[x + 2][y + 1] = "2";
    if (isValidDestinationMove(x, y, x + 2, y - 1))
        currentState.boardHighlight[x + 2][y - 1] = "2";
    if (isValidDestinationMove(x, y, x + 1, y - 2))
        currentState.boardHighlight[x + 1][y - 2] = "2";
    if (isValidDestinationMove(x, y, x + 1, y + 2))
        currentState.boardHighlight[x + 1][y + 2] = "2";
}

function highlightCellsForKing(x, y) {
    currentState.boardHighlight[x][y] = "1";

    if (isValidDestinationMove(x, y, x - 1, y - 1))
        currentState.boardHighlight[x - 1][y - 1] = "2";
    if (isValidDestinationMove(x, y, x - 1, y))
        currentState.boardHighlight[x - 1][y] = "2";
    if (isValidDestinationMove(x, y, x - 1, y + 1))
        currentState.boardHighlight[x - 1][y + 1] = "2";

    if (isValidDestinationMove(x, y, x, y - 1))
        currentState.boardHighlight[x][y - 1] = "2";
    if (isValidDestinationMove(x, y, x, y + 1))
        currentState.boardHighlight[x][y + 1] = "2";

    if (isValidDestinationMove(x, y, x + 1, y - 1))
        currentState.boardHighlight[x + 1][y - 1] = "2";
    if (isValidDestinationMove(x, y, x + 1, y))
        currentState.boardHighlight[x + 1][y] = "2";
    if (isValidDestinationMove(x, y, x + 1, y + 1))
        currentState.boardHighlight[x + 1][y + 1] = "2";

    currentState.boardHighlight[x][y] = "1";
}

function highlightCellsForBishop(x, y) {
    currentState.boardHighlight[x][y] = "1";

    for (var i = 0; i < BOARD_HEIGHT; i++) {
        if (isValidDestinationMove(x, y, x + i, y + i))
            currentState.boardHighlight[x + i][y + i] = "2";

        if (isValidDestinationMove(x, y, x + i, y - i))
            currentState.boardHighlight[x + i][y - i] = "2";

        if (isValidDestinationMove(x, y, x - i, y + i))
            currentState.boardHighlight[x - i][y + i] = "2";

        if (isValidDestinationMove(x, y, x - i, y - i))
            currentState.boardHighlight[x - i][y - i] = "2";
    }
}

function highlightCellsForRock(x, y) {
    currentState.boardHighlight[x][y] = "1";

    for (var ip = x + 1; ip < BOARD_WIDTH; ip++) {
        if (!isValidDestinationMove(x, y, ip, y))
            break;

        currentState.boardHighlight[ip][y] = "2";
    }

    for (var im = x - 1; im >= 0; im--) {
        if (!isValidDestinationMove(x, y, im, y))
            break;
        currentState.boardHighlight[im][y] = "2";
    }

    for (var yp = y + 1; yp < BOARD_WIDTH; yp++) {
        if (!isValidDestinationMove(x, y, x, yp))
            break;
        currentState.boardHighlight[x][yp] = "2";
    }

    for (var ym = y - 1; ym >= 0; ym--) {
        if (!isValidDestinationMove(x, y, x, ym))
            break;
        currentState.boardHighlight[x][ym] = "2";
    }
}

function highlightCellsForQueen(x, y) {
    currentState.boardHighlight[x][y] = "1";

    for (var i = 0; i < BOARD_HEIGHT; i++) {
        if (isValidDestinationMove(x, y, x + i, y + i))
            currentState.boardHighlight[x + i][y + i] = "2";

        if (isValidDestinationMove(x, y, x + i, y - i))
            currentState.boardHighlight[x + i][y - i] = "2";

        if (isValidDestinationMove(x, y, x - i, y + i))
            currentState.boardHighlight[x - i][y + i] = "2";

        if (isValidDestinationMove(x, y, x - i, y - i))
            currentState.boardHighlight[x - i][y - i] = "2";
    }

    for (var ip = x + 1; ip < BOARD_WIDTH; ip++) {
        if (!isValidDestinationMove(x, y, ip, y))
            break;

        currentState.boardHighlight[ip][y] = "2";
    }

    for (var im = x - 1; im >= 0; im--) {
        if (!isValidDestinationMove(x, y, im, y))
            break;
        currentState.boardHighlight[im][y] = "2";
    }

    for (var yp = y + 1; yp < BOARD_WIDTH; yp++) {
        if (!isValidDestinationMove(x, y, x, yp))
            break;
        currentState.boardHighlight[x][yp] = "2";
    }

    for (var ym = y - 1; ym >= 0; ym--) {
        if (!isValidDestinationMove(x, y, x, ym))
            break;
        currentState.boardHighlight[x][ym] = "2";
    }
}

function highlightCellsForPawn(x, y) {
    currentState.boardHighlight[x][y] = "1";

    for (var i = x + 1; i < BOARD_HEIGHT; i++) {
        if (!isValidDestinationMove(x, y, i, y))
            break;
        currentState.boardHighlight[i][y] = "2";
    }

    for (var j = x - 1; j >= 0; j--) {
        if (!isValidDestinationMove(x, y, j, y))
            break;
        currentState.boardHighlight[j][y] = "2";
    }

    if (isValidDestinationMove(x, y, x + 1, y + 1))
        currentState.boardHighlight[x + 1][y + 1] = "2";
    if (isValidDestinationMove(x, y, x + 1, y - 1))
        currentState.boardHighlight[x + 1][y - 1] = "2";
    if (isValidDestinationMove(x, y, x - 1, y + 1))
        currentState.boardHighlight[x - 1][y + 1] = "2";
    if (isValidDestinationMove(x, y, x - 1, y - 1))
        currentState.boardHighlight[x - 1][y - 1] = "2";
}

function canMoveTo(x, y){
    if (!isFigureExist(x, y)){
        return true;
    }

    return !isSameColor(x, y);
}

function isFigureExist(x, y) {
    if (x === undefined || y === undefined)
        return false;

    if (x >= BOARD_WIDTH || y >= BOARD_HEIGHT || x < 0 || y < 0)
        return false;

    if (x === null || x === " " || y === null || y === " ")
        return false;

    return currentState.board[y][x] !== " ";
}

function getFigure(x, y) {
    if (!isFigureExist(x, y))
        return null;

    return currentState.board[y][x];
}

function getFigureColor(figure){
    return figure.toUpperCase() === figure ? "white": "black";
}

function isSameColor(x, y) {
    if(!isFigureExist(x, y))
        return false;

    var figure = getFigure(x, y);
    var color = getFigureColor(figure);

    return color === this.currentState.currentMoveColor;
}

function showChessBoard() {
    document.getElementById("table").innerHTML = showBoardState();
}