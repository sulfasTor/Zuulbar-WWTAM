/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class GameEngine
{
  private Parser parser;
  private Room currentRoom;
  private UserInterface gui;
  
  /**
   * Create the game and initialise its internal map.
   */
  public GameEngine() 
  {
    createRooms();
    parser = new Parser();
  }

  public void setGUI(UserInterface userInterface)
  {
    gui = userInterface;
    printWelcome();
  }  

  /**
   * Create all the rooms and link their exits together.
   */
  private void createRooms()
  {
    Room[] levels = new Room[15];
    Room outside;
      
    // create the rooms
    for(int i = 0; i < 15; i++)
      levels[i] = new Room("Room Lvl " + i);
    outside = new Room("You win");
        
    // initialise room exits
    levels[0].setExits(levels[1], null, null, null, null, null);
    for(int i = 1; i < 15 - 1; i++) {
      levels[i].setExits(null, null, levels[i-1], null, null, null);
      levels[i].setExits(levels[i+1], null, null, null, null, null);
    }
    levels[14].setExits(null, outside, null, null, null, null);
    outside.setExits(null, null, levels[14], null, null, null);

    currentRoom = levels[0];  // start game outside
  }

  /**
   * Print out the opening message for the player.
   */
  private void printWelcome()
  {
    gui.print("\n");
    gui.println("Welcome to the premiere of the biggest, most exciting, most dramatic ");
    gui.println("television show that I, or you, or anyone else has ever seen! Every ");
    gui.println("night, for the next two weeks, real people, just like you, who called ");
    gui.println("our phone number, will be playing for an unprecedented cash prize of ");
    gui.println("one million dollars! We are terribly excited about this show! The ");
    gui.println("technology, the music, the lights, the human drama! Even this audience ");
    gui.println("gets us crazy! What a night this is going to be! The contestants will ");
    gui.println("have to get through all the rooms answering the most challenging ");
    gui.println("questions. The first to exit the building win the prize! This is Zuul ");
    gui.println("Wants To Be A Millionaire. ");
    gui.print("\n");
    this.printLocationInfo();
  }

  /**
   * Given a command, process (that is: execute) the command.
   * @param command The command to be processed.
   * @return true If the command ends the game, false otherwise.
   */
  public boolean interpretCommand(String commandString) 
  {
    boolean wantToQuit = false;
    Command command = parser.getCommand(commandString);

    if(command.isUnknown()) {
      gui.print("I don't know what you mean...");
      return false;
    }

    String commandWord = command.getCommandWord();
    if (commandWord.equals("help"))
      printHelp();
    else if (commandWord.equals("go"))
      goRoom(command);
    else if (commandWord.equals("quit"))
      wantToQuit = quit(command);
    else if (commandWord.equals("look"))
      look();
    else if (commandWord.equals("eat"))
      eat();
    return wantToQuit;
  }

  // implementations of user commands:

  /**
   * Print out some help information.
   * Here we print some stupid, cryptic message and a list of the 
   * command words.
   */
  private void printHelp() 
  {
    gui.println(parser.getCommandList());
  }
  
  /**
   * Prints out information about the current room
   */
  private void printLocationInfo()
  {	
    gui.println(currentRoom.getLongDescription());
  }
  
  /** 
   * Try to go to one direction. If there is an exit, enter
   * the new room, otherwise print an error message.
   */
  private void goRoom(Command command) 
  {
    if(!command.hasSecondWord()) {
      // if there is no second word, we don't know where to go...
      gui.print("Go where?");
      return;
    }

    String direction = command.getSecondWord();

    // Try to leave current room.
    Room nextRoom = null;
    if(direction.equals("north")) {
      nextRoom = currentRoom.exits.get("north");
    }
    if(direction.equals("east")) {
      nextRoom = currentRoom.exits.get("east");;
    }
    if(direction.equals("south")) {
      nextRoom = currentRoom.exits.get("south");
    }
    if(direction.equals("west")) {
      nextRoom = currentRoom.exits.get("west");
    }

    if (nextRoom == null) {
      gui.print("There is no door!");
    }
    else {
      currentRoom = nextRoom;
      gui.println(currentRoom.getLongDescription());
      gui.print("\n");
    }
  }

  /** 
   * "Quit" was entered. Check the rest of the command to see
   * whether we really quit the game.
   * @return true, if this command quits the game, false otherwise.
   */
  private boolean quit(Command command) 
  {
    if(command.hasSecondWord()) {
      gui.print("Quit what?");
      return false;
    }
    else {
      return true;  // signal that we want to quit
    }
  }
  /**
   *
   * Prints a long description of the currentRoom
   */
  private void look() {
    gui.print(currentRoom.getLongDescription());
  }
  
  /**
   *
   * Prints a message abouth eating
   */
  private void eat() {
    gui.print("You just ate something from your right pocket");
  }
}
