package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

public class SudokuCell extends JFormattedTextField implements KeyListener {

	private static final long serialVersionUID = -8624126699112453185L;

	private static final Dimension dim = new Dimension(30, 30);
	private static final Font font = new Font("Verdana", Font.BOLD, 14);

	public SudokuCell() throws ParseException {
		super(new MaskFormatter("#"));
		super.setPreferredSize(dim);
		super.setFont(font);
		super.setHorizontalAlignment(SwingConstants.CENTER);
		this.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if ("123456789".indexOf(e.getKeyChar()) != -1) {
			this.setText(new Character(e.getKeyChar()).toString());
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		}
	}
}
