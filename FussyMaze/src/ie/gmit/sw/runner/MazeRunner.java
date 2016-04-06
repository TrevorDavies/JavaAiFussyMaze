package ie.gmit.sw.runner;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import ie.gmit.sw.maze.*;


public class MazeRunner implements KeyListener{
	
	private static final int MAZE_DIMENSION = 70;
	private Node[][] model;
	private Node goal;
	private Maze ma;

	private HulkFighter hulkFighter= new HulkFighter();
	private boolean itsGameOver = false;
	private MazeView view;
	private int currentRow;
	private int currentCol;
	
	private int enemyRow;
	private int enemyCol;
	
	private Player hulk;
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	
	public MazeRunner() throws Exception{
		Maze m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();
		//goal = m.getGoalNode();
    	view = new MazeView(model, goal);
    	
    	hulk = new Player(100, 8, 100);
    	
    	placePlayer(hulk);
    	
    	for(int j =0; j < 250; j++){
    		enemyList.add(new Enemy(100, 25));
    		placeEnemy(enemyList);
    	}
    	
    	
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
	private void placePlayer(Player p1){   	
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	model[currentRow][currentCol].setFeature('P');;
    	updateView(); 		
	}
	private void placeEnemy(ArrayList<Enemy> enemyList2){
		enemyRow = (int)(MAZE_DIMENSION * Math.random());
		enemyCol = (int)(MAZE_DIMENSION * Math.random());
		model[enemyRow][enemyCol].setFeature('E');
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
	        System.out.println("Goal node: " +goal);
	        System.out.println("current Player Pos: "+currentCol+":"+currentRow);
	        System.out.println("Player Stats: Health="+hulk.getpHealth()+"Power: "+hulk.getpPower());
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
			hulk.setpPower(hulk.getpPower()+3);
			return false;
		}else if(model[r][c].getFeature() =='H'){
			model[r][c].setFeature('X');
			if(hulk.getpHealth()> 90){
				System.out.println("you are to healthy");
			}else{
				hulk.setpHealth(hulk.getpHealth()+10);			
			}
			
			return false;
		}else if(model[r][c].getFeature() =='G'){
			//model[r][c].setFeature('X');
			System.out.println("Goal Node found at row: "+model[r][c].getRow()+" col: "+model[r][c].getCol()) ;
			System.out.println("Game Over, You Win!!!");
			itsGameOver = true;
			model[r][c].setFeature(' ');


			return true;
		}else if(model[r][c].getFeature() =='E'){
			fight();//not working 
			if(itsGameOver){
				model[r][c].setFeature('E');
				return true;//should be false, but if set to false you can not move past enemy
							//and may get stuck
			}
			else{
				model[currentRow][currentCol].setFeature(' '); 
				model[r][c].setFeature('P');
				return true; 
				}
		}
		else{
			return false; //Can't move
		}
	}
	private boolean fight(){
		float damage = hulkFighter.hulkfight(hulk.getpHealth(),hulk.getpPower(),hulk.getpSmash() );
		System.out.println("damage: " + damage);
		
		float health = hulk.getpHealth();
		float power = hulk.getpPower();
		int newHealth = (int) (health - ((health/ 100)* damage));
		int newPower = (int) (power - ((power/ 100)* damage));
		
		System.out.println("health: " + health + " - " +damage+  "% = new Health: " + newHealth );
		System.out.println("power: " + power + " - " +damage+  "% = new Power: " + newPower );
		
		hulk.setpHealth(newHealth);
		hulk.setpPower(newPower);
		
		//System.out.println("newly retrieved health: " + p1.getPlayerHealth()+ " newly retrieved power: " + p1.getPlayerPower());
		
		if(newHealth <= 1 || newPower <=1){
			
			System.out.println("You Lost, Game Over");
			return itsGameOver = true; 
			//exit out of game or rerun it
			//new GameRunner();
		}
		return itsGameOver;
	}

	
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	
	public static void main(String[] args) throws Exception {
		new MazeRunner();

	}

}
