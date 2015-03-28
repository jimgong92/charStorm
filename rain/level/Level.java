package com.jimgong.rain.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jimgong.rain.entity.Entity;
import com.jimgong.rain.entity.mob.Chaser;
import com.jimgong.rain.entity.mob.Mob;
import com.jimgong.rain.entity.mob.Player;
import com.jimgong.rain.entity.particle.Particle;
import com.jimgong.rain.entity.projectile.Projectile;
import com.jimgong.rain.graphics.Screen;
import com.jimgong.rain.level.tile.Tile;

public class Level {
	
	protected int width, height;
	protected int[] tilesInt; //For rand levels
	protected int[] tiles;
	protected final Random random = new Random();
	protected int time = 0;
	
	//Array of all entities to have on the level
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Mob> nonPlayerMobs = new ArrayList<Mob>();
	private List<Player> players = new ArrayList<Player>();
	public static Level spawn = new SpawnLevel("/levels/spawn.png");
	
	//Random level
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}
	//Custom level
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() {
	}
	
	protected void loadLevel(String path) {
	}
	
	public void update() {
		
		//time based events
		if (time++ > 2100) time = 0;
		//spawn new chaser mob every half second
		if (time % 30 == 0) {
			if (nonPlayerMobs.size() < 20) add(new Chaser(random.nextInt(3) + 15, random.nextInt(10) + 10));
		}
		
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for (int i = 0; i < nonPlayerMobs.size(); i++) {
			nonPlayerMobs.get(i).update();
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}
		remove();
	}
	
	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
		for (int i = 0; i < nonPlayerMobs.size(); i++) {
			if (nonPlayerMobs.get(i).isRemoved()) nonPlayerMobs.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
		}
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	//The offsets adjust for the unrendered (pink) area of the sprite
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		//Corner precision
		for (int corner = 0; corner < 4; corner++) {
			int xt = (x - corner % 2 * size + xOffset) >> 4;
			int yt = (y - corner / 2 * size + yOffset) >> 4;
			if (getTile((int) xt,(int) yt).solid()) solid = true;
		}
		
		return solid;
	}
	
	//Scroll indicates coordinate of player
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		
		//Set cornerpins
		int x0 = xScroll >> 4; //same as xScroll / 16, shifting bit 4 places to right
		//Aggregates pixels into tiles, 16 is size of a tile to render an extra tile for offsetting
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		
		for (int y = y0; y < y1; y++) { 
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		//Render entities
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		//Render projectiles
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		//Render particles
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		//Render non-player mobs
		for (int i = 0; i < nonPlayerMobs.size(); i++) {
			nonPlayerMobs.get(i).render(screen);
		}
		//Render players
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
		
	}
	
	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle)e);
		}
		else if (e instanceof Projectile) {
			projectiles.add((Projectile)e);
		}
		else if (e instanceof Player) {
			players.add((Player)e);
		}
		else if (e instanceof Mob) {
			nonPlayerMobs.add((Mob)e);
		}
		else entities.add(e);
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayerAt(int index) {
		return players.get(index);
	}
	
	public Player getClientPlayer() {
		return players.get(0);
	}
	
	public List<Mob> getMobs() {
		return nonPlayerMobs;
	}
	
	//Returns entities within a certain radius
	public List<Entity> getEntitiesInRadius (Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		
		//x and y coordinates of e
		int ex = e.getX(), ey = e.getY();
		
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x = entity.getX();
			int y = entity.getY();
			double distance = Math.sqrt((x-ex)*(x-ex) + (y-ey)*(y-ey));
			
			//if entity is within radius, add
			if (radius >= distance) {
				result.add(entity);
			}
		}
		return result;
	}
	
	//Returns mobs within a certain radius
	public List<Mob> getMobsInRadius (Entity e, int radius) {
		List<Mob> result = new ArrayList<Mob>();
		
		//x and y coordinates of e
		int ex = e.getX(), ey = e.getY();
		
		for (int i = 0; i < nonPlayerMobs.size(); i++) {
			Mob mob = nonPlayerMobs.get(i);
			int x = mob.getX();
			int y = mob.getY();
			double distance = Math.sqrt((x-ex)*(x-ex) + (y-ey)*(y-ey));
			
			//if entity is within radius, add
			if (radius >= distance) {
				result.add(mob);
			}
		}
		return result;
	}
	
	//Returns mobs within a certain radius for a given coordinate
	public List<Mob> getMobsInRadius (int ex, int ey, int radius) {
		List<Mob> result = new ArrayList<Mob>();
		
		for (int i = 0; i < nonPlayerMobs.size(); i++) {
			Mob mob = nonPlayerMobs.get(i);
			int x = mob.getX();
			int y = mob.getY();
			double distance = Math.sqrt((x-ex)*(x-ex) + (y-ey)*(y-ey));
			
			//if entity is within radius, add
			if (radius >= distance) {
				result.add(mob);
			}
		}
		return result;
	}
	
	//Returns player entities within a certain radius
	public List<Player> getPlayersInRadius (Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		
		//x and y coordinates of e
		int ex = e.getX(), ey = e.getY();
		
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			int x = player.getX();
			int y = player.getY();
			double distance = Math.sqrt((x-ex)*(x-ex) + (y-ey)*(y-ey));
			
			//if entity is within radius, add
			if (radius >= distance) {
				result.add(player);
			}
		}
		return result;
	}
	
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == Tile.col_spawn_floor) return Tile.spawn_floor;
		if (tiles[x + y * width] == Tile.col_spawn_flower) return Tile.spawn_flower;
		if (tiles[x + y * width] == Tile.col_spawn_grass) return Tile.spawn_grass;
		if (tiles[x + y * width] == Tile.col_spawn_rock) return Tile.spawn_rock;
		if (tiles[x + y * width] == Tile.col_spawn_wall) return Tile.spawn_wall;
		if (tiles[x + y * width] == Tile.col_spawn_water) return Tile.spawn_water;
		return Tile.voidTile;
	}
}
