//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Color;

class HumanPlayer extends GomokuPlayer {
    public Move clicked = null;

    HumanPlayer() {
    }

    public synchronized Move chooseMove(Color[][] var1, Color var2) {
        while(true) {
            this.clicked = null;

            try {
                this.wait();
            } catch (InterruptedException var4) {
                ;
            }

            Move var3 = this.clicked;
            if (var3 != null && var3.row >= 0 && var3.row < 8 && var3.col >= 0 && var3.col < 8 && var1[var3.row][var3.col] == null) {
                return var3;
            }

            System.err.println("Illegal move: " + var3);
        }
    }
}
