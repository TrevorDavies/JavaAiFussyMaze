package ie.gmit.sw.maze;

//import ie.gmit.sw.maze.Node.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
public class MazeView extends JPanel implements ActionListener{
	
	//private Timer timer=new Timer(50, this);
	
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;
	
	private static final int IMAGE_COUNT = 13;
	private int cellspan = 5;	
	private int cellpadding = 2;
	
	private Timer timer;
	private Node[][] maze;
	private BufferedImage[] images;
	
	private int enemy_state = 5;
	private int player_state = 7;
	private int currentRow;
	private int currentCol;
	
	private boolean zoomOut = false;
	private int imageIndex = -1;
	
	public MazeView(Node[][] maze, Node goal) throws Exception {
		init();
		this.maze = maze;
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		timer.start();
	}
	

//	public void setCurrentPosition(int row, int col){
//		this.currentRow = row;
//		this.currentCol = col;
//	}
	
//	public int getCurrentRow() {
//		return currentRow;
//	}

	public void setCurrentRow(int row) {
		if (row < cellpadding){
			currentRow = cellpadding;
		}else if (row > (maze.length - 1) - cellpadding){
			currentRow = (maze.length - 1) - cellpadding;
		}else{
			currentRow = row;
		}
	}

//	public int getCurrentCol() {
//		return currentCol;
//	}

	public void setCurrentCol(int col) {
		if (col < cellpadding){
			currentCol = cellpadding;
		}else if (col > (maze[currentRow].length - 1) - cellpadding){
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		}else{
			currentCol = col;
		}
	}

//	public void actionPerformed(ActionEvent ev){
//		if(ev.getSource()==timer){
//			 repaint();
//		}
//	}
		
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
              
        cellspan = zoomOut ? maze.length : 5;         
        final int size = DEFAULT_VIEW_SIZE/cellspan;
        g2.drawRect(0, 0, MazeView.DEFAULT_VIEW_SIZE, MazeView.DEFAULT_VIEW_SIZE);
        
        for(int row = 0; row < cellspan; row++) {
        	for (int col = 0; col < cellspan; col++){  
        		int x1 = col * size;
        		int y1 = row * size;
        		
        		char ch = 'X';
       		
        		if (zoomOut){
        			ch = maze[row][col].getFeature();
        			if (row == currentRow && col == currentCol){
        				g2.setColor(Color.GREEN);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        			}
        			if(ch == 'G'){
        				g2.setColor(Color.BLUE);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        			}
        			if(ch == 'X'){
        				g2.setColor(Color.RED);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        			}
        			if(ch == ' '){
        				g2.setColor(Color.GRAY);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        			}
        			if(ch == 'H'){
        				g2.setColor(Color.PINK);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        			}
        			if(ch == 'B'){
        				g2.setColor(Color.ORANGE);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        			}
        			if(ch == 'E'){
        				g2.setColor(Color.BLACK);
        				g2.fillRect(x1, y1, size, size);
        				continue;
        			}
        		}else{
        			ch = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getFeature();
        		}
        		
        		
        		if (ch == 'X'){        			
        			imageIndex = 0;//wall
        		}else if (ch == ' '){
        			imageIndex = 1;//floor
        		}else if (ch == 'W'){
        			imageIndex = 2;//power
        		}else if (ch == 'H'){
        			imageIndex = 3;//health
        		}else if (ch == 'B'){
        			imageIndex = 4;//smash
        		}else if (ch == '?'){
        			imageIndex = 5;//helper
        		}else if (ch == 'G'){
        			imageIndex = 6;//goal node
        		}else if (ch == 'P'){
        			imageIndex = player_state;       			
        		}else if (ch == 'E'){
        			imageIndex = enemy_state;       			
        		}else{
        			imageIndex = -1;
        		}
        		
        		if (imageIndex >= 0){
        			g2.drawImage(images[imageIndex], x1, y1, null);
        		}else{
        			g2.setColor(Color.WHITE);
        			g2.fillRect(x1, y1, size, size);
        		}      		
        	}
        }
	}//end paintComponent(Graphics g)
	public void toggleZoom(){
		zoomOut = !zoomOut;		
	}



	public void actionPerformed(ActionEvent e) {
		if (player_state < 0 || player_state == 7){
			player_state = 8;
		}else if(player_state < 7 || player_state == 8){
			player_state = 9;
		}else{
			player_state = 7;
		}
		if (enemy_state < 0 || enemy_state == 10){
			enemy_state = 11;
		}else if(enemy_state < 10 || enemy_state == 11){
			enemy_state = 12;
		}else{
			enemy_state = 10;
		}
		this.repaint();
	
	}	
	private void init() throws Exception{
		images = new BufferedImage[IMAGE_COUNT];
		images[0] = ImageIO.read(new java.io.File("resources/wall.png"));
		images[1] = ImageIO.read(new java.io.File("resources/stoneFloor.png"));		
		images[2] = ImageIO.read(new java.io.File("resources/ptPower_wall.png"));
		images[3] = ImageIO.read(new java.io.File("resources/ptH_wall.png"));
		images[4] = ImageIO.read(new java.io.File("resources/hulkSmash_wall.png"));
		images[5] = ImageIO.read(new java.io.File("resources/help_wall.png"));
		images[6] = ImageIO.read(new java.io.File("resources/goal.png"));
		images[7] = ImageIO.read(new java.io.File("resources/heroHulk1.png"));
		images[8] = ImageIO.read(new java.io.File("resources/heroHulk2.png"));
		images[9] = ImageIO.read(new java.io.File("resources/heroHulk3.png"));
		images[10] = ImageIO.read(new java.io.File("resources/enemyVader1.png"));
		images[11] = ImageIO.read(new java.io.File("resources/enemyVader2.png"));
		images[12] = ImageIO.read(new java.io.File("resources/enemyVader3.png"));
	}
}