package com.matthew.java.springboottest.factories;

import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Polyline;
import com.matthew.java.springboottest.shapes.Shape;

import java.util.ArrayList;
import java.util.Arrays;

public class PolylineFactory implements ShapeFactory {
    @Override
    public Shape createShape(Point[] points, String fill, String stroke, int strokeWidth) {
        return new Polyline(new ArrayList<>(Arrays.asList(points)), fill, stroke, strokeWidth);
    }

    @Override
    public String getShapeClassName() {
        return "Polyline";
    }

    @Override
    public String getRussianShapeName() {
        return "Ломаная";
    }
}
