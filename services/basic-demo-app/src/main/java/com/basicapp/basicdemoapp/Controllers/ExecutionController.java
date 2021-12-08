package com.basicapp.basicdemoapp.Controllers;

import com.basicapp.basicdemoapp.DTO.ActionResponseWithAdditionalDetails;
import com.basicapp.basicdemoapp.Services.ExecutionService;
import com.bigid.appinfrastructure.controllers.AbstractExecutionController;
import com.bigid.appinfrastructure.dto.ActionResponseDetails;
import com.bigid.appinfrastructure.dto.ExecutionContext;
import com.bigid.appinfrastructure.dto.ParamDetails;
import com.bigid.appinfrastructure.dto.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

@Controller
public class ExecutionController extends AbstractExecutionController{

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
                ((ExecutionService)executionService).sendIdConnections(executionContext);
                return generateSyncSuccessMessage(executionId, "hello world!");
            case ("sendFileToBigID"):
                ((ExecutionService)executionService).uploadFileToBigID(executionContext);
                return generateSyncSuccessMessage(executionId, "Test file uploaded successfully!");
            case("counter"):
                int count = ((ExecutionService)executionService).count();
                return generateSyncSuccessMessage(executionId, "Counter is at: " + count);
            case("customCredProvider"):
                HashMap additionalData = new HashMap();
                additionalData.put("username", "bigid_tests");
                additionalData.put("password", "bigid_tests");
                putCredentialProviderCustomQuery(executionContext, additionalData);

                return ResponseEntity.status(200).body(new ActionResponseWithAdditionalDetails(executionId,
                    StatusEnum.COMPLETED, 1, "Sent Password Successfuly", additionalData));
            default:
                return ResponseEntity.badRequest().body(
                        new ActionResponseDetails(executionId,
                                StatusEnum.ERROR,
                                0d,
                                "Got unresolved action = " + action));
        }
    }

    private void putCredentialProviderCustomQuery(ExecutionContext executionContext, HashMap additionalData) {
        try {
             ParamDetails param = executionContext.getActionParams().get(0);
             if (param.getParamName().equals("credentialProviderCustomQuery")){
                 additionalData.put("username", "bigid");
                 additionalData.put("password", "bigidsql");
             }
         } catch (IndexOutOfBoundsException e) {
             System.out.println("Did not receive credentialProviderCustomQuery param");
         }
    }
}