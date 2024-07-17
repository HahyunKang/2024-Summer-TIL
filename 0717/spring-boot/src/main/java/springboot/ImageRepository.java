package springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.data.ChildImage;

import java.awt.*;

public interface ImageRepository extends JpaRepository<ChildImage,Long> {

}
