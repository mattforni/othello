package com.mattforni.games.othello.players;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.players.ai.Minimax;
import com.mattforni.games.othello.players.ai.Strategy;

/**
 * {@link ComputerPlayer} is only slightly more complicated than it's counterpart
 * {@link HumanPlayer} is the sense that it contains and incorporates
 * {@link ComputerPlayer#intel} into the {@link #makeMove(Gameboard)} method.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

public class ComputerPlayer extends Player {
    private final Strategy strategy;

    public ComputerPlayer(final int intel, final Side side) {
        super(side);
        this.strategy = new Minimax(intel);
    }

    @Override
    public final boolean makeMove(final Gameboard gameboard) {
        return makeMove(gameboard, strategy.getBestMove(gameboard, side).getSquare());
    }
}
