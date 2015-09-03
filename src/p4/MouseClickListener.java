package p4;

import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseClickListener implements MouseListener {
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(!((JButton) e.getSource()).isEnabled()) {
			JOptionPane.showMessageDialog(TicTacToe.frame, "This space is disabled.");
		}
	}
}