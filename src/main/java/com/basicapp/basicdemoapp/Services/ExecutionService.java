package com.basicapp.basicdemoapp.Services;

import com.bigid.appinfra.appinfrastructure.DTO.ActionResponseDetails;
import com.bigid.appinfra.appinfrastructure.DTO.ExecutionContext;
import com.bigid.appinfra.appinfrastructure.DTO.StatusEnum;
import com.bigid.appinfra.appinfrastructure.ExternalConnections.BigIDProxy;
import com.bigid.appinfra.appinfrastructure.Services.AbstractExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@Service
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
