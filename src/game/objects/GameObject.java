package game.objects;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	//--------instance variable--------
	protected int x, y, velX = 0, velY = 0;
	protected int ticks;	//triggers to do certain actions
	protected ID id;
	protected boolean removable;
	
	//---------constructor---------
	public GameObject(int x, int y, ID id, int ticks){
		this.x = x;
		this.y = y;
		this.id = id;
		this.ticks = ticks;
	}
	
	//---------abstract methods--------
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	//-------------getter and setter-------------
	public int getX() {	return x;	}
	public void setX(int x) { this.x = x;	}
	public int getY() {	return y;	}
	public void setY(int y) { this.y = y;	}
	public int getVelX() { return velX;	  }
	public void setVelX(int velX) {	this.velX = velX;	}
	public int getVelY() {	return velY;	}
	public void setVelY(int velY) {	this.velY = velY;	}
	public int getTicks() {	return ticks;	}
	public void setTicks(int ticks) { this.ticks = ticks;	}
	public ID getId() {	return id;	}
	public void setId(ID id) {	this.id = id;	}
	public boolean isRemovable() {	return removable;	}
	public void setRemovable(boolean removable) {	this.removable = removable;	}
}
