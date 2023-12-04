package bg.softuni.cloudinary2.web;

import bg.softuni.cloudinary2.repository.PictureRepository;
import bg.softuni.cloudinary2.binding.PictureBindingModel;
import bg.softuni.cloudinary2.model.entity.PictureEntity;
import bg.softuni.cloudinary2.model.view.PictureViewModel;
import bg.softuni.cloudinary2.service.CloudinaryImage;
import bg.softuni.cloudinary2.service.CloudinaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    @DeleteMapping("/pictures/delete")
    public String delete(@RequestParam("publicId") String publicId){
        if(cloudinaryService.delete(publicId)) {
            pictureRepository.deleteAllByPublicId(publicId);
        }
        return "redirect:/pictures/all";
    }

    @GetMapping("/pictures/all")
    public String all(Model model){
        List<PictureViewModel> pictures = pictureRepository
                .findAll()
                .stream()
                .map(this::asViewModel)
                .collect(Collectors.toList());

        model.addAttribute("pictures", pictures);

        return "all";
    }

    private PictureViewModel asViewModel(PictureEntity picture) {
        return new PictureViewModel()
                .setPublicId(picture.getPublicId())
                .setTitle(picture.getTitle())
                .setUrl(picture.getUrl());
    }

    @PostMapping("pictures/all")
    public String allPictures() {
        //todo
        return "redirect:/pictures/all";
    }
}
