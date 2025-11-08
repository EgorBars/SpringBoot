package com.matthew.java.springboottest.serializePlugins.archivation;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileArchiver {

    public void zip(String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            FileInputStream fis = new FileInputStream(filePath);

            ZipEntry zipEntry = new ZipEntry(filePath.substring(filePath.lastIndexOf("/") + 1));
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }

            zos.closeEntry();
            fis.close();
            zos.close();
            fos.close();

            System.out.println("File successfully zipped!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unzip(String zipFilePath) {
        try {
            FileInputStream fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry zipEntry = zis.getNextEntry();

            if (zipEntry != null) {
                String extractedFilePath = zipFilePath.substring(0, zipFilePath.lastIndexOf("."));
                FileOutputStream fos = new FileOutputStream(extractedFilePath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = zis.read(buffer)) >= 0) {
                    fos.write(buffer, 0, length);
                }

                fos.close();
            }

            zis.closeEntry();
            zis.close();
            fis.close();

            System.out.println("File successfully unzipped!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}