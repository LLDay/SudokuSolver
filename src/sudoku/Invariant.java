package sudoku;

public interface Invariant {
	public void exclude(int excludeValue);
	public void attemptToDef();
}
