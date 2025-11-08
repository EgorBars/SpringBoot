package com.matthew.java.springboottest.strategies.draw;

import com.matthew.java.springboottest.shapes.Shape;

public interface DrawStrategy {
    String draw(Shape shape, int id);
}
