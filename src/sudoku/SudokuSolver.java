package sudoku;

import java.util.ArrayList;
import java.util.List;

import static sudoku.Properties.*;
import static sudoku.SudokuState.*;

class SudokuSolver extends Sudoku {

    private SudokuSolver() {
        unions = new ArrayList<>();

        for (int i = 0; i < BOARD_WIDTH; ++i) {
            List<Cell> rowList = new ArrayList<>();
            List<Cell> columnList = new ArrayList<>();
            List<Cell> squaresList = new ArrayList<>();

            for (int j = 0; j < BOARD_WIDTH; ++j)
                rowList.add(super.get(BOARD_WIDTH * i + j));

            for (int j = 0; j < BOARD_WIDTH; ++j)
                columnList.add(super.get(BOARD_WIDTH * j + i));

            for (int j = 0; j < SECTOR_WIDTH; ++j) {
                int rowStartIndex = (i / SECTOR_WIDTH) * SECTOR_WIDTH * BOARD_WIDTH;
                int columnStartIndex = (i % SECTOR_WIDTH) * SECTOR_WIDTH + (BOARD_WIDTH * j);
                int startIndex = rowStartIndex + columnStartIndex;

                for (int k = 0; k < SECTOR_WIDTH; k++) {
                    squaresList.add(super.get(startIndex + k));
                }
            }
            unions.add(new CellUnion(rowList));
            unions.add(new CellUnion(columnList));

            // Cells save this
            new CellUnion(squaresList);
        }
    }

    SudokuSolver(final Sudoku other) {
        this();

        for (int i = 0; i < CELL_NUMBER; ++i)
            if (other.get(i).hasValue())
                this.set(i, other.get(i).getValue());
    }

    void solve() {
        defineState();
        if (getState() != UNSOLVED)
            return;

        int undefCellIndex = getUndefinedCellIndex();

        SudokuSolver firstSolved = null;

        for (int i = 1; i < BOARD_WIDTH + 1; ++i)
            if (super.get(undefCellIndex).canBe(i)) {
                SudokuSolver subSudoku = new SudokuSolver(this);

                subSudoku.set(undefCellIndex, i);
                subSudoku.solve();

                if (subSudoku.getState() == SOLVED) {

                    if (firstSolved == null)
                        firstSolved = subSudoku;
                    else
                        firstSolved.setState(MANY_SOLVES);

                    if (firstSolved.getState() == MANY_SOLVES) {
                        this.moveFrom(firstSolved);
                        return;
                    }
                } else if (subSudoku.getState() == MANY_SOLVES) {
                    this.moveFrom(subSudoku);
                    return;
                }
            }

        if (firstSolved != null) {
            this.moveFrom(firstSolved);
            return;
        }

        if (getState() == UNSOLVED)
            setState(UNSOLVABLE);
    }

    private int getUndefinedCellIndex() {
        for (int i = 0; i < CELL_NUMBER; ++i)
            if (!super.get(i).hasValue())
                return i;

        return -1;
    }

    private void defineState() {
        if (getState() != UNSOLVED)
            return;

        int solvedUnions = 0;
        for (CellUnion union : unions) {
            if (union.isSolved())
                solvedUnions++;

            if (union.isErr()) {
                setState(UNSOLVABLE);
                break;
            }
        }

        if (solvedUnions == 2 * BOARD_WIDTH)
            setState(SOLVED);
    }

    private List<CellUnion> unions;
}