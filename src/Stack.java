

/**
 * Inspired by Debora Weber-Wulff
 * @credits https://people.f4.htw-berlin.de/~weberwu/info2/Handouts/Stack.java
 *
 */
public interface Stack {

	public void push(Object obj) throws StackOverflow; // pushes an Object to the stack
	public void pop() throws StackUnderflow; // pops an Object from the Stack
	public Object top() throws StackUnderflow; // returns the Object on top of the Stack
	public boolean isEmpty(); // checks if the Stack is empty
	public void Empty(); // creates an empty Stack
}
