package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private final String USER_AGENT = "Mozilla/5.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        try {
            String ret = "false";
            EditText login = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            JSONObject json = new JSONObject();
            try {
                json.put("login", login.getText());
                json.put("password", password.getText());
                ret = new ApiPost().execute("/user/login", "", json.toString()).get();

            } catch (Exception e) {
                ;
            }
            if (ret.equals("false")) {
                if (login.getText().length() == 0 || password.getText().length() == 0)
                    Toast.makeText(getApplicationContext(), "Username and Password are required!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Username and Password don't match!", Toast.LENGTH_SHORT).show();
            } else {
                json = new JSONObject(ret);
                Intent intent = new Intent(this, Tasks.class);
                intent.putExtra("accesstoken", json.get("accesstoken").toString());
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void signUp(View view) {
        try {
            String ret = "false";
            EditText login = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            JSONObject json = new JSONObject();
            try {
                json.put("login", login.getText());
                json.put("password", password.getText());
                ret = new ApiPost().execute("/user", "", json.toString()).get();
                //System.out.println(ret);
            } catch (Exception e) {
                ;
            }
            if (ret.equals("false")) {
                Toast.makeText(getApplicationContext(), "Username and Password are required!", Toast.LENGTH_SHORT).show();
            } else {
                this.login(null);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }


}
