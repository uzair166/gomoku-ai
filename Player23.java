import java.awt.*;
import java.util.ArrayList;

/** The random gomoku player chooses random squares on the board (using a
 *  uniform distribution) until an unoccupied square is found, which is then
 *  returned as the player's move. It is assumed that the board is not full,
 *  otherwise chooseMove() will get stuck in an infinite loop.
 *	Author: Simon Dixon
 **/
class Player23 extends GomokuPlayer {

    public Move chooseMove(Color[][] board, Color me) {
        return miniMax2(board, me);
    } // chooseMove()




    public ArrayList<Move> possibleMoves(Color[][] board){
        ArrayList<Move> moves = new ArrayList<>();
        for (int row = 0; row < GomokuBoard.ROWS; row++){
            for (int col = 0; col < GomokuBoard.COLS; col++){
                if(board[row][col] == null) moves.add(new Move(row, col));
            }
        }
        return moves;
    } // possibleMoves()

    public static ArrayList<Move> possibleAdjacentMoves(Color[][] board){
        ArrayList<Move> moves = new ArrayList<>();
        //HashSet<Move> movesSet = new HashSet<>(); // hash set to avoid duplicates
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == Color.white || board[i][j] == Color.black) { //success!
                        int x=1;
                        if(i+x < board.length && board[i+x][j] == null){//down
                            moves.add(new Move(i+x, j));
                        }
                        if(i-x >= 0 && board[i-x][j] == null){//up
                            moves.add(new Move(i-x, j));
                        }
                        if(j+x < board.length && board[i][j+x] == null){//right
                            moves.add(new Move(i,j+x));
                        }
                        if(j-x >= 0 && board[i][j-x] == null){//left
                            moves.add(new Move(i, j-x));
                        }
                        if(i-x >= 0 && j-x >= 0 && board[i-x][j-x] == null){//top-left
                            moves.add(new Move(i-x, j-x));
                        }
                        if(i+x < board.length && j-x >=0 && board[i+x][j-x] == null){//bottom-left
                            moves.add(new Move(i+x, j-x));
                        }
                        if(i-x >= 0 && j+x < board.length && board[i-x][j+x] == null){//top-right
                            moves.add(new Move(i-x, j+x));
                        }
                        if(i+x < board.length && j+x < board.length && board[i+x][j+x] == null){//bottom-right
                            moves.add(new Move(i+x, j+x));
                        }

                }
            }
        }
        if (moves.size()==0) moves.add(new Move(4,4));
        return moves;
    }

    public static ArrayList<Color[][]> possibleBoards(Color[][] board, Color me, ArrayList<Move> moves){
        ArrayList<Color[][]> boards = new ArrayList<>();
        for (int i=0; i < moves.size(); i++){
            boards.add(simMove(board, me, moves.get(i)));
        }
        return boards;
    }

    public static Color[][] simMove(Color[][] board, Color me, Move move){
        Color[][] newBoard = new Color[GomokuBoard.ROWS][GomokuBoard.COLS];
        for(int i=0; i<board.length; i++){
            newBoard[i] = board[i].clone();
        }
        newBoard[move.row][move.col] = me;
        return newBoard;
    }

    public static Color getOpponent(Color me){
        return (me == Color.white) ? Color.black :  Color.white;
    }

    public static boolean fivePossible(Color[] row, Color me, int pos){
        int from = pos-4 < 0 ?  0 : pos-4; //search 4 before and 4 after
        int to = pos + 4 >= row.length ? row.length-1 : pos+4;
        int count = 0;
        for (int i = from; i <= to; i++){
            //if (5-count > to-i) return false;
            if (row[i] == getOpponent(me)) count = 0;
            else count++;
            if (count==5) return true;
        }
        return false;
    }

    public Move miniMax2(Color[][] board, Color me){
        int maxDepth = 2;
        Move bestMove = null;
        Double bestMoveEval = Double.NEGATIVE_INFINITY, moveEval;
        ArrayList<Move> moves = possibleAdjacentMoves(board);
        for (int i=0; i<moves.size(); i++){
            moveEval = min2(simMove(board, me, moves.get(i)), getOpponent(me), maxDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            if (moveEval > bestMoveEval){
                bestMove = moves.get(i);
                bestMoveEval = moveEval;
            }
        }
        return bestMove;
    }

    public static double max2(Color[][] board, Color me, int depth, double alpha, double beta){
        double evalNow = eval(board, me);
        if (depth == 0) return evalNow;
        Double bestMoveEval = Double.NEGATIVE_INFINITY, moveEval;
        ArrayList<Move> moves = possibleAdjacentMoves(board);
        for (int i=0; i<moves.size(); i++){
            moveEval = min2(simMove(board, me, moves.get(i)), getOpponent(me), depth-1, alpha, beta);
            if (moveEval > bestMoveEval) bestMoveEval = moveEval;
            if (moveEval >= beta) return moveEval;
            if (moveEval > alpha) alpha = moveEval;
        }
        return bestMoveEval;
    }

    public static double min2(Color[][] board, Color me, int depth, double alpha, double beta){
        double evalNow = eval(board, getOpponent(me));
        if (depth == 0) return evalNow;
        Double bestMoveEval = Double.POSITIVE_INFINITY, moveEval;
        ArrayList<Move> moves = possibleAdjacentMoves(board);
        for (int i=0; i< moves.size(); i++){
            moveEval = max2(simMove(board, me, moves.get(i)), getOpponent(me), depth-1, alpha, beta);
            if (moveEval <bestMoveEval) bestMoveEval = moveEval;
            if (moveEval<=alpha) return moveEval;
            if (moveEval < beta) beta = moveEval;
        }
        return bestMoveEval;
    }

    public static double eval(Color[][] board, Color me){
        double val = 0.0;

        //**********************CHECK ROWS*****************************
        for (int row = 0; row < GomokuBoard.ROWS; row++){
            Color curPiece = null;
            Color prevPiece = null;
            boolean beginOpen = false;
            boolean endOpen = false;
            int curCount = 0;
            for (int col = 0; col < GomokuBoard.COLS; col++){
                //System.out.println("checking row "+ row+" col "+ col );
                curPiece = board[row][col];
                if (curPiece != null && fivePossible(board[row], curPiece, col)){
                    //System.out.println("- found five possible");
                    curCount = 1;
                    if (col!=0 && board[row][col-1]==null) beginOpen = true;
                    for (int i = col+1; i<=col+4; i++){

                        if (i>=GomokuBoard.COLS) break;
                        if (board[row][i]==curPiece) curCount++;
                        else break;
                        col = i;
                    }
                    if (col+1 != GomokuBoard.COLS && board[row][col+1]==null) endOpen = true;
                    val += points(me, curPiece, curCount, beginOpen, endOpen);
                    if (val == Double.POSITIVE_INFINITY) return val;
                    //System.out.println("- "+curCount+" "+curPiece+" in a row");
                    //System.out.println("- beginopen: "+beginOpen+" // endopen: "+endOpen);
                    //System.out.println("- "+points(me, curPiece, curCount, beginOpen, endOpen)+" points given");
                    beginOpen = false;
                    endOpen= false;
                    continue;
                }
                prevPiece = curPiece;
            } // inner loop - cols
        } // outer loop - rows



        //**********************CHECK COLS*****************************
        for (int col = 0; col < GomokuBoard.COLS; col++){
            Color curPiece = null;
            Color prevPiece = null;
            boolean beginOpen = false;
            boolean endOpen = false;
            int curCount = 0;
            for (int row = 0; row < GomokuBoard.ROWS; row++){
                //System.out.println("checking row "+ row+" col "+ col );
                curPiece = board[row][col];
                Color[] curRow = new Color[GomokuBoard.ROWS];
                for (int i=0; i<curRow.length; i++){
                    curRow[i] = board[i][col];
                }
                if (curPiece != null && fivePossible(curRow, curPiece, row)){
                    //System.out.println("- found five possible");
                    curCount = 1;
                    if (row!=0 && board[row-1][col]==null) beginOpen = true;
                    for (int i = row+1; i<=row+4; i++){

                        if (i>=GomokuBoard.ROWS) break;
                        if (board[i][col]==curPiece) curCount++;
                        else break;
                        row = i;
                    }
                    if (row+1 != GomokuBoard.COLS && board[row+1][col]==null) endOpen = true;
                    val += points(me, curPiece, curCount, beginOpen, endOpen);
                    if (val == Double.POSITIVE_INFINITY) return val;
                    //System.out.println("- "+curCount+" "+curPiece+" in a row");
                    //System.out.println("- beginopen: "+beginOpen+" // endopen: "+endOpen);
                    //System.out.println("- "+points(me, curPiece, curCount, beginOpen, endOpen)+" points given");
                    beginOpen = false;
                    endOpen= false;
                    continue;
                }
                prevPiece = curPiece;
            } // inner loop - cols
        } // outer loop - rows


        //**********************CHECK DIAG BOTTOM RIGHT*****************************
        ArrayList<ArrayList<Color>> diagonals = new ArrayList<>();
        for (int i=1-GomokuBoard.ROWS; i<GomokuBoard.COLS; i++){
            ArrayList<Color> list = new ArrayList<>();
            for (int j=0; j<GomokuBoard.ROWS; j++){
                if ((i+j) >= 0 && (i+j) < GomokuBoard.COLS) list.add(board[j][i+j]);
            }
            diagonals.add(list);
        }

        Color[][] boardFlipped = new Color[GomokuBoard.ROWS][GomokuBoard.COLS];
        for (int i = 0; i < GomokuBoard.ROWS; i++) {
            for (int j = GomokuBoard.COLS - 1, k = 0; j >= 0; j--, k++) {
                boardFlipped[k][i] = board[j][i];
            }
        }

        for (int i=1-GomokuBoard.ROWS; i<GomokuBoard.COLS; i++){
            ArrayList<Color> list = new ArrayList<>();
            for (int j=0; j<GomokuBoard.ROWS; j++){
                if ((i+j) >= 0 && (i+j) < GomokuBoard.COLS) list.add(boardFlipped[j][i+j]);
            }
            diagonals.add(list);
        }

        for (int a=0; a<diagonals.size(); a++){
            if (diagonals.get(a).size() < 5) continue;
            Color curPiece = null;
            Color prevPiece = null;
            boolean beginOpen = false;
            boolean endOpen = false;
            int curCount = 0;
            Color[] row = new Color[diagonals.get(a).size()];
            row = diagonals.get(a).toArray(row);
            for (int col=0; col<row.length; col++){
                curPiece = row[col];
                if (curPiece!=null && fivePossible(row, curPiece, col)){
                    curCount = 1;
                    if (col!=0 && row[col-1]==null) beginOpen=true;
                    for (int i = col+1; i<=col+4; i++){

                        if (i>=row.length) break;
                        if (row[i]==curPiece) curCount++;
                        else break;
                    }
                    if (col+1 != row.length && row[col+1]==null) endOpen = true;
                    val += points(me, curPiece, curCount, beginOpen, endOpen);
                    if (val == Double.POSITIVE_INFINITY) return val;
                    //System.out.println("- "+curCount+" "+curPiece+" in a row");
                    //System.out.println("- beginopen: "+beginOpen+" // endopen: "+endOpen);
                    //System.out.println("- "+points(me, curPiece, curCount, beginOpen, endOpen)+" points given");
                    beginOpen = false;
                    endOpen= false;
                    continue;
                }
                prevPiece = curPiece;
            }
        }

        return val;
    }

    public static double eval2(Color[][] board, Color me){
        double val = 0.0;

        Color[][] linesToCheck = new Color[30][];

        for (int i=0; i<8; i++){
            linesToCheck[i] = board[i];
        }
        for (int a=8; a<16; a++){
            linesToCheck[a] = new Color[8];
            for (int i=0; i<8; i++){
                for (int j=0; j<8; j++){
                    linesToCheck[a][i] = board[j][i];
                }
            }
        }
        ArrayList<ArrayList<Color>> diagonals = new ArrayList<>();
        for (int i=1-GomokuBoard.ROWS; i<GomokuBoard.COLS; i++){
            ArrayList<Color> list = new ArrayList<>();
            for (int j=0; j<GomokuBoard.ROWS; j++){
                if ((i+j) >= 0 && (i+j) < GomokuBoard.COLS) list.add(board[j][i+j]);
            }
            diagonals.add(list);
        }

        Color[][] boardFlipped = new Color[GomokuBoard.ROWS][GomokuBoard.COLS];
        for (int i = 0; i < GomokuBoard.ROWS; i++) {
            for (int j = GomokuBoard.COLS - 1, k = 0; j >= 0; j--, k++) {
                boardFlipped[k][i] = board[j][i];
            }
        }

        for (int i=1-GomokuBoard.ROWS; i<GomokuBoard.COLS; i++) {
            ArrayList<Color> list = new ArrayList<>();
            for (int j = 0; j < GomokuBoard.ROWS; j++) {
                if ((i + j) >= 0 && (i + j) < GomokuBoard.COLS) list.add(boardFlipped[j][i + j]);
            }
            diagonals.add(list);
        }

        for (int i=16; i<30; i++){
            linesToCheck[i] = diagonals.get(i-16).toArray(new Color[diagonals.get(i-16).size()]);
        }

        for (int a=0; a<linesToCheck.length; a++){
            if (linesToCheck[a].length < 5) continue;
            Color curPiece;
            boolean beginOpen = false;
            boolean endOpen = false;
            int curCount;
            Color[] row = linesToCheck[a];
            for (int col=0; col<row.length; col++){
                curPiece = row[col];
                if (curPiece!=null && fivePossible(row, curPiece, col)){
                    curCount = 1;
                    if (col!=0 && row[col-1]==null) beginOpen=true;
                    for (int i = col+1; i<=col+4; i++){
                        if (i>=row.length) break;
                        if (row[i]==curPiece) curCount++;
                        else break;
                        col=i;
                    }
                    if (col+1 != row.length && row[col+1]==null) endOpen = true;
                    val += points(me, curPiece, curCount, beginOpen, endOpen);;
                    beginOpen = false;
                    endOpen= false;
                    continue;
                }
            }
        }


        return val;
    }

    public static double eval3(Color[][] board, Color me){
        double val = 0.0;

        Color[][] linesToCheck = new Color[30][];

        for (int i=0; i<8; i++){
            linesToCheck[i] = board[i];
        }
        for (int a=8; a<16; a++){
            linesToCheck[a] = new Color[8];
            for (int i=0; i<8; i++){
                for (int j=0; j<8; j++){
                    linesToCheck[a][j] = board[j][i];
                }
            }
        }
        ArrayList<ArrayList<Color>> diagonals = new ArrayList<>();
        for (int i=1-GomokuBoard.ROWS; i<GomokuBoard.COLS; i++){
            ArrayList<Color> list = new ArrayList<>();
            for (int j=0; j<GomokuBoard.ROWS; j++){
                if ((i+j) >= 0 && (i+j) < GomokuBoard.COLS) list.add(board[j][i+j]);
            }
            diagonals.add(list);
        }

        Color[][] boardFlipped = new Color[GomokuBoard.ROWS][GomokuBoard.COLS];
        for (int i = 0; i < GomokuBoard.ROWS; i++) {
            for (int j = GomokuBoard.COLS - 1, k = 0; j >= 0; j--, k++) {
                boardFlipped[k][i] = board[j][i];
            }
        }

        for (int i=1-GomokuBoard.ROWS; i<GomokuBoard.COLS; i++) {
            ArrayList<Color> list = new ArrayList<>();
            for (int j = 0; j < GomokuBoard.ROWS; j++) {
                if ((i + j) >= 0 && (i + j) < GomokuBoard.COLS) list.add(boardFlipped[j][i + j]);
            }
            diagonals.add(list);
        }

        System.out.println(diagonals);

        for (int i=16; i<30; i++){
            linesToCheck[i] = diagonals.get(i-16).toArray(new Color[diagonals.get(i-16).size()]);
        }

        for (int a=0; a<linesToCheck.length; a++){
            if (linesToCheck[a].length < 5) continue;
            Color curPiece;
            boolean beginOpen = false;
            boolean endOpen = false;
            int curCount;
            Color[] row = linesToCheck[a];
            for (int col=0; col<row.length; col++){
                curPiece = row[col];
                if (curPiece!=null && fivePossible(row, curPiece, col)){
                    curCount = 1;
                    if (col!=0 && row[col-1]==null) beginOpen=true;
                    for (int i = col+1; i<=col+4; i++){
                        if (i>=row.length) break;
                        if (row[i]==curPiece) curCount++;
                        else break;
                        col=i;

                    }
                    if (col+1 != row.length && row[col+1]==null) endOpen = true;

                    if (a<8){
                        System.out.println("row "+points(me, curPiece, curCount, beginOpen, endOpen));
                    }
                    else if (a<16){
                        System.out.println("col "+points(me, curPiece, curCount, beginOpen, endOpen));
                    }
                    else if (a<23){
                        System.out.println("diag1 "+points(me, curPiece, curCount, beginOpen, endOpen));
                    }
                    else {
                        System.out.println("diag2 "+points(me, curPiece, curCount, beginOpen, endOpen));
                    }
                    val += points(me, curPiece, curCount, beginOpen, endOpen);
                    beginOpen = false;
                    endOpen= false;
                    continue;
                }
            }
        }


        return val;
    }

    public static Double points(Color me, Color thePiece, int howMany, boolean beginOpen, boolean endOpen){
        Double pts = 0.0;


        if (beginOpen && endOpen) {
            if (howMany == 1) pts = 2.0;
            else if (howMany == 2) pts = 10.0;
            else if (howMany == 3) pts = 100.0;
            else if (howMany == 4) pts = 500.0;
            else if (howMany == 5) pts = 10000000.0;
        }
        else {
            if (howMany == 1) pts = 1.0;
            else if (howMany == 2) pts = 5.0;
            else if (howMany == 3) pts = 10.0;
            else if (howMany == 4) pts = me == thePiece ? 50.0 : 100.0;
            else if (howMany == 5) pts = 10000000.0;
        }








        return me == thePiece ? pts : (pts) * -1.5;

    }

//    public static double points(Color me, Color thePiece, int howMany, boolean beginOpen, boolean endOpen){
//        Double pts = 0.0;
//
////        switch (howMany){
////            case 5: return Double.POSITIVE_INFINITY;
////            case 4: pts = 100000;
////            case 3: pts = 1000;
////            case 2: pts = 10;
////            case 1: pts = 1;
////            default:  pts =0;
////        }
//
////        if (howMany == 1) pts = 1.0;
////        else if (howMany == 2) pts = 2.0;
////        else if (howMany == 3) pts = 3.0;
////        else if (howMany == 4) pts = 2.0;
////        else if (howMany == 5) pts = me == thePiece ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
////
////        if (beginOpen && endOpen) pts = pts*10;
////        else if (howMany == 4) pts = pts*5;
//
//        if(beginOpen &&endOpen){
//            if (howMany == 1) pts = me == thePiece ? 2.5:2.5;
//            else if (howMany == 2) pts = 4.0;
//            else if (howMany == 3) pts = me == thePiece ? 6.0:12.0;
//            else if (howMany == 4) pts = me == thePiece ? 1000000.0:100000.0;
//            else if (howMany == 5) pts = 1000000.0;
//
//
//        }
//        else{
//            if (howMany == 1) pts = 1.0;
//            else if (howMany == 2) pts = 2.0;
//            else if (howMany == 3) pts = 3.0;
//            else if (howMany == 4) pts = me == thePiece ? 4:1000.0;
//            else if (howMany == 5) pts = 1000000.0;
//
//
//        }
//
////        if (me1 == thePiece) pts+=1;
////        else {
////            if (howMany==4) pts+=2;
////            pts*=-1;
////        }
//
//        return me == thePiece ? pts : (pts) * -1;
////        return pts;
//
//    }



//    if (howMany == 1) pts = 1.0;
//        else if (howMany == 2) pts = 10.0;
//        else if (howMany == 3) pts = 100.0;
//        else if (howMany == 4) pts = 1000000.0;
//        else if (howMany == 5) pts = 10000000000.0;
//
//        if (beginOpen && endOpen) pts = pts*10;


}

