package game.msc;
 
import game.BCFramework;
import game.objects.GameObject;

public class Camera {
	//-----instance variables-------
	int x, y;
	
	//-----constructor--------
	public Camera(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject obj){
		x += ((obj.getX() - x) - BCFramework.WIDTH/2) * 0.05;
		y += ((obj.getY() - y) - BCFramework.HEIGHT/2) * 0.05;
		
		//algorithm so that the camera won't exit the wall
		//int literals was to adjust due to border length
		x = Math.max(0, Math.min(BCFramework.WIDTH-44, x));
		y = Math.max(0, Math.min(BCFramework.HEIGHT-122, y));
	}

	//----------getter and setter-----
	public int getX() {	return x;	}
	public void setX(int x) {	this.x = x;	}
	public int getY() {	return y;	}
	public void setY(int y) {	this.y = y; }
}