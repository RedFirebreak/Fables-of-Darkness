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
    private Stack<String> itemStack;
    private ArrayList<String> roomInventory; // stores items of this room.
    private Room room;

    /**
     * Create the randomizer for the items in rooms. Every item will generate in a different place every time the game is started.
     */
    public Randomizer() {
        //Get room amount;
        roomCount = 24;
        itemStack = new Stack<String>();
        roomInventory = new ArrayList<String>();
        room = new Room("","",false);

    }

    public String getRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(roomCount); 
        String randomNumberString = Integer.toString(randomNumber);
        return randomNumberString;
    }

    public void setEmptyRoomInventories(int roomCount) {
        for( int i=0; i<roomCount; i++) {
            roomInventory.add("Empty");
        }
    }

    public ArrayList getAllRoomInventories() {
        return roomInventory;
    }

    public void randomizeRoomInventories() {
        String randomlyGeneratedNumber = "0"; //gonna be the roomID number
        Room randomlyGeneratedRoom = new Room("Empty Room", "Long Empty Room", false); //The room te be generated

        Levels level = new Levels(); //get the item Stack and total amount of rooms from levels
        itemStack = level.getItemStack(); //get the Stack locally
        int stackCount = level.getStackCount(); // get the amount of items in the stack
        roomCount=24;
        // roomCount = level.getTotalRooms(); //set the total amount of rooms

        //Check if roomcount or stackcount is lower
        if(stackCount>roomCount) {
            for(int i=0; i<roomCount; i++) {
                randomlyGeneratedNumber = getRandomNumber(); //generating the random number
                int roomIntID = Integer.parseInt(randomlyGeneratedNumber); //make the id from String to int
                String itemToPutOnRoomID = itemStack.peek();//check the top-most item on the stack and make it a String
                randomlyGeneratedRoom = room.getRoomFromID(randomlyGeneratedNumber); //find the room associated with the randomly generated number

                roomInventory.set(roomIntID, itemToPutOnRoomID);
                itemStack.pop();
                System.out.println("TEST VOOR ROOMCOUNT");

            }
        }
        else {
            for(int i=0; i<stackCount; i++) {
                randomlyGeneratedNumber = getRandomNumber(); //generating the random number
                int roomIntID = Integer.parseInt(randomlyGeneratedNumber); //make the id from String to int
                String itemToPutOnRoomID = itemStack.peek();//check the top-most item on the stack and make it a String
                randomlyGeneratedRoom = room.getRoomFromID(randomlyGeneratedNumber); //find the room associated with the randomly generated number

                roomInventory.set(roomIntID, itemToPutOnRoomID);
                itemStack.pop();
                System.out.println("TEST VOOR ROOMCOUNT");

            }
        }

    }

    public String getRoomInventory(String roomIDNumber) {
        int roomIntID = Integer.parseInt(roomIDNumber);
        return roomInventory.get(roomIntID);
    }

    public void setRoomInventory(String input) {
        roomInventory.add(input);
    }

    public void setRoomInventoryOnIDNumber(int roomNumber, String item) {
        roomInventory.set(roomNumber, item);
    }
}
