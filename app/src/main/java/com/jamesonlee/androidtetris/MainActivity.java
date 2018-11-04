package com.jamesonlee.androidtetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startTetris = (Button) findViewById(R.id.startButton);

        startTetris.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent startGame = new Intent(MainActivity.this, mainGame.class);
                MainActivity.this.startActivity(startGame);
            }
        });

        final Button highScore = (Button) findViewById(R.id.highScoreButton);

        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHS = new Intent(MainActivity.this, highScores.class);
                MainActivity.this.startActivity(goToHS);
            }
        });



    }
}
