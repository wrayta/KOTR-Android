package com.example.thomas.kotr_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//import Gameplay.Knight.DisplayKnight;
//import Gameplay.Knight.Knight;
//import Gameplay.Life.LifeController;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Gameplay.Knight.KnightFactory;
import Gameplay.Life.LifeFactory;
import Gameplay.Life.LifeSprite;
import Gameplay.Score.ScoreFactory;
import Gameplay.Score.ScoreSprite;
import Gameplay.Shield.ShieldFactory;
import Gameplay.Timer.TimeFactory;
//import Gameplay.Pattern.DisplayPattern;
//import Gameplay.Pattern.PatternKey;
//import Gameplay.Timer.TimerController;

public class MainActivity extends AppCompatActivity {

    // finals
    private final int TOTAL_KNIGHT_SPRITES = 8; // how many knights there are to
                                                // be loaded in

    private final int KNIGHTS_IN_PATTERN = 5; // how many knights will appear in
                                              // the gold frame

    private final int STARTING_LIVES = 3; // how many lives the player will
                                          // start with

    private final int STARTING_TIME = 10; // how much initial time is given for
                                          // each round

    // finals

    private int width;
    private int height;

    private List<Integer> key;
    private int keyPointer;
    private int score;
    private int lives;
    private int time;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Screen Dimensions
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
//        Log.d("WIDTH", "value: " + width);

