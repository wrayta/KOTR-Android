package com.example.thomas.kotr_android;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

//import Gameplay.Knight.DisplayKnight;
//import Gameplay.Knight.Knight;
//import Gameplay.Life.LifeController;
import Gameplay.Life.LifeSprite;
import Gameplay.Score.ScoreSprite;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doStageSetup();
    }

    private void doStageSetup() {
        setupScore();
        setupLives();
        setupPlainShieldsInFrame();
        setupPlainKnights();

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
        ImageView plainShieldsView = new ImageView(this);
        RelativeLayout.LayoutParams plainShieldLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        plainShieldLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        Bitmap plainShield1 = BitmapFactory.decodeResource(getResources(), R.drawable.plain_shield_v2);
        Bitmap plainShield2 = BitmapFactory.decodeResource(getResources(), R.drawable.plain_shield_v2);
        Bitmap plainShield3 = BitmapFactory.decodeResource(getResources(), R.drawable.plain_shield_v2);
        Bitmap plainShield4 = BitmapFactory.decodeResource(getResources(), R.drawable.plain_shield_v2);
        Bitmap plainShield5 = BitmapFactory.decodeResource(getResources(), R.drawable.plain_shield_v2);

        Bitmap plainShieldBlock = Bitmap.createBitmap(plainShield1.getWidth() * 5, plainShield1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas plainShieldCanvas = new Canvas(plainShieldBlock);
        plainShieldCanvas.drawBitmap(plainShield1, 0, 0, null);
        plainShieldCanvas.drawBitmap(plainShield2, plainShield1.getWidth(), 0, null);
        plainShieldCanvas.drawBitmap(plainShield3, plainShield2.getWidth() * 2, 0, null);
        plainShieldCanvas.drawBitmap(plainShield4, plainShield3.getWidth() * 3, 0, null);
        plainShieldCanvas.drawBitmap(plainShield5, plainShield4.getWidth() * 4, 0, null);

        plainShieldsView.setImageBitmap(plainShieldBlock);
        plainShieldsView.setLayoutParams(plainShieldLayoutParams);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout_gold_frame);
        layout.addView(plainShieldsView);
    }

    private void setupPlainKnights() {

        // Screen Dimensions
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
//        Log.d("WIDTH", "value: " + width);

        // Main layout of game
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        // Top Center Knight
        ImageView topCenterKnightView = new ImageView(this);
        RelativeLayout.LayoutParams topCenterKnightLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        topCenterKnightLayoutParams.leftMargin = (int)(0.5 * width) - (int)(0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth()));
        topCenterKnightLayoutParams.topMargin = (int)((1.0 / 3.0) * height) - (getResources().getDrawable(R.drawable.score0).getIntrinsicHeight() + getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight());
//        topCenterKnightLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        topCenterKnightView.setImageResource(R.drawable.knight_three_intro_resized_v2);
        topCenterKnightView.setLayoutParams(topCenterKnightLayoutParams);
        layout.addView(topCenterKnightView);

        // Top Left Knight
        ImageView topLeftKnightView = new ImageView(this);
        RelativeLayout.LayoutParams topLeftKnightLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        topLeftKnightLayoutParams.leftMargin = (int)((1.0 / 3.0) * width) - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth();
        topLeftKnightLayoutParams.topMargin = (int)((1.0 / 3.0) * height) - (int)(0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight()));
        topLeftKnightView.setImageResource(R.drawable.knight_three_intro_resized_v2);
        topLeftKnightView.setLayoutParams(topLeftKnightLayoutParams);
        layout.addView(topLeftKnightView);

        // Top Right Knight
        ImageView topRightKnightView = new ImageView(this);
        RelativeLayout.LayoutParams topRightKnightLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        topRightKnightLayoutParams.leftMargin = width - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth();
        topRightKnightLayoutParams.topMargin = (int)((1.0 / 3.0) * height) - (int)(0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight()));
        topRightKnightView.setImageResource(R.drawable.knight_three_intro_resized_v2);
        topRightKnightView.setLayoutParams(topRightKnightLayoutParams);
        layout.addView(topRightKnightView);

        // Bottom Left Knight
        ImageView bottomLeftKnightView = new ImageView(this);
        RelativeLayout.LayoutParams bottomLeftKnightLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomLeftKnightLayoutParams.leftMargin = (int)((1.0 / 3.0) * width) - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth();
        bottomLeftKnightLayoutParams.topMargin = (int)((2.0 / 3.0) * height) - (getResources().getDrawable(R.drawable.gold_frame).getIntrinsicHeight() + (int)((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight()));
        bottomLeftKnightView.setImageResource(R.drawable.knight_three_intro_resized_v2);
        bottomLeftKnightView.setLayoutParams(bottomLeftKnightLayoutParams);
        layout.addView(bottomLeftKnightView);

        // Bottom Right Knight
        ImageView bottomRightKnightView = new ImageView(this);
        RelativeLayout.LayoutParams bottomRightKnightLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomRightKnightLayoutParams.leftMargin = width - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth();
        bottomRightKnightLayoutParams.topMargin = (int)((2.0 / 3.0) * height) - (getResources().getDrawable(R.drawable.gold_frame).getIntrinsicHeight() + (int)((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight()));
        bottomRightKnightView.setImageResource(R.drawable.knight_three_intro_resized_v2);
        bottomRightKnightView.setLayoutParams(bottomRightKnightLayoutParams);
        layout.addView(bottomRightKnightView);
    }
}
