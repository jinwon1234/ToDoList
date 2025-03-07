package springpr.toyproject.fileprocess;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@Slf4j
public class ImageProcessController {

    @Value("${userImage.dir}")
    private String userImageDir;
    @GetMapping("/images/{fileDir}")
    public Resource getImage(@PathVariable String fileDir) throws MalformedURLException {
        log.info(userImageDir + fileDir);
        return new UrlResource("file:" + userImageDir + fileDir);
    }
}
