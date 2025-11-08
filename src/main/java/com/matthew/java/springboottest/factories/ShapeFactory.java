package com.matthew.java.springboottest.factories;

import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Shape;

public interface ShapeFactory {
    Shape createShape(Point[] points, String fill, String stroke, int strokeWidth);
    String getShapeClassName();
    String getRussianShapeName();
}