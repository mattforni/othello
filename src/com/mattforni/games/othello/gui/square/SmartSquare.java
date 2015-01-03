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
    private final static Color ILLUMINATED = new Color(51, 255, 51);
    private final static Color UNILLUMINATED = new Color(0, 225, 0);

    private final Ellipse piece;

    private Side side;

    public SmartSquare(final Gameboard gameboard, final int row, final int col) {
        super(gameboard, row, col);
        // Creates the square piece of the board
        square.setFillColor(UNILLUMINATED);

        // Creates the Othello piece and then makes it invisible
        piece = new Ellipse(gameboard, column*SIZE+INDENT, 
                row*SIZE+INDENT, SIZE-2*INDENT, SIZE-2*INDENT);
        piece.setVisible(false);
    }

    @Override
    public final Side belongsTo () { return side; }

    @Override
    public final boolean hasPiece() { return piece.isVisible(); }

    public void setPiece(Side side){
        this.side = side;
        piece.setFillColor(side.color);
        piece.setVisible(true);
    }

    public boolean isVisible(){
        return piece.isVisible();
    }

    public int getRow() { return row; }

    public int getColumn() { return column; }

    public boolean isValidMove(final Side side) {
        // makes sure that i am NOT checking pieces that already exist & it is not a border piece
        if(this.isBorder() || piece.isVisible()){
            return false;
        } else {
            // loops from one less than this piece's col and row to one plus
            for(int row = this.row - 1; row <= this.row + 1; row++) {
                for(int column = this.column - 1; column <= this.column + 1; column++) {
                    // if it is the piece itself we want to skip this square and continue on
                    if(row == this.row && column == this.column) { continue; }
                    final Square square = gameboard.get(row, column);
                    if(square.isVisible() && square.belongsTo() != side) {
                        int rInc = (row-this.row), cInc = (column-this.column);
                        if(gameboard.get(row+rInc, column+cInc).checkSandwich(side, rInc, cInc)){
                            return true; 
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkSandwich(final Side side, int rInc, int cInc) {
        // increment the row and column so that we check the NEXT piece and not the same
    	// if the piece is the same color then a sandwich has been formed, return true
        if(this.isVisible() && this.belongsTo() == side) {
            return true;
        }
        // if the piece has been placed, but is of the opposite color, check the next
        if(this.isVisible() && this.belongsTo() != side) {
            final Square square = gameboard.get(row+rInc, column+cInc);
            return square.checkSandwich(side, rInc, cInc);
        }
        // if the piece does not satisfy either of these conditions there is either
        // no piece on the square or this is a border piece in which case there is no sandwich
        return false;
    }

    public void flip(Side side){
        for(int row = this.row - 1; row <= this.row + 1; row++){
            for(int column = this.column - 1; column <= this.column + 1; column++){
                if(row == this.row && column == this.column) { continue; }
                final Square square = gameboard.get(row, column);
                if(square.isVisible() && square.belongsTo() != side){
                    int rInc = (row-this.row), cInc = (column-this.column);
                    if(gameboard.get(row+rInc, column+cInc).checkSandwich(side, rInc, cInc)) {
                        square.flipSandwich(side, rInc, cInc);
                    }
                }
            }
        }
    }

    public void flipSandwich(Side side, int rInc, int cInc){
        Square square = gameboard.get(this.row, this.column);
        while(square.belongsTo() != side) {
            square.setPiece(side);
            square = gameboard.get(square.getRow()+rInc, square.getColumn()+cInc);
        }
    }

    public void paint(final Graphics2D brush){
        square.paint(brush);
        piece.paint(brush);
    }

    public void illuminate() { square.setFillColor(ILLUMINATED); }

    public void unilluminate() { square.setFillColor(UNILLUMINATED); }
}
