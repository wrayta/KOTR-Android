package Gameplay.Timer;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.thomas.kotr_android.R;

/**
 * Created by jeanine on 5/19/17.
 */

public class TimeFactory extends ContextWrapper {

    private int time;

    public TimeFactory(Context base, int time) {

        super(base);

        this.time = time;
    }

    public ImageView createTime() {
        int allTimes[] = {R.drawable.num0_small, R.drawable.num1_small, R.drawable.num2_small,
            R.drawable.num3_small, R.drawable.num4_small, R.drawable.num5_small, R.drawable.num6_small,
            R.drawable.num7_small, R.drawable.num8_small, R.drawable.num9_small};

        ImageView timeView = new ImageView(this);
        RelativeLayout.LayoutParams timeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        timeLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        timeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        timeView.setImageResource(allTimes[time - 1]);
        timeView.setLayoutParams(timeLayoutParams);
        timeView.setId(R.id.timeView);

        return timeView;
    }
}
