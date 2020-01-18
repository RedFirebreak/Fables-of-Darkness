
/**
 * Write a description of class enemies here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Enemy
{    
    private String enemyName; // the enemy name
    private int maxHealth; // the max health the enemy can have.
    private int healthPoints; // the amount of health the enemy has.
    private String description; // the description op the enemy
    private boolean alive = true; // Make sure the enemy is alive when created
    
    private int maxHit;
    private int minHit;
    
    private Room currentRoom;

    /**
     * Set the enemy's name, health and description based on input
     */
    public Enemy(String name, int health, String description, int maxHit, int minHit) {
        maxHealth = health; // set the max health
        healthPoints = health; // set the current health (to max)
        enemyName = name; // set the health
        this.description = description; // set the description
        this.maxHit = maxHit;
        this.minHit = minHit;
    }

    public void removeHealth(int amount) {
        healthPoints = healthPoints - amount;
        if (healthPoints < 0){
            healthPoints = 0; // make sure the enemy health can never be below 0
            alive = false;
        }
    }

    public void addHealth(int amount) {
        healthPoints = healthPoints + amount;
        if (healthPoints > 20){
            healthPoints = 20; // make sure the players health can never be above 20
        }
    }
    
    public void setRoom(Room room){
     currentRoom = room;   
    }
    
    public Room getRoom(){
     return currentRoom;   
    }

    public int getHealth() {
        return healthPoints;
    } 

    public String getName() {
        return enemyName;
    }

    public String getDescription() {
        return description;
    }
    
    public boolean getAlive() {
        return alive;
    }
}
