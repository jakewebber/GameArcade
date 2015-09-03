package p4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Tetris {
	//Main Titles and Labels
	private static JFrame frame = new JFrame("Tetris");
	private static 	JLabel mainTitle = new JLabel("Tetris");
	private static ImageIcon tetrisBanner;
	private static JLabel statsTitle = new JLabel("Player Stats");
	private static JLabel cueTitle = new JLabel("Piece Queue");
	private static JPanel stats = new JPanel();
	private static JPanel board = new JPanel();
	private static JPanel header = new JPanel();
	private static JPanel[][] panelHolder = new JPanel[0][0]; //Board grid display
	private static char[][] panelValues = new char[0][0];
	private static JLabel scoreTitle = new JLabel("Your Score:");
	private static JLabel scoreboard = new JLabel();
	private static JLabel highScoreTitle = new JLabel("High Score:");
	private static JLabel highScoreLabel = new JLabel();
	private static JLabel piecesLeftTitle = new JLabel("Pieces Left:");
	private static JLabel piecesLeftLabel = new JLabel();
	private static JLabel linesTitle = new JLabel("Lines Cleared:");
	private static JLabel linesLabel = new JLabel();
	//Konami
	private static JLabel konamiLabel = new JLabel();
	private static ImageIcon konamiBanner;
	//MAIN MENU COMPONENTS
	//-----------------------------------------------
	private static JMenuBar menuBar = new JMenuBar();
	private static JMenu optionsMenu = new JMenu("Options");
	private static JMenu view = new JMenu("View");
	private static JMenu helpMenu = new JMenu("Help");
	//Help menu items
	private static JMenuItem instructions = new JMenuItem("Gameplay Instructions");
	//Options menu items
	private static JMenuItem exit = new JMenuItem("Exit");
	private static JMenuItem restart = new JMenuItem("Restart");
	private static JMenuItem arcade = new JMenuItem("Arcade Home");
	private static JMenuItem playpause = new JMenuItem("Pause the game");
	private static JMenuItem noCue = new JMenuItem("Show no pieces ahead");
	private static JMenuItem onePieceCue = new JMenuItem("Show 1 piece ahead");
	private static JMenuItem fourPieceCue = new JMenuItem("Show 4 pieces ahead");
	private static JSlider delaySlider = new JSlider(200, 2200, 1000);
	//CUE MENU COMPONENTS
	//-----------------------------------------------
	private static JPanel cue = new JPanel(); //In Frame East
	private static JPanel piece1Panel = new JPanel();
	private static JPanel piece2Panel = new JPanel();
	private static JPanel piece3Panel = new JPanel();
	private static JPanel piece4Panel = new JPanel();
	private static JLabel piece1Label = new JLabel();
	private static JLabel piece2Label = new JLabel();
	private static JLabel piece3Label = new JLabel();
	private static JLabel piece4Label = new JLabel();
	//GAME VARIABLES
	//-----------------------------------------------
	private static int width = 10;
	private static int height = 20;
	private static int delay = 1000; //Initial timer in milliseconds
	private static boolean gameStarted = false;
	private static boolean firstNewPiece = true;
	private static boolean paused = false;
	public static int score = 0;
	private static int bonus = 10;
	public static int linesCleared = 0;
	private static int piecesLeft = 200;
	private static int highScore = 0;
	private static Writer wr;
	private static Timer timer = new Timer(delay, null);
	//Pieces
	private static TetrisPiece piece;
	private static PieceQueue pieceQueue = new PieceQueue();
	private static int newPiece = 0;
	private static int showPiece1 = 0;
	private static int showPiece2 = 0;
	private static int showPiece3 = 0;
	private static int showPiece4 = 0;

	private static ActionListener MAListener;
	private static boolean myIsWaiting = false;

	//Starting Main method
	//-----------------------------------------------------------------------------
	public static void main(String[] args) {
		try {			
			//SETTING UP MAIN FRAME
			//-------------------------------------------------------------------
			frame.setMinimumSize(new Dimension(650, 800));
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(header, BorderLayout.NORTH);
			frame.add(stats, BorderLayout.WEST);
			frame.add(board, BorderLayout.CENTER);
			frame.add(cue, BorderLayout.EAST);
			board.setBackground(Color.black);

			//SETTING UP STATS PANEL
			//-------------------------------------------------------------------
			stats.setBackground(Color.lightGray);
			statsTitle.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 25));
			stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
			stats.add(statsTitle);
			
			///SETTING UP QUEUE PANEL
			//-------------------------------------------------------------------
			cue.setBackground(Color.lightGray);
			cue.setMinimumSize(new Dimension(800, 600));
			cue.setAlignmentX(Component.CENTER_ALIGNMENT);
			cueTitle.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 24));
			cue.add(cueTitle);
			cue.setLayout(new BoxLayout(cue, BoxLayout.Y_AXIS));
			//Queue panel 1
			piece1Panel.setLayout(new BoxLayout(piece1Panel, BoxLayout.Y_AXIS));
			piece1Panel.add(Box.createGlue());
			piece1Panel.add(new JLabel("Next Piece"));
			piece1Panel.add(piece1Label);
			piece1Panel.setBackground(new Color(223, 255, 255));
			cue.add(piece1Panel);
			cue.add(Box.createRigidArea(new Dimension(0,20)));
			//Queue panel 2
			piece2Panel.setLayout(new BoxLayout(piece2Panel, BoxLayout.Y_AXIS));
			piece2Panel.add(Box.createGlue());
			piece2Panel.add(new JLabel("2nd Next Piece"));
			piece2Panel.add(piece2Label);
			cue.add(piece2Panel);
			cue.add(Box.createRigidArea(new Dimension(0,20)));
			//Queue panel 3
			piece3Panel.setLayout(new BoxLayout(piece3Panel, BoxLayout.Y_AXIS));
			piece3Panel.add(Box.createGlue());
			piece3Panel.add(new JLabel("3rd Next Piece"));
			piece3Panel.add(piece3Label);
			cue.add(piece3Panel);
			cue.add(Box.createRigidArea(new Dimension(0,20)));
			//Queue panel 4
			piece4Panel.setLayout(new BoxLayout(piece4Panel, BoxLayout.Y_AXIS));
			piece4Panel.add(Box.createGlue());
			piece4Panel.add(new JLabel("4th Next Piece"));
			piece4Panel.add(piece4Label);
			cue.add(piece4Panel);
			mainTitle.setFont(new Font("Helvetica", Font.ITALIC, 30));
			
			//SETTING UP HEADER
			//-------------------------------------------------------------------
			try {
				tetrisBanner = new ImageIcon(ImageIO.read(new File("tetrispieces/tetristitle.png")));
			} catch (IOException e1) {
				System.out.println("Tetris banner image not found");
			}
			mainTitle = new JLabel(tetrisBanner);
			header.setBackground(Color.lightGray);
			header.add(mainTitle);
			frame.setLocationRelativeTo(null); // Center the frame
			
			//SETTING UP STATS
			//-------------------------------------------------------------------
			highScore = getHighScore();
			scoreboard.setText(String.valueOf(score));
			highScoreLabel.setText(String.valueOf(highScore));
			scoreTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
			scoreboard.setFont(new Font("Helvetica", Font.PLAIN, 20));
			highScoreTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
			highScoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
			piecesLeftTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
			piecesLeftLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
			piecesLeftLabel.setText(Integer.toString(piecesLeft));
			linesTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
			linesLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
			linesLabel.setText(String.valueOf(linesCleared));
			stats.add(Box.createRigidArea(new Dimension(0, 10)));
			stats.add(scoreTitle);
			stats.add(scoreboard);
			stats.add(Box.createRigidArea(new Dimension(0, 30)));
			stats.add(highScoreTitle);
			stats.add(highScoreLabel);
			stats.add(Box.createRigidArea(new Dimension(0, 30)));
			stats.add(piecesLeftTitle);
			stats.add(piecesLeftLabel);
			stats.add(Box.createRigidArea(new Dimension(0, 30)));
			stats.add(linesTitle);
			stats.add(linesLabel);
			stats.add(Box.createGlue());

			//Konami stuff
			if(P4Arcade.hasKonami) {
				try{
					konamiBanner = new ImageIcon(ImageIO.read(new File("tetrispieces/konamiBanner.png")));
				}catch(IOException e){
					System.out.println("konami banner image not found");
				}
				konamiLabel = new JLabel(konamiBanner);
				konamiLabel.setFont(new Font("Helvetica", Font.BOLD + Font.ITALIC, 18));
				konamiLabel.setForeground(Color.red);
				stats.add(konamiLabel);
			}

			//SETTING UP OPTIONS MENU LISTENER AND MENU COMPONENTS
			//-------------------------------------------------------------------
			class MenuActionListener implements ActionListener {
				public void actionPerformed(ActionEvent event) {
					if(event.getSource() == instructions) { //Display instructions dialog and pause the game
						timer.stop();
						playpause.setText("Play the game");
						paused = true;
						JOptionPane.showMessageDialog(frame, "Use the controls to move the Tetriminos around the board and fit them together.\n"
								+ "Try to stack the Tetriminos to fill an entire row of completely\n"
								+ "Placing a single Tetrimino will add 1 point to your score.\n"
								+ "Finishing a row will clear the line and add 100 points to your score.\n"
								+ "Clearing multiple rows with the same Tetrimino will double the points awarded.\n"
								+ "The game will end after 200 Tetriminos have been used OR the board ceiling is filled\n\n"
								+ "CONTROLS: \n"
								+ "-   UP: Rotate Tetrimino  90 degrees\n"
								+ "-   DOWN: Move Tetrimino  down\n"
								+ "-   LEFT: Move Tetrimino  left\n"
								+ "-   RIGHT: Move Tetrimino  right\n"
								+ "-   SHIFT: Move Tetrimino down instantly\n"
								+ "-   SPACEBAR: Pause game\n\n"
								+ "-   CTRL+X: Exit game\n"
								+ "-   CTRL+R: Restart game\n"
								+ "-   CTRL+A: Exit to Arcade\n"
								+ "-   ALT+0: Show nothing in the queue\n"
								+ "-   ALT+1: Show 1 Tetrimino in the queue\n"
								+ "-   ALT+2: Show 4 Tetriminos in the queue", 
								"INSTRUCTIONS", JOptionPane.INFORMATION_MESSAGE);
					} else if(event.getSource() == exit) { //Exit the game
						cue.removeAll();
						stats.removeAll();
						board.removeAll();
						frame.removeAll();
						frame.dispose();
						timer.stop();
						System.exit(0);
					} else if(event.getSource() == restart) { //Restart the game
						cue.removeAll();
						stats.removeAll();
						board.removeAll();
						frame.removeAll();
						frame.dispose();
						timer.stop();
						reset();
						String[] empty = new String[0];
						main(empty);
					} else if(event.getSource() == arcade) { //Exit to arcade main						
						frame.dispose();
						timer.stop();
						timer =  null;
						String[] empty = new String[0];
						P4Arcade.main(empty);
					} else if(event.getSource() == playpause) { //Play-Pause the game
						if(paused == false) {
							timer.stop();
							playpause.setText("Play the game");
							paused = true;
						} else if(paused == true) {
							timer.start();
							playpause.setText("Pause the game");
							paused = false;
						}
					} else if(event.getSource() == noCue) { //Set Queue to no visibility
						piece1Panel.setVisible(false);
						piece2Panel.setVisible(false);
						piece3Panel.setVisible(false);
						piece4Panel.setVisible(false);
					} else if(event.getSource() == onePieceCue) { //Set Queue to show 1 piece ahead
						piece1Panel.setVisible(true);
						piece2Panel.setVisible(false);
						piece3Panel.setVisible(false);
						piece4Panel.setVisible(false);
					} else if(event.getSource() == fourPieceCue) { //Set Queue to show 4 pieces ahead
						piece1Panel.setVisible(true);
						piece2Panel.setVisible(true);
						piece3Panel.setVisible(true);
						piece4Panel.setVisible(true);
					}
				}
			}
			MAListener = new MenuActionListener();
			
			delaySlider.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() { //Speed slider
				public void mouseDragged(java.awt.event.MouseEvent evt) {
					delay = delaySlider.getValue();		
					bonus = (2000 - delay) / 100 + 2; //BONUS POINTS FORMULA FOR SETTING PIECES
					timer.setDelay(delay);
				}
			});
			
			//Help menu items
			instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			instructions.addActionListener(MAListener);
			helpMenu.add(instructions);
			//Options menu items
			exit.addActionListener(MAListener);
			exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
			restart.addActionListener(MAListener);
			restart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
			arcade.addActionListener(MAListener);
			arcade.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
			playpause.addActionListener(MAListener);
			playpause.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_SPACE));
			delaySlider.setFont(new Font("Arial", Font.PLAIN, 8));
			JLabel sliderLabel = new JLabel("Speed in Milliseconds", JLabel.CENTER);
			delaySlider.setPaintTicks(true);
			delaySlider.setPaintLabels(true);
			delaySlider.setMajorTickSpacing(1000);
			delaySlider.setMinorTickSpacing(500);
			delaySlider.setSnapToTicks(true);
			//Queue menu items
			noCue.addActionListener(MAListener);
			noCue.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.ALT_MASK));
			onePieceCue.addActionListener(MAListener);
			onePieceCue.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
			fourPieceCue.addActionListener(MAListener);
			fourPieceCue.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
			//ADDING TO THE SEPARATE MENUS
			optionsMenu.add(exit);
			optionsMenu.add(restart);
			optionsMenu.add(arcade);
			optionsMenu.addSeparator();
			optionsMenu.add(playpause);
			view.add(noCue);
			view.add(onePieceCue);
			view.add(fourPieceCue);
			optionsMenu.addSeparator();
			optionsMenu.add(sliderLabel);
			optionsMenu.add(delaySlider);
			//ADDING SEPARATE MENUS TO MENUBAR
			menuBar.add(optionsMenu);
			menuBar.add(view);
			menuBar.add(helpMenu, BorderLayout.PAGE_END);
			frame.setJMenuBar(menuBar);

			//SETTING UP TETRIS KEY MOVEMENT LISTENER
			//-------------------------------------------------------------------
			class TetrisKeyListener implements KeyListener {
				@Override
				public void keyPressed(KeyEvent arg0) {}
				@Override
				public void keyReleased(KeyEvent key) {
					if(!paused) {
						if(key.getKeyCode() == 37) { // left arrow
							piece.move('l');
						} else if(key.getKeyCode() == 39) { // right arrow
							piece.move('r');
						} else if(key.getKeyCode() == 40) { // down arrow
							piece.move('d');
						} else if(key.getKeyCode() == 38) { // up arrow
							piece.rotate('c');
						} else if(key.getKeyCode() == 16) { // shift key
							while(piece.needNewPiece == false){
								piece.move('d');
							}
						}
						displayBlock();
					}
				}
				@Override
				public void keyTyped(KeyEvent arg0) {}
			}
			
			//SETTING UP GAME BOARD PANEL GRID
			//-------------------------------------------------------------------
			panelHolder = new JPanel[height][width];
			GridLayout layout = new GridLayout(height, width, 2, 2); 
			//Double array of panels
			board.setLayout(layout);

			panelValues = new char[height][width];

			frame.addKeyListener(new TetrisKeyListener());	

			if(gameStarted == false) {
				for(int i = 0; i < height; i++) {
					for(int j = 0; j < width; j++){
						panelHolder[i][j] = new JPanel();
						board.add(panelHolder[i][j]);
					}
					gameStarted = true;
				}
			}

			//SETTING UP CONSTANT DOWN MOVEMENT TIMER
			//-------------------------------------------------------------------
			getNewPiece(); //generate random piece
			timer.start(); 
			ActionListener timerListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(piece.needNewPiece == true) { //If piece reached final spot, get new piece
						piecesLeft--;
						if(piecesLeft == 0){ //End game if there are no pieces left
							endGame(score);
						}
						getNewPiece();
						score += bonus;
						piecesLeftLabel.setText(Integer.toString(piecesLeft));
						scoreboard.setText(String.valueOf(score));
						linesLabel.setText(String.valueOf(linesCleared));
						piece.needNewPiece = false;
					} else {
						piece.moveDown();
					}
					panelValues = piece.finishAction(); 
					displayBlock();
				}
			};

			timer.addActionListener(timerListener);

			// Gets the frame ready to be visible
			frame.pack();

			// Makes the frame visible
			frame.setVisible(true);
		} catch (NullPointerException e) {
			// Display an error message if initialization fails
			System.out.println("Error initializing. Program exiting");
			System.exit(1);
		}
	}
	//-------------------------------------------------------------------------------------------------
	//END OF MAIN METHOD
	

	/** --------------------------------------------------------------------
	 * displayBlock() Fills the tetris board with color values for displaying */	
	public static void displayBlock() {
		panelValues = piece.finishAction();
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(panelValues[i][j] == piece.pieceType) { // Filling grid with piece color
					panelHolder[i][j].setBackground(piece.color);
				} else if(panelValues[i][j] == '\u0000') {
					panelHolder[i][j].setBackground(Color.white);
				}			
			}
		}
	}

	/** --------------------------------------------------------------------
	 * addScore(int) - Allows other classes to access/add to the player's score
	 * @param int tempScore: the value to be added to the curernt score. */
	public static void addScore(int tempScore) {
		score += tempScore;
	}

	/** --------------------------------------------------------------------
	 * getHighScore() gets the value of the highscore.txt file.
	 * @return int highscore: the player's highest recorded score. */	
	public static int getHighScore() {
		BufferedReader br = null;
		int line = 0;
		try {
			br = new BufferedReader(new FileReader("highscore.txt"));
			line = Integer.parseInt(br.readLine());
		} catch (FileNotFoundException e1) {
			// No high score file, using default 0 value
			System.out.println("High score file not found");
			return line;
		} catch (NumberFormatException e) {
			// File is not a number, using default 0 value
		} catch (IOException e) {
			// Input output exception, using default 0 value
		} 
		try {
			br.close();
		} catch (IOException e) {
			System.out.println("Unable to close file");
		}
		return line;
	}

	/** --------------------------------------------------------------------
	 * endGame() - Saves high score file, displays options dialog box with 
	 * some game stats.
	 * @param score: the player's score before ending */
	public static void endGame(int score) {
		String titleText = "GAME OVER"; 
		String scoreText = "Your score: " + score + "\nLast high score: " + highScore;
		ImageIcon enderpiece = new ImageIcon();
		try {
			enderpiece = new ImageIcon(ImageIO.read(new File("tetrispieces/enderpiece.png")));
		} catch (IOException e1) {
			System.out.println("Missing zpiece image");
		}
		timer.stop();
		//Updating high score
		try {
			if(score > highScore) {
				scoreText = "New high score! " + scoreText;
				titleText = "GAME OVER - NEW HIGH SCORE";
				wr = new FileWriter("highscore.txt");
				wr.write( String.valueOf(score) );
				wr.flush();
				wr.close();
			}
		} catch (IOException e) {
			System.out.println("Unable to save high score to file");
		}

		Object[] options = {"Yes", "No (close)", "Arcade Menu"}; //Options for dialog
		int endGameOption = JOptionPane.showOptionDialog(frame,"Game over.\n"
				+ scoreText
				+ "\nThere were " + piecesLeft + " tetronimoes left.\n"
				+ "You managed to clear " + linesCleared + " lines of blocks.\n"
				+ "\n Would you like to start another round?", titleText, 
				JOptionPane.YES_NO_CANCEL_OPTION, //option type
				JOptionPane.QUESTION_MESSAGE, //message type
				enderpiece, //Icon to display in the dialog
				options, //adding options
				options[0]); //specifying default value to be selected.

		if(endGameOption == 0) { //START NEW GAME: reset variables and repaint.
			cue.removeAll();
			stats.removeAll();
			board.removeAll();
			frame.removeAll();
			frame.dispose();
			timer.stop();
			reset();
			String[] empty = new String[0];
			main(empty);
		} else if(endGameOption == 1) { //EXIT GAME.
			frame.dispose();
			System.exit(0);
		} else if(endGameOption == 2) { //RETURN TO ARCADE MENU. Reset variables.
			frame.dispose();
			timer.stop();
			timer =  null;
			String[] empty = new String[0];
			P4Arcade.main(empty);
		}
		endGameOption = -1;
	}

	/** --------------------------------------------------------------------
	 * getNewPiece() - sets up the cue panel visualizations and gets the next 
	 * block from the queue generated. */
	public static void getNewPiece() {
		ImageIcon OImage = new ImageIcon();
		ImageIcon IImage = new ImageIcon();
		ImageIcon SImage = new ImageIcon();
		ImageIcon ZImage = new ImageIcon();
		ImageIcon LImage = new ImageIcon();
		ImageIcon JImage = new ImageIcon();
		ImageIcon TImage = new ImageIcon();
		//Importing piece image files			
		try {
			OImage =  new ImageIcon(ImageIO.read(new File("tetrispieces/OPiece.png")));
			IImage =  new ImageIcon(ImageIO.read(new File("tetrispieces/IPiece.png")));
			SImage =  new ImageIcon(ImageIO.read(new File("tetrispieces/SPiece.png")));
			ZImage =  new ImageIcon(ImageIO.read(new File("tetrispieces/ZPiece.png")));
			LImage =  new ImageIcon(ImageIO.read(new File("tetrispieces/LPiece.png")));
			JImage =  new ImageIcon(ImageIO.read(new File("tetrispieces/JPiece.png")));
			TImage =  new ImageIcon(ImageIO.read(new File("tetrispieces/TPiece.png")));
		} catch (IOException e) {
			System.err.println("Error: All Tetris piece image files were not found");
		}
		// Pull 5 pieces (int) from queue initially
		if(firstNewPiece == true) {
			newPiece = pieceQueue.queue.poll(); //THIS PIECE IS USED AND DISPLAYED
			showPiece1 = pieceQueue.queue.poll();
			showPiece2 = pieceQueue.queue.poll();
			showPiece3 = pieceQueue.queue.poll();
			showPiece4 = pieceQueue.queue.poll();
			firstNewPiece = false;
		} else { // Move pieces down after they've been pulled
			// Handle Konami code to take out I piece
			if(piece.isWaiting) {
				myIsWaiting = true;
			}
			if(myIsWaiting) {
				do {
					newPiece = showPiece1;
					showPiece1 = showPiece2;
					showPiece2 = showPiece3;
					showPiece3 = showPiece4;
					showPiece4 = pieceQueue.queue.poll();
				} while (newPiece == 1);
			} else {
				newPiece = showPiece1;
				showPiece1 = showPiece2;
				showPiece2 = showPiece3;
				showPiece3 = showPiece4;
				showPiece4 = pieceQueue.queue.poll();
			}
		}
		//MAIN PIECE
		if(newPiece == 0){
			piece = new OPiece(panelValues); //O piece
		}else if(newPiece == 1){
			piece = new IPiece(panelValues); //I piece
		}else if(newPiece == 2){
			piece = new SPiece(panelValues); //S piece
		}else if(newPiece == 3){
			piece = new ZPiece(panelValues); //Z piece
		}else if(newPiece == 4){
			piece = new LPiece(panelValues); //L piece
		}else if(newPiece == 5){
			piece = new JPiece(panelValues); //J piece
		}else if(newPiece == 6){
			piece = new TPiece(panelValues); //T piece
		}
		//showPiece1 & piece1Label
		if(showPiece1 == 0){
			piece1Label.setIcon(OImage); //O piece
		}else if(showPiece1 == 1){
			piece1Label.setIcon(IImage); //I piece
		}else if(showPiece1 == 2){
			piece1Label.setIcon(SImage); //S piece
		}else if(showPiece1 == 3){
			piece1Label.setIcon(ZImage); //Z piece
		}else if(showPiece1 == 4){
			piece1Label.setIcon(LImage); //L piece
		}else if(showPiece1 == 5){
			piece1Label.setIcon(JImage); //J piece
		}else if(showPiece1 == 6){
			piece1Label.setIcon(TImage); //T piece
		}
		//showpiece2 & piece2Label
		if(showPiece2 == 0){
			piece2Label.setIcon(OImage); //O piece
		}else if(showPiece2 == 1){
			piece2Label.setIcon(IImage); //I piece
		}else if(showPiece2 == 2){
			piece2Label.setIcon(SImage); //S piece
		}else if(showPiece2 == 3){
			piece2Label.setIcon(ZImage); //Z piece
		}else if(showPiece2 == 4){
			piece2Label.setIcon(LImage); //L piece
		}else if(showPiece2 == 5){
			piece2Label.setIcon(JImage); //J piece
		}else if(showPiece2 == 6){
			piece2Label.setIcon(TImage); //T piece
		}
		//showpiece3 & piece3Label
		if(showPiece3 == 0){
			piece3Label.setIcon(OImage); //O piece
		}else if(showPiece3 == 1){
			piece3Label.setIcon(IImage); //I piece
		}else if(showPiece3 == 2){
			piece3Label.setIcon(SImage); //S piece
		}else if(showPiece3 == 3){
			piece3Label.setIcon(ZImage); //Z piece
		}else if(showPiece3 == 4){
			piece3Label.setIcon(LImage); //L piece
		}else if(showPiece3 == 5){
			piece3Label.setIcon(JImage); //J piece
		}else if(showPiece3 == 6){
			piece3Label.setIcon(TImage); //T piece
		}
		//showpiece4 & piece4Label
		if(showPiece4 == 0){
			piece4Label.setIcon(OImage); //O piece
		}else if(showPiece4 == 1){
			piece4Label.setIcon(IImage); //I piece
		}else if(showPiece4 == 2){
			piece4Label.setIcon(SImage); //S piece
		}else if(showPiece4 == 3){
			piece4Label.setIcon(ZImage); //Z piece
		}else if(showPiece4 == 4){
			piece4Label.setIcon(LImage); //L piece
		}else if(showPiece4 == 5){
			piece4Label.setIcon(JImage); //J piece
		}else if(showPiece4 == 6){
			piece4Label.setIcon(TImage); //T piece
		}
	}

	/** --------------------------------------------------------------------
	 * reset() - resets the Tetris game components for a restart. */
	public static void reset() {
		bonus = 10;
		delaySlider.setValue(1000);
		frame = new JFrame("Tetris");
		mainTitle = new JLabel("Tetris");
		statsTitle = new JLabel("Player Stats");
		cueTitle = new JLabel("Piece Queue");
		stats = new JPanel();
		board = new JPanel();
		header = new JPanel();
		panelHolder = new JPanel[0][0];
		panelValues = new char[0][0];
		scoreboard = new JLabel();
		linesTitle = new JLabel("Lines Cleared:");
		linesLabel = new JLabel();
		highScoreLabel = new JLabel();
		konamiLabel = new JLabel();
		//MAIN MENU COMPONENTS
		//-----------------------------------------------
		menuBar = new JMenuBar();
		optionsMenu = new JMenu("Options");
		view = new JMenu("View");
		helpMenu = new JMenu("Help");
		// Help menu items
		instructions = new JMenuItem("Gameplay Instructions");
		// Options menu items		
		exit = new JMenuItem("Exit");
		restart = new JMenuItem("Restart");
		arcade = new JMenuItem("Arcade Home");
		playpause = new JMenuItem("Pause the game");
		noCue = new JMenuItem("Show no pieces ahead");
		onePieceCue = new JMenuItem("Show 1 piece ahead");
		fourPieceCue = new JMenuItem("Show 4 pieces ahead");
		delaySlider = new JSlider(200, 2200, 1000);
		//CUE MENU COMPONENTS
		//-----------------------------------------------
		cue = new JPanel(); //In Frame East
		piece1Panel = new JPanel();
		piece2Panel = new JPanel();
		piece3Panel = new JPanel();
		piece4Panel = new JPanel();
		piece1Label = new JLabel();
		piece2Label = new JLabel();
		piece3Label = new JLabel();
		piece4Label = new JLabel();
		//GAME VARIABLES
		//-----------------------------------------------
		width = 10;
		height = 20;
		delay = 1000;
		gameStarted = false;
		firstNewPiece = true;
		paused = false;
		score = 0;
		highScore = 0;
		linesCleared = 0;
		timer = new Timer(delay, null);
		piecesLeft = 200;
		//Piece variables
		pieceQueue = new PieceQueue();
		newPiece = 0;
		showPiece1 = 0;
		showPiece2 = 0;
		showPiece3 = 0;
		showPiece4 = 0;
	}
}
