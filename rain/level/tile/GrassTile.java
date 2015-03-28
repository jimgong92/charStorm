//Grass tile has no collision, so no need to override solid method
package com.jimgong.rain.level.tile;

import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.Sprite;

public class GrassTile extends Tile{
	
	public GrassTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
