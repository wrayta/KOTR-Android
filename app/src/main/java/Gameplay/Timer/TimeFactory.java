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
        int allTimes[] = {R.drawable.num0, R.drawable.num1, R.drawable.num2,
            R.drawable.num3, R.drawable.num4, R.drawable.num5, R.drawable.num6,
            R.drawable.num7, R.drawable.num8, R.drawable.num9};

        ImageView timeView = new ImageView(this);
        RelativeLayout.LayoutParams timeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        timeLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        timeView.setImageResource(allTimes[time - 1]);
        timeView.setLayoutParams(timeLayoutParams);
        timeView.setId(R.id.timeView);

        return timeView;
    }
}
