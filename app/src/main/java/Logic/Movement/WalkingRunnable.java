package Logic.Movement;

import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import Logic.Random.GameElementRandomizer;

/**
 * Created by jeanine on 5/25/17.
 */

public class WalkingRunnable implements Runnable {

    private final byte LEFT = 0b0000;
    private final byte RIGHT = 0b0001;
    private final byte UP = 0b0010;
    private final byte DOWN = 0b0011;

    private final int X_MOVEMENT_INTERVAL = 10;
    private final int Y_MOVEMENT_INTERVAL = 10;

    private Handler handler;
    private ImageView knight;
    private RelativeLayout.MarginLayoutParams layoutParams;
    private float knightXCoord;
    private float knightYCoord;

    private int knightHeight;
    private int knightWidth;

    private int screenWidth;
    private int screenHeight;

    public WalkingRunnable(Handler handler, ImageView knight, int knightWidth, int knightHeight, int screenWidth, int screenHeight) {

        this.handler = handler;
        this.knight = knight;
        this.layoutParams = (RelativeLayout.MarginLayoutParams)knight.getLayoutParams();

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.knightHeight = knightHeight;
        this.knightWidth = knightWidth;
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

        if(knightXCoord - X_MOVEMENT_INTERVAL >= (0 + knightWidth)) {
            layoutParams.leftMargin = (int)(knightXCoord - X_MOVEMENT_INTERVAL);

            knight.setLayoutParams(layoutParams);
        }
    }

    private void moveRight() {
        if(knightXCoord + X_MOVEMENT_INTERVAL <= (screenWidth - knightWidth)) {
            layoutParams.leftMargin = (int)(knightXCoord + X_MOVEMENT_INTERVAL);

            knight.setLayoutParams(layoutParams);
        }
    }

    private void moveUp() {
        if((knightYCoord - Y_MOVEMENT_INTERVAL) >= (0 + knightHeight)) {
            layoutParams.topMargin = (int)(knightYCoord - Y_MOVEMENT_INTERVAL);

            knight.setLayoutParams(layoutParams);

        }
    }

    private void moveDown() {
        if((knightYCoord + Y_MOVEMENT_INTERVAL) <= (screenHeight - knightHeight)) {
            layoutParams.topMargin = (int)(knightYCoord + Y_MOVEMENT_INTERVAL);

            knight.setLayoutParams(layoutParams);
        }
    }

}
