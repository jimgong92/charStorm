package com.jimgong.rain.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jimgong.rain.entity.mob.Chaser;
import com.jimgong.rain.entity.mob.Dummy;

public class SpawnLevel extends Level{
	
	public SpawnLevel (String path) {
		super(path);
	}
	
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			tiles = new int[width * height];
			image.getRGB(0, 0, width, height, tiles, 0 , width);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
		//load dummies in 5 random locations
		for (int i = 0; i < 1; i++) {
			add(new Chaser(19, 25));
			add(new Dummy(19, 25));
		}
	}
	
	//Grass = 0xFF00FF00
	//Flower = 0xFFFFFF00
	//Rock = 0xFF7F7F00
	protected void generateLevel() {
	}
}
