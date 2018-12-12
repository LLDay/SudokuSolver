package sudoku;

import java.util.ArrayList;
import java.util.List;

class CellValuePublisher {

    CellValuePublisher() {
        blocks = new ArrayList<>();
    }

    void connect(Block block) {
        blocks.add(block);
    }

    void event(int setValue) {
        for (Block it : blocks)
            it.exclude(setValue);
    }

    private List<Block> blocks;
}
