package Gameplay.Life;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.thomas.kotr_android.R;

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
        int allLives[] = {R.drawable.life1, R.drawable.life2, R.drawable.life3};

        ImageView lifeView = new ImageView(this);
        RelativeLayout.LayoutParams lifeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lifeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lifeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lifeView.setImageResource(allLives[lives - 1]);
        lifeView.setLayoutParams(lifeLayoutParams);
        lifeView.setId(R.id.lifeView);

        return lifeView;

    }
}

