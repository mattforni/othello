package com.mattforni.shapes;

import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

/**
 * A basic elliptical shape.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */
public class Ellipse extends Shape {
    /**
     * Creates a standard ellipse.
     * @param panel The panel on which to render the ellipse.
     */
    public Ellipse(final JPanel panel){
        super(panel, new Ellipse2D.Double());
    }

    /**
     * Creates an ellipse with the given location and size.
     * @param panel The panel on which to render the ellipse.
     * @param x The x coordinate of the ellipse.
     * @param y The y coordinate of the ellipse.
     * @param w The width of the ellipse.
     * @param h The height of the ellipse.
     */
    public Ellipse(final JPanel panel,
            final double x, final double y,
            final double w, final double h) {
        super(panel, new Ellipse2D.Double(x, y, w, h));
    }
}
