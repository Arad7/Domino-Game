package domino;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Author: Gal Arad
 * The Board class sets up the game, keeps track of all the domino tiles
 * (those being played and those still to be played), and manages both the
 * human and computer players' turns.
 * It checks if moves are allowed, handles drawing new tiles,
 * and decides when the game ends and who wins.
 */
public class Board {
    private final Player human;
    private final Player computer;
    private final ArrayList<DominoTile> boneyard;
    private final ArrayList<DominoTile> playBoard;
    private int countNoPlay;

    /**
     * Initializes the game board by creating the boneyard
     * (pool of all domino tiles),
     * distributing tiles to both human and computer players, and setting the
     * initial start of the game
     *
     */
    public Board() {
        countNoPlay = 0;
        boneyard = new ArrayList<>();
        playBoard = new ArrayList<>();
        human = new Player();
        computer = new Player();
        creatBoneyard();

        human.draw7(boneyard);
        computer.draw7(boneyard);
    }
    private void creatBoneyard() {
        for (int i =6; i > -1; i--){
            for (int k = i; k>-1; k --) {
                boneyard.add(new DominoTile(i,k));
            }
        }
        Collections.shuffle(boneyard);
    }

    /**
     * Adds to the count of consecutive turns without a play
     */
    public void addToCountNoPlay(){
        countNoPlay = countNoPlay + 1;
    }

    /**
     * Prints the current hand of the human player to the console version.
     */
    public void printHumanTrey(){
        System.out.println(human.toString());
    }

    /**
     * @return The play board -
     * ArrayList containing the domino tiles on the play board.
     */
    public ArrayList<DominoTile> getPlayBoard(){return playBoard;}

    /**
     * @return The human Player object.
     */
    public Player getHumanPlayer(){return human;}

    /**
     * @return the boneYard - ArrayList containing the
     * domino tiles in the boneyard.
     */
    public ArrayList<DominoTile> getBoneyard(){return boneyard;}

    /**
     * Prints the current state of the play board to the console.
     */
    public void printPlayBoard() {
        StringBuilder topRow = new StringBuilder();
        StringBuilder bottomRow = new StringBuilder("   ");

        for (int i = 0; i < playBoard.size(); i++) {
            DominoTile tile = playBoard.get(i);

            if (i % 2 == 0) {
                topRow.append(tile.toString());
            } else {
                bottomRow.append(tile.toString());
            }
        }
        System.out.println(topRow);
        System.out.println(bottomRow);
    }

    /**
     * CHeck the playable sides of the playBoard and return them in an array
     * @return An array of two integers representing the values at the left
     * and right ends of the play board.
     */
    public int[] getPlayBoardSides(){
        int[] array = new int[2];
        array[0] = playBoard.get(0).getLeftSide();
        array[1] = playBoard.get(playBoard.size() -1).getRightSide();
        return array;

    }

    /**
     * Manages the drawing of tiles by a player until they can make a
     * legal play. This method
     * is intended to be used when a player cannot play any of their
     * current tiles and must draw from the boneyard.
     * @param player The player (either human or computer) who is drawing tiles.
     */
    public void drawCardsUntilCanPlay(Player player){
        DominoTile tile;
        do {
            tile = boneyard.remove(0);
            player.drawTile(tile);

            System.out.println("You could not play, card has been drawn.");

        } while ((player.canPlayTile(playBoard.get(0).getLeftSide(),
                playBoard.get(playBoard.size()-1).getRightSide()) == null) &&
                (!(boneyard.isEmpty())));
    }

    /**
     * Handles the logic for a player's turn, including playing a tile to
     * the play board or drawing tiles from the boneyard if no
     * legal move is available.
     * @param index The index of the tile in the player's hand will be played.
     * @param side A string indicating the side of the play board where the
     *           tile should be played ("l" for left, and anything else for
     *             right).
     */
    public void playerTurn(int index, String side){
        if (playBoard.isEmpty()){
            playBoard.add(human.getHand().remove(index));
        } else {
            // if cannot play - take cards until can
            int[] sides = getPlayBoardSides();
            if (human.canPlayTile(sides[0], sides[1]) == null &&
                    (!boneyard.isEmpty())) {
                do {
                    human.drawTile(boneyard.remove(0));
                    System.out.println("You could not play, card has " +
                            "been drawn.");
                    sides = getPlayBoardSides();
                } while ((human.canPlayTile(sides[0],sides[1]) == null) &&
                        (!(boneyard.isEmpty())));
            }
            if(human.canPlayTile(sides[0],sides[1]) != null){
                if (side.equals("l")) {
                    playBoard.add(0, human.getHand().remove(index));
                } else {
                    playBoard.add(playBoard.size(), human.getHand().
                            remove(index));
                }
                countNoPlay = 0;
            }else {
                countNoPlay++;
            }
        }
    }

