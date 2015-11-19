package com.anrosoft.mozbiemission.assets;

import com.badlogic.gdx.graphics.g2d.Animation;

public class PointObject {
	
	public float pointPosX;
	public float pointPosY;
	public float pointWidth;
	public float pointHeight;
	public float pointSpeedX;
	public float pointSpeedY;
	public String pointTextureCategory;
	
	public boolean ispointEnemy;
	
	
	public float elapsedTime = 0;
	public float keyFrameDelay = 0.05f;
	
	public int animationAtlasIndex;
	
	public int pointAtlasIndex;
	
	public float pointAplha;
	
	public Animation animation;

	/**
	 * @param pointPosX
	 * @param pointPosY
	 * @param pointWidth
	 * @param pointHeight
	 * @param ispointEnemy
	 */
	public PointObject(float pointPosX, float pointPosY, 
			float pointWidth,float pointHeight,
			float pointSpeedX, float pointSpeedY,
			int pointAtlasIndex, float pointAplha, float keyFrameDelay) {
		super();
		this.pointPosX = pointPosX;
		this.pointPosY = pointPosY;
		this.pointWidth = pointWidth;
		this.pointHeight = pointHeight;
		this.pointSpeedX = pointSpeedX;
		this.pointSpeedY = pointSpeedY;
		this.pointAtlasIndex = pointAtlasIndex;
		this.pointAplha = pointAplha;
		this.keyFrameDelay = keyFrameDelay;
		this.animation = new Animation(keyFrameDelay, GameAssets.texturePointAtlas[pointAtlasIndex].getRegions());

		
	}
	
}
