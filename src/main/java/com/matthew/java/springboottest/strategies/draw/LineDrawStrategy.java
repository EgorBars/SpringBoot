package com.matthew.java.springboottest.strategies.draw;

import com.matthew.java.springboottest.shapes.Line;
import com.matthew.java.springboottest.shapes.Shape;

public class LineDrawStrategy implements DrawStrategy {
    @Override
    public String draw(Shape shape, int id) {
        if (shape.getClass() != Line.class)
            throw new RuntimeException();
        Line line = (Line) shape;
        return "<line data-type=\"Line\" data-id=\"" + id + "\" x1=\"" + line.getP1().getX() + "\" y1=\"" + line.getP1().getY() + "\" x2=\"" + line.getP2().getX() + "\" y2=\"" + line.getP2().getY() + "\" fill=\"none\" stroke=\"" + line.getStroke() + "\" stroke-width=\"" + line.getStrokeWidth() + "\"/>";
    }
}
