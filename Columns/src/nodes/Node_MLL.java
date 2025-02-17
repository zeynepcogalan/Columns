package nodes;

public class Node_MLL {
	Object data;
	Node_MLL next;
	Node_SLL child;

	public Node_MLL(Object obj) {
		data = obj;
		next = null;
		child = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object setData) {
		this.data = setData;
	}

	public Node_MLL getNext() {
		return next;
	}

	public void setNext(Node_MLL setNode) {
		this.next = setNode;
	}

	public Node_SLL getChild() {
		return child;
	}

	public void setChild(Node_SLL setNode) {
		this.child = setNode;
	}
}
