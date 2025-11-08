package com.matthew.java.springboottest.shapes;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.matthew.java.springboottest.strategies.draw.DrawStrategy;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Scope("singleton")
@Component
public class ShapeList {
    private final ArrayList<Shape> shapes;

    public ShapeList() {
        shapes = new ArrayList<>();
    }

    public void addAllShape(List<Shape> shapes) {
        this.shapes.addAll(shapes);
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void putShape(int id, Shape shape) {
        shapes.set(id, shape);
    }

    public void deleteShape(int id) {
        shapes.remove(id);
    }

    public void clear() {
        shapes.clear();
    }

    public String drawList() {
        int i = 0;
        StringBuilder svg = new StringBuilder("<svg id=\"svg\" >");
        for (Shape shape: shapes)
            svg.append(shape.draw(i++));
        svg.append("</svg>");

        return svg.toString();
    }

    public void serializeXML(String filename) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)))) {
            encoder.writeObject(shapes);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deserializeXML(String filename) {
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)))) {
            this.clear();
            this.addAllShape((ArrayList<Shape>)decoder.readObject());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    static class ShapeWrapper {
        private final String type;
        private final Object data;

        public ShapeWrapper(String type, Object data) {
            this.type = type;
            this.data = data;
        }
    }

    static class MyClassSerializer implements JsonSerializer<DrawStrategy> {
        @Override
        public JsonElement serialize(DrawStrategy src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", src.getClass().getName());
            return jsonObject;
        }
    }

    class MyClassDeserializer implements JsonDeserializer<DrawStrategy> {
        @Override
        public DrawStrategy deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonElement type = jsonObject.get("type");
            try {
//                return (DrawStrategy) Class.forName(type.getAsString()).getConstructor().newInstance();
                return (DrawStrategy) getClass().getClassLoader().loadClass(type.getAsString()).getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void serializeJSON(String filename) {
        List<ShapeWrapper> shapeWrappers = new ArrayList<>();
        for (Shape shape : shapes) {
            shapeWrappers.add(new ShapeWrapper(shape.getClass().getName(), shape));
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(DrawStrategy.class, new MyClassSerializer())
                .create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(shapeWrappers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeJSON(String filename) {
        Gson gson = new GsonBuilder().registerTypeAdapter(DrawStrategy.class, new MyClassDeserializer())
                .create();
        this.clear();
        try (FileReader reader = new FileReader(filename);
            JsonReader jsonReader = new JsonReader(reader)) {
            jsonReader.setLenient(true);

            JsonElement jsonElement = JsonParser.parseReader(jsonReader);
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                for (JsonElement element : jsonArray) {
                    JsonObject obj = element.getAsJsonObject();
                    String type = obj.get("type").getAsString();
                    JsonObject data = obj.getAsJsonObject("data");

                    Class<? extends Shape> shapeClass;
                    try {
                        // shapeClass = (Class<? extends Shape>) Class.forName(type);
                        shapeClass = (Class<? extends Shape>) getClass().getClassLoader().loadClass(type);
                        shapes.add(gson.fromJson(data, shapeClass));
                    } catch (ClassNotFoundException e) {
                        System.out.println("Unknown shape type: " + type);
                    }
                }
            } else {
                System.out.println("Expected a JSON array at the beginning of the file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}