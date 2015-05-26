package com.Sct.gamebone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.Sct.gamebone.TileCache;

public class NAnimator {

	private int delta = 50;
	private long lastStateTime = 0;
	private int state = 0;
	private int totalState = 0;
	private int[] stateId = new int[] {};
	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int height = 0;
	protected int rotation = 0;
	protected int rotatespeed = 0;
	protected int speed = 0;
	protected int degree = 0;
	protected boolean running = false;

	public NAnimator(Context context, int[] stateIds, int x, int y, int width,
			int height) {
		// TODO Auto-generated constructor stub
		setStateTileId(stateIds);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void pause() {
		running = false;
	}

	public void resume() {
		running = true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setRotateSpeed(int d) {
		rotatespeed = d;
	}

	public void setStateTileId(int[] ids) {
		stateId = ids;
		totalState = ids.length;
	}

	public void onDraw(Canvas canvas) {
		long time = System.currentTimeMillis();
		if (time - lastStateTime >= delta) {
			update((int) (time - lastStateTime));
			lastStateTime = time;
			doDraw(canvas);
		}
	}

	protected void update(int delta) {
		if (running) {
			nextState();
			rotation = (rotation + delta * rotatespeed / 1000) % 360;
			y += speed * Math.cos(degree) * delta / 1000;
			x += speed * Math.sin(degree) * delta / 1000;
		}
	}

	private void nextState() {
		state = (state + 1) % totalState;
	}

	public void doDraw(Canvas canvas) {
		if (totalState != 0) {
			Rect dst = new Rect(x, y, x + width, y + height);
			canvas.save();
			canvas.rotate(rotation, dst.centerX(), dst.centerY());
			TileCache.Draw(canvas, stateId[state], dst, new Paint());
			canvas.restore();
		}
	}

	public NAnimator setSpeed(int speed) {
		this.speed = speed;
		return this;
	}

	public NAnimator setDegree(int degree) {
		this.degree = degree;
		return this;
	}
}
