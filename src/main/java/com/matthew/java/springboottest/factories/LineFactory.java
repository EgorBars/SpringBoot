package com.matthew.java.springboottest.factories;

import com.matthew.java.springboottest.shapes.Line;
import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Shape;

public class LineFactory implements ShapeFactory {
    @Override
    public Shape createShape(Point[] points, String fill, String stroke, int strokeWidth) {
        return new Line(points[0], points[1], fill, stroke, strokeWidth);
    }

    @Override
    public String getShapeClassName() {
        return "Line";
    }

    @Override
    public String getRussianShapeName() {
        return "Линия";
    }
}
