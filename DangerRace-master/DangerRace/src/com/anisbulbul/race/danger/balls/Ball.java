package com.anisbulbul.race.danger.balls;

import com.anisbulbul.race.danger.assets.GameAssets;
import com.badlogic.gdx.Gdx;

public class Ball extends GameAssets{
	
	public float ballX;
	public float ballY;
	public float ballWidth;
	public float ballHeight;
	public float ballSpeedX;
	public float ballSpeddY;
	public int ballIndex;
	public float ballJumpCounter;
	public float ballAngle;
		
	/**
	 * 
	 */
	public Ball(float posX, float posY, 
			float width, float height, 
			float speedX, float speedY, 
			int index, float jumpCounter, float angle) {
		ballX = posX;
		ballY = posY;
		ballWidth = width;
		ballHeight = height;
		ballSpeedX = speedX;
		ballSpeddY = speedY;
		ballIndex = index;
		ballJumpCounter = jumpCounter;
		ballAngle = angle;
	}

	
}
