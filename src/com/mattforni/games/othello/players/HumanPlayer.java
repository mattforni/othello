package com.mattforni.games.othello.players;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.gui.square.Square;

/**
 * TODO re-doc
 * The human player.  This class instantiates the methods of it's parent class, telling a specific human
 * player how to react when it is it's turn to move.  These methods are different from its sibling class
 * since a human player and a computer player behave in different ways when asked to move.  
 *
 * @author <Matthew Fornaciari>
 */

public class HumanPlayer extends Player {
    public HumanPlayer(final Side side){
        super(side);
    }

    @Override
    public boolean makeMove(final Gameboard gameboard, final Square square){
        // TODO make this smarter
        gameboard.makeMove(side, square.getRow(), square.getColumn());
        gameboard.hideMoves();
        return true;
    }

    @Override
    public boolean isHuman() { return true; }
}