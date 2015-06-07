package com.Sct.gamebone.view;

import android.graphics.Canvas;

public abstract class BaseNode {
	public int x = 0;
	public int y = 0;

	public float anchorX = 0f;
	public float anchorY = 0f;

	public int width = 0;
	public int height = 0;

	public int getRealX() {
		return (int) (x + width * anchorX);
	}

	public int getRealY() {
		return (int) (y + height * anchorY);
	}

	protected void onDraw(Canvas canvas) {
	}

	protected void update(float delta) {
	}
}
