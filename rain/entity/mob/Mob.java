package com.jimgong.rain.entity.mob;

import com.jimgong.rain.entity.Entity;
import com.jimgong.rain.entity.particle.Particle;
import com.jimgong.rain.entity.projectile.Projectile;
import com.jimgong.rain.entity.projectile.WizardProjectile;
import com.jimgong.rain.entity.spawner.ParticleSpawner;
import com.jimgong.rain.entity.valuebar.Hitpoints;
import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.Sprite;

public abstract class Mob extends Entity {
	
	protected static final int NORTH = 0;
	protected static final int EAST = 1;
	protected static final int SOUTH = 2;
	protected static final int WEST = 3;
	
	protected Sprite sprite;
	protected int dir = 0; //Entity's direction 0-N, 1-E, 2-S, 3-W
	protected boolean moving = false;
	
	protected Hitpoints hp;
	
	//xa,ya denote where the player is moving on the x and y axis
	public void move(int xa, int ya) {
		//if trying to move on two axis
		if (xa != 0 && ya != 0) {
	//separate movement so collision on one axis is not a mutual relationship
			move(xa, 0);
			move(0,ya);
			return;
		}
		
		if (xa > 0) dir = EAST; //Going right
		if (xa < 0) dir = WEST; //Going left
		if (ya > 0) dir = SOUTH; //Going down
		if (ya < 0) dir = NORTH; //Going up
		
		if (!collision(xa, ya)) {
			//xa = -1, 0, 1 passed as arguments (moving left, staying still, moving right)
			x += xa;
			y += ya;
		}
		
	}
	
	public void update() {
	}
	
	protected void shoot(int x, int y, double dir) {
		//dir *= 180 / Math.PI;
		Projectile p = new WizardProjectile(x, y, dir);
		level.add(p);
	}
	

	private boolean collision(int xa, int ya) {
		boolean solid = false;
		//Corner precision
		for (int corner = 0; corner < 4; corner++) {
			int xt = ((x + xa) + corner % 2 * 14 - 8 ) >> 4;
			int yt = ((y + ya) + corner % 2 / 10 + (ya * 4 + 3)) >> 4;
			if (level.getTile(xt, yt).solid()) solid = true;
		}
		
		return solid;
	}
	
	public abstract void render(Screen screen);
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Hitpoints getHitpoints() {
		return hp;
	}
	
	public boolean isRemoved() {
		if (removed) level.add(new ParticleSpawner(x, y, 60, 50, level));
		return removed;
	}
}
