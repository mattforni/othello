package com.mattforni.games.othello;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mattforni.games.othello.gui.BottomPanel;
import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.gui.TopPanel;

/**
 * TODO re-doc this
 * The OthelloMain is the large and top-level all-encompassing object that creates both the
 * board and the sidepanel.  Then the board and the sidepanel handle the rest of the work.
 * Essentially, this class is the lazy class that starts everything in motion by delegating
 * the rest of the work out to the smaller-level objects.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

@SuppressWarnings("serial")
public class Othello extends JPanel{
    private final Gameboard gameboard;
    private final TopPanel topPanel;
    private final BottomPanel bottomPanel;
    private final Referee referee;

    public Othello() {
        super(new BorderLayout());

        this.topPanel = new TopPanel();
        this.gameboard = new Gameboard();
        this.referee = new Referee(gameboard, topPanel);
        this.bottomPanel = new BottomPanel(referee);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(gameboard, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);

        new GameFrame(this);
    }

    private class GameFrame extends JFrame {
        private GameFrame(final JPanel othello) {
            super();
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);

            // Add the othello panel to the frame and show it
            this.add(othello);

            this.pack();
            this.setVisible(true);
        }
    }

    public static void main(String[] argv) {
        new Othello();
    }
}
