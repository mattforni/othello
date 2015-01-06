package com.mattforni.games.othello.gui.square;

import java.awt.Graphics2D;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.players.Player.Side;
import com.mattforni.shapes.Rectangle;

/**
 * TODO re-doc
 * Square is the superclass for the two type of sqares that are used to create the gameboard.  There 
 * are various methods that are defined throughout this class that are the defaults for any subclass
 * that extends it; however, for the special squares, the smart squares, the vast majority of these
 * methods are overridden.  In addition there is a paint method that allows for the squares, and if 
 * they are smartsquares, the pieces, to be painted and visible to the players.
 *
 * @author <Matthew Fornaciari>
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

    public boolean checkSandwich(final Side side, final int rInc, final int cInc) { return false; }

    public void flip() {}

    public void flipSandwich(final Side side, final int rInc, final int cInc) {}

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
