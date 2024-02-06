package com.example.tourmanagement.controller;

import com.example.tourmanagement.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final FileUtils fileUtils;

    @ResponseBody
    @GetMapping(value = "{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImages(@PathVariable String imageName, @RequestParam String directory, @RequestParam String id) {

        return fileUtils.getImage(imageName, directory, id);
    }


}
