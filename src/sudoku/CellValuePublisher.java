package sudoku;

import java.util.ArrayList;
import java.util.List;

class CellValuePublisher {

    CellValuePublisher() {
        unions = new ArrayList<>();
    }

    void connect(CellUnion union) {
        unions.add(union);
    }

    void event(int setValue) {
        for (CellUnion it : unions)
            it.exclude(setValue);
    }

    private List<CellUnion> unions;
}
