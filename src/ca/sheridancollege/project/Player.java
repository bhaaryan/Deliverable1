/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;

/**
 * A class that models each Player in the game. Players have an identifier, which should be unique.
 *
 * @author aryan bhardwaj, abdul basith
 * Date - June 30 , 2023
     */
public abstract class Player {

     
    private String playerID; //the unique name for this player
   
    

    /**
     * @return the player name
     */
    public String getPlayerID() {
        return playerID;
    }
    
      /**
     * A method to check that the palyerId length is 3 letters or more
     *
     * @param name
     * @return
     */
    public static boolean checkId(String name) {
        if (name.length() >= 3) {
            return true;
        }
        return false;
    }

    /**
     * A method that check if the password does not have any special character
     *
     * @param pass
     * @return
     */
    public static boolean checkChar(String name) {
        for (int i = 0; i < name.length(); i++) {
            int ch = name.charAt(i);
            if (!Character.isLetterOrDigit(ch)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method to test if the user ii is unique
     *
     * @param player1
     * @return
     */
    public static boolean checkUnique(String player1, String player2) {
        if (player1.equals(player2)) {
            throw new IllegalArgumentException("Error, Enter unique user Id");
        }
        return true;
    }

    /**
     * Ensure that the playerID is unique
     *
     * @param givenID the playerID to set
     */
    public void setPlayerID(String givenID) {
        if (checkId(givenID)
                && checkChar(givenID)) {
            this.playerID = givenID;
        } else {
            throw new IllegalArgumentException(" Wrong value, try again!");
        }
    }
}
