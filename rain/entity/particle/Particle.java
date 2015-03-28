package com.jimgong.rain.entity.particle;

import com.jimgong.rain.entity.Entity;
import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.graphics.Sprite;

public class Particle extends Entity {
	
	private Sprite sprite;
	
	//Duration (called "life" in Cherno's videos) indicates how long particles last
	protected int duration;
	
	private int time = 0;
	
	//Actual x and y locations
	//zed models gravity
	protected double xx, yy, zz;
	
	//Amount of pixels particle moves in the x and y axes
	protected double xa, ya, za;
	
	public Particle(int x, int y, int duration) {
		this.x = x;
		this.y = y;
		
		this.xx = x;
		this.yy = y;
		
		this.duration = duration + (random.nextInt(duration) - duration / 2);
		sprite = Sprite.particle_normal;
		
		//Gives a random number between - and 1, but gives a normal distribution
		//more likely for number to be around 0 than -1 or 1
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		
		this.zz = random.nextFloat() + 5;
	}
	
	public void update() {
		time++;
		if (time >= 2100) time = 0;
		if (time > duration) remove();
		
		za -= 0.1;
		
		if(zz < 0) {
			zz = 0;
			za *= -0.6;
			xa *= 0.4;
			ya *= 0.4;
		}
		
		move((xx + xa), (yy + ya) + (za));
		
	}
	
	private void move(double x, double y) {
		
		if (collision(x, y)) {
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}
	
	public boolean collision(double x, double y) {
		boolean solid = false;
		/*
		 * Corner precision
		 * If corner % 2 == 0, checking left side
		 * If corner / 2 == 0, checking top side
		 */
		for (int corner = 0; corner < 4; corner++) {
			double xt = (x - corner % 2 * 16) / 16;
			double yt = (y - corner / 2 * 16) / 16;
			int ix = (int) Math.ceil(xt);	//Math.ceil rounds up before casting
			int iy = (int) Math.ceil(yt);
			if (corner % 2 == 0) ix = (int)xt;
			if (corner / 2 == 0) iy = (int)yt;
			if (level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx, (int) yy, sprite, true);
	}
	
}
