package com.example.thomas.kotr_android;

import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import Gameplay.Knight.KnightFactory;
import Gameplay.Score.ScoreFactory;
import Gameplay.Shield.ShieldFactory;
import Gameplay.Sound.MediaPlayerPlayer;
import Gameplay.Sound.SoundPoolPlayer;
import Gameplay.Timer.TimeFactory;
import Logic.Movement.WalkingRunnable;
import Logic.Random.GameElementRandomizer;
import Messages.ReplayDialogFragment;

public class TimeAttackModeActivity extends FragmentActivity {

    private final int TOTAL_KNIGHT_SPRITES = 8;                           // how many knights there
                                                                          // are to be loaded in

    private final int KNIGHTS_IN_PATTERN = 5;                             // how many knights will
                                                                          // appear in the gold frame

    private final int STARTING_TIME = 99;                                 // how much initial time

    private final Character[] KNIGHT_PATTERN_IDS = {'B', 'H', 'M', 'W'};  // how many coord patterns
                                                                          // for knights will be
                                                                          // available

    private int width;                                                    // screen width
    private int height;                                                   // screen height
    private int score;                                                    // player score
    private int time;                                                     // keeps track of time

    private List<Integer> key;                                            // the ordered pattern to
                                                                          // match

    private CountDownTimer timer;                                         // timer object
    private MediaPlayerPlayer mediaPlayerPlayer;                          // plays the theme song
    private SoundPoolPlayer soundPoolPlayer;                              // plays 'correct' and
                                                                          // 'incorrect' .wavs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack_mode);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        doStartGame();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mediaPlayerPlayer != null && mediaPlayerPlayer.isPlayingAudio) {
            mediaPlayerPlayer.pauseAudio();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mediaPlayerPlayer != null && !mediaPlayerPlayer.isPlayingAudio) {
            mediaPlayerPlayer.resumePlayingAudio();
        }
    }

    private void doStartGame() {
        setupScore();
//        setupSound();
        playLevel();
    }

    private void setupScore() {
        score = 0;
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        ScoreFactory scoreFactory = new ScoreFactory(this, score);
        ImageView scoreView = scoreFactory.createScore();

        layout.addView(scoreView);
    }

    private void setupSound() {

        mediaPlayerPlayer = new MediaPlayerPlayer();

        if(!mediaPlayerPlayer.isPlayingAudio) {
            mediaPlayerPlayer.createAudio(this, R.raw.theme);
            mediaPlayerPlayer.resumePlayingAudio();
        }

        mediaPlayerPlayer.setLooping(true);

        soundPoolPlayer = new SoundPoolPlayer(this);

    }

    private void playLevel() {
        setupPlainShieldToFindInTopCorner();
        char patternId = randomizeKnightPatternId();
        setupPlainKnights(patternId);
        setupAnswerKey();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                setupTimer();
                setupWalkTimer();
                key = randomizePatternedKnights(KNIGHTS_IN_PATTERN, TOTAL_KNIGHT_SPRITES, key);
                setupPatternedKnights(key);
                key = randomizePatternedShieldsInFrame(KNIGHTS_IN_PATTERN, TOTAL_KNIGHT_SPRITES, key);
                setupPatternedShieldToFindInTopCorner(key);
                timer.start();
            }
        }, 2000);
    }

    private void setupPlainShieldToFindInTopCorner() {

        int[] plainShield = {R.drawable.plain_shield_v2};

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        ShieldFactory shieldFactory = new ShieldFactory(this, R.id.plainShieldsView, plainShield);
        ImageView plainShieldsView = shieldFactory.createShields(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(plainShieldsView);

    }

    private char randomizeKnightPatternId() {

        return GameElementRandomizer.pickRandomElementFromList(KNIGHT_PATTERN_IDS);

    }

    private void setupPlainKnights(char patternId) {

        // Main layout of game
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        KnightFactory knightFactory = new KnightFactory(this, KNIGHTS_IN_PATTERN, width, height, patternId);
        List<ImageView> knightViews = knightFactory.createKnights();

        for(int i = 0; i < knightViews.size(); i++) {
            layout.addView(knightViews.get(i));
        }

    }

    private List<Integer> randomizePatternedKnights(int thingsToSelect, int allPossibleThings, List<Integer> key) {

        return GameElementRandomizer.returnRndIntListFromIntBounds(thingsToSelect, allPossibleThings, key);

    }

    private List<Integer> randomizePatternedShieldsInFrame(int thingsToSelect, int allPossibleThings, List<Integer> key) {

        return GameElementRandomizer.returnRndIntListIncludingElemsFromOtherList(thingsToSelect, allPossibleThings, key);

    }

    private void setupAnswerKey() {
        key = new ArrayList<Integer>();
    }

    private void setupPatternedShieldToFindInTopCorner(List<Integer> key) {
        int[] allFrameShields = {R.drawable.shield_1, R.drawable.shield_2, R.drawable.shield_3,
                R.drawable.shield_4, R.drawable.shield_5, R.drawable.shield_6,
                R.drawable.shield_7, R.drawable.shield_8};         // ALL OF THE POSSIBLE SHIELD SPRITES TO LOAD

        int[] shieldToFind = {allFrameShields[key.get(0)]};

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        ImageView plainShieldView = (ImageView) findViewById(R.id.plainShieldsView);

        layout.removeView(plainShieldView);

        ShieldFactory shieldFactory = new ShieldFactory(this, R.id.patternedShieldsView, shieldToFind);
        ImageView patternedShieldsView = shieldFactory.createShields(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(patternedShieldsView);
    }

    private void setupPatternedKnights(List<Integer> list) {
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

            knight.setBackground(allPatternedKnights[list.get(i)]);
            ((AnimationDrawable) knight.getBackground()).start();

            knight.setEnabled(true);
            knight.setTag(list.get(i));
            knight.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    checkAgainstKey(view);
                }
            });
        }
    }

    private void updatePatternedKnights(int knightId, Integer patternedKnightInt) {
        Drawable[] allPatternedKnights =  {getResources().getDrawable(R.drawable.shield_1_animation),
                getResources().getDrawable(R.drawable.shield_2_animation),
                getResources().getDrawable(R.drawable.shield_3_animation),
                getResources().getDrawable(R.drawable.shield_4_animation),
                getResources().getDrawable(R.drawable.shield_5_animation),
                getResources().getDrawable(R.drawable.shield_6_animation),
                getResources().getDrawable(R.drawable.shield_7_animation),
                getResources().getDrawable(R.drawable.shield_8_animation)};  // ALL OF THE POSSIBLE KNIGHT SPRITES TO LOAD

        ImageView updatedKnight = (ImageView) findViewById(knightId);

        updatedKnight.setBackground(allPatternedKnights[patternedKnightInt]);
        ((AnimationDrawable) updatedKnight.getBackground()).start();

        updatedKnight.setEnabled(true);
        updatedKnight.setTag(patternedKnightInt);
        updatedKnight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                checkAgainstKey(view);
            }
        });
    }

    private void checkAgainstKey(View knight) {
        if (knight.getTag() == key.get(0)) {

//            soundPoolPlayer.playShortResource(R.raw.correct);

            ((AnimationDrawable) knight.getBackground()).stop();

            key.remove(0);

            //increment score
            incrementScore();

            //choose a new shield not already in key
            key = GameElementRandomizer.returnRndIntListFromIntBounds(1, TOTAL_KNIGHT_SPRITES, key);

            //replace clicked patternedKnight with new patternedKnight (end of key)
            updatePatternedKnights(knight.getId(), key.get(key.size() - 1));

            //otherwise, knight click pattern wouldn't change
            key = GameElementRandomizer.shuffleKeyOrder(key);

            //display updated shields on screen
            updatePatternedShieldToFindInTopCorner(key);


        }

        else {
//            soundPoolPlayer.playShortResource(R.raw.incorrect);
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

    private void updatePatternedShieldToFindInTopCorner(List<Integer> key) {
        int[] allFrameShields = {R.drawable.shield_1, R.drawable.shield_2, R.drawable.shield_3,
                R.drawable.shield_4, R.drawable.shield_5, R.drawable.shield_6,
                R.drawable.shield_7, R.drawable.shield_8};         // ALL OF THE POSSIBLE SHIELD SPRITES TO LOAD

        int[] shieldToFind = {allFrameShields[key.get(0)]};

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        ImageView oldPatternedShieldsView = (ImageView) findViewById(R.id.patternedShieldsView);

        layout.removeView(oldPatternedShieldsView);

        ShieldFactory shieldFactory = new ShieldFactory(this, R.id.patternedShieldsView, shieldToFind);
        ImageView patternedShieldsView = shieldFactory.createShields(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.ALIGN_PARENT_RIGHT);

        layout.addView(patternedShieldsView);
    }

    private void setupWalkTimer() {

        Handler handler = new Handler();

        ImageView[] knightsExceptBottomLeft = {(ImageView)findViewById(R.id.topLeftKnightView), (ImageView)findViewById(R.id.topCenterKnightView),
                (ImageView)findViewById(R.id.topRightKnightView), (ImageView)findViewById(R.id.bottomRightKnightView)};

        ImageView bottomLeftKnightView = (ImageView)findViewById(R.id.bottomLeftKnightView);
        WalkingRunnable walkingRunnableKnight1 = new WalkingRunnable(this, handler, bottomLeftKnightView, getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth()
                ,getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight(), width, height, knightsExceptBottomLeft);

        handler.post(walkingRunnableKnight1);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ImageView[] knightsExceptTopLeft = {(ImageView)findViewById(R.id.bottomLeftKnightView), (ImageView)findViewById(R.id.topCenterKnightView),
                (ImageView)findViewById(R.id.topRightKnightView), (ImageView)findViewById(R.id.bottomRightKnightView)};

        ImageView topLeftKnightView = (ImageView)findViewById(R.id.topLeftKnightView);
        WalkingRunnable walkingRunnableKnight2 = new WalkingRunnable(this, handler, topLeftKnightView, getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth()
                ,getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight(), width, height, knightsExceptTopLeft);

        handler.post(walkingRunnableKnight2);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ImageView[] knightsExceptTopCenter = {(ImageView)findViewById(R.id.bottomLeftKnightView), (ImageView)findViewById(R.id.topLeftKnightView),
                (ImageView)findViewById(R.id.topRightKnightView), (ImageView)findViewById(R.id.bottomRightKnightView)};

        ImageView topCenterKnightView = (ImageView)findViewById(R.id.topCenterKnightView);
        WalkingRunnable walkingRunnableKnight3 = new WalkingRunnable(this, handler, topCenterKnightView, getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth()
                ,getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight(), width, height, knightsExceptTopCenter);

        handler.post(walkingRunnableKnight3);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ImageView[] knightsExceptTopRight = {(ImageView)findViewById(R.id.bottomLeftKnightView), (ImageView)findViewById(R.id.topLeftKnightView),
                (ImageView)findViewById(R.id.topCenterKnightView), (ImageView)findViewById(R.id.bottomRightKnightView)};

        ImageView topRightKnightView = (ImageView)findViewById(R.id.topRightKnightView);
        WalkingRunnable walkingRunnableKnight4 = new WalkingRunnable(this, handler, topRightKnightView, getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth()
                ,getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight(), width, height, knightsExceptTopRight);

        handler.post(walkingRunnableKnight4);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ImageView[] knightsExceptBottomRight = {(ImageView)findViewById(R.id.bottomLeftKnightView), (ImageView)findViewById(R.id.topLeftKnightView),
                (ImageView)findViewById(R.id.topCenterKnightView), (ImageView)findViewById(R.id.topRightKnightView)};

        ImageView bottomRightKnightView = (ImageView)findViewById(R.id.bottomRightKnightView);
        WalkingRunnable walkingRunnableKnight5 = new WalkingRunnable(this, handler, bottomRightKnightView, getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth()
                ,getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight(), width, height, knightsExceptBottomRight);

        handler.post(walkingRunnableKnight5);

    }

    private void setupTimer() {
        time = STARTING_TIME;

        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);

        timer = new CountDownTimer(STARTING_TIME * 1000 + 1000, 1000) {
            public void onTick(long millisUntilFinished) {

                ImageView oldTimeView = (ImageView) findViewById(R.id.timeView);

                layout.removeView(oldTimeView);

                TimeFactory timeFactory = new TimeFactory(TimeAttackModeActivity.this, time);
                ImageView timeView = timeFactory.createTime();

                layout.addView(timeView);

                time--;

            }

            public void onFinish() {
                setupEnd();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        setupAlertDialog();
                    }
                }, 1000);
            }
        };
    }

    private void setupEnd() {

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_layout);
        layout.removeAllViews();

        ImageView endView = new ImageView(this);
        RelativeLayout.LayoutParams endLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        endLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        endView.setImageResource(R.drawable.end_screen);
        endView.setLayoutParams(endLayoutParams);
        endView.setId(R.id.endView);

        layout.addView(endView);

    }

    private void setupAlertDialog() {
        ReplayDialogFragment replayDialogFragment = new ReplayDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("SCORE", score);
        bundle.putString("MODE", "timeAttack");
        replayDialogFragment.setArguments(bundle);
        replayDialogFragment.show(getSupportFragmentManager(), "replay");
    }
}
