
public class SetAsList implements Set {

	private List myList = null;
	private SetAsList finalList;

	public SetAsList() {
		myList = new List();
	}

	
	public void addAll(SetAsList list) {
		Object listCurrent;
		boolean found = false;
		for (int a = 0; a < list.myList.getSize(); a++) {
			if (list.myList.currentElement() == null)
				listCurrent = list.myList.firstElement();
			else
				listCurrent = list.myList.currentElement();
			for (int b = 0; b < myList.getSize(); b++) {
				if (myList.equals(listCurrent)) {
					found = true; 
					//System.out.println("----------------------> found");
					break;
				}else
					found = false;
				if (!myList.isLastMember())
					myList.moveOn();
				//System.out.println(">> myList done");
			}
			if(!found) {
				myList.add(listCurrent);
				//System.out.println(">> added");
			}
			if (!list.myList.isLastMember())
				list.myList.moveOn();
			//System.out.println("----------------------> list done");
		}
	}
	
	public SetAsList addAll(SetAsList list1, SetAsList list2) 
	{
		Object listCurrent;
		boolean found = false;
		finalList = new SetAsList();
		
		finalList = copy(list1);
		
		list2.reset();
		finalList.reset();
		
		for (int i = 0; i < list2.size(); i++) 
		{
				listCurrent = list2.currentElement();
			
			for(int j = 0; j < finalList.size(); j++) 
			{
				if (finalList.currentElement().equals(listCurrent)) 
				{
					found = true;
					break;
				}
				else
					found = false;
				
				if (!finalList.isLastMember())
					finalList.moveOn();
				
				if (!found)
					finalList.insert(listCurrent);
			}
			
			if (!list2.isLastMember())
				list2.moveOn();
		}
		System.out.println("New complete List: " + finalList.print());
		return finalList;
		
	}
	
	
	public SetAsList copy(SetAsList list) 
	{
		SetAsList newList = new SetAsList();
		Object listCurrent;
		
		list.reset();
		
		for (int i = 0; i < list.size(); i++ ) 
		{
			listCurrent = list.currentElement();
			newList.insert(listCurrent);
			
			if (!list.isLastMember())
				list.moveOn();
			else
				break;
		}
//		System.out.println("List 1: " + list.print());
//		System.out.println("New List: " + newList.print());
		return newList;
	}
	
	public SetAsList subtract(SetAsList list1, SetAsList list2) 
	{
		finalList = new SetAsList();
		Object listCurrent;
		
		list1.reset();
		list2.reset();
		
		for (int i = 0; i < list1.size(); i++) 
		{
			listCurrent = list1.currentElement();
			
			if (!list2.hasValue(listCurrent))
					finalList.insert(listCurrent);
				
			if (!list1.isLastMember())
				list1.moveOn();
		}
		return finalList;
	}
	
	public SetAsList intersection(SetAsList list1, SetAsList list2) 
	{
		finalList = new SetAsList();
		Object listCurrent;
		
		list1.reset();
		list2.reset();
		
		for (int i = 0; i < list1.size(); i++) 
		{
			listCurrent = list1.currentElement();
			
			if (list2.hasValue(listCurrent))
					finalList.insert(listCurrent);
				
			if (!list1.isLastMember())
				list1.moveOn();
		}
		
		return finalList;
	}

	public void insert(Object obj) {
		if (!this.hasValue(obj)) {
			myList.add(obj);
			//System.out.println("Added: " + obj);
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
		//System.out.println("\n");
		for (int i = 0; i < iterator.getSize(); i++) {
			//System.out.println("index: " + i);
			if (iterator.equals(obj)) {
				//System.out.println("true");
				return true;
			}
			if (!myList.isLastMember())
				myList.moveOn();
			//System.out.println("false");
		}
		//System.out.println(print());
		return false;
	}

	public String print() {
		return myList.print("{", "}");
	}

	public int size() {
		return myList.getSize();
	}
	
	public boolean isLastMember() 
	{
		return myList.isLastMember();
	}
	
	public Object firstElement() 
	{
		return myList.firstElement();
	}
	
	public Object currentElement() 
	{
		return myList.currentElement();
	}
	
	public void moveOn() 
	{
		myList.moveOn();
	}
	
	public void reset() 
	{
		myList.reset();
	}

}
