//package com.basicapp.basicdemoapp.Services;
//
//import com.basicapp.basicdemoapp.Configurations.BigIDConnectionConfiguration;
//import com.basicapp.basicdemoapp.Configurations.BigIDCredentials;
//import com.bigid.appinfra.appinfrastructure.ExternalConnections.BigIDProxy;
//import com.bigid.appinfra.appinfrastructure.ExternalConnections.RemoteClient;
//import com.google.gson.Gson;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class BigIDProxyImpl extends BigIDProxy {
//
//    @Autowired
//    private BigIDConnectionConfiguration bigIDConnectionConfiguration;
//
//    public BigIDProxyImpl(RemoteClient remoteClient) {
//        super(remoteClient);
//        postRequestToGetBigIDToken();
//    }
//
//    public void postRequestToGetBigIDToken(){
//        try (CloseableHttpClient client = remoteClient.getHttpClient()) {
//            HttpPost httpPost = new HttpPost(this.getBigidUrl() + "/sessions");
//
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
//
//            StringEntity entity = new StringEntity(new Gson().toJson(bigIDConnectionConfiguration.getBigIDCredentials()));
//            httpPost.setEntity(entity);
//            HttpResponse httpResponse = client.execute(httpPost);
//            log.info("httpResponse: " + httpResponse.getStatusLine());
//        } catch (Exception e) {
//            log.error(e.toString());
//        }
//    }
//}
