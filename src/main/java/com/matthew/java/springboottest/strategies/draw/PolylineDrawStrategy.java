package com.matthew.java.springboottest.strategies.draw;

import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Polyline;
import com.matthew.java.springboottest.shapes.Shape;

public class PolylineDrawStrategy implements DrawStrategy {
    @Override
    public String draw(Shape shape, int id) {
        if (shape.getClass() != Polyline.class)
            throw new RuntimeException();
        Polyline polyline = (Polyline) shape;
        StringBuilder svg = new StringBuilder("<polyline data-type=\"Polyline\" data-id=\"" + id + "\" stroke-width=\"" + polyline.getStrokeWidth() + "\" stroke=\"" + polyline.getStroke() + "\" fill=\"" + polyline.getFill() + "\" points=\"");
        for (Point point: polyline.getPoints()) {
            svg.append(point.getX()).append(",").append(point.getY());
            if (!polyline.getPoints().get(polyline.getPoints().size() - 1).equals(point))
                svg.append(",");
        }
        return svg.append("\" />").toString();
    }
}