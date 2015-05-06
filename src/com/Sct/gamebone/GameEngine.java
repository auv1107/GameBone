package com.Sct.gamebone;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameEngine {
	protected Context mContext = null;
	protected int mGameState = 0;

	public final static int GAME = 0;
	public final static int WIN = 1;
	public final static int LOSE = 2;

	public GameEngine(Context context) {
		mContext = context;
	}

	public void initGame() {

	}

	public void onDraw(Canvas canvas) {
		drawBackground(canvas);
		drawGame(canvas);
		drawAnimation(canvas);
		drawHud(canvas);
		drawState(canvas);
	}

	private void drawHud(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	private void drawAnimation(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	private void drawState(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	private void drawGame(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	private void drawBackground(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_launcher), new Rect(0, 0, 800, 800), new Rect(0,
				0, 800, 800), new Paint());
	}

	public void onTouch(float x, float y) {
		switch (mGameState) {
		case GAME:
			onGameTouch(x, y);
			break;
		case WIN:
			break;
		case LOSE:
			break;
		}
	}

	public void onGameTouch(float x, float y) {

	}
}
