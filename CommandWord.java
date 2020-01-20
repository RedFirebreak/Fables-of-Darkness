import java.util.*;

/**
 * Representations for all the valid command words for the game along with a
 * string in a particular language.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public enum CommandWord {
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), SEARCH("search"), LOOK("look"), TAKE("take"), DROP("drop"),  INV("inv"), USE("use"), BURN("burn"), INSPECT("inspect"), INFO("info"), EAT("eat"), BACK("back"), QUIT("quit"), HELP("help"),
    UNKNOWN("?"),
    
    // Aliasses for commands
    NORTH("north"), EAST("east"), SOUTH("south"), WEST("west");

    // The command string.
    private String commandString;

    /**
     * Initialise with the corresponding command string.
     * 
     * @param commandString The command string.
     */
    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    /**
     * @return The command word as a string.
     */
    public String toString() {
        return commandString;
    }

}
