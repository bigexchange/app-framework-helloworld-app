package com.basicapp.basicdemoapp.controllers;

import com.basicapp.basicdemoapp.services.ConfigureService;
import com.bigid.appinfrastructure.controllers.AbstractTenantConfigurationController;
import com.bigid.appinfrastructure.dto.TenantConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TenantConfigurationController extends AbstractTenantConfigurationController {
    ConfigureService configureService;

    @Autowired
    public TenantConfigurationController(ConfigureService configureService) {
        this.configureService = configureService;
    }

    @Override
    public String configureTenant(TenantConfiguration tenantConfiguration) {
        configureService.registerHelloWorldActionAsCommand();
        return null;
    }
}
