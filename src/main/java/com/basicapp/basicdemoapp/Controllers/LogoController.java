package com.basicapp.basicdemoapp.Controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LogoController {

    @GetMapping(
            value = "/assets/icon",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @ResponseBody
    public byte[] getLogo(HttpServletResponse response) {
        try {
            ClassPathResource image = new ClassPathResource("demo-all-logo.jpg");
            StreamUtils.copy(image.getInputStream(), response.getOutputStream());
        } catch (IOException ex){
            System.out.println(ex);
        }

        return null;
    }
}
