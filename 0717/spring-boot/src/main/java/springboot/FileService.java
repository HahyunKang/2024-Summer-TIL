package springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springboot.data.ChildImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final ImageRepository imageRepository;
    private final FileHandler fileHandler;

    @Autowired
    public FileService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        this.fileHandler = new FileHandler();
    }

    public List<ChildImage> addChildImage(List<MultipartFile> files) throws Exception {
        // 파일을 저장하고 그 ChildImage 에 대한 list 를 가지고 있는다
        List<ChildImage> list = fileHandler.parseFileInfo(files);

        if (list.isEmpty()){
            // TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
        }
        // 파일에 대해 DB에 저장하고 가지고 있을 것
        else{
            List<ChildImage> pictureBeans = new ArrayList<>();
            for (ChildImage ChildImages : list) {
                pictureBeans.add(imageRepository.save(ChildImages));
            }
        }
        return list;
    }
    public List<ChildImage> findChildImages() {
        return imageRepository.findAll();
    }
    public Optional<ChildImage> findChildImage(Long id) {
        return imageRepository.findById(id);
    }
}
