package com.jimgong.rain.entity;

import java.util.Random;

import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.level.Level;

public abstract class Entity {
	protected int x, y; //Location of particular entity
	protected boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void update() {
	}
	
	public void render(Screen screen) {
	}
	
	public void remove() {
		//Removes entity from level
		removed = true;
	}
	
	public int getX() {
		return x;	
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		this.level = level;
	}
	
	
}
