

/**
 * @credits https://moodle.htw-berlin.de/pluginfile.php/777008/mod_resource/content/1/04_datastructures_lists-start.pdf
 */

public class Node {

	Object data;
	Node next;
	
	// constructors
	public Node(Object obj) {
		data = obj;
	}

	public Node(Object obj, Node next) 
	{
		data = obj;
		this.next = next;
	}
}
