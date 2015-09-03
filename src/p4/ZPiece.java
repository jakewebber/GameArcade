package p4;

import java.awt.Color;

public class ZPiece extends TetrisPiece {

	ZPiece(char[][] values) {
		super.color = Color.red;
		super.currentPanelValues = values;
		init();
	}
	
	@Override
	public void init() {
		super.needNewPiece = false;
		
		super.pieceType = 'Z';
		
		// 1st block
		super.currentPanelValues[0][4] = 'X';
		// 2nd block
		super.currentPanelValues[0][5] = 'X';
		// 3rd block
		super.currentPanelValues[1][5] = 'X';
		// 4th block
		super.currentPanelValues[1][6] = 'X';
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
			// From horizontal to vertical
			if(numTurns % 2 == 0) {
				try {
					if((super.currentPanelValues[blocksToMove[0][0] - 1][blocksToMove[0][1] + 2] == '\u0000'
					|| super.currentPanelValues[blocksToMove[0][0] - 1][blocksToMove[0][1] + 2] == 'X')
					&& (super.currentPanelValues[blocksToMove[1][0] - 1][blocksToMove[1][1] + 1] == '\u0000'
					|| super.currentPanelValues[blocksToMove[1][0] - 1][blocksToMove[1][1] + 1] == 'X')
					&& (super.currentPanelValues[blocksToMove[3][0]    ][blocksToMove[3][1] - 1] == '\u0000'
					|| super.currentPanelValues[blocksToMove[3][0]    ][blocksToMove[3][1] - 1] == 'X')) {
						
						super.currentPanelValues[blocksToMove[0][0] - 1][blocksToMove[0][1] + 2] = 'X';
						super.currentPanelValues[blocksToMove[0][0]][blocksToMove[0][1]] = '\u0000';
						super.currentPanelValues[blocksToMove[1][0] - 1][blocksToMove[1][1] + 1] = 'X';
						super.currentPanelValues[blocksToMove[1][0]][blocksToMove[1][1]] = '\u0000';
						super.currentPanelValues[blocksToMove[3][0]    ][blocksToMove[3][1] - 1] = 'X';
						super.currentPanelValues[blocksToMove[3][0]][blocksToMove[3][1]] = '\u0000';
					}
				} catch (IndexOutOfBoundsException e) { // Handle failed turn
					numTurns--;
				}				
			} else { // From vertical to horizontal
				try {
					if((super.currentPanelValues[blocksToMove[1][0] + 1][blocksToMove[1][1]    ] == '\u0000'
					|| super.currentPanelValues[blocksToMove[1][0] + 1][blocksToMove[1][1]    ] == 'X')
					&& (super.currentPanelValues[blocksToMove[3][0] + 1][blocksToMove[3][1] - 2] == '\u0000'
					|| super.currentPanelValues[blocksToMove[3][0] + 1][blocksToMove[3][1] - 2] == 'X')) {
						
						super.currentPanelValues[blocksToMove[1][0] + 1][blocksToMove[3][1]    ] = 'X';
						super.currentPanelValues[blocksToMove[1][0]][blocksToMove[3][1]] = '\u0000';
						super.currentPanelValues[blocksToMove[3][0] + 1][blocksToMove[3][1] - 2] = 'X';
						super.currentPanelValues[blocksToMove[3][0]][blocksToMove[3][1]] = '\u0000';
					}
				} catch (IndexOutOfBoundsException e) { // Handle failed turn
					numTurns--;
				}				
			}
			numTurns++;
		} 
	}
}
