package com.mattforni.games.othello.gui.square;

import java.awt.Color;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.players.Player.Side;

/**
 * This is the much more primitive of the two square children subclasses.  It simply returns a true
 * for is bordersquare and is thus ruled out as ever being a valid move.  As a result this square does 
 * NOT have an ellipse istnace variable
 *
 * @author <Matthew Fornaciari>
 */

public class BorderSquare extends Square {
    public BorderSquare(Gameboard dp, int row, int col) {
        super(dp, row, col);
        square.setFillColor(Color.black);
    }

    @Override
    public final boolean isBorder(){ return true; }

    @Override
    public final boolean isValidMove(final Side side) { return false; }
}
