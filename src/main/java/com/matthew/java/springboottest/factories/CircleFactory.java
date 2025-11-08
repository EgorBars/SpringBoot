package com.matthew.java.springboottest.factories;

import com.matthew.java.springboottest.shapes.Circle;
import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Shape;

public class CircleFactory implements ShapeFactory {
    @Override
    public Shape createShape(Point[] points, String fill, String stroke, int strokeWidth) {
        return new Circle(points[0], points[1].getX(), fill, stroke, strokeWidth);
    }

    @Override
    public String getShapeClassName() {
        return "Circle";
    }

    @Override
    public String getRussianShapeName() {
        return "Круг";
    }
}