package com.anisbulbul.race.danger.points;

import com.anisbulbul.race.danger.assets.GameAssets;

public class PointEarn extends GameAssets{
	
	public float pointEarnX;
	public float pointEarnY;
	public float pointEarnWidth;
	public float pointEarnHeight;
	public float pointEarnSpeedX;
	public float pointEarnSpeddY;
		
	/**
	 * 
	 */
	public PointEarn(float posX, float posY, 
			float tempWidth, float tempHeight, 
			float speedX, float speedY) {
		pointEarnX = posX;
		pointEarnY = posY;
		pointEarnWidth = tempWidth;
		pointEarnHeight = tempHeight;
		pointEarnSpeedX = speedX;
		pointEarnSpeddY = speedY;
	}

	
}
