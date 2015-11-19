package com.anisbulbul.race.danger.fires;

import com.anisbulbul.race.danger.assets.GameAssets;

public class FireExplusion extends GameAssets{
	
	public float fireExplusionX;
	public float fireExplusionY;
	public float fireExplusionWidth;
	public float fireExplusionHeight;
	public float fireExplusionSpeedX;
	public float fireExplusionSpeedY;
	public float fireExplusionIndex;
		
	/**
	 * 
	 */
	public FireExplusion(float posX, float posY, 
			float tempWidth, float tempHeight, 
			float speedX, float speedY, float explusionIndex) {
		fireExplusionX = posX;
		fireExplusionY = posY;
		fireExplusionWidth = tempWidth;
		fireExplusionHeight = tempHeight;
		fireExplusionSpeedX = speedX;
		fireExplusionSpeedY = speedY;
		fireExplusionIndex = explusionIndex;
	}

	
}
