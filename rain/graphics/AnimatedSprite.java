package com.jimgong.rain.graphics;

public class AnimatedSprite extends Sprite {

	private int animate_step = 0;
	private int animate_speed = 32;
	private int frame_length = animate_speed >> 2;
	private boolean animate = false;
	//direction = column (N - 0, E - 1, S - 2, W - 3)
	//animation step = row
	
	public AnimatedSprite(int size, int x, int y, SpriteSheet sheet) {
		super(size, x, y, sheet);
	}
	
	public void update(int direction) {
		if (animate_step++ >= 2100) animate_step = 0;
		
		//Update direction
		x = direction * SIZE;
		
		//If supposed to be animating, 
		if (animate) y = ((animate_step % animate_speed)/ frame_length) * SIZE;
		else y = 0;

		load();
	}
	
	public void animateControl(boolean moving) {
		animate = moving;
	}
}
