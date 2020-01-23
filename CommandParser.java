
import java.util.*;
/**
 * Write a description of class CommandParser here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CommandParser {
    private Parser parser;

    private Levels level;

    private Room currentRoom;
    private Player player;
    private Stack backList;
    private Item items;

    public CommandParser(Player player, Room room, Levels level) {
        this.level = level;
        this.currentRoom = room;
        this.player = player;
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    public void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            System.out.println(currentRoom.getRoomDescription());
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        Room previousRoom = currentRoom;

        HashMap<String, Room> allroomIDs = level.getAllroomIDs();

        if (nextRoom == null) {
            System.out.println("You can't go that way!");
            System.out.println(currentRoom.getRoomDescription());
        } else {
            boolean goToNextRoom = true;
            if (nextRoom.getIsLocked() && nextRoom.getIsTrapRoom()){ // check if the trapdoor was activated before now, and don't go that direction
                System.out.println("This is the corridor where the trapdoor was.");
                System.out.println("You don't want to go in here again so you stay where you are.");
                return;
            }

            if (nextRoom.getIsLocked()){ // if the room is locked, we cannot go there
                System.out.println("This door seems to be locked. It won't open.");
                return;
            }

            if (nextRoom.getIsTrapRoom()) { // check if the room is a trapdoor.
                System.out.println("You fell into a trapdoor!");
                nextRoom.lockRoom();
                player.clearBack(); // clears the entire back command
                currentRoom = nextRoom; // go to the next room
                player.setCurrentRoom(currentRoom); // save the current room in the player class
                player.removeHealth(1);
                System.out.println("You take 1 damage because you hurt your leg after the fall.");
                System.out.println("The hole is too deep so you cannot climb back up if you tried.");
                System.out.println("You see a small crevice you could climb through... You probbly cannot get back here though.");
                System.out.println(currentRoom.getExitString()); // Print out the current description
                return;
            }

            // Check if there is an enemy in the room
            if (nextRoom.hasEnemy()) {
                Battle battle = new Battle(player, nextRoom.getEnemy());
                int result = battle.play();

                // 0 = player won, continue, 1 = player ran! dont go to next room, 2 = player died
                switch (result) {
                    case 0:
                    goToNextRoom = true;
                    nextRoom.setHasEnemy(false); // player defeated the enemy, mark the room as safe for next time the player is here
                    break;

                    case 1:
                    goToNextRoom = false;
                    System.out.println("You ran out of the room with the enemy! The enemy did not follow you.");
                    nextRoom.setHasEnemy(true); // player has not defeated monster
                    break;

                    case 2:
                    goToNextRoom = false; // player died and has 0 hp. Next iteration, the game will end automatically.
                    nextRoom.setHasEnemy(true); // player has not defeated monster
                    player.removeHealth(20); // [FIX] kinda hardcoded.... but kinda not.
                    break;

                }
            }

            if (goToNextRoom) {
                if (previousRoom.getIsTrapRoom()) { // check if the previous room was a trapdoor room, so we dont add it to the backstack
                    currentRoom = nextRoom; // go to the next room
                    player.setCurrentRoom(currentRoom); // save the current room in the player class
                    System.out.println("");
                    System.out.println(currentRoom.getRoomDescription()); // Print out the current description
                    System.out.println("You don't think you can manage to go back to that horrible room.");
                }else{ // normal room movement
                    player.addBack(currentRoom.getRoomID()); // add the previous room to the "back" command.
                    currentRoom = nextRoom; // go to the next room
                    player.setCurrentRoom(currentRoom); // save the current room in the player class
                    System.out.println("");
                    System.out.println(currentRoom.getRoomDescription()); // Print out the current description
                }
            }

        }
    }

    public void goBack() {
        Stack<String> backStack = player.getBack();
        if (backStack.empty()) {
            System.out.println("You cant go back from here!");
        } else {
            HashMap<String, Room> allroomIDs = level.getAllroomIDs(); // get full map from levels
            if (allroomIDs.containsKey(backStack.peek())) { // Check if the latest stack item exists
                Room previousRoom = allroomIDs.get(backStack.peek());// compare the latest in the stack and save that in "previousroom"
                currentRoom = previousRoom; // replace the current room with the previous room we just got
                System.out.println("");
                System.out.println("You went back!"); // inform the user
                System.out.println(currentRoom.getRoomDescription()); // Print out the current description

                player.removeBack();
            } else {
                System.out.println("The previous room couldn't be loaded.");
            }
        }
    }

    public void lookInventory() {
        if(!player.getPlayerInventory().isEmpty()) {
            System.out.println("Your look in your backpack and find the following items: ");
            List<Item> playerInventory = player.getPlayerInventory(); // load the inventory of the room
            for (int loop = 0; loop < playerInventory.size(); loop++) {
                Item currentItem = playerInventory.get(loop);
                System.out.println(currentItem.getItemName() + " ");
            }
        }
        else {
            System.out.println("Your inventory is empty.");
        }
    }

    public void searchRoom() {
        if (currentRoom.doesRoomContainItems()){
            System.out.println("You search the room and find the following items: ");
            ArrayList<Item> currentRoomInventory = currentRoom.getRoomInventory(); // load the inventory of the room
            for (int loop = 0; loop < currentRoomInventory.size(); loop++) {
                Item currentItem = currentRoomInventory.get(loop);
                System.out.println(currentItem.getItemName() + " ");
            }
        }
        else {
            System.out.println("You search around the room but fail to find any items of use.");
        }

    }

    public void getInfo() {
        System.out.println("Your current hp is: " + player.getHealth() + ".");
        System.out.println("Your current carry weight is: " + player.getCarryWeight() + "/" + player.getMaxCarryWeight() + "KGs.");
        System.out.println("Your equipped weapon: " + player.getPlayerWeapon().getItemName() + ". Your equipped armor: " + player.getPlayerArmor().getItemName() + ".");
        System.out.println("You can deal " + player.getMinHit() + " to " + player.getMaxHit() + " damage. Your armor rating is: " + player.getArmorCount() + ".");
    }

    /**
     * "take" was entered. Check if a second word has been send too and check if the item is takeable.
     * If it is takeable, check if the room has the item in their inventory and take the item,
     * adding the item from the inventory and adding the weight, also removing the item from the current room.
     * 
     * @param command The command entered in the Parser.
     */
    public void pickupItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }
        String itemToBeAdded = command.getSecondWord(); // hold the item name we are looking for
        ArrayList<Item> currentRoomInventory = currentRoom.getRoomInventory(); // load the inventory of the room
        // check if the second words matches an itemname in this room
        boolean somethingInThisRoom = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < currentRoomInventory.size(); loop++) {
            Item currentItem = currentRoomInventory.get(loop);
            if (itemToBeAdded.equals(currentItem.getItemName() ) ){ // get the item name, then check if thats something
                // item name matches the player-provided name
                // pick it up! (add to player inv, remove from room)
                if(currentItem.getItemPickupAble()) {
                    player.addItemToInventory(currentItem); // add to player inventory [FIX] WEIGHT 
                    currentRoom.removeRoomInventory(currentItem); // remove from room
                    System.out.println("You take the " + itemToBeAdded + " and put it in your backpack.");
                }
                else {
                    System.out.println("The " + itemToBeAdded + " cannot be picked up as it is way to heavy to be picked up.");
                }

            } else {
                // the item did not match the player provided name
                notThisItem++;
            };
            somethingInThisRoom = true;
        }
        //errors afvangen

        if (!somethingInThisRoom) {
            System.out.println("There is nothing in this room to pick up!");
        }

        if (loop == notThisItem) {
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot take " + itemToBeAdded + " because it does not exist!");
        }
    }

    /**
     * "drop" was entered. Check if a second word has been send too.
     * If there is a second word, check if the player has the item in their inventory and drop the item,
     * removing the item from their inventory and losing the weight, also returning the item to the current room.
     * 
     * @param command The command entered in the Parser.
     */
    public void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }
        String itemToBeDropped = command.getSecondWord(); // hold the item name we are looking for
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the room
        // check if the second words matches an itemname in this room
        boolean somethingToDrop = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < playerInventory.size(); loop++) {
            Item currentItem = playerInventory.get(loop);
            if (itemToBeDropped.equals(currentItem.getItemName()) ){ // get the item name, then check if thats something
                // item name matches the player-provided name
                // pick it up! (add to player inv, remove from room)
                player.removeItemFromInventory(currentItem); // remove from player inventory [FIX] WEIGHT
                currentRoom.setRoomInventory(currentItem); // add to room
                System.out.println("You drop the " + itemToBeDropped + " and put it on the ground.");
            } else {
                // the item did not match the player provided name
                notThisItem++;
            };
            somethingToDrop = true;
        }

        //errors afvangen
        if (!somethingToDrop) {
            System.out.println("You don't have any items!");
        }

        if (loop == notThisItem) {
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot drop " + itemToBeDropped + " because you dont have it in your backpack!");
        }
    }

    /**
     * "inspect" was entered. Check if a second word has been send too.
     * If there is a second word, check if the player has the item in their inventory and return the info.
     * 
     * @param command The command entered in the Parser.
     */
    public void getItemInformation(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to inspect...
            System.out.println("Inspect what?");
            return;
        }
        String itemToBeInspected = command.getSecondWord(); // hold the item name we are looking for
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the player
        // check if the second words matches an itemname in this room
        boolean somethingToInspect = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < playerInventory.size(); loop++) {
            Item currentItem = playerInventory.get(loop);
            if (itemToBeInspected.equals(currentItem.getItemName()) ){ // get the item name, then check if thats something
                System.out.println(itemToBeInspected + "'s description: " + currentItem.getItemDescription());
                System.out.println(itemToBeInspected + "'s weight: " + currentItem.getItemWeight());
                System.out.println(itemToBeInspected + "'s value: " + currentItem.getItemValue());
            } else {
                // the item did not match the player provided name
                notThisItem++;
            }
            somethingToInspect = true;
        }

        //errors afvangen
        if (!somethingToInspect) {
            System.out.println("You can't inspect that!");
        }

        if (loop == notThisItem) {
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot inspect " + itemToBeInspected + " because you don't have it in your inventory!");
        }

    }

    public void useItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to use...
            System.out.println("Use what?");
            return;
        }
        String itemToBeUsed = command.getSecondWord();
        HashMap<String, Room> allroomIDs = level.getAllroomIDs();
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the player

        String unlockItem;
        Room roomToUnlock;
        boolean somethingToUse = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < playerInventory.size(); loop++) {
            Item currentItem = playerInventory.get(loop);
            if (itemToBeUsed.equals(currentItem.getItemName()) ){ // get the item name, then check if thats something
                if (currentRoom.getUnlockRoom()) {

                    unlockItem = currentRoom.getUnlockItem();
                    if (itemToBeUsed.equals(unlockItem)) {
                        System.out.println("You unlock the door. The key is stuck in the door, so you lose it.");
                        player.removeItemFromInventory(currentItem); // remove item from inventory [FIX] itemweight
                        roomToUnlock = allroomIDs.get(currentRoom.getUnlocksRoomID()); 
                        roomToUnlock.unlockRoom();
                    }
                    else {
                        System.out.println("This is not the place to use the key!");
                    }
                }
                else {
                    System.out.println("You cannot use this item here.");
                }

            } else {
                // the item did not match the player provided name
                notThisItem++;
            }
            somethingToUse = true;
        }

        //errors afvangen
        if (!somethingToUse) {
            System.out.println("You can't inspect that!");
        }

        if (loop == notThisItem) {
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot use " + itemToBeUsed + " because you don't have it in your inventory!");
        }

    }

    public void burnItem(Command command) { //[FIX] HARDCODED
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to burn...
            System.out.println("Burn what?");
            return;
        }
        String whatToBurn = command.getSecondWord();
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the player
        String currentRoomid = currentRoom.getRoomID();
        HashMap<String, Room> allroomIDs = level.getAllroomIDs();

        Room roomToUnlock;
        String unlockItem;
        boolean somethingToUse = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < playerInventory.size(); loop++) {
            Item currentItem = playerInventory.get(loop);
            String tempname = currentItem.getItemName();
            if (tempname.equals("torch")) { // check if item is in your inventory
                if (currentRoom.getCanBeBurned()) {
                    if (whatToBurn.equals("door")) {
                        System.out.println("You burn the door with your torch, the way is now free!");
                        roomToUnlock = allroomIDs.get("14"); //14 is c1
                        roomToUnlock.unlockRoom();
                        currentRoom.setCanBeBurned(false);
                    } else {
                        System.out.println("You cannot burn this.");
                    }
                } else{ 
                    System.out.println("You can't burn " + whatToBurn + " in this room.");
                }
            } else {
                notThisItem++;
            }
            // the item did not match the player provided name

            somethingToUse = true;
        }

        //errors afvangen
        if (!somethingToUse) {
            System.out.println("You can't burn");
        }

        if (loop == notThisItem) {
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot burn " + whatToBurn + " because you don't have it in your inventory!");
        }
    }

    /**
     * "eat" was entered. Check if a second word has been send too and check if the item is eatable.
     * If it is eatable, heal the player if their hp isn't full, and remove the item and its weight from the player.
     * 
     * @param command The command entered in the Parser.
     */
    public void eatItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to eat...
            System.out.println("Eat what?");
            return;
        }
        String itemToBeEaten = command.getSecondWord();
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the player

        boolean somethingToUse = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < playerInventory.size(); loop++) {
            Item currentItem = playerInventory.get(loop);
            if (itemToBeEaten.equals(currentItem.getItemName()) ){ // get the item name, then check if thats something
                if (currentItem.getItemCategory().equals("food")){
                    if((player.getHealth())<(20)) {
                        player.addHealth(currentItem.getHealAmount());
                        player.removeItemFromInventory(currentItem); // remove item from inventory
                        System.out.println("You eat the " + itemToBeEaten + ". It heals for " + currentItem.getHealAmount()+".");
                    } else {
                        System.out.println("Your HP is full!");
                    }
                } else {
                    System.out.println("You can't eat that item!");
                }

            } else {
                // the item did not match the player provided name
                notThisItem++;
            }
            somethingToUse = true;
        }

        //errors afvangen
        if (!somethingToUse) {
            System.out.println("You can't eat that!");
        }

        if (loop == notThisItem) {
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot eat " + itemToBeEaten + " because you don't have it in your inventory!");
        }
    }

    public void equipItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to equip...
            System.out.println("Equip what?");
            return;
        }
        String itemToBeEquipped = command.getSecondWord();
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the player

        Item currentlyEquippedArmor = player.getPlayerArmor(); // get the current armor
        Item currentlyEquippedWeapon = player.getPlayerWeapon(); // get the current weapon

        boolean somethingToUse = false;
        int notThisItem = 0;
        int loop;
        for (loop = 0; loop < playerInventory.size(); loop++) {
            Item currentItem = playerInventory.get(loop);
            if (itemToBeEquipped.equals(currentItem.getItemName()) ){ // get the item name, then check if thats something
                if (currentItem.getItemCategory().equals("armor") || currentItem.getItemCategory().equals("weapon")) {
                    if (currentItem.getItemCategory().equals("armor")){ // check if the item is armor
                        if (!currentlyEquippedArmor.getItemName().equals("naked")) {
                            player.addItemToInventory(currentlyEquippedArmor); // Place currently equipped armor in inventory
                        }
                        currentlyEquippedArmor = currentItem; // Equip new armor
                        player.setArmor(currentlyEquippedArmor);
                        player.setArmorCount(currentlyEquippedArmor.getItemArmorRating()); // Update armor
                        player.removeItemFromInventory(currentlyEquippedArmor);
                        System.out.println("You equip the " + currentlyEquippedArmor.getItemName() + ". Your armor value is now: " + player.getArmorCount()); // inform user
                    }
                    if (currentItem.getItemCategory().equals("weapon")){ // check if the item is a weapon
                        if (!currentlyEquippedWeapon.getItemName().equals("unarmed")) {
                            player.addItemToInventory(currentlyEquippedWeapon);
                        }
                        currentlyEquippedWeapon = currentItem; // Equip new weapon
                        player.setWeapon(currentlyEquippedWeapon);
                        player.setMinHit(currentlyEquippedWeapon.getItemMinDamage()); // Update min damage
                        player.setMaxHit(currentlyEquippedWeapon.getItemMaxDamage()); // Update max damage
                        player.removeItemFromInventory(currentlyEquippedWeapon);
                        System.out.println("You equip the " + currentlyEquippedWeapon.getItemName() + ".\n"); // inform user
                        System.out.println("Your min damage is: " + player.getMinHit() + " and your max damage is: " + player.getMaxHit() + ".");
                    }
                } else {
                    // the item did not match the player provided name
                    notThisItem++;
                }
                somethingToUse = true;
            }
        }

        //errors afvangen
        if (!somethingToUse) {
            System.out.println("You can't equip that!");
        }

        if (loop == notThisItem) {
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot equip " + itemToBeEquipped + " because you don't have it in your inventory!");
        }
    }

    public void unequipItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to unequip...
            System.out.println("Unequip what?");
            return;
        }
        String itemToBeUnequipped = command.getSecondWord();
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the player
        Item currentlyEquippedArmor = player.getPlayerArmor(); // get the current armor
        Item currentlyEquippedWeapon = player.getPlayerWeapon(); // get the current weapon
        Item unarmed = new Item("unarmed","Nothing in your hands equipped.","weapon",1,3,0,0,0,0,false);
        Item naked = new Item("naked","Nothing is on your body.","armor",0,0,0,0,0,0,false);

        if (itemToBeUnequipped.equals(currentlyEquippedArmor.getItemName()) || itemToBeUnequipped.equals(currentlyEquippedWeapon.getItemName())) {
            if (itemToBeUnequipped.equals(currentlyEquippedArmor.getItemName()) ){ // check if the item is the currently equipped armor
                player.addItemToInventory(currentlyEquippedArmor);
                currentlyEquippedArmor = naked;
                player.setArmor(naked);
                player.setArmorCount(currentlyEquippedArmor.getItemArmorRating()); // Update armor
                System.out.println("You unequip the " + itemToBeUnequipped + ".");
                System.out.println("Your armor rating is now: " + player.getArmorCount() + ".");
            }
            if (itemToBeUnequipped.equals(currentlyEquippedWeapon.getItemName()) ){ // check if the item is the currently equipped weapon
                player.addItemToInventory(currentlyEquippedWeapon);
                currentlyEquippedWeapon = unarmed;
                player.setWeapon(unarmed);
                player.setMinHit(currentlyEquippedWeapon.getItemMinDamage()); // Update min damage
                player.setMaxHit(currentlyEquippedWeapon.getItemMaxDamage()); // Update max damage
                System.out.println("You unequip the " + itemToBeUnequipped + ".");
                System.out.println("Your min damage is now: " + player.getMinHit() + " and your max damage is now: " + player.getMaxHit() + ".");
            }
        }else {
            System.out.println("You can't unequip that item because you are not wearing it!");
        }
    }
}