package game.objects.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.BCFramework;
import game.objects.GameObject;
import game.objects.Handler;
import game.objects.ID;

public class Cat extends GameObject{
	//-----------class object declaration-----
	Handler handler;
	
	//---------------static variable--------------
	public static int catLife = 3;
	
	//-----------------constructor-------------
	public Cat(int x, int y, ID id, Handler handler, int ticks) {
		super(x, y, id, ticks);
		this.handler = handler;
	}

	public void tick() {
		collision();
		
		x += velX;
		y += velY;
		
		//cat movements
		if(handler.isUp()) velY = -5;
		else if(!handler.isDown()) velY = 0;
		
		if(handler.isDown()) velY = 5;
		else if(!handler.isUp()) velY = 0;
		
		if(handler.isLeft()) velX = -5;
		else if(!handler.isRight()) velX = 0;
		
		if(handler.isRight()) velX = 5;
		else if(!handler.isLeft()) velX = 0;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x+10, y+10, 30, 30);
	}

	public Rectangle getBounds() { //horizontal bounds
		return new Rectangle(x, y, 50, 50);
	}
	
	public void collision(){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject obj = handler.object.get(i);
			
			if(obj.getId() == ID.Wall || obj.getId() == ID.Block || obj.getId() == ID.Poop){
				if(getBounds().intersects(obj.getBounds())){
					int ox = obj.getX(),
							oy = obj.getY(),
							ow = obj.getBounds().width,
							oh = obj.getBounds().height;

					if(Math.abs(y-oy)<=getBounds().height || Math.abs(y-oy)>=0){
						if(x-ox==-getBounds().width+5) 			//touches left
							x = Math.max(0, Math.min(Math.abs(ox-getBounds().width), x));
						else if(x-ox==ow-5) 					//touches right
							x = Math.max(ox+ow, Math.min(BCFramework.WIDTH-44, x));
					}

					if(Math.abs(x-ox)<=getBounds().width || x+ox+ow>=0){
						if(y-oy==-getBounds().height+5)			//touches top
							y = Math.max(0, Math.min(oy-getBounds().height, y));
						if(y-oy==oh-5)							//touches bottom
							y = Math.max(oy+oh, Math.min(BCFramework.HEIGHT-50, y));
					}
				}
			}
		}
	}
}