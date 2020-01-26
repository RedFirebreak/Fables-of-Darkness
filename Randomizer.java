import java.util.Random;

/**
 * Randomizer is a Random Number Generator, generates random numbers.
 *
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24.01.2020
 */
public class Randomizer
{
    /**
     * Create the randomizer.
     */
    public Randomizer() {
    }

    /**
     * Get a random number between 1 and maxNumber.
     * 
     * @param  maxNumber The maximum number to be generated.
     * @return The randomly generated number as an int. (between 1 and maxNumber)
     */
    public int getRandomNumber(int maxNumber) {
        Random randomMethod = new Random();
        int randomNumber = 1 + (randomMethod.nextInt(maxNumber-1)); // do the random generating, eliminating 0
        return randomNumber;
    }

    /**
     * Get a random number between 0 and maxNumber.
     * 
     * @param  maxNumber The maximum number to be generated.
     * @return The randomly generated number as an int. (between 0 and maxNumber)
     */
    public int getRandomDamage(int maxNumber) {
        Random randomMethod = new Random();
        int randomNumber = randomMethod.nextInt(maxNumber);
        return randomNumber;
    }
}