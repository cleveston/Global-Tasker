package com.example.myapplication;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by lucasfronza on 07/11/15.
 */


class ApiGet extends AsyncTask<String, String, String>{

    @Override
    protected String doInBackground(String... params) {
        String path = params[0];
        String query = params[1];
        String body = params[2];
        URI uri = null;
        HttpResponse response = null;
        String responseString = null;
        try {
            uri = URIUtils.createURI("http", Config.HOST, 8080, path, query, null);
            HttpGet get = new HttpGet(uri);
            //get.setEntity(new StringEntity(body));
            get.setHeader("Accept", "application/json");
            get.setHeader("Content-type", "application/json; charset=UTF-8");
            HttpClient client = new DefaultHttpClient();
            response = client.execute(get);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(response.getStatusLine().getStatusCode());

        try {
            responseString = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            ;
        }

        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}