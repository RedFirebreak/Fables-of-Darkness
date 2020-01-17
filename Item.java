import java.util.*;

/**
 * Write a description of class Items here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item {

    public Item() {
    }   
    
    public String getItemDescription(String itemToGetDescriptionFrom) {
        String itemDescription = "Item not found";
        switch (itemToGetDescriptionFrom) {
            case "bread":
            itemDescription="A nice loaf of bread. Not warm though. Can be eaten";
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
            
            case "torch":
            itemValue=1;
            break;
        }
        return itemValue;
    }
}
