package com.jimgong.rain.entity.valuebar;

public class Mana extends ValueBar{
	
	public static final int MANA_COLOR = 0xFF0000FF;
	public static final int EMPTY_COLOR = 0xFF8080FF;
	public static final int X_OFFSET = 11;
	public static final int Y_OFFSET = -8;

	
	//x & y visibility coordinates relative to the Mob to whom it belongs
	//HP bar visible to the right of the player
	public Mana (int manaPoints, int x, int y) {
		//2 pixels wide, offset by 2 pixels from player
		super(manaPoints, x + X_OFFSET, y + Y_OFFSET, MANA_COLOR, EMPTY_COLOR); 
		point_color = MANA_COLOR;
		empty_color = EMPTY_COLOR;
	}
	
	public boolean isEmpty() {
		if (points <= 0) return true;
		else return false;
	}
}
