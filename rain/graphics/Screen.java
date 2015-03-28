package com.jimgong.rain.graphics;

import java.util.Random;

import com.jimgong.rain.entity.mob.Chaser;
import com.jimgong.rain.entity.mob.Mob;
import com.jimgong.rain.entity.projectile.Projectile;
import com.jimgong.rain.level.tile.Tile;

public class Screen {

	public int width, height; 
	public int[] pixels;	
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	public int xOffset, yOffset;
	
	private Random random = new Random();
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height]; //50400 pixels total for 300x168
	
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = random.nextInt(0xFFFFFF);
		}
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		//Offsets it so it renders at the area the map currently is
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;			
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
	}
	
	
	/*
	 * Render individual tiles
	 * xa/ya are the absolute position of the tile
	 * x/y are the pixels being rendered
	 * xp/yp are the offsets
	 */
	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp; 
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp; 
				/*Prevent program from rendering entire map
				 * at once; just show immediate screen area
				 */
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
				//pixel on screen getting rendered; pixel on sprite getting rendered
			}
		}
	}
	
	//Render projectiles
	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset; 
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp; 
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp; 
				/*Prevent program from rendering entire map
				 * at once; just show immediate screen area
				 */
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				
				int col = p.getSprite().pixels[x + y * p.getSpriteSize()];
				if (col != 0xFFFF00FF) pixels[xa + ya * width] = col;
	
			}
		}
	}
	
	public void renderMob(int xp, int yp, Mob mob) {
		xp -= xOffset;
		yp -= yOffset;
		
		for (int y = 0; y < mob.getSprite().SIZE; y++) {
			int ya = y + yp; 
			
			for (int x = 0; x < mob.getSprite().SIZE; x++) {
				int xa = x + xp; 
				/*Prevent program from rendering entire map
				 * at once; just show immediate screen area
				 */

				if (xa < -(mob.getSprite().SIZE) || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				
				//Prevent the pink pixels in spritesheet from being rendered
				int col = mob.getSprite().pixels[x + y * mob.getSprite().SIZE];
				//Change color of chasers
				if (mob instanceof Chaser && col == 0xffe02828) col = 0xff054bff;
				
				if (col != 0xFFFF00FF)
					pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderMob(int xp, int yp, Sprite sprite, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp; 
			int ys = y;
			if (flip == 2 || flip == 3) ys = 15 - y;
			
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp; 
				/*Prevent program from rendering entire map
				 * at once; just show immediate screen area
				 */
				int xs = x;
				if (flip == 1 || flip == 3) xs = 15 - x;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				
				//Prevent the pink pixels in spritesheet from being rendered
				int col = sprite.pixels[xs + ys * sprite.SIZE];
				if (col != 0xFFFF00FF)
					pixels[xa + ya * width] = col;
			}
		}
	}
	
	//set Offset
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}

