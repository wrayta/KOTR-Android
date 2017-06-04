package Logic.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jeanine on 5/24/17.
 */

public class GameElementRandomizer {

    public static <E> E pickRandomElementFromList(E[] list) {

        Random rng = new Random();

        Integer idIndex = rng.nextInt(list.length);

        return list[idIndex];

    }

    public static List<Integer> returnRndIntListFromIntBounds(int thingsToSelect, int allPossibleThings, List<Integer> key) {

        Random rng = new Random();

        for (int i = 0; i < thingsToSelect; i++) {
            while (true) {
                Integer next = rng.nextInt(allPossibleThings);
                if (!key.contains(next)) {
                    key.add(next);
                    break;
                }
            }
        }

        return key;

    }

    public static List<Integer> returnRndIntListIncludingElemsFromOtherList(int thingsToSelect, int allPossibleThings, List<?> key) {

        Random rng = new Random();
        List<Integer> generated = new ArrayList<Integer>();

        for (int i = 0; i < thingsToSelect; i++) {
            while (true) {
                Integer next = rng.nextInt(allPossibleThings);
                if (!generated.contains(next) && key.contains(next)) {
                    generated.add(next);
                    break;
                }
            }
        }

        return generated;
    }

    public static byte moveLeftToRightOrTopToBottom() {

        final int NUM_OF_DIRECTIONS = 4; //LEFT-RIGHT, UP-DOWN

        byte direction;

        Random rng = new Random();

        direction = (byte)(rng.nextInt(NUM_OF_DIRECTIONS));

        return direction;
    }

//    public static byte moveLeftToRight() {
//
//    }
}
