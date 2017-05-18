package com.example.thomas.kotr_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
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
import Gameplay.Life.LifeSprite;
import Gameplay.Score.ScoreFactory;
import Gameplay.Score.ScoreSprite;
import Gameplay.Shield.ShieldFactory;
//import Gameplay.Pattern.DisplayPattern;
//import Gameplay.Pattern.PatternKey;
//import Gameplay.Timer.TimerController;

public class MainActivity extends AppCompatActivity {

    // finals
    private final int TOTAL_KNIGHT_SPRITES = 8; // how many knights there are to
                                                // be loaded in

    private final int TOTAL_KNIGHT_SPRITE_ACTIONS = 3; // left leg, right leg,
                                                       // even

    private final int KNIGHTS_IN_PATTERN = 5; // how many knights will appear in
                                              // the gold frame

    private final int STARTING_LIVES = 3; // how many lives the player will
                                          // start with

    private final int STARTING_TIME = 10; // how much initial time is given for
                                          // each round

    // finals

    private Integer scores[] = {R.drawable.score0, R.drawable.score1, R.drawable.score2, R.drawable.score3, R.drawable.score4, R.drawable.score5, R.drawable.score6, R.drawable.score7, R.drawable.score8, R.drawable.score9};

    private Integer lives[] = {R.drawable.life3, R.drawable.life2, R.drawable.life1};
    private int currLife = 0;

    private int width;
    private int height;

    private List<Integer> key;
    private int score;

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
        List<Integer> patternedKnightsList = randomizePatternedKnights();
        setupPatternedKnights(patternedKnightsList);
        List<Integer> patternedShieldsList = randomizePatternedShieldsInFrame(patternedKnightsList);
        setupAnswerKey(patternedShieldsList);
        setupPatternedShieldsInFrame(patternedShieldsList);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
////                setupPatternedKnights();
//            }
//        }, 2000);
    }

    private void setupScore() {
        score = 0;
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        ScoreFactory scoreFactory = new ScoreFactory(this, score);
        ImageView scoreView = scoreFactory.createScore();

        layout.addView(scoreView);
    }

    private void setupLives() {
        LifeSprite lifeSprite = new LifeSprite();
        ImageView lifeView = new ImageView(this);
        RelativeLayout.LayoutParams lifeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lifeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lifeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lifeView.setImageResource(lives[currLife]);
        lifeView.setLayoutParams(lifeLayoutParams);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);
        layout.addView(lifeView);
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

        ShieldFactory shieldFactory = new ShieldFactory(this, R.id.patternedShieldsView, allFrameShields, patternedShieldList);
        ImageView patternedShieldsView = shieldFactory.createShields();

        layout.addView(patternedShieldsView);
    }

    private void setupPatternedKnights(List<Integer> patternedKnightsList) {
        int[] allPatternedKnights =  {R.drawable.shield_1_feet_even, R.drawable.shield_2_feet_even,
                R.drawable.shield_3_feet_even, R.drawable.shield_4_feet_even, R.drawable.shield_5_feet_even,
                R.drawable.shield_6_feet_even, R.drawable.shield_7_feet_even, R.drawable.shield_8_feet_even};  // ALL OF THE POSSIBLE KNIGHT SPRITES TO LOAD

        int[] knightViews = {R.id.topCenterKnightView, R.id.topLeftKnightView, R.id.topRightKnightView,
                R.id.bottomLeftKnightView, R.id.bottomRightKnightView};

        for(int i = 0; i < KNIGHTS_IN_PATTERN; i++) {
            ImageView iv = (ImageView) findViewById(knightViews[i]);
            iv.setImageResource(allPatternedKnights[patternedKnightsList.get(i)]);
            iv.setTag(patternedKnightsList.get(i));
            iv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    checkAgainstKey(view);
                }
            });
        }
    }

    private void checkAgainstKey(View knight) {
        if (knight.getTag() == key.get(0)) {
            //increment score
            score++;
            incrementScore();
            //and
            if(key.size() > 0) {
                key.remove(0);
            }
        }

        else {
            //decrement lives
        }
    }

    private void incrementScore() {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        ImageView oldScoreView = (ImageView) findViewById(R.id.scoreView);
        
        layout.removeView(oldScoreView);

        ScoreFactory scoreFactory = new ScoreFactory(this, score);
        ImageView scoreView = scoreFactory.createScore();

        layout.addView(scoreView);
    }
}
