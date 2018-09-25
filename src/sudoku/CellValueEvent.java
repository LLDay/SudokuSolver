package sudoku;

import java.util.List;

public class CellValueEvent {
	public void connect(Block block) {
		blocks.add(block);
	}
	
	public void event(int excludeValue) {
		for (int i = 0; i < blocks.size(); ++i)
			blocks.get(i).exclude(excludeValue);
	}
	
	private List<Block> blocks;
}
