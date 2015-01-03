package com.mattforni.games.othello;

import static com.mattforni.games.othello.gui.square.Square.SIZE;
import static java.lang.String.format;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.gui.TopPanel;
import com.mattforni.games.othello.gui.square.Square;
import com.mattforni.games.othello.players.Player;
import com.mattforni.games.othello.players.Player.Side;

/**
 * TODO re-doc
 * The referee is the behind the scenes interaction between the two players.  This class keeps track
 * of who the two players are via a combination of its mutator methods and the white/blackplayerbuttons.
 * At the end of every player's turn they call the start method on the referee who then identifies who
 * the current player is and switches the current player to the other player.  The referee then call's
 * the new current player's start method and they begin.  This cycle continues until the referee finds 
 * that neither player has a move in which case it ends the game.
 *
 * @author <Matthew Fornaciari>
 */

@SuppressWarnings("serial")
public class Referee extends Timer {
    private final static String STATUS_FORMAT = "%s's turn";

    private final Gameboard gameboard;
    private final TopPanel topPanel;
    private final Map<Side, Player> players;

    private Side current;

    public Referee(final Gameboard gameboard, final TopPanel topPanel) {
        super(250, null);

        this.gameboard = gameboard;
        this.topPanel = topPanel;
        this.players = new HashMap<Side, Player>();
        new ClickListener(this);

        this.addActionListener(new TurnHandler(this));
    }

    public final int count(final Side side) {
        return gameboard.getColorCount(side);
    }

    public final Player getCurrent() { return players.get(current); }

    public final Gameboard getGameboard() { return gameboard; }

    public final Player getPlayer(final Side side) {
        return players.get(current);
    }

    public final void reset() {
        current = Side.WHITE;
        gameboard.newGame();
        updatePieces();
    }

    public final void setPlayer(final Player player) {
        // If the player is null there is nothing to do
        if (player == null) { return; }
        players.put(player.getSide(), player);
    }

    @Override
    public final void start() {
        updateCurrent();
        super.start();
    }

    public final void nextTurn() {
        if (current == Side.WHITE) {
            current = Side.BLACK;
        } else if (current == Side.BLACK) {
            current = Side.WHITE;
        }
        this.start();
    }

    private Player getOpponent(final Side side) {
        if (side == Side.WHITE) {
            return players.get(Side.BLACK);
        } else if (side == Side.BLACK) {
            return players.get(Side.WHITE);
        }
        return null;
    }

    private void updateCurrent() {
        if (current == null) { current = Side.WHITE; }
    }

    private void updatePieces() {
        topPanel.setPieces(count(Side.WHITE), count(Side.BLACK));
    }

    private class ClickListener extends MouseAdapter {
        private final Gameboard gameboard;
        private final Referee referee;

        public ClickListener(final Referee referee) {
            this.gameboard = referee.getGameboard();
            this.referee = referee;
            gameboard.addMouseListener(this);
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            // If the current player is not set or is not human there is nothing to do
            final Player current = referee.getCurrent();
            if (current == null || !current.isHuman()) { return; }

            final Point point = e.getPoint();
            final Square square = gameboard.get(point.y/SIZE, point.x/SIZE);

            if (referee.getCurrent().attemptMove(gameboard, square)) {
                referee.nextTurn();
            }
        }
    }

    private class TurnHandler implements ActionListener {
        private final Referee referee;

        public TurnHandler(final Referee referee) {
            this.referee = referee;
        }

        public void actionPerformed(final ActionEvent e) {
            final Player current = getCurrent();
            if (current.hasMoves(gameboard)) {
                // Update the status display
                topPanel.setStatus(format(STATUS_FORMAT, current.getSide()));
                if (current.isHuman()) {
                    current.showMoves(gameboard);
                    referee.stop();
                } else {
                    gameboard.unilluminate();
                    current.makeMove(gameboard);
                    nextTurn();
                }
                updatePieces();
            } else {
                final Player opponent = getOpponent(current.getSide());

                // If neither player has any moves the game is over
                if (!opponent.hasMoves(gameboard)) {
                    topPanel.setStatus("Game Over");
                    return;
                }

                // Otherwise move on to the next turn
                nextTurn();
            }
        }
    }
}
