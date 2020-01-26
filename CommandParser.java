import java.util.Stack;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * CommandParser - parses the commands from parser and game
 * 
 * Commands entered in parser are checked in Game, then the commands are processed here.
 *
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24.01.2020
 */

public class CommandParser {
    private Parser parser;

    private Levels level;

    private Room currentRoom;
    private Player player;
    private Stack backList;
    private Item items;

    /**
     * Create the commandparser, it needs to know the current room, level and the player.
     * 
     * @param player The current player state
     * @param room   The current room
     * @param level  The level the player is playing
     */
    public CommandParser(Player player, Room room, Levels level) {
        this.level = level;
        this.currentRoom = room;
        this.player = player;
    }

    /**
     * "go" was entered. Check if a second word has been sent too.
     * Go in the direction the player has entered in secondWord.
     * 
     * This command also checks for locked rooms and trap rooms.
     * If the next room has an enemy, enter into battle.
     * 
     * @param command The command entered in the Parser.
     */
    public void goRoom(Command command) {
        if (!command.hasSecondWord()) { // check if there's a second word
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction); // get the room that is in the direction of SecondWord
        Room previousRoom = currentRoom; // set the current room as the previous room
        HashMap<String, Room> allroomIDs = level.getAllroomIDs(); // load all rooms in the current level
        boolean goToNextRoom = true;

        if (nextRoom == null) { // if there is no room in secondWord's direction
            System.out.println("You can't go that way!");
            return;
        } 

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
            nextRoom.lockRoom(); // lock the trapdoor, so you don't go there again
            player.clearBack(); // clears the entire back command
            currentRoom = nextRoom; // go to the next room
            player.setCurrentRoom(currentRoom); // save the current room in the player class
            player.removeHealth(1); // damage the player by 1, for falling
            System.out.println("You take 1 damage because you hurt your leg after the fall.");
            System.out.println("The hole is too deep so you cannot climb back up if you tried.");
            System.out.println("You see a small crevice you could climb through... You probably cannot get back here though.");
            System.out.println(currentRoom.getExitString()); // print out the current description
            return;
        }

