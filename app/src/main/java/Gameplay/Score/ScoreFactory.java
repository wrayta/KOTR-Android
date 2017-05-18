package Gameplay.Score;

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
 * Created by jeanine on 5/17/17.
 */

public class ScoreFactory extends ContextWrapper {

    private int score;

    public ScoreFactory(Context base, int score) {
        super(base);

        this.score = score;
    }

    public ImageView createScore() {

        int[] allNumbers = {R.drawable.score0, R.drawable.score1, R.drawable.score2,
                R.drawable.score3, R.drawable.score4, R.drawable.score5, R.drawable.score6,
                R.drawable.score7, R.drawable.score8, R.drawable.score9};

        List<Integer> pngScore = calculatePngScore();

        ImageView scoreView = new ImageView(this);
        RelativeLayout.LayoutParams scoreLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scoreLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        scoreLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        List<Bitmap> scoreDigits = new ArrayList<Bitmap>();

        int blockWidth = 0;
        int blockHeight = 0;

        for(int i = 0; i < pngScore.size(); i++) {

            scoreDigits.add(BitmapFactory.decodeResource(getResources(), allNumbers[pngScore.get(i)]));
            blockWidth += BitmapFactory.decodeResource(getResources(), allNumbers[pngScore.get(i)]).getWidth();

            if(pngScore.size() > 1) {
                blockHeight = calculateMaxBlockHeight(allNumbers, pngScore);
            }
            else {
                blockHeight = BitmapFactory.decodeResource(getResources(), allNumbers[pngScore.get(i)]).getHeight();
            }
        }

        Bitmap scoreBlock = Bitmap.createBitmap(blockWidth, blockHeight, Bitmap.Config.ARGB_8888);
        Canvas scoreCanvas = new Canvas(scoreBlock);

        int xPositionOnScreen = 0;

        for(int i = 0; i < pngScore.size(); i++) {

            scoreCanvas.drawBitmap(scoreDigits.get(i), xPositionOnScreen, 0, null);
            xPositionOnScreen += scoreDigits.get(i).getWidth();

        }

        scoreView.setImageBitmap(scoreBlock);
        scoreView.setLayoutParams(scoreLayoutParams);
        scoreView.setId(R.id.scoreView);

        return scoreView;
    }

    private List<Integer> calculatePngScore() {

        List<Integer> retPngScore = new ArrayList<Integer>();

        if (score == 0) {
            retPngScore.add(0);
            return retPngScore;
        }

        int pngScore = score;

        LinkedList<Integer> stack = new LinkedList<Integer>();
        while (pngScore > 0) {
            stack.push( pngScore % 10 );
            pngScore = pngScore / 10;
        }

        while (!stack.isEmpty()) {
            retPngScore.add(stack.pop());
        }

        return retPngScore;

    }

    private int calculateMaxBlockHeight(int[] allNumbers, List<Integer> pngScore) {

        int maxHeight = BitmapFactory.decodeResource(getResources(), allNumbers[pngScore.get(0)]).getHeight();
        int tempHeight = 0;

        for(int i = 0; i < pngScore.size(); i++) {

            tempHeight = BitmapFactory.decodeResource(getResources(), allNumbers[pngScore.get(i)]).getHeight();

            if (tempHeight > maxHeight) {
                maxHeight = tempHeight;
            }
        }

        return maxHeight;
    }
}
