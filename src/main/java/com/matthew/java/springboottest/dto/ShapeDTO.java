package com.matthew.java.springboottest.dto;

import com.matthew.java.springboottest.shapes.Point;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShapeDTO {
    private String type;
    private int id;
    private String fill;
    private String stroke;
    private int strokeWidth;
    private Point[] points;
}