        // Check if there is an enemy in the room
        if (nextRoom.hasEnemy()) {
            Battle battle = new Battle(player, nextRoom.getEnemy()); // start a new battle
            int result = battle.play();

            switch (result) { // get the result of the battle. 0 = player won, continue. 1 = player ran! dont go to next room. 2 = player died, end game
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
                goToNextRoom = false; // player died and has 0 health. Next iteration, the game will end automatically.
                nextRoom.setHasEnemy(true); // player has not defeated monster
                player.removeHealth(20); // player died, remove the health
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
                System.out.println(currentRoom.getlongDescription()); // Print out the current long description
            }else{ // normal room movement
                player.addBack(currentRoom.getRoomID()); // add the previous room to the "back" command.
                currentRoom = nextRoom; // go to the next room
                player.setCurrentRoom(currentRoom); // save the current room in the player class
                System.out.println("");
                System.out.println(currentRoom.getRoomDescription()); // Print out the current description
            }
        }
    }

    /**
     * "back" was entered. Check if the backstack is not empty. 
     * If not empty, place the player back in the previous room.
     */
    public void goBack() {
        Stack<String> backStack = player.getBack();
        if (backStack.empty()) {
            System.out.println("You cant go back from here!");
        } else {
            HashMap<String, Room> allroomIDs = level.getAllroomIDs(); // get full map of all rooms from levels
            if (allroomIDs.containsKey(backStack.peek())) { // check if the latest stack item exists
                Room previousRoom = allroomIDs.get(backStack.peek());// compare the latest room in the stack and save that in "previousRoom"
                currentRoom = previousRoom; // move the player to the previous room
                System.out.println("");
                System.out.println("You went back!"); // inform the user
                System.out.println(currentRoom.getRoomDescription()); // print out the current room description

                player.removeBack(); // remove the new currentRoom from the backStack
            } else { // if the stack is previously cleared
                System.out.println("The previous room couldn't be loaded.");
            }
        }
    }

    /**
     * "inv" was entered. Check if the player inventory is not empty. 
     * If not empty, print the player inventory on screen.
     */
    public void lookInventory() {
        if(!player.getPlayerInventory().isEmpty()) { // check if the player inventory is filled
            System.out.println("Your look in your backpack and find the following items: ");
            List<Item> playerInventory = player.getPlayerInventory(); // load the inventory of the player
            for (int loop = 0; loop < playerInventory.size(); loop++) { // loop through the player inventory to print them on screen
                Item currentItem = playerInventory.get(loop); // set currentitem on the item that is currently in the loop
                System.out.println(currentItem.getItemName() + " ");
            }
        } else { // else the player inventory is empty
            System.out.println("Your inventory is empty.");
        }
    }

    /**
     * "search" was entered. Check if the room inventory is not empty.  
     * If not empty, print the room inventory on screen.
     */
    public void searchRoom() {
        if (currentRoom.doesRoomContainItems()){ // check if the room inventory is filled
            System.out.println("You search the room and find the following items: ");
            ArrayList<Item> currentRoomInventory = currentRoom.getRoomInventory(); // load the inventory of the room
            for (int loop = 0; loop < currentRoomInventory.size(); loop++) { // loop through the room inventory to print them on screen.
                Item currentItem = currentRoomInventory.get(loop); // set currentitem on the item that is currently in the loop
                System.out.println(currentItem.getItemName() + " ");
            }
        } else { // else the room inventory is empty
            System.out.println("You search around the room but fail to find any items of use.");
        }
    }

    /**
     * "info" was entered. 
     * Print the player's current info
     */
    public void getInfo() {
        System.out.println("Your current health is: " + player.getHealth() + ".");
        System.out.println("Your current carry weight is: " + player.getCarryWeight() + "/" + player.getMaxCarryWeight() + "KGs.");
        System.out.println("Your equipped weapon: " + player.getPlayerWeapon().getItemName() + ". Your equipped armor: " + player.getPlayerArmor().getItemName() + ".");
        System.out.println("You can deal " + player.getMinHit() + " to " + player.getMaxHit() + " damage. Your armor rating is: " + player.getArmorCount() + ".");
    }

    /**
     * "take" was entered. Check if a second word has been sent too and check if the item is takeable.
     * If it is takeable, check if the room has the item in their inventory and take the item,
     * adding the item from the inventory and adding the weight, also removing the item from the current room.
     * 
     * @param command The command entered in the Parser.
     */
    public void pickupItem(Command command) {
        if (!command.hasSecondWord()) { // check if there's a second word
            System.out.println("Take what?");
            return;
        }
        String itemToBeAdded = command.getSecondWord(); // hold the item name we are looking for
        ArrayList<Item> currentRoomInventory = currentRoom.getRoomInventory(); // load the inventory of the room
        // check if the second words matches an itemname in this room
        boolean somethingInThisRoom = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < currentRoomInventory.size(); loop++) { // loop through the room inventory
            Item currentItem = currentRoomInventory.get(loop); // set currentitem on the item that is currently in the loop
            if (itemToBeAdded.equals(currentItem.getItemName() ) ){ // get the item name, then check if that matches the secondWord
                // item name matches the player-provided name
                if (player.addItemToInventory(currentItem)) { // check to see if you can add the item to the inventort and do so if you can.
                    currentRoom.removeRoomInventory(currentItem); // remove from room
                    System.out.println("You take the " + itemToBeAdded + " and put it in your backpack.");
                }
                else { // item is too heavy
                    System.out.println("The " + itemToBeAdded + " cannot be picked up as it is way to heavy to be picked up.");
                }
            } else { // the item did not match the player provided name
                notThisItem++;
            }
            somethingInThisRoom = true;
        }
        //errors afvangen

        if (!somethingInThisRoom) { // roominventory is empty
            System.out.println("There is nothing in this room to pick up!");
        }

        if (loop == notThisItem) { // secondWord isn't in roominventory
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot take " + itemToBeAdded + " because it does not exist!");
        }
    }

    /**
     * "drop" was entered. Check if a second word has been sent too.
     * If there is a second word, check if the player has the item in their inventory and drop the item,
     * removing the item from their inventory and losing the weight, also returning the item to the current room.
     * 
     * @param command The command entered in the Parser.
     */
    public void dropItem(Command command) {
        if (!command.hasSecondWord()) { // check if there's a second word
            System.out.println("Drop what?");
            return;
        }
        String itemToBeDropped = command.getSecondWord(); // hold the item name we are looking for
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the room
        // check if the second words matches an itemname in this room
        boolean somethingToDrop = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < playerInventory.size(); loop++) { // loop through the player inventory
            Item currentItem = playerInventory.get(loop); // set currentitem on the item that is currently in the loop
            if (itemToBeDropped.equals(currentItem.getItemName()) ){ // get the item name, then check if that matches the secondWord
                // item name matches the player-provided name
                player.removeItemFromInventory(currentItem); // remove from player inventory
                currentRoom.setRoomInventory(currentItem); // add to room
                System.out.println("You drop the " + itemToBeDropped + " and put it on the ground.");
            } else { // the item did not match the player provided name
                notThisItem++;
            }
            somethingToDrop = true;
        }

        //errors afvangen
        if (!somethingToDrop) { // playerinventory is empty
            System.out.println("You don't have any items!");
        }

        if (loop == notThisItem) { // secondWord isn't in playerinventory
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot drop " + itemToBeDropped + " because you dont have it in your backpack!");
        }
    }

    /**
     * "inspect" was entered. Check if a second word has been sent too.
     * If there is a second word, check if the player has the item in their inventory and return the info.
     * If the item is a weapon or armor, print trh damag or armor rating, respectively.
     * 
     * @param command The command entered in the Parser.
     */
    public void getItemInformation(Command command) {
        if (!command.hasSecondWord()) { // check if there's a second word
            System.out.println("Inspect what?");
            return;
        }
        String itemToBeInspected = command.getSecondWord(); // hold the item name we are looking for
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the player
        // check if the second words matches an itemname in this room
        boolean somethingToInspect = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < playerInventory.size(); loop++) {  // loop through the player inventory
            Item currentItem = playerInventory.get(loop); // set currentitem on the item that is currently in the loop
            if (itemToBeInspected.equals(currentItem.getItemName()) ){ // get the item name, then check if that matches the secondWord
                System.out.println(itemToBeInspected + "'s description: " + currentItem.getItemDescription());
                System.out.println(itemToBeInspected + "'s weight: " + currentItem.getItemWeight());
                System.out.println(itemToBeInspected + "'s value: " + currentItem.getItemValue());
                if(currentItem.getItemCategory().equals("weapon")) { // if the item is a weapon, tell the damage
                    System.out.println(itemToBeInspected + "'s min damage is: " + currentItem.getItemMinDamage() + " and " + itemToBeInspected + "'s max damage is: " + currentItem.getItemMinDamage() + ".");
                }
                if(currentItem.getItemCategory().equals("armor")) { // if the item is armor, tell the armor rating
                    System.out.println(itemToBeInspected + "'s armor value is: " + currentItem.getItemArmorRating() + ".");
                }
            } else { // the item did not match the player provided name
                notThisItem++;
            }
            somethingToInspect = true;
        }

        //errors afvangen
        if (!somethingToInspect) { // the item is not found in the player inventory
            System.out.println("You can't inspect that!");
        }

        if (loop == notThisItem) { // the secondWord was not found in the player inventory
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot inspect " + itemToBeInspected + " because you don't have it in your inventory!");
        }
    }

    /**
     * "use" was entered. Check if a second word has been sent too.
     * If there is a second word, check if the player has the item in it's inventory.
     * Then check if the item can be used (must be a key).
     * Unlock the locked room if it is the correct key and if the player in the right room to use it.
     * 
     * @param command The command entered in the Parser.
     */
    public void useItem(Command command) {
        if (!command.hasSecondWord()) { // check if there's a second word
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

        for (loop = 0; loop < playerInventory.size(); loop++) { // loop through the player inventory
            Item currentItem = playerInventory.get(loop); // set currentitem on the item that is currently in the loop
            if (itemToBeUsed.equals(currentItem.getItemName()) ){ // get the item name, then check if that matches the secondWord
                if (currentRoom.getUnlockRoom()) { // if you are in the room to unlock something
                    unlockItem = currentRoom.getUnlockItem(); // get the right key for this specific room
                    if (itemToBeUsed.equals(unlockItem)) { // if your item is the right key
                        System.out.println("You unlock the door. The key is stuck in the doorlock, but that's fine.");
                        player.removeItemFromInventory(currentItem); // remove item from inventory
                        roomToUnlock = allroomIDs.get(currentRoom.getUnlocksRoomID()); // get the room to unlock
                        roomToUnlock.unlockRoom(); // unlock the room
                    } else { // the player does not use the right item
                        System.out.println("This is not the place to use this key!");
                    }
                } else { // the player is in the wrong room
                    System.out.println("You cannot use this item here.");
                }
            } else { // the item did not match the player provided name
                notThisItem++;
            }
            somethingToUse = true;
        }

        //errors afvangen
        if (!somethingToUse) { // if the item is not found in the player inventory
            System.out.println("You can't use that!");
        }

        if (loop == notThisItem) { // if the second word was not found in the player inventory
            System.out.println("You cannot use " + itemToBeUsed + " because you don't have it in your inventory!");
        }
    }

    /**
     * "burn" was entered. Check if a second word has been sent too.
     * If there is a second word, check if the player has an item to burn something with in it's inventory.
     * Then check if the item or object can be burned.
     * Burn the item or object if the player has something to burn with and has the other conditions met.
     * 
     * @param command The command entered in the Parser.
     */
    public void burnItem(Command command) {
        if (!command.hasSecondWord()) { // check if there's a second word
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

        for (loop = 0; loop < playerInventory.size(); loop++) { // loop through the player inventory
            Item currentItem = playerInventory.get(loop); // set currentitem on the item that is currently in the loop
            if (currentItem.getCanItemBurn()) { // checks if the player inventory contains an item to burn with
                if (currentRoom.getCanBeBurned()) { // checks if there is something to burn in the current room
                    if (whatToBurn.equals("door")) {
                        System.out.println("You burn the door with your torch, the way is now free!");
                        roomToUnlock = allroomIDs.get(currentRoom.getUnlocksRoomID()); // get the room to unlock
                        roomToUnlock.unlockRoom(); // unlock the room
                        currentRoom.setCanBeBurned(false); // you cannot use burn in this room again
                    } else { // secondWord can not be burned
                        System.out.println("You cannot burn this.");
                    }
                } else{ // if payer is in the right room, but types the wrong word
                    System.out.println("You can't burn " + whatToBurn + " in this room.");
                }
            } else { // the item did not match the player provided name
                notThisItem++;
            }
            somethingToUse = true;
        }

        //errors afvangen
        if (!somethingToUse) { // the item is not found in the player inventory
            System.out.println("You can't burn that!");
        }

        if (loop == notThisItem) { // the player has nothing to burn secondWord with
            System.out.println("You cannot burn " + whatToBurn + " because you don't have anything to burn it with!");
        }
    }

    /**
     * "eat" was entered. Check if a second word has been sent too and check if the item is food.
     * If it is a food item, heal the player if their health isn't full, and remove the item and its weight from the player inventory.
     * 
     * @param command The command entered in the Parser.
     */
    public void eatItem(Command command) {
        if (!command.hasSecondWord()) { // check if there's a second word
            System.out.println("Eat what?");
            return;
        }
        String itemToBeEaten = command.getSecondWord();
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the player

        boolean somethingToUse = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < playerInventory.size(); loop++) { // loop through the player inventory
            Item currentItem = playerInventory.get(loop); // set currentitem on the item that is currently in the loop
            if (itemToBeEaten.equals(currentItem.getItemName()) ){ // get the item name, then check if that matches the secondWord
                if (currentItem.getItemCategory().equals("food")){ // check if the item used is an item in the "food" category
                    if((player.getHealth())<(20)) { // check if the player's health is full
                        player.addHealth(currentItem.getHealAmount()); // heal the player
                        player.removeItemFromInventory(currentItem); // remove item from inventory
                        System.out.println("You eat the " + itemToBeEaten + ". It heals for " + currentItem.getHealAmount()+".");
                        System.out.println("Your health is now: " + player.getHealth());
                    } else { // the player's health is full
                        System.out.println("Your are at full health!");
                    }
                } else { // item is not a food item
                    System.out.println("You can't eat that item!");
                }
            } else { // the item did not match the player provided name
                notThisItem++;
            }
            somethingToUse = true;
        }

        //errors afvangen
        if (!somethingToUse) { // the item is not found in the player inventory
            System.out.println("You can't eat that!");
        }

        if (loop == notThisItem) { // the player has nothing to burn secondWord with
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot eat " + itemToBeEaten + " because you don't have it in your inventory!");
        }
    }

    /**
     * "equip" was entered. Check if a second word has been sent too and check if the item is a weapon or armor.
     * If it is a weapon or armor, equip it and remove the currently equipped item in that slot.
     * If player was naked or unarmed, don't add that item to inventory.
     * 
     * @param command The command entered in the Parser.
     */
    public void equipItem(Command command) {
        if (!command.hasSecondWord()) { // check if there's a second word
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
        for (loop = 0; loop < playerInventory.size(); loop++) { // loop through the player inventory
            Item currentItem = playerInventory.get(loop); // set currentitem on the item that is currently in the loop
            if (itemToBeEquipped.equals(currentItem.getItemName()) ){ // get the item name, then check if that matches the secondWord
                if (currentItem.getItemCategory().equals("armor") || currentItem.getItemCategory().equals("weapon")) { // the item is in category weapon or armor
                    if (currentItem.getItemCategory().equals("armor")){ // check if the item is armor
                        if (!currentlyEquippedArmor.getItemName().equals("naked")) { // check if the current equipped armor is not "naked"
                            player.addItemToInventoryFromEquip(currentlyEquippedArmor); // place currently equipped armor in inventory, except for naked
                            System.out.println("You put the " + currentlyEquippedArmor + " back in your inventory.");
                        }
                        currentlyEquippedArmor = currentItem;
                        player.setArmor(currentlyEquippedArmor); // equip new armor
                        player.setArmorCount(currentlyEquippedArmor.getItemArmorRating()); // update armorCount
                        player.removeItemFromInventory(currentlyEquippedArmor); // remove selected item form inventory
                        System.out.println("You equip the " + currentlyEquippedArmor.getItemName() + ". \nYour armor value is now: " + player.getArmorCount());
                    }
                    if (currentItem.getItemCategory().equals("weapon")){ // check if the item is a weapon
                        if (!currentlyEquippedWeapon.getItemName().equals("unarmed")) { // check if the current equipped weapon is not "unarmed"
                            player.addItemToInventoryFromEquip(currentlyEquippedWeapon); // place currently equipped armor in inventory, except for unarmed
                            System.out.println("You put the " + currentlyEquippedWeapon + " back in your inventory.");
                        }
                        currentlyEquippedWeapon = currentItem;
                        player.setWeapon(currentlyEquippedWeapon); // equip new weapon
                        player.setMinHit(currentlyEquippedWeapon.getItemMinDamage()); // update min damage
                        player.setMaxHit(currentlyEquippedWeapon.getItemMaxDamage()); // update max damage
                        player.removeItemFromInventory(currentlyEquippedWeapon);
                        System.out.println("You equip the " + currentlyEquippedWeapon.getItemName() + ".\n");
                        System.out.println("Your min damage is: " + player.getMinHit() + " and your max damage is: " + player.getMaxHit() + ".");
                    }
                } else { // item is not equippable
                    System.out.println("You can't equip that item!");
                }
            }else { // the item did not match the player provided name
                notThisItem++;
            }
            somethingToUse = true;
        }

        //errors afvangen
        if (!somethingToUse) { // the item is not found in the player inventory
            System.out.println("You can't equip that!");
        }

        if (loop == notThisItem) { // the player has nothing to burn secondWord with
            System.out.println("You cannot equip " + itemToBeEquipped + " because you don't have it in your inventory!");
        }
    }

    /**
     * "unequip" was entered. Check if a second word has been sent too and check if the item is equipped.
     * If the item is equipped, unequip the item and replace the armor or weapon slot with "naked" or "unarmed" respectively.
     * 
     * @param command The command entered in the Parser.
     */
    public void unequipItem(Command command) {
        if (!command.hasSecondWord()) { // check if there's a second word
            System.out.println("Unequip what?");
            return;
        }
        String itemToBeUnequipped = command.getSecondWord();
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the player
        Item currentlyEquippedArmor = player.getPlayerArmor(); // get the current armor
        Item currentlyEquippedWeapon = player.getPlayerWeapon(); // get the current weapon
        Item unarmed = new Item("unarmed","Nothing in your hands equipped.","weapon",1,3,0,0,0,0,false,false); // set "unarmed" item, for weapon unequipping
        Item naked = new Item("naked","Nothing is on your body.","armor",0,0,0,0,0,0,false,false); // set "naked" item, for armor unequipping

        if (itemToBeUnequipped.equals(currentlyEquippedArmor.getItemName()) || itemToBeUnequipped.equals(currentlyEquippedWeapon.getItemName())) { // check if the item is equipped
            if (itemToBeUnequipped.equals(currentlyEquippedArmor.getItemName()) ){ // check if the item is the currently equipped armor
                player.addItemToInventory(currentlyEquippedArmor); // add the armor to the player inventory
                currentlyEquippedArmor = naked;
                player.setArmor(naked); // set current armor to naked
                player.setArmorCount(currentlyEquippedArmor.getItemArmorRating()); // update armor
                System.out.println("You unequip the " + itemToBeUnequipped + ".");
                System.out.println("Your armor rating is now: " + player.getArmorCount() + ".");
            }
            if (itemToBeUnequipped.equals(currentlyEquippedWeapon.getItemName()) ){ // check if the item is the currently equipped weapon
                player.addItemToInventory(currentlyEquippedWeapon); // add the weapon to the player inventory
                currentlyEquippedWeapon = unarmed;
                player.setWeapon(unarmed); // set current weapon to unarmed
                player.setMinHit(currentlyEquippedWeapon.getItemMinDamage()); // update min damage
                player.setMaxHit(currentlyEquippedWeapon.getItemMaxDamage()); // update max damage
                System.out.println("You unequip the " + itemToBeUnequipped + ".");
                System.out.println("Your min damage is now: " + player.getMinHit() + " and your max damage is now: " + player.getMaxHit() + ".");
            }
        }else { // the player is not wearing the item in secondWord
            System.out.println("You can't unequip that item because you are not wearing it!");
        }
    }
}