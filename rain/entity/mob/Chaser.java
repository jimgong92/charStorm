package com.jimgong.rain.entity.mob;

import java.util.List;

import com.jimgong.rain.entity.valuebar.Hitpoints;
import com.jimgong.rain.graphics.AnimatedSprite;
import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.SpriteSheet;

public class Chaser extends Mob {
	
	private int time = 0;
	private AnimatedSprite animSprite;
	
	private int xa, ya;
	
	//chases within 10 tile radius
	private int chaseRadius = 10 << 4;
	
	public Chaser(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		animSprite = new AnimatedSprite(16, 0, 0, SpriteSheet.charmander);
		sprite = animSprite;
		hp = new Hitpoints(16, x, y);
	}
	
	//chase player
	private void chase() {
		xa = 0; ya = 0;
		
		List<Player> players = level.getPlayersInRadius(this, chaseRadius);
		if (players.size() > 0) {
			Player player = players.get(0);
			
			if (x < player.getX()) xa++;
			if (x > player.getX()) xa--;
			if (y < player.getY()) ya++;
			if (y > player.getY()) ya--;
		}
	}
	
	private void attack() {
		List<Player> attackRadius = level.getPlayersInRadius(this, 8);
		for (int i = 0; i < attackRadius.size(); i++) {
			attackRadius.get(i).getHitpoints().setPoints(1);
		}
	}
	
	public void update() {
		if (time++ > 210000) time = 0;
		
		chase();
		if (xa != 0 || ya != 0) moving = true;
		else moving = false;
		
		if (moving) {
			if (ya < 0) dir = NORTH;
			if (ya > 0) dir = SOUTH;
			if (xa < 0) dir = WEST;
			if (xa > 0) dir = EAST;
			
			move(xa, ya);
		}
		
		animSprite.update(dir);
		animSprite.animateControl(moving);
		
		if (!hp.isDead()) hp.update(x + Hitpoints.X_OFFSET, y + Hitpoints.Y_OFFSET);
		else remove();
		
		if (time >= 10) {
			attack();
			time = 0;
		}
	}
	
	public void render(Screen screen) {
		//Center sprite
		int xx = x - sprite.SIZE / 2;
		int yy = y - sprite.SIZE / 2;
		screen.renderMob(xx, yy, this);
	}
}
