package com.basicapp.basicdemoapp.Services;

import com.bigid.appinfra.appinfrastructure.DTO.ActionResponseDetails;
import com.bigid.appinfra.appinfrastructure.ExternalConnections.AbstractBigIDProxy;
import com.bigid.appinfra.appinfrastructure.ExternalConnections.RemoteClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BigIDProxyImpl extends AbstractBigIDProxy {


    public BigIDProxyImpl(RemoteClient remoteClient) {
        super(remoteClient);
    }

    public String applyIdConnection() {
        String idConnectionsEndpoint = this.getBigIDUrl() + "/id_connections";

        return executeHttpGetRequest(idConnectionsEndpoint);
    }

    public void sendResponseToBigIDCallback(ActionResponseDetails actionResponseDetails) {
        executeHttpPostUpdateCallback(actionResponseDetails);
    }
}
