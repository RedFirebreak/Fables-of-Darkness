import java.util.*;

/**
 * Write a description of class Items here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item {
    private String itemName;
    private String itemDescription;
    private int itemWeight;
    private int itemValue;
    private boolean itemPickupAble;

    /**
     * Setting initial values for every item created, just in case
     */
    public Item() {
        itemName = "error in itemName";
        itemDescription = "error in itemDescription";
        itemWeight = 0;
        itemValue = 0;
        itemPickupAble = false;
    }

    /**
     * New items for the game are defined here.
     * 
     * @param item The item to be set. 
     */
    public void setItemVariables(String item) {
        switch(item) {
            case "bread":
            itemName = "bread";
            itemDescription = "A nice loaf of bread. Not warm though. Can be eaten to heal 2 health points.";
            itemWeight = 1;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "jug_of_water":
            itemName = "jug_of_water";
            itemDescription = "A jug of water, it contains water.";
            itemWeight = 2;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "steak":
            itemName = "steak";
            itemDescription = "A healthy-sized steak. It looks like Gandhi's flipflop. Can be eaten to heal 5 health points.";
            itemWeight = 3;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "dagger":
            itemName = "dagger";
            itemDescription = "A sharp weapon, relatively small. Good for stabby jabbies.";
            itemWeight = 2;
            itemValue = 5;
            itemPickupAble = true;
            break;

            case "cloak":
            itemName = "cloak";
            itemDescription = "A cloak. Protects from rain and gives warmth. Not much use in here.";
            itemWeight = 3;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "unlit_torch":
            itemName = "unlit_torch";
            itemDescription = "An unlit torch, how useless!";
            itemWeight = 5;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "torch":
            itemName = "torch";
            itemDescription = "A flame on a stick, lights up the area around you so mobs don't spawn. Can burn stuff.";
            itemWeight = 5;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "health_biscuit":
            itemName = "health_biscuit";
            itemDescription = "A life elixer, health potion, red pot or whatever you want to name it, but it is in biscuit form. Can be eaten for 10 health points.";
            itemWeight = 3;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "shortsword":
            itemName = "shortsword";
            itemDescription = "A shortsword, things will get hurt with this.";
            itemWeight = 5;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "brass_key":
            itemName = "brass_key";
            itemDescription = "A brass key, probably used for unlocking something. Why is it always brass though?";
            itemWeight = 5;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "bronze_key":
            itemName = "bronze_key";
            itemDescription = "A bronze key, useful for unlocking things.";
            itemWeight = 5;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "mysterious_key":
            itemName = "mysterious_key";
            itemDescription = "This key glows a little and you can feel its warmth. Must unlock something powerful.";
            itemWeight = 5;
            itemValue = 1;
            itemPickupAble = true;
            break;

            case "boulder":
            itemName = "boulder";
            itemDescription = "It is heavy. Way. Too. Heavy.";
            itemWeight = 10000;
            itemValue = 9001;
            itemPickupAble = false;
            break;
        }
    }

    /**
     * @return The name of the item set in setItemVariables()
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @return The description of the item set in setItemVariables()
     */

    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * @return The weight of the item set in setItemVariables()
     */

    public int getItemWeight() {
        return itemWeight;
    }

    /**
     * @return The value of the item set in setItemVariables()
     */

    public int getItemValue() {
        return itemValue;
    }

    /**
     * @return The boolean if the item is pickup able or not of set in setItemVariables()
     */

    public boolean getItemPickupAble() {
        return itemPickupAble;
    }
}
