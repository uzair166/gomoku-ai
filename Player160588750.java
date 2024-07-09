import java.awt.Color;

/******* RULES *******/
/*
*  Two platers (white and black) take turns at placing a stone of his/her colour on an
*  unoccupied square on the board (white moves first). The first player to complete a continuous
*  horizontal,vertical or diagonal line of 5 or more stones of his/her colour is the winner
*  (scoring 2 points). The loser scores 0. If all squares are occupied and neither player has
*  won, then each player gets 1 point.
*  Your program will be given a time limit of 10 seconds per move (on the ITL computers). Any
*  program which exceeds this limit will immediately forfeit the game to its opponent.
*  Similarly any program which raises an exception or makes an illegal move (out of range or
*  already occupied) will lose immediately. There are no other restrictions on moves (gomoku
*  experts may be aware that some tournaments have further restrictions).
*
*
*  Initial state: empty board
*
*  Successor function: list of (legal move, resulting state) pairs from current state
*
*  Terminal test: Is the game finished? Has either player connected 5 or more stones, or is
*  board full?
*
*  Utility function: Gives numerical value of terminal states
*  • E.g. MAX wins (+1), loses (-1) or draws (0) in noughts and crosses (tic-tac-toe)
*  Utility fn:
*  MAX win = 2
*  MAX loss = 0
*  MAX draw = 1
*
*  Heuristic:
*  5 in a row = 9999, win
*  4 in a row, 2 open ends: 9900, win
*  4 in a row, 1 open end: 8000, threatening position
*  3 in a row, 2 open ends: 5000
*  3 in a row, 1 open end: 3000
*  2 in a row, 2 open ends: 2000
*  2 in a row, 1 open end: 1000
*  4, 3, 2, 1 in a row, 2 closed ends: 0, no possible win
*
*
*
*
*  Each player uses a search tree to determine next move
*  if n is a terminal node, MINIMAX-VALUE(n) = UTILITY(n)
*  if n is a MAX node, MINIMAX-VALUE(n) = maxs ∈ successors(n) MINIMAX-VALUE(s)
*  if n is a MIN node, MINIMAX-VALUE(n) = mins ∈ successors(n) MINIMAX-VALUE(s)
*
*  initial branching factor: 64
*  general branching factor: 64-n if n is number of stones currently on board (state)
*
*
*  The state space search is through the different states of the board. There are a lot of moves, since you can place a
*  stone anywhere unoccupied. Each state can be represented as a e.g. 8x8 matrix, with 3 values -- white, black, or
*  unoccupied. With a 8x8 board, there are therefore 3 ^ 64 possible board states.
*  From any board state, the number of moves is the number of unoccupied vertices. You can place a stone on any of these
*  vertices. You can only play your own color. So, at most there are 64 possible moves .. 64 for the first move, 63 for
*  the second, and so on. So you can search to depth 5 reasonably, and possibly more .. not too bad.
*
*  The proper representation is, as mentioned, a 2D matrix -- this can just be a 2D array of ints, with values e.g. 0 for
*  unoccupied, 1 for white, 2 for black. ... int[8,8].
*  Your evaluation function doesn't sound very good. Instead, I would give points for the following:
*  -- get 5 in a row -- basically give it the max score for this one, since it's a win -- 4 in a row with 2 open ends
*  -- also max score, since opponent can't block you from getting 5. -- 4 in a row with 1 open end -- still a very
*  threatenning position, since opponent has to play in one spot to block. -- 3 in a row with 2 open ends -- very high
*  score again --- 4, 3, 2, 1 with both closed ends -- 0, since can't ever make 5 in a row.
*  and so on.
*  Then, you just apply the standard minimax algorithm -- i.e. alpha beta pruning -- it would be exactly the same as
*  chess, but you have a different state space generator and evaluation function.
*
* */
class Player160588750 extends GomokuPlayer {
    Player160588750() {
    }

//    public Move(int row, int col) {
//        this.row = row;
//        this.col = col;
//    }
    public Move chooseMove(Color[][] board, Color myColour) {
//        int row;
//        int col;
//        do {
//            row = (int)(Math.random() * 8.0D);
//            col = (int)(Math.random() * 8.0D);
//        } while(board[row][col] != null);
        //player: calls minimax algorithm to search complete game tree
        Move move = minimax(board, myColour);
//        row = move.row;
//        col move.col;
        System.out.println("Player160588750:  " + move);

        return move;
    }



//public void makeMove() {
//    game.copy(board);
//    Move move = minimax(Player160588750);
//    System.out.println("Computer:  " + move);
//    game.makeMove(move.row, move.col, computerPlayer);
//} // makeMove()
    /** Minimax:  recursive algorithm to search entire game tree.
     @param player:  1 (max player, first player) or -1 (min player)
     change to: 2 (max player, first player) or 0 (min player) or 1 (draw)
     @return:  the best move and its value
     */
    Move minimax(Color[][] board, Color myColour) {
        Color opponent;
        if (myColour.equals(Color.white))
            opponent = Color.black;
        else if (myColour.equals(Color.black))
            opponent = Color.white;
        Move best = new Move(-1, -1); // dummy row and col
        if ((GomokuBoard.winner != 0) || (GomokuBoard.getMoveCount() == 64)) {  // terminal state?
            best.value = GomokuBoard.getWinner();
            return best;
        }
//        for (int r = 0; r < GomokuBoard.ROWS; r++)
//            for (int c = 0; c < GomokuBoard.COLS; c++)
        for (int r = 0; r < board.ROWS; r++)
            for (int c = 0; c < board.COLS; c++)
                if (board[r][c] == 0) {
                    board.makeMove(minimax(board, myColour));
                    Move move = minimax(board, opponent);
                    if (myColour * move.value > myColour * best.value) {
                        best.value = move.value;
                        best.row = r;
                        best.col = c;
                    }
                    board.unmakeMove(r, c);
                }
        return best;
    } // minimax()



    // To copy the board you can create a method that creates a new Color[][] array, loops through (2 loops) and copys
    // the current board state into it. e.g boardCopy[a][b]=board[a][b] and returns that newly created array. Thats how
    // I done it.
//function MINIMAX-MaxPlayer(state) returns an (action, utility) pair
//    input: state - current state of game
//if terminal-Test(state) then return (null, utility(state))
//best := (null, -∞)
//for a in actions(state) do // actions() returns all legal moves from a state
//    value := MINIMAX-MinPlayer(makeMove(state, a)).utility
//    if value > best.utility then best := (a, value)
// return best




}//END class
