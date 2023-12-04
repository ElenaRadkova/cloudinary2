package bg.softuni.cloudinary2.service.impl;

import bg.softuni.cloudinary2.service.CloudinaryImage;
import bg.softuni.cloudinary2.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";
    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public CloudinaryImage upload(MultipartFile multipartFile) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

       try {
           @SuppressWarnings("unchecked")
           Map<String, String> uploadResult = cloudinary
                   .uploader()
                   .upload(tempFile, Map.of());

           String url = uploadResult.getOrDefault(URL, "https://www.google.com/url?sa=i&url=https%3A%2F%2Ffreefrontend.com%2Fhtml-funny-404-pages%2F&psig=AOvVaw15RuZ1DiIUH_7wnD4vMsHD&ust=1701783686357000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCNCGhrP09YIDFQAAAAAdAAAAABAE");
           String publicId = uploadResult.getOrDefault(PUBLIC_ID, "");

           return new CloudinaryImage()
                   .setPublicId(publicId)
                   .setUrl(url);
       } finally {
           tempFile.delete();
       }
    }

    @Override
    public boolean delete(String publicId) {
        try {
            this.cloudinary.uploader().destroy(publicId, Map.of());
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
