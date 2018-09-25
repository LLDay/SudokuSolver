package sudoku;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {
	public Sudoku() {
		cells 	= new Cell[81];		
		rows 	= new Block[9];
		columns	= new Block[9];
		squares	= new Block[9];
		state = SudokuState.UNSOLVED;
		
		for (int i = 0; i < cells.length; i++)
			cells[i] = new Cell();
		
		for (int i = 0; i < 9; ++i) {
			List<Cell> rowList 		= new ArrayList<>();
			List<Cell> columnList 	= new ArrayList<>();
			List<Cell> squaresList 	= new ArrayList<>();
			
			for (int j = 0; j < 9; ++j)
				rowList.add(cells[9 * i + j]);
			
			for (int j = 0; j < 9; ++j)
				columnList.add(cells[9 * j + i]);
			
			for (int j = 0; j < 3; ++j) {
				int startIndex = (i / 3) * 27 + (i % 3) * 3 + (9 * j);
				for (int k = 0; k < 3; k++) {
					squaresList.add(cells[startIndex + k]);
				}
			}
			
			Block rowBlock = new Block(rowList);
			Block colBlock = new Block(columnList);
			Block sqBlock  = new Block(squaresList);
			
			rows[i]    = rowBlock;
			columns[i] = colBlock;
			squares[i] = sqBlock;
			
			inList.add(rowBlock);
			inList.add(colBlock);
			inList.add(sqBlock);
		}		
	}
	
	public Sudoku(final Sudoku other) {
		super();
		
		for (int i = 0; i < 81; ++i)
			this.cells[i] = other.cells[i];
	}
	
	
	public void set(int cellIndex, int value) {
		if (cellIndex > 80 || cellIndex < 0)
			throw new IllegalArgumentException("Index must be from 0 to 80");
		cells[cellIndex].setValue(value);
	}
	
	public void set(int rowIndex, int columnIndex, int value) {
		if (rowIndex < 0 || rowIndex > 8 || columnIndex < 0 || columnIndex > 8)
			throw new IllegalArgumentException("There are only 9 rows and 9 columns. Max index is 8");
		set(rowIndex * 9 + columnIndex, value);
	}
	
	
	public Cell get(int cellIndex) {
		return cells[cellIndex];
	}
	
	public Cell get(int row, int column) {
		if (row < 0 || row > 8 || column < 0 || column > 8)
			throw new IllegalArgumentException("There are only 9 rows and 9 columns. Max index is 8");
		
		return cells[row * 9 + column];
	}

	
	public void solve() {
		
	}
	
	public SudokuState getState() {
		return state;
	}
	
	private void stateChecker() {
		for (int i = 0; i < inList.size(); ++i)
			inList.get(i).attemptToDef();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 9; ++j)
				builder.append(rows[i].getValue(j));
			builder.append('\n');
		}
		
		return builder.toString();
	}
	
	public static void main(String[] args) {
		Sudoku sud = new Sudoku();
		sud.set(1, 8, 1);
		
		System.out.println(sud.toString());
	}
	
	private List<Invariant> inList;
	private Cell[]  cells;
	private Block[] rows;
	private Block[] columns;
	private Block[] squares;
	
	private SudokuState state;
}
