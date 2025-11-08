package com.matthew.java.springboottest.factories;

import com.matthew.java.springboottest.shapes.Ellipse;
import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Shape;

public class EllipseFactory implements ShapeFactory {
    @Override
    public Shape createShape(Point[] points, String fill, String stroke, int strokeWidth) {
        return new Ellipse(points[0], points[1].getX(), points[1].getY(), fill, stroke, strokeWidth);
    }

    @Override
    public String getShapeClassName() {
        return "Ellipse";
    }

    @Override
    public String getRussianShapeName() {
        return "Эллипс";
    }
}