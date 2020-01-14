/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room room1, room2, room3, room4, room5, room6, room7, room8, room9, room10, room11, room12, room13;
        Room c1, c2, c3, c4, c5, c6, c7;
        Room de1, de2;
        Room t1, t2;
        Room bossRoom;
        // create the rooms
        room1 = new Room("where you woke up. This is a storage room");
        room2 = new Room("in another storage room");
        room3 = new Room("");
        room4 = new Room("");
        room5 = new Room("");
        room6 = new Room("");
        room7 = new Room("");
        room8 = new Room("");
        room9 = new Room("");
        room10 = new Room("");
        room11 = new Room("");
        room12 = new Room("");
        room13 = new Room("");
        c1 = new Room("in a crossroad");
        c2 = new Room("");
        c3 = new Room("");
        c4 = new Room("");
        c5 = new Room("");
        c6 = new Room("");
        c7 = new Room("");
        de1 = new Room("");
        de2 = new Room("");
        t1 = new Room("");
        t2 = new Room("");
        bossRoom = new Room("");

        // initialise room exits
        room1.setExit("north", room2);
        room1.setExit("south", c1);

        room2.setExit("south", room1);
        
        c1.setExit("north", room1);
        c1.setExit("east", room3);
        c1.setExit("west", room4);

        room3.setExit("south", c1);
        
        room4.setExit("southwest", room5);
        room4.setExit("southeast", c1);
        
        room5.setExit("north", room6);
        room5.setExit("southwest", room4);
        room5.setExit("southeast", c2);
        
        c2.setExit("south", room5);
        c2.setExit("north", room8);
        c2.setExit("west", t1);
        
        room6.setExit("south", room5);
        
        room7.setExit("north", room8);
        room7.setExit("south", t1);
        
        room8.setExit("north", c3);
        room8.setExit("east", c2);
        room8.setExit("south", room7);
        
        c3.setExit("east", bossRoom);
        c3.setExit("west", c4);
        c3.setExit("south", room8);
        
        c4.setExit("north", c3);
        c4.setExit("west", room9);
        c4.setExit("south", room11);
        
        room9.setExit("east", c4);
        room9.setExit("west", room10);
        
        room10.setExit("east", room9);
        
        room11.setExit("east", c4);
        room11.setExit("south", room12);
        
        room12.setExit("north", room11);
        room12.setExit("south", c5);
        
        c5.setExit("north", room12);
        c5.setExit("east", de1);
        c5.setExit("west", c6);
        
        de1.setExit("west", c5);
        
        c6.setExit("north", room13);
        c6.setExit("west", t2);
        c6.setExit("south", c5);
        
        room13.setExit("north", c6);
        
        de2.setExit("east", t2);
        
        currentRoom = room1;  // start game in room1, the Storage Room
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing Fables of Darkness.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Fables of Darkness!");
        System.out.println("You wake up in a dimly lit storage room.");
        System.out.println("The room is lit by a torch in the distance.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
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

            case QUIT:
            wantToQuit = quit(command);
            break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

}
