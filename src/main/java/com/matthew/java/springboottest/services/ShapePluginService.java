package com.matthew.java.springboottest.services;

import com.matthew.java.springboottest.factories.ShapeFactory;
import com.matthew.java.springboottest.shapes.Shape;
import com.matthew.java.springboottest.strategies.draw.DrawStrategy;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@Service
public class ShapePluginService {
    private final String pluginFolderForJAR = "compiledShapePlugins";
    private final String pluginFolderForJS = "src/main/resources/static/js/tools";
    public Map<String, ShapeFactory> factories;
    private URLClassLoader classLoader;

    public ShapePluginService() {
        factories = new HashMap<>();
        loadPlugins();
        System.out.println(factories.toString());
    }

    public void addPlugin(MultipartFile jarFile, MultipartFile jsFile) {
        try {
            jarFile.transferTo(new File(pluginFolderForJAR + "/" + jarFile.getOriginalFilename()).toPath());
            jsFile.transferTo(new File(pluginFolderForJS + "/" + jsFile.getOriginalFilename()).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadPlugins();
    }

    public void loadPlugins() {
        factories.clear();
        List<String> jarFiles = getJARFiles();

        if (!jarFiles.isEmpty()) {
            List<URL> urls = new ArrayList<>();
            Map<URI, String[]> pluginClassNames = new HashMap<>();
            for (String jar : jarFiles) {
                URL pluginUrl = getClass().getClassLoader().getResource(jar);
                if (pluginUrl != null) {
                    try (JarFile jarFile = new JarFile(pluginFolderForJAR + "\\" + jar)) {
                        Manifest manifest = jarFile.getManifest();
                        Attributes attributes = manifest.getMainAttributes();
                        if (attributes.isEmpty()) {
                            System.err.println("Ошибка при работе с манифестом: " + jar);
                            continue;
                        } else {
                            String shapeClassName = attributes.getValue("Shape-Class-Name");
                            String shapeDrawStrategyClassName = attributes.getValue("Shape-Draw-Strategy-Class-Name");
                            String shapeFactoryClassName = attributes.getValue("Shape-Factory-Class-Name");
                            if (shapeClassName == null || shapeDrawStrategyClassName == null || shapeFactoryClassName == null) {
                                System.err.println("Отсутствуют необходимые аттрибуты в манифесте: " + jar);
                                continue;
                            } else {
                                pluginClassNames.put(pluginUrl.toURI(), new String[]{shapeClassName, shapeDrawStrategyClassName, shapeFactoryClassName});
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Ошибка при работе с плагином: " + jar);
                        e.printStackTrace();
                        continue;
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        continue;
                    }
                    urls.add(pluginUrl);
                }
            }
            if (!urls.isEmpty()) {
                classLoader = new URLClassLoader(urls.toArray(new URL[0]), getClass().getClassLoader());
                for (URL url : urls)
                    try {
                        System.out.println(pluginClassNames.get(url.toURI())[0]);
                        Class<?> clazz = classLoader.loadClass(pluginClassNames.get(url.toURI())[0]);
                        if (!Shape.class.isAssignableFrom(clazz)) {
                            System.err.println("Класс " + clazz.getName() + " не наследует класс " + Shape.class.getName());
                            continue;
                        }
                        clazz = classLoader.loadClass(pluginClassNames.get(url.toURI())[1]);
                        if (!DrawStrategy.class.isAssignableFrom(clazz)) {
                            System.err.println("Класс " + clazz.getName() + " не наследует класс " + DrawStrategy.class.getName());
                            continue;
                        }
                        clazz = classLoader.loadClass(pluginClassNames.get(url.toURI())[2]);
                        if (!ShapeFactory.class.isAssignableFrom(clazz)) {
                            System.err.println("Класс " + clazz.getName() + " не наследует класс " + ShapeFactory.class.getName());
                            continue;
                        }
                        ShapeFactory shapeFactory = (ShapeFactory) clazz.getDeclaredConstructor().newInstance();
                        factories.put(shapeFactory.getShapeClassName(), shapeFactory);
                    } catch (URISyntaxException | ClassNotFoundException | NoSuchMethodException e) {
                        System.err.println("Ошибка при загрузке классов из плагина: " + url);
                        e.printStackTrace();
                    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        System.err.println("Ошибка создания объекта класса фабрики: " + url);
                        e.printStackTrace();
                    }
            }
        }
    }

    private List<String> getJARFiles() {
        ArrayList<String> list = new ArrayList<>();

        File folder = new File(pluginFolderForJAR);
        File[] files = folder.listFiles();

        if (files != null)
            for (File file : files)
                if (file.isFile() && file.getName().toLowerCase().endsWith(".jar"))
                    list.add(file.getName());

        return list;
    }

    @PreDestroy
    public void cleanup() {
        if (classLoader != null) {
            try {
                classLoader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
