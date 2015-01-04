package com.mattforni.games.othello.players;

import java.awt.Color;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.gui.square.Square;

/**
 * TODO re-doc
 * The player interface defines four methods that must then be defined by both of the classe
 * that extend it.  Each player, whether human or computer, must know how to begin their move,
 * make the move, know whether they are human, and know what color they are representing.
 * Thus, the big four methods.
 *
 * @author <Matthew Fornaciari>
 */

public abstract class Player {
    protected final Side side;

    protected Player(final Side side) {
        this.side = side;
    }

    public abstract boolean isHuman();

    public final boolean attemptMove(final Gameboard gameboard, final Square square) {
        if (square.isValidMove(side)) {
            return makeMove(gameboard, square);
        }
        return false;
    }

    public int getIntel() { return -1; }

    public final Side getSide() { return side; }

    public final boolean hasMoves(final Gameboard gameboard) {
        return gameboard.numMoves(this) > 0;
    }

    public void makeMove(final Gameboard gameboard) {}

    public boolean makeMove(final Gameboard gameboard, final Square square) {
        if (!square.isBorder()) {
            gameboard.makeMove(side, square.getRow(), square.getColumn());
            return true;
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

        public final String toString() {
            return string;
        }
    }
}