package p4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.Stack;

public class TicTacToe {
	public static JFrame frame = new JFrame("Tic Tac Toe"); //MAIN FRAME
	public static JPanel buttons = new JPanel(); //Panel: Holds grid board and buttons
	public static JPanel playerStatus = new JPanel(); //Panel: displays player's turn
	public static JLabel currentPlayer = new JLabel("Player 1 move"); //Label: updates players sturn
	private static ImageIcon titleImage; //Tic Tac Toe banner Icon
	
	//Stats panel components
	public static JPanel stats = new JPanel(); //Panel: Holds game stats.
	public static JLabel player1totalWins = new JLabel("Player 1 total wins:");
	public static JLabel player1winCount = new JLabel("0"); //Label: updates p1 win count
	public static JLabel player2totalWins = new JLabel("Player 2 total wins:");
	public static JLabel player2winCount = new JLabel("0"); //Label: updates p2 win count
	public static JLabel playerWins = new JLabel(); //Label: states which player won
	
	//Main Menu components
	private static JMenuBar menuBar = new JMenuBar();
	private static JMenu optionsMenu = new JMenu("Options");
	private static JMenu helpMenu = new JMenu("Help");
	private static JMenuItem instructions = new JMenuItem("Gameplay Instructions");
	private static JMenuItem exit = new JMenuItem("Exit");
	private static JMenuItem arcade = new JMenuItem("Arcade Home");
	private static JMenuItem restart = new JMenuItem("Restart Game");
	private static JMenuItem undo = new JMenuItem("Undo Move");
	private static JMenuItem redo = new JMenuItem("Redo Move");
	

	//Game variables
	public static boolean isPlayer1 = true; //switch between player 1 and player 2
	public static char[] gridValues = new char[9]; //holds x & o values for board grid
	private static JButton[] gridButtons = new JButton[9];
	public static int boardFill = 0; //Number of spaces used on board
	public static int endGameOption = -1; //int for endGame menu options
	public static Color player1color = new Color(0, 200, 0); //p1 color (green)
	public static Color player2color = new Color(100, 100, 255); //p2 color (blue
	public static Stack<JButton> undoStack = new Stack<JButton>(); //holds undo stack
	public static Stack<JButton> redoStack = new Stack<JButton>(); //holds redo stack
	public static KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	public static boolean isFirst = true;
	public static boolean firstGame = true;
	private static MouseClickListener MCListener = new MouseClickListener();
	
