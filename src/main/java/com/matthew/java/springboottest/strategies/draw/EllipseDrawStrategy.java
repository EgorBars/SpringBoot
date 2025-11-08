package com.matthew.java.springboottest.strategies.draw;

import com.matthew.java.springboottest.shapes.Ellipse;
import com.matthew.java.springboottest.shapes.Shape;

public class EllipseDrawStrategy implements DrawStrategy {
    @Override
    public String draw(Shape shape, int id) {
        if (shape.getClass() != Ellipse.class) {
            throw new RuntimeException();
        }
        Ellipse ellipse = (Ellipse) shape;
        return "<ellipse data-type=\"Ellipse\" data-id=\"" + id + "\" fill=\"" + ellipse.getFill() + "\" stroke=\"" + ellipse.getStroke() + "\" stroke-width=\"" + ellipse.getStrokeWidth() + "\" cx=\"" + ellipse.getCentre().getX() + "\" cy=\"" + ellipse.getCentre().getY() + "\" rx=\"" + ellipse.getWidth() + "\" ry=\"" + ellipse.getHeight() + "\"/>";
    }
}
