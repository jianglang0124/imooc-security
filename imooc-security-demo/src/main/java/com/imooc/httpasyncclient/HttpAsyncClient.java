package com.imooc.httpasyncclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;

public class HttpAsyncClient {


    private CloseableHttpAsyncClient  httpClient;

    public  HttpAsyncClient(){
        httpClient  = HttpAsyncClients.createDefault();
        httpClient.start();
    }

    public void  execute(HttpUriRequest request, FutureCallback<HttpResponse> callback){
         httpClient.execute(request,callback);
    }

    public  void  close() throws IOException {
        httpClient.close();
    }


}
