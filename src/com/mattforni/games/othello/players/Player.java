package com.mattforni.games.othello.players;

import java.awt.Color;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.gui.square.Square;

/**
 * {@link Player} acts as a fairly comprehensive superclass for both
 * {@link HumanPlayer} and {@link ComputerPlayer} by defining sane defaults for
 * publicly exposed methods. In addition this class defines a public enum
 * {@link Side} which is essentially a convenient wrapper for what color piece
 * should be displayed.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

public abstract class Player {
    protected final Side side;

    protected Player(final Side side) {
        this.side = side;
    }

    public final boolean attemptMove(final Gameboard gameboard, final Square square) {
        if (square.isValidMove(side)) {
            return makeMove(gameboard, square);
        }
        return false;
    }

    public final Side getSide() { return side; }

    public final boolean hasMoves(final Gameboard gameboard) {
        return gameboard.numMoves(this) > 0;
    }

    public boolean isHuman() { return false; }

    public boolean makeMove(final Gameboard gameboard) { return false; }

    public boolean makeMove(final Gameboard gameboard, final Square square) {
        if (!square.isBorder()) {
            if (gameboard.makeMove(side, square.getRow(), square.getColumn())) {
                if (isHuman()) { gameboard.hideMoves(); }
                return true;
            }
        }
        return false;
    }

    public void showMoves(final Gameboard gameboard) {
        gameboard.showMoves(this);
    }

    public enum Side {
        WHITE(Color.WHITE), BLACK(Color.BLACK);

        public final Color color;
        public final String string;

        private Side(final Color color) {
            this.color = color;
            final char[] chars = name().toLowerCase().toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            this.string = String.valueOf(chars);
        }

        public final Side getOpponent() {
            switch (this) {
                case BLACK:
                    return Side.WHITE;
                case WHITE:
                    return Side.BLACK;
            }
            return null;
        }

        @Override
        public final String toString() {
            return string;
        }
    }
}