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
    public Item(String itemName, String itemDescription,String itemCategory,int itemMinDamage,int itemMaxDamage,int itemArmorRating,int itemHealAmount,int itemWeight,int itemValue,boolean itemPickupAble) {
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
     * @return The name of the item set in setItemVariables()
     */
    public String getItemName() {
        return itemName;
    }
    
    /**
     * @return The heal amount of the item set in setItemVariables()
     */
    public int getHealAmount() {
        return itemHealAmount;
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
     * @return The armorrating of the item set in setItemVariables()
     */

    public int getItemArmorRating() {
        return itemArmorRating;
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
