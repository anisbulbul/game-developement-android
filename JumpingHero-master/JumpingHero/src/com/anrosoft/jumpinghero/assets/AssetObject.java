package com.anrosoft.jumpinghero.assets;

public class AssetObject {

	public float assetPosX;
	public float assetPosY;
	public float assetWidth;
	public float assetHeight;
	public float assetSpeedX;
	public float assetSpeedY;
	public int assetTextureIndex;
	public String assetTextureCategory;

	public float assetAplha;

	public boolean isAssetEnemy;

	public boolean isHeroJumpBY;

	/**
	 * @param assetPosX
	 * @param assetPosY
	 * @param assetWidth
	 * @param assetHeight
	 * @param isassetEnemy
	 */
	public AssetObject(float assetPosX, float assetPosY, float assetWidth,
			float assetHeight, float assetSpeedX, float assetSpeedY,
			int assetTextureIndex, boolean isAssetEnemy,
			String assetTextureCategory, float assetAplha) {
		super();
		this.assetPosX = assetPosX;
		this.assetPosY = assetPosY;
		this.assetWidth = assetWidth;
		this.assetHeight = assetHeight;
		this.assetSpeedX = assetSpeedX;
		this.assetSpeedY = assetSpeedY;
		this.isAssetEnemy = isAssetEnemy;
		this.assetTextureIndex = assetTextureIndex;
		this.assetTextureCategory = assetTextureCategory;
		this.assetAplha = assetAplha;
		this.isHeroJumpBY = false;
	}

}
