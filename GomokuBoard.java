//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

class GomokuBoard extends Canvas {
    public static final int ROWS = 8;
    public static final int COLS = 8;
    static final int SIZE = 80;
    private static final int[][] allRuns = new int[][]{{0, 32, 64, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {0, 1, 36, 65, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {0, 1, 2, 40, 66, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {0, 1, 2, 3, 44, 67, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {0, 1, 2, 3, 48, 80, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {1, 2, 3, 52, 81, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {2, 3, 56, 82, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {3, 60, 83, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {4, 32, 33, 68, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {4, 5, 36, 37, 64, 69, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {4, 5, 6, 40, 41, 65, 70, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {4, 5, 6, 7, 44, 45, 66, 71, 80, -1, -1, -1, -1, -1, -1, -1}, {4, 5, 6, 7, 48, 49, 67, 81, 84, -1, -1, -1, -1, -1, -1, -1}, {5, 6, 7, 52, 53, 82, 85, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {6, 7, 56, 57, 83, 86, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {7, 60, 61, 87, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {8, 32, 33, 34, 72, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {8, 9, 36, 37, 38, 68, 73, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {8, 9, 10, 40, 41, 42, 64, 69, 74, 80, -1, -1, -1, -1, -1, -1}, {8, 9, 10, 11, 44, 45, 46, 65, 70, 75, 81, 84, -1, -1, -1, -1}, {8, 9, 10, 11, 48, 49, 50, 66, 71, 82, 85, 88, -1, -1, -1, -1}, {9, 10, 11, 52, 53, 54, 67, 83, 86, 89, -1, -1, -1, -1, -1, -1}, {10, 11, 56, 57, 58, 87, 90, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {11, 60, 61, 62, 91, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {12, 32, 33, 34, 35, 76, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {12, 13, 36, 37, 38, 39, 72, 77, 80, -1, -1, -1, -1, -1, -1, -1}, {12, 13, 14, 40, 41, 42, 43, 68, 73, 78, 81, 84, -1, -1, -1, -1}, {12, 13, 14, 15, 44, 45, 46, 47, 64, 69, 74, 79, 82, 85, 88, -1}, {12, 13, 14, 15, 48, 49, 50, 51, 65, 70, 75, 83, 86, 89, 92, -1}, {13, 14, 15, 52, 53, 54, 55, 66, 71, 87, 90, 93, -1, -1, -1, -1}, {14, 15, 56, 57, 58, 59, 67, 91, 94, -1, -1, -1, -1, -1, -1, -1}, {15, 60, 61, 62, 63, 95, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {16, 32, 33, 34, 35, 80, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {16, 17, 36, 37, 38, 39, 76, 81, 84, -1, -1, -1, -1, -1, -1, -1}, {16, 17, 18, 40, 41, 42, 43, 72, 77, 82, 85, 88, -1, -1, -1, -1}, {16, 17, 18, 19, 44, 45, 46, 47, 68, 73, 78, 83, 86, 89, 92, -1}, {16, 17, 18, 19, 48, 49, 50, 51, 64, 69, 74, 79, 87, 90, 93, -1}, {17, 18, 19, 52, 53, 54, 55, 65, 70, 75, 91, 94, -1, -1, -1, -1}, {18, 19, 56, 57, 58, 59, 66, 71, 95, -1, -1, -1, -1, -1, -1, -1}, {19, 60, 61, 62, 63, 67, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {20, 33, 34, 35, 84, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {20, 21, 37, 38, 39, 85, 88, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {20, 21, 22, 41, 42, 43, 76, 86, 89, 92, -1, -1, -1, -1, -1, -1}, {20, 21, 22, 23, 45, 46, 47, 72, 77, 87, 90, 93, -1, -1, -1, -1}, {20, 21, 22, 23, 49, 50, 51, 68, 73, 78, 91, 94, -1, -1, -1, -1}, {21, 22, 23, 53, 54, 55, 69, 74, 79, 95, -1, -1, -1, -1, -1, -1}, {22, 23, 57, 58, 59, 70, 75, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {23, 61, 62, 63, 71, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {24, 34, 35, 88, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {24, 25, 38, 39, 89, 92, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {24, 25, 26, 42, 43, 90, 93, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {24, 25, 26, 27, 46, 47, 76, 91, 94, -1, -1, -1, -1, -1, -1, -1}, {24, 25, 26, 27, 50, 51, 72, 77, 95, -1, -1, -1, -1, -1, -1, -1}, {25, 26, 27, 54, 55, 73, 78, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {26, 27, 58, 59, 74, 79, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {27, 62, 63, 75, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {28, 35, 92, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {28, 29, 39, 93, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {28, 29, 30, 43, 94, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {28, 29, 30, 31, 47, 95, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {28, 29, 30, 31, 51, 76, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {29, 30, 31, 55, 77, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {30, 31, 59, 78, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}, {31, 63, 79, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}};
    private Color[][] board = new Color[8][8];
    private Color[][] publicBoard = new Color[8][8];
    private int[] whiteRuns = new int[96];
    private int[] blackRuns = new int[96];
    private Color turn;
    private Color winner;
    private int count;

    public GomokuBoard() {
        this.init();
    }

    public void init() {
        this.turn = Color.white;
        this.winner = null;
        this.count = 0;

        int var1;
        for(var1 = 0; var1 < 8; ++var1) {
            for(int var2 = 0; var2 < 8; ++var2) {
                this.board[var1][var2] = null;
            }
        }

        for(var1 = 0; var1 < 96; ++var1) {
            this.whiteRuns[var1] = this.blackRuns[var1] = 0;
        }

    }

    public String makeMove(Move var1, Color var2) {
        Color var4 = this.turn == Color.white ? Color.black : Color.white;
        if (this.winner != null) {
            return "ERROR: Game is already over";
        } else if (this.turn != var2) {
            return "ERROR: It is not your turn";
        } else if (var1 == null) {
            this.winner = var4;
            return "Exception or null Move";
        } else {
            int var5 = var1.row;
            int var6 = var1.col;
            if (var6 >= 0 && var6 < 8 && var5 >= 0 && var5 < 8) {
                if (this.board[var5][var6] != null) {
                    this.winner = var4;
                    return "Move in occupied cell";
                } else {
                    this.board[var5][var6] = this.turn;
                    this.turn = var4;
                    ++this.count;
                    int var3;
                    if (this.turn == Color.black) {
                        for(var3 = 0; allRuns[var5 * 8 + var6][var3] != -1; ++var3) {
                            if (++this.whiteRuns[allRuns[var5 * 8 + var6][var3]] == 5) {
                                this.winner = Color.white;
                                return "White wins";
                            }
                        }
                    } else {
                        for(var3 = 0; allRuns[var5 * 8 + var6][var3] != -1; ++var3) {
                            if (++this.blackRuns[allRuns[var5 * 8 + var6][var3]] == 5) {
                                this.winner = Color.black;
                                return "Black wins";
                            }
                        }
                    }

                    for(var3 = 0; var3 < 96 && this.whiteRuns[var3] != 0 && this.blackRuns[var3] != 0; ++var3) {
                        ;
                    }

                    if (this.count != 64 && var3 != 96) {
                        return this.turn == Color.white ? "White to play" : "Black to play";
                    } else {
                        this.winner = Color.blue;
                        return "Game drawn";
                    }
                }
            } else {
                this.winner = var4;
                return "Move out of range";
            }
        }
    }

    public Color getWinner() {
        return this.winner;
    }

    public Color getTurn() {
        return this.turn;
    }

    public int getMoveCount() {
        return this.count;
    }

    public Color[][] getPublicBoard() {
        for(int var1 = 0; var1 < 8; ++var1) {
            for(int var2 = 0; var2 < 8; ++var2) {
                this.publicBoard[var1][var2] = this.board[var1][var2];
            }
        }

        return this.publicBoard;
    }

    public void paint(Graphics var1) {
        for(int var2 = 0; var2 < 8; ++var2) {
            for(int var3 = 0; var3 < 8; ++var3) {
                if ((var3 + var2) % 2 == 0) {
                    var1.setColor(new Color(200, 50, 100));
                } else {
                    var1.setColor(new Color(50, 200, 100));
                }

                var1.fillRect(var3 * 80, var2 * 80, 80, 80);
                if (this.board[var2][var3] != null) {
                    var1.setColor(this.board[var2][var3]);
                    var1.fillOval(var3 * 80 + 10, var2 * 80 + 10, 60, 60);
                }
            }
        }

    }

    public void update(Graphics var1) {
        this.paint(var1);
    }
}
