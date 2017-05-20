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
                ImageView bottomLeftKnightView = createKnight(R.id.bottomLeftKnightView, RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.ALIGN_PARENT_BOTTOM);
                knightViews.add(bottomLeftKnightView);

                ImageView topLeftKnightView = createKnight(R.id.topLeftKnightView, RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.ALIGN_PARENT_LEFT);
                knightViews.add(topLeftKnightView);

                ImageView topCenterKnightView = createKnight(R.id.topCenterKnightView, RelativeLayout.CENTER_IN_PARENT, RelativeLayout.ABOVE);
                knightViews.add(topCenterKnightView);

                ImageView topRightKnightView = createKnight(R.id.topRightKnightView, RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.ALIGN_PARENT_RIGHT);
                knightViews.add(topRightKnightView);

                ImageView bottomRightKnightView = createKnight(R.id.bottomRightKnightView, RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.ALIGN_PARENT_BOTTOM);
                knightViews.add(bottomRightKnightView);
                break;
            default:
                break;
        }

        return knightViews;
    }

    private ImageView createKnight(int id, int layoutRule1, int layoutRule2) {

        ImageView knightView = new ImageView(this);
        RelativeLayout.LayoutParams knightLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        knightLayoutParams.addRule(layoutRule1);
        knightLayoutParams.addRule(layoutRule2);
//        knightLayoutParams.leftMargin = leftMargin;
//        knightLayoutParams.topMargin = topMargin;
        knightView.setBackground(getResources().getDrawable(R.drawable.knight_three_intro_resized_v2));
        knightView.setLayoutParams(knightLayoutParams);
        knightView.setId(id);
        knightView.setEnabled(false);

        return knightView;
    }
}
