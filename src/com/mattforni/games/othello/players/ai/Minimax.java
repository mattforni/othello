package com.mattforni.games.othello.players.ai;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.gui.square.Square;
import com.mattforni.games.othello.players.ComputerPlayer;
import com.mattforni.games.othello.players.Player;
import com.mattforni.games.othello.players.Player.Side;

/**
 * AW! Behind every good AI player there is an algorithm that simulates motion.  This is the class that 
 * creates and executes the algorithms that dictate which move a computer player should make.  The
 * getBestMove method is not exactly recursive; however, through the use of a helper method,
 * evaluateBoard.  The computer has three levels with which is can act, the first of which being
 * a straight boardeval.  The second level recurses once evaluating the best move in the first level
 * but comparing all of the values for all initial valid moves.  Lastly there is level three, which
 * similarly, recurses three times.  The combination of getBestMove, evaluateBoard and boardEval
 * make it fairly easy to simulate computer intelligence.  At least a primitive computer intelligence.
 *
 * @author <Matthew Fornaciari>
 */

public class Minimax extends Strategy {
    private DefaultWeights _weights;

    public Minimax(){
        _weights = new DefaultWeights();
    }

    @Override
    public final Move getBestMove(final Gameboard gameboard, final Player player) {
        final Side side = player.getSide();

        Move bestMove = new Move(null, Integer.MIN_VALUE);

        // creates the logical containment for the passed gameboard
        for(int row = 0; row < gameboard.numRows(); row++) {
            for(int col = 0; col < gameboard.numColumns(); col++) {
                if(gameboard.get(row, col).isValidMove(side)){
                    // if a move is valid, a copy board is created and 
                    // the move is made on that board
                    Gameboard hypothetical = new Gameboard(gameboard);
                    // makes the move on the copy's gameboard
                    final Square square = hypothetical.get(row, col);
                    square.setPiece(side);
                    square.flip(side);
                    int value = this.evaluateBoard(hypothetical, player);
                    if (value >= bestMove.getValue()) {
                        bestMove = new Move(square, value);
                    }
                }
            }
        }
        return bestMove;
    }

    // a helper class that evaluates and returns the simulated value of a higher level move
    public int evaluateBoard(final Gameboard gameboard, final Player player) {
        final Side side = player.getSide();
        final int intel = player.getIntel();

        if(intel == 1){
            return this.boardEval(gameboard, side);
        } else {
            final Player next = new ComputerPlayer(side.getOpponent(), intel-1);
            return getBestMove(gameboard, next).getValue();
        }
    }

    public int boardEval(Gameboard gameboard, Side side){
        final Side opponent = side.getOpponent();
        int total = 0;
        for(int row=0; row < gameboard.numRows(); row++){
            for(int col=0; col < gameboard.numColumns(); col++){
                final Square square = gameboard.get(row, col);
                if(square.getSide() == side) {
                    total += _weights.getWeight(row, col);
                } else if(square.getSide() == opponent) {
                    total -= _weights.getWeight(row, col);
                }
            }
        }
        return total;
    }

    private static class DefaultWeights {
        public final int getWeight(final int row, final int column) {
            return 1;
        }
    }
}
