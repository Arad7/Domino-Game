package domino;

import java.util.Scanner;

/**
 * Author: Gal Arad
 * The MainConsole class is the entry point for a console-based domino game.
 * It handles game flow, player interactions, and displays game state updates.
 */
public class MainConsole {

    /**
     * The main method starts the domino game, managing the game loop
     * and player inputs.
     * It prompts the human player for actions,
     * updates the game state accordingly, and controls whose turn is it
     * between the human and computer players.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        System.out.println("Hello This is Domino game!");
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        String gameEnd = null;
        while (gameEnd == null){ //if the game is not over keep the loop
            System.out.println("Boneyard contains " + board.getBoneyard().
                    size() + " cards");
            System.out.print("Trey: " );
            board.printHumanTrey();
            System.out.println("Human's turn");
            System.out.println("Playboard:");
            board.printPlayBoard();
            if (board.getPlayBoard().isEmpty()){
                //if it is the first turn player can choose to play whatever
                firstTurn(scanner, board);
            }
            else {
                boolean legalMove;
                int index;
                String s1, s2 = "";
                if (board.getHumanPlayer().canPlayTile(board.getPlayBoard().
                    get(0).getLeftSide(), board.getPlayBoard().
                        get(board.getPlayBoard().size()-1)
                            .getRightSide()) == null){

                    board.drawCardsUntilCanPlay(board.getHumanPlayer());
                    System.out.println("Boneyard contains " +
                            board.getBoneyard().size() + "cards");
                    System.out.print("Trey: " );
                    board.printHumanTrey();
                }
                do{
                    System.out.println("which Domino do you want to play?");
                    index = scanner.nextInt();
                    if (index > board.getHumanPlayer().getHand().size() -1){
                        System.out.println("This index is out of bounds, choose"
                                + "and index you can play");
                        legalMove = false;
                    }
                    else {
                        System.out.println("Rotate the tile? y/n");
                        s1 = scanner.next();
                        if (s1.equals("y")) {
                            board.getHumanPlayer().getHand().get(index).
                                    flipSides();
                        }
                        System.out.println("From the left of to the right? l/r");
                        s2 = scanner.next();
                        legalMove = board.isMoveLegal(index, s2);
                        if (!legalMove) {
                            System.out.println("Move is illegal, try again");
                            if (s1.equals("y")) {
                                board.getHumanPlayer().getHand().get(index).
                                        flipSides();
                            }
                            System.out.print("Trey: ");
                            board.printHumanTrey();
                        }
                    }
                }while (!legalMove);
                board.playerTurn(index, s2);

            }
            gameEnd = board.isGameOver(board.getHumanPlayer());
            if (gameEnd != null) {
                if ((!board.getHumanPlayer().getHand().isEmpty()) &&
                        (!board.getComputerPlayer().getHand().isEmpty()) ){
                    System.out.println("Both players could not play");
                    System.out.println(gameEnd + " won as the sum of his tile " +
                            "is less");
                }
                break;
            }
            else {
                board.computerTurn();
                gameEnd = board.isGameOver(board.getHumanPlayer());
            }

        }
        System.out.println("Game Over, " + gameEnd);
    }
    private static void firstTurn(Scanner scanner, Board board){
        int index;
        boolean legalMove;
        do {
            System.out.println("You can play any Domino");
            System.out.println("Which Domino do you want to play?");
            index = scanner.nextInt();

            if (index > board.getHumanPlayer().getHand().size() -1){
                System.out.println("index out of range, try again");
                legalMove = false;
            }
            else {legalMove = true;}
        }while (!legalMove);
        board.getPlayBoard().add(board.getHumanPlayer().getHand().remove(index));
    }
}
