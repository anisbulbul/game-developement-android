package com.anisbulbul.race.danger.points;

import com.anisbulbul.race.danger.assets.GameAssets;

public class PointBall extends GameAssets{
	
	public float pointX;
	public float pointY;
	public float pointWidth;
	public float pointHeight;
	public float pointSpeedX;
	public float pointSpeddY;
	public float pointAngle;
		
	/**
	 * 
	 */
	public PointBall(float posX, float posY, 
			float tempWidth, float tempHeight, 
			float speedX, float speedY, float angle) {
		pointX = posX;
		pointY = posY;
		pointWidth = tempWidth;
		pointHeight = tempHeight;
		pointSpeedX = speedX;
		pointSpeddY = speedY;
		pointAngle = angle;
	}

	
}
