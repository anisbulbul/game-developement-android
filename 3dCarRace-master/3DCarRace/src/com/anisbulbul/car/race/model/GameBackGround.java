package com.anisbulbul.car.race.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameBackGround {
	public static final float SIZE = 0.5f;

	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	Vector2 velocity = new Vector2();

	public GameBackGround(Vector2 position) {
		super();
		this.position = position;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE + .10f;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void update(float delta) {
		position.add(velocity.cpy().mul(delta));

	}

	public static float getSize() {
		return SIZE;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
