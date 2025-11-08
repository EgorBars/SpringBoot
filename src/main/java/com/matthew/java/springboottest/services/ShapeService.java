package com.matthew.java.springboottest.services;

import com.matthew.java.springboottest.dto.ShapeDTO;
import com.matthew.java.springboottest.factories.ShapeFactory;
import com.matthew.java.springboottest.shapes.ShapeList;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ShapeService {
    private final ShapePluginService shapePluginService;
    private final SerializePluginService serializePluginService;
    private final ShapeList shapeList;
    private Map<String, ShapeFactory> factories;

    public ShapeService(ShapeList shapeList, ShapePluginService shapePluginService, SerializePluginService serializePluginService) {
        this.shapeList = shapeList;
        this.shapePluginService = shapePluginService;
        this.serializePluginService = serializePluginService;
        loadFactories();
        loadPluginFactories();
    }

    public void loadPluginFactories() {
        factories.putAll(shapePluginService.factories);
    }

    private void loadFactories() {
        factories = new HashMap<>();

        Reflections reflections = new Reflections("com.matthew.java.springboottest.factories");
        Set<Class<? extends ShapeFactory>> classes = reflections.getSubTypesOf(ShapeFactory.class);

        for (Class<? extends ShapeFactory> clazz : classes) {
            try {
                ShapeFactory instance = clazz.getDeclaredConstructor().newInstance();
                factories.put(instance.getShapeClassName(), instance);
            } catch (InstantiationException | IllegalAccessException |
                     NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, String> getShapeNames() {
        Map<String, String> map = new HashMap<>();
        factories.forEach((k, v) -> map.put(k, v.getRussianShapeName()));

        return map;
    }

    public String drawShapes() {
        return shapeList.drawList();
    }

    public void createShape(ShapeDTO shape) {
        shapeList.addShape(factories.get(shape.getType()).createShape(shape.getPoints(), shape.getFill(), shape.getStroke(), shape.getStrokeWidth()));
    }

    public void putShape(ShapeDTO shape) {
        shapeList.putShape(shape.getId(), factories.get(shape.getType()).createShape(shape.getPoints(), shape.getFill(), shape.getStroke(), shape.getStrokeWidth()));
    }

    public void deleteShape(int id) {
        shapeList.deleteShape(id);
    }

    public void clearShapes() {
        shapeList.clear();
    }

    public void serializeXML() {
        shapeList.serializeXML("data.xml");
        serializePluginService.serialize("data.xml");
    }

    public void deserializeXML() {
        serializePluginService.deserialize("data.xml");
        shapeList.deserializeXML("data.xml");
    }

    public void serializeJSON() {
        shapeList.serializeJSON("data.json");
    }

    public void deserializeJSON() {
        shapeList.deserializeJSON("data.json");
    }
}