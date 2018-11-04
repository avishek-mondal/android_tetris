package com.jamesonlee.androidtetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class highScores extends AppCompatActivity {
    private ListView listView;
    ArrayList<String> highScores;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        Button backButton = (Button) findViewById(R.id.buttonBack);
        highScores = new ArrayList<>();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(highScores.this, MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                highScores.this.startActivity(home);
            }
        });

        listView = (ListView) findViewById(R.id.HSView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, highScores);
        listView.setAdapter(arrayAdapter);
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://live.jamesonlee.com/getHighScore.php");
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    String highScoreData = in.readLine();

                    String[] values = highScoreData.split(",");
                    String temp = "";
                    for (int i = 0; i < values.length; i++) {
                        if (i%2 == 0) {
                            temp = values[i];
                        } else {
                            temp = temp + ": " + values[i];
                            highScores.add(0, temp);
                        }
                    }
                    //arrayAdapter.notifyDataSetChanged();
                } catch (Exception e){
                    highScores.add("Could not get data");
                    //arrayAdapter.notifyDataSetChanged();
                    Log.e("Test", e.toString());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
            }
        }.start();
    }
}
