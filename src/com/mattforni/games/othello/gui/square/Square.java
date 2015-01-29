package com.mattforni.games.othello.gui.square;

import java.awt.Graphics2D;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.players.Player.Side;
import com.mattforni.graphics.shape.Rectangle;

/**
 * {@link Square} acts as a simple superclass for both {@link BorderSquare} and
 * {@link PlayableSquare} by defining sane defaults for publicly exposed methods.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

public abstract class Square {
    public static final int INDENT = 5;
    public static final int SIZE = 50;

    protected final Gameboard gameboard;
    protected final int row, column;
    protected final Rectangle square;

    public Square(final Gameboard gameboard, final int row, final int column) {
        this.gameboard = gameboard;
        this.row = row;
        this.column = column;
        this.square = new Rectangle(gameboard, column*SIZE, row*SIZE, SIZE, SIZE);
    }

    public final int getColumn() { return column; }

    public final int getRow() { return row; }

    public Side getSide() { return null; }

    public boolean hasPiece() { return false; }

    public void highlight() {}

    public boolean isValidMove(final Side side) { return false; }

    public void paint(final Graphics2D brush) { square.paint(brush); }

    public void setPiece(final Side side) {}

    public boolean isBorder() { return true; }

    public void flip(final Side side) {}

    public void unhighlight() {}
}
