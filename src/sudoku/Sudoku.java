package sudoku;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {

	public Sudoku() {
		cells = new ArrayList<>();
		state = SudokuState.UNSOLVED;

		for (int i = 0; i < 81; ++i)
			cells.add(new Cell());
	}

	public Sudoku(String str) {
		this();

		str = str.replaceAll("\n+", "");
		if (str.length() != 81)
			throw new IllegalArgumentException("Wrong size of a string");

		for (int i = 0; i < 9; ++i)
			for (int j = 0; j < 9; ++j) {
				char ch = str.charAt(9 * i + j);
				if (ch != '0')
					set(i, j, Character.getNumericValue(ch));
			}
	}

	public Sudoku(final Sudoku other) {
		this();
		
		for (int i = 0; i < 81; ++i)
			if (other.get(i).hasValue())
				this.set(i, other.get(i).getValue());
	}


	public void set(int cellIndex, int value) {
		if (value == 0)
			return;

		if (cellIndex > 80 || cellIndex < 0)
			throw new IllegalArgumentException("Index must be from 0 to 80");

		cells.get(cellIndex).setValue(value);
	}

	public void set(int rowIndex, int columnIndex, int value) {
		if (rowIndex < 0 || rowIndex > 8 || columnIndex < 0 || columnIndex > 8)
			throw new IllegalArgumentException("There are only 9 rows and 9 columns. Max index is 8");

		set(rowIndex * 9 + columnIndex, value);
	}


	public Cell get(int cellIndex) {
		return cells.get(cellIndex);
	}

	public Cell get(int row, int column) {
		if (row < 0 || row > 8 || column < 0 || column > 8)
			throw new IllegalArgumentException("There are only 9 rows and 9 columns. Max index is 8");

		return cells.get(row * 9 + column);
	}


	public Sudoku getSolve() {
		SudokuSolver solver = new SudokuSolver(this);
		solver.solve();
		return solver;
	}

	public SudokuState getState() {
		return state;
	}


	protected void setState(SudokuState newState) {
		state = newState;
	}

	protected void moveFrom(Sudoku other) {
		if (this != other) {
			this.cells = other.cells;
			this.state = other.state;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 9; ++j)
				builder.append(cells.get(i * 9 + j).getValue());
			builder.append('\n');
		}

		for (int j = 0; j < 9; ++j)
			builder.append(cells.get(72 + j).getValue());

		return builder.toString();
	}

	private List<Cell> cells;
	private SudokuState state;
}