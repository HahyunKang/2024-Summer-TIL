package springboot;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springboot.data.ChildImage;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final FileService fileService;

    @PostMapping("/upload")
    public String uploadImage(@Validated @RequestParam("files") List<MultipartFile> files) throws Exception {
//        이미지
        List<ChildImage> boards = fileService.addChildImage(files);
        return "redirect:/";
    }
}