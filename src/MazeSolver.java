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

public class MazeSolver {
	
	class GridSquare {
		
		private int row, col;
		private boolean visited;
		
		public GridSquare (int row, int col) {
			this.row = row;
			this.col = col;
			visited = false;
		}
		
		public int getRow() {
			return row;
		}
		
		public int getCol() {
			return col;
		}
		
		public boolean getVisited() {
			return visited;
		}
		
		void visitSquare () {
			visited = true;
		}
		
	}
	
	class Grid extends JFrame {
		
		//private GridSquare[][] gridArr;
		private Set<GridSquare> gridSet;
		
		public Grid(int rows, int cols) {
			
			//gridArr = new GridSquare[rows][cols];
			gridSet = new HashSet<>();
			
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					gridSet.add(new GridSquare(i, j));
				}
			}
			
			
			addMouseListener(new MouseAdapter(){
				public void mousePressed (MouseEvent e){
	                
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
		
		
		
		
		
	}
	
	
	/*
	public static Map<GridSquare, LinkedList<GridSquare>> grid = new HashMap<>();
	public static Set searched = new HashSet <GridSquare>();
	
	public static void bfs(GridSquare a) {
		Queue<GridSquare> q = new LinkedList<GridSquare>();
		
		q.add(a);
		while (!q.isEmpty()) {
			int size = q.size();
			
			for(int k=0; k<size; k++){
               	GridSquare p=q.remove();
                LinkedList<GridSquare> temp = grid.get(p);
                if(temp==null){  return; }
            	for (GridSquare i : temp) {
            		if(!searched.contains(i)){
            			q.add(i);
            			searched.add(i);
            		}
            	}
            }
		}
	}*/
	
	
	
//	
//	
//	public static Map<String, LinkedList<String>> map = new HashMap<>();
//    public static Set visited = new HashSet <String>();
//
//    /**
//     * @param args the command line arguments
//     */
//    
//    
//        public static void bfs(String a) {
//        Queue<String> q = new LinkedList<String>();
//       /* LinkedList<Integer> list = map.get(a);
//        int layers = 1;
//        if (list == null) {
//            return;
//        }
//        for (int i : list) {
//            q.add(i);
//            visited[i] = true;
//        }
//*/  
//       q.add(a);
//        while (!q.isEmpty()) {
//            int size = q.size();
//   
//            for(int k=0; k<size; k++){
//               String p=q.remove();
//                LinkedList<String> temp = map.get(p);
//                if(temp==null){  return; }
//                 for (String i : temp) {
//                     if(!visited.contains(i)){
//                        q.add(i);
//                     
//                        visited.add(i);
//                     }
//            }
//            }
//            
//         
//        }
//    }
        
    public static void main(String[] args) throws IOException {
//        // TODO code application logic here
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        Scanner in = new Scanner(System.in);
//        int n = in.nextInt();
//        int count = 0;
//        
//        
//        for (int i = 0; i < n; i++) {
//            String s = br.readLine();
//           // System.out.println("HI");
//           
//            String b = br.readLine();
//            while (!b.contains("</HTML>")) {
//                
//                    //System.out.println("B:"+ b);
//                while (b.contains("<A HREF=\"")) {
//                    ///System.out.println("B:"+ b);
//                    int index = b.indexOf("<A HREF=\"");
//                    b = b.substring(index+9);
//                    int index2 = b.indexOf("\"");
//                    String link = b.substring(0, index2);
//                    
//                    b = b.substring(index2+1);
//                    System.out.println("Link from " + s + " to " + link);
//                    
//                    LinkedList<String> temp1 = map.get(s);
//                    if (temp1 == null) {
//                        LinkedList<String> te = new LinkedList<>();
//                        map.put(s, te);
//                    }
//                    map.get(s).add(link);
//
//                }
//                
//
//            b = br.readLine();
//            }
//
//        }
//        while(true){
//            String x = br.readLine();
//           // if(x.equals("The End")) break;
//        while (!x.contains("The End")) {
//            String y = br.readLine();
//            bfs(x);
//            if(visited.contains(y)) System.out.println("Can surf from " + x + " to " + y + ".");
//            else System.out.println("Can't surf from " + x + " to " + y + ".");
//            visited = new HashSet<String>();
//            x = br.readLine();
//        }
//        }
//
    	
    	boolean arr[][] = new boolean[20][20];
    	
    	
    }
	

}
