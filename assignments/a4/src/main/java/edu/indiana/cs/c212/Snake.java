package edu.indiana.cs.c212;

import java.awt.Color;
import java.awt.Point;
import java.util.*;

public class Snake {
	private static final Random randomNumberSource = new Random();

	private static final int BABY_SNAKE_LENGTH = 3;
	private static final int FIRST_PIECE_TO_CHECK_FOR_SELF_HIT = 3;

	private static final Color LIVE_COLOR = Color.GREEN;
	private static final Color DEAD_COLOR = Color.BLACK;
	private static final Color HEAD_COLOR = Color.YELLOW;

	private enum Heading {
		NORTH, SOUTH, EAST, WEST;
	}

	private SnakePit snakePit;
	private Grid grid;

	private List<Point> pieces;
	private Heading heading;
	private Point savedTailPiece;
	private Point target;
	private boolean isAlive;

	public Snake(SnakePit snakePit, Grid grid) {
		this.snakePit = snakePit;
		this.grid = grid;

		this.savedTailPiece = new Point();
		
		int length = BABY_SNAKE_LENGTH;

		int safeHeight = grid.getRowCount() - length * 2;
		int headRow = length + randomNumberSource.nextInt(safeHeight);
		int safeWidth = grid.getColumnCount() - length * 2;
		int headColumn = length + randomNumberSource.nextInt(safeWidth);
		Point firstPoint = new Point(headRow, headColumn);

		pieces = new ArrayList<Point>();
		pieces.add(firstPoint);

		int headingCount = Heading.values().length;
		int randomHeadingIndex = randomNumberSource.nextInt(headingCount);
		Heading randomHeading = Heading.values()[randomHeadingIndex];
		heading = randomHeading;

		createBabySnake(length);
		isAlive = true;

		setTarget();		
	}

	public void changeState() {
		if (isEatingItself() || !isAlive) {
			isAlive = false;
			return;
		}

		if (isEating()) {
			snakePit.removeFruitAt(getHeadPiece());
			growNewHeadPiece();
		} else {
			growNewHeadPiece();
			removeCurrentTailPiece();
		}

		setTarget();
	}		

