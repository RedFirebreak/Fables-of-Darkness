import java.util.*;

/**
 * Write a description of class Randomizer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Randomizer
{
    private int roomCount;
    public Randomizer() {
        roomCount = 20; //amount of rooms and crossroads.
    }
    
    public int getRandomNumber() {
        Random random = new Random();
        int randomnumber = random.nextInt(roomCount);
    }
    
    
    
    
}
