package com.mattforni.games.othello.players;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.players.ai.Minimax;

/**
 * TODO re-doc
 * The computer player.  This class instantiates the methods of it's parent class, telling the computer player
 * specifically how to react when it is it's turn to move.  These methods are different from its sibling class
 * since a computer player and a human player behave in different ways when asked to move.  In addition, this class
 * has the extra intel variable which indicates just how smart the computer is.  
 *
 * @author <Matthew Fornaciari>
 */

public class ComputerPlayer extends Player {
    private static final Minimax MINIMAX = new Minimax();

    private final int intel;

    public ComputerPlayer(final Side side, final int intel) {
        super(side);
        this.intel = intel;
    }

    @Override
    public final boolean makeMove(final Gameboard gameboard) {
        return makeMove(gameboard, MINIMAX.getBestMove(gameboard, this).getSquare());
    }

    @Override
    public final boolean isHuman() { return false; }

    public final int getIntel() { return intel; }
}