package game.msc;

import java.awt.Color;
import java.awt.Graphics;

import game.objects.Handler;
import game.objects.player.Cat;

public class HUD {
	//--------instance variable-----------
	private int x, y;
	
	//------------declaration of object of class------------
	Handler handler;
	Camera camera;
	
	//------------constructor-----------
	public HUD(Handler handler, Camera camera){
		this.handler = handler;
		this.camera = camera;
	}
	
	public void tick(){
		x=camera.getX()+20;
		y=camera.getY()+20;
	}
	
	public void render(Graphics g){
		for(int i=0; i<Cat.catLife; i++){
			g.setColor(Color.GREEN);
			g.fillRect(x+30*i, y, 20, 20);
		}
	}
}
