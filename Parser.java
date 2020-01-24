import java.util.Scanner;

/**
 * This parser reads user inputs and tries to interpret it as an "Adventure"command.
 * Every time it is called it reads a line from the terminal and tries to interpret the line as a two-word command. 
 * It returns the command as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against the
 * known commands, and if the input is not one of the known commands, it returns
 * a command object that is marked as an unknown command.
 * 
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24.01.2020
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
     * Gets the command to use.
     * 
     * @return The next command from the user.
     */
    public Scanner getCommand() {
        String inputLine; // will hold the full input line
        System.out.print("> "); // print prompt

        inputLine = reader.nextLine(); // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);

        return tokenizer;
    }

    /**
     * This parses the command. It gets the words from the scanner and puts them into a Command.
     * 
     * @param tokenizer The scanner from above.
     * @return give the command back as a Command class.
     */
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

        if(word1==null){ // check if user gave input
            // do nothing
        } else { // check if there's a synonym in place
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

                default: // do nothing with the input, no synonyms found
                word1 = word1;
                word2 = word2;
                break;
            }
        }

        return new Command(commands.getCommandWord(word1), word2); // make a Command" out of it
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands() {
        commands.showAll();
    }
}