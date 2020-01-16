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
    private Stack items;
    private List roomItems = new ArrayList();
    private int itemCount = 0;
    
    /**
    * Create the randomizer for the items in rooms. Every item will generate in a different place every time the game is started.
    */
    public Randomizer() {
        //Get room amount
        Game room = new Game();
        Room currentRoom = room.getCurrentRoom();
        //roomCount = currentRoom.getRoomAmount();

        //Get item stack from Levels
        Levels item = new Levels();
        items = item.getItemStack();

        //Get item stack count from Levels
        Levels count = new Levels ();
        itemCount = count.getStackCount();
    }

    public int getRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(roomCount);
        return randomNumber;
    }

    public void randomizeItems() {
        int random = 0;
        if(roomCount<itemCount) {
            for(int i = 0; i<roomCount; i++) {
                random = getRandomNumber(); 
                roomItems.add (random, items.peek());
                items.pop();
            }
        }
        else {
            for(int i = 0; i<itemCount; i++) {
                random = getRandomNumber(); 
                roomItems.add (random, items.peek());
                items.pop();
            }
        }
    }

}
