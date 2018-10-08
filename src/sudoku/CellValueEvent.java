package sudoku;

import java.util.ArrayList;
import java.util.List;

public class CellValueEvent {
	
	public CellValueEvent() {
		blocks = new ArrayList<Block>();
	}
	
	public void connect(Block block) {
		blocks.add(block);
	}
	
	public void event(int setValue) {
		for (Block it : blocks)
			it.exclude(setValue);
	}
	
	private List<Block> blocks;
}
