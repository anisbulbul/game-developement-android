package com.anisbulbul.race.danger.destruction;

import com.anisbulbul.race.danger.assets.GameAssets;

public class DragonDestruction extends GameAssets{
	
	public float dragonDestructionX;
	public float dragonDestructionY;
	public float dragonDestructionWidth;
	public float dragonDestructionHeight;
	public float dragonDestructionSpeedX;
	public float dragonDestructionSpeedY;
	public float dragonDestructionIndex;
		
	/**
	 * 
	 */
	public DragonDestruction(float posX, float posY, 
			float tempWidth, float tempHeight, 
			float speedX, float speedY, float explusionIndex) {
		dragonDestructionX = posX;
		dragonDestructionY = posY;
		dragonDestructionWidth = tempWidth;
		dragonDestructionHeight = tempHeight;
		dragonDestructionSpeedX = speedX;
		dragonDestructionSpeedY = speedY;
		dragonDestructionIndex = explusionIndex;
	}

	
}
