package ie.gmit.sw.ai;

//import ie.gmit.sw.ai.audio.SoundEffects;
import ie.gmit.sw.maze.*;

import java.awt.Color;
import java.awt.Component;
import java.util.*;
public class BruteForceTraversator implements Traversator{
	private boolean dfs = false;
	
	public BruteForceTraversator(boolean depthFirst){
		this.dfs = depthFirst;
		//SoundEffects.init();
	}
	


	@Override
	public void traverse(Node[][] maze, Node node) {
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		Deque<Node> queue = new LinkedList<Node>();
		queue.offer(node);

		while (!queue.isEmpty()){
			node = queue.poll();
			//System.out.println(node);
			node.setVisited(true);
			visitCount++;
			System.out.println(visitCount);
			if (node.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount);
				break;
			}
			
			
//			try { //Simulate processing each expanded node				
//				Thread.sleep(10);						
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					if (dfs){
						queue.addFirst(children[i]);
					}else{
						queue.addLast(children[i]);
					}
				}									
			}			
		}
		
    	double depth = 0;
		while (node != null){			
			node = node.getParent();
			if (node != null) node.setColor(Color.YELLOW);
			depth++;			
		}
		System.out.println("goal: " + depth);
		
	}

}