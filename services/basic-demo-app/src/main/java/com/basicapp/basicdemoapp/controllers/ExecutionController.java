package com.basicapp.basicdemoapp.controllers;

import com.basicapp.basicdemoapp.dto.ActionResponseWithAdditionalDetails;
import com.basicapp.basicdemoapp.services.ExecutionService;
import com.bigid.appinfrastructure.controllers.AbstractExecutionController;
import com.bigid.appinfrastructure.dto.ActionResponseDetails;
import com.bigid.appinfrastructure.dto.ExecutionContext;
import com.bigid.appinfrastructure.dto.ParamDetails;
import com.bigid.appinfrastructure.dto.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@Controller
public class ExecutionController extends AbstractExecutionController{
    Logger logger = LoggerFactory.getLogger(ExecutionController.class);

    @Autowired
    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Override
    public ResponseEntity<ActionResponseDetails> executeAction(@RequestBody ExecutionContext executionContext) {
        String action = executionContext.getActionName();
        String executionId = executionContext.getExecutionId();
        switch (action) {
            case ("helloWorld"):
                String message = ((ExecutionService)executionService).fetchIdConnections(executionContext);
                return generateSyncSuccessMessage(executionId, message);
            case ("sendFileToBigID"):
                ((ExecutionService)executionService).uploadFileToBigID(executionContext);
                return generateSyncSuccessMessage(executionId, "Test file uploaded successfully!");
            case("counter"):
                int count = ((ExecutionService)executionService).count(executionContext);
                return generateSyncSuccessMessage(executionId, "Counter is at: " + count);
            case("customCredProvider"):
                HashMap<String, String> additionalData = new HashMap<>();
                additionalData.put("username", "bigid_tests");
                additionalData.put("password", "bigid_tests");
                putCredentialProviderCustomQuery(executionContext, additionalData);

                return ResponseEntity.status(200).body(new ActionResponseWithAdditionalDetails(executionId,
                    StatusEnum.COMPLETED, 1, "Sent Password Successfully", additionalData));
            default:
                return ResponseEntity.badRequest().body(
                        new ActionResponseDetails(executionId,
                                StatusEnum.ERROR,
                                0d,
                                "Got unresolved action = " + action));
        }
    }

    private void putCredentialProviderCustomQuery(ExecutionContext executionContext, HashMap<String, String> additionalData) {
        try {
             ParamDetails param = executionContext.getActionParams().get(0);
             if (param.getParamName().equals("credentialProviderCustomQuery")){
                 additionalData.put("username", "bigid");
                 additionalData.put("password", "bigidsql");
             }
         } catch (IndexOutOfBoundsException e) {
             logger.error("Did not receive credentialProviderCustomQuery param");
         }
    }
}