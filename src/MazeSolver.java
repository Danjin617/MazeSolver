import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;  // Needed for ActionListener
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;

public class MazeSolver extends JFrame implements ActionListener{
	
	class GridSquare {
		
		private int row, col;
		private int parRow, parCol;
		private boolean visited;
		private boolean valid;
		private boolean start;
		private boolean end;
		private boolean alongPath;
		
		public GridSquare (int row, int col) {
			this.row = row;
			this.col = col;
			parRow= -1;
			parCol= -1;
			visited = false;
			valid = true;
			start = false;
			end = false;
			alongPath = false;
		}
		
		public int getRow() {
			return row;
		}
		
		public int getCol() {
			return col;
		}
		
		public int getParRow() {
			return parRow;
		}
		
		public int getParCol() {
			return parCol;
		}
		
		public boolean getVisited() {
			return visited;
		}
		
		public boolean getValid() {
			return valid; 
		}
		
		
		public void visitSquare () {
			visited = true;
		}
		
		public void turnIntoWall () {
			valid = !valid;
		}
		
		public boolean getStart() {
			return start;
		}
		
		public boolean getEnd() {
			return end;
		}
		
		public void clearAttributes() {
			parRow = -1;
			parCol = -1;
			visited = false;
			alongPath = false;
		}
		

		
	}
	
	class Maze extends JPanel {
		
		public Maze (int x, int y) {
			this.setPreferredSize(new Dimension(x, y));
		}
		
		public void paintComponent(Graphics g) {
			visualize(g);
		}
	}

	
	private GridSquare[][] gridArr;
	static JButton start;
	static JButton end;
	static JButton wall;
	static JButton runBFS;
	boolean addStart;
	boolean addEnd;
	boolean addWall;
	int startRow;
	int startCol;
	int endRow;
	int endCol;
	int width;
	int height;
	
