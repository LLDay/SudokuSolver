package gui;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.text.ParseException;

import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

public class SudokuApp {

	private JFrame frmSudoku;
	private JPanel state_panel;
	private JLabel lblNewLabel;
	private JPanel info_panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SudokuApp window = new SudokuApp();
					window.frmSudoku.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws ParseException
	 */
	public SudokuApp() throws ParseException {
		frmSudoku = new JFrame();
		frmSudoku.setResizable(false);
		frmSudoku.setTitle("Sudoku");
		frmSudoku.setBounds(100, 100, 325, 366);
		frmSudoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSudoku.getContentPane().setLayout(new BorderLayout(0, 3));

		JPanel sudoku_panel = new JPanel();
		frmSudoku.getContentPane().add(sudoku_panel, BorderLayout.NORTH);
		sudoku_panel.setLayout(new GridLayout(3, 3, 0, 0));

		for (int i = 0; i < 9; ++i) {
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
			panel.setLayout(new GridLayout(3, 3, 0, 0));
			sudoku_panel.add(panel);
		}

		SudokuCell lastCell = null;

		//set correct tab order
		for (int rowBlock = 0; rowBlock < 3; ++rowBlock)
			for (int i = 0; i < 3; ++i)
				for (int columnBlock = 0; columnBlock < 3; ++columnBlock) {
					JPanel currentPanel = (JPanel) sudoku_panel.getComponent(rowBlock * 3 + columnBlock);
					for (int j = 0; j < 3; ++j) {
						SudokuCell currentCell = new SudokuCell();
						if (lastCell != null)
							lastCell.setNextFocusableComponent(currentCell);
						lastCell = currentCell;
						currentPanel.add(currentCell);
					}
				}

		info_panel = new JPanel();
		frmSudoku.getContentPane().add(info_panel, BorderLayout.CENTER);
		info_panel.setLayout(new BorderLayout(0, 0));

		JPanel button_panel = new JPanel();
		info_panel.add(button_panel, BorderLayout.NORTH);
		FlowLayout flowLayout = (FlowLayout) button_panel.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);

		JButton btnNewButton = new JButton("Solve");
		button_panel.add(btnNewButton);

		state_panel = new JPanel();
		info_panel.add(state_panel, BorderLayout.SOUTH);
		state_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblNewLabel = new JLabel("New label");
		state_panel.add(lblNewLabel);

	}
}