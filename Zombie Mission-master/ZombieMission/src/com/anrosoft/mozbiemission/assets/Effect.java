package com.anrosoft.mozbiemission.assets;


public class Effect {
	
	public float effectPosX;
	public float effectPosY;
	
	public float effectWidth;
	public float effectHeight;
	
	public float effectSpeedX;
	public float effectSpeedY;
	
	public int effectTextureIndex;
	
	public float effectAplha;

	public Effect(float effectPosX, float effectPosY, float effectWidth,
			float effectHeight, float effectSpeedX, float effectSpeedY,
			int effectTextureIndex, float assetAplha) {
		super();
		this.effectPosX = effectPosX;
		this.effectPosY = effectPosY;
		this.effectWidth = effectWidth;
		this.effectHeight = effectHeight;
		this.effectSpeedX = effectSpeedX;
		this.effectSpeedY = effectSpeedY;
		this.effectTextureIndex = effectTextureIndex;
		this.effectAplha = assetAplha;
	}
	
}
