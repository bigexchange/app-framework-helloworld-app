package com.basicapp.basicdemoapp.Services;

import com.bigid.appinfra.appinfrastructure.DTO.ActionResponseDetails;
import com.bigid.appinfra.appinfrastructure.DTO.ExecutionContext;
import com.bigid.appinfra.appinfrastructure.DTO.StatusEnum;
import com.bigid.appinfra.appinfrastructure.Services.AbstractExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService extends AbstractExecutionService {

    @Autowired
    public ExecutionService(BigIDProxyImpl bigIDProxy) {
        super(bigIDProxy);
    }

    public void sendIdConnections(ExecutionContext executionContext) {
        String idConnections = ((BigIDProxyImpl) bigIDProxy).applyIdConnection();
        System.out.println(idConnections);

        ActionResponseDetails actionResponseDetails = initializeResponse(executionContext,
                StatusEnum.COMPLETED,
                1,
                "Sent list of entity sources connections to slack successfully!"
        );
        ((BigIDProxyImpl) bigIDProxy).sendResponseToBigIDCallback(actionResponseDetails);
    }

}
