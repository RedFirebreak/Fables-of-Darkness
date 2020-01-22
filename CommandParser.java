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

        HashMap<String, Room> allroomIDs = level.getAllroomIDs();

        if (nextRoom == null) {
            System.out.println("You can't go that way!");
        } else {
            boolean goToNextRoom = true;
            if (nextRoom.getIsLocked() && nextRoom.getIsTrapRoom()){
                System.out.println("This is the corridor where the trapdoor was.");
                System.out.println("You don't want to go in here again so you stay where you are.");
                return;
            }

            if (nextRoom.getIsLocked()){
                System.out.println("This door seems to be locked. It won't open.");
                return;
            }

            if (nextRoom.getIsTrapRoom()) {
                System.out.println("You fell into a trapdoor!");
                nextRoom.lockRoom();
                player.clearBack(); // clears the entire back command
                currentRoom = nextRoom; // go to the next room
                player.setCurrentRoom(currentRoom); // save the current room in the player class
                player.removeHealth(1);
                System.out.println("You take 1 damage because you hurt your leg after the fall.");
                System.out.println("The hole is too deep so you cannot climb back up if you tried.");
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
                player.addBack(currentRoom.getRoomID()); // add the previous room to the "back" command.
                currentRoom = nextRoom; // go to the next room
                player.setCurrentRoom(currentRoom); // save the current room in the player class
                System.out.println("");

                System.out.println(currentRoom.getRoomDescription()); // Print out the current description
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
            Iterator printInventoryList=player.getPlayerInventory().iterator();
            while(printInventoryList.hasNext()) {
                String nextItem = printInventoryList.next().toString();
                System.out.println(nextItem);
            }
        }
        else {
            System.out.println("Your inventory is empty.");
        }
    }

    public void searchRoom() {
        if (currentRoom.doesRoomContainItems()){
            System.out.println("You search the room and find the following items: ");
            Iterator printList=currentRoom.getRoomInventory().iterator();
            while(printList.hasNext()) {
                String nextItem = printList.next().toString();
                System.out.println(nextItem);
            }
        }
        else {
            System.out.println("You search around the room but fail to find any items of use.");
        }

    }

    public void getInfo() {
        System.out.println("Your current hp is: " + player.getHealth() + ".");
        System.out.println("Your current carry weight is: " + player.getCarryWeight() + "/" + player.getMaxCarryWeight() + "KGs.");
        System.out.println("You can deal " + player.getMinHit() + " to " + player.getMaxHit() + " damage.");
        System.out.println("The deal the damage with: " + "[[FIX]] ENTER ITEM TO DEAL DAMAGE WITH");
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
        int notThisItem;
        int loop;

        for (loop = 0; loop < currentRoomInventory.size(); loop++) {
            Item currentItem = currentRoomInventory.get(loop);
            if (itemToBeAdded == currentItem.getItemName() ){ // get the item name, then check if thats something
                // item name matches the player-provided name
                // pick it up! (add to player inv, remove from room)
                if(currentItem.getItemPickupAble()) {
                    player.addItemToInventory(currentItem); // add to player inventory 
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
        String itemToBeDropped = command.getSecondWord();
        Item selectedItem = new Item(); //setting the new item..
        selectedItem.setItemVariables(itemToBeDropped); //.. and getting all variables

        if (player.getPlayerInventory().contains(itemToBeDropped)) {
            player.removeItemFromInventory(itemToBeDropped);
            player.removeFromCarryWeight(selectedItem.getItemWeight());
            currentRoom.setRoomInventory(itemToBeDropped);
            System.out.println("You drop the " + itemToBeDropped + " and put it on the ground.");
        }
        else {
            System.out.println("You cannot drop " + itemToBeDropped + " because you don't have it in your inventory!");
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
        String itemToBeInspected = command.getSecondWord();
        Item selectedItem = new Item(); //setting the new item..
        selectedItem.setItemVariables(itemToBeInspected); //.. and getting all variables

        if (player.getPlayerInventory().contains(itemToBeInspected)) {
            System.out.println(itemToBeInspected + "'s description: " + selectedItem.getItemDescription());
            System.out.println(itemToBeInspected + "'s weight: " + selectedItem.getItemWeight());
            System.out.println(itemToBeInspected + "'s value: " + selectedItem.getItemValue());
        }
        else {
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
        String correctRoom;
        HashMap<String, Room> allroomIDs = level.getAllroomIDs();
        Room roomToUnlock;
        Item selectedItem = new Item(); //setting the new item..
        selectedItem.setItemVariables(itemToBeUsed); //.. and getting all variables

        if (player.getPlayerInventory().contains(itemToBeUsed)) { // check if item is in your inventory
            correctRoom = currentRoom.getRoomID();
            switch (correctRoom) {
                case "8": //room 8, to unlock c3
                System.out.println("You unlock the door. The key is stuck in the door, so you lose it.");
                player.removeItemFromInventory(itemToBeUsed); // remove item from inventory
                player.removeFromCarryWeight(selectedItem.getItemWeight()); // remove the item weight from carryWeight
                roomToUnlock = allroomIDs.get("16"); //16 is c3
                roomToUnlock.unlockRoom();
                break;

                case "10": //room 10, to unlock room9
                System.out.println("You unlock the door. The key is stuck in the door, so you lose it.");
                player.removeItemFromInventory(itemToBeUsed); // remove item from inventory
                player.removeFromCarryWeight(selectedItem.getItemWeight()); // remove the item weight from carryWeight
                roomToUnlock = allroomIDs.get("9");
                roomToUnlock.unlockRoom();
                break;

                case "16": //c3. to unlock bossRoom
                System.out.println("You unlock the door. The key is stuck in the door, so you lose it.");
                player.removeItemFromInventory(itemToBeUsed); // remove item from inventory
                player.removeFromCarryWeight(selectedItem.getItemWeight()); // remove the item weight from carryWeight
                roomToUnlock = allroomIDs.get("24");
                roomToUnlock.unlockRoom();
                break;

                default:
                System.out.println("You cannot use this item here.");
                break;
            }
        }
        else {
            System.out.println(itemToBeUsed + " can't be used because you don't have it in your inventory!");
        }
    }

    public void burnItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to burn...
            System.out.println("Burn what?");
            return;
        }
        String whatToBurn = command.getSecondWord();
        String currentRoomid = currentRoom.getRoomID();
        HashMap<String, Room> allroomIDs = level.getAllroomIDs();
        Room roomToUnlock;

        if (player.getPlayerInventory().contains("torch")) { // check if item is in your inventory
            if (currentRoom.getCanBeBurned()) {
                if (whatToBurn.equals("door")) {
                    System.out.println("You burn the door with your torch, the way is now free!");
                    roomToUnlock = allroomIDs.get("14"); //14 is c1
                    roomToUnlock.unlockRoom();
                    currentRoom.setCanBeBurned(false);
                }
                else {
                    System.out.println("You cannot burn this.");
                }
            }
            else{
                System.out.println("There is no " + whatToBurn + " to burn in this room.");
            }
        }
        else {
            System.out.println("You have nothing to burn " + whatToBurn + " with!");
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
        Item selectedItem = new Item(); //setting the new item..
        selectedItem.setItemVariables(itemToBeEaten); //.. and getting all variables

        if (player.getPlayerInventory().contains(itemToBeEaten)) { // check if item is in your inventory
            switch (itemToBeEaten) {
                case "bread":
                if((player.getHealth())<=(20)) {
                    System.out.println("You eat the bread. It heals 2 HP.");
                    player.addHealth(2); // heal the player a selected amount
                    player.removeItemFromInventory(itemToBeEaten); // remove item from inventory
                    player.removeFromCarryWeight(selectedItem.getItemWeight()); // remove the item weight from carryWeight
                    System.out.println("Your HP is now " + player.getHealth() + ".");
                }
                else {
                    System.out.println("Your HP is full!");
                }
                break;

                case "steak":
                if((player.getHealth())<=(20)) {
                    System.out.println("You eat the steak. It heals 5 HP.");
                    player.addHealth(5); // heal the player a selected amount
                    player.removeItemFromInventory(itemToBeEaten); // remove item from inventory
                    player.removeFromCarryWeight(selectedItem.getItemWeight()); // remove the item weight from carryWeight
                    System.out.println("Your HP is now " + player.getHealth() + ".");
                }
                else {
                    System.out.println("Your HP is full!");
                }
                break;

                case "health_biscuit":
                if((player.getHealth())<=(20)) {
                    System.out.println("You eat the biscuit. It heals 10 HP.");
                    player.addHealth(10); // heal the player a selected amount
                    player.removeItemFromInventory(itemToBeEaten); // remove item from inventory
                    player.removeFromCarryWeight(selectedItem.getItemWeight()); // remove the item weight from carryWeight
                    System.out.println("Your HP is now " + player.getHealth() + ".");
                }
                else {
                    System.out.println("Your HP is full!");
                }
                break;

                default:
                System.out.println("You cannot eat this item.");
                break;

            }
        }
        else {
            System.out.println(itemToBeEaten + " can't be eaten because you don't have it in your inventory!");
        }
    }

}