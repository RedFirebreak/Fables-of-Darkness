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
        unarmed = new Item("unarmed","Nothing in your hands equipped.","weapon",1,3,0,0,0,0,false);
        naked = new Item("naked","Nothing is on your body.","armor",0,0,0,0,0,0,false);
        playerWeapon = unarmed;
        playerArmor = naked;
    }

    public void setArmor(Item armorPiece){
        String category = armorPiece.getItemCategory();
        if (category.equals("armor")) {
            if (playerArmor != naked) {
                Item currentlyEquippedArmor = playerArmor; // Check for currently equipped armor
                addItemToInventory(playerArmor); // Place currently equipped armor in inventory
            }
            this.playerArmor = armorPiece; // Equip new armor
            armorCount = armorPiece.getItemArmorRating(); // Update armor
            System.out.println("You equip the " + armorPiece.getItemName() + ". Your armor value is now: " + armorCount); // inform user
        } else {
            System.out.println("You cant equip " + armorPiece.getItemName() + " as it is not an armor piece.");
        }
    }

    public void setWeapon(Item weaponPiece){
        String category = weaponPiece.getItemCategory();
        if (category.equals("weapon")) {
            if (playerWeapon != unarmed) {
                Item currentlyEquippedWeapon = playerWeapon; // Check for currently equipped weapon
                addItemToInventory(playerWeapon); // Place currently equipped weapon in inventory
            }
            this.playerWeapon = weaponPiece; // Equip new weapon
            minHit = weaponPiece.getItemMinDamage();
            maxHit = weaponPiece.getItemMaxDamage();
            System.out.println("You equip the " + weaponPiece.getItemName() + ". Your min damage is now: " + minHit + " and your max damage is now: " + maxHit + "."); // inform user
        } else {
            System.out.println("You cant equip " + weaponPiece.getItemName() + " as it is not a weapon.");
        }
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
     * @param The item to be added to the player's inventory.
     */
    public void addItemToInventory(Item itemName) {
        playerInventory.add(itemName);
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
