package com.mattforni.games.othello.gui.square;

import java.awt.Color;
import java.awt.Graphics2D;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.players.Player.Side;
import com.mattforni.shapes.Ellipse;

/**
 * {@link PlayableSquare} is an intelligent subclass of {@link Square} which
 * encapsulates much of the game play logic. {@link PlayableSquare} objects can
 * contain a piece and are able to determine whether or not they are a valid move
 * for a given {@link Side}. When the {@link #flip(Side)} method is called this
 * object will evaluate the {@link Gameboard} and will flip all necessary
 * {@link Square} instances.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

public class PlayableSquare extends Square {
    private final static Color HIGHLIGHTED = new Color(51, 255, 51);
    private final static Color REGULAR = new Color(0, 225, 0);

    private final Ellipse piece;

    private Side side;

    public PlayableSquare(final Gameboard gameboard, final int row, final int col) {
        super(gameboard, row, col);
        square.setFillColor(REGULAR);
        piece = createPiece();
    }

    @Override
    public final Side getSide () { return side; }

    @Override
    public final boolean hasPiece() { return side != null; }

    public void setPiece(final Side side) {
        this.side = side;
        piece.setFillColor(side.color);
        piece.setVisible(true);
    }

    @Override
    public final boolean isBorder() { return false; }

    @Override
    public boolean isValidMove(final Side side) {
        // If this square is a border or already has a piece it is not valid
        if(isBorder() || hasPiece()) { return false; }

        // Check all adjacent squares for a valid 'sandwich'
        for(int row = this.row-1; row <= this.row+1; row++) {
            for(int column = this.column-1; column <= this.column+1; column++) {
                // No need to evaluate the square against itself
                if(row == this.row && column == this.column) { continue; }

                // If there is a valid 'sandwich' in any direction return true
                if (hasSandwich(side, row, column)) { return true; }
            }
        }

        return false;
    }

    @Override
    public final void flip(final Side side) {
        // Flip the current square 
        setPiece(side);

        // Check each diagonal for a 'sandwhich' and flip if necessary
        for(int row = this.row-1; row <= this.row+1; row++) {
            for(int column = this.column-1; column <= this.column+1; column++) {
                // No need to evaluated the square against itself
                if(row == this.row && column == this.column) { continue; }

                // If there is a valid 'sandwich' in this direction flip it
                if (hasSandwich(side, row, column)) {
                    flip(side, row, column);
                }
            }
        }
    }

    @Override
    public final void highlight() { square.setFillColor(HIGHLIGHTED); }

    @Override
    public final void paint(final Graphics2D brush){
        super.paint(brush);
        piece.paint(brush);
    }

    @Override
    public final void unhighlight() { square.setFillColor(REGULAR); }

    /* Private methods */
    private Ellipse createPiece() {
        final Ellipse piece = new Ellipse(gameboard,
                column*SIZE+INDENT, row*SIZE+INDENT,
                SIZE-2*INDENT, SIZE-2*INDENT);
        piece.setVisible(false);
        return piece;
    }

    private void flip(final Side side, int nextRow, int nextColumn) {
        final int rowDirection = nextRow - this.row;
        final int columnDirection = nextColumn - this.column;

        // Flip all pieces in the given direction
        Square next = gameboard.get(nextRow, nextColumn);
        while(next.hasPiece() && next.getSide() != side) {
            next.setPiece(side);
            nextRow += rowDirection;
            nextColumn += columnDirection;
            next = gameboard.get(nextRow, nextColumn);
        }
    }

    private boolean hasSandwich(final Side side, int nextRow, int nextColumn) {
        // If the next piece is not of the opposite color there is no 'sandwich'
        Square next = gameboard.get(nextRow, nextColumn);
        if (next.hasPiece() && next.getSide() != side) {
            final int rowDirection = nextRow - this.row;
            final int columnDirection = nextColumn - this.column;

            nextRow += rowDirection;
            nextColumn += columnDirection;
            next = gameboard.get(nextRow, nextColumn);
            // If we get to a border or an empty square there is no 'sandwich'
            while (next.hasPiece()) {
                // If we find a piece with the same color there is a 'sandwich'
                if (next.getSide() == side) { return true; }
                nextRow += rowDirection;
                nextColumn += columnDirection;
                next = gameboard.get(nextRow, nextColumn);
            }
        }

        return false;
    }
}
