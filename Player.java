import java.util.*;

/**
 * The player class contains everything that is dedicated to the player.
 *
 * @author (Stefan Kuppen)
 * @version (14-01-2020)
 */
public class Player
{
    private Stack back = new Stack();
// voeg current room hier toe
    /**
     * Create a player for player management
     */
    public Player(){
        List<String> inv = new ArrayList<String>();
        // om items toe te voegen: inv.add("itemname");
        // view command met: System.out.println(inv);
        int HP;
    }
    
    public void addBack(String addToBack){       
        back.push(addToBack); // Add something to the stack
        //int index = back.search("3"); // Search the thirdindex = 3
    }

    public void removeBack(){       
        back.pop();
    }
    
    public Stack getBack(){       
        return back;
    }
}
