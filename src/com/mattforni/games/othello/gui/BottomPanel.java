package com.mattforni.games.othello.gui;

import static java.lang.String.format;
import static javax.swing.SwingConstants.CENTER;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.mattforni.games.othello.Referee;
import com.mattforni.games.othello.players.ComputerPlayer;
import com.mattforni.games.othello.players.HumanPlayer;
import com.mattforni.games.othello.players.Player;
import com.mattforni.games.othello.players.Player.Side;

/**
 * TODO re-doc
 * The SidePanel contains all of the controls, buttons, score, etc. that are incorporated in the game.
 * Everything that is needed to control the game is located in this panel which is then added to the 
 * eastern border of the OthelloMain class which utilizes a border layout.  
 *
 * @author <Matthew Fornaciari>
 */

@SuppressWarnings("serial")
public class BottomPanel extends JPanel {
    private static final String COMPUTER_FORMAT = "Computer, Level %d";
    private static final Dimension DIMENSION = new Dimension(300, 150);
    private static final int NUM_COMPUTERS = 3;

    private final Referee referee;
    private final Map<Side, Player> players;

    public BottomPanel(final Referee referee){
        super(new GridLayout(0, 1));

        this.setBackground(Color.WHITE);
        this.setSize(DIMENSION);
        this.setPreferredSize(DIMENSION);
        this.setVisible(true);

        this.referee = referee;
        this.players = new HashMap<Side, Player>();

        this.add(playerSelection());
        this.add(buttons());
        updatePlayers();
    }

    private final JPanel buttons() {
        final JPanel buttons = new JPanel(new GridLayout(1, 3));
        buttons.setBackground(Color.WHITE);
        buttons.add(new ResetButton());
        buttons.add(new ApplyButton(referee));
        buttons.add(new QuitButton());
        return buttons;
    }

    private final JPanel playerSelection() {
        final JPanel playerSelection = new JPanel(new GridLayout(5, 2));
        playerSelection.setBackground(Color.WHITE);

        final Map<Side, ButtonGroup> groups = new HashMap<Side, ButtonGroup>();

        // Create labels
        for (final Side side : Side.values()) {
            groups.put(side, new ButtonGroup());
            final JLabel label = new JLabel(side.toString(), CENTER);
            playerSelection.add(label);
        }

        // Create human buttons
        for (final Side side : Side.values()) {
            final Player human = new HumanPlayer(side);
            players.put(side, human);

            final PlayerButton button = new PlayerButton("Human", human);
            button.setBackground(Color.WHITE);
            button.setSelected(true);
            groups.get(side).add(button);
            playerSelection.add(button);
        }

        // Create computer buttons
        for (int number = 1; number <= NUM_COMPUTERS; number++) {
            for (final Side side : Side.values()) {
                final String name = format(COMPUTER_FORMAT, number);
                final Player computer = new ComputerPlayer(side, number);

                final PlayerButton button = new PlayerButton(name, computer);
                if (number % 2 == 0) { button.setBackground(Color.WHITE); }
                groups.get(side).add(button);
                playerSelection.add(button);
            }
        }

        return playerSelection;
    }

    private final void updatePlayers() {
        final Player black = players.get(Side.BLACK);
        final Player white = players.get(Side.WHITE);

        referee.setPlayer(black);
        referee.setPlayer(white);
    }

    /* Private static classes used in constructing the side panel */
    public class ApplyButton extends JButton {
        public ApplyButton(final Referee referee) {
            super("Apply Settings");
            this.addActionListener(new PlayerListener());
        }

        private class PlayerListener implements ActionListener {
            public final void actionPerformed(final ActionEvent e) {
                if (referee.isRunning()) { referee.stop(); }
                updatePlayers();
                referee.start();
            }
        }
    }

    private class PlayerButton extends JRadioButton {
        private final Player player;

        public PlayerButton(final String name, final Player player) {
            super(name, false);
            this.player = player;
            this.addActionListener(new PlayerListener());
        }

        private class PlayerListener implements ActionListener{
            public final void actionPerformed(final ActionEvent e) {
                players.put(player.getSide(), player);
            }
        }
    }

    private static final class QuitButton extends JButton {
        public QuitButton(){
            super("Quit");
            this.addActionListener(new QuitListener());
        }

        private class QuitListener implements ActionListener {
            public void actionPerformed(final ActionEvent e){
                System.exit(0);
            }
        }
    }

    /**
     * This is a convenient subclass of JButton that simply resets the board
     * back to it initial conditions with black pieces in the top left and
     * bottom right of the middle four squares and white pieces in the top right
     * and bottom left.
     */
    private final class ResetButton extends JButton {
        public ResetButton() {
            super("New Game");
            this.addActionListener(new ResetListener());
        }

        private class ResetListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                referee.reset();
                referee.start();
            }
        }
    }
}
