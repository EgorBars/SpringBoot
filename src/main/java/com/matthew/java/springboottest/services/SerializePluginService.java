package com.matthew.java.springboottest.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Getter
@Setter
public class SerializePluginService {
    private final String archiverJARName = "archive.jar";
    private final String transformerJARName = "transformer.jar";
    private final String archiverClassName = "com.matthew.java.springboottest.serializePlugins.archivation.FileArchiver";
    private final String transformerClassName = "com.matthew.java.springboottest.serializePlugins.transformation.XMLTransformer";
    private boolean isArchiveMod = false;
    private boolean isTransformMod = true;

    private Class<?> transformerClass;
    private Object archiver;

    public SerializePluginService() {
        loadPlugins();
    }

    public void serialize(String fileName) {
        if (isTransformMod) {
            try {
                Method method = transformerClass.getMethod("replaceTags", File.class, File.class, Map.class);
                Map<String, String> map = new HashMap<>();
                map.put("double", "float");
                method.invoke(transformerClass, new File(fileName), new File(fileName), map);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        if (isArchiveMod) {
            try {
                System.out.println(Arrays.toString(archiver.getClass().getMethods()));
                Method zipMethod = archiver.getClass().getMethod("zip", String.class);
                zipMethod.invoke(archiver, fileName);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error zipping file: " + e.getMessage());
            }
        }
    }

    public void deserialize(String fileName) {
        if (isArchiveMod) {
            try {
                Method zipMethod = archiver.getClass().getDeclaredMethod("unzip", String.class);
                zipMethod.invoke(archiver, fileName);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error zipping file: " + e.getMessage());
            }
        }

        if (isTransformMod) {
            try {
                Method method = transformerClass.getMethod("replaceTags", File.class, File.class, Map.class);
                Map<String, String> map = new HashMap<>();
                map.put("float", "double");
                method.invoke(transformerClass, new File(fileName), new File(fileName), map);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void loadPlugins() {
        URL archiverURL;
        URL transformerURL;
        try {
            archiverURL = new File(archiverJARName).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try {
            transformerURL = new File(transformerJARName).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{archiverURL, transformerURL})) {
            transformerClass = classLoader.loadClass(transformerClassName);
            archiver = classLoader.loadClass(archiverClassName).getDeclaredConstructor().newInstance();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
