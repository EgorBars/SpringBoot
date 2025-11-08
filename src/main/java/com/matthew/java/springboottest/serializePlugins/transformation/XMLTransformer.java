package com.matthew.java.springboottest.serializePlugins.transformation;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Map;

public class XMLTransformer {
    public static void replaceTags(File inputFile, File outputFile, Map<String, String> tagReplacements) {
        try {
            // Создаем DocumentBuilderFactory и DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Парсим входной XML файл
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Заменяем теги
            for (Map.Entry<String, String> entry : tagReplacements.entrySet()) {
                String oldTag = entry.getKey();
                String newTag = entry.getValue();

                NodeList nodeList = doc.getElementsByTagName(oldTag);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    doc.renameNode(node, node.getNamespaceURI(), newTag);
                }
            }

            // Записываем измененный XML в выходной файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);

            System.out.println("XML файл успешно обновлен.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
