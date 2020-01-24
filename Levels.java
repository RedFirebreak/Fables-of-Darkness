import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Levels contains all different levels Fables of Darkness has.
 * It sets the rooms, items, enemies and all their variables.
 *
 * @author Stefan Jilderda and Stefan Kuppen
 * @version 24-01-2020
 */
public class Levels {
    private Room currentRoom;
    private Stack<Item> randomItems = new Stack<Item>();
    private HashMap<String, Room> allroomIDs; // stores ids of ALL rooms.
    private int totalRooms = 0;

    public Room getStartRoom() {
        return currentRoom;
    }

    /**
     * Generate the rooms, their exits, descriptions and item availability.
     * Set key items in rooms, and also generate random items.
     * Put all roomids in a HashMap for use on other pages.
     * Set all enemies and set them in rooms.
     * Set all item values.
     */
    public void level1() {
        //Generate all rooms we want to have.
        Room room1, room2, room3, room4, room5, room6, room7, room8, room9, room10, room11, room12, room13; // all rooms
        Room c1, c2, c3, c4, c5, c6; // all crossroads
        Room de1, de2; // all dead-ends
        Room t1, t2; // all trapdoor-rooms
        Room bossRoom, winRoom; // important rooms

        //Generate enemies
        Enemy wyrm1, wyrm2, human1;
        Enemy boss;

        //Items to randomly generate
        Item bread, bread2, jug_of_water, steak, dagger, cloak, chainmail, unlit_torch, torch, health_biscuit, shortsword, boulder;
        //Start key items
        Item brass_key, bronze_key, mysterious_key;

        //Open the connection to the randomizer. as this level requires it
        Randomizer randomize = new Randomizer();

        //Make all rooms, with each room add to the integer roomid
        int roomid = 1;
        room1 = new Room(Integer.toString(roomid++), "a dark storage room", "This is the storage room you woke up in! \nYou see some crates in the corner and some neatly stacked boxes.", true);
        room2 = new Room(Integer.toString(roomid++), "a storage room", "You see some boxes all over the place. \nSome are even completely drenched in the little puddle of water that is on the floor.", true);
        room3 = new Room(Integer.toString(roomid++), "a bloody empty room", "You see some bloody scratch marks and a crack in the wall. \nLooks ominous. The only way out is south.", true);
        room4 = new Room(Integer.toString(roomid++), "a room with some greens.", "This room has some little bushes with some light coming from above. \nYou see a door to the southwest, and a small opening to the southeast.", true);
        room5 = new Room(Integer.toString(roomid++), "a large decorated room", "It looks like somebody has been living here. \nThe ground looks slimy and some candles are lit. \nYou can go north, southwest and southeast.", true);
        room6 = new Room(Integer.toString(roomid++), "a odd looking sleeping room", "There are some cocoons hanging from the walls. \nIt looks like something was sleeping here recently. \nYou can only go south from here.", true);
        room7 = new Room(Integer.toString(roomid++), "a empty looking slimy room", "Yeez! Is this a stockpile for all their slime or something! \nA lot of light is radiating in the room from the north. \nThere's a way into a long hallway to the south, there are some blood splatters on the ground.", true);
        room8 = new Room(Integer.toString(roomid++), "a very brighly lit room", "Something or someone lit over a hundred candles in this room! \nThe ground is still slimy and slippery. \nA sturdy looking bronze door is on the north, a passage-way on the east, it looks like there is another room on the south.", true);
        room9 = new Room(Integer.toString(roomid++), "a tiny kitchen", "This kitchen looks like its been used to cook something delicious, there's a very nice smell coming from the kitchen. \nTo the west there's a door. To the east is a large brass door.", true);
        room10 = new Room(Integer.toString(roomid++), "a type of jail", "You see some shackles protruding from the wall. \nWait, is that a skeleton hanging from the ceiling?.... \nThere's a door to the west. ", true);
        room11 = new Room(Integer.toString(roomid++), "a black room with alot of scorch marks", "Wow! Has a dragon been here or something! \nThe whole room looks black from burn marks! \nThere's a nice looking corridor on the east and a door the south. You can hear some static noise.", true);
        room12 = new Room(Integer.toString(roomid++), "a decent looking room", "Wait, is that a television? And why is it on static... \nThere is also a very worn out couch made of silk. \nThere's a badly burned door do the north and passage way to the south.", true);
        room13 = new Room(Integer.toString(roomid++), "a nicely looking sleeping room", "There's a slimy bed in the room. \nAlso a small night-stand next to it with a small jewelery box. \nTo the north there is a dimly lit corridor.", true);
        c1 = new Room(Integer.toString(roomid++), " in a small cavern ", "You can see a long corridor going three ways. \nOne way leads back to the room you woke up in.  \nIt smells kind of damp.", true);
        c2 = new Room(Integer.toString(roomid++), "a fork form the long hallway", "You see some light coming from the north. \nSome bloody scratches on the wall on the left. \nSouth doesn't seem all that interesting.", true);
        c3 = new Room(Integer.toString(roomid++), "a round room", "There's a nice looking corridor to the west. \nYou can also spot a red light coming from a mysterious glowing door to the east. \nTo the south is a opened bronze door.", true);
        c4 = new Room(Integer.toString(roomid++), "a nice looking corridor", "This is a nice looking corridor! \nThere are alot of shiny decorations on the walls! \nThe corridor continues to the south, there's a brass door to the west and there's a faint glow coming from the north.", true);
        c5 = new Room(Integer.toString(roomid++), "a fork in the path!", "There's nothing special looking in this crossroad. \nYou can hear some static noise coming from the north. \nOn the west, there's a small candle on the wall in a corner. \nIt's dark on the east.  ", true);
        c6 = new Room(Integer.toString(roomid++), "a dimly lit hallway", "You can barely see here. \nHowever, there's a nice smell coming from the west. \nA faint light is coming from the south and a passage way to the north.", true);
        de1 = new Room(Integer.toString(roomid++), "in a dead end", "Why are you looking around in a dead end?... \nWait, is that something shiny? \nYou can only go back to the west.", false);
        de2 = new Room(Integer.toString(roomid++), "a crossroad that isn't possible to get to.", "You will literally die if you stay here. Wtf.", false);
        t1 = new Room(Integer.toString(roomid++), "a room with some rocks.", "Why did I land DIRECTLY on these rocks? \nLets never do that again. \nYou see a small stairway leading up to a very small hole in the wall to the west. ", false);
        t2 = new Room(Integer.toString(roomid++), "a small puddle of blood", "Okay, that hurt. \nYou see a faint glow of light coming from the hole you just fell trough. \nTo the west is a small pathway leading up to a crack in the wall. \nThere is a nice smell coming from the crack. \nYou wonder if you can squeeze trough it.", false);
        bossRoom = new Room(Integer.toString(roomid++), "a very large battle arena", "You smell fresh air coming from the stairway on the south. \nTo the east is the horrible cyst.", false);
        winRoom = new Room(Integer.toString(roomid++), "a grass patch. You are outside!", "You breath the fresh air! You are free.", false);

        //Set the roomID in the room, then add it to the level-map
        allroomIDs = new HashMap<>();
        allroomIDs.put(room1.getRoomID(), room1);
        allroomIDs.put(room2.getRoomID(), room2);
        allroomIDs.put(room3.getRoomID(), room3);
        allroomIDs.put(room4.getRoomID(), room4);
        allroomIDs.put(room5.getRoomID(), room5);
        allroomIDs.put(room6.getRoomID(), room6);
        allroomIDs.put(room7.getRoomID(), room7);
        allroomIDs.put(room8.getRoomID(), room8);
        allroomIDs.put(room9.getRoomID(), room9);
        allroomIDs.put(room10.getRoomID(), room10);
        allroomIDs.put(room11.getRoomID(), room11);
        allroomIDs.put(room12.getRoomID(), room12);
        allroomIDs.put(room13.getRoomID(), room13);
        allroomIDs.put(c1.getRoomID(), c1);
        allroomIDs.put(c2.getRoomID(), c2);
        allroomIDs.put(c3.getRoomID(), c3);
        allroomIDs.put(c4.getRoomID(), c4);
        allroomIDs.put(c5.getRoomID(), c5);
        allroomIDs.put(c6.getRoomID(), c6);
        allroomIDs.put(de1.getRoomID(), de1);
        allroomIDs.put(de2.getRoomID(), de2);
        allroomIDs.put(t1.getRoomID(), t1);
        allroomIDs.put(t2.getRoomID(), t2);
        allroomIDs.put(bossRoom.getRoomID(), bossRoom);
        allroomIDs.put(winRoom.getRoomID(), winRoom);

        //Generate all random items
        bread = new Item("bread","A nice loaf of bread. Not warm though. Can be eaten to heal 2 health points.","food",0,0,0,2,1,1,true,false);
        bread2 = new Item("bread","A nice loaf of bread. Not warm though. Can be eaten to heal 2 health points.","food",0,0,0,2,1,1,true,false);
        jug_of_water = new Item("jug_of_water","A jug. it contains water.","food",0,0,0,1,2,1,true,false);
        steak = new Item("steak","A healthy-sized steak. It looks like Gandhi's flipflop. Can be eaten to heal 5 health points.","food",0,0,0,5,3,1,true,false);
        dagger = new Item("dagger","A sharp weapon, relatively small. Good for stabby jabbies.","weapon",3,5,0,0,2,5,true,false);
        cloak = new Item("cloak","A cloak. Protects from rain and gives warmth.","armor",0,0,1,0,3,1,true,false);
        chainmail = new Item("chainmail","A piece of chainmail armor. Protects the body when equipped.","armor",0,0,3,0,20,10,true,false);
        unlit_torch = new Item("unlit_torch","An unlit torch, how useless!","generic",0,0,0,0,5,1,true,false);
        health_biscuit = new Item("health_biscuit","A life elixer, health potion, red pot or whatever you want to name it, but it is in biscuit form. \nIt smells really good! Can be eaten for 10 health points.","food",0,0,0,10,3,1,true,false);
        shortsword = new Item("shortsword","A shortsword, things will get more 'ouch' with this.","weapon",5,9,0,0,5,1,true,false);
        boulder = new Item("boulder","It is heavy. Way. Too. Heavy.","wtf",5000,10000,2000,0,10000,9001,false,false);

        //Generate all key items needed for this level
        torch = new Item("torch","A flame on a stick, lights up the area around you so mobs don't spawn. Can burn stuff.","keyItem",0,0,0,0,5,1,true,true);
        brass_key = new Item("brass_key","A brass key, probably used for unlocking something. Why is it always brass though?","keyItem",0,0,0,0,5,1,true,false);
        bronze_key = new Item("bronze_key","A bronze key, useful for unlocking things.","keyItem",0,0,0,0,5,1,true,false);
        mysterious_key = new Item("mysterious_key","This key glows a little and you can feel its warmth. Must unlock something powerful.","keyItem",0,0,0,0,5,1,true,false);

        //Set key-items in their rooms
        room2.setRoomInventory(torch); // "key" for c1, burn the door!
        room10.setRoomInventory(brass_key); // used to unlock room 9
        room13.setRoomInventory(mysterious_key); // used to unlock bossroom

        //Set other non-random items
        room9.setRoomInventory(health_biscuit); // best food
        room10.setRoomInventory(shortsword); // best weapon
        de1.setRoomInventory(chainmail); // best armor

        //Randomize the place where the bronze_key is.
        int tempRoomForItem = 2 + randomize.getRandomNumber(6); // will return 3, 4, 5, 6, 7 or 8
        Room roomToAddItem = allroomIDs.get(Integer.toString(tempRoomForItem)); // load the room based on string-ID
        roomToAddItem.setRoomInventory(bronze_key); // add the bronze_key to the generated room ID

        //Add the rest of the items to be randomized, completely random over the entire map
        randomItems.push(bread);
        randomItems.push(bread2);
        randomItems.push(jug_of_water);
        randomItems.push(steak);
        randomItems.push(dagger);
        randomItems.push(cloak);
        randomItems.push(unlit_torch);
        randomItems.push(boulder);

        //Get a random number in String form based on the max of the roomcount 
        totalRooms = roomid; // copy the max amount of rooms
        Iterator randomItemsIterator = randomItems.iterator(); // Create a iterator to loop trough all the random items 
        while (randomItemsIterator.hasNext()) {
            int randomNumber = randomize.getRandomNumber(totalRooms); // get a random number (int)
            String randomRoomID = Integer.toString(randomNumber); // convert the int to string
            Room roomToAddRandomItem = allroomIDs.get(randomRoomID); // load the room based on string-ID
            if (roomToAddRandomItem.canRoomHoldItems()) { // check if the room may hold items, if not the getting of the rooms is done again
                roomToAddRandomItem.setRoomInventory(randomItems.peek()); // add the top-most item to the room we just loaded.
                randomItems.pop();
            }
        } 

        //Set locked rooms, and the rooms to unlock them
        c1.setIsLocked(true);
        room1.setUnlockRoom(true, "nothing, needs to be burned.", c1.getRoomID());

        c3.setIsLocked(true);
        room8.setUnlockRoom(true, "bronze_key", c3.getRoomID());

        room9.setIsLocked(true);
        room10.setUnlockRoom(true, "brass_key", room9.getRoomID());

        bossRoom.setIsLocked(true);
        c3.setUnlockRoom(true, "mysterious_key", bossRoom.getRoomID());

        //Set the rooms that can be burned
        room1.setCanBeBurned(true);

        //Set the trap rooms
        t1.setIsTrapRoom(true);
        t2.setIsTrapRoom(true);

        //Set room exits
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

        t1.setExit("west", room3);

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

        t2.setExit("west", room10);

        room13.setExit("north", c6);

        de2.setExit("east", t2);

        bossRoom.setExit("west", c3);
        bossRoom.setExit("south", winRoom);

        //Create enemies and the boss monster
        wyrm1 = new Enemy("Heasdasz", 5, "A small but angry looking wyrm! It looks like he has some sharp fangs!", 2, 3); // found in room 5,6,7 or 8
        wyrm2 = new Enemy("Trorzegs", 8, "A angry looking fire wyrm! He breathes fire!", 2, 5); // found in room 11, 12 or 13
        human1 = new Enemy("Arlin", 10, "A fit looking human. He's holding a small dagger.", 3, 5); // found in room 9 or 10
        boss = new Enemy("Drace Grim", 20, "A VERY strong looking human! He's holding a giant axe.", 3, 7); // found in bossRoom

        //Add the enemy to specifed, random room
        int tempRoomNumber;
        Room roomToAddEnemy;

        tempRoomNumber = 4 + randomize.getRandomNumber(4); // will return 5,6,7 or 8
        roomToAddEnemy = allroomIDs.get(Integer.toString(tempRoomNumber)); // load the room based on string-ID
        roomToAddEnemy.addEnemy(wyrm1);

        tempRoomNumber = 10 + randomize.getRandomNumber(3); // will return 11, 12 or 13
        roomToAddEnemy = allroomIDs.get(Integer.toString(tempRoomNumber)); // load the room based on string-ID
        roomToAddEnemy.addEnemy(wyrm2);

        tempRoomNumber = 8 + randomize.getRandomNumber(2); // will return 9 or 10
        roomToAddEnemy = allroomIDs.get(Integer.toString(tempRoomNumber)); // load the room based on string-ID
        roomToAddEnemy.addEnemy(human1);

        //Add the boss to the bossRoom
        bossRoom.addEnemy(boss);

        currentRoom = room1; // set current game to start here
    }

    /**
     * @return Returns the HashMap of all roomIDs
     */
    public HashMap<String, Room> getAllroomIDs() {
        return allroomIDs;
    }
}