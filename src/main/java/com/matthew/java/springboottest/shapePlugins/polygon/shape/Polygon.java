package com.matthew.java.springboottest.shapePlugins.polygon.shape;

import com.matthew.java.springboottest.shapePlugins.polygon.strategy.PolygonDrawStrategy;
import com.matthew.java.springboottest.shapes.Point;
import com.matthew.java.springboottest.shapes.Polyline;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Polygon extends Polyline {
    public Polygon(ArrayList<Point> points) {
        this.points = points;
        setDrawStrategy(new PolygonDrawStrategy());
    }

    public Polygon(ArrayList<Point> points, String fill, String stroke, int strokeWidth) {
        this(points);
        setFill(fill);
        setStroke(stroke);
        setStrokeWidth(strokeWidth);
    }
}