	public MazeSolver(int rows, int cols) {
		
		gridArr = new GridSquare[rows][cols];
		addStart = false;
		addEnd = false;
		addWall = false;
		startRow = -1;
		startCol = -1;
		endRow = -1;
		endCol = -1;
		width = cols * 50;
		height = rows * 50;
		
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				gridArr[i][j] = new GridSquare(i, j);
			}
		}
		
		//create content pane and set layout
		JPanel window = new JPanel();
		window.setLayout(new FlowLayout());
		JPanel buttons = new JPanel();
		start = new JButton("Select Start Point");
		start.addActionListener(this);
		end = new JButton("Select End Point");
		end.addActionListener(this);
		wall = new JButton("Add Wall");
		wall.addActionListener(this);
		runBFS = new JButton("Find Path");
		runBFS.addActionListener(this);
		
		buttons.add(start);
		buttons.add(end);
		buttons.add(wall);
		buttons.add(runBFS);
		buttons.setLayout(new FlowLayout());
		Maze maze = new Maze(width + 50, height + 50); // _____________________________________________________________________________________________
		maze.setLayout(new FlowLayout());
		
		window.add(buttons);
		window.add(maze);
		
		setContentPane(window);
		pack();
		setTitle ("Maze Solver");
        setSize (500, 500); // ___________________________________________________________________________________________________________
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setLocation(0,0);
        this.setVisible(true);
        
        
		
		
		maze.addMouseListener(new MouseAdapter(){
			public void mousePressed (MouseEvent e){
                int colPressed = e.getX()/50;
                int rowPressed = e.getY()/50;
                if (rowPressed >= 0 && rowPressed < gridArr.length && colPressed >= 0 && colPressed < gridArr[0].length) {
		            if (addStart == true && gridArr[rowPressed][colPressed].getValid() == true) {
		            	if (!(startRow == -1 && startCol == -1)) {
		            		gridArr[startRow][startCol].start = false;
		            	}
		            	startRow = rowPressed;
		            	startCol = colPressed;
		            	gridArr[rowPressed][colPressed].start = true;
		            	clearPath();
		            } else if (addEnd == true && gridArr[rowPressed][colPressed].getValid() == true) {
		            	if (!(endRow == -1 && endCol == -1)) {
		            		gridArr[endRow][endCol].end = false;
		            	}
		            	endRow = rowPressed;
		            	endCol = colPressed;
		            	gridArr[rowPressed][colPressed].end = true;
		            	clearPath();
		            } else if (addStart == false && addEnd == false && addWall == true && gridArr[rowPressed][colPressed].getStart() == false && gridArr[rowPressed][colPressed].getEnd() == false ) {
		            	gridArr[rowPressed][colPressed].turnIntoWall();
		            	clearPath();
		            }
                }
                System.out.println(rowPressed + " " + colPressed);
                repaint();
			}
            public void mouseExited (MouseEvent e){

            }
            public void mouseEntered (MouseEvent e){

            }
            public void mouseReleased (MouseEvent e){

            }
            public void mouseClicked (MouseEvent e){

            }
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Select Start Point")) {
			addStart = true;
			addEnd = false;
			addWall = false;
		}
		if (e.getActionCommand().equals("Select End Point")) {
			addStart = false;
			addEnd = true;
			addWall = false;
		}
		if (e.getActionCommand().equals("Add Wall")) {
			addStart = false;
			addEnd = false;
			addWall = true;
		}
		if (e.getActionCommand().equals("Find Path")) {
			bfs(startRow, startCol, gridArr[0].length, gridArr.length);
			backtrack(startRow, startCol, endRow, endCol);
			repaint();
		}
	}
	
	public void clearPath() {
		for (int i = 0; i < gridArr.length; i++) {
			for (int j = 0; j < gridArr[0].length; j++) {
				gridArr[i][j].clearAttributes();
			}
		}
	}
	
	public void bfs(int startX, int startY, int gridCols, int gridRows) {
		Queue<GridSquare> q = new LinkedList<GridSquare>();
		q.add(gridArr[startX][startY]);
		
		while (!q.isEmpty()) {
			int size = q.size();
			
			for(int i =0; i <size; i++) {
				GridSquare temp = q.remove();
				int r = temp.getRow();
				int c = temp.getCol();
				
				if((r+1)<gridRows && !gridArr[r+1][c].getVisited() && gridArr[r+1][c].getValid()) {
					q.add(gridArr[r+1][c]);
					gridArr[r+1][c].visited = true;
					gridArr[r+1][c].parRow = r;
					gridArr[r+1][c].parCol = c;
				}
				
				if((r-1)>=0 && !gridArr[r-1][c].getVisited() && gridArr[r-1][c].getValid()) {
					q.add(gridArr[r-1][c]);
					gridArr[r-1][c].visited = true;
					gridArr[r-1][c].parRow = r;
					gridArr[r-1][c].parCol = c;
				}
				
				if((c+1)<gridCols && !gridArr[r][c+1].getVisited() && gridArr[r][c+1].getValid()) {
					q.add(gridArr[r][c+1]);
					gridArr[r][c+1].visited = true;
					gridArr[r][c+1].parRow = r;
					gridArr[r][c+1].parCol = c;
				}
				
				if((c-1)>=0 && !gridArr[r][c-1].getVisited() && gridArr[r][c-1].getValid()) {
					q.add(gridArr[r][c-1]);
					gridArr[r][c-1].visited = true;
					gridArr[r][c-1].parRow = r;
					gridArr[r][c-1].parCol = c;
				}
				
				
			}
		}
		
		
	}
	
	public void backtrack(int startX, int startY, int endX, int endY) {
		ArrayList<Integer> pathX = new ArrayList<>();
		ArrayList<Integer> pathY = new ArrayList<>();
		int curX = endX;
		int curY = endY;
		pathX.add(curX);
		pathY.add(curY);
		gridArr[curX][curY].alongPath = true;
		
		
		if (gridArr[curX][curY].getParRow() == -1 || gridArr[curX][curY].getParCol() == -1) {
			System.out.println("No path found.");
		}
		
		while (!(startX == curX && startY == curY)) {
//			System.out.println("old = " + curX+ " "+ curY);
			int tempX = gridArr[curX][curY].getParRow();
			int tempY = gridArr[curX][curY].getParCol();
//			System.out.println("new = " + curX+ " "+ curY);
			curX = tempX;
			curY = tempY;
			pathX.add(curX);
			pathY.add(curY);
			gridArr[curX][curY].alongPath = true;
		}
		
		//for (int i = 0; i < pathX.size(); i++) {
    	//	System.out.println(pathX.get(i) + " " + pathY.get(i));
    	//}
//		System.out.println("pathX size is "+ pathX.size());
//		System.out.println("!!!!!!!!" + gridArr[1][0].getParRow() + " " + gridArr[1][0].getParCol());
//		System.out.println("ASDHAKSLJ" + gridArr[2][0].getParRow() + " " + gridArr[2][0].getParCol());
	}
	
	public void visualize(Graphics g) {
		int squareWidth = 45;
		int spacing = 5;
		g.fillRect(0, 0, gridArr[0].length * (squareWidth + spacing) + spacing, gridArr.length * (squareWidth + spacing) + spacing);
		for (int i = 0; i < gridArr.length; i++) {
			for (int j = 0; j < gridArr[0].length; j++) {
				if (gridArr[i][j].getValid() == true) {
					if (gridArr[i][j].start == true) {
						g.setColor(Color.magenta);
					} else if (gridArr[i][j].end == true) {
						g.setColor(Color.cyan);
					} else {
						g.setColor(Color.white);
					}
					if (gridArr[i][j].alongPath == true) {
						g.setColor(Color.green);
					}
				} else {
					g.setColor(Color.red);
				}
				g.fillRect (j * (squareWidth + spacing) + spacing, i * (squareWidth + spacing) + spacing, squareWidth, squareWidth);
			}
		}
	}

	public GridSquare getGridSquare(int r, int c) {
		return gridArr[r][c];
	}
	
	public void invalidateSquare(int r, int c) {
		gridArr[r][c].turnIntoWall();
	}
	
	
	


	

    public static void main(String[] args) throws IOException {

    	MazeSolver g = new MazeSolver(4, 5);
//    	g.invalidateSquare(0, 1);
//    	g.invalidateSquare(1, 1);
//    	g.invalidateSquare(2, 1);
//    	g.invalidateSquare(3, 1);
//    	g.invalidateSquare(1, 3);
//    	g.invalidateSquare(2, 3);
//    	g.invalidateSquare(3, 3);
//    	g.invalidateSquare(4, 3);
//    	g.bfs(0, 2, 4, 4);
//    	g.backtrack(0, 2, 0, 0);
    	
    
//    	for (int i = 0; i < pathX.size(); i++) {
//    		System.out.println(pathX.get(i) + " " + pathY.get(i));
//    	}
//    	for (int i = 0; i < 4; i++) {
//    		for (int j = 0; j < 4; j++) {
//    			if (g.getGridSquare(i, j).getAlongPath() == true) {
//    				System.out.println(i + " " + j);
//    			}
////    			System.out.println(g.getGridSquare(i, j).getParRow() + " " + g.getGridSquare(i, j).getParCol());
//    		}
//    	}
    	
    }

	
	

}