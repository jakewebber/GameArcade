package p4;

import java.awt.Color;

public class OPiece extends TetrisPiece {
	
	OPiece(char[][] values) {
		super.color = Color.yellow;
		super.currentPanelValues = values;
		init();
	}
	
	@Override
	public void init() {
		super.needNewPiece = false;
		
		super.pieceType = 'O';
		
		// 1st block
		super.currentPanelValues[0][4] = 'X';
		// 2nd block
		super.currentPanelValues[0][5] = 'X';
		// 3rd block
		super.currentPanelValues[1][4] = 'X';
		// 4th block
		super.currentPanelValues[1][5] = 'X';
	}

	@Override
	public void rotate(char direction) {
		// Do nothing because it's a square
	}

}
