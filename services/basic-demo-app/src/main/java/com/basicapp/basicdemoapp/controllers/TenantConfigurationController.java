package com.basicapp.basicdemoapp.Controllers;

import com.bigid.appinfrastructure.controllers.AbstractTenantConfigurationController;
import com.bigid.appinfrastructure.dto.TenantConfiguration;
import org.springframework.stereotype.Controller;

@Controller
public class TenantConfigurationController extends AbstractTenantConfigurationController {
    @Override
    public String configureTenant(TenantConfiguration tenantConfiguration) {
        return null;
    }
}
