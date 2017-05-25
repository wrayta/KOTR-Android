package Gameplay.Timer;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.thomas.kotr_android.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

        List<Integer> pngTime = calculatePngTime();

        ImageView timeView = new ImageView(this);
        RelativeLayout.LayoutParams timeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        timeLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        timeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        List<Bitmap> timeDigits = new ArrayList<Bitmap>();

        int blockWidth = 0;
        int blockHeight = 0;


        for(int i = 0; i < pngTime.size(); i++) {

            timeDigits.add(BitmapFactory.decodeResource(getResources(), allTimes[pngTime.get(i)]));
            blockWidth += BitmapFactory.decodeResource(getResources(), allTimes[pngTime.get(i)]).getWidth();

            if(pngTime.size() > 1) {
                blockHeight = calculateMaxBlockHeight(allTimes, pngTime);
            }
            else {
                blockHeight = BitmapFactory.decodeResource(getResources(), allTimes[pngTime.get(i)]).getHeight();
            }
        }

        Bitmap timeBlock = Bitmap.createBitmap(blockWidth, blockHeight, Bitmap.Config.ARGB_8888);
        Canvas timeCanvas = new Canvas(timeBlock);

        int xPositionOnScreen = 0;

        for(int i = 0; i < pngTime.size(); i++) {

            timeCanvas.drawBitmap(timeDigits.get(i), xPositionOnScreen, 0, null);
            xPositionOnScreen += timeDigits.get(i).getWidth();

        }

        timeView.setImageBitmap(timeBlock);
        timeView.setLayoutParams(timeLayoutParams);
        timeView.setId(R.id.timeView);

        return timeView;
    }

    private List<Integer> calculatePngTime() {

        List<Integer> retPngTime = new ArrayList<Integer>();

        if (time == 0) {
            retPngTime.add(0);
            return retPngTime;
        }

        int pngScore = time;

        LinkedList<Integer> stack = new LinkedList<Integer>();
        while (pngScore > 0) {
            stack.push( pngScore % 10 );
            pngScore = pngScore / 10;
        }

        while (!stack.isEmpty()) {
            retPngTime.add(stack.pop());
        }

        return retPngTime;

    }

    private int calculateMaxBlockHeight(int[] allTimes, List<Integer> pngTime) {

        int maxHeight = BitmapFactory.decodeResource(getResources(), allTimes[pngTime.get(0)]).getHeight();
        int tempHeight = 0;

        for(int i = 0; i < pngTime.size(); i++) {

            tempHeight = BitmapFactory.decodeResource(getResources(), allTimes[pngTime.get(i)]).getHeight();

            if (tempHeight > maxHeight) {
                maxHeight = tempHeight;
            }
        }

        return maxHeight;
    }
}
