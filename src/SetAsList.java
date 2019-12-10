
public class SetAsList implements Set {

	private List myList = null;

	public SetAsList() {
		myList = new List();
	}

	public void addAll(SetAsList list) {
		Object listCurrent;
		Object myListCurrent;
		boolean found = false;
		for (int a = 0; a < list.myList.getSize(); a++) {
			if (list.myList.currentElement() == null)
				listCurrent = list.myList.firstElement();
			else
				listCurrent = list.myList.currentElement();
			for (int b = 0; b < myList.getSize(); b++) {
				if (myList.equals(listCurrent)) {
					found = true;
					b = myList.getSize(); 
					System.out.println("----------------------> found");
				}else
					found = false;
				if (!myList.isLastMember())
					myList.moveOn();
				System.out.println(">> myList done");
			}
			if(!found) {
				myList.add(listCurrent);
				System.out.println(">> added");
			}
			if (!list.myList.isLastMember())
				list.myList.moveOn();
			System.out.println("----------------------> list done");
		}
	}

	public void insert(Object obj) {
		if (!this.hasValue(obj)) {
			myList.add(obj);
			System.out.println("Added: " + obj);

		}
	}

	public void delete(Object obj) {
		if (this.hasValue(obj))
			myList.remove();
	}

	public boolean isEmpty() {
		return myList.isEmpty();
	}

	public boolean hasValue(Object obj) {
		List iterator = myList;
		System.out.println("\n");
		for (int i = 0; i < iterator.getSize(); i++) {
			System.out.println("index: " + i);
			if (iterator.equals(obj)) {
				System.out.println("true");
				return true;
			}
			if (!myList.isLastMember())
				myList.moveOn();
			System.out.println("false");
		}

		System.out.println(print());
		return false;
	}

	public String print() {
		return myList.print("{", "}");

	}

	public int size() {
		return myList.getSize();
	}

}
