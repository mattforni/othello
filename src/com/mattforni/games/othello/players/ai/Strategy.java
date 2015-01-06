package com.mattforni.games.othello.players.ai;

/**
 * {@link Strategy} defines the contract to which all AI algorithms must conform.
 * As an abstract class it also defines the static class {@link Move} which
 * essentially pairs a {@link Square} with a value for determining which move
 * will be the best according to the active AI algorithm.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.gui.square.Square;
import com.mattforni.games.othello.players.Player.Side;

public abstract class Strategy {
    public abstract Move getBestMove(Gameboard gameboard, Side side);

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
