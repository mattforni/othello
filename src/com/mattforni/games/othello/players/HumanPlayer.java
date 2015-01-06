package com.mattforni.games.othello.players;

/**
 * {@link HumanPlayer} is a simple extension of the {@link Player} class which
 * merely defines a constructor and overrides the {@link #isHuman()} method.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

public class HumanPlayer extends Player {
    public HumanPlayer(final Side side) {
        super(side);
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}