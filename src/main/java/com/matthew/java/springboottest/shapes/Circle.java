package com.matthew.java.springboottest.shapes;

import com.matthew.java.springboottest.strategies.draw.CircleDrawStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Circle extends Ellipse {
    public Circle(Point centre, double radius) {
        super(centre, radius, radius);
        setDrawStrategy(new CircleDrawStrategy());
    }

    public Circle(Point centre, double radius, String fill, String stroke, int strokeWidth) {
        this(centre, radius);
        setFill(fill);
        setStroke(stroke);
        setStrokeWidth(strokeWidth);
    }
}
