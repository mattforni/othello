package com.mattforni.games.othello.gui.square;

import java.awt.Color;
import java.awt.Graphics2D;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.players.Player.Side;
import com.mattforni.shapes.Ellipse;

/**
 * TODO re-doc
 * An extension of the square class, this SmartSquare represents a space on the gameboard that 
 * may contain pieces.  This class is a composite shape, containing both a background square that
 * represents the board square and an ellipse that represents a piece.  The class overrides a good
 * deal of its parent class's methods, redefining them to allow for pieces to be played and other
 * such actions reserved only for gameboard squares.  In addition, this class sports a copy constructor
 * which is used when the current gameboard is to be copied, thus creating a new similar gameboard
 * of new but similar SmartSquares.
 *
 * @author <Matthew Fornaciari>
 */

public class SmartSquare extends Square {
    private final static Color HIGHLIGHTED = new Color(51, 255, 51);
    private final static Color REGULAR = new Color(0, 225, 0);

    private final Ellipse piece;

    private Side side;

    public SmartSquare(final Gameboard gameboard, final int row, final int col) {
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
                // TODO this may be able to be removed in favor of checkSandwich
                Square square = gameboard.get(row, column);
                if(square.hasPiece() && square.getSide() != side) {
                    final int rInc = row - this.row;
                    final int cInc = column - this.column;
                    // TODO this may need to be fixed
                    square = gameboard.get(row + rInc, column + cInc);
                    if(square.checkSandwich(side, rInc, cInc)) { return true; }
                }
            }
        }

        return false;
    }

    @Override
    public final boolean checkSandwich(final Side side, final int rInc, final int cInc) {
        // increment the row and column so that we check the NEXT piece and not the same
        // if the piece is the same color then a sandwich has been formed, return true
        if(hasPiece() && getSide() == side) { return true; }

        // if the piece has been placed, but is of the opposite color, check the next
        if(hasPiece() && getSide() != side) {
            // TODO this may need to be fixed
            final Square square = gameboard.get(row + rInc, column + cInc);
            return square.checkSandwich(side, rInc, cInc);
        }

        // if the piece does not satisfy either of these conditions there is either
        // no piece on the square or this is a border piece in which case there is no sandwich
        return false;
    }

    @Override
    public final void flip(final Side side) {
        for(int row = this.row - 1; row <= this.row + 1; row++){
            for(int column = this.column - 1; column <= this.column + 1; column++){
                if(row == this.row && column == this.column) { continue; }
                final Square square = gameboard.get(row, column);
                if(square.hasPiece() && square.getSide() != side){
                    int rInc = (row-this.row), cInc = (column-this.column);
                    if(gameboard.get(row+rInc, column+cInc).checkSandwich(side, rInc, cInc)) {
                        square.flipSandwich(side, rInc, cInc);
                    }
                }
            }
        }
    }

    @Override
    public final void flipSandwich(final Side side, final int rInc, final int cInc) {
        Square square = gameboard.get(this.row, this.column);
        while(square.hasPiece() && square.getSide() != side) {
            square.setPiece(side);
            square = gameboard.get(square.getRow()+rInc, square.getColumn()+cInc);
        }
    }

    @Override
    public final void highlight() { square.setFillColor(HIGHLIGHTED); }

    @Override
    public final void paint(final Graphics2D brush){
        square.paint(brush);
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
}
