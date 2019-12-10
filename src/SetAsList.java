

public class SetAsList implements Set{
	
	private List setAsList = null;
	
	public SetAsList() {
		setAsList = new List();
	}
	
	public void addAll(SetAsList l) {
		for(int i = 0; i < l.size(); i++) {
			if(!this.hasValue(setAsList.currentElement())) {
				setAsList.add(this.setAsList.currentElement());
			}
			setAsList.moveOn();
		}
	}
	
	public void insert(Object obj) {
		if(!this.hasValue(obj))
			setAsList.add(obj);
	}
	
	public void delete(Object obj) {
		if(this.hasValue(obj))
			setAsList.remove();
	}
	
	public boolean isEmpty() {
		return setAsList.isEmpty();
	}
	
	public boolean hasValue(Object obj) {
		if(setAsList.equals(obj))
			return true;
		else if(!setAsList.isLastMember())
			setAsList.moveOn();
		return false;
	}
	
	public String print() {
		return setAsList.print("{", "}");
		
	}
	
	public int size() {
		return setAsList.getSize();
	}
}
