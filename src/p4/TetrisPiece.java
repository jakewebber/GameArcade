package p4;

import java.awt.Color;
import java.util.Arrays;

public abstract class TetrisPiece {
	protected char[][] panelValues = new char[20][10]; //[height][width]
	protected char[][] currentPanelValues = new char[20][10]; //[height][width]
	private int numMoveDown = 0;
	protected int numTurns = 0;
	//Keeping track of Konami code implementation
	private int waitingCount = 0;
	private int waitingColumn = -1;
	public boolean isWaiting = false; 
	
	public char pieceType = ' '; //Determines the color displayed in the tetris board
	protected Color color = Color.black;
	public boolean needNewPiece = true; //Determines whether a new piece is needed
	protected boolean canRotate = true; //Controls whether a piece is allowed to rotate

	public abstract void init();
	public abstract void rotate(char direction);
	
	/**--------------------------------------------------------------------------
	 * move(char) - Moves the current piece around the board
	 * @param char direction: The direction in which the piece will move.  */
	public void move(char direction) {
		int count = 0;
		int[][] blocksToMove = new int[4][2];
		
		if(direction == 'l') {
			// Check to see if can move left
			for(int j = 0; j < 10; j++) {
				for(int i = 19; i >= 0; i--) {
					if(currentPanelValues[i][j] == 'X') {
						try {
							if(currentPanelValues[i][j-1] == '\u0000' || currentPanelValues[i][j-1] == 'X') {
								// Save values to be moved later
								blocksToMove[count][0] = i;
								blocksToMove[count][1] = j;
								count++;
							} else {
								return; // MAKE IT STOP
							}
						} catch (IndexOutOfBoundsException e) {
							return; // MAKE IT STOP
						}
					}
				}
			}
			// If all pieces can move left, do so
			if(count == 4) {
				for(int i = 0; i < 4; i++) {
					currentPanelValues[blocksToMove[i][0]][blocksToMove[i][1] - 1] = 'X';
					currentPanelValues[blocksToMove[i][0]][blocksToMove[i][1]] = '\u0000';
				}
			}
		} else if(direction == 'r') {
			// Check to see if can move right
			for(int j = 9; j >= 0; j--) {
				for(int i = 19; i >= 0; i--) {
					if(currentPanelValues[i][j] == 'X') {
						try {
							if(currentPanelValues[i][j+1] == '\u0000' || currentPanelValues[i][j+1] == 'X') {
								// Save values to be moved later
								blocksToMove[count][0] = i;
								blocksToMove[count][1] = j;
								count++;
							} else {
								return; // MAKE IT STOP
							}
						} catch (IndexOutOfBoundsException e) {
							return; // MAKE IT STOP
						}
					}
				}
			}
			// If all pieces can move right, do so
			if(count == 4) {
				for(int i = 0; i < 4; i++) {
					currentPanelValues[blocksToMove[i][0]][blocksToMove[i][1] + 1] = 'X';
					currentPanelValues[blocksToMove[i][0]][blocksToMove[i][1]] = '\u0000';
				}
			}
		} else if(direction == 'd') {
			moveDown();
		}
		// Update array
	}
	
	/**--------------------------------------------------------------------------
	 * moveDown() - Moves the entire piece down a space.   */
	public void moveDown() {
		int count = 0;
		int[][] blocksToMove = new int[4][2];
		numMoveDown++;
		
		// check lowest row for currently moving block
		for(int i = 19; i >= 0; i--) {
			for(int j = 0; j < 10; j++) {
				// If space is 'X', move down
				if(currentPanelValues[i][j] == 'X') {
					try {
						if(currentPanelValues[i+1][j] == '\u0000' || currentPanelValues[i+1][j] == 'X') {
							// Save values to be moved later
							blocksToMove[count][0] = i;
							blocksToMove[count][1] = j;
							count++;
						} else {
							if(numMoveDown == 1) {
								Tetris.endGame(Tetris.score);
							} else {
								needNewPiece = true;
							}
							return; // MAKE IT STOP
						}
					} catch (IndexOutOfBoundsException e) {
						needNewPiece = true;
						return; // MAKE IT STOP
					}
				}
			}
			// Move up one row & repeat
		}
		// If all pieces can move down, do so
		if(count == 4) {
			for(int i = 0; i < 4; i++) {
				currentPanelValues[blocksToMove[i][0] + 1][blocksToMove[i][1]] = 'X';
				currentPanelValues[blocksToMove[i][0]][blocksToMove[i][1]] = '\u0000';
			}
		}
	}
	
	/**--------------------------------------------------------------------------
	 * finishAction() - handles board modifications like clearing the board and
	 * updating points. 
	 * @return char[][] array to be displayed as colored JPanels  */
	public char[][] finishAction() {
		// Convert piece to readable format by the board
		panelValues = new char[currentPanelValues.length][];
		for (int i = 0; i < currentPanelValues.length; i++) {
			panelValues[i] = Arrays.copyOf(currentPanelValues[i], currentPanelValues[i].length );
	    }
		
		for(int i = 19; i >= 0; i--) {
			for(int j = 0; j < 10; j++) {
				// If space below is x or " ", move down
				if(panelValues[i][j] == 'X') {
					panelValues[i][j] = pieceType;
				}
			}
		}
		
		if(needNewPiece) {
			int scoreCounter = -1;
			// Remove any full lines, move all pieces above down in response
			for(int i = 19; i >= 0; i--) {
				int fullCount = 0;
				for(int j = 0; j < 10; j++) {
					if(panelValues[i][j] != 'X' && panelValues[i][j] != '\u0000') {
						fullCount++;
					}
				}
				
				// Check Konami code
				if(P4Arcade.hasKonami) {
					if(!isWaiting) {
					if(fullCount == 9) {
						for(int j = 0; j < 10; j++) {
							if(!isWaiting) {
								if(panelValues[i][j] == '\u0000') {
									if(waitingColumn == -1) { // If there is no previous row of 9, initialize this empty space as the mark
										waitingColumn = j;
										waitingCount = 1;
									} else if(waitingColumn == j) { // Increment the count if the space matches the previous empty space
										waitingCount++;
									} else { // Reset if the space does match the previous empty space
										waitingColumn = -1;
										waitingCount = 0;
										isWaiting = false;
									}
									
									if(waitingCount == 4) {
										isWaiting = true;
									}
								}
							}
						}
					} else {
						waitingColumn = -1;
						waitingCount = 0;
						isWaiting = false;
					}
					}
				}
				
				// Remove full rows
				if(fullCount == 10) {
					Tetris.addScore(100); // Add 100 points for clearing row
					scoreCounter++; // Increment the score multiplier
					Tetris.linesCleared++; // Increment total lines cleared
					// Reset row values
					for(int j = 0; j < 10; j++) {
						panelValues[i][j] = '\u0000';
					}
					int tempI = i;
					// Move entire panel down
					while(tempI > 0) {
						for(int j = 0; j < 10; j++) {
							panelValues[tempI][j] = panelValues[tempI-1][j];
						}
						tempI--;
					}
					i++; // Check the line that just moved down
				}	
			}
			// Applying score multiplier for clearing multiple rows at once
			for(int i = 1; i < scoreCounter; i++) {
				Tetris.addScore(100);
			}
		}
		return panelValues;
	}	
}
