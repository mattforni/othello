package com.mattforni.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.RectangularShape;

import javax.swing.JPanel;

/**
 * A basic shape class to handle rendering and basic manipulations.
 *
 * @author Matthew Fornaciari <mattforni@gmail.com>
 */
public abstract class Shape {
    private RectangularShape shape;
    private JPanel container;
    private Color borderColor, color;
    private double rotation; // In radians
    private boolean isWrapping;
    private boolean isVisible;
    private int borderWidth;

    public Shape(final JPanel container, final RectangularShape shape) {
        this.container = container;
        this.shape = shape;
        this.borderColor = Color.black;
        this.color = Color.gray;
        this.rotation = 0.0;
        this.isWrapping = true;
        this.isVisible = true;
        this.borderWidth = 2;
    }

    /** 
     * Returns the x location of the top left corner of the shape's bounding box.
     */
    public final double getX() {
        return shape.getX();
    }

    /** 
     * Returns the y location of the top left corner of the shape's bounding box.
     */
    public final double getY() {
        return shape.getY();
    }

    /** 
     * Returns the height of the shape's bounding box.
     */
    public final double getHeight() {
        return shape.getHeight();
    }

    /** 
     * Returns the width of the shape's bounding box.
     */
    public final double getWidth() {
        return shape.getWidth();
    }

    /** 
     * Returns the color of the shape's border.
     */
    public final Color getBorderColor() {
        return borderColor;
    }

    /** 
     * Returns the color of the shape.
     */
    public final Color getColor() {
        return color;
    }

    /** 
     * Returns the rotation of the shape.
     */
    public final double getRotation() {
        return rotation * 180.0 / Math.PI;
    }

    /** 
     * Returns the width of the shape's border.
     */
    public final int getBorderWidth() {
        return borderWidth;
    }

    /** 
     * Returns whether or not the shape should wrap in it's container.
     */
    public final boolean getWrapping() {
        return isWrapping;
    }

    /** 
     * Returns whether or not the shape is visible.
     */
    public final boolean isVisible() {
        return isVisible;
    }

    /** 
     * Sets the location of shape, wrapping if necessary.
     * @param x The desired x location of the shape.
     * @param y The desired y location of the shape.
     */
    public final void setLocation(final double x, final double y) {
        if (isWrapping == true) {
            final int height = container.getHeight();
            final int width = container.getWidth();
            double newX = (x+width) % width;
            double newY = (y+height) % height;
            shape.setFrame(newX, newY, shape.getWidth(), shape.getHeight());
        } else {
            shape.setFrame(x, y, shape.getWidth(), shape.getHeight());
        }
    }

    /**
     * Sets the size of the shape.
     * @param width The desired width of the shape.
     * @param height The desired height of the shape.
     */
    public final void setSize(final double width, final double height) {
        shape.setFrame(shape.getX(), shape.getY(), width, height);	
    }

    /**
     * Sets the color of the shape's border.
     * @param color The desired color of the shape's border.
     */
    public final void setBorderColor(final Color color) {
        this.borderColor = color;
    }

    /**
     * Sets the color of the shape.
     * @param color The desired color of the shape.
     */
    public final void setFillColor(final Color color) {
        this.color = color;
    }

    /**
     * Sets the color of both the shape and the border.
     * @param color The desired color of the shape.
     */
    public final void setColor(final Color color) {
        this.borderColor = color;
        this.color = color;
    }

    /**
     * Sets the rotation of the shape.
     * @param degrees The desired rotation of the shape in degrees.
     */
    public final void setRotation(final double degrees) {
        rotation = degrees * Math.PI / 180;
    }

    /**
     * Sets the width of shape's border.
     * @param width The desired width of the shape's border.
     */
    public final void setBorderWidth(final int width) {
        this.borderWidth = width;
    }

    /**
     * Sets whether or not the shape should wrap in the container.
     * @param wrap Whether or not the shape should wrap.
     */
    public final void setWrapping(boolean wrap) {
        this.isWrapping = wrap;
    }

    /**
     * Sets whether or not the shape should be rendered.
     * @param visible Whether or not the shape should be rendered.
     */
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    /**
     * Paints the shape taking into account the shape's properties.
     * @param brush The brush with which to paint the shape.
     */
    public void paint(final Graphics2D brush) {
        if(isVisible == true){
            borderColor = this.getBorderColor();
            color = this.getColor();
            brush.setStroke(new java.awt.BasicStroke(borderWidth));
            // Rotate the brush to draw at an angle.
            brush.rotate(rotation, shape.getCenterX(), shape.getCenterY());
            brush.setColor(borderColor);
            brush.draw(shape);
            brush.setColor(color);
            brush.fill(shape);
            // Unrotate the brush for the next shape.
            brush.rotate(0-rotation, shape.getCenterX(), shape.getCenterY());
        }
    }

    /**
     * Returns true if the point is contained in the shape.
     * @param point The point to use during evaluation.
     */
    public boolean contains(final Point point) {
        if (rotation != 0) {
            final AffineTransform trans = AffineTransform.getRotateInstance(
                    rotation, shape.getCenterX(), shape.getCenterY());
            final java.awt.Shape shape = trans.createTransformedShape(this.shape);
            return shape.contains(point);
        }
        return shape.contains(point);
    }

    /**
     * Defines what the shape should do when interacted with.  By default the
     * shape has no behavior and thus does not react to interaction.
     */
    public void react() {}
}
