import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Set;

/**
 * Class Room - a room in this adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is connected to other rooms via exits. 
 * For each existing exit, the room stores a reference to the neighboring room.
 * 
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24.01.2020
 */
public class Room {
    private String shortDescription;
    private String longDescription;
    private String roomID;
    private boolean canHoldItem;
    private boolean isLocked;
    private boolean isTrapRoom;

    private boolean canBeBurned;
    private boolean winRoom;
    
    private boolean unlockRoom;
    private String unlockItem;
    private String unlocksRoomID;

    private boolean hasEnemy;

    private HashMap<String, Room> exits; // stores exits of this room.
    private ArrayList<Item> roomInventory; // stores items of this room.

    private Stack<Enemy> enemies;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     * 
     * @param ID               The room ID.
     * @param shortDescription The short description of the room.
     * @param longDescription  The long description of the room.
     * @param canHoldItem      Checks if the room may hold items for the randomly generated items.
     */
    public Room(String ID, String shortDescription, String longDescription, boolean canHoldItem) {
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.roomID = ID;
        this.canHoldItem = canHoldItem;
        isLocked = false;
        isTrapRoom = false;

        this.roomInventory = new ArrayList<Item>();
        exits = new HashMap<>();

        hasEnemy = false;

        canBeBurned= false;

        unlockRoom = false;
        unlockItem = "item_does_not_exist";
        winRoom = false;
        unlocksRoomID = "0";
    }

    //Adders
    /**
     * Add something to the current room inventory
     */
    public void setRoomInventory(Item itemToBeAdded) {
        roomInventory.add(itemToBeAdded);
    }

    /**
     * Add an enemy (or enemies) to the room.
     */
    public void addEnemy(Enemy enemy) {
        enemies = new Stack<Enemy>(); // make a new stack for "all" the enemies.
        hasEnemy = true;
        enemies.push(enemy); // Add something to the stack
    }

    //Removers
    /**
     * @param itemToBeRemoved Removes an item from the current room inventory.
     */
    public void removeRoomInventory(Item itemToBeRemoved) {
        if (roomInventory.contains(itemToBeRemoved)) { // check if it contains the item
            roomInventory.remove(itemToBeRemoved);
        }
    }

    /**
     * Remove an enemy from a room.
     */
    public void removeEnemy() {   
        enemies.pop();
        Enemy moreEnemies = enemies.peek(); // check if there are more enemies in the room.
        if (moreEnemies == null) {
            hasEnemy = false;
        }
    }

    //Setters
    /**
     * @param unlockRoom    Whether or not the room is an unlockroom.
     * @param unlockItem    The item where the unlocksRoomID is opened with.
     * @param unlocksRoomID The String of the room ID number to open.
     */
    public void setUnlockRoom(boolean unlockRoom, String unlockItem, String unlocksRoomID) {
        this.unlockRoom = unlockRoom;
        this.unlockItem = unlockItem;
        this.unlocksRoomID = unlocksRoomID;
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * @param hasEnemy Sets if the room has an enemy or not.
     */
    public void setHasEnemy(boolean hasEnemy) {
        this.hasEnemy = hasEnemy;
    }

    /**
     * @param canBurn sets if you can burn something in this room.
     */
    public void setCanBeBurned(boolean canBurn) {
        canBeBurned = canBurn;
    }

    public boolean getIsTrapRoom() {
        return isTrapRoom;
    }
    
    public boolean getWinRoom() {
        return winRoom;
    }
    
    public void setWinRoom(boolean isWinRoom){
        winRoom = isWinRoom;
    }

    /**
     * @param isTrapRoom Sets the room as a traproom.
     */
    public void setIsTrapRoom(boolean isTrapRoom) {
        this.isTrapRoom = isTrapRoom;
    }

    /**
     * @param True Sets a lock on the room.
     */
    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * Unlocks the room.
     */
    public void unlockRoom() {
        setIsLocked(false);
    }

    /**
     * Locks the room.
     */
    public void lockRoom() {
        setIsLocked(true);
    }

    /**
     * @param ID Sets the room ID.
     */
    public void setRoomID(String ID) {
        this.roomID = ID;
    }

    //Getters
    /**
     * @return Returns whether or not the room is an unlockRoom.
     */
    public boolean getUnlockRoom() {
        return unlockRoom;
    }

    /**
     * @return Returns the item where the next room can be unlocked with.
     */
    public String getUnlockItem() {
        return unlockItem;
    }

    /**
     * @return Returns the String of the roomID that can be unlocked.
     */
    public String getUnlocksRoomID() {
        return unlocksRoomID;
    }

    /**
     * @return True if you can burn something in this room.
     */
    public boolean getCanBeBurned() {
        return canBeBurned;
    }

    /**
     * @return True if this room is a Trapdoor.
     */
    public boolean getIsTrapRoom() {
        return isTrapRoom;
    }

    /**
     * @return True if the room is locked.
     */
    public boolean getIsLocked() {
        return isLocked;
    }

    /**
     * @return True if the room can hold items.
     */
    public boolean canRoomHoldItems() {
        return canHoldItem;
    }

    /**
     * @return True in the room contains items.
     */
    public boolean doesRoomContainItems() {
        boolean returnBoolean = false;
        if (!roomInventory.isEmpty()) { // check if the room is empty
            returnBoolean = true;
        }
        return returnBoolean;
    }

    /**
     * Get the current room ID.
     * @return roomID is the ID of the room.
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Get the current room inventory.
     */
    public ArrayList<Item> getRoomInventory() {
        return roomInventory;
    }

    /**
     * @return The short description of the room.
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @return The long description of the room.
     */
    public String getlongDescription() {
        return longDescription;
    }

    /**
     * Return a description of the room in the form: 
     * You are in the kitchen. 
     * Exits: north west
     * 
     * @return A short description of this room with the exits
     */
    public String getRoomDescription() {
        return "You are standing in " + shortDescription + ".\n" + getlongDescription() + ".\n" +getExitString();
    }

    /**
     * Return a String describing the room's exits, for example "Exits: north west".
     * 
     * @return Details of the room's exits.
     */
    public String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }
    
    /**
     * @return True if the room has an enemy.
     */
    public boolean hasEnemy(){
        return hasEnemy;
    }

    /**
     * @return The enemies in the room.
     */
    public Enemy getEnemy(){
        Enemy enemy = null;
        if (hasEnemy) {
            enemy = enemies.peek();
        }
        return enemy;
    }
}