package com.matthew.java.springboottest.factories;

import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Shape;
import com.matthew.java.springboottest.shapes.Square;

public class SquareFactory implements ShapeFactory {
    @Override
    public Shape createShape(Point[]points, String fill, String stroke, int strokeWidth) {
        return new Square(points[0], points[1].getX(), fill, stroke, strokeWidth);
    }

    @Override
    public String getShapeClassName() {
        return "Square";
    }

    @Override
    public String getRussianShapeName() {
        return "Квадрат";
    }
}
