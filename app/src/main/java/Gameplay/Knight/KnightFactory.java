package Gameplay.Knight;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
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

    public KnightFactory(Context base, int numberOfKnights, int screenWidth, int screenHeight) {
        super(base);

        this.numberOfKnights = numberOfKnights;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public List<ImageView> createKnights() {

        List<ImageView> knightViews = new ArrayList<ImageView>();

        switch (numberOfKnights){
            case 5:
                ImageView bottomLeftKnightView = createKnight(R.id.bottomLeftKnightView, (int)((1.0 / 3.0) * screenWidth) - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                        (int)((2.0 / 3.0) * screenHeight) - (getResources().getDrawable(R.drawable.gold_frame).getIntrinsicHeight() + (int)((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                knightViews.add(bottomLeftKnightView);

                ImageView topLeftKnightView = createKnight(R.id.topLeftKnightView, (int)((1.0 / 3.0) * screenWidth) - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                        (int)((1.0 / 3.0) * screenHeight) - (int)(0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                knightViews.add(topLeftKnightView);

                ImageView topCenterKnightView = createKnight(R.id.topCenterKnightView, (int)(0.5 * screenWidth) - (int)(0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth())),
                        (int)((1.0 / 3.0) * screenHeight) - (getResources().getDrawable(R.drawable.score0).getIntrinsicHeight() + getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight()));
                knightViews.add(topCenterKnightView);

                ImageView topRightKnightView = createKnight(R.id.topRightKnightView, screenWidth - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                        (int)((1.0 / 3.0) * screenHeight) - (int)(0.5 * (getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                knightViews.add(topRightKnightView);

                ImageView bottomRightKnightView = createKnight(R.id.bottomRightKnightView, screenWidth - getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                        (int)((2.0 / 3.0) * screenHeight) - (getResources().getDrawable(R.drawable.gold_frame).getIntrinsicHeight() + (int)((1.0 / 3.0) * getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight())));
                knightViews.add(bottomRightKnightView);
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
        knightView.setImageResource(R.drawable.knight_three_intro_resized_v2);
        knightView.setLayoutParams(knightLayoutParams);
        knightView.setId(id);

        return knightView;
    }
}
