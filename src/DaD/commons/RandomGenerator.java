package DaD.commons;

import java.util.Random;

/**
 * Class use to make random generation easier.
 */
public class RandomGenerator {
    /**
     * Generate an int between 0 and 100.
     * If number generated is between 0 and successChance
     * then return true.
     * @param successChance Chance to success, from 0 to 100.
     * @return boolean
     */
    public static boolean RNG(double successChance){
        Random random = new Random();
        // Generate a random number from 0 to 100
        int result = random.nextInt(100 + 1);
        // If the result is between 0 & 20 (0 < result <= 20), then it is a success
        if(result <= successChance) {
            return true;
        }
        // Else, the result is higher than the successChance, it is a fail
        return false;
    }

    /**
     * Return an int between 0 and 100.
     * @return int
     */
    public static int RNG(){ // This will be used when require a random number from 0 to 100
        Random random = new Random();
        // Generate a random number from 0 to 100
        return random.nextInt(100 + 1);
    }
}
