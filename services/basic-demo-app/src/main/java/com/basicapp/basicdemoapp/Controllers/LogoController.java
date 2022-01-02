package com.basicapp.basicdemoapp.Controllers;

import com.bigid.appinfrastructure.controllers.AbstractLogoController;
import org.springframework.stereotype.Controller;

@Controller
class IconController extends AbstractLogoController {
    public String getSideBarIconPath(){
        return "side-bar-icon.png";
    }
    public String getIconPath(){
        return "icon.png";

    }
}
