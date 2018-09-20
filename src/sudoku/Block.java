package sudoku;

import java.util.List;


public class Block implements Invariant {
	public Block(List<Cell> cells) {
		if (cells.size() != MAX_VALUE)
			throw new IllegalArgumentException("An array must have 9 elements");
		
		this.cells = new Cell[9];
		for (int i = 0; i < MAX_VALUE; ++i)
			this.cells[i] = cells.get(i);
	}
	
	
	@Override
	public void exclude(int excludeValue) {
		for (int i = 0; i < cells.length; ++i) 
			cells[i].exclude(excludeValue);
	}
	
	@Override
	public void attemptToDef() {
		int countDef = 0;
		int det = 362880; //factorial(9)
		int undefIndex = -1;
		
		for (int i = 0; i < cells.length; ++i)
			if (cells[i].hasValue()) {
				countDef++;
				det /= cells[i].getValue();
			}
			else
				undefIndex = i;
		
		if (countDef == 8)
			cells[undefIndex].setValue(det);
	}
	
	private Cell[] cells;
	private static int MAX_VALUE;
}
