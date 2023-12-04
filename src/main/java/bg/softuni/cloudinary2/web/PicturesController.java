package bg.softuni.cloudinary2.web;

import bg.softuni.cloudinary2.binding.PictureBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PicturesController {

    @GetMapping("/pictures/add")
    public String addPicture(){
        return "add";
    }
    @PostMapping("pictures/add")
    public String addPicture(PictureBindingModel bindingModel) {
        //todo
        return "redirect:/pictures/all";
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
