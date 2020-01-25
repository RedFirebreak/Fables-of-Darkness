import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

/**
 * The player class contains everything that is dedicated to the player.
 *
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24.01.2020
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

    //Adders
    /**
     * @param amount is the amount of health added to the player.
     */
    public void addHealth(int amount) {
        healthPoints = healthPoints + amount;
        if (healthPoints > 20){// make sure the players health can never be above 20
            healthPoints = 20; 
        }
    }

    /**
     * @param addToBack add the String to the backStack
     */
    public void addBack(String addToBack) {
        back.push(addToBack); // Add something to the stack
    }

    /**
     * @param  itemName The item to be added to the player's inventory.
     * @return The boolean whether the item will be picked up or not.
     */
    public boolean addItemToInventory(Item itemName) {
        boolean canPickup = false;
        int itemWeight = itemName.getItemWeight();
        int currentCarryWeight = carryWeight;

        if (itemName.getItemPickupAble()) { // check if item can be picked up
            int checkWeight = currentCarryWeight + itemWeight;
            if(checkWeight>maxCarryWeight) { // check if player carryweight plus the new item does not exceed the maxcarryweight
                canPickup = false;
            } else { // add the item to the inventory
                carryWeight = checkWeight; // set the new carryweight
                playerInventory.add(itemName); // add the item to the inventory
                canPickup = true;
            }
        }
        return canPickup;
    }

    /**
     * Only used for equip, when inventory is full you dont lose the item.
     * 
     * @param  itemName The item to be added to the player's inventory, or room inventory.
     * @return The boolean whether the item will be picked up or not.
     */
    public boolean addItemToInventoryFromEquip(Item itemName) {
        boolean canPickup = false;
        int itemWeight = itemName.getItemWeight();
        int currentCarryWeight = carryWeight;
        int checkWeight = currentCarryWeight + itemWeight;
        
        if(checkWeight>maxCarryWeight) { // check if player carryweight plus the new item does not exceed the maxcarryweight
            canPickup = false;
            currentRoom.setRoomInventory(itemName);
            System.out.println("You drop the item on the floor because you inventory is full.");
        } else { // add the item to the inventory
            carryWeight = checkWeight; // set the new carryweight
            playerInventory.add(itemName); // add the item to the inventory
            canPickup = true;
        }

        return canPickup;
    }
    
    // Removers
    /**
     * @param amount Removes some health equal to the amount
     */
    public void removeHealth(int amount) {
        healthPoints = healthPoints - amount;
        if (healthPoints < 0){ // make sure the players health can never be below 0
            healthPoints = 0; 
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
     * @param Remove the carryweight of the dropped, eaten or used item from the current carryweight.
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
    /**
     * @param armorCount changes the armorCount.
     */
    public void setArmorCount(int armorCount) {
        this.armorCount = armorCount;
    }

    /**
     * @param armorPiece changes the player's armor.
     */
    public void setArmor(Item armorPiece){
        this.playerArmor = armorPiece;
    }

    /**
     * @param weaponPiece changes the player's weapon.
     */
    public void setWeapon(Item weaponPiece){
        this.playerWeapon = weaponPiece;
    }

    /**
     * @param room Sets the currentRoom.
     */
    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    /**
     * @param minHit Sets the new minimum damage hit
     */
    public void setMinHit(int minHit) {
        this.minHit = minHit;
    }

    /**
     * @param minHit Sets the new maximum damage hit
     */
    public void setMaxHit(int maxHit) {
        this.maxHit = maxHit;
    }

    // Getters
    /**
     * @return The player's current armorCount
     */
    public int getPlayerArmorRating() {
        int armorRating = 0;
        if (playerArmor == null) { // never have armorRating at 0
            armorRating = 0;
        } else {
            armorRating = playerArmor.getItemArmorRating(); // set the armorCount equal to the currently equipped armor
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
     * @return The whole backStack, full of roomID Strings.
     */
    public Stack<String> getBack() {
        return back;
    }

    /**
     * @return The player's current room.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * @return The player's current health.
     */
    public int getHealth() {
        return healthPoints;
    }

    /**
     * @return The player's current minimum damage hit.
     */
    public int getMinHit() {
        return minHit;
    }

    /**
     * @return The player's current maximum damage hit.
     */
    public int getMaxHit() {
        return maxHit;
    }

    /**
     * @return The player's current armorCount.
     */
    public int getArmorCount() {
        return armorCount;
    }

    /**
     * @return The player's currently equipped weapon.
     */
    public Item getPlayerWeapon() {
        return playerWeapon;
    }

    /**
     * @return The player's currently equipped armor.
     */
    public Item getPlayerArmor() {
        return playerArmor;
    }
}