import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileInputStream;
import java.io.OutputStream;

@Controller
public class ImgController {
    @RequestMapping("/image/{fileName}.{suffix}")
    public void sendPic(@PathVariable("fileName") String fileName, @PathVariable("suffix") String suffix) {
        System.out.println(fileName + suffix);
    }
}
