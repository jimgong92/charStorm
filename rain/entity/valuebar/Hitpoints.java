package com.jimgong.rain.entity.valuebar;

public class Hitpoints extends ValueBar {
	
	public static final int HITPOINTS_COLOR = 0xFFFF0000;
	public static final int EMPTY_COLOR = 0xFFFF8080;
	public static final int X_OFFSET = 9;
	public static final int Y_OFFSET = -8;

	
	//x & y visibility coordinates relative to the Mob to whom it belongs
	//HP bar visible to the right of the player
	public Hitpoints (int hitpoints, int x, int y) {
		//2 pixels wide, offset by 2 pixels from player
		super(hitpoints, x + X_OFFSET, y + Y_OFFSET, HITPOINTS_COLOR, EMPTY_COLOR); 
		point_color = HITPOINTS_COLOR;
		empty_color = EMPTY_COLOR;
	}
	
	public boolean isDead() {
		if (points <= 0) return true;
		else return false;
	}
}
