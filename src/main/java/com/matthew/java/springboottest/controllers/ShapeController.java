package com.matthew.java.springboottest.controllers;

import com.matthew.java.springboottest.dto.ShapeDTO;
import com.matthew.java.springboottest.services.ShapeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShapeController {
    private final ShapeService shapeService;

    public ShapeController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @PostMapping("/add")
    public int saveShape(@RequestBody() ShapeDTO shape) {
        System.out.println("added: " + shape);

        shapeService.createShape(shape);
        return 200;
    }

    @PostMapping("/change")
    public int changeShape(@RequestBody() ShapeDTO shape) {
        System.out.println("putted: " + shape);

        shapeService.putShape(shape);
        return 200;
    }

    @PostMapping("/delete")
    public int changeShape(@RequestBody() int id) {
        System.out.println("deleted: " + id);

        shapeService.deleteShape(id);
        return 200;
    }

    @PostMapping("/reset")
    public int resetShapes() {
        System.out.println("reset");

        shapeService.clearShapes();
        return 200;
    }

    @PostMapping("/serialize")
    public int serialize(@RequestBody String method) {
        System.out.println("serialize: " + method);

       if (method.equals("\"xml\""))
           shapeService.serializeXML();
       else if (method.equals("\"json\""))
           shapeService.serializeJSON();

       return 200;
    }

    @PostMapping("/deserialize")
    public int deserialize(@RequestBody String method) {
        System.out.println("deserialize: " + method);

        if (method.equals("\"xml\""))
            shapeService.deserializeXML();
        else if (method.equals("\"json\""))
            shapeService.deserializeJSON();

        return 200;
    }
}