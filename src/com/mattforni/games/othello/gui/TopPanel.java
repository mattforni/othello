package com.mattforni.games.othello.gui;

import static java.lang.String.format;
import static javax.swing.SwingConstants.CENTER;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * TODO re-doc
 * The SidePanel contains all of the controls, buttons, score, etc. that are incorporated in the game.
 * Everything that is needed to control the game is located in this panel which is then added to the 
 * eastern border of the OthelloMain class which utilizes a border layout.  
 *
 * @author <Matthew Fornaciari>
 */

@SuppressWarnings("serial")
public class TopPanel extends JPanel {
    private static final Dimension DIMENSION = new Dimension(300, 50);
    private static final String PIECES_FORMAT = "White: %d Black: %d";
    private static final String STATUS = "Select Players, then press 'Apply Settings'";

    private final JLabel pieces;
    private final JLabel status;

    public TopPanel() {
        super(new GridLayout(2, 1));

        this.status = new JLabel(STATUS, CENTER);
        this.pieces = new JLabel(format(PIECES_FORMAT, 2, 2), CENTER);

        this.setBackground(Color.white);
        this.setSize(DIMENSION);
        this.setPreferredSize(DIMENSION);
        this.setVisible(true);

        this.add(status);
        this.add(pieces);
    }

    public final void setPieces(final int white, final int black) {
        pieces.setText(format(PIECES_FORMAT, white, black));
    }

    public final void setStatus(final String string) {
        status.setText(string);
    }
}
