import java.awt.Color;

//matchstick example : N matchsticks, depth = N
//Here, 64 possible moves, initial depth = 64
class ActionUtilPair {
    Move move;
    int util;

    public ActionUtilPair(Move m, Color[][] s) {
        this.move = m;
        this.state = s;
    }//END ActionUtil constructor
}//END HELPER CLASS ACTIONUTILPAIR

class PlayerAlex extends GomokuPlayer {
    GomokuBoard b;


    public PlayerAlex() {
        b = new GomokuBoard();


    }//constructor

    public Move chooseMove(Color[][] board, Color myColour) {
        int depth=0;
        //Color[][] boardCopy = copyBoard(board);
        //Move move = minimax(board, myColour);
        ActionUtilPair possibleMovePair = null;
        //if my turn
        if (myColour.equals(Color.black))
            possibleMovePair = minimax_MAX(board, myColour, depth);

        else if (myColour.equals(Color.white))
            possibleMovePair = minimax_MIN(board, myColour, depth);
        return possibleMovePair.move;
    }
    //function MINIMAX-MaxPlayer(state) returns an (action, utility) pair
    //    input: state - current state of game
    // if terminal-Test(state) then return (null, utility(state))
    // best := (null, -∞)
    // for a in actions(state) do // actions() returns all legal moves from a state
    //    value := MINIMAX-MinPlayer(makeMove(state, a)).utility
    //    if value > best.utility then best := (a, value)
    // return best
    public ActionUtilPair minimax_MAX(Color[][] board, Color myColour, int depth) {
//        Color player;
//        if (myColour.equals(Color.white))
//            player = Color.white;
//        else if (myColour.equals(Color.black))
//            player = Color.black;
        ActionUtilPair best = new ActionUtilPair(new Move(-1,-1),board); //dummy move
        //Move move = new Move(-1,-1);
        if ((b.getWinner() != null) || b.getMoveCount() ==64)/*terminal node*/ {
            //return action utility pair (null, -infinity) AKA (null, utility(state))
            best.move = null;
            //best.score = evaluate(best.move);
            //best.score = -10000;
            best.state = board;
        }
        for (int r = 0; r<=b.ROWS; r++)
            for (int c=0; c<=b.COLS; c++)
                if (board[r][c] == null) {
                    Move newMove = new Move(r,c);
                    b.makeMove(newMove, myColour);
                    //board[r][c]=myColour;
                    Color[][] newBoard = copyBoard(b.getPublicBoard());
                    Color newColour = b.getTurn();
                    //Move move = chooseMove(newBoard, newColour);
                    depth++;
                    ActionUtilPair pair = minimax_MIN(newBoard, newColour, depth);
                    if (evaluate(pair) > evaluate(new ActionUtilPair(best.move, newBoard))) {
//                        best.move = pair.move;
//                        best.score = evaluate(move, newBoard);
                        best = pair;
                    }
                //b.makeMove(new Move(r,c), myColour);
                }
        return best;
    }

//    function MINIMAX-MinPlayer(state) returns an (action, utility) pair input: state - current state of game
//     if terminal-Test(state) then return (null, utility(state)) best := (null, +∞)
//            for a in actions(state) do
//    value := MINIMAX-MaxPlayer(makeMove(state, a)).utility
//if value < best.utility then best := (a, value) return best

    public ActionUtilPair minimax_MIN(Color[][] board, Color myColour, int depth) {
//        int player;
//        if (myColour.equals(Color.white))
//            player = 1;
//        else
//            player=-1;
        ActionUtilPair best = new ActionUtilPair(new Move(-1,-1),board); //dummy move
        //Move move = new Move(-1,-1);
        //CUT-OFF-TEST
        //if ((b.getWinner()))
        //TERMINAL-TEST
        if ((b.getWinner() != null) || b.getMoveCount() ==64)/*terminal node*/ {
            //return action utility pair (null, +infinity) AKA (null, utility(state))
            best.move = null;
            //best.score = evaluate(best.move);
            best.state = board;
        }
        for (int r = 0; r<=b.ROWS; r++)
            for (int c=0; c<=b.COLS; c++)
                if (board[r][c] == null) {
                    Move newMove = new Move(r,c);
                    b.makeMove(newMove, myColour);
                    Color[][] newBoard = copyBoard(b.getPublicBoard());
                    Color newColour = b.getTurn();
                    depth++;
                    //Move move = chooseMove(newBoard, newColour);
                    ActionUtilPair pair = minimax_MAX(newBoard, newColour, depth);
                    if (evaluate(pair) > evaluate(new ActionUtilPair(best.move, newBoard))) {
//                        best.move = pair.move;
//                        best.score = evaluate(move, newBoard);
                        best = pair;
                    }
                    //b.makeMove(new Move(board[r],c), myColour);
                }
        return best;
    }

