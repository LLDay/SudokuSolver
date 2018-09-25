package sudoku;

import java.util.List;


public class Block implements Invariant {
	public Block(List<Cell> cells) {
		if (cells.size() != 9)
			throw new IllegalArgumentException("An array must have 9 elements");
		
		this.cells = new Cell[9];
		for (int i = 0; i < 9; ++i) {
			this.cells[i] = cells.get(i);
			this.cells[i].connect(this);
		}
		
		state = BlockState.UNSOLVED;
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
	
	
	public int getValue(int index) {
		return cells[index].getValue();
	}
	
	private Cell[] cells;
	private BlockState state;
}
