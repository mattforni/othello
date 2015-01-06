package com.mattforni.games.othello.players.ai;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.gui.square.Square;
import com.mattforni.games.othello.players.Player.Side;

/**
 * {@link Minimax} is the most basic of AI strategies in that it uses a universal
 * weight distribution to add and subtract hypothetical moves. Though it is fairly
 * primitive it offers recursive assessment based on the {@link #intel} parameter
 * and is capable of generating decent games.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

public class Minimax extends Strategy {
    private final int intel;
    private final DefaultWeights weights;

    public Minimax(final int intel) {
        this.intel = intel;
        this.weights = new DefaultWeights();
    }

    @Override
    public final Move getBestMove(final Gameboard gameboard, final Side side) {
        return getBestMove(gameboard, side, intel);
    }

    /* Private methods */
    private final Move getBestMove(final Gameboard gameboard, final Side side, final int intel) {
        // Initialize the best move to the minimum value
        Move bestMove = new Move(null, Integer.MIN_VALUE);

        for(int row = 0; row < gameboard.numRows(); row++) {
            for(int column = 0; column < gameboard.numColumns(); column++) {
                if(gameboard.get(row, column).isValidMove(side)){
                    // If a move is valid a hypothetical board is created
                    final Gameboard hypothetical = new Gameboard(gameboard);
                    final Square square = hypothetical.get(row, column);
                    square.flip(side);

                    // The hypothetical board is then scored and evaluated
                    int value = this.score(hypothetical, side, intel);
                    if (value >= bestMove.getValue()) {
                        bestMove = new Move(square, value);
                    }
                }
            }
        }

        return bestMove;
    }

    private int score(final Gameboard gameboard, final Side side, final int intel) {
        int total = score(gameboard, side);
        if(intel > 1) {
            total += getBestMove(gameboard, side.getOpponent(), intel-1).getValue();
        }
        return total;
    }

    private int score(final Gameboard gameboard, final Side side) {
        final Side opponent = side.getOpponent();
        int total = 0;
        for (int row = 0; row < gameboard.numRows(); row++) {
            for (int column = 0; column < gameboard.numColumns(); column++) {
                final Square square = gameboard.get(row, column);
                if (square.getSide() == side) {
                    total += weights.getWeight(row, column);
                } else if (square.getSide() == opponent) {
                    total -= weights.getWeight(row, column);
                }
            }
        }
        return total;
    }

    /* Private static classes */
    private static class DefaultWeights {
        public final int getWeight(final int row, final int column) { return 1; }
    }
}
