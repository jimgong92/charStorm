package com.jimgong.rain.entity.valuebar;

import com.jimgong.rain.entity.Entity;
import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.Sprite;

public class ValueBar extends Entity {
	
	protected int capacity;
	protected int points;
	protected Sprite sprite;
	protected int point_color;
	protected int empty_color;
	public final static int BAR_WIDTH = 1;
	
	//Construct full value bar (loaded to capacity)
	//x & y designate visibility coordinates
	public ValueBar(int capacity, int x, int y, int point_color, int empty_color) {
		this.capacity = capacity;
		this.points = capacity;
		this.x = x;
		this.y = y;
		sprite = new Sprite(BAR_WIDTH, capacity, point_color, empty_color, 0);
	}
	
	public void update(int x, int y) {
		sprite = new Sprite(BAR_WIDTH, capacity, point_color, empty_color, capacity - points);
		this.x = x;
		this.y = y;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public int getPoints() {
		return points;
	}
	
	//positive value = damage, negative value = heal
	public void setPoints(int value) {
		points -= value;
	}
	
	public void render(Screen screen) {
		screen.renderSprite(x, y, sprite, true);
	}
	
}
