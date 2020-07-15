package game.objects.enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import game.objects.GameObject;
import game.objects.Handler;
import game.objects.ID;
import game.objects.player.Cat;

public class BasicHuman extends GameObject{
	//------------instance variables-------------
	private boolean isUp=false, isDown=false, 
			isLeft=false, isRight=false, isBetweenWalls; 
	private int initialDir, origX, origY;
	
	//------------declaration of object of class-------------
	Handler handler;
	Random rand = new Random();
	
	//--------constructor------------
	public BasicHuman(int x, int y, ID id, Handler handler, int ticks) {
		super(x, y, id, ticks);
		this.handler = handler;
		
		origX=x;
		origY=y;
		
		initialDir = rand.nextInt(4);
		if(initialDir == 0)		 isUp=true;
		else if(initialDir == 1) isDown=true; 
		else if(initialDir == 2) isLeft=true;
		else if(initialDir == 3) isRight=true;
	}

	public void tick() {
		//assigns whether the position is in between walls
		if((origX%100==0 && origY%100==50) || (origX%100==50 && origY%100==0)) isBetweenWalls=true;
		else isBetweenWalls=false;
		
		if(isUp){ velY=-1; velX=0;	}
		else if(isDown){ velY=1; velX=0;	}
		else if(isLeft){ velY=0; velX=-1;	}
		else if(isRight){ velY=0; velX=1;	}
		
		//checks position then change if possible
		changeDir(rand.nextInt(5)*50);

		checkObject();
		x+=velX;
		y+=velY;
	}

	//changes direction if possible
	private void changeDir(int loc){
		if(isBetweenWalls && loc%2==0) loc=0;
		else if(isBetweenWalls && loc%2==1) loc*=50;
		else if(!isBetweenWalls && loc%2==1) loc=0;
		else loc*=50;
		
		//starting position is between walls
		if(isBetweenWalls){
			//moving left or right
			if(Math.abs(origX-x)==loc && y==origY)
				changeDirection(rand.nextInt(2));
			//moving up or down
			else if (Math.abs(origY-y)==loc && x==origX)
				changeDirection(rand.nextInt(2)+2);
		}else{ //starting position is not between walls
			//moving left or right
			if(Math.abs(origX-x)==loc && y==origY)
				changeDirection(rand.nextInt(2));
			//moving up or down
			else if (Math.abs(origY-y)==loc && x==origX)
				changeDirection(rand.nextInt(2)+2);
		}
	}
	
	//changes enemy's direction
	
	private void changeDirection(int dir){
		//reassigns origX and origY to current x and y
		origX=x;
		origY=y;
		
		//makes current direction to false
		if(isUp) isUp=false;
		else if(isDown) isDown=false;
		else if(isLeft) isLeft=false;
		else if(isRight) isRight=false;
		
		if(dir == 0){ isUp=true; velX=0; velY=-1;	}
		else if(dir == 1){ isDown=true; velX=0; velY=1;	}
		else if(dir == 2){ isLeft=true; velX=-1; velY=0;	}
		else if(dir == 3){ isRight=true; velX=1; velY=0;	}
	}
		
	//checks if there is a block wall or enemy then changes path
	private void checkObject(){
		for(int i=0; i<handler.object.size(); i++){
			GameObject obj = handler.object.get(i);
			
			if(obj.getId()==ID.Wall || obj.getId()==ID.Block || obj.getId()==ID.BasicHuman || obj.getId()==ID.Poop){
				if(getBounds().intersects(obj.getBounds())){
					if(Math.abs(x-obj.getX())<=getBounds().width || x+obj.getX()+obj.getBounds().width>=0){
						if(y-obj.getY()==obj.getBounds().height-5){			//touches bottom
							isUp=false;
							isDown=true;
						}
						else if(y-obj.getY()==-getBounds().height+5){		//touches top
							isDown=false;
							isUp=true;
						}
					}

					if(Math.abs(y-obj.getY())<=getBounds().height || Math.abs(y-obj.getY())>=0){
						if(x-obj.getX()==-getBounds().width+5){ 			//touches left
							isRight=false;
							isLeft=true;
						}
						else if(x-obj.getX()==obj.getBounds().width-5){	 	//touches right
							isLeft=false;
							isRight=true;
						}
					}
				}
			}
			
			else if(obj.getId()==ID.Explosion && obj.getBounds().intersects(getBounds()))
				handler.removeObject(this);
			else if(obj.getId()==ID.Cat && obj.getBounds().intersects(getBounds())){
				handler.removeObject(this);
				Cat.catLife--;
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.PINK);
		g.fillRect(x+10, y+10, 30, 30);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 50, 50);
	}
}