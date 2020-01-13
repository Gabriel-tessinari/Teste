package main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {

	public static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	private final int WIDTH = 160;
	private final int HEIGTH = 120;
	private final int SCALE = 4;
	
	public static void main(String[] args) {
		
		Main game = new Main();
		game.start();
	}
	
	public Main() {
		
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGTH*SCALE));
		initFrame();
	}
	
	private void initFrame() {
		
		frame = new JFrame("Game Screen");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		
	}
	
	public void update() {
		
	}
	
	public void render( ) {
		
	}
	
	public void run() {
		
		Long lastTime = System.nanoTime();
		Double amountOfTicks = 60D;
		Double ns = 1000000000 / amountOfTicks;
		Double base = 0D;
		
		Integer frames = 0;
		Long timer = System.currentTimeMillis();
		
		while(isRunning) {
			
			Long now = System.nanoTime();
			base += (now - lastTime) / ns;
			lastTime = now;
			
			if(base >= 1) {
				
				update();
				render();				
				frames++;
				base--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer += 1000;
			}
		}
	}
}
