import java.util.ArrayList;
import java.util.Collections;
/**
 * A class that solves given Maze
 * @author Chaitanya Mukesh Patel
 * Student ID: A15346478
 * 
 */

public class MazeSolver {

    // The worklist to hold the search as it proceeds.
    private SearchWorklist worklist;
    // The Maze to solve.
    private Maze maze = new Maze();
    // The path to the exit, if any
    private String path="Found the Escape!";
    
    // Begin: Used in MazeApp
    private boolean gameOver = false; // game over, exit has(n't) been found
    private boolean foundExit = false; // exit has been found

    public void setGameOver() {
        gameOver = true;
    }

    public void setFoundExit() {
        foundExit = true;
    }

    public boolean gameOver() {
        return gameOver || foundExit;
    }

    public boolean isFoundExit() {
        return foundExit;
    }

    public void makeEmpty() {
        // remove Squares until empty
        while (!worklist.isEmpty()) {
            worklist.getNext();
        }
    }
    // End: Used in MazeApp

    public SearchWorklist getWorkList() {
        return worklist;
    }

    /** isEmpty
    * @return true if the worklist is empty, false otherwise
    */
    public boolean isEmpty() {
        return worklist.isEmpty();
    }

    /** size of the worklist
    * @return The number of elements in the worklist
    */
    public int size() {
        return worklist.size();
    }

    /** Make a new Solver with a given Maze and Worklist
    * @param theMaze The Maze to solve
    * @theWorklist The worklist to use
    */
    MazeSolver(Maze theMaze, SearchWorklist theWorklist){
        this.maze = theMaze;
        this.worklist = theWorklist;
    }

    /** 
    * Get the Maze object
    * @return the maze
    */
    public Maze getMaze() {
        return this.maze;
    }
    
    /**
     * Solve the maze, if possible.
     * If a solution is found, set the path variable and the 
     * foundExit variable appropriately.
     */
    public void solve() {
        
    	worklist.add(maze.getStart());
    	ArrayList<Square> neighbors = new ArrayList<>();
    	
    	while(!worklist.isEmpty())
    	{    		
    		Square sq = step();
    		if(sq.isEnd())
    		{
    			setPath(sq);
    			this.setFoundExit();    			
    			break;
    		}
    		neighbors = maze.getNeighbors(sq);
    		for(int i=0; i<neighbors.size() ; i++)
    		{
    			//System.out.println("Checking for neighbor " + neighbors.get(i).getRow() + " " + neighbors.get(i).getCol() );
    			if(!neighbors.get(i).isVisited())
    			{
    				//System.out.println("Is Visited is false");
    				if(neighbors.get(i).previous==null)
    				{    		
    					//System.out.println("Adding to list and setting previous");
    					worklist.add(neighbors.get(i));
    					neighbors.get(i).previous = sq;	
    				}	
    			}    			
    		}    		
    	}
    	
    	
        // This function should use next.  You should also create and use any
        // other helper fuctions you find helpful here.
		
    }

    /** Take the next step toward the goal
    * PRECONDITION: The worklist is not empty
    * @return The next Square that has just been visited.
    */
    public Square step() {
    	
    	Square sq = worklist.getNext();
		//System.out.println("Checking for " + sq.getRow() + " " + sq.getCol());
		sq.setVisited();
		//System.out.println("SetVisited " + sq.getRow() + " " + sq.getCol());		
		return sq;
    }

    // Set the squares in the path appropriately and set the path
    // from start to finish.
    public void setPath(Square finish) {

    	ArrayList<Square> pathway = new ArrayList<>();
    	while(!finish.isStart())
    	{
    		pathway.add(finish);
    		finish.setFinalPath();
    		finish = finish.getPrevious();
    	}
    	pathway.add(finish);
    	Collections.reverse(pathway);
    	StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("Found the Escape!");
    	stringbuilder.append("\nPath from start to finish: ");
    	for(int i=0; i<pathway.size(); i++)
    	{
    		stringbuilder.append("[" + pathway.get(i).getRow() + "," + pathway.get(i).getCol() + "] ");
    	}
    	
    	path = stringbuilder.toString();
    	
    }
    
    /**
     * Get the number of elements that are left on the worklist
     * @return The size of the worklist
     */
    public int getWorklistSize() {
        return worklist.size();
    }
    
    /**
     * Get the path from start to exit, if any.
     * @return Path from S to E as a list of coordinates [row,col]
     * If not solvable, the path is a message
     */
    public String getPath() {
        if (foundExit) {
            return path;
        } else {
            path = "Uh Oh!! There's no escape!!";
            return path;
        }
    }
    
    /** A program to solve a maze using either BFS or DFS */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java hw3.MazeSolver mazefile " +
                                "[dfs|bfs]");
            System.exit(0);
        }
        if (!(args[1].equals("dfs") || args[1].equals("bfs"))) {
            System.out.println("Second command line argument must be " +
                "either dfs (depth first search) or bfs "+
                "(breadth first search)");
            System.exit(0);
        }        
        
        Maze myMaze = new Maze();
        boolean load = myMaze.loadMaze(args[0]);
        String output = "";
        if (!load) {
            System.out.println("Oops!! Could not load the Maze");
            System.exit(0);
        }
        
        MazeSolver solver = null;
        if (args[1].equals("bfs")) {
            solver = new MazeSolver(myMaze, new QueueWorklist());
        } else {
            solver = new MazeSolver(myMaze, new StackWorklist());
        }
        solver.solve();
        System.out.println(solver.getPath() +"\n");
        System.out.println(solver.getMaze().toString());
        System.out.println("Number of squares remaining in the "+
            "worklist = "+ solver.getWorklistSize());
    }
}
