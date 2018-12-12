package gui;

import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class SudokuApp {

	private JFrame frmSudoku;
    private Controller controller;

    private JLabel lblState;
	private List<SudokuCell> cellList;

    public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				SudokuApp window = new SudokuApp();
				window.frmSudoku.setVisible(true);
				window.frmSudoku.setFocusable(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private SudokuApp() throws ParseException {
        frmSudoku = new JFrame();
		frmSudoku.setResizable(false);
		frmSudoku.setTitle("Sudoku");
		frmSudoku.setBounds(100, 100, 325, 380);
		frmSudoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSudoku.getContentPane().setLayout(new BorderLayout(0, 3));

		JPanel sudoku_panel = new JPanel();
		sudoku_panel.setLayout(new GridLayout(3, 3, 0, 0));

		frmSudoku.getContentPane().add(sudoku_panel, BorderLayout.NORTH);

		for (int i = 0; i < 9; i++) {
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(Color.black, 2));
			panel.setLayout(new GridLayout(3, 3, 0, 0));
			sudoku_panel.add(panel);
		}
		 cellList = new ArrayList<>();

		//provide correct tab order
		for (int rowBlock = 0; rowBlock < 3; rowBlock++)
			for (int i = 0; i < 3; i++)
				for (int columnBlock = 0; columnBlock < 3; columnBlock++) {
					JPanel currentPanel = (JPanel) sudoku_panel.getComponent(rowBlock * 3 + columnBlock);
					for (int j = 0; j < 3; ++j) {
						SudokuCell currentCell = new SudokuCell();
						cellList.add(currentCell);
						currentPanel.add(currentCell);
					}
				}

		frmSudoku.setFocusTraversalPolicy(new FocusPolicy(cellList));

        JPanel info_panel = new JPanel();
		info_panel.setLayout(new BorderLayout(0, 0));
		frmSudoku.getContentPane().add(info_panel, BorderLayout.CENTER);

		JPanel button_panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) button_panel.getLayout();
		flowLayout.setHgap(10);

		JButton btnSolve = new JButton("Solve");
		btnSolve.addActionListener(e -> controller.actionSolve());

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(e -> controller.actionClear());

        button_panel.add(btnSolve);
        button_panel.add(btnClear);

		info_panel.add(button_panel, BorderLayout.NORTH);

        JPanel state_panel = new JPanel();
		state_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        lblState = new JLabel("New label");
		state_panel.add(lblState);
		info_panel.add(state_panel, BorderLayout.SOUTH);

		controller = new Controller(this);
	}

	int getCell(int index) {
        String text = cellList.get(index).getText();
        if (text.trim().isEmpty())
            return 0;

	    return Integer.parseInt(text);
    }

    void setCell(int index, int value) {
        cellList.get(index).setText(Integer.toString(value));
    }

    void clearBoard() {
    	for (SudokuCell cell : cellList)
    		cell.setValue(null);
	}

	void setState(String state) {
    	lblState.setText(state);
	}
}