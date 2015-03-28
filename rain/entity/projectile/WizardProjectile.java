package com.jimgong.rain.entity.projectile;

import java.util.List;

import com.jimgong.rain.entity.mob.Mob;
import com.jimgong.rain.entity.spawner.ParticleSpawner;
import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.Sprite;

public class WizardProjectile extends Projectile{
	
	public static final int FIRE_RATE = 6; //10 per second at 60fps
	
	public WizardProjectile(int x, int y, double dir) {
		super(x, y, dir);
		//varying range
		range = 150;
		speed = 3;
		damage = 2;

		sprite = Sprite.projectile_wizard;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	public void update() {
		if  (level.tileCollision((int)(x + nx), (int)(y + ny), 8, 4, 4)) {
			level.add(new ParticleSpawner((int)x, (int)y, 60, 50, level));
			remove();
		}
		else move();
		
		List<Mob> hitbox = level.getMobsInRadius((int)x, (int)y, 8);
		
		for (int i = 0; i < hitbox.size(); i++) {
			hitbox.get(i).getHitpoints().setPoints((int)damage);
		}
		
	}
	protected void move() {
		x += nx;
		y += ny;			
		if (getDistance() > range) remove();
	}
	
	private double getDistance() {
		double dist = 0;
		dist = Math.sqrt((xOrigin - x) * (xOrigin - x)  + (yOrigin - y) * (yOrigin - y));
		return dist;
	}
	
	public void render(Screen screen) {
		//x y render projectile relative to character
		screen.renderProjectile((int)x - 8, (int)y - 8, this);
	}
	 
}
