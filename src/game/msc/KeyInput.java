package game.msc;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.BCFramework;
import game.objects.GameObject;
import game.objects.Handler;
import game.objects.ID;

public class KeyInput extends KeyAdapter{
	//----------declaration of class objects-------------
	Handler handler;
	
	//--------------constructor-------------
	public KeyInput(Handler handler){
		this.handler = handler;
	}
	
	//-------------action methods-------------
	public void keyPressed(KeyEvent e){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject obj = handler.object.get(i);
			
			if(obj.getId() == ID.Cat){
				//movements
				if(e.getKeyCode() == KeyEvent.VK_W) handler.setUp(true);
				if(e.getKeyCode() == KeyEvent.VK_S) handler.setDown(true);
				if(e.getKeyCode() == KeyEvent.VK_A) handler.setLeft(true);
				if(e.getKeyCode() == KeyEvent.VK_D) handler.setRight(true);
				//creates bomb
				if(e.getKeyCode() == KeyEvent.VK_SPACE) BCFramework.createBomb = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0);
	}
	
	public void keyReleased(KeyEvent e){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject obj = handler.object.get(i);
			
			if(obj.getId() == ID.Cat){
				//movements
				if(e.getKeyCode() == KeyEvent.VK_W) handler.setUp(false);
				if(e.getKeyCode() == KeyEvent.VK_S) handler.setDown(false);
				if(e.getKeyCode() == KeyEvent.VK_A) handler.setLeft(false);
				if(e.getKeyCode() == KeyEvent.VK_D) handler.setRight(false);
			}
		}
	}
}