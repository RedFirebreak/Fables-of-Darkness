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
    private int carryWeight;
    private int maxCarryWeight;
    private int HP;
    private int maxHP;
    
    /**
     * Create a player for player management, initialize the inventory and the hp.
     */
    public Player() {
        playerInventory = new ArrayList<String>();
        HP = 0;
    }
    
    //Start of player's hp section
    /**
     * @param Heal the player the amount given.
     */
    public void healPlayer(int healAmount) {
        HP += healAmount;
    }
    
    /**
     * @param Damage the player the amount given.
     */
    public void damagePlayer(int damageAmount) {
        HP -= damageAmount;
    }
    
    /**
     * @param Set the player's Max HP.
     */
    public void setMaxHP(int HPsetter) {
        maxHP = HPsetter;
    }
    
    /**
     * @param Set the player's HP.
     */
    public void setHP(int HPsetter) {
        HP = HPsetter;
    }
    
    /**
     * @return The player's maximum HP.
     */
    public int getMaxHP() {
        return maxHP;
    }
    
    /**
     * @return The player's current HP.
     */
    public int getHP() {
        return HP;
    }
    //End of player's hp section
    
    //Start of player's back stack section
    /**
     * [[ENTER JAVADOC]]
     */
    public void addBack(String addToBack) {
        back.push(addToBack); // Add something to the stack
        // int index = back.search("3"); // Search the thirdindex = 3
    }

    /**
     * [[ENTER JAVADOC]]
     */
    public void removeBack() {
        back.pop();
    }

    /**
     * [[ENTER JAVADOC]]
     */
    public Stack<String> getBack() {
        return back;
    }
    //End of player's back stack section
    
    //Start of player inventory section
    /**
     * @param The item to be added to the player's inventory.
     */
    public void addItemToInventory(String itemName) {
        playerInventory.add(itemName);
    }
    
    /**
     * @param The item to be removed from the player's inventory.
     */
    public void removeItemFromInventory(String itemName) {
        playerInventory.remove(itemName);
    }
    
    /**
     * @return The player's current inventory.
     */
    public List<String> getPlayerInventory() {
        return playerInventory;
    }
    //End of player's inventory section
    
    //Start of player's carryweight section
    /**
     * @param Add the carryweight of the new item to the current carryweight.
     */
    public void addToCarryWeight(int carryWeight) {
        this.carryWeight += carryWeight;
    }
    
    /**
     * @param Remove the carryweight of the dropped or eaten item from the current carryweight.
     */
    public void removeFromCarryWeight(int carryWeight) {
        this.carryWeight -= carryWeight;
    }
    
    /**
     * @param Set the initial carryweight of the player.
     */
    public void setCarryWeight(int carryWeight) {
        this.carryWeight = carryWeight;
    }
    
    /**
     * @param Set the maximum carryweight of the player.
     */
    public void setMaxCarryWeight(int maxCarryWeight) {
        this.maxCarryWeight = maxCarryWeight;
    }
    
    /**
     * @return The player's current carryweight.
     */
    public int getCarryWeight() {
        return carryWeight;
    }
    
    /**
     * @return The player's maximum carryweight.
     */
    public int getMaxCarryWeight() {
        return maxCarryWeight;
    }
    //End of player's carryweight section
}
