package com.Sct.gamebone;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

public class GameEngine {
	protected Context mContext = null;
	protected int Mode = NORMAL;
	protected int mGameState = GAME;

	public final static int NORMAL = 0;
	public final static int MAP_EDIT = 1;

	protected GameData mGameData = null;
	protected Paint mBodyPaint = null;
	protected Paint mHeadPaint = null;
	protected Paint mDoorPaint = null;
	protected Paint mObstaclePaint = null;

	public final static int GAME = 0;
	public final static int WIN = 1;
	public final static int LOSE = 2;

	public final static int DELTA = 30;
	public long lastUpdateTime = 0;
	public boolean mIsRunning = false;

	private long lastMoveTime = 0;

	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (mIsRunning) {
				long time = System.currentTimeMillis();
				if (time - lastUpdateTime >= DELTA) {
					time = System.currentTimeMillis();
					update((int) (time - lastUpdateTime));
					lastUpdateTime = time;
				}
				Thread.yield();
			}
		}
	};

	public GameEngine(Context context) {
		mContext = context;
		mGameData = new GameData();
		mBodyPaint = new Paint();
		mBodyPaint.setColor(Color.YELLOW);
		mHeadPaint = new Paint();
		mHeadPaint.setColor(Color.GREEN);
		mDoorPaint = new Paint();
		mDoorPaint.setColor(Color.YELLOW);
		mDoorPaint.setStyle(Paint.Style.FILL);
		mObstaclePaint = new Paint();
		mObstaclePaint.setColor(Color.MAGENTA);
	}

	public void initGame() {
		mGameState = GAME;
		mGameData.initData(0);
		lastUpdateTime = System.currentTimeMillis();
		lastMoveTime = System.currentTimeMillis();
		mIsRunning = true;
		new Thread(mRunnable).start();
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
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setTextSize(40);
		switch (mGameState) {
		case GAME:
			break;
		case WIN:
			break;
		case LOSE:
			canvas.drawColor(Color.argb(100, 0xff, 0xff, 0xff));
			canvas.drawText("Lose",
					GameApp.getApplication().getScreenWidth() / 2, GameApp
							.getApplication().getScreenHeight() / 2, p);
			break;
		}
	}

	private void drawGame(Canvas canvas) {
		// TODO Auto-generated method stub
		drawObstacle(canvas);
		drawDoor(canvas);
		drawSnake(canvas);
	}

	private void drawBackground(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawColor(Color.BLACK);
	}

	public void onTouch(float x, float y) {
		switch (mGameState) {
		case GAME:
			onGameTouch(x, y);
			break;
		case WIN:
			break;
		case LOSE:
			initGame();
			break;
		}
	}

	public void onGameTouch(float x, float y) {
		if (x < GameApp.getApplication().getScreenWidth() / 2) {
			mGameData.Turn(GameData.LEFT);
		} else {
			mGameData.Turn(GameData.RIGHT);
		}
	}

	private void drawHead(Canvas canvas, int headId) {
		canvas.drawRect(mGameData.getRect(headId), mHeadPaint);
	}

	private void drawBody(Canvas canvas, int id) {
		Rect r = mGameData.getRect(id);
		int size = r.height();
		int margin = size / 10;
		canvas.drawRect(new Rect(r.left + margin, r.top + margin, r.right
				- margin, r.bottom - margin), mBodyPaint);
	}

	private void drawSnake(Canvas canvas) {
		List<Integer> snake = mGameData.snake;
		if (snake.size() > 0) {
			drawHead(canvas, snake.get(0));
		}
		for (int i = 1; i < snake.size(); i++) {
			drawBody(canvas, snake.get(i));
		}
	}

	private void drawDoor(Canvas canvas) {
		if (mGameData.doorId != -1) {
			Rect r = mGameData.getRect(mGameData.doorId);
			int margin = r.height() / 10;
			canvas.save();
			canvas.rotate(mGameData.doorDirection * 90, r.centerX(),
					r.centerY());
			Path p = new Path();
			p.moveTo(r.left - margin, r.top + margin * 3);
			p.lineTo(r.left + margin + margin, r.bottom - margin * 3);
			p.lineTo(r.right - margin - margin, r.bottom - margin * 3);
			p.lineTo(r.right + margin, r.top + margin * 3);
			p.close();
			canvas.drawPath(p, mDoorPaint);
			canvas.restore();
		}
	}

	private void drawObstacle(Canvas canvas) {
		for (int i = 0; i < mGameData.obstacle.size(); i++) {
			int id = mGameData.obstacle.get(i);
			Rect r = mGameData.getRect(id);
			canvas.drawCircle(r.centerX(), r.centerY(), r.height() / 2,
					mObstaclePaint);
		}
	}

	public void update(int delta) {
		long time = System.currentTimeMillis();
		if (time - lastMoveTime >= mGameData.speed) {
			mGameData.Move();
			if (mGameData.checkIfReached())
				mGameData.GenerateNewDoor();
			if (mGameData.checkIfCollided()) {
				gameOver();
			}
			lastMoveTime = time;
		}
	}

	public void exit() {
		mIsRunning = false;
	}

	public void gameOver() {
		mIsRunning = false;
		mGameState = LOSE;
	}
}