    public ActionUtilPair minimax(Color[][] board, Color myColour) {
        // fun minimax(n: node, d: int): int =
        //   if leaf(n) or depth=0 return evaluate(n)
        //   if n is a max node
        //      v := L
        //      for each child of n
        //         v' := minimax (child,d-1)
        //         if v' > v, v:= v'
        //      return v
        //   if n is a min node
        //      v := W
        //      for each child of n
        //         v' := minimax (child,d-1)
        //         if v' < v, v:= v'
        //      return v



        /*
        /** Minimax:  recursive algorithm to search entire game tree.
@param player:  1 (max player, first player) or -1 (min player)
@return:  the best move and its value
*
        Move minimax(int player) {
            Move best = new Move(-1, -1, -2*player); // dummy row and col
            if ((board.winner != 0) || (board.moves == 9)) { best.value = board.winner;
                return best;
            }
            for (int r = 0; r < TicTacToeGame.ROWS; r++)
                for (int c = 0; c < TicTacToeGame.COLS; c++)
// terminal state?
                    if (board.cell[r][c] == 0) {
                        board.makeMove(r, c, player);
                        Move move = minimax(-player);
                        if (player * move.value > player * best.value) {
                            best.value = move.value;
                            best.row = r;
                            best.col = c;
                        }
                        board.unmakeMove(r, c);
                    }
            return best; } // minimax()
         */




    return null;}
//    public int utility(Color[][] board, Move move) {
//        //calls evaluate
//        int utilityScore = evaluate(move);
//        return null;
//    }

    public int evaluate(ActionUtilPair pair) { //static evaluator
        //Usually expanding the entire game tree is infeasible because there are so many
        // possible states. The solution is to only search the tree to a specified depth.
        // The evaluate function (the static evaluator) is extended so it  returns a value
        // between L and W for game positions that are not final positions. For game positions
        // that look better for the current player, it returns larger numbers. When the depth
        // limit of the search is exceeded, the static evaluator is applied to the node as if
        // it were a leaf:

        /*  Heuristic:
                *  5 in a row = 9999, win
                *  4 in a row, 2 open ends: 9900, win
                *  4 in a row, 1 open end: 8000, threatening position
                *  3 in a row, 2 open ends: 5000
                *  3 in a row, 1 open end: 3000
                *  2 in a row, 2 open ends: 2000
                *  2 in a row, 1 open end: 1000
                *  4, 3, 2, 1 in a row, 2 closed ends: 0, no possible win
                */
       // utility()
        if (pair.move==null)
            return -10000;
        else if (consecutives(pair.state)==5) //five in a row
            return 9999;
//        else if (consecutives(pair.state)==4) //4 in a row, 2 open ends
//            return 9900;
//        else if (consecutives(pair.state)==44) //4 in a row, 1 open ends
//            return 8000;
//        else if (consecutives(pair.state)==3) //3 in a row, 2 open ends
//            return 5000;
//        else if (consecutives(board)) //3 in a row, 1 open ends
//            return 3000;
//        else if (consecutives(board)) //2 in a row, 2 open ends
//            return 2000;
//        else if (consecutives(board)) //2 in a row, 1 open end
//            return 1000;
//        else if (consecutives(board)) //1 in a row, 2 open ends
//            return 500;
//        else if (consecutives(board)) //1 in a row, 1 open end
//            return 200;
        else
            return 0;
    }
    //need to consider exception if r=7 for example
    public int consecutives(Color[][] board) {
        int consecutiveUp=0;
        int consecutiveDown=0;
        int consecutiveLeft=0;
        int consecutiveRight=0;
        int consecutiveNE=0;
        int consecutiveSE=0;
        int consecutiveSW=0;
        int consecutiveNW=0;
//iterate through each row and column
        for (int r = 0; r <=7; r++) {
            for (int c =0;c<=7;c++){
                try {
                    if (board[r][c]==null) {
                        //do nothing
                    }
                    else {
                        if (r==7){//if top row
                            consecutiveUp=0;//cant go up
                            consecutiveNW=0;//cant go diagonal left
                            consecutiveNE=0;//cant go diagonal right
                        }
                        else { //not top row
                            consecutiveUp++; //add 1 to consecutiveUP
                            if (board[r][c] != board[r+1][c])//if next row UP is NOT same color
                                //remove consecutiveUp point you just added. Now when it tries to
                                //evaluate from next row, consecutive UP
                                consecutiveUp--;
                        }
                        if (r==0){
                            consecutiveDown= 0;
                            consecutiveSE=0;
                            consecutiveSW=0;
                        }
                        else {
                            consecutiveDown++;
                            if (board[r][c] != board[r-1][c])//2 consecutive downwards
                            consecutiveDown--;
                        }
                        if (c==0) {
                            consecutiveLeft = 0;
                            consecutiveNW=0;
                            consecutiveSW=0;
                        }
                        else {
                            consecutiveLeft++;
                            if (board[r][c] != board[r][c-1])//2 consecutive leftwards
                                consecutiveLeft--;
                        }
                        if (c==7)
                            consecutiveRight = 0;
                        else {
                            consecutiveRight++;
                            if (board[r][c] != board[r][c+1])//2 consecutive rightwards
                                consecutiveRight--;
                        }



//                        if ((board[r][c] == board[r+1][c]) && (board[r][c]== board[r+2][c]))
//                            consecutiveUp = consecutiveUp+1;//3 consecutive upwards
                    }//end else
                    if (consecutiveDown >= 5 || consecutiveLeft >= 5 || consecutiveRight==5 || consecutiveUp ==5)
                        return 5;

                }//end try block
                catch (Exception e) {
                    System.out.println("exception caught");
                }//end catch block
            }
        }
        return -1;
    }

    public Color[][] copyBoard(Color[][] board) {
        Color[][] boardCopy = new Color[8][8];
        for (int r=0;r<=7;r++)
            for (int c=0;c<=7;c++)
                boardCopy[r][c]=board[r][c];
        return boardCopy;
    }
}