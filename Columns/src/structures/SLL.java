package structures;

import java.util.Random;

import nodes.Node_SLL;

public class SLL {

	private Node_SLL head;

	public SLL() {
		head = null;
	}

	public Node_SLL getHead() {
		if (head == null) {
			return null;
		} else
			return head;
	}

	public void setHead(Node_SLL node) {
		this.head = node;
	}

	/**
	 * Append a new element to the end of the list
	 * 
	 * @param dataToAdd
	 */
	public void add(Object dataToAdd) {
		if (head == null) {
			Node_SLL newnode = new Node_SLL(dataToAdd);
			head = newnode;
		} else {
			Node_SLL temp = head;
			while (temp.getNext() != null) {
				temp = temp.getNext();
			}
			Node_SLL newnode = new Node_SLL(dataToAdd);
			temp.setNext(newnode);
		}
	}

	/**
	 * Append a new element to the list, to the first place where the new element is
	 * greater than the next one.
	 * 
	 * @param dataToAdd
	 */
	public void addSorted(Object dataToAdd) {
		if (head == null) {
			Node_SLL newnode = new Node_SLL(dataToAdd);
			head = newnode;
		} else {
			if ((int) dataToAdd > (int) head.getData()) {
				Node_SLL newnode = new Node_SLL(dataToAdd);
				newnode.setNext(head);
				head = newnode;
			}
			Node_SLL temp = head;
			Node_SLL previous = null;
			while (temp != null && (Integer) dataToAdd < (Integer) head.getData()) {
				previous = temp;
				temp = temp.getNext();
			}
			if (temp == null) {
				// listenin sonuna ekleme
				Node_SLL newnode = new Node_SLL(dataToAdd);
				previous.setNext(newnode);
			} else if (previous != null) { // listenin ortasýna ekleme
				Node_SLL newnode = new Node_SLL(dataToAdd);
				newnode.setNext(temp);
				previous.setNext(newnode);
			}
		}
	}

	/**
	 * Remove the first node whose data is equal to the parameter.
	 */
	public void deleteNumber(Object dataToDelete) {
		if (head != null) {
			if ((Integer) head.getData() == (Integer) dataToDelete) {
				head = head.getNext();
			} else {
				Node_SLL temp = head;
				Node_SLL previous = null;
				while (temp != null) {
					if ((Integer) temp.getData() == (Integer) dataToDelete) {
						previous.setNext(temp.getNext());
						temp = previous;
						break;
					}
					previous = temp;
					temp = temp.getNext();
				}
			}
		}
	}

	/**
	 * @return Return integer equal to the number of Nodes in current list.
	 */
	public int size() {
		int count = 0;
		if (head == null)
			return count;
		else {
			Node_SLL temp = head;
			while (temp != null) {
				count++;
				temp = temp.getNext();
			}
		}
		return count;
	}

	/**
	 * @return Return true if current list includes a node whose data is equal to the parameter.
	 *         parameter. False otherwise.
	 */
	public Boolean searchNumber(int item) {
		boolean flag = false;

		if (head == null)
			System.out.println("Linked list is empty");
		else {
			Node_SLL temp = head;
			while (temp != null) {
				if (item == (int) temp.getData())
					flag = true;
				temp = temp.getNext();
			}
		}
		return flag;
	}

	/**
	 * Shuffle 5 of each integer between [1,10] randomly to the list (List size will
	 * be 50 at max after method call).
	 */
	public void shuffleFifty() {
		Random rand = new Random();
		int[] numbers = new int[11];
		for (int index = 1; index < 11; index++) {
			numbers[index] = 5;
		}

		while (this.size() < 50) {
			int randInt = rand.nextInt(1, 11);
			if (numbers[randInt] != 0) {
				this.add(randInt);
				numbers[randInt]--;
			}
		}
	}
}
