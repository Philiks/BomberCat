package game.objects.others;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.objects.GameObject;
import game.objects.Handler;
import game.objects.ID;

public class Poop extends GameObject{
	//---------------declaration of object of class------------
	Handler handler;
	
	//------------static variables--------------
	public static final int EXPLOSION_LOAD = 20;
	
	//----------------constructor----------
	public Poop(int x, int y, ID id, Handler handler, int ticks) {
		super(x, y, id, ticks);
		this.handler = handler;
	}

	public void tick() {
		//repositions the poop 
		if(x%50>25)
			x+=50-(x%50);
		else if(y%50>25)
			y+=50-(y%50);
		else{
			x-=x%50;
			y-=y%50;
		}
		
		if(ticks==EXPLOSION_LOAD){
			handler.addObject(new Explosion(x, y, ID.Explosion, handler));
			handler.removeObject(this);
		}
		ticks--;
	}

	public void render(Graphics g) {
		g.setColor(new Color(150, 75, 0));
		g.fillRect(x+10, y+10, 30, 30);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 50, 50);
	}
}