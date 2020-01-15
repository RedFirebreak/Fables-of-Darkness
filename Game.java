import java.util.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    private Player player;
    private java.util.List<String> inventory;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        level = new Levels();
        level.level1();
        
        parser = new Parser();
    }

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing Fables of Darkness.  Have a nice day and goodbye!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {

        System.out.println("You wake up in a dimly lit storage room.");
        System.out.println("The room is lit by a torch in the distance.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println("");
        //System.out.println(currentRoom.getRoomDescription());
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
            goRoom(command);
            break;

        case LOOK:
            System.out.println(currentRoom.getlongDescription());
            break;

        case SEARCH:
            System.out.println(currentRoom.getlongDescription());
            break;
            
        case INV:
            if(inventory==null) {inventory.add("empty");}
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
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getlongDescription());
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
