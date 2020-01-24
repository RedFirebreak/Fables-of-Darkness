import java.util.Stack;
import java.util.Scanner;

/**
 * This class is the main class of the "Fables of Darkness" application. 
 * "Fables of Darkness" is a text based adventure game. 
 * Users can walk around some scenery, get items, kill monsters.
 * Get to the end and you will be free from the monsters!
 *
 * 
 * This main class creates and initialises all the others:
 * It creates the Parser, loads the CommandParser, sets the level, 
 * loads the player, it's items and backlist.
 * 
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24.01.2020
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
     * Constructor is empty. Things are set in method play().
     */
    public Game() {
    }

    /**
     * Main play routine. Loops until end of play.
     * In here you have the level selector. The rest is loaded in Levels.
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

        switch (in.nextLine()) { //check what the player has entered
            case "1":
            System.out.println("");
            System.out.println("Level 1 selected: The Cyst of Elemental Worms");

            // load the level
            level = new Levels(); // make connection with the levels
            level.level1(); // load level 1
            currentRoom = level.getStartRoom(); // load the first room

            // load the player
            player = new Player(); // make connection and initialize a player (This player will have 20 hp and 50 carry weight)
            player.setCurrentRoom(currentRoom); // save the current room in the player class
            backList = player.getBack(); // set the backStack
            
            parser = new Parser(); // start the game-listener
            commandParser = new CommandParser(player, currentRoom, level); // start the commandParser

            System.out.println("");
            printWelcomeLevel1(); // welcome the player
            playloop(); // start the game
            break;
            
            case "2":
            System.out.println("");
            System.out.println("Level 2 selected: This level has not been made yet!");
            waitForKeyPress();
            play();
            break;
            
            case "3":
            System.out.println("");
            System.out.println("Fables of Darkness");
            System.out.println("A game made by Stefan Kuppen and Stefan Jilderda");
            System.out.println("We spent a lot of time making this game for a school project, and we hope you will enjoy all its glory. Even though it's only a text-adventure game.");
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

    /**
     * Player needs to input something to continue.
     */
    public void waitForKeyPress(){
        Scanner in = new Scanner(System.in);
        System.out.println("");
        System.out.println("Press any key to continue....");
        switch (in.nextLine()) {
            // nothing. Any input works 
        }
    }

    /**
     * A level is loaded, this loop will play until the end.
     */
    public void playloop() {
        // Enter the main command loop. Here we repeatedly read commands and execute them until the game is over.
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
    private void printWelcomeLevel1() {
        System.out.println("You wake up in a dimly lit storage room.");
        System.out.println("The room is lit by a torch in the next room.");
        System.out.println("You can type 'help' at any time to see your command words.");

        System.out.println("");
        System.out.println(currentRoom.getRoomDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed.
     * @return        true: ends the game, false otherwise.
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
            wantToQuit = true;
            break;
        }
        return wantToQuit;
    }

    /**
     * Print out some help information. Here we print an helpful message,
     * aswell as a list of the command words and how to use them.
     */
    public void printHelp() {
        System.out.println("You have woken up inside of a cave, you do not know how you got here.");
        System.out.println("You have the feeling that you better get out of here, it's so dark here.");
        System.out.println("You can hear some sounds in the distance.. Hopefully that isn't trouble.");
        System.out.println("");
        parser.showCommands();
    }
}