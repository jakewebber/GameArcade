compile : P4Arcade.java Tetris.java TicTacToe.java IPiece.java JPiece.java LPiece.java MouseClickListener.java OPiece.java PieceQueue.java SPiece.java TetrisPiece.java TPiece.java ZPiece.java

	javac P4Arcade.java Tetris.java TicTacToe.java IPiece.java JPiece.java LPiece.java MouseClickListener.java OPiece.java PieceQueue.java SPiece.java TetrisPiece.java TPiece.java ZPiece.java

run : P4Arcade.class
	java P4Arcade
clean : 
	rm *.class 
