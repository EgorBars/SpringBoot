package com.matthew.java.springboottest.shapes;

import com.matthew.java.springboottest.strategies.draw.LineDrawStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Line extends Shape {
    private Point p1, p2;

    public Line(Point p1, Point p2) {
        super(new LineDrawStrategy());
        this.p1 = p1;
        this.p2 = p2;
    }

    public Line(Point p1, Point p2, String fill, String stroke, int strokeWidth) {
        this(p1, p2);
        setFill(fill);
        setStroke(stroke);
        setStrokeWidth(strokeWidth);
    }
}