package com.jimgong.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.jimgong.rain.entity.mob.Player;
import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.Sprite;
import com.jimgong.rain.input.Keyboard;
import com.jimgong.rain.input.Mouse;
import com.jimgong.rain.level.Level;
import com.jimgong.rain.level.TileCoordinate;

//
public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	//Screen Resolution 16 x 9 ratio
	private static int width = 300;
	private static int height = width / 16 * 9;  //168
	private static int scale = 3; 
	public static String title = "Rain";
	
	private Thread gameThread;
	private JFrame frame;
	private Keyboard key;
	private Level level; 
	private Player player;
	private boolean running = false;
	
	private Screen screen; 
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() {	
		Dimension size = new Dimension(width * scale, height * scale); 
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		//level = new RandomLevel(64, 64);
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(19,30);
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);
		level.add(player);
		addKeyListener(key);
		
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}
	
	public synchronized void start() {
		running = true;
		gameThread = new Thread(this, "Display");
		gameThread.start();
	}
	
	//Method to stop the applet
	public synchronized void stop() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Run method
	public void run() {
		
		//Store time
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0; //Accumulator
		int frames = 0;
		int updates = 0;
		
		requestFocus();
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now; //Update lastTime
			
			while (delta >= 1) { 
				update(); //Restrict to 60fps
				updates++;
				delta--;
			}
			render(); 
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {  
				timer += 1000;
				frame.setTitle(title + " | " + updates + " ups " + frames + " fps"); //display fps in window title
				updates = 0; frames = 0;
			}
		}
	}
	
	public void update() {
		key.update();
		level.update();
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) { //If no buffer strategy on canvas
			createBufferStrategy(3); //3 is number of buffers
			return;
		}

		screen.clear();
		//Center player
		int xScroll = player.getX() - screen.width / 2;
		int yScroll = player.getY() - screen.height / 2;
		level.render(xScroll, yScroll, screen);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", 0, 20));
		g.drawString("Button: " + Mouse.getButton(), 20, 20);
		g.drawString("X: " + player.getX() + ", Y: " + player.getY(), 350, 300);
		g.dispose();	
		bs.show();
	}
	
	/** Main Method */
	public static void main(String[] args) {
		Game game = new Game();
		
		game.frame.setResizable(false); 
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); 
		game.frame.setVisible(true); 
		
		game.start();
	}
}
