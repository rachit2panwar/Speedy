package com.rachitpanwar.speedy;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView player;
    private ImageView food;
    private ImageView diamond;
    private ImageView enemy1;
    private ImageView enemy2;
    ImageButton pauseLb;

    private ImageButton pause;
    ImageButton startLb;

    private FrameLayout frameLb;

    private int frameHeight;
    private int playerSize;
    private int screenWidth;
    private int screenHeight;

    //postion
    private int playerY;
    private int foodY;
    private int foodX;
    private int diamondY;
    private int diamondX;
    private int enemy1Y;
    private int enemy1X;
    private int enemy2Y;
    private int enemy2X;

    private int playerSpeed;
    private int foodSpeed;
    private int diamondSpeed;
    private int enemy1Speed;
    private int enemy2Speed;

    private int score = 0;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private boolean action_flg = false;
    private boolean start_flg = false;
    private boolean pause_flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        scoreLabel = (TextView) findViewById(R.id.score_lb);
        startLabel = (TextView) findViewById(R.id.startLb);
        pauseLb = (ImageButton) findViewById(R.id.pause_lb);
        player = (ImageView) findViewById(R.id.player);
        food = (ImageView) findViewById(R.id.food);
        diamond = (ImageView) findViewById(R.id.diamond);
        enemy1 = (ImageView) findViewById(R.id.enemy1);
        enemy2 = (ImageView) findViewById(R.id.enemy2);

        startLb = (ImageButton) findViewById(R.id.start_lb);

        frameLb = (FrameLayout)findViewById(R.id.frame_lb);

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        pauseLb.setEnabled(false);
        frameLb.setVisibility(View.GONE);

        screenWidth = size.x;
        screenHeight = size.y;

        playerSpeed = Math.round(screenWidth / 59);
        foodSpeed = Math.round(screenWidth / 59);
        diamondSpeed = Math.round(screenWidth / 35);
        enemy1Speed = Math.round(screenWidth / 44);
        enemy2Speed = Math.round(screenWidth / 39);

        food.setX(-80f);
        food.setY(-80f);
        diamond.setX(-80f);
        diamond.setY(-80f);
        enemy1.setX(-80f);
        enemy1.setY(-80f);
        enemy2.setX(-80f);
        enemy2.setY(-80f);

        scoreLabel.setText("Score: " + 0);

    }

    public void position(){

        hit();

        foodX -= foodSpeed;
        if(foodX < 0){
            foodX = screenWidth + 20;
            foodY = (int)Math.floor(Math.random()*(frameHeight - food.getHeight()));
        }
        food.setX(foodX);
        food.setY(foodY);

        enemy1X -= enemy1Speed;
        if(enemy1X < 0){
            enemy1X = screenWidth + 10;
            enemy1Y = (int)Math.floor(Math.random()*(frameHeight - enemy1.getHeight()));
        }
        enemy1.setX(enemy1X);
        enemy1.setY(enemy1Y);

        enemy2X -= enemy2Speed;
        if(enemy2X < 0){
            enemy2X = screenWidth + 20;
            enemy2Y = (int)Math.floor(Math.random()*(frameHeight - enemy2.getHeight()));
        }
        enemy2.setX(enemy2X);
        enemy2.setY(enemy2Y);

        diamondX -= diamondSpeed;
        if(diamondX < 0){
            diamondX = screenWidth + 20;
            diamondY = (int)Math.floor(Math.random()*(frameHeight - diamond.getHeight()));
        }
        diamond.setX(diamondX);
        diamond .setY(diamondY);

        if(action_flg==true){

            playerY -=playerSpeed;
            player.setImageResource(R.drawable.player1);
        }
        else{
            playerY +=playerSpeed;
            player.setImageResource(R.drawable.player2);
        }

        if(playerY < 0){
            playerY = 0;
        }

        if(playerY > frameHeight - playerSize){
            playerY = frameHeight - playerSize;
        }

        player.setY(playerY);

        scoreLabel.setText("Score: "+ score);
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {

        if(start_flg == false){
            start_flg = true;

            FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            playerY = (int)player.getY();

            playerSize = player.getHeight();

            startLabel.setVisibility(View.GONE);

            pauseLb.setEnabled(true);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            position();
                        }
                    });
                }
            },0,20);
        }else {
            if(me.getAction() == MotionEvent.ACTION_DOWN){
                action_flg = true;
            }
            else if (me.getAction()==MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }
        return true;
    }
    public void hit(){

        int foodCentreX =  foodX + food.getWidth()/2;
        int foodCentreY =  foodY + food.getHeight()/2;

        if( 0 <= foodCentreX && foodCentreX <= playerSize &&
                playerY <= foodCentreY && foodCentreY<= playerY + playerSize){
            score  = score + 1;
            foodX = -10;
        }

        int diamondCentreX =  diamondX + diamond.getWidth()/2;
        int diamondCentreY =  diamondY + diamond.getHeight()/2;

        if( 0 <= diamondCentreX && diamondCentreX <= playerSize &&
                playerY <= diamondCentreY && diamondCentreY<= playerY + playerSize){
            score  = score + 3;
            diamondX = -10;
        }

        int enemy1CentreX =  enemy1X + enemy1.getWidth()/2;
        int enemy1CentreY =  enemy1Y + enemy1.getHeight()/2;

        if( 0 <= enemy1CentreX && enemy1CentreX <= playerSize &&
                playerY <= enemy1CentreY && enemy1CentreY<= playerY + playerSize){

            timer.cancel();
            timer = null;

            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
            intent.putExtra("SCORE",score);
            startActivity(intent);
        }

        int enemy2CentreX =  enemy2X + enemy2.getWidth()/2;
        int enemy2CentreY =  enemy2Y + enemy2.getHeight()/2;

        if( 0 <= enemy2CentreX && enemy2CentreX <= playerSize &&
                playerY <= enemy2CentreY && enemy2CentreY<= playerY + playerSize){

            timer.cancel();
            timer = null;

            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
            intent.putExtra("SCORE",score);
            startActivity(intent);
        }

    }


}
