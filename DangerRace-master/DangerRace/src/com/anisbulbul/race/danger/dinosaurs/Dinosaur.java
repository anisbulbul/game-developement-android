package com.anisbulbul.race.danger.dinosaurs;

import com.anisbulbul.race.danger.assets.GameAssets;

public class Dinosaur extends GameAssets{
	
	public float dinosaurX;
	public float dinosaurY;
	public float dinosaurWidth;
	public float dinosaurHeight;
	public float dinosaurSpeedX;
	public float dinosaurSpeddY;
	public int dinosaurIndex;
		
	/**
	 * 
	 */
	public Dinosaur(float posX, float posY, 
			float tempWidth, float tempHeight, 
			float speedX, float speedY, int index) {
		dinosaurX = posX;
		dinosaurY = posY;
		dinosaurWidth = tempWidth;
		dinosaurHeight = tempHeight;
		dinosaurSpeedX = speedX;
		dinosaurSpeddY = speedY;
		dinosaurIndex = index;
	}

	
}
