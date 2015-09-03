package p4;

import java.awt.Color;

public class LPiece extends TetrisPiece {

	LPiece(char[][] values) {
		super.color = new Color(255, 140, 0);
		super.currentPanelValues = values;
		init();
	}
	
	@Override
	public void init() {
		super.needNewPiece = false;
		
		super.pieceType = 'L';
		
		// 1st block
		super.currentPanelValues[0][4] = 'X';
		// 2nd block
		super.currentPanelValues[1][4] = 'X';
		// 3rd block
		super.currentPanelValues[2][4] = 'X';
		// 4th block
		super.currentPanelValues[2][5] = 'X';
	}

	@Override
	public void rotate(char direction) {
		int count = 0;
		int[][] blocksToMove = new int[4][2];
		
		for(int j = 0; j < 10; j++) {
			for(int i = 19; i >= 0; i--) {
				if(count < 4) {
					if(super.currentPanelValues[i][j] == 'X') {
						blocksToMove[count][0] = i;
						blocksToMove[count][1] = j;
						count++;
					}
				}
			}
		}
		if(direction == 'c') {
			// Default
			if(numTurns % 4 == 0) {
				try {
					if(super.currentPanelValues[blocksToMove[3][0] - 1][blocksToMove[3][1] + 1] == '\u0000'
					&& super.currentPanelValues[blocksToMove[2][0] + 1][blocksToMove[2][1] + 1] == '\u0000') {
						
						super.currentPanelValues[blocksToMove[3][0] - 1][blocksToMove[3][1] + 1] = 'X';
						super.currentPanelValues[blocksToMove[3][0]][blocksToMove[3][1]] = '\u0000';
						super.currentPanelValues[blocksToMove[2][0] + 1][blocksToMove[2][1] + 1] = 'X';
						super.currentPanelValues[blocksToMove[2][0]][blocksToMove[2][1]] = '\u0000';
					}	
				} catch (IndexOutOfBoundsException e) { // Handle failed turn
					numTurns--;
				}				
			} else if(numTurns % 4 == 1) { // Second state
				try {
					if(super.currentPanelValues[blocksToMove[0][0]    ][blocksToMove[0][1] + 1] == '\u0000'
					&& super.currentPanelValues[blocksToMove[1][0] - 1][blocksToMove[2][1]    ] == '\u0000'
					&& super.currentPanelValues[blocksToMove[3][0] - 1][blocksToMove[1][1] - 1] == '\u0000') {
						
						super.currentPanelValues[blocksToMove[0][0]    ][blocksToMove[0][1] + 1] = 'X';
						super.currentPanelValues[blocksToMove[0][0]][blocksToMove[0][1]] = '\u0000';
						super.currentPanelValues[blocksToMove[1][0] - 1][blocksToMove[1][1]    ] = 'X';
						super.currentPanelValues[blocksToMove[1][0]][blocksToMove[1][1]] = '\u0000';
						super.currentPanelValues[blocksToMove[3][0] - 1][blocksToMove[3][1] - 1] = 'X';
						super.currentPanelValues[blocksToMove[3][0]][blocksToMove[3][1]] = '\u0000';
					} 	
				} catch (IndexOutOfBoundsException e) { // Handle failed turn
					numTurns--;
				}				
			} else if(numTurns % 4 == 2) { // Third state
				try {
					if(super.currentPanelValues[blocksToMove[0][0] + 2][blocksToMove[0][1]    ] == '\u0000' 
					&& super.currentPanelValues[blocksToMove[3][0] + 2][blocksToMove[3][1] - 2] == '\u0000') {
						
						super.currentPanelValues[blocksToMove[0][0] + 2][blocksToMove[0][1]    ] = 'X';
						super.currentPanelValues[blocksToMove[0][0]][blocksToMove[0][1]] = '\u0000';
						super.currentPanelValues[blocksToMove[3][0] + 2][blocksToMove[3][1] - 2] = 'X';
						super.currentPanelValues[blocksToMove[3][0]][blocksToMove[3][1]] = '\u0000';
					} 
				} catch (IndexOutOfBoundsException e) { // Handle failed turn
					numTurns--;
				}				
			} else if(numTurns % 4 == 3) { // Fourth state
				try {
					if((super.currentPanelValues[blocksToMove[0][0]    ][blocksToMove[0][1] - 2] == '\u0000'
						|| super.currentPanelValues[blocksToMove[0][0]    ][blocksToMove[0][1] - 2] == 'X')
					&& (super.currentPanelValues[blocksToMove[1][0] - 1][blocksToMove[1][1]    ] == '\u0000'
						|| super.currentPanelValues[blocksToMove[1][0] - 1][blocksToMove[1][1]    ] == 'X')
					&& (super.currentPanelValues[blocksToMove[2][0]    ][blocksToMove[2][1] + 1] == '\u0000'
						|| super.currentPanelValues[blocksToMove[2][0]    ][blocksToMove[2][1] + 1] == 'X')
					&& (super.currentPanelValues[blocksToMove[3][0] - 1][blocksToMove[3][1] - 1] == '\u0000'
						|| super.currentPanelValues[blocksToMove[3][0] - 1][blocksToMove[3][1] - 1] == 'X')) {
						
						super.currentPanelValues[blocksToMove[3][0] - 1][blocksToMove[3][1] - 1] = 'X';
						super.currentPanelValues[blocksToMove[3][0]][blocksToMove[3][1]] = '\u0000';
						super.currentPanelValues[blocksToMove[2][0] - 1][blocksToMove[2][1] - 1] = 'X';
						super.currentPanelValues[blocksToMove[2][0]][blocksToMove[2][1]] = '\u0000';
						super.currentPanelValues[blocksToMove[1][0]    ][blocksToMove[1][1] + 1] = 'X';
						super.currentPanelValues[blocksToMove[1][0]][blocksToMove[1][1]] = '\u0000';
						super.currentPanelValues[blocksToMove[0][0]    ][blocksToMove[0][1] + 1] = 'X';
						super.currentPanelValues[blocksToMove[0][0]][blocksToMove[0][1]] = '\u0000';				
						
					}
				} catch (IndexOutOfBoundsException e) { // Handle failed turn
					numTurns--;
				}				
			}
			numTurns++;
		} 
	}
}
