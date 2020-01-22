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
    private String itemCategory;

    private int itemHealAmount;

    private int itemMinDamage;
    private int itemMaxDamage;
    private int itemArmorRating;

    private int itemWeight;
    private int itemValue;
    private boolean itemPickupAble;

    /**
     * Setting initial values for every item created, just in case
     */
    public Item(String itemname, String itemDescription,String itemCategory,int itemHealAmount,int itemMinDamage,int itemMaxDamage,int itemArmorRating,int itemWeight,int itemValue,boolean itemPickupAble) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;

        this.itemHealAmount = itemHealAmount;

        this.itemMinDamage = itemMinDamage;
        this.itemMaxDamage = itemMaxDamage;
        this.itemArmorRating = itemArmorRating;

        this.itemWeight = itemWeight;
        this.itemValue = itemValue;
        this.itemPickupAble = itemPickupAble;
    }

    /**
     * New items for the game are defined here. NO THEY DONT. BAD KRYEN.
     * 
     * @param item The item to be set. 

    public void setItemVariables(String item) {
    switch(item) {
    case "bread":
    itemName = "bread";
    itemDescription = "A nice loaf of bread. Not warm though. Can be eaten to heal 2 health points.";
    itemCategory = "food";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 0;
    itemHealAmount = 2;
    itemWeight = 1;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "jug_of_water":
    itemName = "jug_of_water";
    itemDescription = "A jug of water, it contains water.";
    itemCategory = "food";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 0;
    itemHealAmount = 1;
    itemWeight = 2;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "steak":
    itemName = "steak";
    itemDescription = "A healthy-sized steak. It looks like Gandhi's flipflop. Can be eaten to heal 5 health points.";
    itemCategory = "food";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 0;
    itemHealAmount = 5;
    itemWeight = 3;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "dagger":
    itemName = "dagger";
    itemDescription = "A sharp weapon, relatively small. Good for stabby jabbies.";
    itemCategory = "weapon";
    itemMinDamage = 3;
    itemMaxDamage = 5;
    itemArmorRating = 0;
    itemHealAmount = 0;
    itemWeight = 2;
    itemValue = 5;
    itemPickupAble = true;
    break;

    case "cloak":
    itemName = "cloak";
    itemDescription = "A cloak. Protects from rain and gives warmth. Not much use in here.";
    itemCategory = "armor";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 1;
    itemHealAmount = 0;
    itemWeight = 3;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "unlit_torch":
    itemName = "unlit_torch";
    itemDescription = "An unlit torch, how useless!";
    itemCategory = "generic";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 0;
    itemHealAmount = 0;
    itemWeight = 5;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "torch":
    itemName = "torch";
    itemDescription = "A flame on a stick, lights up the area around you so mobs don't spawn. Can burn stuff.";
    itemCategory = "keyItem";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 0;
    itemHealAmount = 0;
    itemWeight = 5;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "health_biscuit":
    itemName = "health_biscuit";
    itemDescription = "A life elixer, health potion, red pot or whatever you want to name it, but it is in biscuit form. Can be eaten for 10 health points.";
    itemCategory = "food";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 0;
    itemHealAmount = 0;
    itemWeight = 3;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "shortsword":
    itemName = "shortsword";
    itemDescription = "A shortsword, things will get more 'ouch' with this.";
    itemCategory = "weapon";
    itemMinDamage = 5;
    itemMaxDamage = 9;
    itemArmorRating = 0;
    itemHealAmount = 0;
    itemWeight = 5;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "brass_key":
    itemName = "brass_key";
    itemDescription = "A brass key, probably used for unlocking something. Why is it always brass though?";
    itemCategory = "keyItem";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 0;
    itemHealAmount = 0;
    itemWeight = 5;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "bronze_key":
    itemName = "bronze_key";
    itemDescription = "A bronze key, useful for unlocking things.";
    itemCategory = "keyItem";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 0;
    itemHealAmount = 0;
    itemWeight = 5;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "mysterious_key":
    itemName = "mysterious_key";
    itemDescription = "This key glows a little and you can feel its warmth. Must unlock something powerful.";
    itemCategory = "keyItem";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 0;
    itemHealAmount = 0;
    itemWeight = 5;
    itemValue = 1;
    itemPickupAble = true;
    break;

    case "boulder":
    itemName = "boulder";
    itemDescription = "It is heavy. Way. Too. Heavy.";
    itemCategory = "wtf";
    itemMinDamage = 0;
    itemMaxDamage = 0;
    itemArmorRating = 0;
    itemHealAmount = 0;
    itemWeight = 10000;
    itemValue = 9001;
    itemPickupAble = false;
    break;
    }
    }*/

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
     * @return The category of the item set in setItemVariables()
     */

    public String getItemCategory() {
        return itemCategory;
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
