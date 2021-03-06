package sudoku;

import java.util.ArrayList;
import java.util.List;

import static sudoku.Properties.BOARD_WIDTH;
import static sudoku.Properties.CELL_NUMBER;

public class Sudoku {

    public Sudoku() {
        cells = new ArrayList<>();
        state = SudokuState.UNSOLVED;

        for (int i = 0; i < CELL_NUMBER; ++i)
            cells.add(new Cell());
    }

    public Sudoku(String str) {
        this();

        str = str.replaceAll("\n+", "");
        if (str.length() != CELL_NUMBER)
            throw new IllegalArgumentException("Wrong size of a string");

        for (int i = 0; i < BOARD_WIDTH; ++i)
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                char ch = str.charAt(BOARD_WIDTH * i + j);
                if (ch != '0')
                    set(i, j, Character.getNumericValue(ch));
            }
    }

    public Sudoku(final Sudoku other) {
        this();

        for (int i = 0; i < CELL_NUMBER; ++i)
            if (other.get(i).hasValue())
                this.set(i, other.get(i).getValue());
    }


    public void set(int cellIndex, int value) {
        if (value == 0)
            return;

        if (cellIndex >= CELL_NUMBER || cellIndex < 0)
            throw new IllegalArgumentException("Index must be from 0 to 80");

        cells.get(cellIndex).setValue(value);
    }

    public void set(int rowIndex, int columnIndex, int value) {
        if (rowIndex < 0 || rowIndex >= BOARD_WIDTH
                || columnIndex < 0 || columnIndex > BOARD_WIDTH)
            throw new IllegalArgumentException("There are only 9 rows and 9 columns. Max index is 8");

        set(rowIndex * BOARD_WIDTH + columnIndex, value);
    }


    public Cell get(int cellIndex) {
        return cells.get(cellIndex);
    }

    public Cell get(int row, int column) {
        if (row < 0 || row >= BOARD_WIDTH || column < 0 || column >= BOARD_WIDTH)
            throw new IllegalArgumentException("There are only 9 rows and 9 columns. Max index is 8");

        return cells.get(row * BOARD_WIDTH + column);
    }


    public Sudoku getSolve() {
        SudokuSolver solver = new SudokuSolver(this);
        solver.solve();
        return solver;
    }

    public SudokuState getState() {
        return state;
    }


    protected void setState(SudokuState newState) {
        state = newState;
    }

    protected void moveFrom(Sudoku other) {
        if (this != other) {
            this.cells = other.cells;
            this.state = other.state;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < BOARD_WIDTH - 1; ++i) {
            for (int j = 0; j < BOARD_WIDTH; ++j)
                builder.append(cells.get(i * BOARD_WIDTH + j).getValue());
            builder.append('\n');
        }

        for (int j = 0; j < BOARD_WIDTH; ++j)
            builder.append(cells.get((CELL_NUMBER - BOARD_WIDTH) + j).getValue());

        return builder.toString();
    }

    private List<Cell> cells;
    private SudokuState state;
}