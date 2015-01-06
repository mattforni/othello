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
    private final int intel;
    private final Strategy strategy;

    public ComputerPlayer(final int intel, final Side side) {
        super(side);
        this.intel = intel;
        this.strategy = new Minimax();
    }

    @Override
    public final boolean makeMove(final Gameboard gameboard) {
        return makeMove(gameboard, strategy.getBestMove(gameboard, this).getSquare());
    }

    @Override
    public final int getIntel() { return intel; }
}