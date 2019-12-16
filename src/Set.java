

public interface Set {
	
	public SetAsList addAll(SetAsList list1, SetAsList list2);
	public void insert(Object obj);
	public void delete(Object obj);
	public boolean isEmpty();
	public boolean hasValue(Object obj);
	public String print();
	public int size();
	public SetAsList copy(SetAsList list);
	public SetAsList subtract(SetAsList list1, SetAsList list2);
	public SetAsList intersection(SetAsList list1, SetAsList list2);
	public boolean isLastMember();
	public Object firstElement();
	public Object currentElement();
	public void moveOn();
	public void reset();
	
}
