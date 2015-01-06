package com.mattforni.games.othello.players;

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
    public boolean isHuman() { return true; }
}