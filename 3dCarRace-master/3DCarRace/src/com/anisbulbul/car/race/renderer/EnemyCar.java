package com.anisbulbul.car.race.renderer;

import com.badlogic.gdx.graphics.Texture;

public class EnemyCar {

	public Texture carTexture;
	public float carW;
	public float carH;
	public float carX;
	public float carY;

	public EnemyCar(Texture carTexture, float carW, float carH, float carX,
			float carY) {
		super();
		this.carTexture = carTexture;
		this.carW = carW;
		this.carH = carH;
		this.carX = carX;
		this.carY = carY;
	}

}
