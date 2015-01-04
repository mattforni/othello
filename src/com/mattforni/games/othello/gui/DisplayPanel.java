package com.mattforni.games.othello.gui;

import static java.lang.String.format;
import static javax.swing.SwingConstants.CENTER;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The {@link DisplayPanel} contains all of the display components for Othello
 * which include a status bar indicating whose turn it is and how many pieces
 * each player has at present.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */

@SuppressWarnings("serial")
public class DisplayPanel extends JPanel {
    private static final Dimension DIMENSION = new Dimension(300, 50);
    private static final String PIECES_FORMAT = "White: %d Black: %d";
    private static final String STATUS = "Select Players, then press 'Apply Settings'";

    private final JLabel pieces;
    private final JLabel status;

    public DisplayPanel() {
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
