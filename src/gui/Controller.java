package gui;

import sudoku.Sudoku;

class Controller {
    private SudokuApp app;

    Controller(SudokuApp application) {
        app = application;
        actionClear();
    }

    void actionSolve() {
        Sudoku sudoku = new Sudoku();

        for (int i = 0; i < 81; i++)
            sudoku.set(i, app.getCell(i));

        Sudoku solve = sudoku.getSolve();
        for (int i = 0; i < 81; i++)
            if (solve.get(i).hasValue())
                app.setCell(i, solve.get(i).getValue());

        switch (solve.getState()) {
            case SOLVED:
                app.setState("Solved successfully");
                break;
            case UNSOLVABLE:
                app.setState("There is no solutions");
                break;
            case MANY_SOLVES:
                app.setState("There are several solutions");
                break;
        }
    }

    void actionClear() {
        app.clearBoard();
        app.setState("Enter your Sudoku");
    }
}
