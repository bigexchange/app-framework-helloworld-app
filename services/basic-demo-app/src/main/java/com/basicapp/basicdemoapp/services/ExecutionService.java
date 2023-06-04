package com.basicapp.basicdemoapp.services;

import com.bigid.appinfrastructure.dto.ActionResponseDetails;
import com.bigid.appinfrastructure.dto.ExecutionContext;
import com.bigid.appinfrastructure.dto.StatusEnum;
import com.bigid.appinfrastructure.externalconnections.BigIDProxy;
import com.bigid.appinfrastructure.services.AbstractExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class ExecutionService extends AbstractExecutionService {
    Logger logger = LoggerFactory.getLogger(ExecutionService.class);
    @Autowired
    public ExecutionService(BigIDProxy bigIDProxy) {
        super(bigIDProxy);
    }

    public String fetchIdConnections(ExecutionContext executionContext) {
        String idConnections = bigIDProxy.executeHttpGet(executionContext, "id_connections");
        return "Fetched all the ids connections: " + idConnections;
    }

    public void uploadFileToBigID(ExecutionContext executionContext){
        File file = new File("./test_file.txt");
        try {
            file.createNewFile();
            PrintWriter writer = new PrintWriter("./test_file.txt", "UTF-8");
            writer.println("Test file uploaded!");
            writer.close();

            bigIDProxy.uploadAttachment(executionContext, file);
        } catch (IOException e){
            logger.error("Could not upload file" + e.toString());
        }
    }

    public int count(ExecutionContext executionContext){
        String storageKey = "count";
        int counter;
        String counterInString = bigIDProxy.getValueFromAppStorage(executionContext, storageKey);
        if (StringUtils.isEmpty(counterInString)){
            counter = 1;
            bigIDProxy.saveInStorage(executionContext, storageKey,"1");
        } else {
            counter = Integer.parseInt(counterInString);
            counter++;
            bigIDProxy.saveInStorage(executionContext, storageKey,Integer.toString(counter));
        }
        return counter;
    }
}
