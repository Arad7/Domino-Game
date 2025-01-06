package domino;

import java.util.ArrayList;

/** Author: Gal Arad
 * Represents a player in the game, managing the player's hand of domino tiles.
 * A player can draw tiles into their hand, check if they have a playable tile,
 * play a tile to the game board, and manage their hand of tiles.
 */
public class Player {
    private final ArrayList<DominoTile> hand;

    /**
     * Constructs a Player with an empty hand of domino tiles.
     */
    public Player(){
        hand = new ArrayList<>();
    }

    /**
     * Returns the player's current hand of domino tiles.
     * @return An ArrayList containing the domino tiles
     */
    public ArrayList<DominoTile> getHand() { return hand;}

    /**
     * Adds a domino tile to the player's hand.
     * @param tile The domino tile to be added to the hand.
     */
    protected void drawTile (DominoTile tile){
        hand.add(tile);
    }

    /**
     * Draws 7 domino tiles from a list (which is the boneyard) and adds them
     * to the player's hand.
     * @param ls The list of domino tiles to draw from.
     */
    protected void draw7(ArrayList<DominoTile> ls){
        for (int i = 0; i <7; i ++){
            hand.add(ls.remove(0));
        }
    }
    /**
     * Checks if the player has a tile that can be played.
     * @param side1 The value on one end of the playboard.
     * @param side2 The value on the other end of the playboard.
     * @return The first domino tile that can be played or null if cannot play
     * a tile
     */
    public DominoTile canPlayTile(int side1, int side2){
        for (DominoTile tile : hand) {
            if (tile.getRightSide() == side1 || tile.getRightSide() == side2 ||
                    tile.getLeftSide() == side1 || tile.getLeftSide() == side2
                    || tile.getLeftSide() == 0 || tile.getRightSide() == 0
            || side1 == 0 || side2 == 0) {
                return tile;
            }
        }
        return null;
    }

    /**
     * @return A string representation of the domino tiles in the player's hand.
     */
    @Override
    public String toString() {
        String s = "[";
        for (DominoTile tile : hand){
            s = s + tile.toString();
        }
        s = s + "]";
        return s;
    }

    /**
     * Determines the next move for the computer player, to playing a tile or
     * drawing from the boneyard.
     * @param side1 The value on one end of the playboard.
     * @param side2 The value on the other end of the playboard.
     * @param boneyard The list of remaining tiles that can be drawn.
     * @return The domino tile to be played or null if no move is possible.
     */
    public DominoTile computerPlay(int side1, int side2, ArrayList<DominoTile>
            boneyard){
        DominoTile toPlay = canPlayTile(side1, side2);
        if (toPlay == null && !boneyard.isEmpty()) {
            do {
                drawTile(boneyard.remove(0));
                toPlay = canPlayTile(side1, side2);

            } while (toPlay == null && !boneyard.isEmpty());
        }
        return toPlay;
    }
}
