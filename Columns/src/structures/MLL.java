package structures;

import nodes.Node_SLL;
import nodes.Node_MLL;

public class MLL {

	private Node_MLL head;

	public MLL() {
		head = null;
	}

	public void newColumn(int i) {
		if (head == null) {
			Node_MLL n = new Node_MLL(i);
			head = n;
		} else {
			Node_MLL temp = head;
			while (temp.getNext() != null) {
				temp = temp.getNext();
			}
			Node_MLL n = new Node_MLL(i);
			temp.setNext(n);
		}
	}

	public Node_MLL getHead() {
		return head;
	}

	public void setHead(Node_MLL head) {
		this.head = head;
	}

	public void appendTo(Node_SLL NodeToAppend, int Columns) {
		if (head == null) {
			System.out.print("error");
		} else {
			Node_MLL temp = head;
			while (temp != null) {
				if ((int) temp.getData() == Columns) {
					Node_SLL temp2 = temp.getChild();

					if (temp2 == null) {
						Node_SLL n = NodeToAppend;
						temp.setChild(n);
					} else {
						while (temp2.getNext() != null) {
							temp2 = temp2.getNext();
						}
						Node_SLL n = NodeToAppend;
						temp2.setNext(n);
					}
				}
				temp = temp.getNext();
			}
		}
	}

	public void addTo(Object element, int Columns) {
		if (head == null) {
			System.out.print("error");
		} else {
			Node_MLL temp = head;
			while (temp != null) {
				if ((int) temp.getData() == Columns) {

					Node_SLL temp2 = temp.getChild();

					if (temp2 == null) {
						Node_SLL n = new Node_SLL(element);
						temp.setChild(n);
					} else {
						while (temp2.getNext() != null) {
							temp2 = temp2.getNext();
						}
						Node_SLL n = new Node_SLL(element);
						temp2.setNext(n);
					}
				}
				temp = temp.getNext();

			}
		}
	}

	public int size(int Columns) {
		int count = 0;

		Node_MLL parent = head;
		while (parent != null) {
			if ((int) parent.getData() == Columns) {
				Node_SLL child = parent.getChild();
				if (child == null) {
					return count;
				} else {
					Node_SLL childTemp = child;
					while (childTemp != null) {
						count++;
						childTemp = childTemp.getNext();
					}
				}
				return count;
			}
			parent = parent.getNext();
		}

		return count;
	}

	public void display() {
		if (head == null) {
			System.out.print("linked list is empty");
		} else {
			Node_MLL temp = head;
			while (temp != null) {
				System.out.print(temp.getData() + "----->");
				Node_SLL temp2 = temp.getChild();
				while (temp2 != null) {
					System.out.print(temp2.getData() + "   ");
					temp2 = temp2.getNext();

				}
				temp = temp.getNext();
				System.out.println();
			}
		}
	}

	public Node_SLL getLastNode(int column) {
		Node_MLL temp = head;
		while (temp != null) {
			if ((int) temp.getData() == column) {
				Node_SLL temp2 = temp.getChild();
				if (temp2 == null)
					return null;
				while (temp2.getNext() != null)
					temp2 = temp2.getNext();
				return temp2;
			}
			temp = temp.getNext();
		}
		return null;
	}

}
