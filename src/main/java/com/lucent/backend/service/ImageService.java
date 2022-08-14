package com.lucent.backend.service;

import com.lucent.backend.Repo.ImageRepo;
import com.lucent.backend.Util.ImageUtil;
import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.ImageResponse;
import com.lucent.backend.domain.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired ImageRepo imageRepo;

    /**
     * Saves an image to the database
     * @param image MultipartFile
     * @return Image
     * @throws IOException exception
     */
    public ImageResponse saveImage(MultipartFile image) throws IOException {
        imageRepo.save(Image.builder()
                        .name(image.getOriginalFilename())
                        .image(ImageUtil.compressImage(image.getBytes()))
                .build());
        return new ImageResponse("Image uploaded successfully: " + image.getOriginalFilename());
    }

    /**
     * Returns image given a filename
     * @param fileName Image name
     * @return image
     * @throws ResourceNotFound if no image is found
     */
    public Image findImage(String fileName) throws ResourceNotFound {
        Optional<Image> image = imageRepo.findByName(fileName);
        if(image.isPresent()){
            return image.get();
        }
        else throw new ResourceNotFound("Image not found");

    }
}
