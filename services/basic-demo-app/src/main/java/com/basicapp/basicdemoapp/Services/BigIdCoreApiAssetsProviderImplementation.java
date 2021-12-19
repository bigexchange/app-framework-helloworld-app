package com.basicapp.basicdemoapp.Services;

import com.bigid.appinfrastructure.coreclient.common.BigIdCoreApiAssetsProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class BigIdCoreApiAssetsProviderImplementation implements BigIdCoreApiAssetsProvider {
    @Override
    public HttpHeaders getCommonHeaders() {
        return null;
    }

    @Override
    public String getBaseApiUrl() {
        return null;
    }
}
