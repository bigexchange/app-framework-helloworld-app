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

import java.io.IOException;

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
        bigIDProxy.updateActionStatusToBigID(actionResponseDetails);
    }

    public void uploadFileToBigID(ExecutionContext executionContext){
        ClassPathResource resource = new ClassPathResource("test.txt");
        try {
            bigIDProxy.uploadAttachment(resource.getFile());
        } catch (IOException e){
            System.out.println("Could not upload file");
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
        return counter;
    }

}
