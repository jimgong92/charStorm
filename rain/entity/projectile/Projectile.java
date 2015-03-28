package com.jimgong.rain.entity.projectile;

import java.util.Random;

import com.jimgong.rain.entity.Entity;
import com.jimgong.rain.graphics.Sprite;

public abstract class Projectile extends Entity {
	
	//where projectile spawns
	protected final int xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	
	protected double x, y;
	
	protected final Random random = new Random();
	
	//vector
	protected double nx, ny;
	//Distance projectile has traveled from origin
	protected double distance;
	//speed, range, damage
	protected double speed, range, damage;
	
	public Projectile(int x, int y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	protected void move() {
		
	}
	public Sprite getSprite() {
		return sprite;
	}
	public int getSpriteSize() {
		return sprite.SIZE;
	}

}
