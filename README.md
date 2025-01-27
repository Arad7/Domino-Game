# Domino game

## Introduction  
This is a **human-versus-computer domino game**.  
Each domino has two sides, with a number between **0 and 6** on each side. There is only one variation of each domino, resulting in a total of **28 dominoes**.  

At the start of the game:  
- Each player is dealt **7 dominoes**.  
- The remaining **14 dominoes** are placed in the **boneyard** (the deck).  

### Gameplay  
- On their turn, a player places a domino that matches the configuration of the tiles on the table.  
- **Blank tiles (0s)** act as wild cards.  
- If the player has a valid move, they must play a domino to extend the line of play.  
- If no valid move is possible:  
  - The player draws from the boneyard until they find a matching domino or the boneyard is empty.  
  - If the boneyard is empty and no move is possible, the player's turn ends.  
- Players **cannot draw from the boneyard** if they already have a playable domino.

## Winning the Game  
The game ends when:  
1. The **boneyard is empty**, and either:  
   - A player places their last domino.  
   - Both players take turns without placing a domino.  

### Scoring  
- At the end of the game, the dots on each player’s remaining dominoes are counted.  
- The player with the **lower total dots** wins.  
- If a player has **no dominoes left**, they are guaranteed to win.  
- In the case of a tie (same number of dots), the player who last placed a domino is declared the winner.  

### Special Scenario  
If a human player has one domino left, the computer has none, and there’s one domino remaining in the boneyard:  
- The human plays their last domino but does **not win** because the computer will draw the last domino from the boneyard.  
- If the computer can play the domino it drew, it wins, as per the game rules.  

 ---

## GUI Version  
### How to Play  
- **Placing a Tile**:  
  - Click on a tile, then choose whether to play it on the **left** or **right** using buttons at the bottom (the above the player domino's).  
  - If no button is selected, the move defaults to the side the bottom is currently on.  
  - If the move is **illegal**, nothing happens.  

- **Flipping a Tile**:  
  - Option 1: Click the tile, then click the "Flip Sides" button.  
  - Option 2: Double-click the tile to flip it.  

- **Drawing Tiles**:  
  - If you cannot play, press **"Draw"** to add a domino to your hand. Continue pressing until you can play.  

- **No Moves Available**:  
  - If the boneyard is empty and no moves are possible, press **"Cannot Play or Draw"** to update the turn counter. If both players cannot play for 2 turns, the game ends.  

### Notes:  
- **Visuals**: Resizing the screen can cause tiles to display incorrectly.  
- **Console Output**: The GUI version prints the computer’s moves in the console for clarity and adds to the overall experience.  

---

## Console Version  
### How to Play  
- **Placing a Tile**:  
  - When prompted, type the **index** of the domino you want to play (starting from 0).  
  - The program will then ask if you want to rotate the tile.  
    - Press `y` for yes or any other key for no.  
  - Finally, specify the placement direction:  
    - Press `l` for left or any other key for right.  

- **Illegal Moves**:  
  - If the move is not valid, you’ll be notified and prompted to try again.  

- **Drawing Tiles**:  
  - If you cannot play, the program will automatically draw tiles for you until a valid move is possible.  
  - If the boneyard is empty, your turn will end.  
