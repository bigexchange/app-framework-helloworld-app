package com.basicapp.basicdemoapp.Controllers;

import com.bigid.appinfrastructure.controllers.AbstractLogoController;
import org.springframework.stereotype.Controller;

@Controller
class IconController extends AbstractLogoController {
    public String getSideBarIconPath(){
        return "demo-app-logo.jpg";
    }
    public String getIconPath(){
        return "demo-app-logo.jpg";

    }
}
