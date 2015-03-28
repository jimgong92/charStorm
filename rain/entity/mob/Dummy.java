package com.jimgong.rain.entity.mob;

import com.jimgong.rain.entity.valuebar.Hitpoints;
import com.jimgong.rain.graphics.AnimatedSprite;
import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.SpriteSheet;

public class Dummy extends Mob {
	private int animate_step = 0;
	private int animate_speed = 30;
	private AnimatedSprite sprite;

	//x & y are starting coordinates
	public Dummy(int x, int y) {
		//Shift left by 4 (x16) to adjust for tile size
		this.x = x << 4;
		this.y = y << 4;
		sprite = new AnimatedSprite(16, 0, 0, SpriteSheet.charmander);
		hp = new Hitpoints(16, x, y);
	}
		
	public void update() {
		//Note direction sprite is supposed to move
		int xa = 0, ya = 0;
		//Update animation, prevent crashing
		if (animate_step++ >= 2100) animate_step = 0; 
		
		//at every animate_speed interval, randomly generate a new direction and if moving
		if (animate_step % animate_speed == 0) {
			dir = random.nextInt(4);
			//1 in 3 chance to not move
			if (random.nextInt(3) == 2) moving = false;
			else moving = true;
		}
		
		if (moving) {
			if (dir == NORTH) ya--;
			if (dir == SOUTH) ya++;
			if (dir == WEST) xa--;
			if (dir == EAST) xa++;
			
			move(xa, ya);
		}
		
		sprite.update(dir);
		sprite.animateControl(moving);
		
		if (!hp.isDead()) hp.update(x + Hitpoints.X_OFFSET, y + Hitpoints.Y_OFFSET);
		else remove();
	}
	
	public void render(Screen screen) {
		//Center sprite
		int xx = x - sprite.SIZE / 2;
		int yy = y - sprite.SIZE / 2;
		screen.renderMob(xx, yy, sprite, 0);
	}
	
}
