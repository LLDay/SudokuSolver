package sudoku;

public class Cell implements Invariant {
	
	public Cell() {
		value = 0;
		defCount = 0;
		states = new CellValueState[MAX_VALUE];
	
		for (int i = 0; i < MAX_VALUE; ++i)
			states[i] = CellValueState.UNDEFINE;
	}
	
	public Cell(int cellValue) {
		if (cellValue < 1 || cellValue > MAX_VALUE)
			throw new IllegalArgumentException("Cell must have value from 1 to 9");
		
		value = cellValue;
		defCount = 0;
		states = new CellValueState[MAX_VALUE];
		
		for (int i = 0; i < MAX_VALUE; ++i)
			states[i] = CellValueState.DEFINE;
	}
	
	
	public boolean hasValue() {
		return value != 0;
	}
	
	public void setValue(int someValue) {
		if (value != 0)
			throw new IllegalStateException("Value has been defined");
		
		if (someValue < 1 || someValue > MAX_VALUE)
			throw new IllegalArgumentException("Value must be from 1 to 9");
		
		value = someValue;
		defCount = MAX_VALUE;
		
		for (int i = 1; i < MAX_VALUE; ++i)
			states[i] = CellValueState.DEFINE;
	
	}
	
	public int getValue() {
		return value;
	}
	
	
	public boolean canBe(int someValue) {
		if (hasValue() && someValue == value)
			return true;
		
		return states[someValue - 1] == CellValueState.UNDEFINE;
	}
	
	@Override
	public void exclude(int excludeValue) {
		if (excludeValue < 1 || excludeValue > MAX_VALUE)
			throw new IllegalArgumentException("Value must be from 1 to 9");
		
		states[excludeValue - 1] = CellValueState.DEFINE;
		++defCount;
		
		if (defCount == 8)
			attemptToDef();
	}
	
	@Override
	public void attemptToDef() {
		if (hasValue() || defCount != 8)
			return; 
		
		for (int i = 0; i < MAX_VALUE; ++i)
			if (states[i] != CellValueState.DEFINE) {
				setValue((int) (i + 1));
				break;
			}
	}
	
	
	private int value;
	private int defCount;
	private CellValueState[] states;
	
	private static int MAX_VALUE = 9;
}