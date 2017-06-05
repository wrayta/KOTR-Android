package Logic.Movement;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.thomas.kotr_android.R;

import Logic.Random.GameElementRandomizer;

/**
 * Created by jeanine on 5/25/17.
 */

public class WalkingRunnable extends ContextWrapper implements Runnable {

    private final byte LEFT = 0b0000;
    private final byte RIGHT = 0b0001;
    private final byte UP = 0b0010;
    private final byte DOWN = 0b0011;

    private final int X_MOVEMENT_INTERVAL = 10;
    private final int Y_MOVEMENT_INTERVAL = 10;

    private Handler handler;
    private ImageView knight;
    private RelativeLayout.MarginLayoutParams layoutParams;
    private ImageView[] knights;
    private float knightXCoord;
    private float knightYCoord;

    private int knightHeight;
    private int knightWidth;

    private int screenWidth;
    private int screenHeight;

    public WalkingRunnable(Context base, Handler handler, ImageView knight, int knightWidth, int knightHeight, int screenWidth, int screenHeight, ImageView[] allKnightsOnScreen) {
        super(base);
        this.handler = handler;
        this.knight = knight;
        this.layoutParams = (RelativeLayout.MarginLayoutParams)knight.getLayoutParams();

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.knightHeight = knightHeight;
        this.knightWidth = knightWidth;

        this.knights = new ImageView[allKnightsOnScreen.length];

        for(int i = 0; i < allKnightsOnScreen.length; i++) {
            this.knights[i] = allKnightsOnScreen[i];
        }
    }

    @Override
    public void run() {
        getAndSetXAndYCoords();

        handler.postDelayed(this, 500);
    }

    private void getAndSetXAndYCoords() {

        knightXCoord = knight.getX();
        knightYCoord = knight.getY();

        Log.d("KNIGHT-ID", "ID: " + knight.getId());
        Log.d("X-COORD", "value: " + knightXCoord);
        Log.d("Y-COORD", "value: " + knightYCoord);


        byte direction = GameElementRandomizer.moveLeftToRightOrTopToBottom();

        if((direction & 0b1111) == LEFT) {
            moveLeft();
        } else if((direction & 0b1111) == RIGHT) {
            moveRight();
        } else if((direction & 0b1111) == UP) {
            moveUp();
        } else if((direction & 0b1111) == DOWN) {
            moveDown();
        }

    }

    private void moveLeft() {

        if(checkLeftScreenBounds()) {
            if(!intersectsImageView()) {
                layoutParams.leftMargin = (int) (knightXCoord - X_MOVEMENT_INTERVAL);

                knight.setLayoutParams(layoutParams);
            } else {
                reverseDirection(LEFT);
            }
        }
    }

    private void moveRight() {
        if(checkRightScreenBounds()) {

            if(!intersectsImageView()) {
                layoutParams.leftMargin = (int) (knightXCoord + X_MOVEMENT_INTERVAL);

                knight.setLayoutParams(layoutParams);
            } else {
                reverseDirection(RIGHT);
            }
        }
    }

    private void moveUp() {
        if(checkTopScreenBounds()) {

            if(!intersectsImageView()) {
                layoutParams.topMargin = (int) (knightYCoord - Y_MOVEMENT_INTERVAL);

                knight.setLayoutParams(layoutParams);
            } else {
                reverseDirection(UP);
            }

        }
    }

    private void moveDown() {
        if(checkBottomScreenBounds()) {

            if(!intersectsImageView()) {
                layoutParams.topMargin = (int) (knightYCoord + Y_MOVEMENT_INTERVAL);

                knight.setLayoutParams(layoutParams);
            } else {
                reverseDirection(DOWN);
            }
        }
    }

    private void reverseDirection(byte direction) {

        if(direction == LEFT) { //move right now

            if(checkRightScreenBounds()) {
                layoutParams.leftMargin = (int) (knightXCoord + X_MOVEMENT_INTERVAL);

                knight.setLayoutParams(layoutParams);
            }

        } else if(direction == RIGHT) { //move left now

            if(checkLeftScreenBounds()) {
                layoutParams.leftMargin = (int) (knightXCoord - X_MOVEMENT_INTERVAL);

                knight.setLayoutParams(layoutParams);
            }

        } else if(direction == UP) { //move down now

            if(checkBottomScreenBounds()) {
                layoutParams.topMargin = (int) (knightYCoord + Y_MOVEMENT_INTERVAL);

                knight.setLayoutParams(layoutParams);
            }

        } else if(direction == DOWN) { //move up now

            if(checkTopScreenBounds()) {
                layoutParams.topMargin = (int) (knightYCoord - Y_MOVEMENT_INTERVAL);

                knight.setLayoutParams(layoutParams);
            }
        }
    }

    private boolean checkLeftScreenBounds() {
        return (knightXCoord - X_MOVEMENT_INTERVAL >= (0 + knightWidth));
    }

    private boolean checkRightScreenBounds() {
        return (knightXCoord + X_MOVEMENT_INTERVAL <= (screenWidth - knightWidth));
    }

    private boolean checkTopScreenBounds() {
        return ((knightYCoord - Y_MOVEMENT_INTERVAL) >= (0 + knightHeight));
    }

    private boolean checkBottomScreenBounds() {
        return ((knightYCoord + Y_MOVEMENT_INTERVAL) <= (screenHeight - knightHeight));
    }

    private boolean intersectsImageView() {
        boolean intersects = false;

        final int[] loc = new int[2];

//        knight.getLocationOnScreen(loc);

        final Rect movingKnightsBounds = new Rect((int)(knightXCoord), (int)(knightYCoord),
                (int)(knightXCoord) + knightWidth, (int)(knightYCoord) + knightHeight);

        for(int i = 0; i < knights.length; i++) {
            knights[i].getLocationOnScreen(loc);
            Rect knightsBound = new Rect(loc[0], loc[1],
                    loc[0] + getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicWidth(),
                    loc[1] + getResources().getDrawable(R.drawable.knight_three_intro_resized_v2).getIntrinsicHeight());

            if(Rect.intersects(movingKnightsBounds, knightsBound)) {
                intersects = true;
                return intersects;
            }
        }

        return intersects;

    }

}
