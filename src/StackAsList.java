

public class StackAsList implements Stack {

	private List stackList;

	public StackAsList() {
		Empty();
	}

	public void push(Object obj) throws StackOverflow {
		// not throwing any exception here, because the list is infinite and
		// there can't be a StackOverflow unless the memory is full
		stackList.add(obj);
	}

	public void pop() throws StackUnderflow {
		if (stackList.isEmpty())
			throw new StackUnderflow();
		else
			stackList.remove();
	}

	public Object top() throws StackUnderflow {
		if (stackList.isEmpty())
			throw new StackUnderflow();
		else
			return stackList.firstElement();
	}

	public boolean isEmpty() {
		return stackList.isEmpty();
	}

	public void Empty() {
		stackList = new List();
	}

	public String toString() {
		return stackList.print("Top: ", " :Bottom");
	}

}
