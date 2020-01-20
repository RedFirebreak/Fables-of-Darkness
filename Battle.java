import java.util.*;

/**
 * Write a description of class Battle here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Battle
{
    Randomizer randomize = new Randomizer();
    Parser parser = new Parser(); // Make sure the command can be parsed

    private Scanner reader; // What the user puts in
    boolean battleOver = false;
    boolean succeededrun = false;
    int round = 0;

    Player player;
    Enemy enemy;
    /**
     * Constructor for objects of class Battle
     */
    public Battle(Player player, Enemy enemy) {

        this.player = player;
        this.enemy = enemy;
        System.out.println("While walking into the room, you encounter " + enemy.getName() + ". He looks angry! Get ready to fight.");
        System.out.println("Your options are: Attack, Eat, Inspect, Run");
    }

    /**
     * The loop that keeps the battle going (or ends it)
     */
    public int play() {
        int returncode = 0; // 0 = player won, 1 = player ran, 2 = player died

        while (!battleOver) {

            if (enemy.getHealth()==0){
                battleOver = true;
                returncode = 0;
            }

            if (succeededrun) {
                battleOver = true;
                returncode = 1;
            }

            if (player.getHealth()==0){
                battleOver = true;
                returncode = 2;
            }

            if (!battleOver){ // battle is ongoing!
                // display the battle-menu
                round++;
                menu();
            }
            // Add stuff here that needs to happen EVERY round
        }

        switch (returncode) {
            case 0:
            System.out.println("The enemy crumbles before you. You live to fight another day.");
            System.out.println();
            break;

            case 1:
            System.out.println("You run away from the enemy. Leaving it in the dust.");
            System.out.println();
            break;

            case 2:
            System.out.println("As the enemy attacks you, you feel a sharp pain.");
            System.out.println("Oh dear! You are dead.");
            System.out.println();
            break;
        }

        return returncode;
    }

    private void menu(){
        System.out.println("");
        System.out.println("Round: "+round);
        System.out.println("Please choose your action: ");

        Scanner tokenizer = parser.getCommand(); // prompt the user to put a new command in
        Command command = parser.parseCommand(tokenizer); // send the "scanner" result to make it into a command
        processCommand(command); // process the command

    }

    private void processCommand(Command command) {
        // Switch construct
        System.out.println(); // formatting
        CommandWord commandWord = command.getCommandWord();
        switch (commandWord) {
            case ATTACK:
            System.out.println("You attack the monster!");

            // calculate the damage this enemy will do

            int playerDifferenceHit = player.getMaxHit() - player.getMinHit(); // 5 - 3 = 2. 
            playerDifferenceHit++; // +1 as we want "0" an option too  = 3
            int playerMinimumDamage = randomize.getRandomNumber(playerDifferenceHit); // returns 1-3
            playerMinimumDamage--; // -1 as so "0" is an option.
            int damage = player.getMinHit() + playerMinimumDamage; // add the minimum damage, to random hit damage.

            System.out.println("You deal " +damage+ " damage!");
            enemy.removeHealth(damage);

            enemyturn(); // let the enemy attack
            break;

            case EAT: // reach out to the main game again, then parse the command as usual
            // [FIX] broken
            int tempHealth = player.getHealth();
            Game game = new Game(); // Open up game again to check the commands
            game.eatItem(command);

            if (tempHealth == player.getHealth()) {
                // The player failed to eat something. Decrease the round
                round--;
            } else {
                // The player succesfully ate and recovered some HP.
                enemyturn(); // let the enemy attack
            }
            break;

            case INSPECT:
            System.out.println("You have a good look at the enemy:");
            enemy.getDescription();
            break;

            case RUN:
            System.out.println("You try to run...");
            int canRun = randomize.getRandomNumber(4); // gets back 1 -4
            if (canRun == 4) {
                System.out.println(".. but fail!");
                enemyturn(); // let the enemy attack
            } else {
                System.out.println(".. and succeed!");
                succeededrun = true;
            }
            break;

            default:
            System.out.println("That option does not exist! Please try again.");
            System.out.println("Your battle options are: Attack, Eat, Inspect, Run");
            System.out.println("");
            round--;
            break; // This break is not really necessary
        }
    }

    private void enemyturn() {
        if (enemy.getHealth() == 0) {
            enemy.setAlive(false);
        }

        if (enemy.getAlive()) {
            // time to fuck the players day up
            System.out.println(enemy.getName() + "attacks you!");

            // calculate the damage this enemy will do
            int differenceHit = enemy.getMaxHit() - enemy.getMinHit(); // difference will be 1-3. But plus 1 (as the result will ALWAYS be +1 in the randomizer so a "0" will not happen.
            differenceHit++;
            int minimumDamage = randomize.getRandomNumber(differenceHit); // for example, will return 0-4 if the min and max hit was 4-7. Then do a -1. Making it a 0-3 chance.
            minimumDamage--;
            int damage = enemy.getMinHit() + minimumDamage; // add the minimum damage, to random hit damage.

            System.out.println("You take " +damage+ " damage!");
            player.removeHealth(damage);
        } else {
            System.out.println("The enemy has no chance to fight back. As your final blow killed the enemy.");
        }

    }
}