	public void draw() {
		Color color;

		grid.turnOff(savedTailPiece);

		if (isAlive)
			color = LIVE_COLOR;
		else
			color = DEAD_COLOR;
		for (int i = 1; i < getLength(); ++i) {
			grid.turnOn(getPiece(i), color);
		}

		color = HEAD_COLOR;
		grid.turnOn(getHeadPiece(), color);
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean occupies(Point point) {
		return pieces.contains(point);
	}

	private void growNewHeadPiece() {
		// generate the next head based on safety and the current target			
		setTarget();
		ArrayList<Node> path = AStar(getHeadPiece(), target);
		Point point; 
		if (path == null || path.size() <= 1){
			point = randomMove();							
		} else {
			point = path.get(1).p;
		}
		addNewHeadPiece(point);
	}

	class Node implements Comparable<Node> {
		Point p, goal;
		int dist;
		
		public Node(Point p, Point goal){
			this.p = p;
			this.goal = goal;
			this.dist = heuristicCostEstimate(p, goal);
		}
		@Override
		public int compareTo(Node node) {
			if (this.p.x == node.p.x && this.p.y == node.p.y ){
				return 0;
			} else if (heuristicCostEstimate(this.p, this.goal) > (heuristicCostEstimate(node.p, node.goal))) { 
				return 1;
			} else { 
				return -1;
			}
		}
		
		@Override
		public boolean equals(Object obj){
			if (obj.getClass() != this.getClass()) return false;
			Node node = (Node) obj;
			return this.p.x == node.p.x && this.p.y == node.p.y;
		}
		
		@Override
		public String toString(){
			return p.toString() + "," + dist + "goal=" + goal;
		}
		
	}
	
	public ArrayList<Node> AStar(Point start, Point goal){
		Node startNode = new Node(start, goal);
		Set<Node> visitedSet = new HashSet<Node>();
		PriorityQueue<Node> fringe = new PriorityQueue<Node>(); 		
		fringe.add(startNode);		
		Map<Node, Node> came_from = new HashMap<Node, Node>();
 
		Map<Node, Integer> g_score  = new HashMap<Node, Integer>();
		g_score.put(startNode, 0);

		int count = 0;
	     while (!fringe.isEmpty() && count < 100){
	         Node current = fringe.poll();    	 
	         if (current.p.x == goal.x && current.p.y == goal.y){
	             ArrayList<Node> path = reconstruct_path(came_from, current);
	             path.add(new Node(start, goal));
	             return path;
	         }
	         count++;
	         visitedSet.add(current);
	         for (Node neighbor : neighborNodes(current.p, goal)){
	             int tentative_g_score = g_score.get(current) + 1;
	             if (visitedSet.contains(neighbor) && tentative_g_score >= g_score.get(neighbor))
	                     continue;
	 
	             if (!g_score.containsKey(neighbor) || tentative_g_score < g_score.get(neighbor)){
	                 came_from.put(neighbor, current);
	                 g_score.put(neighbor, tentative_g_score);                                  
	                 if (!fringe.contains(neighbor))
	                     fringe.add(neighbor);
	             }
	         }         
	     }
	     
	     return null;   
	}
	
	private Point randomMove(){
		Random random = new Random();
		ArrayList<Node> directions = neighborNodes(getHeadPiece(), target);
		if (directions.size() > 0)
			return directions.get(random.nextInt(directions.size( ))).p;
		else {
			return new Point(getHeadPiece().x + 1, getHeadPiece().y);
		}
	}
	
    private ArrayList<Node> neighborNodes(Point current, Point goal) {
    	ArrayList<Node> neighbors = new ArrayList<Node>();
    	ArrayList<Point> potential = new ArrayList<Point>();
    	potential.add((new Point(current.x + 1, current.y)));
    	potential.add((new Point(current.x - 1, current.y)));
    	potential.add((new Point(current.x, current.y + 1)));
    	potential.add((new Point(current.x, current.y - 1)));
    	for (Point p: potential){
    		if (!occupies(p) && grid.contains(p)){
    			neighbors.add(new Node(p, goal));
    		}
    	}
    	
    	return neighbors;
    }

	//Manhattan distance
	protected Integer heuristicCostEstimate(Point start, Point goal) {
		return (Math.abs(start.x - goal.x) + Math.abs(start.y - goal.y)); 
	}
		 
	 private ArrayList<Node> reconstruct_path(Map<Node, Node> came_from, Node current_node){
	     if (came_from.containsKey(current_node)){ 
	    	 ArrayList<Node> path = reconstruct_path(came_from, came_from.get(current_node));
	    	 path.add(current_node);
	         return path;
	     }
	     else {
	    	 ArrayList<Node> path = new ArrayList<Node>();
	    	 path.add(current_node);
	         return path;
	     }
	        		 
	 }

	private final void createBabySnake(int length) {
		int row = getHeadRow();
		int column = getHeadColumn();
		Point point;
		switch (heading) {
		case NORTH: {
			for (int i = 1; i < length; i++) {
				point = new Point(row + i, column);
				pieces.add(point);
			}
			break;
		}
		case SOUTH: {
			for (int i = 1; i < length; i++) {
				point = new Point(row - i, column);
				pieces.add(point);
			}
			break;
		}
		case EAST: {
			for (int i = 1; i < length; i++) {
				point = new Point(row, column + i);
				pieces.add(point);
			}
			break;
		}
		case WEST: {
			for (int i = 1; i < length; i++) {
				point = new Point(row, column - i);
				pieces.add(point);
			}
			break;
		}
		}
	}


	private final void setTarget() {		
		target = getRandom();
		//FIXME uncomment when you add the getPoint() method in fruit
/*		if (snakePit.hasFruit()) {
			List<Fruit> fruits = snakePit.getFruits();
			Fruit closest = fruits.get(0);
			int lowDist = Integer.MAX_VALUE;
			for (Fruit fruit : fruits){
				
				//int distance = heuristicCostEstimate(getHeadPiece(), fruit.getPoint());
				if (distance < lowDist){
					lowDist = distance;
					closest = fruit;
				}
			}

			target = closest.getPoint();
		}*/
	}
	
	public Point getRandom() {
		Random random = new Random();
		Point goal = new Point(random.nextInt(grid.getColumnCount()),random.nextInt(grid.getRowCount()));;
		
		while (occupies(goal)){
			snakePit.considerAddingAFruitAt(goal);
			goal = new Point(random.nextInt(grid.getColumnCount()),random.nextInt(grid.getRowCount()));
		}			
		return goal;
	}

	private boolean isEating() {
		return snakePit.hasFruitAt(getHeadPiece());
	}

	private boolean isEatingItself() {
		Point head = getHeadPiece();
		for (int i = FIRST_PIECE_TO_CHECK_FOR_SELF_HIT; i < getLength(); i++) {
			if (head.equals(getPiece(i))) {
				return true;
			}
		}

		return false;
	}

	private int getHeadRow() {
		return getRow(getHeadPiece());
	}

	private int getHeadColumn() {
		return getColumn(getHeadPiece());
	}

	private Point getHeadPiece() {
		return getPiece(0);
	}

	private void addNewHeadPiece(Point point) {
		pieces.add(0, point);
	}

	private Point getTailPiece() {
		return getPiece(getLength() - 1);
	}

	private void removeTailPiece() {
		pieces.remove(getLength() - 1);
	}

	private void removeCurrentTailPiece() {
		savedTailPiece = getTailPiece();
		removeTailPiece();
	}

	private Point getPiece(int i) {
		return pieces.get(i);
	}

	private int getLength() {
		return pieces.size();
	}

	private int getRow(Point point) {
		return point.x;
	}

	private int getColumn(Point point) {
		return point.y;
	}

	@Override
	public String toString() {
		String string = "";
		string += "snake is alive is " + isAlive + "\n";
		string += "length is " + getLength() + "\n";
		string += "heading is " + heading + "\n";
		string += "target is ";
		string += "(" + getRow(target) + "," + getColumn(target) + ")" + "\n";
		return string;
	}
}
