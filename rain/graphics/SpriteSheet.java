package com.jimgong.rain.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	/** Specify path */
	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
	public int[] pixels;
	
	//Tiles spritesheet
	public static SpriteSheet tiles = new SpriteSheet("/textures/sheets/spritesheet.png", 256);
	public static SpriteSheet spawn_level = new SpriteSheet("/textures/sheets/spawn_level.png", 48);
	//Player spritesheet
	public static SpriteSheet player = new SpriteSheet("/textures/sheets/player.png", 64);

	//Projectiles
	public static SpriteSheet projectile_wizard = new SpriteSheet("/textures/sheets/projectiles/wizard.png", 48);
	
	//Charmander - used for Dummy NPC
	public static SpriteSheet charmander = new SpriteSheet("/textures/sheets/charmander.png", 64);
	
	/**Constructor*/
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		WIDTH = SIZE;
		HEIGHT = SIZE;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	/** Load spritesheet */
	private void load() {
		//Create new buffered image object and set it equal to image of path
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));

			int w = image.getWidth();
			int h = image.getHeight();
			
			//Translate loaded image into pixels
			image.getRGB(0, 0, w, h, pixels, 0, w);			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
