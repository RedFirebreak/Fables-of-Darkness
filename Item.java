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

    public Item(String itemName, String description, int weight, int value) {
        this.itemName = itemName;
        this.itemDescription = description;
        this.itemWeight = weight;
        this.itemValue = value;
    }   
    
    public String getItemName() {
        return itemName;
    }
    
    public String getItemDescription(String itemToGetDescriptionFrom) {
        String itemDescription = "Item not found";
        switch (itemToGetDescriptionFrom) {
            case "bread":
            itemDescription="A nice loaf of bread. Not warm though. Can be eaten to heal 2 hp.";
            break;
            
            case "steak":
            itemDescription="A piece of well-done steak. Can be eaten to heal 5 hp.";
            break;
            
            case "jug_of_water":
            itemDescription="A jug of water. Can this be used for anything?";
            break;
            
            case "torch":
            itemDescription="A healthy fire on a stick, good for light and for burning things.";
            break;
        }
        return itemDescription;
    }
    
    public int getItemWeight(String itemToGetWeightFrom) {
        int itemWeight = 0;
        switch (itemToGetWeightFrom) {
            case "bread":
            itemWeight=1;
            break;
            
            case "steak":
            itemWeight=2;
            break;
            
            case "jug_of_water":
            itemWeight=2;
            break;
            
            case "torch":
            itemWeight=5;
            break;
        }
        return itemWeight;
    }
    
    public int getItemValue(String itemToGetValueFrom) {
        int itemValue = 0;
        switch (itemToGetValueFrom) {
            case "bread":
            itemValue=1;
            break;
            
            case "steak":
            itemValue=1;
            break;
            
            case "jug_of_water":
            itemValue=1;
            break;
            
            case "torch":
            itemValue=1;
            break;
        }
        return itemValue;
    }
}
