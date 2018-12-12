package sudoku;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;


class Block {
	Block(List<Cell> cells) {
		if (cells.size() != 9)
			throw new IllegalArgumentException("A list must have 9 elements");
		
		this.cells = cells;
		
		for (int i = 0; i < 9; ++i) 
			this.cells.get(i).connect(this);
	}

	void exclude(int excludeValue) {
		for (Cell it : cells)
			it.exclude(excludeValue);
	}
	
	boolean isSolved() {
		int def = 3628800; //10!
		for (Cell cell : cells)
			if (cell.hasValue())
				// exclude def /= 1
				def /= (cell.getValue() + 1);
		
		return def == 1;
	}
	
	boolean isErr() {
		int allNumbers = 0;
		
		Set<Integer> numSet = new TreeSet<>();
		for (Cell cell : cells)
			if (cell.hasValue()) {
				numSet.add(cell.getValue());
				allNumbers++;
			}
		
		return numSet.size() != allNumbers;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Cell cell : cells)
			str.append(cell.getValue());
		
		return str.toString();
	}
	
	private List<Cell> cells;
}
