package sudoku;

public class Cell extends CellValuePublisher {
	
	public enum CellValueState {
		DEFINE,
		UNDEFINE
	}

	Cell() {
		value = 0;
		defCount = 0;
		states = new CellValueState[MAX_VALUE];
	
		for (int i = 0; i < MAX_VALUE; ++i)
			states[i] = CellValueState.UNDEFINE;
	}

	public boolean hasValue() {
		return value != 0;
	}
	
	void setValue(int someValue) {
		// Sudoku is wrong
		if (value != 0 && someValue != value) 
			return;
		
		if (someValue < 1 || someValue > MAX_VALUE)
			throw new IllegalArgumentException("Value must be from 1 to 9");
		
		value = someValue;
		defCount = MAX_VALUE;
		
		for (int i = 0; i < MAX_VALUE; ++i)
			states[i] = CellValueState.DEFINE;
	
		super.event(someValue);
	}
	
	public int getValue() {
		return value;
	}

	boolean canBe(int someValue) {
		if (hasValue() && someValue == value)
			return true;
		
		return states[someValue - 1] == CellValueState.UNDEFINE;
	}
	
	void exclude(int excludeValue) {
		if (excludeValue < 1 || excludeValue > MAX_VALUE)
			throw new IllegalArgumentException("Value must be from 1 to 9");
		
		if (states[excludeValue - 1] != CellValueState.DEFINE) {	
			states[excludeValue - 1] = CellValueState.DEFINE;
			++defCount;
		}
		
		if (defCount == 8)
			attemptToDef();
	}
	
	private void attemptToDef() {
		if (hasValue() || defCount != 8)
			return; 
		
		for (int i = 0; i < MAX_VALUE; ++i)
			if (states[i] != CellValueState.DEFINE) {
				setValue(i + 1);
				break;
			}
	}
	
	@Override
	public String toString() {
		return Integer.toString(value);
	}
	
	private int value;
	private int defCount;
	private CellValueState[] states;
	
	private static int MAX_VALUE = 9;
}
