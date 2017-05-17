package com.example.thomas.kotr_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
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
    private int currScore = 0;

    private Integer lives[] = {R.drawable.life3, R.drawable.life2, R.drawable.life1};
    private int currLife = 0;

    private int width;
    private int height;

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
        setupPatternedShieldsInFrame(patternedShieldsList);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
//                setupPatternedKnights();
            }
        }, 2000);
//        setupPatternedKnights();
    }

    private void setupScore() {
        ScoreSprite scoreSprite = new ScoreSprite();
        ImageView scoreView = new ImageView(this);
        RelativeLayout.LayoutParams scoreLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scoreLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        scoreLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        Bitmap scoreDigitOnes = BitmapFactory.decodeResource(getResources(), R.drawable.score0);
        Bitmap scoreDigitTens = BitmapFactory.decodeResource(getResources(), R.drawable.score0);
        Bitmap scoreDigitHundreds = BitmapFactory.decodeResource(getResources(), R.drawable.score0);

        Bitmap scoreBlock = Bitmap.createBitmap(scoreDigitOnes.getWidth() * 3, scoreDigitOnes.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas scoreCanvas = new Canvas(scoreBlock);
        scoreCanvas.drawBitmap(scoreDigitHundreds, 0, 0, null);
        scoreCanvas.drawBitmap(scoreDigitTens, scoreDigitHundreds.getWidth(), 0, null);
        scoreCanvas.drawBitmap(scoreDigitOnes, scoreDigitTens.getWidth() * 2, 0, null);

        scoreView.setImageBitmap(scoreBlock);
        scoreView.setLayoutParams(scoreLayoutParams);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);
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

//        int[] currentPatternedKnights = new int[KNIGHTS_IN_PATTERN];     // ONLY THE CURRENT LEVEL KNIGHT SPRITES
//        int[] currentPatternShieldsInFrame = new int[KNIGHTS_IN_PATTERN];
//        int[] currentPatternShieldsInFrameOrder = new int[KNIGHTS_IN_PATTERN];
//        PatternOrOrderRandomizer randomizer = new PatternOrOrderRandomizer(0, TOTAL_KNIGHT_SPRITES - 1); // UTILITY FOR RANDOMIZING THE SHIELD PATTERNS AND ORDER
//
//
//
//        currentPatternShieldsInFrame = randomizer.randomizePatternOrOder(KNIGHTS_IN_PATTERN, currentPatternedKnights); // RANDOMLY SELECTS FROM DIFFERENT PATTERNED SPRITES
//
//        randomizer = new PatternOrOrderRandomizer(0, KNIGHTS_IN_PATTERN - 1);
//
//        currentPatternShieldsInFrameOrder = randomizer.randomizePatternOrOderSecond(KNIGHTS_IN_PATTERN, currentPatternShieldsInFrame); // RANDOMIZES THE ORDER OF THE RANDOMIZED SPRITE PATTERN SELECTION

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
        }
    }
}
