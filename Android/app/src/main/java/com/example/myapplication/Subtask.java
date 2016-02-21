package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Subtask extends AppCompatActivity {

    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtasks);

        Intent intent = getIntent();
        accessToken = intent.getStringExtra("accesstoken");

        try {
            String ret = "false";
            try {
                ret = new ApiGet().execute("/subtask", "access-token=" + accessToken, "").get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ret.equals("false")) {//TODO show errors
                //Toast.makeText(getApplicationContext(), "Username and Password are required!", Toast.LENGTH_SHORT).show();
            } else {

                JSONArray tasksWaiting = new JSONArray(ret);

                ListView listview = (ListView) findViewById(R.id.listView);

                final ArrayList<SubtaskItem> list = new ArrayList<SubtaskItem>();
                for (int i = 0; i < tasksWaiting.length(); i++) {
                    JSONObject jsonObject = tasksWaiting.getJSONObject(i);
                    Integer idsubtask = jsonObject.getInt("idsubtask");
                    String login = jsonObject.getString("login");
                    String type = jsonObject.getString("type");
                    Integer score = jsonObject.getInt("score");

                    list.add(new SubtaskItem(idsubtask, login, type, score));
                }

                final SubtaskAdapter adapter = new SubtaskAdapter(this, list);
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        SubtaskItem item = adapter.getItem(position);

                        String ret = "false";
                        try {
                            ret = new ApiGet().execute("/subtask/"+item.getIdsubtask(), "access-token=" + accessToken, "").get();
                            JSONObject jsonObject = new JSONObject(ret);
                            System.out.println(ret);
                            String array = jsonObject.get("subtask").toString();
                            array = array.substring(1, array.length()-1);//take out '[' and ']' from the string;
                            String[] parts = array.split(",");
                            int[] numbers = new int[parts.length];
                            for(int n = 0; n < parts.length; n++) {
                                numbers[n] = Integer.parseInt(parts[n]);
                            }

                            Arrays.sort(numbers);


                            JSONObject json = new JSONObject();
                            try {
                                json.put("result", Arrays.toString(numbers));

                                ret = new ApiPut().execute("/subtask/"+item.getIdsubtask(), "access-token=" + accessToken, json.toString()).get();

                                finish();
                                Intent intent = getIntent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(getIntent());
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Error. Please try again later.", Toast.LENGTH_SHORT).show();
                            }


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


}
