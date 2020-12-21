package com.basicapp.basicdemoapp.Controllers;

import com.basicapp.basicdemoapp.DTO.ActionResponseWithAdditionalDetails;
import com.basicapp.basicdemoapp.Services.ExecutionService;
import com.bigid.appinfra.appinfrastructure.Controllers.AbstractExecutionController;
import com.bigid.appinfra.appinfrastructure.DTO.ActionResponseDetails;
import com.bigid.appinfra.appinfrastructure.DTO.ExecutionContext;
import com.bigid.appinfra.appinfrastructure.DTO.StatusEnum;
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
                additionalData.put("username", "bigid_username");
                additionalData.put("password", "bigid_password");
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
}