package com.matthew.java.springboottest.factories;

import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Rectangle;
import com.matthew.java.springboottest.shapes.Shape;

public class RectangleFactory implements ShapeFactory{
    @Override
    public Shape createShape(Point[] points, String fill, String stroke, int strokeWidth) {
        return new Rectangle(points[0], points[1].getX(), points[1].getY(), fill, stroke, strokeWidth);
    }

    @Override
    public String getShapeClassName() {
        return "Rectangle";
    }

    @Override
    public String getRussianShapeName() {
        return "Прямоугольник";
    }
}
