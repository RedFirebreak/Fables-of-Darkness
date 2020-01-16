import java.util.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
    private Parser parser;
    private Levels level;
    private Room currentRoom;
    private Randomizer itemRandomizer;
    private Player player;
    private Stack backList;
    private java.util.List<String> inventory = new ArrayList<>();

    /**
     * Getting everything ready to start the game
     */
    public Game() {
        inventory.add("Bread");
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        Scanner in = new Scanner(System.in);
        
        System.out.println(" __________________________ ");
        System.out.println("|    FABLES OF DARKNESS    |");
        System.out.println("|        1. level 1        |");
        System.out.println("|        2. level 2        |");
        System.out.println("|        3. Exit           |");
        System.out.println("|__________________________|");
        System.out.println("");
        System.out.println("1) Level 1\n2) Level 2\n3) Exit");
        System.out.print(" > ");

        // Switch construct
        try {
        
            switch (in.nextInt()) {
            case 1:
                // in.close(); // Close the menu listener
                System.out.println("");
                System.out.println("Level 1 selected: The Cyst of Elemental Worms");
    
                level = new Levels(); // make connecting with the levels
                level.level1(); // load map 1
                currentRoom = level.getStartRoom(); // Get the start room
    
                player = new Player(); // load the player
                backList = player.getBack();
    
                parser = new Parser(); // start the game-listener
    
                System.out.println("");
    
                printWelcome(); // welcome the player
                playloop(); // start the game
                break;
            case 2:
                System.out.println("Level 2 selected: NoName.");
                break;
            case 3:
                System.out.println("Exit selected!");
                System.out.println("");
                goodbyeMessage();
                break;
            default:
                System.out.println("That option does not exist! Please try again.");
                System.out.println("");
                play();
                break;
            }
        }
        catch (Exception e) {
          // Someone probably entered a string instead of a number. Inform the user and try again
          System.out.println("Please put in a number.");
          System.out.println("");
          play();
    }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void playloop() {
        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        goodbyeMessage();
    }
    
    private void goodbyeMessage() {
     System.out.println("Thank you for playing Fables of Darkness.  Have a nice day and goodbye!");   
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println("You wake up in a dimly lit storage room.");
        System.out.println("The room is lit by a torch in the distance.");
        System.out.println("Your command words are:");
        parser.showCommands();
        
        System.out.println("");
        System.out.println(currentRoom.getRoomDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
        case UNKNOWN:
            System.out.println("I don't know what you mean...");
            break;

        case HELP:
            printHelp();
            break;

        case GO:
            // backList.push(command.getSecondWord()); // save the current room, then
            // TELEPORT him there
            goRoom(command);
            break;

        case LOOK:
            System.out.println(currentRoom.getlongDescription());
            break;

        case SEARCH:
            System.out.println(currentRoom.getRoomInventory());
            break;

        case TAKE:
            pickupItem(command);
            break;

        case BACK:
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
            break;

        case INV:
            System.out.println("Your inventory contains: ");
            System.out.println(inventory);
            break;

        case QUIT:
            wantToQuit = quit(command);
            break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information. Here we print some stupid, cryptic message
     * and a list of the command words.
     */
    private void printHelp() {
        System.out.println("Somehow you ended up in this underground building.");
        System.out.println("You are alone. Or are you?...");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            System.out.println(currentRoom.getRoomDescription());
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("You can't go that way!");
        } else {
            player.addBack(currentRoom.getRoomID()); // add the previous room to the "back" command.
            currentRoom = nextRoom; // go to the next room
            System.out.println("");
            System.out.println(currentRoom.getRoomDescription()); // Print out the current description

            Levels level = new Levels();
            int roomCount = level.getRoomCount();
        }
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    private void pickupItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }

    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we really
     * quit the game.
     * 
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }
}