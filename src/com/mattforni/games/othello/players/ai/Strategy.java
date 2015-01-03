package com.mattforni.games.othello.players.ai;

/**
 * TODO re-doc
 * The Strategy interface is fairly useless considering we have but one strategy of AI movement.
 * However, is lays out the plan for Minimax and any strategy yet to come, YAY!
 *
 * @author <Matthew Fornaciari>
 */

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.gui.square.Square;
import com.mattforni.games.othello.players.Player;
import com.mattforni.games.othello.players.Player.Side;

public abstract class Strategy {
    public abstract Move getBestMove(Gameboard gameboard, Player player);
    public abstract int boardEval(Gameboard gameboard, Side side);

    public static class Move {
        private final Square square;
        private final int value;

        public Move(final Square square, final int value){
            this.square = square;
            this.value = value;
        }

        public Move(final Square square){ this(square, -1); }

        public final int getColumn() { return square.getColumn(); }

        public final int getRow() { return square.getRow(); }

        public final Square getSquare() { return square; }

        public final int getValue() { return value; }
    }
}
