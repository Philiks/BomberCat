package game.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.objects.GameObject;
import game.objects.ID;

public class Block extends GameObject{
	//-----------constructor-----------
	public Block(int x, int y, ID id, int ticks) {
		super(x, y, id, ticks);
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, 50, 50);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 50, 50);
	}
}