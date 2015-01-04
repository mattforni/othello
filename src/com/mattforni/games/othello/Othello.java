package com.mattforni.games.othello;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mattforni.games.othello.gui.ControlPanel;
import com.mattforni.games.othello.gui.Gameboard;
import com.mattforni.games.othello.gui.DisplayPanel;

/**
 * {@link Othello} is simply a container that acts as the main entry point to
 * the application. It creates and binds all of the components necessary to play
 * and display the game of Othello ({@link http://en.wikipedia.org/wiki/Reversi}).
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

@SuppressWarnings("serial")
public class Othello extends JPanel{
    private final Gameboard gameboard;
    private final DisplayPanel display;
    private final ControlPanel control;
    private final Referee referee;

    public Othello() {
        super(new BorderLayout());

        // Create and add all of the display components
        this.display = new DisplayPanel();
        this.gameboard = new Gameboard();
        this.referee = new Referee(gameboard, display);
        this.control = new ControlPanel(referee);

        this.add(display, BorderLayout.NORTH);
        this.add(gameboard, BorderLayout.CENTER);
        this.add(control, BorderLayout.SOUTH);

        this.setVisible(true);

        // Create and display the frame
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] argv) {
        new Othello();
    }
}
