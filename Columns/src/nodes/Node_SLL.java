package nodes;
public class Node_SLL {
	Object data;
	Node_SLL next;

	public Node_SLL(Object obj) {
		data = obj;
		next = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Node_SLL getNext() {
		return next;
	}

	public void setNext(Node_SLL setNode) {
		this.next = setNode;
	}
}