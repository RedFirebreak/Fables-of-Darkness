import java.util.*;

/**
 * The player class contains everything that is dedicated to the player.
 *
 * @author Stefan Kuppen / Stefan Jilderda
 * @version 14-01-2020
 */
public class Player {
    private Stack<String> back = new Stack<String>();
    private Room currentRoom;
    private List<Item> playerInventory;

    private int carryWeight = 0;
    private int maxCarryWeight = 50;
    private int healthPoints = 20;

    private Item playerWeapon;
    private Item playerArmor;
    private Item unarmed;
    private Item naked;

    private int maxHit = 3;
    private int minHit = 1;
    private int armorCount = 0;

    /**
     * Create a player for player management, initialize the inventory and the hp.
     */
    public Player() {
        playerInventory = new ArrayList<Item>();
        unarmed = new Item("unarmed","Nothing in your hands equipped.","weapon",1,3,0,0,0,0,false,false);
        naked = new Item("naked","Nothing is on your body.","armor",0,0,0,0,0,0,false,false);
        playerWeapon = unarmed;
        playerArmor = naked;
    }

    public void setArmorCount(int armorCount) {
        this.armorCount = armorCount;
    }

    public void setArmor(Item armorPiece){
        this.playerArmor = armorPiece;
    }

    public void setWeapon(Item weaponPiece){
        this.playerWeapon = weaponPiece;
    }

    //Adders
    public void addHealth(int amount) {
        healthPoints = healthPoints + amount;
        if (healthPoints > 20){
            healthPoints = 20; // make sure the players health can never be above 20
        }
    }

    /**
     * [[ENTER JAVADOC]]
     */
    public void addBack(String addToBack) {
        back.push(addToBack); // Add something to the stack
        // int index = back.search("3"); // Search the thirdindex = 3
    }

    /**
     * @param  itemName The item to be added to the player's inventory.
     * @return          The boolean whether the item will be picked up or not.
     */
    public boolean addItemToInventory(Item itemName) {
        boolean canPickup = false;
        int itemWeight = itemName.getItemWeight();
        int currentCarryWeight = carryWeight;

        if (itemName.getItemPickupAble()) {
            int checkWeight = currentCarryWeight + itemWeight;
            if(checkWeight>maxCarryWeight) {
                canPickup = false;
            } else {
                carryWeight = checkWeight;
                playerInventory.add(itemName);
                canPickup = true;
            }
        }
        return canPickup;
    }

    /**
     * @param Add the carryweight of the new item to the current carryweight.
     */
    public void addToCarryWeight(int carryWeight) {
        this.carryWeight += carryWeight;
    }

    // Removers
    public void removeHealth(int amount) {
        healthPoints = healthPoints - amount;
        if (healthPoints < 0){
            healthPoints = 0; // make sure the players health can never be below 0
        }
    }

    /**
     * Remove the latest item from the back stack
     */
    public void removeBack() {
        back.pop();
    }

    /**
     * @param The item to be removed from the player's inventory.
     */
    public void removeItemFromInventory(Item itemName) {
        playerInventory.remove(itemName);
        removeFromCarryWeight(itemName.getItemWeight());
    }

    /**
     * @param Remove the carryweight of the dropped or eaten item from the current carryweight.
     */
    public void removeFromCarryWeight(int carryWeight) {
        this.carryWeight -= carryWeight;
    }

    /**
     * Clears the entire back stack
     */
    public void clearBack() {
        back.clear();
    }

    // Setters
    public void setCurrentRoom(Room room) {
        currentRoom = room;
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

    public void setMinHit(int minHit) {
        this.minHit = minHit;
    }

    public void setMaxHit(int maxHit) {
        this.maxHit = maxHit;
    }

    // Getters
    /**
     * @return The player's current armorrating.
     */
    public int getPlayerArmorRating() {
        int armorRating = 0;
        if (playerArmor == null) {
            armorRating = 0;
        } else {
            armorRating = playerArmor.getItemArmorRating();// een int is altijd 0 als die nog niet bestaat. dus geen "NULL"
        }
        return armorRating;
    }

    /**
     * @return The player's current inventory.
     */
    public List<Item> getPlayerInventory() {
        return playerInventory;
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

    /**
     * [[ENTER JAVADOC]]
     */
    public Stack<String> getBack() {
        return back;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public int getHealth() {
        return healthPoints;
    }

    public int getMinHit() {
        return minHit;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public int getArmorCount() {
        return armorCount;
    }

    public Item getPlayerWeapon() {
        return playerWeapon;
    }

    public Item getPlayerArmor() {
        return playerArmor;
    }
}
