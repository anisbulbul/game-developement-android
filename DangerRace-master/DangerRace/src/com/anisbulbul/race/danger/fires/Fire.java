package com.anisbulbul.race.danger.fires;

import com.anisbulbul.race.danger.assets.GameAssets;

public class Fire extends GameAssets{
	
	public float fireX;
	public float fireY;
	public float fireWidth;
	public float fireHeight;
	public float fireSpeedX;
	public float fireSpeddY;
		
	/**
	 * 
	 */
	public Fire(float posX, float posY, 
			float tempWidth, float tempHeight, 
			float speedX, float speedY) {
		fireX = posX;
		fireY = posY;
		fireWidth = tempWidth;
		fireHeight = tempHeight;
		fireSpeedX = speedX;
		fireSpeddY = speedY;
	}

	
}
