
/**
 * Write a description of class Levels here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Levels {
    // instance variables - replace the example below with your own
    private Room currentRoom;
    
    public Room getStartRoom(){
        return currentRoom;
    }
    public void level1() {
        Room room1, room2, room3, room4, room5, room6, room7, room8, room9, room10, room11, room12, room13;
        Room c1, c2, c3, c4, c5, c6, c7;
        Room de1, de2;
        Room t1, t2;
        Room bossRoom;
        // create the rooms
        room1 = new Room("a dark storage room",
                "This is the storage room you woke up in! You see some crates in the corner, A small opening to another room and a sturdy looking wooden door");
        room2 = new Room("a storage room",
                "You see some neatly stacked boxes. A little puddle of water and a very bright torch on the wall.(CHANGE IF TORCH IS PICKED UP)");
        room3 = new Room("SHORT_DESC", "LONG_DESC");
        room4 = new Room("SHORT_DESC", "LONG_DESC");
        room5 = new Room("SHORT_DESC", "LONG_DESC");
        room6 = new Room("SHORT_DESC", "LONG_DESC");
        room7 = new Room("SHORT_DESC", "LONG_DESC");
        room8 = new Room("SHORT_DESC", "LONG_DESC");
        room9 = new Room("SHORT_DESC", "LONG_DESC");
        room10 = new Room("SHORT_DESC", "LONG_DESC");
        room11 = new Room("SHORT_DESC", "LONG_DESC");
        room12 = new Room("SHORT_DESC", "LONG_DESC");
        room13 = new Room("SHORT_DESC", "LONG_DESC");
        c1 = new Room("In a crossroad", "You can see a long corridor. It smells kind of damp.");
        c2 = new Room("SHORT_DESC", "LONG_DESC");
        c3 = new Room("SHORT_DESC", "LONG_DESC");
        c4 = new Room("SHORT_DESC", "LONG_DESC");
        c5 = new Room("SHORT_DESC", "LONG_DESC");
        c6 = new Room("SHORT_DESC", "LONG_DESC");
        c7 = new Room("SHORT_DESC", "LONG_DESC");
        de1 = new Room("SHORT_DESC", "LONG_DESC");
        de2 = new Room("SHORT_DESC", "LONG_DESC");
        t1 = new Room("SHORT_DESC", "LONG_DESC");
        t2 = new Room("SHORT_DESC", "LONG_DESC");
        bossRoom = new Room("SHORT_DESC", "LONG_DESC");

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
        

        currentRoom = room1;
    }
}
