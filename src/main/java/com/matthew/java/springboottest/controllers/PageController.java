package com.matthew.java.springboottest.controllers;

import com.matthew.java.springboottest.services.SerializePluginService;
import com.matthew.java.springboottest.services.ShapeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    private final ShapeService shapeService;
    private final SerializePluginService serializePluginService;

    public PageController(ShapeService shapeService, SerializePluginService serializePluginService) {
        this.shapeService = shapeService;
        this.serializePluginService = serializePluginService;
    }

    @GetMapping
    public String MyPaint(Model model) {
        model.addAttribute("shape_list", shapeService.drawShapes());
        model.addAttribute("shape_names", shapeService.getShapeNames());
        model.addAttribute("plugin_transform_mod", serializePluginService.isTransformMod());
        model.addAttribute("plugin_archive_mod", serializePluginService.isArchiveMod());

        return "canvas";
    }
}
