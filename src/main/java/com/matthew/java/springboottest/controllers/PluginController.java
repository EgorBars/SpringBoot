package com.matthew.java.springboottest.controllers;

import com.matthew.java.springboottest.services.SerializePluginService;
import com.matthew.java.springboottest.services.ShapePluginService;
import com.matthew.java.springboottest.services.ShapeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PluginController {
    private final SerializePluginService serializePluginService;
    private final ShapePluginService shapePluginService;
    private final ShapeService shapeService;

    public PluginController(ShapePluginService shapePluginService, ShapeService shapeService, SerializePluginService serializePluginService) {
        this.serializePluginService = serializePluginService;
        this.shapePluginService = shapePluginService;
        this.shapeService = shapeService;
    }

    @PostMapping("/plugin")
    public int downloadShapePlugin(@RequestParam("jar") MultipartFile jarFile, @RequestParam("js") MultipartFile jsFile) {
        shapePluginService.addPlugin(jarFile, jsFile);
        shapeService.loadPluginFactories();
        return 200;
    }

    @PostMapping("/plugin/change")
    public void changeSerializePluginMod() {

    }
}
