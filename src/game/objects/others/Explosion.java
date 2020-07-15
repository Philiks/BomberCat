package game.objects.others;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.objects.GameObject;
import game.objects.Handler;
import game.objects.ID;
import game.objects.player.Cat;

public class Explosion extends GameObject{
	//-------------instance variables----------------
	private int time=100, hit=1;
	private boolean extendTop = true, extendBottom = true,
			extendLeft = true, extendRight = true;
	
	//------------final variable----------
	private final int TOP=1, BOTTOM=2, LEFT=3, RIGHT=4;
	
	//-----------static variable----------
	public static int bomb_size=2;
	
	//-----------static final variable----------
	public static final int EXPLOSION_TIME = 20;
	
	//------------object of class-----------
	Handler handler;
	Color colorTop=Color.RED, colorBottom=Color.RED, colorLeft=Color.RED, colorRight=Color.RED;
	
	//----------------constructor----------------
	public Explosion(int x, int y, ID id, Handler handler){
		super(x,y,id,0);
		this.handler = handler;
	}
	
	public void tick(){
		time--;
		if(time==EXPLOSION_TIME){
			handler.removeObject(this);
			removeObject();
		}
		
		//detect collision only once
		if(hit==1) collision();
	}
	
	public void render(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(x, y, 50, 50); //center
		
		for(int e=1; e<=bomb_size; e++){
			
			if(!isWall(x, y-50*e, TOP) && extendTop){
				objectHit(x, y-50*e, TOP);
				g.setColor(colorTop);
				g.fillRect(x, y-50*e, 50, 50); //top
			}
			
			if(!isWall(x, y+50*e, BOTTOM) && extendBottom){
				objectHit(x, y+50*e, BOTTOM);
				g.setColor(colorBottom);
				g.fillRect(x, y+50*e, 50, 50); //bottom
			}
			
			if(!isWall(x-50*e, y, LEFT) && extendLeft){
				objectHit(x-50*e, y, LEFT);
				g.setColor(colorLeft);
				g.fillRect(x-50*e, y, 50, 50); //left
			}
			
			if(!isWall(x+50*e, y, RIGHT) && extendRight){
				objectHit(x+50*e, y, RIGHT);
				g.setColor(colorRight);
				g.fillRect(x+50*e, y, 50, 50); //right
			}
		}
		
		extendTop = true; extendBottom = true;	extendLeft = true; extendRight = true;
		colorTop=Color.RED; colorBottom=Color.RED; colorLeft=Color.RED; colorRight=Color.RED;
	}
	
	//collision detection
	private void collision(){
		for(int i=0; i<handler.object.size(); i++){
			GameObject obj = handler.object.get(i);
			
			if(obj.getId() == ID.Cat){
				//checks all bounds
				if(obj.getBounds().intersects(getBounds()) || obj.getBounds().intersects(getBoundsBottom())
						|| obj.getBounds().intersects(getBoundsLeft()) || obj.getBounds().intersects(getBoundsRight())
						|| obj.getBounds().intersects(getBoundsTop())){
					Cat.catLife--;
					hit++;
				}
			}
			
			if(obj.getId() == ID.Poop){
				if(obj.getBounds().intersects(getBoundsBottom()) || obj.getBounds().intersects(getBoundsLeft()) 
						|| obj.getBounds().intersects(getBoundsRight())	|| obj.getBounds().intersects(getBoundsTop())){
					obj.setTicks(Poop.EXPLOSION_LOAD);
				}
			}
		}
	}
	
	//removes an object
	private void removeObject(){
		for(int j=0; j<handler.object.size(); j++){
			GameObject obj = handler.object.get(j);
			if(obj.getId() == ID.Block && obj.isRemovable()){
				handler.removeObject(obj);
				obj.setRemovable(false);
			}
		}
	}
	
	//returns if an explosion can extend and the color if block is hit
	private void objectHit(int xx, int yy, int pos){
		for(int j=0; j<handler.object.size(); j++){
			GameObject obj = handler.object.get(j);
			
			if(obj.getId() == ID.Block && xx==obj.getX() && yy==obj.getY()){
				setExtend(pos, true);
				obj.setRemovable(true); 
				break;
			}
		}
	}
	
	private void setExtend(int x, boolean changeColor){
		switch(x){
			case TOP: extendTop=false;	if(changeColor) colorTop=Color.MAGENTA; break;
			case BOTTOM: extendBottom=false;	if(changeColor) colorBottom=Color.MAGENTA;	break;
			case LEFT: extendLeft=false;	if(changeColor) colorLeft=Color.MAGENTA;	break;
			case RIGHT: extendRight=false;	if(changeColor) colorRight=Color.MAGENTA;	break;
		}
	}
	
	//returns true if the explosion hits a wall
	//renders the explosion if false
	private boolean isWall(int xx, int yy, int pos){
		boolean returnValue = false;
		for(int j=0; j<handler.object.size(); j++){
			GameObject obj = handler.object.get(j);
			if(obj.getId() == ID.Wall){
				returnValue = xx==obj.getX() && yy==obj.getY();
				if(returnValue){	setExtend(pos, false);	break;	}
			}
		}
		return returnValue;
	}
	
	//for collision detection
	public Rectangle getBounds(){
		return new Rectangle(x, y, 50, 50);
	}
	
	public Rectangle getBoundsTop(){
		return new Rectangle(x, y-50*bomb_size, 50, 50*bomb_size);
	}
	
	public Rectangle getBoundsBottom(){
		return new Rectangle(x, y+50*bomb_size, 50, 50*bomb_size);
	}
	
	public Rectangle getBoundsLeft(){
		return new Rectangle(x-50*bomb_size, y, 50*bomb_size, 50);
	}
	
	public Rectangle getBoundsRight(){
		return new Rectangle(x+50*bomb_size, y, 50*bomb_size, 50);
	}
}