package com.matthew.java.springboottest.shapePlugins.polygon.strategy;

import com.matthew.java.springboottest.shapePlugins.polygon.shape.Polygon;
import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Shape;
import com.matthew.java.springboottest.strategies.draw.DrawStrategy;

public class PolygonDrawStrategy implements DrawStrategy {
    @Override
    public String draw(Shape shape, int id) {
        if (shape.getClass() != Polygon.class)
            throw new RuntimeException();
        Polygon polygon = (Polygon) shape;
        StringBuilder svg = new StringBuilder("<polygon data-type=\"Polygon\" data-id=\"" + id + "\" stroke-width=\"" + polygon.getStrokeWidth() + "\" stroke=\"" + polygon.getStroke() + "\" fill=\"" + polygon.getFill() + "\" points=\"");
        for (Point point: polygon.getPoints()) {
            svg.append(point.getX()).append(",").append(point.getY());
            if (!polygon.getPoints().get(polygon.getPoints().size() - 1).equals(point))
                svg.append(",");
        }
        return svg.append("\" />").toString();
    }
}
