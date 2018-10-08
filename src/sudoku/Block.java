package sudoku;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class Block {

	public Block(List<Cell> cells) {
		if (cells.size() != 9)
			throw new IllegalArgumentException("A list must have 9 elements");
		
		this.cells = cells;
		
		for (int i = 0; i < 9; ++i) 
			this.cells.get(i).connect(this);
	}
	
	
	public void exclude(int excludeValue) {
		for (Cell it : cells)
			it.exclude(excludeValue);
	}
	
	public int getValue(int index) {
		return cells.get(index).getValue();
	}
	
	public boolean isSolved() {
		int def = 39916800; //11!
		for (Cell cell : cells)
			if (cell.hasValue())
				// exclude def /= 1
				def /= (cell.getValue() + 1);
		
		return def == 1;
	}
	
	public boolean isErr() {
		Set<Integer> numSet = new TreeSet<>();
		for (Cell cell : cells)
			if (cell.hasValue())
				numSet.add(cell.getValue());
		
		return numSet.size() != 9;
	}
	
	private List<Cell> cells;
}
