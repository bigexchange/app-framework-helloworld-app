package com.basicapp.basicdemoapp.Controllers;

import com.basicapp.basicdemoapp.Services.ReadfileService;
import com.bigid.appinfra.appinfrastructure.controllers.AbstractManifestController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class ManifestController extends AbstractManifestController {

    @Override
    public String getManifest() {
        try {
            ClassPathResource resource = new ClassPathResource("Manifest");
            InputStream inputStream = resource.getInputStream();
            return ReadfileService.readFileContentFromInputStream(inputStream);

        } catch (IOException ex){
            System.out.println(ex);
        }

        return "Unable to receive manifest!";
    }
}
