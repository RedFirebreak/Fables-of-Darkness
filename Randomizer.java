import java.util.*;

/**
 * Write a description of class Randomizer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Randomizer
{
    /**
     * Create the randomizer for the items in rooms. Every item will generate in a different place every time the game is started.
     */
    public Randomizer() {
    }

    public int getRandomNumber(int maxNumber) {
        Random randomMethod = new Random();
        int randomNumber = randomMethod.nextInt(maxNumber);
        if (randomNumber == 0) {
         randomNumber++;   
        }
        return randomNumber;
    }

   public void addItemToRandomRoom(String item) {

       /* O/
        String randomlyGeneratedNumber = "0"; //gonna be the roomID number

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
        }*/

    }
}
