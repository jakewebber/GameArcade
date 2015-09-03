GameArcade - Tic Tac Toe & Tetris
Created by Zach Saucier & Jake Webber
Last updated 4/11/2014
Version 1.0

NOTE: The Konami code initialization on the arcade homescreen can be a little finicky.
If it does not apply, try restarting the Arcade and, without moving the mouse, try
retyping the Konami code. A Konami code sequence notification should appear in the 
middle of the arcade screen if it was successful. The change remains active until the
arcade is closed and restarted completely. 

Purpose:
	This program was made to practice OOP, Swing, the use of stacks, and various
other Java and software development techniques.
IN A UNIX ENVIRONMENT: 
To compile each file to be ready to run, use the following command:
	$ make compile

To run this program use the following command:
	$ make run

To remove the class files (clean this program), use the following command:
	$ make clean
	
Project Optimized for Eclipse IDE for import.  



How to activeate the Konami code:

To activate the Konami code, one must start the arcade menu and, before doing
anything (it stops listening if certain buttons are hovered), enter the
following sequence of characters:
	up arrow, up arrow, down arrow, down arrow, left arrow, right arrow,
	left arrow, right arrow, b, a

====================================================================================
Tic Tac Toe
====================================================================================
Game objective:
	Make a line of three of your symbols in a row while preventing your opponent
from doing the same. Clicking on an already taken space will not work.

Controls:
	CLICK: Claim a blank space as yours - places your symbol on it
	CTRL+Z: Undo previous move
	CTRL+Y: Redo last un-done move
	CTRL+X: Exit game
	CTRL+R: Restart game
	CTRL+A: Exit to Arcade

Press `Help -> Gameplay Instructions` or `F1` for game instructions

====================================================================================
Tetris
====================================================================================
Game objective:
	Complete as many full lines as possible, preventing the blocks from reaching 
the top of the grid. Filling up several lines at once achieves more points. Simply 
placing a Tetrimo block at the bottom will add one point as well. The game will end 
after 200 Tetrimos have been used or the board ceiling is reached

Controls:
	UP: Rotate Tetrimino  90 degrees
	DOWN: Move Tetrimino  down
	LEFT: Move Tetrimino  left
	RIGHT: Move Tetrimino  right
	SPACEBAR: Pause game
	SHIFT: Moe Tetrimino down to next available space
	CTRL+X: Exit game
	CTRL+R: Restart game
	CTRL+A: Exit to Arcade
	ALT+0: Show nothing in the queue
	ALT+1: Show 1 Tetrimino in the queue
	ALT+2: Show 4 Tetriminos in the queue

Press `Help -> Gameplay Instructions` or `F1` for game instructions (pauses game)
