package com.basicapp.basicdemoapp.controllers;

import com.bigid.appinfrastructure.controllers.AbstractInstallationHookController;
import com.bigid.appinfrastructure.dto.ExecutionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HooksController extends AbstractInstallationHookController {


    @Override
    public void executePostInstall(Boolean isUpdate, ExecutionContext executionContext) {
        log.info("postInstallation hook was called");
    }
}