    /**
     * make the computer player's turn, including selecting and playing
     * a tile or drawing from the boneyard.
     */
    public void computerTurn(){
        int sideLeft, sideRight;
        sideLeft = playBoard.get(0).getLeftSide();
        sideRight = playBoard.get(playBoard.size() -1).getRightSide();
        DominoTile tile = computer.computerPlay(sideLeft,sideRight,boneyard );
        if (tile == null){
            countNoPlay = countNoPlay +1;
        }
        else {
            countNoPlay = 0;
            System.out.println("computer turn");
            System.out.println("game board:");
            printPlayBoard();
            computer.getHand().remove(tile);
            if (tile.getRightSide() == sideLeft|| sideLeft == 0){
                playBoard.add(0,tile);
                System.out.println("computer played " + tile + " at the left");
            }
            else if (tile.getLeftSide() == sideRight || sideRight == 0){
                playBoard.add(playBoard.size(), tile);
                System.out.println("computer played " + tile + " at the right");
            }
            else if (tile.flipSides().getRightSide() == sideLeft) {
                playBoard.add(0,tile);
                System.out.println("computer played " + tile + " at the left");
            } else if (tile.getLeftSide() == sideRight) {
                playBoard.add(playBoard.size(), tile);
                System.out.println("computer played " + tile + " at the right");
            } else if (tile.getRightSide() == 0) {
                playBoard.add(0,tile);
                System.out.println("computer played " + tile + " at the left");
            }
            else {
                playBoard.add(playBoard.size(), tile);
                System.out.println("computer played " + tile + " at the right");
            }
            System.out.println("after playing, computer has " +
                    computer.getHand().size() + " cards");
        }
    }

    /**
     * Checks if a move is legal based on the selected tile and planned
     * side of play on the play board.
     * @param index The index of the tile in the player's hand that is
     *             being checked.
     * @param side - the side of the play board where the tile is intended to
     *            be played ("l" for left, anything else for right).
     * @return A boolean indicating whether the move is legal (true) or not (false).
     */
    public Boolean isMoveLegal(int index, String side){
        int playerNum;
        int sideOfPlayBoard;
        if (playBoard.isEmpty()){return true;}
        if (side.equals("l")){
            playerNum = human.getHand().get(index).getRightSide();
            sideOfPlayBoard = playBoard.get(0).getLeftSide();
        }
        else{
            playerNum = human.getHand().get(index).getLeftSide();
            sideOfPlayBoard = playBoard.get(playBoard.size()-1).getRightSide();
        }
        return playerNum == 0 || sideOfPlayBoard == 0 ||
                playerNum == sideOfPlayBoard;
    }

    /**
     * Evaluates if the game is over based on the current state of the playboard
     * players, and the boneyard.
     * @param turn The player who made the last move,
     * @return A String indicating the winner or null
     * if the game is not over.
     */
    public String isGameOver(Player turn){
        if(boneyard.isEmpty()) {
            if (human.getHand().isEmpty() && turn.equals(this.human)) {
                return "Human won!";
            } else if (computer.getHand().isEmpty() && turn.equals(this.computer)) {
                return "Computer won!";
            } else if (computer.getHand().isEmpty()) {
                return "Computer won!";
            } else if (human.getHand().isEmpty()) {
                return "Human won!";
            } else if (countNoPlay == 2) {
                int player = 0;
                int comp = 0;
                for (DominoTile tile : human.getHand()) {
                    player = player + tile.getRightSide() + tile.getLeftSide();
                }
                for (DominoTile tile : computer.getHand()) {
                    comp = comp + tile.getLeftSide() + tile.getRightSide();
                }
                if (player > comp) {
                    return "As both players can't play\nComputer won as the sum"
                            + "of its tiles was less than yours!";
                }
                return "As both players can't play\nHuman won as the sum of " +
                        "your tiles was less his!";
            }
        }
        return null;
    }
    /**
     * @return The computer Player object.
     */
    public Player getComputerPlayer(){
        return computer;
    }
}
