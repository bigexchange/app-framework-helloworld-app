package com.basicapp.basicdemoapp.services;

import com.bigid.appinfrastructure.coreclient.common.BigIdCoreApiAssetsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BigIdCoreApiAssetsProviderOverride implements BigIdCoreApiAssetsProvider {

    @Value("${bigid.api.base-url}")
    private String baseApiUrl;

    @Override
    public HttpHeaders getCommonHeaders() {
        return null;
    }

    @Override
    public String getBaseApiUrl() {
        return baseApiUrl;
    }
}
