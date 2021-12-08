package com.basicapp.basicdemoapp.Services;

import com.bigid.appinfrastructure.dto.ActionResponseDetails;
import com.bigid.appinfrastructure.dto.ExecutionContext;
import com.bigid.appinfrastructure.dto.StatusEnum;
import com.bigid.appinfrastructure.externalconnections.BigIDProxy;
import com.bigid.appinfrastructure.services.AbstractExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@Service
@ComponentScan("com.bigid.appinfrastructure.externalconnections")
public class ExecutionService extends AbstractExecutionService {

    private final String ID_CONNECTIONS_ENDPOINT = "id_connections";

    @Autowired
    public ExecutionService(BigIDProxy bigIDProxy) {
        super(bigIDProxy);
    }

    public void sendIdConnections(ExecutionContext executionContext) {
        String idConnections = bigIDProxy.executeHttpGet(ID_CONNECTIONS_ENDPOINT);
        System.out.println(idConnections);

        ActionResponseDetails actionResponseDetails = initializeResponse(executionContext,
                StatusEnum.COMPLETED,
                1,
                "logged list of entity sources connections successfully!"
        );
        bigIDProxy.updateActionStatusToBigID(executionContext.getExecutionId(), actionResponseDetails);
    }

    public void uploadFileToBigID(ExecutionContext executionContext){
        File file = new File("./test_file.txt");
        try {
            file.createNewFile();
            PrintWriter writer = new PrintWriter("./test_file.txt", "UTF-8");
            writer.println("Test file uploaded!");
            writer.close();

            bigIDProxy.uploadAttachment(executionContext.getExecutionId(), file);
        } catch (IOException e){
            System.out.println("Could not upload file" + e.toString());
        }
    }

    public int count(){
        int counter;
        String counterInString = bigIDProxy.getValueFromAppStorage("count");
        if (StringUtils.isEmpty(counterInString)){
            counter = 1;
            bigIDProxy.saveInStorage("count","1");
        } else {
            counter = Integer.parseInt(counterInString);
            counter++;
            bigIDProxy.saveInStorage("count",Integer.toString(counter));
        }
        System.out.println(counter);
        return counter;
    }
}
