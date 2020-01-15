import java.util.*;

/**
 * Write a description of class Items here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item {
    private String name;
    private int weight;
    private int quantity;
    private int value;
    private String description;

    public Item(String name, int value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }
    
    private String item(Command addedItem) {
        String returnStatement = "Item not found.";
        String itemName = addedItem.getSecondWord();
        switch (itemName) {
            case "Bread":
            name="Bread"; weight=3; quantity=1; value=1; description="A nice loaf of bread. Not warm though.";
            returnStatement = "You take the bread and put it in your inventory.";
            break;
            
            case "Torch":
            name="Torch"; weight=5; quantity=1; value=1; description="A healthy fire on a stick, good for light and for burning things.";
            returnStatement = "You take the torch and put it in your inventory.";
            break;
        }
        return returnStatement;
    }
    

}
