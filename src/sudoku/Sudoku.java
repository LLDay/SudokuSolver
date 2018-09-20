package sudoku;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {
	public Sudoku() {
		cells 	= new Cell[81];
		rows 	= new Block[9];
		columns	= new Block[9];
		squares	= new Block[9];
		
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
				
			rows[i]    = new Block(rowList);
			columns[i] = new Block(columnList);
			squares[i] = new Block(squaresList);
		}		
	}
	
	public Sudoku(final Sudoku other) {
		super();
		
		for (int i = 0; i < 81; ++i)
			this.cells[i] = other.cells[i];
	}
	
	
	public void set(int cellNumber, int value) {
		if (cellNumber > 81 || cellNumber < 1)
			throw new IllegalArgumentException("Number must be from 1 to 81");
		cells[cellNumber - 1].setValue(value);
	}
	
	public void solve() {
		
	}
	
	private List<Invariant> inList;
	private Cell[]  cells;
	private Block[] rows;
	private Block[] columns;
	private Block[] squares;
}