        doStageSetup();
    }

    private void doStageSetup() {
        setupScore();
        setupLives();
        setupPlainShieldsInFrame();
        setupPlainKnights();
        final List<Integer> patternedKnightsList = randomizePatternedKnights();
        final List<Integer> patternedShieldsList = randomizePatternedShieldsInFrame(patternedKnightsList);
        setupAnswerKey(patternedShieldsList);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                setupTimer();
                setupPatternedKnights(patternedKnightsList);
                setupPatternedShieldsInFrame(patternedShieldsList);
                timer.start();
            }
        }, 2000);

    }

    private void setupScore() {
        score = 0;
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        ScoreFactory scoreFactory = new ScoreFactory(this, score);
        ImageView scoreView = scoreFactory.createScore();

        layout.addView(scoreView);
    }

    private void setupLives() {
        lives = 3;
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        LifeFactory lifeFactory = new LifeFactory(this, lives);
        ImageView lifeView = lifeFactory.createLives();

        layout.addView(lifeView);
    }

    private void setupTimer() {
        time = 10;

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        timer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {

                ImageView oldTimeView = (ImageView) findViewById(R.id.timeView);

                layout.removeView(oldTimeView);

                TimeFactory timeFactory = new TimeFactory(MainActivity.this, time);
                ImageView timeView = timeFactory.createTime();

                layout.addView(timeView);

                time--;
            }

            public void onFinish() {
                setupEnd();
            }
        };

        TimeFactory timeFactory = new TimeFactory(this, time);
        ImageView timeView = timeFactory.createTime();

        layout.addView(timeView);
    }

    private void setupPlainShieldsInFrame() {

        int[] plainShields = {R.drawable.plain_shield_v2, R.drawable.plain_shield_v2,
            R.drawable.plain_shield_v2, R.drawable.plain_shield_v2, R.drawable.plain_shield_v2};

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout_gold_frame);

        ShieldFactory shieldFactory = new ShieldFactory(this, R.id.plainShieldsView, plainShields);
        ImageView plainShieldsView = shieldFactory.createShields();

        layout.addView(plainShieldsView);

    }

    private void setupPlainKnights() {

        // Main layout of game
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        KnightFactory knightFactory = new KnightFactory(this, KNIGHTS_IN_PATTERN, width, height);
        List<ImageView> knightViews = knightFactory.createKnights();

        for(int i = 0; i < knightViews.size(); i++) {
            layout.addView(knightViews.get(i));
        }

    }

    private List<Integer> randomizePatternedKnights() {
        Random rng = new Random();
        List<Integer> generated = new ArrayList<Integer>();

        for(int i = 0; i < KNIGHTS_IN_PATTERN; i++) {
            while(true) {
                Integer next = rng.nextInt(TOTAL_KNIGHT_SPRITES);
                if(!generated.contains(next)) {
                    generated.add(next);
                    break;
                }
            }
        }

        return generated;

    }

    private List<Integer> randomizePatternedShieldsInFrame(List<Integer> randomizedPatternedKnightsList) {

        Random rng = new Random();
        List<Integer> generated = new ArrayList<Integer>();

        for(int i = 0; i < KNIGHTS_IN_PATTERN; i++) {
            while(true) {
                Integer next = rng.nextInt(TOTAL_KNIGHT_SPRITES);
                if(!generated.contains(next) && randomizedPatternedKnightsList.contains(next)) {
                    generated.add(next);
                    break;
                }
            }
        }

        return generated;

    }

    private void setupAnswerKey(List<Integer> patternedShieldsList) {
        keyPointer = 0;
        key = new ArrayList<Integer>();

        for(int i = 0; i < patternedShieldsList.size(); i++) {
            key.add(patternedShieldsList.get(i));
        }
    }

    private void setupPatternedShieldsInFrame(List<Integer> patternedShieldList) {
        int[] allFrameShields = {R.drawable.shield_1, R.drawable.shield_2, R.drawable.shield_3,
                R.drawable.shield_4, R.drawable.shield_5, R.drawable.shield_6,
                R.drawable.shield_7, R.drawable.shield_8};         // ALL OF THE POSSIBLE SHIELD SPRITES TO LOAD

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout_gold_frame);

        ImageView plainShieldView = (ImageView) findViewById(R.id.plainShieldsView);

        layout.removeView(plainShieldView);

        ShieldFactory shieldFactory = new ShieldFactory(this, R.id.patternedShieldsView, allFrameShields, patternedShieldList);
        ImageView patternedShieldsView = shieldFactory.createShields();

        layout.addView(patternedShieldsView);
    }

    private void setupPatternedKnights(List<Integer> patternedKnightsList) {
        Drawable[] allPatternedKnights =  {getResources().getDrawable(R.drawable.shield_1_animation),
                getResources().getDrawable(R.drawable.shield_2_animation),
                getResources().getDrawable(R.drawable.shield_3_animation),
                getResources().getDrawable(R.drawable.shield_4_animation),
                getResources().getDrawable(R.drawable.shield_5_animation),
                getResources().getDrawable(R.drawable.shield_6_animation),
                getResources().getDrawable(R.drawable.shield_7_animation),
                getResources().getDrawable(R.drawable.shield_8_animation)};  // ALL OF THE POSSIBLE KNIGHT SPRITES TO LOAD

        int[] knightViews = {R.id.topCenterKnightView, R.id.topLeftKnightView, R.id.topRightKnightView,
                R.id.bottomLeftKnightView, R.id.bottomRightKnightView};

        for(int i = 0; i < KNIGHTS_IN_PATTERN; i++) {
            ImageView knight = (ImageView) findViewById(knightViews[i]);

            knight.setBackground(allPatternedKnights[patternedKnightsList.get(i)]);
            ((AnimationDrawable) knight.getBackground()).start();

            knight.setEnabled(true);
            knight.setTag(patternedKnightsList.get(i));
            knight.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    checkAgainstKey(view);
                }
            });
        }
    }

    private void checkAgainstKey(View knight) {
        if (knight.getTag() == key.get(keyPointer)) {
            //change to plain knight
            ((AnimationDrawable) knight.getBackground()).stop();

            knight.setBackground(getResources().getDrawable(R.drawable.knight_animation));
            ((AnimationDrawable) knight.getBackground()).start();

            //increment score
            incrementScore();

            keyPointer++;

            updatePatternedShieldsInFrame();

            knight.setEnabled(false);
        }

        else {
            decrementLives();
        }
    }

    private void incrementScore() {
        score++;

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        ImageView oldScoreView = (ImageView) findViewById(R.id.scoreView);

        layout.removeView(oldScoreView);

        ScoreFactory scoreFactory = new ScoreFactory(this, score);
        ImageView scoreView = scoreFactory.createScore();

        layout.addView(scoreView);
    }

    private void updatePatternedShieldsInFrame() {
        int[] allFrameShields = {R.drawable.shield_1, R.drawable.shield_2, R.drawable.shield_3,
                R.drawable.shield_4, R.drawable.shield_5, R.drawable.shield_6,
                R.drawable.shield_7, R.drawable.shield_8};         // ALL OF THE POSSIBLE SHIELD SPRITES TO LOAD

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout_gold_frame);

        ImageView oldPatternedShieldsView = (ImageView) findViewById(R.id.patternedShieldsView);

        layout.removeView(oldPatternedShieldsView);

        ShieldFactory shieldFactory = new ShieldFactory(this, R.id.patternedShieldsView, allFrameShields, key);
        ImageView patternedShieldsView = shieldFactory.createShields(keyPointer);

        layout.addView(patternedShieldsView);
    }

    private void decrementLives() {
        lives--;

        if(lives == 0) {

            timer.cancel();
            setupEnd();

        } else {
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

            ImageView oldLifeView = (ImageView) findViewById(R.id.lifeView);

            layout.removeView(oldLifeView);

            LifeFactory lifeFactory = new LifeFactory(this, lives);
            ImageView lifeView = lifeFactory.createLives();

            layout.addView(lifeView);
        }
    }

    private void setupEnd() {

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);
        layout.removeAllViews();

        ImageView endView = new ImageView(this);
        RelativeLayout.LayoutParams endLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        endLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        endView.setImageResource(R.drawable.end_screen);
        endView.setLayoutParams(endLayoutParams);

        layout.addView(endView);
    }
}
