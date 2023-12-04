package bg.softuni.cloudinary2.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    CloudinaryImage upload(MultipartFile file) throws IOException;
    boolean delete(String publicId);
}
