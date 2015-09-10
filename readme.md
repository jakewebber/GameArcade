Game Arcade - Tic Tac Toe & Tetris
=====================
<p>Created by Jacob Webber & Zach Saucier <br> Last updated 4/11/2014 <br> Version 1.0 <br>
![Alt text](https://raw.githubusercontent.com/jakewebber/GameArcade/f10a51c7bb947fd981145e2a8c9b9d823b6037be/mainscreenshot.jpg "Game Arcade")

Purpose:
	This program was made to practice OOP, Swing, the use of stacks, queues, and various
other Java and software development techniques.

IN A UNIX ENVIRONMENT: 
<p>To compile each file to be ready to run, use the following command: <br>
>	$ make compile	<br>

To run this program use the following command: <br>
>	$ make run <br>

To remove the class files (clean this program), use the following command: <br>
>	$ make clean <br>
	
Project Optimized for Eclipse IDE for import.  


====================================================================================
Tic Tac Toe
====================================================================================
<b>Game objective:</b>
	Make a line of three of your symbols in a row while preventing your opponent
from doing the same. Clicking on an already taken space will not work.

<b>Controls:</b>
	CLICK: Claim a blank space as yours - places your symbol on it <br>
	CTRL+Z: Undo previous move <br>
	CTRL+Y: Redo last un-done move <br>
	CTRL+X: Exit game <br>
	CTRL+R: Restart game <br>
	CTRL+A: Exit to Arcade <br>

Press `Help -> Gameplay Instructions` or `F1` for game instructions

====================================================================================
Tetris
====================================================================================
![Alt text](https://raw.githubusercontent.com/jakewebber/GameArcade/f10a51c7bb947fd981145e2a8c9b9d823b6037be/tetrisscreenshot.jpg "Tetris")

<b>Game objective:</b>
	Complete as many full lines as possible, preventing the blocks from reaching 
the top of the grid. Filling up several lines at once achieves more points. Simply 
placing a Tetrimo block at the bottom will add one point as well. The game will end 
after 200 Tetrimos have been used or the board ceiling is reached

<b>Controls:</b>
	UP: Rotate Tetrimino  90 degrees <br>
	DOWN: Move Tetrimino  down <br>
	LEFT: Move Tetrimino  left <br>
	RIGHT: Move Tetrimino  right <br>
	SPACEBAR: Pause game <br>
	SHIFT: Moe Tetrimino down to next available space <br>
	CTRL+X: Exit game <br>
	CTRL+R: Restart game <br>
	CTRL+A: Exit to Arcade <br>
	ALT+0: Show nothing in the queue <br>
	ALT+1: Show 1 Tetrimino in the queue <br>
	ALT+2: Show 4 Tetriminos in the queue <br>

Press `Help -> Gameplay Instructions` or `F1` for game instructions (pauses game) <br>

<b>How to activate the Konami code:</b>

To activate the Konami code, one must start the arcade menu and, before doing
anything (it stops listening if certain buttons are hovered), enter the
following sequence of characters:
	up arrow, up arrow, down arrow, down arrow, left arrow, right arrow,
	left arrow, right arrow, b, a

NOTE: The Konami code initialization on the arcade homescreen can be a little finicky.
If it does not apply, try restarting the Arcade and, without moving the mouse, try
retyping the Konami code. A Konami code sequence notification should appear in the 
middle of the arcade screen if it was successful. The change remains active until the
arcade is closed and restarted completely. 
