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
            int playerMinimumDamage = randomize.getRandomDamage(playerDifferenceHit); // returns 1-3
            int damage = playerDifferenceHit + playerMinimumDamage; // add the minimum damage, to random hit damage.

            System.out.println("You deal " +damage+ " damage!");
            enemy.removeHealth(damage);

            enemyturn(); // let the enemy attack
            break;

            case EAT: // reach out to the main game again, then parse the command as usual
            // [FIX] broken
            int tempHealth = player.getHealth();
            eatItem(command);

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
            System.out.println(enemy.getDescription() + " The enemy has " + enemy.getHealth() + " health left.");
            System.out.println("You have a good look at yourself:");
            System.out.println("You have " + player.getHealth() + " health left.");
            round--;
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
            System.out.println("");//formatting
            System.out.println(enemy.getName() + " attacks you!");

            // calculate the damage this enemy will do
            int differenceHit = enemy.getMaxHit() - enemy.getMinHit(); // difference will be 0-3. But plus 1 (as the result will ALWAYS be +1 in the randomizer so a "0" will not happen.
            int minimumDamage = randomize.getRandomDamage(differenceHit); // for example, will return 0-4 if the min and max hit was 4-7. Then do a -1. Making it a 0-3 chance.
            int damage = differenceHit + minimumDamage; // add the minimum damage, to random hit damage.

            // calculate the players armor
            damage = damage - player.getPlayerArmorRating();
            if (damage <= 0){
                System.out.println("... but you dont take any damage because of your armor!");
            } else {

                System.out.println("You take " + damage + " damage!");
                player.removeHealth(damage);
            }

        } else {
            System.out.println("The enemy has no chance to fight back. As your final blow killed the enemy.");
        }

    }

    /**
     * "eat" was entered. Check if a second word has been send too and check if the item is eatable.
     * If it is eatable, heal the player if their hp isn't full, and remove the item and its weight from the player.
     * 
     * @param command The command entered in the Parser.
     */
    public void eatItem(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to eat...
            System.out.println("Eat what?");
            return;
        }
        String itemToBeEaten = command.getSecondWord();
        List<Item> playerInventory =  player.getPlayerInventory(); // load the inventory of the player

        String unlockItem;
        Room roomToUnlock;
        boolean somethingToUse = false;
        int notThisItem = 0;
        int loop;

        for (loop = 0; loop < playerInventory.size(); loop++) {
            Item currentItem = playerInventory.get(loop);
            if (itemToBeEaten.equals(currentItem.getItemName()) ){ // get the item name, then check if thats something
                if (currentItem.getItemCategory().equals("food")){
                    if((player.getHealth())<(20)) {
                        player.addHealth(currentItem.getHealAmount());
                        player.removeItemFromInventory(currentItem); // remove item from inventory
                        System.out.println("You eat the " + itemToBeEaten + ". It heals for " + currentItem.getHealAmount()+".");
                    } else {
                        System.out.println("Your HP is full!");
                    }
                } else {
                    System.out.println("You can't eat that item!");
                }

            } else {
                // the item did not match the player provided name
                notThisItem++;
            };
            somethingToUse = true;
        }

        //errors afvangen
        if (!somethingToUse) {
            System.out.println("You can't inspect that!");
        }

        if (loop == notThisItem) {
            //ThisItem is the same amount as loop. Then the player put something in that is not in the room
            System.out.println("You cannot eat " + itemToBeEaten + " because you don't have it in your inventory!");
        }

    }
}
