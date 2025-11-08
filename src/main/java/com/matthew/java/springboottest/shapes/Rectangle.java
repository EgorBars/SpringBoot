package com.matthew.java.springboottest.shapes;

import com.matthew.java.springboottest.strategies.draw.RectangleDrawStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Rectangle extends Shape{
    static final String TYPE = "rect";
    protected Point centre;
    protected double width;
    protected double height;

    public Rectangle(Point centre, double width, double height) {
        super(new RectangleDrawStrategy());
        this.centre = centre;
        this.width = width;
        this.height =height;
    }

    public Rectangle(Point centre, double width, double height, String fill, String stroke, int strokeWidth) {
        this(centre, width, height);
        setFill(fill);
        setStroke(stroke);
        setStrokeWidth(strokeWidth);
    }
}