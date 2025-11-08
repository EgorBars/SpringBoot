package com.matthew.java.springboottest.shapes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Square extends Rectangle {
    public Square(Point centre, double width) {
        super(centre, width, width);
    }

    public Square(Point centre, double width, String fill, String stroke, int strokeWidth) {
        super(centre, width, width, fill, stroke, strokeWidth);
    }
}