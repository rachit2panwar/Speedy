package com.rachitpanwar.speedy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.security.Key;

public class ResultActivity extends AppCompatActivity {

    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel = (TextView)findViewById(R.id.scoreLabel);
        TextView highScoreLabel = (TextView)findViewById(R.id.highScoreLabel);
        TextView gamesPlayedLabel = (TextView)findViewById(R.id.gamesPlayedLabel);

        int score = getIntent().getIntExtra("SCORE",0);
        scoreLabel.setText(""+score);

        SharedPreferences preferencesScore = getSharedPreferences("HIGHSCORE", Context.MODE_PRIVATE);
        highScore = preferencesScore.getInt("HIGHSCORE",0);

        if(score > highScore){
            highScoreLabel.setText("High Score: "+score);

            SharedPreferences.Editor editor = preferencesScore.edit();
            editor.putInt("HIGHSCORE",score);
            editor.commit();
        }
        else {
            highScoreLabel.setText("High Score :" + highScore);
        }

        SharedPreferences preferencesGames = getSharedPreferences("GAMES",Context.MODE_PRIVATE);
        int games = preferencesGames.getInt("GAMES",0);

        gamesPlayedLabel.setText("Games Played :"+ (games+1));

        SharedPreferences.Editor editor = preferencesGames.edit();
        editor.putInt("GAMES", (games+1));
        editor.commit();
    }

    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(),StartActivity.class));
        finish();
    }

    public boolean dispatchKeyEvent(KeyEvent event){
        if(event.getAction()== KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
