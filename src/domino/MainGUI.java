package domino;

import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import java.util.ArrayList;

/** Author: Gal Arad
 * MainGUI doing the and runs the GUI version graphical user interface for a
 * domino game application.
 * It sets up the game board, player hand display, and controls for
 * player interaction,
 */
public class MainGUI extends Application {
    private Board board;
    private HBox playerHandDisplay;
    private VBox gameBoardDisplay2;
    private DominoTile selectedTile = null;
    private Pane selectedTilePane = null;
    private String playSide = "l";
    private final Text COMPUTER_CARDS_TEXT = new Text();
    private final Text BONEYARD_COUNT_TEXT = new Text();


    /**
     * Starts the application, setting up the primary stage, scene,
     * and UI components.
     * Initializes the game board managing interactions with the player, and
     * dealing with appropriate consequences for those interactions
     * @param primaryStage The primary window of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        board = new Board();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1500, 770);
        root.setBackground(Background.fill(Color.BEIGE));

        primaryStage.setTitle("Domino Game");
        primaryStage.setScene(scene);

        updateBoneyardCountText();
        playerHandDisplay = new HBox(5);
        playerHandDisplay.setAlignment(Pos.CENTER);
        updatePlayerHand();

        gameBoardDisplay2 = new VBox(5);
        gameBoardDisplay2.setAlignment(Pos.CENTER);
        updateGameBoard2();
        updateComputerCardsText();

        Text directionText = new Text("Play tile on the: ");
        ToggleButton toggleButton = new ToggleButton("Left");
        toggleButton.setOnAction(e -> leftOrRight(toggleButton));

        Button cantPlay = new Button("I can't play or draw!");
        cantPlay.setOnAction(e -> playerCantPlay(root));

        Button flipSides = new Button("Flip Sides");
        flipSides.setOnAction(e -> flipSides());

        Button drawButton = new Button("Draw");
        drawButton.setOnAction(e -> drawButton(root));

        Button playButton = new Button("Play");
        playButton.setOnAction(e -> playButton(root));

        HBox actionButtons = new HBox(10, cantPlay, drawButton,
                playButton, flipSides,directionText, toggleButton ,
                COMPUTER_CARDS_TEXT, BONEYARD_COUNT_TEXT );
        actionButtons.setAlignment(Pos.CENTER);

        VBox bottomLayout = new VBox(10, actionButtons,
                playerHandDisplay);
        bottomLayout.setAlignment(Pos.CENTER);

        root.setBottom(bottomLayout);
        root.setCenter(gameBoardDisplay2);

        primaryStage.setTitle("Domino Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void playButton(BorderPane root){
        if (!board.getHumanPlayer().getHand().isEmpty() && selectedTile !=
                null && !checkForGameOver(root, board.getHumanPlayer())) {

            int index = board.getHumanPlayer().getHand().
                    indexOf(selectedTile);

            if (board.isMoveLegal(index, playSide)) {
                board.playerTurn(index, playSide);
                selectedTile = null;
                selectedTilePane = null;
                updatePlayerHand();
                updateGameBoard2();
                if (!checkForGameOver(root,board.getHumanPlayer())) {
                    board.computerTurn();
                    updateComputerCardsText();
                    updateGameBoard2();
                    updateBoneyardCountText();
                    checkForGameOver(root,board.getComputerPlayer());
                }
            }
        }
    }
    private void drawButton(BorderPane root){
        if (!board.getPlayBoard().isEmpty()){
            int[] sides = board.getPlayBoardSides();
            if (!board.getBoneyard().isEmpty() && board.getHumanPlayer().
                    canPlayTile(sides[0],sides[1]) == null) {

                board.getHumanPlayer().drawTile(board.getBoneyard().
                        remove(0));
                updatePlayerHand();
                updateGameBoard2();
                updateBoneyardCountText();

            }
            if (board.getBoneyard().isEmpty() && board.getHumanPlayer().
                    canPlayTile(sides[0],sides[1]) == null){
                board.addToCountNoPlay();
                if (!checkForGameOver(root, board.getHumanPlayer())){
                    board.computerTurn();
                    updateGameBoard2();
                    updateComputerCardsText();
                    updateBoneyardCountText();
                    checkForGameOver(root,board.getComputerPlayer());
                }
            }
        }
    }
    private void leftOrRight(ToggleButton toggleButton){
        if (toggleButton.isSelected()) {
            toggleButton.setText("Right");
            playSide = "r";

        } else {
            toggleButton.setText("Left");
            playSide = "l";
        }
    }
    private void flipSides(){
        if (selectedTile != null){
            int index = board.getHumanPlayer().getHand().
                    indexOf(selectedTile);
            board.getHumanPlayer().getHand().get(index).flipSides();
            updatePlayerHand();
            updateGameBoard2();
            selectedTile = null;
            selectedTilePane = null;
        }
    }

    private void playerCantPlay(BorderPane root) {
        if (!board.getPlayBoard().isEmpty()) {
            int[] sides = board.getPlayBoardSides();
            if (board.getHumanPlayer().canPlayTile(sides[0], sides[1]) == null
                    && board.getBoneyard().isEmpty() &&
                    !board.getPlayBoard().isEmpty()) {

                board.addToCountNoPlay();
                if (!checkForGameOver(root, board.getHumanPlayer())) {
                    board.computerTurn();
                    checkForGameOver(root, board.getComputerPlayer());
                }
            }
        }
    }

    private Boolean checkForGameOver(BorderPane root, Player turn){
        String winner = board.isGameOver(turn);
        if (winner != null){
            Text text = new Text();
            text.setText("Game Over \n" + winner);
            text.setX(150);
            text.setY(150);
            root.getChildren().add(text);
            return true;
        }
        return false;
    }
    private void updatePlayerHand(){
        createTiles( board.getHumanPlayer().getHand(), playerHandDisplay);
    }

    private void createTiles(ArrayList<DominoTile> ls, HBox box) {
        box.getChildren().clear();
        ls.forEach(tile -> {
            Pane tilePane = new Pane();
            tilePane.setPrefSize(97.5, 52.5);
            Rectangle rectangle = new Rectangle(90, 45);
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.BLACK);
            Line line = new Line(45, 0, 45, 45);
            tilePane.getChildren().addAll(rectangle, line);

            drawDots(tile.getLeftSide(), tilePane, true);
            drawDots(tile.getRightSide(), tilePane, false);

            tilePane.setOnMouseClicked(e -> {
                if (selectedTile != tile) {
                    if (selectedTilePane != null) {
                        ((Rectangle)selectedTilePane.getChildren().get(0))
                                .setStroke(Color.BLACK);
                    }
                    selectedTile = tile;
                    selectedTilePane = tilePane;
                    ((Rectangle)selectedTilePane.getChildren().get(0))
                            .setStroke(Color.RED);
                } else {
                    ((Rectangle)selectedTilePane.getChildren().get(0))
                            .setStroke(Color.BLACK);
                    selectedTile.flipSides();
                    selectedTile = null;
                    selectedTilePane = null;
                    updatePlayerHand();
                    updateGameBoard2();
                }
            });
            box.getChildren().add(tilePane);
        });
    }
    private void drawDots(int number, Pane pane, boolean isLeftSide) {
        for (int i = 0; i < number; i++) {
            Circle dot = new Circle(4, Color.BLACK);
            double x;
            double y = 7.5 + (i / 2) * 15;
            if (isLeftSide) {
                if (i % 2 == 1) {
                    x = 30;
                }
                else{
                    x = 15;
                }
            } else {
                x = 52.5 + (i % 2) * 15;
            }
            dot.setCenterX(x);
            dot.setCenterY(y);
            pane.getChildren().add(dot);
        }
    }
    private void updateComputerCardsText() {
        COMPUTER_CARDS_TEXT.setText("Computer has " +
                board.getComputerPlayer().getHand().size() + " cards");
    }
    private Pane createSingleBoardRec(int num){
        DominoTile tile = board.getPlayBoard().get(num);
        Pane tilePane = new Pane();
        tilePane.setPrefSize(97.5, 52.5);
        Rectangle rectangle = new Rectangle(90, 45);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        Line line = new Line(45, 0, 45, 45);
        tilePane.getChildren().addAll(rectangle, line);

        drawDots(tile.getLeftSide(), tilePane, true);
        drawDots(tile.getRightSide(), tilePane, false);
        return tilePane;
    }
    private void updateGameBoard2(){
        HBox topBox = new HBox(15);
        HBox bottomBox = new HBox(15);
        Rectangle rectangle = new Rectangle(45,45);
        rectangle.setFill(Color.BEIGE);
        bottomBox.getChildren().add(rectangle);
        gameBoardDisplay2.getChildren().clear();
        for (int i = 0; i < board.getPlayBoard().size(); i ++){
            if (i%2==0){
                topBox.getChildren().add(createSingleBoardRec(i));
            }
            else {
                bottomBox.getChildren().add(createSingleBoardRec(i));
            }
        }
        gameBoardDisplay2.getChildren().addAll(topBox,bottomBox);
    }
    private void updateBoneyardCountText() {
        BONEYARD_COUNT_TEXT.setText("Tiles in boneyard: " + board.getBoneyard().size());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
