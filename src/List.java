

public class List {

	private Node head, current, previous;
	private int size;

	public List() {
		head = null;
		current = null;
		previous = null;
		size = 0;
	}

	/*
	 * Adding an element to the top of the List. No options for putting it at the
	 * end or in the middle, because we don't need it for this task.
	 */
	public void add(Object obj) {
		Node n = new Node(obj, head);
		head = n;
		previous = null;
		current = head;

		size += 1;
	}

	public void remove() {
		reset(); // reset because we always want to remove the first object

		if (isEmpty())
			return;
		else {
			head = current.next;
			current = head;

			size -= 1;
		}
	}

	public boolean isEmpty() {
		return head == null;
	}

	public int getSize() {
		return size;
	}

	public void moveOn() {
		current = current.next;
		if (previous == null)
			previous = head;
		else
			previous = previous.next;
	}

	public boolean isLastMember() {
		return (current == null);
	}

	public Object firstElement() {
		return head.data;
	}
	
	public Object currentElement() {
		return current.data;
	}

	public void reset() {
		current = head;
		previous = null;
	}

	/**
	 * Taken from layout and edited
	 * 
	 * @credits https://people.f4.htw-berlin.de/~weberwu/info2/Handouts/List.java
	 */
	public String print(String open, String close) {
		String s = (open == "") ? "[ " : open;
		for (this.reset(); !this.isLastMember(); this.moveOn()) {
			// this wastes a lot of system data
			s = s + current.data + ((current.next != null) ? ", " : "");
		}

//		reset();

		return s + ((close == "") ? " ]" : close);
	}
	
	public boolean equals(Object obj) {
		if(obj == current)
			return true;
		else 
			return false;
	}
}
