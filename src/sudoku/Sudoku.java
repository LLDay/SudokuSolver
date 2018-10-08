package sudoku;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {
	
	public enum SudokuState {
		SOLVED,
		UNSOLVED,
		UNSOLVABLE,
		MANY_SOLVES
	}

	
	public Sudoku() {
		blocks = new ArrayList<Block>();
		cells  = new Cell[81];		
		state  = SudokuState.UNSOLVED;
		
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
			
			blocks.add(new Block(rowList));
			blocks.add(new Block(columnList));
			
			// Cells save this block
			new Block(squaresList);
		}		
	}
	
	public Sudoku(final Sudoku other) {
		this();
		
		for (int i = 0; i < 81; ++i)
			if (other.get(i).hasValue())
				this.cells[i].setValue(other.get(i).getValue());
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
		if (getState() != SudokuState.UNSOLVED)
			return;
		

		int minCellCountUndef = 10;
		int minCellIndex = -1;
		
		for (int i = 0; i < 81; ++i)
			if (!cells[i].hasValue() && cells[i].countUndef() < minCellCountUndef) {
				minCellCountUndef = cells[i].countUndef();
				minCellIndex = i;
				
				if (minCellCountUndef == 2)
					break;
			}
		
		if (minCellIndex == -1) {
			state = SudokuState.UNSOLVABLE;
			return;
		}
			
		List<Sudoku> sudokuList = new ArrayList<>();
		for (int i = 1; i < 10; ++i)
			if (cells[minCellIndex].canBe(i)) {
				Sudoku subSudoku = new Sudoku(this);
				subSudoku.set(minCellIndex, i);
				subSudoku.solve();
				sudokuList.add(subSudoku);
				
				if (subSudoku.getState() == SudokuState.MANY_SOLVES)
					break;
		}
		
		int sudokuSolves = 0;
					
		for (Sudoku sud : sudokuList) {
			if (sud.getState() == SudokuState.MANY_SOLVES) {
				this.state = SudokuState.MANY_SOLVES;
				complete(sud);
				return;
			}
			
			if (sud.getState() == SudokuState.SOLVED) {
				sudokuSolves++;
				
				if (state == SudokuState.UNSOLVED)
					complete(sud);
			}
		}
		
		
		if (sudokuSolves == 0)
			state = SudokuState.UNSOLVABLE;
		else if (sudokuSolves == 1)
			state = SudokuState.SOLVED;
		else 
			state = SudokuState.MANY_SOLVES;
	}
	
	public void complete(Sudoku other) {
		for (int i = 0; i < 81; ++i)
			if (!cells[i].hasValue())
				cells[i].setValue(other.get(i).getValue());
	}
	
	public SudokuState getState() {
		if (state == SudokuState.UNSOLVED) {
			int solvedBlocks = 0;
			for (Block block : blocks) {
				if (block.isSolved())
					solvedBlocks++;
				
				if (block.isErr()) {
					state = SudokuState.UNSOLVABLE;	
					break;
				}
			}
			
			if (solvedBlocks == 18)
				state = SudokuState.SOLVED;
		}
		
		return state;
	}

	public static void main(String[] str) 
	{
		
	}
	
	@Override
 	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 9; ++j)
				builder.append(cells[i * 9 + j].getValue());
			builder.append('\n');
		}
		
		return builder.toString();
	}
	
	
	private Cell[]  cells;
	private List<Block> blocks;
	
	private SudokuState state;
}
