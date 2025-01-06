package domino;

/**
 * Author: Gal Arad
 * Represents a single domino tile with two sides, each having a number (int).
 * Allows to flip the sides, print it nicely and return the values of the
 * right and left sides of the tile.
 */
public class DominoTile {
    private int rightSide;
    private int leftSide;

    /**
     * Constructs a DominoTile object with specified values for the left and
     * right sides.
     * @param val1 The value for the right side of the domino tile.
     * @param val2 The value for the left side of the domino tile.
     */
    public DominoTile(int val1, int val2){
        rightSide = val1;
        leftSide = val2;
    }

    /**
     * Returns the value of the right side of the domino tile.
     */
    public int getRightSide() { return rightSide; }

    /**
     * Returns the value of the left side of the domino tile.
     */
    public int getLeftSide() { return leftSide; }

    /**
     * Flips the sides of the domino tile, swapping the left and right side values.
     * @return The tile itself after flipping it sides
     */
    public DominoTile flipSides(){
        int placeHolder = rightSide;
        rightSide = leftSide;
        leftSide = placeHolder;
        return this;
    }

    /**
     * Returns a string representation of the domino tile,
     * with values of the left and right sides.
     */
    @Override
    public String toString() {
        return "[" + leftSide + " " + rightSide + "]";
    }
}

