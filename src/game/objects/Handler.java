package game.objects;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	//stores all game objects in a list
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	//--------instance variables-----------
	private boolean up = false, down = false, left = false, right = false;
	
	public void tick(){
		//updates all objects
		for(int i = 0; i < object.size(); i++){
			GameObject obj = object.get(i);
			obj.tick();
		}
	}
	
	public void render(Graphics g){
		//renders all objects
		for(int i = 0; i < this.object.size(); i++){
			GameObject obj = this.object.get(i);
			obj.render(g);
		}
	}
	
	//adds and remove method for game objects
	public void addObject(GameObject object){	this.object.add(object);	}
	public void removeObject(GameObject object){	this.object.remove(object);   }
	
	//-------------getter and setter for up down left right movements-------------
	public boolean isUp() {	return up;	}
	public void setUp(boolean up) {	this.up = up; }
	public boolean isDown() { return down;	}
	public void setDown(boolean down) {	this.down = down; }
	public boolean isLeft() { return left;	}
	public void setLeft(boolean left) {	this.left = left; }
	public boolean isRight() { return right; }
	public void setRight(boolean right) {	this.right = right;	}
}