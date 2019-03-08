/**
 * A class that defines Queue methods using Adapter Design Pattern  
 * @author Chaitanya Mukesh Patel
 * Student ID: A15346478
 * 
 */
import java.util.LinkedList;

public class QueueWorklist implements SearchWorklist{
	
	private LinkedList<Square> queue;	
	
	public QueueWorklist()
	{
		queue = new LinkedList<Square>();
	}
	
	public void add(Square s)
	{
		queue.addLast(s);	
	}
	
	public Square getNext()
	{
		return queue.removeFirst();		
	}
	
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	public int size()
	{
		return queue.size();
	}
	
}
