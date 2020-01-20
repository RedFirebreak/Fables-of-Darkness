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

    public Item() {
        itemName = "error in itemName";
        itemDescription = "error in itemDescription";
        itemWeight = 0;
        itemValue = 0;
        itemPickupAble = false;
    }
    
    public void setItemVariables (String item) {
        switch(item) {
            case "bread":
            //itemName = setItemName("bread");
            //itemDescription = setItemDescription("A nice loaf of bread. Not warm though. Can be eaten to heal 2 hp.");
            //itemWeight = setItemWeight(1);
            //itemValue = setItemValue(1);
            itemPickupAble = setItemPickupable(true);
        }
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public String getItemDescription() {
        return itemDescription;
    }
    
    public int getItemWeight() {
        return itemWeight;
    }
    
    public int getItemValue() {
        return itemValue;
    }
    
    public boolean getItemPickupAble() {
        return itemPickupAble;
    }
    
    public void setItemName(String name) {
        itemName = name;
    }
    
    public void setItemDescription(String description) {
        itemDescription = description;
    }
    
    public void setItemWeight(int weight) {
        itemWeight = weight;
    }
    
    public void setItemValue(int value) {
        itemValue = value ;
    }
    
    public void setItemPickupAble(boolean pickupAble) {
        itemPickupAble = pickupAble;
    }
    
    
}
