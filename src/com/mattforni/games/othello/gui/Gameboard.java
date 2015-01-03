package com.mattforni.games.othello.gui;

import static com.mattforni.games.othello.gui.square.Square.SIZE;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.mattforni.games.othello.gui.square.BorderSquare;
import com.mattforni.games.othello.gui.square.SmartSquare;
import com.mattforni.games.othello.gui.square.Square;
import com.mattforni.games.othello.players.Player;
import com.mattforni.games.othello.players.Player.Side;

/**
 * The Gameboard is quite possibly the most important class in the entire project, for it is in this class
 * that both the logical and visual containment are created and executed.  This class sports a copy constructor
 * that allows for the copying of a given gameboard, a very important method for the Minimax class.  It also has
 * several methods that both access and mutate several attributes of the squares it contains.  This class
 * keeps track of basically everything needed to play the game.  Thank god for the Gameboard!
 *
 * @author <Matthew Fornaciari>
 */

@SuppressWarnings("serial")
public class Gameboard extends JPanel {
    public static final int ROWS = 10;
    public static final int COLS = 10;

    private final Square[][] squares;
    private final List<Square> illuminated;

    public Gameboard() {
        super();

        this.squares = new Square[ROWS][COLS];
        this.illuminated = new ArrayList<Square>();
        newGame();

        this.setSize(new Dimension(ROWS*SIZE, COLS*SIZE));
        this.setPreferredSize(new Dimension(ROWS*SIZE, COLS*SIZE));
        this.setVisible(true);
    }

    public Gameboard(final Gameboard gameboard) {
        this();

        for (int row = 0; row < ROWS; row ++) {
            for (int column = 0; column < COLS; column++) {
                final Square square = gameboard.get(row, column);
                if (square.hasPiece()) {
                    squares[row][column].setPiece(square.belongsTo());
                }
            }
        }
    }

    public final Square get(final int row, final int column) {
        if (row >= ROWS || column >= COLS) { return null; }
        return squares[row][column];
    }

    public final void newGame(){
        for(int row=0; row<ROWS; row++){
            for(int col=0; col<COLS; col++){
                if(row==0 || row==(ROWS-1) || col==0 || col==(COLS-1)){
                    squares[row][col] = new BorderSquare(this, row, col);
                } else{
                    squares[row][col] = new SmartSquare(this, row, col);
                   if((col==4 && row==4) || (col==5 && row==5)){
                       squares[row][col].setPiece(Side.BLACK);
                   }
                   if((col==4 && row==5) || (col==5 && row==4)){
                       squares[row][col].setPiece(Side.WHITE);
                    }
                }
            }
        }
        this.repaint();
    }

    public int numMoves(final Player player){
        int moves = 0;
        for(int row = 0; row < ROWS; row++){
            for(int col=0; col < COLS; col++){
                if(squares[row][col].isValidMove(player.getSide())) { moves++; }
            }
        }
        return moves;
    }

    public int getColorCount(Side side){
        int count = 0;
        for(int row=0; row<ROWS; row++){
            for(int col=0; col<COLS; col++){
        if(squares[row][col].belongsTo()==side && squares[row][col].isVisible()){
                    count++;
                }
            }
        }
        return count;
    }

    public void illuminate(final Player player) {
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                final Square square = squares[row][col];
                if(square.isValidMove(player.getSide())){
                    square.illuminate();
                    illuminated.add(square);
                }
            }
        }
        this.repaint();
    }

    public final void makeMove(final Side side, final int row, final int column) {
        squares[row][column].setPiece(side);
        squares[row][column].flip(side);
        this.repaint();
    }

    public void unilluminate(){
        if (illuminated.isEmpty()) { return; }
        for (final Square square : illuminated) {
            square.unilluminate();
        }
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        final Graphics2D brush = (Graphics2D) g;
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                squares[row][col].paint(brush);
            }
        }
    }
}
