package bg.softuni.cloudinary2.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    CloudinaryImage upload(MultipartFile file);
    boolean delete(String publicId);
}
