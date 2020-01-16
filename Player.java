import java.util.*;

/**
 * The player class contains everything that is dedicated to the player.
 *
 * @author (Stefan Kuppen)
 * @version (14-01-2020)
 */
public class Player
{

    /**
     * Create a player for player management
     */
    public Player(){
        List<String> inv = new ArrayList<String>();
        // om items toe te voegen: inv.add("itemname");
        // view command met: System.out.println(inv);
        int HP;
    }
    
    public void backcommand(){
        Stack back = new Stack();
        
        back.push("1"); // Add something to the stack
        back.pop(); // Remove the top item from the stack
        back.peek(); // View the top item from the stack
        int index = back.search("3"); // Search the thirdindex = 3
        
    
    }

}
