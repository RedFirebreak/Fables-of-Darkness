import java.util.*;

/**
 * Write a description of class Levels here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Levels {
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private Stack<Item> randomItems = new Stack<Item>();
    private List<String> lockedRoomList = new ArrayList<String>();
    private List<String> allItemsList = new ArrayList<String>();
    private int totalRooms = 0;

    private HashMap<String, Room> allroomIDs; // stores ids of ALL rooms.

    public Room getStartRoom() {
        return currentRoom;
    }

    /**
     * Generate the rooms, the exits, descreptions and item availability.
     * 
     */
    public void level1() {
        // What rooms do you want to generate? room is a room. c is a crossroad. de is a
        // dead end. t is a trapdoor. bossRoom is unique.
        Room room1, room2, room3, room4, room5, room6, room7, room8, room9, room10, room11, room12, room13;
        Room c1, c2, c3, c4, c5, c6;
        Room de1, de2;
        Room t1, t2;
        Room bossRoom;

        // enemies
        Enemy goblin1, goblin2, human1;
        Enemy boss;

        // items
        Item bread, bread2, jug_of_water, steak, dagger, cloak, chainmail, unlit_torch, torch, health_biscuit, shortsword, boulder;
        // key items
        Item brass_key, bronze_key, mysterious_key;

        // Open the connection to the randomizer. as this level requires it
        Randomizer randomize = new Randomizer();

        // First, create a roomname, then calculate the next ID, provide a  "short description
        // (shows when you move into the
        // room)", "long description (shows when you seach the room)", boolean true or
        // false (checks whether the room can have an item or not)
        int roomid = 1;
        room1 = new Room(Integer.toString(roomid++), "a dark storage room",
            "This is the storage room you woke up in! You see some crates in the corner, A small opening to another room and a sturdy looking wooden door",
            true);
        room2 = new Room(Integer.toString(roomid++), "a storage room",
            "You see some neatly stacked boxes. A little puddle of water and a very bright torch on the wall.(CHANGE IF TORCH IS PICKED UP)",
            true);
        room3 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        room4 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        room5 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        room6 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        room7 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        room8 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        room9 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        room10 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        room11 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        room12 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        room13 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        c1 = new Room(Integer.toString(roomid++), "In a crossroad",
            "You can see a long corridor. It smells kind of damp.", true);
        c2 = new Room(Integer.toString(roomid++), "In a crossroad", "In a damp crossroad", true);
        c3 = new Room(Integer.toString(roomid++), "In a crossroad", "In a hot crossroad", true);
        c4 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        c5 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        c6 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", true);
        de1 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", false);
        de2 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", false);
        t1 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", false);
        t2 = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", false);
        bossRoom = new Room(Integer.toString(roomid++), "SHORT_DESC", "LONG_DESC", false);

        // Set the roomID in the room, then add it to the level-map
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

        // Generate all items needed for this level
        // (required input) String itemname, String itemDescription, String itemCategory, int itemMinDamage, int itemMaxDamage, int itemArmorRating,
        // int itemHealAmount, int itemWeight, int itemValue, boolean itemPickupAble

        bread = new Item("bread","A nice loaf of bread. Not warm though. Can be eaten to heal 2 health points.","food",0,0,0,2,1,1,true);
        bread2 = new Item("bread","A nice loaf of bread. Not warm though. Can be eaten to heal 2 health points.","food",0,0,0,2,1,1,true);
        jug_of_water = new Item("jug_of_water","A jug. it contains water.","food",0,0,0,1,2,1,true);
        steak = new Item("steak","A healthy-sized steak. It looks like Gandhi's flipflop. Can be eaten to heal 5 health points.","food",0,0,0,5,3,1,true);
        dagger = new Item("dagger","A sharp weapon, relatively small. Good for stabby jabbies.","weapon",3,5,0,0,2,5,true);
        cloak = new Item("cloak","A cloak. Protects from rain and gives warmth.","armor",0,0,1,0,3,1,true);
        chainmail = new Item("chainmail","A piece of chainmail armor. Protects the body when equipped.","armor",0,0,3,0,20,10,true);
        unlit_torch = new Item("unlit_torch","An unlit torch, how useless!","generic",0,0,0,0,0,1,true);
        health_biscuit = new Item("health_biscuit","A life elixer, health potion, red pot or whatever you want to name it, but it is in biscuit form. Can be eaten for 10 health points.","food",0,0,0,10,3,1,true);
        shortsword = new Item("shortsword","A shortsword, things will get more 'ouch' with this.","weapon",5,9,0,0,5,1,true);
        boulder = new Item("boulder","It is heavy. Way. Too. Heavy.","wtf",5000,10000,2000,0,10000,9001,false);

        // Generate all KEY items needed for this level
        torch = new Item("torch","A flame on a stick, lights up the area around you so mobs don't spawn. Can burn stuff.","keyItem",0,0,0,0,5,1,true);
        brass_key = new Item("brass_key","A brass key, probably used for unlocking something. Why is it always brass though?","keyItem",0,0,0,0,5,1,true);
        bronze_key = new Item("bronze_key","A bronze key, useful for unlocking things.","keyItem",0,0,0,0,5,1,true);
        mysterious_key = new Item("mysterious_key","This key glows a little and you can feel its warmth. Must unlock something powerful.","keyItem",0,0,0,0,5,1,true);

        //test
        room1.setRoomInventory(cloak);
        room1.setRoomInventory(chainmail);
        //test end
        
        
        //Set key-items in their rooms
        room2.setRoomInventory(torch);
        room9.setRoomInventory(health_biscuit);
        room10.setRoomInventory(shortsword);
        room10.setRoomInventory(brass_key); // used to unlock room 9
        room13.setRoomInventory(mysterious_key); // used to unlock bossroom

        //randomizer for keyForC3 DOE DIT OOK VOOR DE ANDERE ITEMS

        int tempRoomForItem = 2 + randomize.getRandomNumber(6); // will return 3, 4, 5, 6, 7 or 8
        Room roomToAddItem = allroomIDs.get(Integer.toString(tempRoomForItem)); // load the room based on string-ID
        roomToAddItem.setRoomInventory(bronze_key);

        // Add the rest of the items to be randomized, completely random over the entire map
        randomItems.push(bread);
        randomItems.push(bread2);
        randomItems.push(jug_of_water);
        randomItems.push(steak);
        randomItems.push(dagger);
        randomItems.push(cloak);
        randomItems.push(unlit_torch);
        randomItems.push(boulder);

        // Get a random String-number based on the max of the roomcount 
        totalRooms = roomid; // copy the max amount of rooms (is 25 for the first level)
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

        // set locked rooms, and the rooms to unlock them
        c1.setIsLocked(true);

        c3.setIsLocked(true);
        room8.setUnlockRoom(true, "bronze_key", c3.getRoomID());

        room9.setIsLocked(true);
        room10.setUnlockRoom(true, "brass_key", room9.getRoomID());

        bossRoom.setIsLocked(true);
        c3.setUnlockRoom(true, "mysterious_key", bossRoom.getRoomID());

        // set the rooms that can be burned
        room1.setCanBeBurned(true);

        // set the trap rooms
        t1.setIsTrapRoom(true);
        t2.setIsTrapRoom(true);

        // initialise room exits, roomname.setExit("direction", room_to_exit_to)
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

        // Create enemies and the boss monster
        // Name, max health, description, minimum damage, maximum damage
        goblin1 = new Enemy("Heasdasz", 5, "A small but angry looking goblin! He's holding a stick!", 2, 3); // found in room 5,6,7 or 8
        goblin2 = new Enemy("Trorzegs", 5, "A small but angry looking goblin!", 1, 3); // found in room 11, 12 or 13
        human1 = new Enemy("Arlin", 10, "A fit looking human. He's holding a small dagger.", 2, 3); // found in room 9 or 10

        boss = new Enemy("Drace Grim", 30, "A VERY strong looking human! He's holding a giant axe.", 3, 5); // found ALWAYS in boss-room

        // Add the enemy to specifed, random room
        int tempRoomNumber;
        Room roomToAddEnemy;

        tempRoomNumber = 4 + randomize.getRandomNumber(4); // will return 5,6,7 or 8
        roomToAddEnemy = allroomIDs.get(Integer.toString(tempRoomNumber)); // load the room based on string-ID
        roomToAddEnemy.addEnemy(goblin1);

        tempRoomNumber = 10 + randomize.getRandomNumber(3); // will return 11, 12 or 13
        roomToAddEnemy = allroomIDs.get(Integer.toString(tempRoomNumber)); // load the room based on string-ID
        roomToAddEnemy.addEnemy(goblin2);

        tempRoomNumber = 8 + randomize.getRandomNumber(2); // will return 9 or 10
        roomToAddEnemy = allroomIDs.get(Integer.toString(tempRoomNumber)); // load the room based on string-ID
        roomToAddEnemy.addEnemy(human1);

        // add the boss room
        bossRoom.addEnemy(boss);

        currentRoom = room1;
    }

    public List<String> getLockedRoomList() {
        return lockedRoomList;
    }

    public Stack getItemStack() {
        return randomItems;
    }

    public HashMap<String, Room> getAllroomIDs() {
        return allroomIDs;
    }
}
