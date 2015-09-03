package p4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class P4Arcade {
	private static JFrame frame; // main frame
	private static JButton TTT; // TicTacToe button
	private static JButton tetris; // Tetris button
	private static JButton titleButton; //Title button
	private static JButton konamiButton; //Konami button
	private static BufferedImage background; //Arcade bg image
	private static ImageIcon icon; //Title icon
	private static JPanel panel;
	// KONAMI CODE
	private static int[] konamiCode = { 38, 38, 40, 40, 37, 39, 37, 39, 66, 65 }; //Konami code sequence
	public static boolean hasKonami = false; //State of Konami code
	private static int currentKey = 0;

	//STARTING MAIN METHOD
	//-------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		try {
			//Paint background for arcade
			panel = new JPanel(){
				public void paintComponent(Graphics g) {
					g.drawImage(background, 0, 0, null);
				} };
			panel.setLayout(new BorderLayout());

			// Initialize Konami Code listener
			konamiButton = new JButton();
			
			//---------------------------------------------------
			//END OF MAIN METHOD
			class KonamiKeyListener implements KeyListener {
				@Override
				public void keyPressed(KeyEvent arg0) {}
				@Override
				public void keyReleased(KeyEvent e) {
					if(!hasKonami) {
						hasKonami = checkKonami(e.getKeyCode());
						if(hasKonami) {
							try {
								konamiButton.setIcon(new ImageIcon(ImageIO.read(new File("konami.png"))));
							} catch (IOException e1) {
								System.out.println("konami image file missing");
							}
						}
					}
				}
				@Override
				public void keyTyped(KeyEvent arg0) {}
			}			
			
			// Create a new JFrame
			frame = new JFrame("ZACH & JAKE ARCADE");
			// Set a minimum size for the JFrame
			frame.setMinimumSize(new Dimension(1285, 832));
			// Give the JFrame the ability to be closed
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// Center the frame
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			
			//Importing background image and setting it
			background = ImageIO.read(new File("background.png"));
			
			frame.getContentPane().add(panel);
			 icon = new ImageIcon(ImageIO.read(new File("title.png")));
			//Setting up title
			JLabel title = new JLabel("Zach & Jake's Arcade", JLabel.CENTER);
			title.setFont(new Font("Helvetica", Font.PLAIN, 50));
			title.setForeground(Color.white);
			
			//Create button listener for game buttons
			class ButtonActionListener implements ActionListener {
				@Override
				public void actionPerformed(ActionEvent event) {
					final String[] empty = new String[0];
					if(event.getSource() == TTT) { //Start Tic Tac Toe
						TicTacToe.main(empty);
						frame.dispose();
					}
					if(event.getSource() == tetris) { //Start Tetris
						Tetris.reset();
						Tetris.main(empty);
						frame.dispose();
					}
					if(event.getSource() == titleButton) { //Display info
						JOptionPane.showMessageDialog(frame, "CSCI 1302 PROJECT 4\n\n"
								+ "Created by Zach Saucier and Jake Webber UGA Spring 2014\n"
								+ "", "Info", JOptionPane.PLAIN_MESSAGE);
					}
				}				
			}
			
			//Setting up konami display
			konamiButton.setOpaque(false);
			konamiButton.setContentAreaFilled(false);
			konamiButton.setBorderPainted(false);
			konamiButton.setFocusPainted(false);
			
			//Setting up title button
			titleButton = new JButton();
			titleButton.setIcon(new ImageIcon(ImageIO.read(new File("title.png"))));
			titleButton.setRolloverIcon(new ImageIcon(ImageIO.read(new File("titleover.png"))));
			titleButton.setOpaque(false);
			titleButton.setContentAreaFilled(false);
			titleButton.setBorderPainted(false);
			titleButton.setFocusPainted(false);
			titleButton.addActionListener(new ButtonActionListener());
			
			//Setting up Tic Tac Toe Icon Button
			TTT = new JButton();
			TTT.setIcon(new ImageIcon(ImageIO.read(new File("TTT.png"))));
			TTT.setRolloverIcon(new ImageIcon(ImageIO.read(new File("TTTover.png"))));
			TTT.setPressedIcon(new ImageIcon(ImageIO.read(new File("TTTpressed.png"))));
			TTT.setOpaque(false);
			TTT.setContentAreaFilled(false);
			TTT.setBorderPainted(false);
			TTT.setFocusPainted(false);
			TTT.addActionListener(new ButtonActionListener());
			
			//Setting up Tetris Icon Button
			tetris = new JButton();
			tetris.setIcon(new ImageIcon(ImageIO.read(new File("tetris.png"))));
			tetris.setRolloverIcon(new ImageIcon(ImageIO.read(new File("tetrisover.png"))));
			tetris.setPressedIcon(new ImageIcon(ImageIO.read(new File("tetrispressed.png"))));
			tetris.setOpaque(false);
			tetris.setContentAreaFilled(false);
			tetris.setBorderPainted(false);
			tetris.addActionListener(new ButtonActionListener());
			tetris.setFocusPainted(false);
			
			//Adding components to Panel
			panel.add(title, BorderLayout.NORTH);
			panel.add(tetris, BorderLayout.EAST);
			panel.add(TTT, BorderLayout.WEST);
			panel.add(titleButton, BorderLayout.NORTH);
			panel.add(konamiButton, BorderLayout.CENTER);
			
			//Adding Konami keylistener
			titleButton.addKeyListener(new KonamiKeyListener());
			
			// Gets the frame ready to be visible
			frame.pack();
			
			// Makes the frame visible
			frame.setVisible(true);
		} catch (IOException x){
			System.out.println("Error: Cannot find image files.");
		}catch (Exception e) {
			// Display an error message if initialization fails
			System.out.println("Error initializing. Program exiting");
			System.exit(1);
		}
	}
	//-------------------------------------------------------------------------------------------------
	//END OF MAIN METHOD
	
	/**--------------------------------------------------------------------
	 * checkKonami(int) - Handles and determines the state of the Konami code.
	 * @param keyPressed
	 * @return boolean controlling state of Konami code */
	static boolean checkKonami(int keyPressed) {
	    // Check sequence so far
	    if(keyPressed == konamiCode[currentKey]) {
	        currentKey++;
	        // KONAMI CODE IS APPLIED
	        if(currentKey == konamiCode.length) {
	        	// Prevent out of bounds exception
	            currentKey = 0;
	            return true;
	        }
	    }
	    else {
	        // Reset currentKey
	        currentKey = 0;
	    }
	    return false;
	}
}