import java.util.*;

/**
 * The player class contains everything that is dedicated to the player.
 *
 * @author (Stefan Kuppen / Stefan Jilderda)
 * @version (14-01-2020)
 */
public class Player {
    private Stack<String> back = new Stack<String>();
    private List<String> playerInventory;

    // voeg current room hier toe
    /**
     * Create a player for player management
     */
    public Player() {
        playerInventory = new ArrayList<String>();
        // om items toe te voegen: playerInventory.add("itemname");
        // view command met: System.out.println(inv);
        int HP;
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
    
    public void addItemToInventory(String itemName) {
        playerInventory.add(itemName);
    }
    
    public void removeItemFromInventory(String itemName) {
        playerInventory.remove(itemName);
    }
    
    public List<String> getPlayerInventory() {
        return playerInventory;
    }
}
