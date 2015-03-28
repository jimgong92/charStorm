package com.jimgong.rain.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	//Array for keystroke states
	private boolean[] keys = new boolean[120];
	
	public boolean up, down, left, right;
	
	public boolean space;
	
	public boolean shift;
	
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		
		space = keys[KeyEvent.VK_SPACE];
		
		shift = keys[KeyEvent.VK_SHIFT];
		
		for (int i = 0; i < keys.length; i++) {
			if (keys[i]) {
				System.out.println("KEY: " + i);
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
