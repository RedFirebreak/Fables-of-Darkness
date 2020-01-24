import java.util.*;

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
        System.out.println("1) Level 1\n2) Level 2\n3) About\n4) Exit");
        System.out.print("> ");

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

            printWelcomeLevel1(); // welcome the player
            playloop(); // start the game
            break;
            case "2":
            System.out.println("");
            System.out.println("Level 2 selected: This level has not been made yet!");
            waitForKeyPress();
            break;
            case "3":
            System.out.println("");
            System.out.println("Fables of Darkness");
            System.out.println("A game made by Stefan Kuppen and Stefan Jilderda");
            System.out.println("We spent alot of time making this game for a school project, and hope you will enjoy all its glory. Even thought its only a text-adventure game.");
            waitForKeyPress();
            play();
            break;
            case "4":
            System.out.println("");
            System.out.println("Exit selected!");
            System.out.println("");
            goodbyeMessage();
            break;
            default:
            System.out.println("That option does not exist! Please try again.");
            waitForKeyPress();
            play();
            break;
        }
    }

    public void waitForKeyPress(){
        Scanner in = new Scanner(System.in);
        System.out.println("");
        System.out.println("Press any key to continue....");
        switch (in.nextLine()) {
            // nothing. Any input works 
        }
    }

    public void playloop() {
        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;
        boolean gameover = false;
       
        while (!finished) {
            currentRoom = player.getCurrentRoom();
            int playerHealth = player.getHealth();
            if (playerHealth == 0){
                gameover = true;
            }
            if (currentRoom.getWinRoom()) {
                // player is in the win room! yay!
                finished = true;
                System.out.println("You won the game! Congratulations! Did you know that enemies, items and damage is completely random?");
                System.out.println("Play again to get a completely different expierence!");
                System.out.println("");
                waitForKeyPress();

            } else {
                if (gameover){
                    System.out.println("Game over! Want to try again?");
                    finished = true;
                    waitForKeyPress();
                    play();
                } else {
                    Scanner tokenizer = parser.getCommand(); // prompt the user to put a new command in
                    Command command = parser.parseCommand(tokenizer); // send the "scanner" result to 
                    finished = processCommand(command);
                }
            }
        }
        // check if currentroom = the winroom
        play(); // back to the menu!
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
    private void printWelcomeLevel1() {
        System.out.println("You wake up in a dimly lit storage room.");
        System.out.println("The room is lit by a torch in the distance.");
        System.out.println("Your command words are:");
        System.out.println("You can type 'help' at any time to see your command words.");

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
        // update current room first
        currentRoom = player.getCurrentRoom();
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        currentRoom.getRoomID();

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

            case EQUIP:
            commandParser.equipItem(command);
            break;

            case UNEQUIP:
            commandParser.unequipItem(command);
            break;

            case INFO:
            commandParser.getInfo();
            break;

            case QUIT:
            wantToQuit = quit(command);
            break;
        }
        return wantToQuit;
    }

    /**
     * Print out some help information. Here we print some stupid, cryptic message
     * and a list of the command words.
     */
    public void printHelp() {
        System.out.println("You have woken up inside of a cave, you do not know how you got here.");
        System.out.println("You have the feeling that you better get out of here, it's so dark here.");
        System.out.println("You can hear some sounds in the distance.. Hopefully that isn't trouble.");
        System.out.println("");
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