	//STARTING MAIN METHOD
	//--------------------------------------------------------------------------------
	public static void main(String[] args) {
		try {
			// Create main JFrame
			frame.setResizable(false);
			// Set a minimum size for the JFrame
			frame.setMinimumSize(new Dimension(750, 500));
			// Give the JFrame the ability to be closed
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// Center the frame
			frame.setLocationRelativeTo(null);
			
			
			//Creating buttons listener for every click turn
			class ButtonActionListener implements ActionListener {
				JButton myButton = new JButton();
				int myPosition;
				public ButtonActionListener(JButton button) {
					myButton = button;
					myPosition = Integer.parseInt(button.getText());
				}
				@Override
				public void actionPerformed(ActionEvent event) {
					Image buttonImage;
					try {
						if(isPlayer1) { //Player 1
							gridValues[myPosition] = 'x';
							buttonImage = ImageIO.read(new File("xbutton.png"));
							playerStatus.setBackground(player2color);
							currentPlayer.setText("Player 2 move");
						} else { //Player 2
							gridValues[myPosition] = 'o';
							buttonImage = ImageIO.read(new File("obutton.png"));
							currentPlayer.setText("Player 1 move");
							playerStatus.setBackground(player1color);
						}
						myButton.setDisabledIcon(new ImageIcon(buttonImage));
					} catch (IOException e) {
						System.out.println("Error: Button image file missing");
						System.exit(1);
					}
					// Clear redo stack
					redoStack.clear();
					myButton.addMouseListener(MCListener);
					isPlayer1 = !isPlayer1; //switch players
					undoStack.push(myButton); //add to undo stack
					myButton.setEnabled(false);
					boardFill++; //increment board positions taken
					checkWin(); //check if game is won
				}
			}			
			
			// Undo & Redo functions
			class MyKeyEventDispatcher implements KeyEventDispatcher {
				@Override
				public boolean dispatchKeyEvent(KeyEvent e) {
					// Undo (CTRL + Z)
					if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown() && e.getKeyCode() == 90) {
						
					}
					// Redo (CTRL + Y)
					if (e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown() && e.getKeyChar() != 'a' && e.getKeyCode() == 89) {
						
					}
					return false;
				}
			}
			if(isFirst) {
				manager.addKeyEventDispatcher(new MyKeyEventDispatcher());
				isFirst = false;
			}

			//Create title panel
			//----------------------------------------------
			titleImage = new ImageIcon(ImageIO.read(new File("tictactitle.png")));
			JPanel panel = new JPanel();
			JLabel title = new JLabel(titleImage);
			panel.add(title);
			panel.setBackground(Color.black);
			
			//Create stats panel & information 
			//----------------------------------------------
			playerWins.setFont(new Font("Stencil", Font.BOLD, 15));
			stats.setBackground(Color.white);
			stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
			JLabel statsTitle = new JLabel("   Player Stats   ");
			statsTitle.setFont(new Font("Helvetica", Font.ITALIC, 20));
			stats.add(statsTitle);
			player1totalWins.setFont(new Font("Helvetica", Font.PLAIN, 16));
			stats.add(player1totalWins);
			player1winCount.setFont(new Font("Helvetica", Font.PLAIN, 16));
			stats.add(player1winCount);
			player2totalWins.setFont(new Font("Helvetica", Font.PLAIN, 16));
			stats.add(player2totalWins);
			player2winCount.setFont(new Font("Helvetica", Font.PLAIN, 16));
			stats.add(player2winCount);

			//Create player status panel 
			//-----------------------------------------------
			currentPlayer.setText("Player 1 move");
			currentPlayer.setFont(new Font("Helvetica", Font.BOLD, 15));
			playerStatus.setBackground(player1color);
			playerStatus.add(currentPlayer, BorderLayout.CENTER);
			frame.add(playerStatus, BorderLayout.SOUTH);

			//Reset & create buttons panel (game board)
			//-----------------------------------------------
			buttons.removeAll();
			buttons.repaint();
			GridLayout layout = new GridLayout(3, 3, 10, 10);
			buttons.setLayout(layout);
			buttons.setBackground(Color.black);

			//Adding 9 Jbutton to buttons panel
			for(int i = 0; i < 9; i++){
				JButton button = new JButton(Integer.toString(i));
				button.setEnabled(true);
				button.repaint();
				Image buttonImage;
				Image buttonImageOver;
				gridButtons[i] = button;
				//Setting button images
				try {
					buttonImage = ImageIO.read(new File("button.png"));	
					buttonImageOver = ImageIO.read(new File("buttonover.png"));
					button.setIcon(new ImageIcon(buttonImage));
					button.setRolloverIcon(new ImageIcon(buttonImageOver));
					button.setContentAreaFilled(false);					
					button.addActionListener(new ButtonActionListener(button));
					button.setText(Integer.toString(i));
				}				
				catch(IOException IObg) {
					System.out.println("Error: Button image file missing");
					System.exit(1);
				}
				button.setPreferredSize(new Dimension(160, 180));

				buttons.add(button);
			}			

			//Setting up Main Menu
			//--------------------------------------------------------------------------
			class MenuActionListener implements ActionListener {
				public void actionPerformed(ActionEvent event) {
					if(event.getSource() == instructions){
						JOptionPane.showMessageDialog(frame, "Green (X) player and Blue (O) player alternate choosing spots on a 3x3 board\n"
								+ "The first player to get 3 spots in a line horizontally, vertically, or diagonally is the winner.", 
								"INSTRUCTIONS", JOptionPane.PLAIN_MESSAGE);
					} else if(event.getSource() == exit) { //exit game menu  option
						frame.dispose();
						System.exit(0);
					} else if(event.getSource() == arcade) { //return to arcade menu option
						returnToArcade();
					} else if(event.getSource() == restart) { //restart game menu option
						restartGame();						
					} else if(event.getSource() == undo) { //undo move
						if(undoStack.isEmpty() == false) {
							JButton thisButton = undoStack.pop();
							thisButton.removeMouseListener(MCListener);
							redoStack.push(thisButton);
							try {
								thisButton.setIcon(new ImageIcon(ImageIO.read(new File("button.png"))));
							} catch (IOException error) {
								System.out.println("Image not found");
							}
							int myPosition = Integer.parseInt(thisButton.getText());
							if(isPlayer1) {
								gridValues[myPosition] = ' ';
								playerStatus.setBackground(player2color);
								currentPlayer.setText("Player 2 move");
							} else {
								gridValues[myPosition] = ' ';
								playerStatus.setBackground(player1color);
								currentPlayer.setText("Player 1 move");
							}
							thisButton.setEnabled(true);
							isPlayer1 = !isPlayer1; //switch players
							boardFill--; //increment board positions taken
						}
					} else if(event.getSource() == redo) { //redo move
						if(redoStack.isEmpty() == false) {
							JButton thisButton = redoStack.pop();
							undoStack.push(thisButton);
							thisButton.addMouseListener(MCListener);
							
							int myPosition = Integer.parseInt(thisButton.getText());
							if(isPlayer1) {
								try {
									thisButton.setIcon(new ImageIcon(ImageIO.read(new File("xbutton.png"))));
								} catch (IOException error) {
									System.out.println("Image not found");
								}
								gridValues[myPosition] = 'x';
								playerStatus.setBackground(player2color);
								currentPlayer.setText("Player 2 move");
							} else {
								try {
									thisButton.setIcon(new ImageIcon(ImageIO.read(new File("obutton.png"))));
								} catch (IOException error) {
									System.out.println("Image not found");
								}
								gridValues[myPosition] = 'o';
								playerStatus.setBackground(player1color);
								currentPlayer.setText("Player 1 move");
							}
							thisButton.setEnabled(false);
							isPlayer1 = !isPlayer1; //switch players
							boardFill--; //increment board positions taken
						}
					}
				}
			}
			//Help menu items
			if(firstGame == true) {
				instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
				instructions.addActionListener(new MenuActionListener());
				helpMenu.add(instructions);
				//Options menu items
				exit.addActionListener(new MenuActionListener());
				exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
				arcade.addActionListener(new MenuActionListener());
				arcade.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
				restart.addActionListener(new MenuActionListener());
				restart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
				undo.addActionListener(new MenuActionListener());
				undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
				redo.addActionListener(new MenuActionListener());
				redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
	
				
				optionsMenu.add(exit);
				optionsMenu.add(restart);
				optionsMenu.add(arcade);
				optionsMenu.addSeparator();
				optionsMenu.add(undo);
				optionsMenu.add(redo);
				//Add to main menu bar
				menuBar.add(optionsMenu);
				menuBar.add(helpMenu, BorderLayout.PAGE_END);
				frame.setJMenuBar(menuBar);
				firstGame = false;
			}
			//Adding panels to mainframe with specified borderlayout
			frame.add(panel, BorderLayout.NORTH);
			frame.add(stats, BorderLayout.WEST);
			frame.add(buttons, BorderLayout.CENTER);
			// Gets the frame ready to be visible
			frame.pack();
			// Makes the frame visible
			frame.setVisible(true);
			
		} catch (Exception e) {
			// Display an error message if initialization fails
			System.out.println("Error initializing. Program exiting");
			System.exit(1);
		}
	}
	//--------------------------------------------------------------------------------
	//END OF MAIN

	
	/**-------------------------------------------------------------------------
	 * checkWin(): Checks to see if the current game has ended by either a win or a tie.  */
	private static void checkWin() {
		int winningPlayer = -1; //0 is tie, 1 is p1, 2 is p2
		String player = ""; 
		//Check horizontal 
		for(int i = 0; i < 8; i+=3){
			if(gridValues[i] == gridValues[i+1] && gridValues[i+1] == gridValues[i+2]) {
				if(gridValues[i] == 'x') {
					winningPlayer = 1;
				} else if(gridValues[i] == 'o') {
					winningPlayer = 2;
				}
			}
		}
		//Check vertical
		for(int i = 0; i < 3; i++) {
			if(gridValues[i] == gridValues[i+3] && gridValues[i+3] == gridValues[i+6]) {
				if(gridValues[i] == 'x') {
					winningPlayer = 1;
				} else if(gridValues[i] == 'o') {
					winningPlayer = 2;
				}
			}
		}
		//Check diagonal
		if(gridValues[0] == gridValues[4] && gridValues[4] == gridValues[8]) {
			if(gridValues[0] == 'x') {
				winningPlayer = 1;
			} else if(gridValues[0] == 'o') {
				winningPlayer = 2;
			}
		}
		if(gridValues[2] == gridValues[4] && gridValues[4] == gridValues[6]) {
			if(gridValues[2] == 'x') {
				winningPlayer = 1;
			} else if(gridValues[2] == 'o') {
				winningPlayer = 2;
			}
		}
		//Check full grid (tie)
		if(boardFill >= 9 && !(winningPlayer > 0)) {
			winningPlayer = 0;
		}

		//Updating stats if game is won
		if(winningPlayer == 0) {
			currentPlayer.setText("Game over");
			playerStatus.setBackground(Color.gray);
			playerWins.setText("Tie Game!");
			stats.setBackground(Color.gray);
			stats.add(playerWins);
			player = "No Player";
		} else if(winningPlayer == 1) {
			currentPlayer.setText("Game over");
			playerStatus.setBackground(Color.gray);
			playerWins.setText("Player 1 wins!");
			stats.setBackground(player1color);
			stats.add(playerWins);
			player1winCount.setText(String.valueOf(Integer.parseInt(player1winCount.getText()) + 1) );
			player = "Player 1 (x)";
		} else if(winningPlayer == 2) {
			currentPlayer.setText("Game over");
			playerStatus.setBackground(Color.gray);

			playerWins.setText("Player 2 wins!");
			stats.setBackground(player2color);
			stats.add(playerWins);
			player2winCount.setText(String.valueOf(Integer.parseInt(player2winCount.getText()) + 1));
			player = "Player 2 (o)";
		}
		frame.validate();
		//Options after game is won
		if(winningPlayer >= 0) {
			Object[] options = {"Yes", "No (close)", "Arcade Menu"}; //Options for dialog
			endGameOption = JOptionPane.showOptionDialog(frame, player + " has won.\n Would you like to start another round?", "GAME OVER", 
					JOptionPane.YES_NO_CANCEL_OPTION, //option type
					JOptionPane.QUESTION_MESSAGE, //message type
					null, //Icon to display in the dialog
					options, //adding options
					options[0]); //specifying default value to be selected.
		}
		//END OF GAME OPTION ACTIONS
		if(endGameOption == 0) { //START NEW GAME: reset variables and repaint.
			restartGame();
		} else if(endGameOption == 1) { //EXIT GAME.
			frame.dispose();
			System.exit(0);
		} else if(endGameOption == 2) { //RETURN TO ARCADE MENU. Reset variables.
			returnToArcade();
		}
		endGameOption = -1; //resetting endgame menu
	}//end of checkwin
	
	
	/**-------------------------------------------------------------------------
	 * restartGame(): Resets frame components to start a new game. 
	 * */
	private static void restartGame() {
		frame.repaint();
		stats.removeAll();
		stats.remove(playerWins);
		stats.repaint();
		boardFill = 0;
		isPlayer1 = true;
		gridValues = new char[9];
		undoStack.removeAllElements();
		redoStack.removeAllElements();
		for(int i = 0; i < gridButtons.length; i++) {
			gridButtons[i].removeMouseListener(MCListener);
		}
		String[] empty = new String[0];
		TicTacToe.main(empty);
	}
	
	/**-------------------------------------------------------------------------
	 * returnToArcade(): Closes current game frame and opens the arcade main frame.
	 * */
	private static void returnToArcade(){
		undoStack.removeAllElements();
		redoStack.removeAllElements();
		frame.dispose();
		stats.removeAll();
		stats.remove(playerWins);
		stats.repaint();
		boardFill = 0;
		isPlayer1 = true;
		gridValues = new char[9];
		String[] empty = new String[0];
		P4Arcade.main(empty);
	}
}
