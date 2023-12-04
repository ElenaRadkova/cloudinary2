package bg.softuni.cloudinary2.web;

import bg.softuni.cloudinary2.PictureRepository;
import bg.softuni.cloudinary2.binding.PictureBindingModel;
import bg.softuni.cloudinary2.model.entity.PictureEntity;
import bg.softuni.cloudinary2.service.CloudinaryImage;
import bg.softuni.cloudinary2.service.CloudinaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class PicturesController {
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public PicturesController(CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }

    @GetMapping("/pictures/add")
    public String addPicture(){
        return "add";
    }

    @PostMapping("pictures/add")
    public String addPicture(PictureBindingModel bindingModel) throws IOException {
        var picture = createPictureEntity(bindingModel.getPicture(), bindingModel.getTitle());
        pictureRepository.save(picture);

        return "redirect:/pictures/all";
    }

    private PictureEntity createPictureEntity(MultipartFile file, String title) throws IOException {
        final CloudinaryImage upload = this.cloudinaryService.upload(file);
        return new PictureEntity()
                .setPublicId(upload.getPublicId())
                .setTitle(title)
                .setUrl(upload.getUrl());
    }

    @GetMapping("/pictures/all")
    public String all(){
        return "all";
    }
    @PostMapping("pictures/all")
    public String allPictures() {
        //todo
        return "redirect:/pictures/all";
    }
}
