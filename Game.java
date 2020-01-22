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
    private CommandParser commandParser;
    
    private Room currentRoom;
    private Player player;
    private Stack backList;
    private Item items;

    /**
     * Getting everything ready to start the game
     */
    public Game() {
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
        System.out.print("> ");

        // Switch construct
        //try {

        switch (in.nextLine()) {
            case "1":
            System.out.println("");
            System.out.println("Level 1 selected: The Cyst of Elemental Worms");

            // load the level
            level = new Levels(); // make connecting with the levels
            level.level1(); // load map 1
            currentRoom = level.getStartRoom(); // load the first room

            // load the player
            player = new Player(); // make connection and initialize a player (This player will have 20 hp and 50 carry weight)
            player.setCurrentRoom(currentRoom); // save the current room in the player class

            backList = player.getBack();
            parser = new Parser(); // start the game-listener
            
            commandParser = new CommandParser(player, currentRoom, level);

            System.out.println("");

            printWelcome(); // welcome the player
            playloop(); // start the game
            break;
            case "2":
            System.out.println("Level 2 selected: NoName.");
            break;
            case "3":
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
        /*}[FIX][FIX]
        catch (Exception e) {
        // Someone probably entered a string instead of a number. Inform the user and try again
        System.out.println("Please put in a number.");
        System.out.println("");
        play();
        }*/
    }

    public void playloop() {
        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        boolean gameover = false;
        while (!finished) {
            int playerHealth = player.getHealth();
            if (playerHealth == 0){
                gameover = true;
            }

            if (gameover){
                System.out.println("The game is over! Want to try again?");
                finished = true;
                play();
            } else {
                Scanner tokenizer = parser.getCommand(); // prompt the user to put a new command in
                Command command = parser.parseCommand(tokenizer); // send the "scanner" result to 
                finished = processCommand(command);
            }
        }
        goodbyeMessage();
    }

    /**
     * Print the goodbye message for the player if quit has been selected.
     */
    private void goodbyeMessage() {
        System.out.println("Thank you for playing Fables of Darkness.  Have a nice day and goodbye!");   
    }

    /**
     * Print the opening message for the player.
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
    public boolean processCommand(Command command) {
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
            commandParser.goRoom(command);
            break;

            case LOOK:
            System.out.println(currentRoom.getlongDescription());
            break;

            case SEARCH:
            commandParser.searchRoom();
            break;

            case TAKE:
            commandParser.pickupItem(command);
            break;

            case DROP:
            commandParser.dropItem(command);
            break;

            case BACK:
            commandParser.goBack();
            break;

            case INV:
            commandParser.lookInventory();
            break;

            case USE:
            commandParser.useItem(command);
            break;

            case BURN:
            commandParser.burnItem(command);
            break;

            case INSPECT:
            commandParser.getItemInformation(command);
            break;

            case EAT:
            commandParser.eatItem(command);
            break;

            case INFO:
            commandParser.getInfo();
            break;

            case QUIT:
            wantToQuit = quit(command);
            break;

            // Now come the aliasses of commands. This list MIGHT get long lol.
            /*case NORTH:
             * goRoom(command);(go north);
            break;
            case EAST:
            processCommand(go east);
            break;
            case SOUTH:
            processCommand(go south);
            break;
            case WEST:
            processCommand(go west);
            break;*/
        }
        return wantToQuit;
    }
    
    /**
     * Print out some help information. Here we print some stupid, cryptic message
     * and a list of the command words.
     */
    public void printHelp() {
        System.out.println("Somehow you ended up in this underground building.");
        System.out.println("You are alone. Or are you?...");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
        /**
     * "Quit" was entered. Check the rest of the command to see whether we really
     * quit the game.
     * 
     * @return true, if this command quits the game, false otherwise.
     */
    public boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }
}