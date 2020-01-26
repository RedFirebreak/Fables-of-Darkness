/**
 * Enemy contains all information on enemies, and sets their variables.
 *
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24.01.2020
 */
public class Enemy{    
    private String enemyName; // the enemy name
    private int maxHealth; // the max health the enemy can have.
    private int healthPoints; // the amount of health the enemy has.
    private String description; // the description op the enemy
    private boolean alive = true; // Make sure the enemy is alive when created

    private int maxHit;
    private int minHit;

    private Room currentRoom;

    /**
     * Set the enemy's name, max and current health, description, max and min damage hit based on input.
     */
    public Enemy(String name, int health, String description, int minHit, int maxHit) {
        maxHealth = health; // set the max health
        healthPoints = health; // set the current health (to max)
        enemyName = name; // set the health
        this.description = description; // set the description
        this.maxHit = maxHit; // set the max hit
        this.minHit = minHit; // set the min hit
    }

    /**
     * @param amount removes amount of health from the enemy.
     */
    public void removeHealth(int amount) {
        healthPoints = healthPoints - amount;
        if (healthPoints < 0){
            healthPoints = 0; // make sure the enemy health can never be below 0
            alive = false;
        }
    }

    /**
     * @param amount adds amount of health from the enemy.
     */
    public void addHealth(int amount) {
        healthPoints = healthPoints + amount;
        if (healthPoints > 20){
            healthPoints = 20; // make sure the players health can never be above 20
        }
    }

    /**
     * Make sure the enemy is alive.
     * @param alive True sets the enemy is alive.
     */
    public void setAlive(boolean alive){
        // make sure the enemy can be alive'nt
        this.alive = alive; 
    }

    /**
     * @param room Sets the room the enemy is in.
     */
    public void setRoom(Room room){
        currentRoom = room;   
    }

    /**
     * @return Returns the maximum hit of the enemy.
     */
    public int getMaxHit(){
        return maxHit;   
    }

    /**
     * @return Returns the minimum hit of the enemy.
     */
    public int getMinHit(){
        return minHit;   
    }

    /**
     * @return Returns the current room of the enemy.
     */
    public Room getRoom(){
        return currentRoom;   
    }

    /**
     * @return Returns the current health of the enemy.
     */
    public int getHealth() {
        return healthPoints;
    } 

    /**
     * @return Returns the name of the enemy.
     */
    public String getName() {
        return enemyName;
    }

    /**
     * @return Returns the description of the enemy.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Returns if the enemy is alive or not.
     */
    public boolean getAlive() {
        return alive;
    }
}