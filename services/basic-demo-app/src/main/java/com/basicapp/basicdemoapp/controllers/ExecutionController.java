package com.basicapp.basicdemoapp.controllers;

import com.basicapp.basicdemoapp.dto.ActionResponseWithAdditionalDetails;
import com.basicapp.basicdemoapp.services.ExecutionService;
import com.bigid.appinfrastructure.controllers.AbstractExecutionController;
import com.bigid.appinfrastructure.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;

@Slf4j
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
            case("feedbackAction"):
                ((ExecutionService)executionService).feedback(executionContext);
                return generateAsyncSuccessMessage(executionId, "started");
            case("checkHashiProvidedCreds"):
                ParamDetails authParam = findParameterByName(executionContext.getGlobalParams(), "basic_auth");
                ParamDetails username = findParameterByName(executionContext.getGlobalParams(), "username");
                ParamDetails password = findParameterByName(executionContext.getGlobalParams(), "password");

                ObjectMapper mapper = new ObjectMapper();
                try {
                    JsonNode jsonNode = mapper.readTree(authParam.getParamValue());
                    if (jsonNode.get("username").asText().equals("bigid") &&
                        jsonNode.get("password_enc").asText().equals("password") &&
                        username.getParamValue().equals("bigid") &&
                        password.getParamValue().equals("password")) {

                        log.info("Returning success response for checkHashiProvidedCreds");
                        return generateSyncSuccessMessage(executionId, "success");
                    }
                    log.info("Returning failed response for checkHashiProvidedCreds");
                    return generateFailedResponse(executionId, new RuntimeException("Right credentials was not provided to the app by orch/vault"));
                } catch (Exception e) {
                    log.info("Returning failed response for checkHashiProvidedCreds", e);
                    return generateFailedResponse(executionId, e);
                }
            default:
                return ResponseEntity.badRequest().body(
                        new ActionResponseDetails(executionId,
                                StatusEnum.ERROR,
                                0d,
                                "Got unresolved action = " + action));
        }
    }

    private  ParamDetails findParameterByName(List<ParamDetails> paramList, String paramName) {
        return paramList
                .stream()
                .filter((item) -> item.getParamName().equals(paramName))
                .findAny()
                .orElseThrow(() -> new RuntimeException(String.format("paramName(%s) should be presented on request", paramName)));
    }

    private void putCredentialProviderCustomQuery(ExecutionContext executionContext, HashMap<String, String> additionalData) {
        try {
             ActionParamDetails param = executionContext.getActionParams().get(0);
             if (param.getParamName().equals("credentialProviderCustomQuery")){
                 additionalData.put("username", "bigid");
                 additionalData.put("password", "bigidsql");
             }
         } catch (IndexOutOfBoundsException e) {
             logger.error("Did not receive credentialProviderCustomQuery param");
         }
    }
}