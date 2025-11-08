package com.matthew.java.springboottest.shapes;

import com.matthew.java.springboottest.strategies.draw.EllipseDrawStrategy;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Ellipse extends Shape {
    protected Point centre;
    protected double height;
    protected double width;

    public Ellipse(Point centre, double width, double height) {
        super(new EllipseDrawStrategy());
        this.centre = centre;
        this.height = height;
        this.width = width;
    }

    public Ellipse(Point centre, double width, double height, String fill, String stroke, int strokeWidth) {
        this(centre, width, height);
        setFill(fill);
        setStroke(stroke);
        setStrokeWidth(strokeWidth);
    }
}
