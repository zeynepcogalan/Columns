package structures;
import nodes.Node_DLL;
import utilities.Player;

public class DLL {
	private Node_DLL head;
	private Node_DLL tail;

	public DLL ()
	{
	    head=null;
	    tail=null;
	}
	
	public Node_DLL getHead() {
		return head;
	}
	public void setHead(Node_DLL head) {
		this.head = head;
	}
	public Node_DLL getTail() {
		return tail;
	}
	public void setTail(Node_DLL tail) {
		this.tail = tail;
	}
	
	public void add (Player dataToAdd)     ////////problemli
	{
	    if (head == null && tail==null) {
	    	Node_DLL newNode = new Node_DLL(dataToAdd); 
	    	head = newNode; /*pointing the first node*/
	    	tail = newNode; /*pointing the last node*/
	    }
	    else {
	    	Node_DLL newnode = new Node_DLL (dataToAdd);
	    	Player player = (Player) head.getData();
	    	Node_DLL temp = head;
	    	if ((double)dataToAdd.getScore() > (double)player.getScore())
	    	{
	    		newnode.setNext(head);
	    		head.setPrev(newnode);
	    		head = newnode;
	    	}
	    	else if(temp.getNext () != null) {
	    		player = (Player) temp.getNext().getData();
	    		while (temp.getNext().getNext() != null &&
	    		        (double)dataToAdd.getScore() < (double)player.getScore())
	    		{
	    		    temp = temp.getNext ();
	    		    player = (Player) temp.getNext().getData();
	    		}
	    		newnode.setPrev (temp);
	    		newnode.setNext (temp.getNext ());
	    		if (temp.getNext () !=null) //adding between nodes
	    		   temp.getNext ().setPrev (newnode);
	    		else //adding to the end
	    		    tail=newnode;
	    		temp.setNext (newnode);
	    	}
	    	else {
	    		newnode.setPrev(temp);
	    		tail = newnode;
	    		temp.setNext(newnode);
	    	}
	    }
	    	
	 }    
	
	public void printListPlayer () {
		 if (head == null)  System.out.print("-");
		 else {
			Node_DLL temp = head;
			while (temp != null) {
				Player data = (Player) temp.getData();
				 System.out.println("                                   " + 
				data.getName() + " "+ data.getSurname() + ":" + " " + data.getScore());
				 System.out.println();
				 temp = temp.getNext();
			 }
		 }
	 }
}
