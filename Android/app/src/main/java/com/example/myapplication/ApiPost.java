package com.example.myapplication;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.example.myapplication.Config.HOST;

/**
 * Created by lucasfronza on 07/11/15.
 */


class ApiPost extends AsyncTask<String, String, String>{

    @Override
    protected String doInBackground(String... params) {
        String path = params[0];
        String query = params[1];
        String body = params[2];
        String responseString = null;
        try {
            URI uri = null;
            HttpResponse response = null;

            try {
                uri = URIUtils.createURI("http", HOST, 8080, path, query, null);

                HttpPost post = new HttpPost(uri);
                //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                //nameValuePairs.add(new BasicNameValuePair("", "{\"username\": \"example\", \"email\": \"user@example.com\"}"));
                //post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                post.setEntity(new StringEntity(body));
                post.setHeader("Accept", "application/json");
                post.setHeader("Content-type", "application/json; charset=UTF-8");
                //post.setEntity(new UrlEncodedFormEntity( "{\"username\": \"example\", \"email\": \"user@example.com\"}" ));
                //post.setHeaders(headers);
                //post.setEntity(new StringEntity(stringEntity, "UTF-8"));
                HttpClient client = new DefaultHttpClient();
                response = client.execute(post);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //System.out.println(response.getStatusLine().getStatusCode());
            responseString = EntityUtils.toString(response.getEntity());
            //System.out.println(responseString);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO Handle problems..
        }
        //System.out.println(responseString);
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //Do anything with response..
    }
}