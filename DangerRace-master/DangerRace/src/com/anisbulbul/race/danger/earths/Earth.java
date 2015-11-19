package com.anisbulbul.race.danger.earths;

import com.anisbulbul.race.danger.assets.GameAssets;
import com.badlogic.gdx.Gdx;

public class Earth extends GameAssets{
	
	public float earthX;
	public float earthY;
	public float earthWidth;
	public float earthHeight;
	public float earthSpeedX;
	public float earthSpeddY;
	public int earthIndex;
		
	/**
	 * 
	 */
	public Earth(float posX, float posY, 
			float width, float height, 
			float speedX, float speedY, int index) {
		earthX = posX;
		earthY = posY;
		earthWidth = width;
		earthHeight = height;
		earthSpeedX = speedX;
		earthSpeddY = speedY;
		earthIndex = index;
	}

	
}
