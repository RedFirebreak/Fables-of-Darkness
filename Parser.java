import java.util.*;

/**
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and tries
 * to interpret the line as a two-word command. It returns the command as an
 * object of class Command.
 *
 * The parser has a set of known command words. It checks user input against the
 * known commands, and if the input is not one of the known commands, it returns
 * a command object that is marked as an unknown command.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Parser {
    private CommandWords commands; // holds all valid command words
    private Scanner reader; // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Scanner getCommand() {
        String inputLine; // will hold the full input line

        System.out.print("> "); // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.

        Scanner tokenizer = new Scanner(inputLine);

        return tokenizer;
    }

    public Command parseCommand(Scanner tokenizer) {
        String word1 = null;
        String word2 = null;
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next(); // get first word
            word1 = word1.toLowerCase(); // formatting
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next(); // get second word
                word2 = word2.toLowerCase(); // formatting
                // note: we just ignore the rest of the input line.
            }
        }

        if(word1==null){
            // do nothing
        } else {
            // check if there's a synonym in place
            switch (word1) {
                case "north":
                word1 = "go";
                word2 = "north";
                break;

                case "east":
                word1 = "go";
                word2 = "east";
                break;

                case "south":
                word1 = "go";
                word2 = "south";
                break;

                case "west":
                word1 = "go";
                word2 = "west";
                break;

                case "view":
                word1 = "look";
                break;
                
                case "pickup":
                word1 = "take";
                break;
                
                case "inventory":
                word1 = "inv";
                break;

                default: // do nothing with the input
                word1 = word1;
                word2 = word2;

            }
        }

        if(word2==null){
            // do nothing
        } else {
            // check if there's a synonym in place
            switch (word1) {
                case "yeet":
                word2 = "YEEEEEEEEEEET";
                break;
                default: // do nothing with the input
                word2 = word2;

            }
        }

        return new Command(commands.getCommandWord(word1), word2); // make a "command" out of it
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands() {
        commands.showAll();
    }
}
