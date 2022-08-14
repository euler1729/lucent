package com.lucent.backend.api;

import com.lucent.backend.Util.ImageUtil;
import com.lucent.backend.api.Exception.ResourceNotFound;
import com.lucent.backend.api.dto.ImageResponse;
import com.lucent.backend.domain.Image;
import com.lucent.backend.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    @Autowired ImageService imageService;

    /**
     * Returns the image
     * @param fileName given a file name
     * @return the image
     * @throws ResourceNotFound if filename is not found
     */
    @Operation(summary = "Returns an image given a filename")
    @ApiResponse(responseCode = "200", description = "Returns an image given a filename | Open for all")
    @GetMapping("/images/{fileName:.+}")
    public ResponseEntity<byte[]> getImages(@PathVariable String fileName) throws ResourceNotFound {
        Image image = imageService.findImage(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .body(image.getImage());
    }


}
