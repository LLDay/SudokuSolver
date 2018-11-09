package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

public class SudokuCell extends JFormattedTextField {
	
	public SudokuCell() throws ParseException {
		super(new MaskFormatter("#"));
		super.setPreferredSize(new Dimension(30, 30));
		super.setHorizontalAlignment(SwingConstants.CENTER);
		super.setFont(new Font("Verdana", Font.BOLD, 14));
	}
	
}
