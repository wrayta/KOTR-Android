package Gameplay.Life;

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
import java.util.List;

/**
 * Created by jeanine on 5/18/17.
 */

public class LifeFactory extends ContextWrapper {

    private int lives;

    public LifeFactory(Context base, int lives) {

        super(base);

        this.lives = lives;
    }

    public ImageView createLives() {
        int allLives[] = {R.drawable.knight_life};

        ImageView lifeView = new ImageView(this);
        RelativeLayout.LayoutParams lifeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lifeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lifeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        List<Bitmap> numberOfLives = new ArrayList<Bitmap>();

        int blockWidth = 0;
        int blockHeight = 0;

        for(int i = 0; i < lives; i++) {

            numberOfLives.add(BitmapFactory.decodeResource(getResources(), allLives[0]));
            blockWidth += BitmapFactory.decodeResource(getResources(), allLives[0]).getWidth();
            blockHeight = BitmapFactory.decodeResource(getResources(), allLives[0]).getHeight();

        }

        Bitmap lifeBlock = Bitmap.createBitmap(blockWidth, blockHeight, Bitmap.Config.ARGB_8888);
        Canvas lifeCanvas = new Canvas(lifeBlock);

        for(int i = 0; i < lives; i++) {

            lifeCanvas.drawBitmap(numberOfLives.get(i), numberOfLives.get(i).getWidth() * i, 0, null);
        }

        lifeView.setImageBitmap(lifeBlock);
        lifeView.setLayoutParams(lifeLayoutParams);
        lifeView.setId(R.id.lifeView);

        return lifeView;

    }
}

