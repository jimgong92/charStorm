package com.jimgong.rain.entity.spawner;

import com.jimgong.rain.entity.particle.Particle;
import com.jimgong.rain.entity.spawner.Spawner.Type;
import com.jimgong.rain.level.Level;

public class ParticleSpawner extends Spawner {
	
	private int duration;
	
	public ParticleSpawner(int x, int y, int duration, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.duration = duration;
		for (int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, duration));
		}
	}
}
