package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.opengl.GLES11Ext;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView myScore;
    ImageView bird,raceRoad,raceRoad1,bg1,bg2,colum;
    ImageView start;
    ImageView tl1,tl2,tl3,tl4,tl5;
    RelativeLayout play;
    CountDownTimer countDownTimerStart,countDownTimerBird,countDownTimerPlay,roadTime;
    MediaPlayer flap,fall,point;
    int x=0;
    int screenWitch = 0;
    int screenHeight = 0;
    int r1,r2,r3,r4,r5;
    int score = 0;
    int SPEEP_TL = 10;
    int TIME_PLAY = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSizeWindow();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        bird = findViewById(R.id.bird);
        play = findViewById(R.id.play);
        raceRoad = findViewById(R.id.raceRoad);
        raceRoad1 = findViewById(R.id.raceRoad1);
        bg1 = findViewById(R.id.bg1);
        bg2 = findViewById(R.id.bg2);
        start = findViewById(R.id.startGame);
        myScore = findViewById(R.id.myScore);

        tl1 = findViewById(R.id.tl1);
        tl2 = findViewById(R.id.tl2);
        tl3 = findViewById(R.id.tl3);
        tl4 = findViewById(R.id.tl4);
        tl5 = findViewById(R.id.tl5);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        flap = MediaPlayer.create(this,R.raw.flap);
        fall = MediaPlayer.create(this,R.raw.fall);
        point = MediaPlayer.create(this,R.raw.getpoint);


        setPositionHeightTenLua();

        raceRoad1.setX(screenWitch);
        bg2.setX(screenWitch);

