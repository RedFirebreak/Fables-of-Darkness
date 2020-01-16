import java.util.*;

/**
 * Write a description of class Randomizer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Randomizer
{
    private int roomCount = 0;
    
    public Randomizer() {
        Game room = new Game();
        Room currentRoom = room.getCurrentRoom();
        roomCount = currentRoom.getRoomAmount();
        System.out.println("DEBUG: roomamount: " + roomCount);
    }
    
    public int getRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(roomCount);
        return randomNumber;
    }
    
    public void randomizeItems() {
        int random = 0;
        
        for(int i = 0; i<roomCount; i++) {
            random = getRandomNumber(); 
            
        }
        
    }
    
}
