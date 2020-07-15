package game;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window{
		//-----------------constructor-----------------
	public Window(int width, int height, String title, BCFramework bc){
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.add(bc);
		frame.setVisible(true);
	}
}