//        tl1.setX(screenWitch);
//        tl1.setY(r1);
//        tl2.setX(screenWitch+100);
//        tl2.setY(r2);
//        tl3.setX(screenWitch+200);
//        tl3.setY(r3);
//        tl4.setX(screenWitch+300);
//        tl4.setY(r4);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPEEP_TL = 10;
                TIME_PLAY = 0;
                score = 0;
                myScore.setText(score+"");
                start.setVisibility(View.GONE);
                play.setEnabled(true);
                bird.setX(screenWitch/2);
                bird.setY(screenHeight/5);
                tl1.setX(screenWitch);
                tl1.setY(r1);
                tl2.setX(screenWitch+100);
                tl2.setY(r2);
                tl3.setX(screenWitch+300);
                tl3.setY(r3);
                tl4.setX(screenWitch+500);
                tl4.setY(r4);
                tl5.setX(screenWitch+700);
                tl5.setY(r5);

                roadTime = new CountDownTimer(6000000,10) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(TIME_PLAY >= 5000 && TIME_PLAY <= 15000){
                            SPEEP_TL = 12;
                        }
                        if(TIME_PLAY > 15000 && TIME_PLAY <= 25000){
                            SPEEP_TL = 14;
                        }
                        if(TIME_PLAY > 25000 && TIME_PLAY <= 35000){
                            SPEEP_TL = 16;
                        }
                        if(TIME_PLAY > 35000){
                            SPEEP_TL = 18;
                        }
                        TIME_PLAY += 10;

                        ItemMove();

                        conditionMove();
                    }
                    @Override
                    public void onFinish() {

                    }
                }.start();

                countDownTimerStart=new CountDownTimer(6000000,10) {
                    @Override
                    public void onTick(long l) {
                        bird.setY((float) (bird.getY() + 6));

                        play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                flap.start();
                                countDownTimerPlay = new CountDownTimer(400,10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        bird.setY((float) (bird.getY()-2));
                                        countDownTimerStart.cancel();
                                        countDownTimerBird.cancel();
                                        vaCham(tl1);
                                        vaCham(tl2);
                                        vaCham(tl3);
                                        vaCham(tl4);
                                        vaCham(tl5);
                                    }

                                    @Override
                                    public void onFinish() {
                                        countDownTimerStart.start();
                                        countDownTimerBird.start();
                                    }
                                }.start();
                            }
                        });

                        if(bird.getY() >= (raceRoad.getY()-60)){
                            countDownTimerStart.cancel();
                            countDownTimerBird.cancel();
                            roadTime.cancel();
                            start.setVisibility(View.VISIBLE);
                            play.setEnabled(false);
                            fall.start();
                        }

                        vaCham(tl1);
                        vaCham(tl2);
                        vaCham(tl3);
                        vaCham(tl4);
                        vaCham(tl5);
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        });

        countDownTimerBird = new CountDownTimer(6000000,100) {
            @Override
            public void onTick(long l) {
                if(x == 0){
                    bird.setImageResource(R.drawable.bird2);
                    x=1;
                }else if(x == 1){
                    bird.setImageResource(R.drawable.bird3);
                    x=2;
                }else if(x == 2){
                    bird.setImageResource(R.drawable.bird1);
                    x=0;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void setScore(){
        if(bird.getX()-20 <= tl1.getX() && tl1.getX() <= bird.getX()-10
                || bird.getX()-20 <= tl2.getX() && tl2.getX() <= bird.getX()-10
                || bird.getX()-20 <= tl3.getX() && tl3.getX() <= bird.getX()-10
                || bird.getX()-20 <= tl4.getX() && tl4.getX() <= bird.getX()-10
                || bird.getX()-20 <= tl5.getX() && tl5.getX() <= bird.getX()-10){
            score = score+1;
            myScore.setText(score+"");
            point.start();
        }
    }

    /** condition move*/
    private void conditionMove() {
        if((raceRoad.getX()+screenWitch) <= 0){
            raceRoad.setX(screenWitch);
        }
        if((bg1.getX()+screenWitch) <= 0){
            bg1.setX(screenWitch);
        }
        if((raceRoad1.getX()+screenWitch) <= 0){
            raceRoad1.setX(screenWitch);
        }
        if((bg2.getX()+screenWitch) <= 0){
            bg2.setX(screenWitch);
        }

        setPositionHeightTenLua();

        if((tl1.getX()+screenWitch) <= 0){
            tl1.setX(screenWitch);
            tl1.setY(r1);
        }
        if((tl2.getX() + screenWitch+100) <= 0){
            tl2.setX(screenWitch + 100);
            tl2.setY(r2);
        }
        if((tl3.getX() + screenWitch + 200) <= 0){
            tl3.setX(screenWitch + 300);
            tl3.setY(r3);
        }
        if((tl4.getX() + screenWitch + 300) <= 0){
            tl4.setX(screenWitch + 500);
            tl4.setY(r4);
        }
        if((tl5.getX() + screenWitch + 400) <= 0){
            tl5.setX(screenWitch + 700);
            tl5.setY(r5);
        }
    }

    /** move item*/
    private void ItemMove() {
        raceRoad.setX((float) raceRoad.getX() - 10);
        bg1.setX((float) bg1.getX() - 5);
        raceRoad1.setX((float) raceRoad1.getX() - 10);
        bg2.setX((float) bg2.getX() - 5);
        moveTenLua(tl1);
        moveTenLua(tl2);
        moveTenLua(tl3);
        moveTenLua(tl4);
        moveTenLua(tl5);

        setScore();
    }

    /** move tên lửa*/
    private void moveTenLua(ImageView tl){
        tl.setX(tl.getX() - SPEEP_TL);
    }

    /** set positive tenlua*/
    private void setPositionHeightTenLua() {
        do {
            Random random = new Random();
            r1 = random.nextInt(screenHeight);
            r2 = random.nextInt(screenHeight);
            r3 = random.nextInt(screenHeight);
            r4 = random.nextInt(screenHeight);
            r5 = random.nextInt(screenHeight);
        }while (r1 == r2 || r1 == r3 || r1 == r4 || r1 == r5 || r2 == r3 || r2 == r4 || r2 == r5 || r3 == r4 || r3 == r5 || r4 == r5
                || r1 <= 50 || r1 >= screenHeight - 50
                || r2 <= 50 || r2 >= screenHeight - 50
                || r3 <= 50 || r3 >= screenHeight - 50
                || r4 <= 50 || r4 >= screenHeight - 50
                || r5 <= 50 || r5 >= screenHeight - 50);
    }

    /** set va chạm */
    private void vaCham(ImageView tl){
        if( -40 <= Math.sqrt(Math.pow(Math.abs(tl.getX() - bird.getX()),2) + Math.pow(Math.abs(tl.getY() - bird.getY()),2))
                && Math.sqrt(Math.pow(Math.abs(tl.getX() - bird.getX()),2) + Math.pow(Math.abs(tl.getY() - bird.getY()),2)) <= 50){
            countDownTimerStart.cancel();
            countDownTimerBird.cancel();
            roadTime.cancel();
            start.setVisibility(View.VISIBLE);
            play.setEnabled(false);
            fall.start();
        }
    }

    /** get size window*/
    private void getSizeWindow() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWitch = size.x;
        screenHeight = size.y;
    }
}