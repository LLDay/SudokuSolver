package gui;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class SudokuCell extends JFormattedTextField implements KeyListener, FocusListener {

	private static final long serialVersionUID = -8624126699112453185L;

	private static final Dimension dim = new Dimension(30, 30);
	private static final Font font = new Font("Verdana", Font.BOLD, 14);

	SudokuCell() throws ParseException {
		setPreferredSize(dim);
		setFont(font);
		setHorizontalAlignment(SwingConstants.CENTER);

		Color transparent = new Color(0, 0, 0, 0);
		setCaretColor(transparent);
		setSelectionColor(transparent);

		MaskFormatter formatter = new MaskFormatter("#");
		formatter.setValidCharacters("123456789");
		DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);

		setFormatterFactory(factory);

		addKeyListener(this);
		addFocusListener(this);

		getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(false);
		//getActionMap().get(DefaultEditorKit.beepAction).setEnabled(false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if ("123456789".indexOf(e.getKeyChar()) != -1) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
		}
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			setValue(null);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void focusGained(FocusEvent e) {
		setBackground(new Color(192, 255, 184));
	}

	@Override
	public void focusLost(FocusEvent e) {
		setBackground(Color.white);
	}
}
