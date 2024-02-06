package com.example.constructionmanagementspring.service;

import com.example.constructionmanagementspring.model.Assets;
import com.example.constructionmanagementspring.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MediaService {
    String editUserPhoto(MultipartFile image) throws IOException;

}
