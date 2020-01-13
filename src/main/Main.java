package main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {

	public static JFrame frame;
	private final int WIDTH = 160;
	private final int HEIGTH = 120;
	private final int SCALE = 3;
	
	public Main() {
		
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGTH*SCALE));
		
		frame = new JFrame();
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		Main screen = new Main();
	}
	
	public void run() {
		
	}
}
