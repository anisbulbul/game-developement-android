package com.anisbulbul.car.race.renderer;

import com.badlogic.gdx.graphics.Texture;

public class GameEndAnimation {

	public float gateX;
	public float gateY;
	public float gateW;
	public float gateH;
	public Texture gateTexture;

	/**
	 * @param gateX
	 * @param gateY
	 * @param gateH
	 * @param gateW
	 * @param gateTexture
	 */
	public GameEndAnimation(float gateX, float gateY, float gateW, float gateH,
			Texture gateTexture) {
		super();
		this.gateX = gateX;
		this.gateY = gateY;
		this.gateW = gateW;
		this.gateH = gateH;
		this.gateTexture = gateTexture;
	}

}
