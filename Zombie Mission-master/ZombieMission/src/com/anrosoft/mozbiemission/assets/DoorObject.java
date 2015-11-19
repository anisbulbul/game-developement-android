package com.anrosoft.mozbiemission.assets;

public class DoorObject {
	
	public float doorPosX;
	public float doorPosY;
	public float doorWidth;
	public float doorHeight;
	public float doorSpeedX;
	public float doorSpeedY;
	public int doorTextureIndex;
	public String doorTextureCategory;
	
	public float doorAplha;
	
	public boolean isdoorEnemy;

	/**
	 * @param doorPosX
	 * @param doorPosY
	 * @param doorWidth
	 * @param doorHeight
	 * @param isdoorEnemy
	 */
	public DoorObject(float doorPosX, float doorPosY, 
			float doorWidth,float doorHeight,
			float doorSpeedX, float doorSpeedY,
			int doorTextureIndex, boolean isdoorEnemy, String doorTextureCategory,
			float doorAplha) {
		super();
		this.doorPosX = doorPosX;
		this.doorPosY = doorPosY;
		this.doorWidth = doorWidth;
		this.doorHeight = doorHeight;
		this.doorSpeedX = doorSpeedX;
		this.doorSpeedY = doorSpeedY;
		this.isdoorEnemy = isdoorEnemy;
		this.doorTextureIndex = doorTextureIndex;
		this.doorTextureCategory = doorTextureCategory;
		this.doorAplha = doorAplha;
	}
	
}
