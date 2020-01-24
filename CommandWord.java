/**
 * Representations for all the valid command words for the game along with the corresponding string.
 * 
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24.01.2020
 */
public enum CommandWord {
    // A value for each command word along with its corresponding user interface string.
    GO("go"), SEARCH("search"), LOOK("look"), TAKE("take"), DROP("drop"),  INV("inv"), USE("use"), BURN("burn"), 
    INSPECT("inspect"), INFO("info"), EAT("eat"), EQUIP("equip"), UNEQUIP("unequip"), BACK("back"), ATTACK("attack"), RUN("run"), QUIT("quit"), HELP("help"),
    UNKNOWN("?");
    
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