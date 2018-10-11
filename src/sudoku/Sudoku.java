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
		
		int firstCellIndex = -1;
		
		for (int i = 0; i < 81; ++i)
			if (!cells[i].hasValue()) {
				firstCellIndex = i;
				break;
			}

		Sudoku firstSolved = null;
		
		for (int i = 1; i < 10; ++i)
			if (cells[firstCellIndex].canBe(i)) {
				Sudoku subSudoku = new Sudoku(this);
		
				subSudoku.set(firstCellIndex, i);
				subSudoku.solve();
			
				if (subSudoku.getState() == SudokuState.SOLVED) {
					
					if (firstSolved == null) 
						firstSolved = subSudoku;
					else  
						firstSolved.state = SudokuState.MANY_SOLVES;
										
					if (firstSolved.state == Sudoku.SudokuState.MANY_SOLVES) {
						this.moveFrom(firstSolved);
						return;
					}
				}
				
				else if (subSudoku.getState() == SudokuState.MANY_SOLVES) {
					this.moveFrom(subSudoku);
					return;
				}
			}
		
		if (firstSolved != null) {
			this.moveFrom(firstSolved);
			return;
		}
		
		if (state == SudokuState.UNSOLVED)
			state = SudokuState.UNSOLVABLE;
	}
	
	private void moveFrom(Sudoku other) {
		if (this == other)
			return; 
		
		this.blocks = other.blocks;
		this.cells  = other.cells;
		this.state  = other.state;
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

	
	@Override
 	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 9; ++j)
				builder.append(cells[i * 9 + j].getValue());
			builder.append('\n');
		}
		
		for (int j = 0; j < 9; ++j)
			builder.append(cells[72 + j].getValue());
		
		return builder.toString();
	}
	
	
	private Cell[]  cells;
	private List<Block> blocks;
	
	private SudokuState state;
}
