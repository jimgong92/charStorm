package com.jimgong.rain.graphics;

import com.jimgong.rain.entity.valuebar.Hitpoints;
import com.jimgong.rain.entity.valuebar.ValueBar;

public class Sprite {
	
	//Size of the particular sprite
	public final int SIZE;
	//width and height
	private int width, height;
	//Coordinates of sprite
	protected int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	public static Sprite grass = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 1, 1, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 2, 1, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 16, 0x1B87E0);
	
	//Spawn level sprites
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_flower = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_rock = new Sprite(16, 0, 2, SpriteSheet.spawn_level);
	public static Sprite spawn_wall = new Sprite(16, 1, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_floor = new Sprite(16, 1, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level);
	
	//Projectile Sprites
	public static Sprite projectile_wizard = new Sprite(16, 0, 2, SpriteSheet.projectile_wizard);
	
	//Particle sprites
	public static Sprite particle_normal = new Sprite(1, 1, 0xCC3130);
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = SIZE;
		this.height = SIZE;
		pixels = new int[SIZE * SIZE];
		//Set location of target sprite in sprite sheet
		this.x = x * size; 
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int color) {
		SIZE = width * height;
		this.width = width;
		this.height = height;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	//for Bar sprites
	//emptyValue designates amount of points empty from capacity
	public Sprite(int width, int height, int fillColor, int emptyColor, int emptyValue) {
		SIZE = width * height;
		this.width = width;
		this.height = height;
		pixels = new int[SIZE];
		setColor(fillColor, emptyColor, emptyValue * ValueBar.BAR_WIDTH);
	}
	
	private void setColor(int color) {
		for (int i = 0; i < SIZE; i++) {
			pixels[i] = color;
		}
	}
	
	//for Bar sprites
	private void setColor(int fillColor, int emptyColor, int limit) {
		//top pixels empty
		for (int i = 0; i < limit; i++) {
			pixels[i] = emptyColor;
		}
		//bottom pixels filled
		for (int i = limit; i < SIZE; i++) {
			pixels[i] = fillColor;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	//Access spritesheet pixels and find right sprite
	protected void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				//Extract single sprite from sprite sheet
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}
}
