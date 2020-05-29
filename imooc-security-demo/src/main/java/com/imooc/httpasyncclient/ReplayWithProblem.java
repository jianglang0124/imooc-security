package com.imooc.httpasyncclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.concurrent.FutureCallback;

import java.util.ArrayList;
import java.util.List;

public class ReplayWithProblem {

    public List<HttpUriRequest> loadMockRequest(int n){

        List<HttpUriRequest> cache = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            HttpGet request = new HttpGet("https://www.baidu.com?a="+i);
            cache.add(request);
        }
        return  cache;
    }

    public  void  start(List<HttpUriRequest> cache) throws  Exception{
        HttpAsyncClient httpAsyncClient = new HttpAsyncClient();
        int i=0;

        while (true){

            final  HttpUriRequest request = cache.get(i % cache.size());
            httpAsyncClient.execute(request, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse response) {
                    System.out.println(request.getRequestLine()+"->"+response.getStatusLine());
                }
                @Override
                public void failed(Exception e) {
                    System.out.println(request.getRequestLine()+"->"+e);
                }
                @Override
                public void cancelled() {
                    System.out.println(request.getRequestLine()+" cancelled");
                }
            });
            i++;
            Thread.sleep(100);
        }
    }




}
