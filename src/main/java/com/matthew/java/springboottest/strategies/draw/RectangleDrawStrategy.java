package com.matthew.java.springboottest.strategies.draw;

import com.matthew.java.springboottest.shapes.Rectangle;
import com.matthew.java.springboottest.shapes.Shape;
import com.matthew.java.springboottest.shapes.Square;

public class RectangleDrawStrategy implements DrawStrategy {
    @Override
    public String draw(Shape shape, int id) {
        if (!Rectangle.class.isAssignableFrom(shape.getClass()))
            throw new RuntimeException();
        Rectangle rectangle = (Rectangle) shape;
        String type = shape.getClass() == Square.class ? "Square" : "Rectangle";
        return "<rect data-type=\"" + type + "\" data-id=\"" + id + "\" x=\"" + rectangle.getCentre().getX() + "\" y=\"" + rectangle.getCentre().getY() + "\" width=\"" + rectangle.getWidth() + "\" height=\"" + rectangle.getHeight() + "\" fill=\"" + rectangle.getFill() + "\" stroke=\"" + rectangle.getStroke() + "\" stroke-width=\"" + rectangle.getStrokeWidth() + "\"/>";
    }
}