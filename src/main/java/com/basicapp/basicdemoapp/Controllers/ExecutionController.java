package com.basicapp.basicdemoapp.Controllers;

import com.basicapp.basicdemoapp.Services.ExecutionService;
import com.bigid.appinfra.appinfrastructure.Controllers.AbstractExecutionController;
import com.bigid.appinfra.appinfrastructure.DTO.ActionResponseDetails;
import com.bigid.appinfra.appinfrastructure.DTO.ExecutionContext;
import com.bigid.appinfra.appinfrastructure.DTO.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExecutionController extends AbstractExecutionController{

    @Autowired
    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Override
    public ResponseEntity<ActionResponseDetails> postResponse(@RequestBody ExecutionContext executionContext) {
        String action = executionContext.getActionName();
        String executionId = executionContext.getExecutionId();
        switch (action) {
            case ("helloWorld"):
                ((ExecutionService)executionService).sendIdConnections(executionContext);
                return generateSyncSuccessMessage(executionId, "hello world!");
            default:
                return ResponseEntity.badRequest().body(
                        new ActionResponseDetails(executionId,
                                StatusEnum.ERROR,
                                0d,
                                "Got unresolved action = " + action));
        }
    }

    @GetMapping("/hello")
    @ResponseBody
    public ResponseEntity<ActionResponseDetails> helloWorld () {
        return generateSyncSuccessMessage("dummy", "hello world!");
    }
}