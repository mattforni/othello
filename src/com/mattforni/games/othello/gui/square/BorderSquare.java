package com.mattforni.games.othello.gui.square;

import java.awt.Color;

import com.mattforni.games.othello.gui.Gameboard;

/**
 * {@link BorderSquare} is a primitive subclass of {@link Square} which may not
 * be played upon. It is used along the edges of the {@link Gameboard} as a
 * buffer against index out of bounds exceptions during AI evaluation.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

public class BorderSquare extends Square {
    public BorderSquare(final Gameboard gameboard, final int row, final int column) {
        super(gameboard, row, column);
        square.setFillColor(Color.black);
    }
}
