package com.vicente.microservicioproductos.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageUploadService {

    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "ddjupcyst",
            "api_key", "581283457538276",
            "api_secret", "NnLmsop6Wb5q7VfcGWoW-fY1l9w"
        )
    );

    public Map<String, String> uploadImage(MultipartFile file) {
        try {
            Path tempFile = Files.createTempFile(null, null);
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            Map<String, Object> uploadResult = cloudinary.uploader().upload(tempFile.toFile(), ObjectUtils.emptyMap());
            Files.delete(tempFile);
            Map<String, String> result = new HashMap<>();
            result.put("url", (String) uploadResult.get("url"));
            result.put("publicId", (String) uploadResult.get("public_id"));
            return result;
        } catch (IOException exception) {
            throw new RuntimeException("No ha sido posible subir la imagen", exception);
        }
    }

    public String deleteImage(String publicId) {
        try {
            Map<String, String> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return (String) result.get("result");
        } catch (IOException exception) {
            throw new RuntimeException("Could not delete image", exception);
        }
    }

}
