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
 * The {@link Gameboard} is quite possibly the most important class in the
 * entire application since it is response for keeping track of the logical
 * representation of the game. While not directly responsible for rendering
 * the board in the gui, it does also define how and when the two dimensional
 * array of {@link Square} objects should display themselves. This class is the
 * bread and butter of the logic behind Othello.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

@SuppressWarnings("serial")
public class Gameboard extends JPanel {
    private static final int DEFAULT_ROWS = 10;
    private static final int DEFAULT_COLUMNS = 10;

    private final int rows;
    private final int columns;
    private final List<Square> moves;
    private final Square[][] squares;

    public Gameboard() throws IllegalArgumentException {
        this(DEFAULT_ROWS, DEFAULT_COLUMNS);
    }

    public Gameboard(final int rows, final int columns) throws IllegalArgumentException {
        super();

        // If either board dimension is not even an exception is thrown
        if (rows % 2 != 0 || columns % 2 != 0) {
            throw new IllegalArgumentException("Board dimensions must be even");
        }

        // If the dimensions are not equal an exception is thrown
        if (rows != columns) {
            throw new IllegalArgumentException("The gameboard must be square");
        }

        this.rows = rows;
        this.columns = columns;

        this.moves = new ArrayList<Square>();
        this.squares = new Square[rows][columns];
        newGame();

        this.setSize(new Dimension(rows*SIZE, columns*SIZE));
        this.setPreferredSize(new Dimension(rows*SIZE, columns*SIZE));
        this.setVisible(true);
    }

    public Gameboard(final Gameboard gameboard) throws IllegalArgumentException {
        this(gameboard.numRows(), gameboard.numColumns());

        for (int row = 0; row < rows; row ++) {
            for (int column = 0; column < columns; column++) {
                final Square square = gameboard.get(row, column);
                if (square.hasPiece()) {
                    squares[row][column].setPiece(square.getSide());
                }
            }
        }
    }

    public final int count(final Side side) {
        int count = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (squares[row][column].getSide() == side) { count++; }
            }
        }
        return count;
    }

    public final Square get(final int row, final int column) {
        // If either row or column are out of bounds return null
        if (row < 0 || row >= rows || column < 0 || column >= columns) { return null; }
        return squares[row][column];
    }

    public final void hideMoves() {
        if (moves.isEmpty()) { return; }
        for (final Square square : moves) { square.unilluminate(); }
        this.repaint();
    }

    public final void makeMove(final Side side, final int row, final int column) {
        squares[row][column].setPiece(side);
        squares[row][column].flip(side);
        this.repaint();
    }

    public final void newGame() {
        final int half = rows / 2;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (isBorder(row, column)) {
                    squares[row][column] = new BorderSquare(this, row, column);
                } else {
                   squares[row][column] = new SmartSquare(this, row, column);
                   if ((column == half-1 && row == half-1) ||
                           (column == half && row == half)) {
                       squares[row][column].setPiece(Side.BLACK);
                   }
                   if((column == half-1 && row == half) ||
                           (column == half && row == half-1)) {
                       squares[row][column].setPiece(Side.WHITE);
                    }
                }
            }
        }
        this.repaint();
    }

    public final int numColumns() { return columns; }

    public final int numMoves(final Player player){
        int moves = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if(squares[row][column].isValidMove(player.getSide())) { moves++; }
            }
        }
        return moves;
    }

    public final int numRows() { return rows; }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        final Graphics2D brush = (Graphics2D) g;
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {
                squares[row][col].paint(brush);
            }
        }
    }

    public final void showMoves(final Player player) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                final Square square = squares[row][column];
                if(square.isValidMove(player.getSide())) {
                    square.illuminate();
                    moves.add(square);
                }
            }
        }
        this.repaint();
    }

    /* Private methods */
    private boolean isBorder(final int row, final int column) {
        return row == 0 || row == rows-1 || column == 0 || column == columns-1;
    }
}
