package com.jimgong.rain.level.tile.spawn_level;

import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.Sprite;
import com.jimgong.rain.level.tile.Tile;

public class SpawnWaterTile extends Tile{
	public SpawnWaterTile(Sprite sprite) {
		super(sprite);
	}
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
