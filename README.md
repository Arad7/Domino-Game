# Domino game

##  Introduction 
This is a human-versus-computer-player game of dominoes.
A Domino has two sides and a number between 0 and 6 on each side. There are only
one variation of each domino, and there are 28 dominos total.
Each player starts the game with seven dominoes; the remaining 14 are in the
boneyard (the deck).
On each turn, the player downs a single domino, matching
the configuration on the table, while a 0 or blank in the GUI version is used as
a wild card. If the player has a domino, that can extend the line of play.
in either direction, they put it down, and their turn ends.
If the player does not have a domino able to extend the line of play,
They must pick one up from the boneyard and continue to do so until
Either a matching domino is found or the boneyard is empty.
If the player has a domino able to extend the line of play, they cannot pick
any pieces from the boneyard.
If the boneyard is empty and the player cannot extend the line of play, they
end their turn.

### Winning 
The game ends when the boneyard is empty and either
– The current player places their last domino.
– Both players have taken a turn without placing a domino.
• At the end of the game, the dots on each player’s dominos are counted and 
the player with the lower total wins. (This means that is one player ends the 
game without any dominos, they’re pretty much guaranteed to be the winner.)
If both players have the same number of dots, the player who last played a 
domino is the winner.

The part "The current player places their last domino." means that there could
be a scenario where humans have one card, there is one card in the boneyard, and
a computer has 0 cards.
In this scenario, if a human plays his card (and stays with 0), the game is not 
over. because there is still one more card in the boneyard. Since the computer
has no cards, it takes the cards from the boneyard. If the computer can play
this card, he wins. as according to the rules: "If the player does not have a 
domino able to extend the line of play. They must pick one up from the boneyard
and continue to do so". 
Because it is technically still a computer game, it wins.


### GUI Specifics:
To play a tile, the user needs to click on the tile. Then he can choose if
The user wants to play the tile from the left or from the right through the
buttons at the top.If nothing is pressed, it will play what the bottle is 
currently on. If the move is not legal, nothing will happen. 
If a user wants to flip the tile (switch the sides), he can do it in two ways :)
Option one: click on the tile and click "flip sides." Option 2: double-click in
the tile he wants to flip. Both options will reset the selected tile to null.
If you can play a tile, you must do so. If you cannot, the user should press
"draw". This will add a card to the user's hand. He will need to keep pressing.
"draw" until he can play a card.

*If a player cannot play and the boneyard is empty, he will press 
"cannotPlayOrDraw" bottom which will update the counter of turns without play
and will keep the game going (or end it if it got to 2).

* If you make the screen smaller, the tiles visuals get messy.
* In the GUI version the console prints what the computer is doing, but I wanted
keep it because it can help see what the computer did and I think it adds to 
the game


### Console Game Specifics
Instead of clicking, you press on the keyboard. If you can play, it will ask you
what Domino you want to play, and you need to write the index (starting from 0).
Then it will ask if you want to rotate the tile.
If you press 'y', it will count as yes; anything else will be counted as no.
It will then go right or left. If the user presses 'l', it will be counted as
left; anything else will be counted as right. 
If the move is not legal, it will tell you, and you do the process again. 
If you cannot play a card, it will a card automatically for you, and will 
keep drawing automatically until you can Play a card if the boneyard is empty.