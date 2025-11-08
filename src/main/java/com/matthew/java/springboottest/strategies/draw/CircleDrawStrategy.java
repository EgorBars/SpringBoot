package com.matthew.java.springboottest.strategies.draw;

import com.matthew.java.springboottest.shapes.Circle;
import com.matthew.java.springboottest.shapes.Shape;

public class CircleDrawStrategy implements DrawStrategy{
    @Override
    public String draw(Shape shape, int id) {
        if (shape.getClass() != Circle.class) {
            throw new RuntimeException();
        }
        Circle circle = (Circle) shape;
        return "<circle data-type=\"Circle\" data-id=\"" + id + "\" fill=\"" + circle.getFill() + "\" stroke=\"" + circle.getStroke() + "\" stroke-width=\"" + circle.getStrokeWidth() + "\" cx=\"" + circle.getCentre().getX() + "\" cy=\"" + circle.getCentre().getY() + "\" r=\"" + circle.getWidth() + "\"/>";
    }
}
