package com.basicapp.basicdemoapp.services;

import com.bigid.appinfrastructure.dto.*;
import com.bigid.appinfrastructure.externalconnections.BigIDProxy;
import com.bigid.appinfrastructure.services.AbstractExecutionService;
import com.bigid.appinfrastructure.services.AppsConfigurationsManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ExecutionService extends AbstractExecutionService {
    Logger logger = LoggerFactory.getLogger(ExecutionService.class);
    private final AppsConfigurationsManagementService appsConfiguration;

    @Autowired
    public ExecutionService(BigIDProxy bigIDProxy, AppsConfigurationsManagementService appsConfiguration) {
        super(bigIDProxy);
        this.appsConfiguration = appsConfiguration;
    }

    public String fetchIdConnections(ExecutionContext executionContext) {
        String idConnections = bigIDProxy.executeHttpGet(executionContext, "id_connections", false);
        return "Fetched all the ids connections: " + idConnections;
    }

    public String getConfiguration(ExecutionContext executionContext, String configuration) {
        AppConfigurationsParams configurationProperties = AppConfigurationsParams.builder()
                .key(configuration)
                .executionContext(executionContext)
                .build();

        return appsConfiguration.getAppConfiguration(configurationProperties).toString();
    }


    public void feedback(ExecutionContext executionContext) {
        ArrayList<String> filesList = (ArrayList<String>) executionContext.getActionParams().get(0).getParamValue();

        Thread newThread = new Thread(() -> {
            ActionResponseDetails actionResponseDetails = new ActionResponseDetails(executionContext.getExecutionId(), StatusEnum.IN_PROGRESS, 0, "on it");
            ArrayList<SubExecutionItem> subExecutionItemsList = new ArrayList<>();
            try {
                for (String file : filesList) {
                    updateBigidInitialStatuses(file, subExecutionItemsList, executionContext, actionResponseDetails);
                }
                for (SubExecutionItem subExecutionItem : actionResponseDetails.getSubExecutionItems()) {
                    updateBigidSubExecutionCompletedStatuses(subExecutionItem, actionResponseDetails, executionContext);
                }
                updateBigidFinalStatus(actionResponseDetails, executionContext);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        newThread.start();
    }

    private void updateBigidInitialStatuses(String file, ArrayList<SubExecutionItem> subExecutionItemsList, ExecutionContext executionContext, ActionResponseDetails actionResponseDetails) throws InterruptedException {
        Thread.sleep(5000);
        SubExecutionItem subExecutionItem = new SubExecutionItem();
        subExecutionItem.setName(file);
        subExecutionItem.setStatusEnum(StatusEnum.IN_PROGRESS);
        subExecutionItemsList.add(subExecutionItem);
        actionResponseDetails.setSubExecutionItems(subExecutionItemsList.toArray(new SubExecutionItem[0]));
        actionResponseDetails.setProgress(Math.min(0.9, actionResponseDetails.getProgress() + 0.1));
        bigIDProxy.updateActionStatusToBigID(executionContext, actionResponseDetails);
    }

    private void updateBigidSubExecutionCompletedStatuses(SubExecutionItem subExecutionItem, ActionResponseDetails actionResponseDetails, ExecutionContext executionContext) throws InterruptedException {
        Thread.sleep(5000);
        subExecutionItem.setStatusEnum(StatusEnum.COMPLETED);
        actionResponseDetails.setProgress(Math.min(0.9, actionResponseDetails.getProgress() + 0.1));
        bigIDProxy.updateActionStatusToBigID(executionContext, actionResponseDetails);
    }


    private void updateBigidFinalStatus(ActionResponseDetails actionResponseDetails, ExecutionContext executionContext) throws InterruptedException {
        Thread.sleep(5000);
        actionResponseDetails.setProgress(1);
        actionResponseDetails.setStatusEnum(StatusEnum.COMPLETED);
        actionResponseDetails.setMessage("execution is finally over!");
        HashMap<String, String> additionalData = new HashMap<>();
        additionalData.put("additionDataField", "value1");
        additionalData.put("anotherAdditionDataField", "value2");
        actionResponseDetails.setAdditionalData(additionalData);
        bigIDProxy.updateActionStatusToBigID(executionContext, actionResponseDetails);
    }

    public void uploadFileToBigID(ExecutionContext executionContext) {
        File file = new File("./test_file.txt");
        try {
            file.createNewFile();
            PrintWriter writer = new PrintWriter("./test_file.txt", "UTF-8");
            writer.println("Test file uploaded!");
            writer.close();

            bigIDProxy.uploadAttachment(executionContext, file);
        } catch (IOException e) {
            logger.error("Could not upload file" + e.toString());
        }
    }

    public int count(ExecutionContext executionContext) {
        String storageKey = "count";
        int counter;
        String counterInString = bigIDProxy.getValueFromAppStorage(executionContext, storageKey);
        if (StringUtils.isEmpty(counterInString)) {
            counter = 1;
            bigIDProxy.saveInStorage(executionContext, storageKey, "1");
        } else {
            counter = Integer.parseInt(counterInString);
            counter++;
            bigIDProxy.saveInStorage(executionContext, storageKey, Integer.toString(counter));
        }
        return counter;
    }
}
