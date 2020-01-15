package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {

	public static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	private final int WIDTH = 240;
	private final int HEIGTH = 160;
	private final int SCALE = 4;
	
	private BufferedImage image;
	private BufferedImage player;
	
	private SpriteSheet sheet;
	
	public static void main(String[] args) {
		
		Main game = new Main();
		game.start();
	}
	
	public Main() {
		
		sheet = new SpriteSheet("/spritesheet.png");
		player = sheet.getSprite(0, 0, 16, 16);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGTH*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGTH, BufferedImage.TYPE_INT_RGB);
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
		
		isRunning = false;
		
		try {
			thread.join();			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	public void update() {
		
	}
	
	public void render( ) {
		BufferStrategy bs = getBufferStrategy();
		
		if(bs == null) {
			
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, WIDTH, HEIGTH);
		
		g.drawImage(player, 50, 50, null);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGTH*SCALE, null);
		bs.show();
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
		
		stop();
	}
}
