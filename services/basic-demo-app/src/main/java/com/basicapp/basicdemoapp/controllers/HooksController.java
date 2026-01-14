package com.basicapp.basicdemoapp.controllers;

import com.bigid.appinfrastructure.controllers.AbstractInstallationHookController;
import com.bigid.appinfrastructure.dto.ExecutionContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

@Slf4j
@RestController
public class HooksController extends AbstractInstallationHookController {
    Logger logger = LoggerFactory.getLogger(ExecutionController.class);


    @Override
    public void executePostInstall(Boolean isUpdate, ExecutionContext executionContext) {
        logger.info("postInstallation hook was called");
    }
}
