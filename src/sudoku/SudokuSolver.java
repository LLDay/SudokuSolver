package sudoku;

import java.util.ArrayList;
import java.util.List;

import static sudoku.SudokuState.*;

public class SudokuSolver extends Sudoku {

	private SudokuSolver() {
		blocks = new ArrayList<Block>();

		for (int i = 0; i < 9; ++i) {
			List<Cell> rowList 		= new ArrayList<>();
			List<Cell> columnList 	= new ArrayList<>();
			List<Cell> squaresList 	= new ArrayList<>();

			for (int j = 0; j < 9; ++j)
				rowList.add(super.get(9 * i + j));

			for (int j = 0; j < 9; ++j)
				columnList.add(super.get(9 * j + i));

			for (int j = 0; j < 3; ++j) {
				int startIndex = (i / 3) * 27 + (i % 3) * 3 + (9 * j);
				for (int k = 0; k < 3; k++) {
					squaresList.add(super.get(startIndex + k));
				}
			}

			blocks.add(new Block(rowList));
			blocks.add(new Block(columnList));

			// Cells save this block
			new Block(squaresList);
		}
	}

	public SudokuSolver(final Sudoku other) {
		this();

		for (int i = 0; i < 81; ++i)
			if (other.get(i).hasValue())
				this.set(i, other.get(i).getValue());
	}

	public void solve() {
		defineState();
		if (getState() != UNSOLVED)
			return;

		int undefCellIndex = getUndefinedCellIndex();

		SudokuSolver firstSolved = null;

		for (int i = 1; i < 10; ++i)
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
				}

				else if (subSudoku.getState() == MANY_SOLVES) {
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
		for (int i = 0; i < 81; ++i)
			if (!super.get(i).hasValue())
				return i;

		return -1;
	}

	private void defineState() {
		if (getState() != UNSOLVED)
			return;

		int solvedBlocks = 0;
		for (Block block : blocks) {
			if (block.isSolved())
				solvedBlocks++;

			if (block.isErr()) {
				setState(UNSOLVABLE);
				break;
			}
		}

		if (solvedBlocks == 18)
			setState(SOLVED);
	}

	private List<Block> blocks;
}