package com.matthew.java.springboottest.shapes;

import com.matthew.java.springboottest.strategies.draw.DrawStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class Shape {
    protected String fill = "none";
    protected String stroke = "black";
    protected int strokeWidth = 6;

    private DrawStrategy drawStrategy;

    public Shape(DrawStrategy drawStrategy) {
        this.drawStrategy = drawStrategy;
    }

    public String draw(int id) {
        return drawStrategy.draw(this, id);
    }
}
