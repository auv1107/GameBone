package com.Sct.gamebone;

import android.graphics.Canvas;

import com.Sct.gamebone.layers.GameLayer;
import com.Sct.gamebone.tools.BaseTool;
import com.Sct.gamebone.view.Sprite;

public class Candy {
	public Sprite s = null;
	private GameLayer mGameLayer = null;
	private int mDirection = 0;
	private int mSpeed = 1;
	private int mPosition = 0;
	private boolean mIsRunning = false;
	private int delta = 100;
	private long lastMoveTime = 0;

	private onCandyMovedListener mListener = null;

	public interface onCandyMovedListener {
		public void onCandyMoved(Candy c);
	}

	public void setOnCandyMovedListener(onCandyMovedListener l) {
		mListener = l;
	}

	public Candy(GameLayer l, Sprite s) {
		this.s = s;
		mGameLayer = l;
		this.s.width = mGameLayer.width / mGameLayer.col;
		this.s.height = mGameLayer.height / mGameLayer.row;

	}

	public void enter(BaseTool t) {
		t.get(this);
	}

	public void exit(BaseTool t) {
		t.lose(this);
	}

	public void update(float delta) {
		if (mIsRunning) {
			long t = System.currentTimeMillis();
			if (t - lastMoveTime >= this.delta) {
				lastMoveTime = t;
				int peerX = (int) (delta / 1000 * s.width * Math.cos(mDirection
						* Math.PI / 180));
				int peerY = (int) (delta / 1000 * s.width * Math.sin(mDirection
						* Math.PI / 180));
				s.x += mSpeed * peerX;
				s.y += mSpeed * peerY;
				if (mListener != null) {
					mListener.onCandyMoved(this);
				}
			}
		}
	}

	public void onDraw(Canvas canvas) {
		if (s != null)
			s.onDraw(canvas);
	}

	public Candy start() {
		mIsRunning = true;
		return this;
	}

	public Candy stop() {
		mIsRunning = false;
		return this;
	}

	/***
	 *** getter and setters
	 ***/
	public int getX() {
		return s.x;
	}

	public int getY() {
		return s.y;
	}

	public Candy setX(int x) {
		s.x = x;
		return this;
	}

	public Candy setY(int y) {
		s.y = y;
		return this;
	}

	public Candy setDirection(int d) {
		mDirection = d;
		return this;
	}

	public Candy setSpeed(int s) {
		mSpeed = s;
		return this;
	}

	public int getPosition() {
		return mPosition;
	}

	public void setPosition(int p) {
		mPosition = p;
	}

	public int getDirection() {
		return mDirection;
	}

	public int getSpeed() {
		return mSpeed;
	}

	public int getDelta() {
		return delta;
	}

	public Candy setDelta(int delta) {
		this.delta = delta;
		return this;
	}

	public int getCenterX() {
		return s.getCenterX();
	}

	public int getCenterY() {
		return s.getCenterY();
	}

}
