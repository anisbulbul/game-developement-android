package com.anrosoft.jetadventure.assets;

public class SnackEnemy {

	public float snackPosX;
	public float snackPosY;

	public float snackWidth;
	public float snackHeight;

	public float snackSpeedX;

	public float snackSpeedY;

	public float snackIncrementX;
	public float snackIncrementY;

	public float snackLengthTempX;
	public float snackLengthX;
	public float snackLengthY;

	public int snackTextureIndex;

	public float snackAplha;

	public boolean isSnackEnemy;
	
	public float snackRotation;

	public SnackEnemy(float snackPosX, float snackPosY, float snackWidth,
			float snackHeight, float snackSpeedX, float snackSpeedY,
			float snackIncrementX, float snackIncrementY, float snackLengthX,
			float snackLengthY, int snackTextureIndex, float snackAplha,
			boolean isSnackEnemy) {
		super();
		this.snackPosX = snackPosX;
		this.snackPosY = snackPosY;
		this.snackWidth = snackWidth;
		this.snackHeight = snackHeight;
		this.snackSpeedX = snackSpeedX;
		this.snackSpeedY = snackSpeedY;
		this.snackIncrementX = snackIncrementX;
		this.snackIncrementY = snackIncrementY;
		this.snackLengthX = snackLengthX;
		this.snackLengthY = snackLengthY;
		this.snackTextureIndex = snackTextureIndex;
		this.snackAplha = snackAplha;
		this.isSnackEnemy = isSnackEnemy;

		this.snackLengthTempX = 0;
		this.snackRotation = 0;

	}

}
