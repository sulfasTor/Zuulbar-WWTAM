/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

public class Room 
{
  public String description;
  public HashMap<String, Room> exits;
  
  /**
   * Create a room described "description". Initially, it has
   * no exits. "description" is something like "a kitchen" or
   * "an open court yard".
   * @param description The room's description.
   */
  public Room(String description) 
  {
    this.description = description;
    this.exits =  new HashMap<String, Room>(4);
  }

  /**
   * Define the exits of this room.  Every direction either leads
   * to another room or is null (no exit there).
   * @param north The north exit.
   * @param east The east east.
   * @param south The south exit.
   * @param west The west exit.
   * @param up The ceiling exit.
   * @param down The trap door.
   */
  public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) 
  { 
    if (north != null)
      this.exits.put("north", north);

    if (east != null)
      this.exits.put("east", east);

    if (south != null)
      this.exits.put("south", south);

    if (west != null)
      this.exits.put("west", west);

    if (up != null)
      this.exits.put("up", up);
    
    if (down != null)
      this.exits.put("down", down);

  }

  /**
   * @return The description of the room.
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * @return The description of the room with more details.
   */
  public String getLongDescription()
  {
    return "You are at " + description + "\n" + getExitString();
  }

  /**
   * @return A string containing the available exits
   */

  public String getExitString() {
    String returnString = "Exits: "; // Chaîne de charactères renvoyée
    Set<String> keys = this.exits.keySet(); // Recupère l'ensemble des clés du Hash [
    for (String exit : keys)           // Ajoute les clés a la chaîne
      returnString += exit + ' ';

    return returnString;
  }


}
