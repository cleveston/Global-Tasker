package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Tasks extends AppCompatActivity {

    private String accessToken;
    private Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        thisContext = this;
        Intent intent = getIntent();
        accessToken = intent.getStringExtra("accesstoken");
    }

    public void onResume() {
        super.onResume();
        run();
    }

    public void run() {
        // Pegar e listar todas as tasks do usuario
        try {
            String ret = "false";
            String ret2 = "false";
            try {
                ret = new ApiGet().execute("/task", "access-token="+accessToken, "").get();
                ret2 = new ApiGet().execute("/user", "access-token="+accessToken, "").get();

            } catch (Exception e) {
                ;
            }
            if (ret.equals("false")) {//TODO show errors
                //Toast.makeText(getApplicationContext(), "Username and Password are required!", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject json = new JSONObject(ret);
                JSONArray tasksWaiting = json.getJSONArray("tasksWaiting");
                JSONArray tasksFinished = json.getJSONArray("tasksFinished");

                JSONObject json2 = new JSONObject(ret2);
                TextView user_text = (TextView) findViewById(R.id.user_text);
                user_text.setText("Login: " + json2.get("login").toString());
                TextView score_text = (TextView) findViewById(R.id.score_text);
                score_text.setText("Score: " + json2.get("score").toString());

                ListView listViewWaiting = (ListView) findViewById(R.id.listView);
                ListView listViewFinished = (ListView) findViewById(R.id.listView2);

                final ArrayList<String> listWaiting = new ArrayList<String>();
                for (int i = 0; i < tasksWaiting.length(); ++i) {
                    String task = tasksWaiting.get(i).toString();
                    listWaiting.add("Task " + task);
                }
                final ArrayAdapter adapterWaiting = new ArrayAdapter(this,
                        android.R.layout.simple_list_item_1, listWaiting);
                listViewWaiting.setAdapter(adapterWaiting);

                final ArrayList<String> listFinished = new ArrayList<String>();
                for (int i = 0; i < tasksFinished.length(); ++i) {
                    String task = tasksFinished.get(i).toString();
                    listFinished.add("Task " + task);
                }
                final ArrayAdapter adapterFinished = new ArrayAdapter(this,
                        android.R.layout.simple_list_item_1, listFinished);
                listViewFinished.setAdapter(adapterFinished);
                listViewFinished.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String item = (String) adapterFinished.getItem(position);

                        String ret = "false";
                        try {
                            ret = new ApiGet().execute("/task/" + item.substring(5), "access-token=" + accessToken, "").get();
                            JSONObject jsonObject = new JSONObject(ret);
                            System.out.println(ret);
                            String array0 = jsonObject.get("subTask_0").toString();
                            String array1 = jsonObject.get("subTask_1").toString();
                            String array2 = jsonObject.get("subTask_2").toString();
                            array0 = array0.substring(1, array0.length() - 1);//take out '[' and ']' from the string;
                            array1 = array1.substring(1, array1.length() - 1);
                            array2 = array2.substring(1, array2.length() - 1);
                            String[] parts = array0.split(", ");
                            int[] numbers0 = new int[parts.length];
                            for (int n = 0; n < parts.length; n++) {
                                numbers0[n] = Integer.parseInt(parts[n]);
                            }
                            parts = array1.split(", ");
                            int[] numbers1 = new int[parts.length];
                            for (int n = 0; n < parts.length; n++) {
                                numbers1[n] = Integer.parseInt(parts[n]);
                            }
                            parts = array2.split(", ");
                            int[] numbers2 = new int[parts.length];
                            for (int n = 0; n < parts.length; n++) {
                                numbers2[n] = Integer.parseInt(parts[n]);
                            }
                            int[] merge1 = Auxiliar.merge(numbers0, numbers1);
                            int[] finalArray = Auxiliar.merge(merge1, numbers2);

                            Intent intent = new Intent(thisContext, Result.class);
                            intent.putExtra("result", Arrays.toString(finalArray));
                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void newTask(View view) {
        Intent intent = new Intent(this, NewTask.class);
        intent.putExtra("accesstoken", accessToken);
        startActivity(intent);
    }

    public void subtasks(View view) {
        Intent intent = new Intent(this, Subtask.class);
        intent.putExtra("accesstoken", accessToken);
        startActivity(intent);
    }
}
