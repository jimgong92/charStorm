package com.jimgong.rain.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.jimgong.rain.Game;
import com.jimgong.rain.entity.projectile.Projectile;
import com.jimgong.rain.entity.projectile.WizardProjectile;
import com.jimgong.rain.entity.valuebar.Hitpoints;
import com.jimgong.rain.entity.valuebar.Mana;
import com.jimgong.rain.graphics.AnimatedSprite;
import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.SpriteSheet;
import com.jimgong.rain.input.Keyboard;
import com.jimgong.rain.input.Mouse;

public class Player extends Mob {
	
	private Keyboard input;
	private int animate_step = 0;
	private AnimatedSprite sprite;
	
	private Mana mana;
	private int manaRegenRate = -2;
	
	public static final int RUN_SPEED = 3;
	public static final int WALK_SPEED = 1;
	private int movespeed = WALK_SPEED;
	
	public Player(Keyboard input) {
		this.input = input;
		sprite = new AnimatedSprite(16, 0, 0, SpriteSheet.player);
	}
	//Constructor to be used if player spawns at new coordinate
	public Player (int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = new AnimatedSprite(16, 0, 0, SpriteSheet.player);
		
		//instantiate hitpoints and mana
		hp = new Hitpoints(16, x, y);
		mana = new Mana(16, x, y);
		
	}
	
	public void update() {
		//Note direction player is supposed to move
		int xa = 0, ya = 0;
		//Update animation, prevent crashing
		if (animate_step++ >= 2100) animate_step = 0; 
		
		if (input.up) ya--;
		if (input.down) ya++;
		if (input.left) xa--;
		if (input.right) xa++;
		
		if (input.space) movespeed = RUN_SPEED;
		else movespeed = WALK_SPEED;
		
		if (xa != 0 || ya != 0) {
			for (int i = 0; i < movespeed; i++) {
				move(xa, ya);
				moving = true;				
			}
		} 
		else moving = false;
		
		clear();
		
		//shoot at max speed of once every 10 frames
		updateShooting();
		sprite.update(dir);
		sprite.animateControl(moving);
		
		//Hitpoints and mana update
		if (!hp.isDead()) hp.update(x + Hitpoints.X_OFFSET, y + Hitpoints.Y_OFFSET);
		else remove();
		mana.update(x + Mana.X_OFFSET, y + Mana.Y_OFFSET);
		
		if (animate_step % 60 == 0) {
			//regen mana
			if (mana.getPoints() - mana.getCapacity() <= manaRegenRate) mana.setPoints(manaRegenRate);
			else if (mana.getPoints() < mana.getCapacity()) mana.setPoints(-1);
		}
		
	}
	
	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) {
				level.getProjectiles().remove(i);
			}
		}
		
	}
	//Shoot if clicking left mouse button
	private void updateShooting() {
		//if out of mana, stop shooting
		if (mana.isEmpty()) return;
		
		if (Mouse.getButton() == 1 && animate_step % WizardProjectile.FIRE_RATE == 0) {
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			
			//mana shoot
			mana.setPoints(1);		
		}
	}
	
	public void render(Screen screen) {
		//Center player
		int xx = x - sprite.SIZE / 2;
		int yy = y - sprite.SIZE / 2;
		screen.renderMob(xx, yy, sprite, 0);
		
		if (input.shift) {
			//render hp and mana bars
			hp.render(screen);
			mana.render(screen);
			
			List<Mob> hpToggle = level.getMobsInRadius(this, Game.getWindowWidth());
			for (int i = 0; i < hpToggle.size(); i++) {
				hpToggle.get(i).getHitpoints().render(screen);
			}
		}
		
	}
}
