package nodes;

public class Node_DLL {
	Object data;
	Node_DLL next;
	Node_DLL prev;

	public Node_DLL(Object obj) {
		data = obj;
		next = null;
		prev = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Node_DLL getNext() {
		return next;
	}

	public void setNext(Node_DLL setNode) {
		this.next = setNode;
	}

	public Node_DLL getPrev() {
		return prev;
	}

	public void setPrev(Node_DLL prev) {
		this.prev = prev;
	}
	
	
}
