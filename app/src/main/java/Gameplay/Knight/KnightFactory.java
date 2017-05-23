package Gameplay.Knight;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
import android.media.Image;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.thomas.kotr_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeanine on 5/16/17.
 */

public class KnightFactory extends ContextWrapper{

    private int numberOfKnights;
    private int screenWidth;
    private int screenHeight;
    private char patternId;

    public KnightFactory(Context base, int numberOfKnights, int screenWidth, int screenHeight, char patternId) {
        super(base);

        this.numberOfKnights = numberOfKnights;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.patternId = patternId;
    }

    public List<ImageView> createKnights() {

        List<ImageView> knightViews = new ArrayList<ImageView>();

        switch (numberOfKnights){
            case 5:
                ImageView bottomLeftKnightView;
                ImageView topLeftKnightView;
                ImageView topCenterKnightView;
                ImageView topRightKnightView;
                ImageView bottomRightKnightView;

                switch (patternId) {

                    //'Box' pattern
                    // *   *
                    //   *
                    // *   *
                    case 'B':
                        bottomLeftKnightView = createKnight(R.id.bottomLeftKnightView, 0,
                                (int) ((2.0 / 3.0) * screenHeight) - ((int) ((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(bottomLeftKnightView);

                        topLeftKnightView = createKnight(R.id.topLeftKnightView, 0,
                                (int) ((1.0 / 3.0) * screenHeight) - (int) (0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(topLeftKnightView);

                        topCenterKnightView = createKnight(R.id.topCenterKnightView, (int) (0.5 * screenWidth) - (int) (0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth())),
                                (int)(0.5 * screenHeight) - (int)(1.0 / 3.0 * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight()));
                        knightViews.add(topCenterKnightView);

                        topRightKnightView = createKnight(R.id.topRightKnightView, screenWidth - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                                (int) ((1.0 / 3.0) * screenHeight) - (int) (0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(topRightKnightView);

                        bottomRightKnightView = createKnight(R.id.bottomRightKnightView, screenWidth - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                                (int) ((2.0 / 3.0) * screenHeight) - ((int) ((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(bottomRightKnightView);

                        break;

                    //'House' pattern
                    //   *
                    // *   *
                    // *   *
                    case 'H':
                        bottomLeftKnightView = createKnight(R.id.bottomLeftKnightView, 0,
                                (int) ((2.0 / 3.0) * screenHeight) - ((int) ((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(bottomLeftKnightView);

                        topLeftKnightView = createKnight(R.id.topLeftKnightView, 0,
                                (int) ((1.0 / 3.0) * screenHeight) + (int) (1.0 / 3.0 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(topLeftKnightView);

                        topCenterKnightView = createKnight(R.id.topCenterKnightView, (int) (0.5 * screenWidth) - (int) (0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth())),
                                (int) ((1.0 / 3.0) * screenHeight) - (int) (0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(topCenterKnightView);

                        topRightKnightView = createKnight(R.id.topRightKnightView, screenWidth - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                                (int) ((1.0 / 3.0) * screenHeight) + (int) (1.0 / 3.0 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(topRightKnightView);

                        bottomRightKnightView = createKnight(R.id.bottomRightKnightView, screenWidth - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                                (int) ((2.0 / 3.0) * screenHeight) - ((int) ((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(bottomRightKnightView);

                        break;

                    //'M' pattern
                    //   *   *
                    //     *
                    // *       *
                    case 'M':
                        bottomLeftKnightView = createKnight(R.id.bottomLeftKnightView, 0,
                                (int) ((2.0 / 3.0) * screenHeight) - ((int) ((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(bottomLeftKnightView);

                        topLeftKnightView = createKnight(R.id.topLeftKnightView, (int)((1.0 / 3.0) * screenWidth) - (int)(0.5 * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth()),
                                (int) ((1.0 / 3.0) * screenHeight) - (int)(0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(topLeftKnightView);

                        topCenterKnightView = createKnight(R.id.topCenterKnightView, (int) (0.5 * screenWidth) - (int) (0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth())),
                                (int)(0.5 * screenHeight) - (int)(1.0 / 3.0 * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight()));
                        knightViews.add(topCenterKnightView);

                        topRightKnightView = createKnight(R.id.topRightKnightView, (int)((2.0 / 3.0) * screenWidth) - (int)(0.5 * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth()),
                                (int) ((1.0 / 3.0) * screenHeight) - (int) (0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(topRightKnightView);

                        bottomRightKnightView = createKnight(R.id.bottomRightKnightView, screenWidth - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                                (int) ((2.0 / 3.0) * screenHeight) - ((int) ((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(bottomRightKnightView);
                        break;

                    //'W' pattern
                    // *     *
                    //    *
                    //  *   *
                    case 'W':
                        bottomLeftKnightView = createKnight(R.id.bottomLeftKnightView, (int)((1.0 / 3.0) * screenWidth) - (int)(0.5 * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth()),
                                (int) ((2.0 / 3.0) * screenHeight) - ((int) ((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(bottomLeftKnightView);

                        topLeftKnightView = createKnight(R.id.topLeftKnightView, 0,
                                (int) ((1.0 / 3.0) * screenHeight) - (int) (0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(topLeftKnightView);

                        topCenterKnightView = createKnight(R.id.topCenterKnightView, (int) (0.5 * screenWidth) - (int) (0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth())),
                                (int)(0.5 * screenHeight) - (int)(2.0 / 3.0 * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight()));
                        knightViews.add(topCenterKnightView);

                        topRightKnightView = createKnight(R.id.topRightKnightView, screenWidth - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                                (int) ((1.0 / 3.0) * screenHeight) - (int) (0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(topRightKnightView);

                        bottomRightKnightView = createKnight(R.id.bottomRightKnightView, (int)((2.0 / 3.0) * screenWidth) - (int)(0.5 * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth()),
                                (int) ((2.0 / 3.0) * screenHeight) - ((int) ((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                        knightViews.add(bottomRightKnightView);

                    default:
                        break;
                }
                break;
            default:
                break;
        }

        return knightViews;
    }

    private ImageView createKnight(int id, int leftMargin, int topMargin) {

        ImageView knightView = new ImageView(this);
        RelativeLayout.LayoutParams knightLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        knightLayoutParams.leftMargin = leftMargin;
        knightLayoutParams.topMargin = topMargin;
        knightView.setBackground(getResources().getDrawable(R.drawable.knight_three_intro_resized_v2));
        knightView.setLayoutParams(knightLayoutParams);
        knightView.setId(id);
        knightView.setEnabled(false);

        return knightView;
    }
}
