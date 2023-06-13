package com.basicapp.basicdemoapp.services;

import com.bigid.appinfrastructure.exceptions.ActionsHubServiceException;
import com.bigid.appinfrastructure.services.ActionsHubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigureService {
    Logger logger = LoggerFactory.getLogger(ExecutionService.class);
    ActionsHubService actionsHubService;

    @Autowired
    public ConfigureService(ActionsHubService actionsHubService) {
        this.actionsHubService = actionsHubService;
    }

    public void registerHelloWorldActionAsCommand() {
        try {
            actionsHubService.registerActionAsCommand("Basic demo app", "feedbackAction", "feedbackActionCommand");
        } catch (ActionsHubServiceException e) {
            logger.error("Could not register feedback action as command");
        }
    }
}

