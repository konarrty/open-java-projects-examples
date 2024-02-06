package com.example.constructionmanagementspring.service.impl;

import com.example.constructionmanagementspring.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    @Override
    public String editUserPhoto(MultipartFile image) throws IOException {

        if (!image.isEmpty()) {
            byte[] bytes = image.getBytes();
            String imageName = UUID.randomUUID() + image.getOriginalFilename();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream("src/main/resources/avatar/" + imageName));
            stream.write(bytes);
            stream.close();

            return imageName;

        }
        return "default-user.png";

    }
}
