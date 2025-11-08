package com.matthew.java.springboottest.shapePlugins.polygon.factory;

import com.matthew.java.springboottest.factories.ShapeFactory;
import com.matthew.java.springboottest.shapePlugins.polygon.shape.Polygon;
import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Shape;

import java.util.ArrayList;
import java.util.Arrays;

public class PolygonFactory implements ShapeFactory {
    @Override
    public Shape createShape(Point[] points, String fill, String stroke, int strokeWidth) {
        return new Polygon(new ArrayList<>(Arrays.asList(points)), fill, stroke, strokeWidth);
    }

    @Override
    public String getShapeClassName() {
        return "Polygon";
    }

    @Override
    public String getRussianShapeName() {
        return "Многоугольник";
    }
}
