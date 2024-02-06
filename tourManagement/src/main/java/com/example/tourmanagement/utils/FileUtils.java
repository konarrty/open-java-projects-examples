package com.example.tourmanagement.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileUtils {

    private static String mainDirectory;

    @Value("${image.directory}")
    public void setMainDirectory(String mainDirectory) {
        FileUtils.mainDirectory = mainDirectory;
    }

    public void saveImage(MultipartFile image, String directory, Long id) {

        if (image == null || image.isEmpty()) {
            log.error("Ошибка загрузки изображения. Файл пуст");
            return;
        }
        byte[] bytes;
        try {
            bytes = image.getBytes();

            String imageName = UUID.randomUUID() + image.getOriginalFilename();
            File nestedDirectory = new File(mainDirectory + "/" + directory + "s" + "/" + id);

            Files.createDirectories(nestedDirectory.toPath());
            try (var stream = new BufferedOutputStream(
                    new FileOutputStream(nestedDirectory + "/" + imageName))) {
                stream.write(bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public byte[] getImage(String imageName, String directory, String id) {

        try (InputStream in = new FileInputStream(mainDirectory + "/" + directory + "/" + id + "/" + imageName)) {
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteImage(Long id, String directory, String url) {

        String str = url.split("/")[1];
        String imageName = str.substring(0, str.indexOf('?'));

        var file = new File(mainDirectory + "/" + directory + "s" + "/" + id + "/" + imageName);

        if (!file.delete())
            log.error("Ошибка при удалении изображения");
    }

    public List<String> getImageNames(Class<?> clazz, Long id) {
        String directory = clazz.getSimpleName().toLowerCase() + "s";
        List<String> images = new ArrayList<>();

        File folder = new File(mainDirectory + "/" + directory + "/" + id);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    images.add(
                            "images/" + file.getName() + "?directory=" + directory + "&id=" + id);

                }
            }
        }

        return images;

    }

}
