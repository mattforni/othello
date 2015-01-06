package com.mattforni.shapes;

import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * A basic rectangular shape.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */
public class Rectangle extends Shape{
    /**
     * Creates a standard rectangle.
     * @param panel The panel on which to render the ellipse.
     */
    public Rectangle(final JPanel panel){
        super(panel, new Rectangle2D.Double());
    }

    /**
     * Creates a rectangle with the given location and size.
     * @param panel The panel on which to render the rectangle.
     * @param x The x coordinate of the rectangle.
     * @param y The y coordinate of the rectangle.
     * @param w The width of the rectangle.
     * @param h The height of the rectangle.
     */
    public Rectangle(final JPanel panel,
            final double x, final double y,
            final double w, final double h){
        super(panel, new Rectangle2D.Double(x, y, w, h));
    }
}
