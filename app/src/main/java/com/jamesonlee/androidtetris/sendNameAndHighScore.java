package com.jamesonlee.androidtetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;

public class sendNameAndHighScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_name_and_high_score);
        TextView t = (TextView) findViewById(R.id.yourScore);
        final int currScore = this.getIntent().getExtras().getInt("currScore");
        t.setText("Game Over! Your score is "+currScore);
        Button b = (Button) findViewById(R.id.sendName);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameField = (EditText) findViewById(R.id.yourName);
                final String name = nameField.getText().toString();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://live.jamesonlee.com/setHighScore.php?name='"+name+"'&score="+currScore+"");
                            Log.e("Test", "http://live.jamesonlee.com/setHighScore.php?name='"+name+"'&score="+currScore+"");
                            url.openStream();
                            Intent gotoHS = new Intent(sendNameAndHighScore.this, highScores.class);
                            gotoHS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            sendNameAndHighScore.this.startActivity(gotoHS);
                        } catch (Exception e){
                            Log.e("Test", e.toString());
                        }
                    }
                }.start();
            }
        });

    }
}
