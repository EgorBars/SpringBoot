package com.matthew.java.springboottest.shapes;

import com.matthew.java.springboottest.strategies.draw.PolylineDrawStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Polyline extends Shape {
    protected List<Point> points;

    public Polyline(ArrayList<Point> points) {
        super(new PolylineDrawStrategy());
        this.points = points;
    }

    public Polyline(ArrayList<Point> points, String fill, String stroke, int strokeWidth) {
        this(points);
        setFill(fill);
        setStroke(stroke);
        setStrokeWidth(strokeWidth);
    }
}
