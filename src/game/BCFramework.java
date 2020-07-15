package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import game.msc.BufferedImageLoader;
import game.msc.Camera;
import game.msc.HUD;
import game.msc.KeyInput;
import game.objects.GameObject;
import game.objects.Handler;
import game.objects.ID;
import game.objects.enemy.BasicHuman;
import game.objects.others.Poop;
import game.objects.player.Cat;
import game.tiles.Block;
import game.tiles.Wall;

public class BCFramework extends Canvas implements Runnable{
	//----------serial number---------
	private static final long serialVersionUID = 1L;

	//----------constant variables-----------
	//width and height with 3x4 resolution
	public final static int WIDTH = 800, HEIGHT = 600;
	
	//----------instance variables----------
	private boolean isRunning = false;
	
	//-----------static variable-----------
	public static boolean createBomb = false;
	
	//------------class object declaration---------
	Thread thread;
	Handler handler;
	HUD hud;
	BufferedImage draw;
	BufferedImageLoader load;
	Camera camera;
	
	//----------constructor---------------
	public BCFramework(){
		new Window(WIDTH, HEIGHT, "BomberCat", this);

		start();
		
		handler = new Handler();
		camera = new Camera(0, 0);
		this.addKeyListener(new KeyInput(handler));
		hud = new HUD(handler, camera);
		
		load = new BufferedImageLoader();
		draw = load.loadImage("/level_one.png");
		
		drawLevel(draw);
		
		
		
		
	}
	
	private void tick(){
		handler.tick();
		hud.tick();
		
		for(int i = 0; i < handler.object.size(); i++){
			GameObject obj = handler.object.get(i);
			
			if(obj.getId() == ID.Cat){	
				camera.tick(obj);
				if(createBomb){
					handler.addObject(new Poop(obj.getX(), obj.getY(), ID.Poop, handler, 100));
					createBomb = false;
				}
			}
		}
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs==null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		/////////////////////////////////////
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.translate(-camera.getX(), -camera.getY());
		
		handler.render(g);
		hud.render(g);
		
		g.translate(camera.getX(), camera.getY());
		/////////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	private void drawLevel(BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		
		int catX = 0 , catY = 0; //for the Cat to be on top of every block
		
		for(int xx = 0; xx < width; xx++){
			for(int yy = 0; yy < height; yy++){
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;
				
				if(red == 0 && green == 0 && blue == 255){
					catX = xx;
					catY = yy;
				}
				
				if(red == 255 && green == 0 && blue == 0)
					handler.addObject(new Wall(xx*50, yy*50, ID.Wall, 0));
				if(red == 0 && green == 255 && blue == 0)
					handler.addObject(new Block(xx*50, yy*50, ID.Block, 0));
				if(red == 0 && green == 255 && blue == 255)
					handler.addObject(new BasicHuman(xx*50, yy*50, ID.BasicHuman, handler, 0));
			}
		}

		handler.addObject(new Cat(catX*50, catY*50, ID.Cat, handler, 0));
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}
	
	public synchronized void stop(){
		try {
			thread.join();
			isRunning = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//-----------game loop by Notch------------
	public void run() {
		this.requestFocus();
		
		long lastTime = System.nanoTime(); 
		double amountOfTicks = 60.0; 
		double ns = 1000000000 / amountOfTicks; 
		double delta = 0;
		while(isRunning) { 
			long now = System.nanoTime(); 
			delta += (now - lastTime) / ns; 
			lastTime = now; 
			while(delta >= 1) { 
				tick(); 
				delta--; 
			} 
			if(isRunning) 
				render();
		} 
		stop();
	}
	
	public static void main(String args[]){
		new BCFramework();
	}
}
