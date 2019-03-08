/**
 * A class that defines Stack methods using Adapter Design Pattern  
 * @author Chaitanya Mukesh Patel
 * Student ID: A15346478
 * 
 */
import java.util.LinkedList;

public class StackWorklist implements SearchWorklist {
	
	private LinkedList<Square> stack;
	
	public StackWorklist()
	{
		stack = new LinkedList<Square>();
	}
	
	public void add(Square s)
	{
		stack.addFirst(s);
	}
	
	public Square getNext()
	{
		return stack.removeFirst();
	}
	
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}
	
	public int size()
	{
		return stack.size();		
	}
}
