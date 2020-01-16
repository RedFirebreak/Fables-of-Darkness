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
    private Stack items = new Stack();
    private HashMap<String, Room> exits; // stores exits of this room.

    public Room getStartRoom(){
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

        // create the rooms with Room("short description (shows when you move into the
        // room)", "long description (shows when you seach the room)", boolean true or
        // false (checks whether the room can have an item or not)
        room1 = new Room("a dark storage room",
            "This is the storage room you woke up in! You see some crates in the corner, A small opening to another room and a sturdy looking wooden door",
            true);
        room2 = new Room("a storage room",
            "You see some neatly stacked boxes. A little puddle of water and a very bright torch on the wall.(CHANGE IF TORCH IS PICKED UP)",
            true);
        room3 = new Room("SHORT_DESC", "LONG_DESC", true);
        room4 = new Room("SHORT_DESC", "LONG_DESC", true);
        room5 = new Room("SHORT_DESC", "LONG_DESC", true);
        room6 = new Room("SHORT_DESC", "LONG_DESC", true);
        room7 = new Room("SHORT_DESC", "LONG_DESC", true);
        room8 = new Room("SHORT_DESC", "LONG_DESC", true);
        room9 = new Room("SHORT_DESC", "LONG_DESC", true);
        room10 = new Room("SHORT_DESC", "LONG_DESC", true);
        room11 = new Room("SHORT_DESC", "LONG_DESC", true);
        room12 = new Room("SHORT_DESC", "LONG_DESC", true);
        room13 = new Room("SHORT_DESC", "LONG_DESC", true);
        c1 = new Room("In a crossroad", "You can see a long corridor. It smells kind of damp.", true);
        c2 = new Room("SHORT_DESC", "LONG_DESC", true);
        c3 = new Room("SHORT_DESC", "LONG_DESC", true);
        c4 = new Room("SHORT_DESC", "LONG_DESC", true);
        c5 = new Room("SHORT_DESC", "LONG_DESC", true);
        c6 = new Room("SHORT_DESC", "LONG_DESC", true);
        de1 = new Room("SHORT_DESC", "LONG_DESC", false);
        de2 = new Room("SHORT_DESC", "LONG_DESC", false);
        t1 = new Room("SHORT_DESC", "LONG_DESC", false);
        t2 = new Room("SHORT_DESC", "LONG_DESC", false);
        bossRoom = new Room("SHORT_DESC", "LONG_DESC", false);

        //Set the roomID
        room1.setRoomID("1",room1);
        room2.setRoomID("2",room2);
        room3.setRoomID("3",room3);
        room4.setRoomID("4",room4);
        room5.setRoomID("5",room5);
        room6.setRoomID("6",room6);
        room7.setRoomID("7",room7);
        room8.setRoomID("8",room8);
        room9.setRoomID("9",room9);
        room10.setRoomID("10",room10);
        room11.setRoomID("11",room11);
        room12.setRoomID("12",room12);
        room13.setRoomID("13",room13);

        c1.setRoomID("14",c1);
        c2.setRoomID("15",c2);
        c3.setRoomID("16",c3);
        c4.setRoomID("17",c4);
        c5.setRoomID("18",c5);
        c6.setRoomID("19",c6);

        de1.setRoomID("20",de1);
        de2.setRoomID("21",de2);

        t1.setRoomID("22",t1);
        t2.setRoomID("23",t2);

        bossRoom.setRoomID("24",bossRoom);

        // Set the items in this level
        items.push("Bread");
        items.push("Bread");
        items.push("Jug of Water");
        items.push("Steak");
        items.push("Dagger");
        items.push("Cloak");
        items.push("Unlit Torch");

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

    public Stack getItemStack() {
        return items;
    }

    public int getStackCount() {
        Stack countStack = items;
        int count = 0;
        while(!countStack.isEmpty()) {
            count++;
            countStack.pop();
        }
        return count;
    }

}
