import java.util.*;

/**
 * The player class contains everything that is dedicated to the player.
 *
 * @author (Stefan Kuppen / Stefan Jilderda)
 * @version (14-01-2020)
 */
public class Player {
    private Stack<String> back = new Stack<String>();
    private Room currentRoom;
    private int HealthPoints = 20;

    /**
     * Create a player for player management
     */
    public Player() {
        List<String> inv = new ArrayList<String>();
        // om items toe te voegen: inv.add("itemname");
        // view command met: System.out.println(inv);
    }

    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    public int getHealth() {
        return HealthPoints;
    }

    public void removeHealth(int amount) {
        HealthPoints = HealthPoints - amount;
        if (HealthPoints < 0){
            HealthPoints = 0; // make sure the players health can never be below 0
        }
    }

    public void addHealth(int amount) {
        HealthPoints = HealthPoints + amount;
        if (HealthPoints > 20){
            HealthPoints = 20; // make sure the players health can never be above 20
        }
    }

    public void addBack(String addToBack) {
        back.push(addToBack); // Add something to the stack
        // int index = back.search("3"); // Search the thirdindex = 3
    }

    public void removeBack() {
        back.pop();
    }

    public Stack<String> getBack() {
        return back;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

}
