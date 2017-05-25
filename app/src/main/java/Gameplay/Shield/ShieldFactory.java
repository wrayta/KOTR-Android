package Gameplay.Shield;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.thomas.kotr_android.R;

import java.util.List;

/**
 * Created by jeanine on 5/16/17.
 */

public class ShieldFactory extends ContextWrapper {

    private int[] shields;
    private int id;

    public ShieldFactory (Context base, int id, int[] shields) {
        super(base);

        this.shields = new int[shields.length];

        for(int i = 0; i < shields.length; i++) {
            this.shields[i] = shields[i];
        }

        this.id = id;
    }

    public ShieldFactory (Context base, int id, int[] shields, List<Integer> shieldsToIncludeIndices) {
        super(base);

        this.shields = new int[shieldsToIncludeIndices.size()];

        for(int i = 0; i < shieldsToIncludeIndices.size(); i++) {
            this.shields[i] = shields[shieldsToIncludeIndices.get(i)];
        }

        this.id = id;
    }

    public ImageView createShields() {

        ImageView shieldsView = new ImageView(this);
        RelativeLayout.LayoutParams shieldLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        shieldLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        Bitmap[] shieldBitMaps = new Bitmap[shields.length];

        for(int i = 0; i < shields.length; i++) {

            shieldBitMaps[i] = BitmapFactory.decodeResource(getResources(), shields[i]);

        }

        Bitmap shieldBlock = Bitmap.createBitmap(shieldBitMaps[0].getWidth() * shieldBitMaps.length, shieldBitMaps[0].getHeight(), Bitmap.Config.ARGB_8888);
        Canvas shieldCanvas = new Canvas(shieldBlock);

        for(int i = 0; i < shieldBitMaps.length; i++) {
            shieldCanvas.drawBitmap(shieldBitMaps[i], shieldBitMaps[i].getWidth() * i, 0, null);
        }

        shieldsView.setImageBitmap(shieldBlock);
        shieldsView.setLayoutParams(shieldLayoutParams);
        shieldsView.setId(id);

        return shieldsView;
    }

    public ImageView createShields(int keyPointer) {
        ImageView shieldsView = new ImageView(this);
        RelativeLayout.LayoutParams shieldLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        shieldLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        Bitmap[] shieldBitMaps = new Bitmap[shields.length];

        for(int i = 0; i < shields.length; i++) {

            while(i < keyPointer) {
                shieldBitMaps[i] = BitmapFactory.decodeResource(getResources(), R.drawable.plain_shield_v2);
                i++;
            }

            if(i < shields.length) {
                shieldBitMaps[i] = BitmapFactory.decodeResource(getResources(), shields[i]);
            }
        }

        Bitmap shieldBlock = Bitmap.createBitmap(shieldBitMaps[0].getWidth() * shieldBitMaps.length, shieldBitMaps[0].getHeight(), Bitmap.Config.ARGB_8888);
        Canvas shieldCanvas = new Canvas(shieldBlock);

        for(int i = 0; i < shieldBitMaps.length; i++) {
            shieldCanvas.drawBitmap(shieldBitMaps[i], shieldBitMaps[i].getWidth() * i, 0, null);
        }

        shieldsView.setImageBitmap(shieldBlock);
        shieldsView.setLayoutParams(shieldLayoutParams);
        shieldsView.setId(id);

        return shieldsView;
    }

}
