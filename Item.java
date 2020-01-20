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
            itemDescription = "A nice loaf of bread. Not warm though. Can be eaten to heal 2 hp.";
            itemWeight = 1;
            itemValue = 1;
            itemPickupAble = true;
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
