<h1>Othello</h1>

<h3>Gameplay</h3>
Othello, also known as <a href="http://en.wikipedia.org/wiki/Reversi">Reversi</a>,  is a two person turn-based strategy game in which opponents take turns placing black and white pieces on an eight by eight grid.  A player can only place a piece in an empty square such that there exists at least one straight (horizontal, vertical, or diagonal) occupied line between the new piece and another piece of the same color, with one or more contiguous pieces of the opposite color between them.  The player with the most pieces when there are no more valid moves is the winner!

<h3>Implementation</h3>
This implementation of Othello is fairly basic and straight forward to operate.  However, in addition to allowing human vs human play, this game offers the ability to play against a computer or even to have two computers play against each other.  There are three levels of computer play that increase in difficulty.  The first level only takes into account the next move while level two and three take into account subsequent moves.  It should be noted the AI implementation is a bit primitive and is largely based off of a manually defined weighting.  In this implementation all squares are assigned equal value, though in reality different squares are more desirable.

<h5>Developers</h5>
For those interested in deconstructing the code base, the bulk of the functionality is located in the ```Gameboard``` class  and ```Square``` subclasses.  The logic behind playing the game and displaying the gameboard is fairly decoupled, though there is an intersection in the ```Gameboard``` class.  It should also be noted that all ```Square``` objects are 'intelligent' in that they are self-aware and can be queried as to their current state.

<h3>Executables</h3>

This repository contains an executable jar for those looking for an easy way to run the program without setting up the source code.  In order to run the jar (assuming a valid JDK is installed), simply navigate to the folder containing the jar and run:
```
java -jar othello.jar
```
.

<h3>Renders</h3>
<h5>Human vs Computer (Level 1)</h5>
![Initial](/images/hVb1/h-vs-b1-1.png "Initial")

![Final](/images/hVb1/h-vs-b1-final.png "Final")

<h5>Computer (Level 1) vs Computer (Level 3)</h5>
![Initial](/images/w1Vb3/w1-vs-b3-1.png "Initial")

![Mid-game](/images/w1Vb3/w1-vs-b3-2.png "Mid-game")

![Final](/images/w1Vb3/w1-vs-b3-final.png "Final")

<h5>Computer (Level 2) vs Computer (Level 2)</h5>
![Initial](/images/w2Vb2/w2-vs-b2-1.png "Initial")

![Mid-game](/images/w2Vb2/w2-vs-b2-2.png "Mid-game")

![Final](/images/w2Vb2/w2-vs-b2-final.png "Final")

<h2>Good luck!</h2>

