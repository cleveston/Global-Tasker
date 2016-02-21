package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Arrays;

public class NewTask extends AppCompatActivity {

    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Intent intent = getIntent();
        accessToken = intent.getStringExtra("accesstoken");
    }

    public void generateArrayData(View view) {
        EditText text = (EditText) findViewById(R.id.taskData);
        int size = (int) (Math.random()*1000+500);
        int [] array = new int[size];


        for (int i=0; i<size; i++) {
            array[i] = ((int) (Math.random()*1000000+1));
        }

        String data = Arrays.toString(array);
        text.setText(data);

        Toast.makeText(getApplicationContext(), "Array with "+ size + " elements created.", Toast.LENGTH_SHORT).show();
    }

    //send task to server and return to task list
    public void sendTask(View v) {

        try {
            String ret = "false";
            //TODO get task type
            EditText data = (EditText) findViewById(R.id.taskData);
            JSONObject json = new JSONObject();
            try {
                json.put("task", data.getText());
                json.put("type", "a");//TODO get task type
                ret = new ApiPost().execute("/task", "access-token="+accessToken, json.toString()).get();

            } catch (Exception e) {
                ;
            }

            if (ret.equals("true")) {
                Toast.makeText(getApplicationContext(), "Task created", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "An error occured. Probably you have a malformed data.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }

    }
}
