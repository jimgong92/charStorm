/*
 * Each tile should have a position and a sprite
 * Say whether it is collidable (i.e. something can pass through it)
 */
package com.jimgong.rain.level.tile;

import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.Sprite;
import com.jimgong.rain.level.tile.spawn_level.SpawnFloorTile;
import com.jimgong.rain.level.tile.spawn_level.SpawnFlowerTile;
import com.jimgong.rain.level.tile.spawn_level.SpawnGrassTile;
import com.jimgong.rain.level.tile.spawn_level.SpawnRockTile;
import com.jimgong.rain.level.tile.spawn_level.SpawnWallTile;
import com.jimgong.rain.level.tile.spawn_level.SpawnWaterTile;

public class Tile {

	public int x, y;
	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	//Spawn level tiles
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_flower = new SpawnFlowerTile(Sprite.spawn_flower);
	public static Tile spawn_rock = new SpawnRockTile(Sprite.spawn_rock);
	public static Tile spawn_wall = new SpawnWallTile(Sprite.spawn_wall);
	public static Tile spawn_floor = new SpawnFloorTile(Sprite.spawn_floor);
	public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
	//color recognition
	public static final int col_spawn_grass = 0xFF00FF00;
	public static final int col_spawn_flower = 0xFFFFFF00; 
	public static final int col_spawn_rock = 0xFFFF0000; 
	public static final int col_spawn_wall = 0xFF808080;
	public static final int col_spawn_floor = 0xFF7F3300;
	public static final int col_spawn_water = 0xFF0000FF;
	
	//Constructor
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
	}
	
	//Whether collidable
	public boolean solid() {
		return false;
	}
	
}
