package ie.gmit.sw.runner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ie.gmit.sw.maze.*;


public class MazeRunner implements KeyListener{
	
	private static final int MAZE_DIMENSION = 70;
	private Node[][] model;
	private Node goal;
	private Maze goalNode;
	
	private MazeView view;
	private int currentRow;
	private int currentCol;
	
	public MazeRunner() throws Exception{
		Maze m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();
    	view = new MazeView(model, goal);
    	
    	placePlayer();
    	
    	Dimension d = new Dimension(MazeView.DEFAULT_VIEW_SIZE, MazeView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(view);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
		
		
		
	}//end MazeRunner
	private void placePlayer(){   	
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	model[currentRow][currentCol].setFeature('P');;
    	updateView(); 		
	}
	
	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	      if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
	        	if (isValidMove(currentRow, currentCol + 1)) currentCol++;   		
	        }else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
	        	if (isValidMove(currentRow, currentCol - 1)) currentCol--;	
	        }else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
	        	if (isValidMove(currentRow - 1, currentCol)) currentRow--;
	        }else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
	        	if (isValidMove(currentRow + 1, currentCol)) currentRow++;        	  	
	        }else if (e.getKeyCode() == KeyEvent.VK_Z){
	        	view.toggleZoom();
	        }else{
	        	return;
	        }
	        
	        updateView();
		
	}
	private boolean isValidMove(int r, int c){
		if (r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getFeature()== ' '){
			model[currentRow][currentCol].setFeature(' ');
			model[r][c].setFeature('P');;
			return true;
		}else if(model[r][c].getFeature() =='?'){
			model[r][c].setFeature('X');
			return false;
		}else if(model[r][c].getFeature() =='B'){
			model[r][c].setFeature('X');
			return false;
		}else if(model[r][c].getFeature() =='W'){
			model[r][c].setFeature('X');
			return false;
		}else if(model[r][c].getFeature() =='H'){
			model[r][c].setFeature('X');
			return false;
		}else if(model[r][c].getFeature() =='G'){
			//model[r][c].setFeature('X');
			System.out.println("Goal Node found at: "+model[r][c].isGoalNode()) ;
			return false;
		}else{
			return false; //Can't move
		}
	}

	
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
	public static void main(String[] args) throws Exception {
		new MazeRunner();

	}

}
