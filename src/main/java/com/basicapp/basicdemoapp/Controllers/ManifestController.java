package com.basicapp.basicdemoapp.Controllers;

import com.bigid.appinfra.appinfrastructure.Controllers.AbstractLoggingController;
import com.bigid.appinfra.appinfrastructure.Controllers.AbstractManifestController;
import org.springframework.stereotype.Controller;

@Controller
public class ManifestController extends AbstractManifestController {

    @Override
    public String getManifest() {
        return "1. log1\n" +
                "2. log2 \n" +
                "3. log3";
    }
}
