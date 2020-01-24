import java.util.HashMap;

/**
 * This class holds an enumeration of all command words known to the game. It is
 * used to recognise commands as they are typed in.
 *
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24-01-2020
 */

public class CommandWords {
    private HashMap<String, CommandWord> validCommands; // a mapping between a command word and the CommandWord associated with it.
    private String description; // description of the command
    
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords() {
        validCommands = new HashMap<>();
        description = "description not found."; // standard description
        for (CommandWord command : CommandWord.values()) {
            if (command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * Find the CommandWord associated with a command word.
     * 
     * @param  commandWord The word to look up.
     * @return The CommandWord correspondng to commandWord, or UNKNOWN if it is not a valid command word.
     */
    public CommandWord getCommandWord(String commandWord) {
        CommandWord command = validCommands.get(commandWord);
        if (command != null) {
            return command;
        } else {
            return CommandWord.UNKNOWN;
        }
    }

    /**
     * Check whether a given String is a valid command word.
     * 
     * @return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() {
        for (String command : validCommands.keySet()) {
            System.out.print(command);
            switch(command) { // add a description for the commands, so players know what they mean
                case "go":
                description = "......Accepts a second input word. Move the player in that direction.";
                break;
                
                case "search":
                description = "..Searches the room for any items laying around.";
                break;
                
                case "look":
                description = "....Looks inside the room for a more accurate description, could give hints.";
                break;
                
                case "take":
                description = "....Accepts a second input word. Take an item that is in the room.";
                break;
                
                case "drop":
                description = "....Accepts a second input word. Drop an item that is inventory to the current room.";
                break;
                
                case "inv":
                description = ".....Checks out all the items in your inventory.";
                break;
                
                case "use":
                description = ".....Accepts a second input word. Use an item that is in your inventory. Useful for keys.";
                break;
                
                case "burn":
                description = "....Accepts a second input word. Burn... things...";
                break;
                
                case "inspect":
                description = ".Accepts a second input word. Inspects an item that is in your inventory. \n        Can be used in battle to inspect your opponent.";
                break;
                
                case "info":
                description = "....Gives information about the player.";
                break;
                
                case "eat":
                description = ".....Accepts a second input word. Eat a food item to heal you some health. Can be used in battles.";
                break;
                
                case "back":
                description = "....Go back from where you came from. Remembers all previous locations.";
                break;
                
                case "quit":
                description = "....Quits the game. WARNING: there is no save of your game, you will lose all progress.";
                break;
                
                case "help":
                description = "....Shows a list of commands and an insight of your miserable situation.";
                break;
                
                case "attack":
                description = "..Used in battles. Attack your opponent.";
                break;
                
                case "run":
                description = ".....Used in battles. Have a chance to run away from your enemy.";
                break;
                
                case "equip":
                description = "...Accepts a second input word. Equips a weapon or armor on the player.";
                break;
                
                case "unequip":
                description = ".Accepts a second input word. Unequips a weapon or armor that is already on the player.";
                break;
                  
                default:
                description = "Some description broke ^_^ ERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERRORERROR";
                break;
            }
            System.out.println(description);
        }
        System.out.println();
    }
}