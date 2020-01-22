import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is connected
 * to other rooms via exits. For each existing exit, the room stores a reference
 * to the neighboring room.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room {
    private String shortDescription;
    private String longDescription;
    private String roomID;
    private boolean canHoldItem;
    private boolean isLocked;
    private boolean isTrapRoom;
    private boolean canBeBurned;

    private boolean hasEnemy;

    private HashMap<String, Room> exits; // stores exits of this room.
    private ArrayList<String> roomInventory; // stores items of this room.

    private Stack<Enemy> enemies;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     * 
     * @param description The room's description.
     */
    public Room(String ID, String shortDescription, String longDescription, boolean canHoldItem) {
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.roomID = ID;
        this.canHoldItem = canHoldItem;
        isLocked = false;
        isTrapRoom = false;

        this.roomInventory = new ArrayList<String>();
        exits = new HashMap<>();
        hasEnemy = false;
        canBeBurned = false;
    }
    
    public void setCanBeBurned(boolean canBurn) {
        canBeBurned = canBurn;
    }
    
    public boolean getCanBeBurned() {
        return canBeBurned;
    }

    public boolean getIsTrapRoom() {
        return isTrapRoom;
    }

    public void setIsTrapRoom(boolean isTrapRoom) {
        this.isTrapRoom = isTrapRoom;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean getIsLocked() {
        return isLocked;
    }

    public void unlockRoom() {
        setIsLocked(false);
    }

    public void lockRoom() {
        setIsLocked(true);
    }

    public boolean canRoomHoldItems() {
        return canHoldItem;
    }

    /**
     * Add something to the current room inventory
     */
    public void setRoomInventory(String itemToBeAdded) {
        roomInventory.add(itemToBeAdded);
    }

    public void removeRoomInventory(String itemToBeRemoved) {
        if (roomInventory.contains(itemToBeRemoved)) {
            roomInventory.remove(itemToBeRemoved);
        }
    }

    public boolean doesRoomContainItems() {
        boolean returnBoolean = false;

        if (!roomInventory.isEmpty()) {
            returnBoolean = true;
        }

        return returnBoolean;
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

    public void setRoomID(String ID) {
        this.roomID = ID;
    }

    // All getters follow now
    /**
     * Get the current room ID
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Get the current room inventory
     */
    public ArrayList getRoomInventory() {
        return roomInventory;
    }

    /**
     * @return The short description of the room (the one that was defined in the
     *         constructor).
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @return The long description of the room (the one that was defined in the
     *         constructor).
     */
    public String getlongDescription() {
        return longDescription;
    }

    /**
     * Return a description of the room in the form: You are in the kitchen. Exits:
     * north west
     * 
     * @return A long description of this room
     */
    public String getRoomDescription() {
        return "You are standing in " + shortDescription + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example "Exits: north west".
     * 
     * @return Details of the room's exits.
     */
    private String getExitString() {
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

    public boolean hasEnemy(){
        return hasEnemy;
    }

    public void addEnemy(Enemy enemy) {
        System.out.println("DEBUG: ADDED ENEMY TO ROOM ID"+ getRoomID());
        enemies = new Stack<Enemy>(); // make a new stack for "all" the enemies.
        hasEnemy = true;
        enemies.push(enemy); // Add something to the stack
    }

    public Enemy getEnemy(){
        Enemy enemy = null;
        if (hasEnemy) {
            enemy = enemies.peek();
        }
        return enemy;
    }

    public void removeEnemy() {   
        enemies.pop();
        Enemy moreEnemies = enemies.peek(); // check if there are more enemies in the room.
        if (moreEnemies == null) {
            hasEnemy = false;
        }
    }

}
