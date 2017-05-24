package Logic;

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

    public static List<Integer> returnRndIntListFromIntBounds(int thingsToSelect, int allPossibleThings) {

        Random rng = new Random();
        List<Integer> generated = new ArrayList<Integer>();

        for (int i = 0; i < thingsToSelect; i++) {
            while (true) {
                Integer next = rng.nextInt(allPossibleThings);
                if (!generated.contains(next)) {
                    generated.add(next);
                    break;
                }
            }
        }

        return generated;

    }

    public static List<Integer> returnRndIntListIncludingElemsFromOtherList(int thingsToSelect, int allPossibleThings, List<?> list) {

        Random rng = new Random();
        List<Integer> generated = new ArrayList<Integer>();

        for (int i = 0; i < thingsToSelect; i++) {
            while (true) {
                Integer next = rng.nextInt(allPossibleThings);
                if (!generated.contains(next) && list.contains(next)) {
                    generated.add(next);
                    break;
                }
            }
        }

        return generated;
    }
}
