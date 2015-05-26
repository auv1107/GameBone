package com.Sct.gamebone;

import com.Sct.gamebone.fragment.BaseGameEngine;
import com.Sct.gamebone.fragment.GameData;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.view.MotionEvent;
import android.widget.Toast;

public class MapEditor extends BaseGameEngine {

	public GameData mGameData = null;
	private Context mContext = null;
	private int selected = -1;
	private Rect[] optionRect = new Rect[] { new Rect(20, 120, 80, 180),
			new Rect(20, 190, 80, 250), new Rect(20, 300, 80, 360) };

	protected Paint mObstaclePaint = null;

	public MapEditor(Context context) {
		mContext = context;
		mObstaclePaint = new Paint();
		mObstaclePaint.setColor(Color.MAGENTA);
		mGameData = new GameData();
	}

	@Override
	public void initGame() {
		// TODO Auto-generated method stub
		super.initGame();
	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawBackground(canvas);
		drawObstacle(canvas);
		drawOption(canvas);
		drawSelection(canvas);
		drawSaveButton(canvas);
	}

	@Override
	public void onTouch(MotionEvent e) {
		// TODO Auto-generated method stub
		super.onTouch(e);
		float x = e.getX();
		float y = e.getY();
		if (optionRect[0].contains((int) x, (int) y)) {
			selected = 0;
		}
		if (optionRect[1].contains((int) x, (int) y)) {
			selected = -1;
		}
		if (optionRect[2].contains((int) x, (int) y)) {
			GameData.writeToFile(Environment.getExternalStorageDirectory()
					+ "/test1.json", mGameData);
			Toast.makeText(mContext, "save successful", Toast.LENGTH_LONG)
					.show();
			;
		}
		if (mGameData.getGameRect().contains((int) x, (int) y)) {
			if (selected == 0) {
				mGameData.obstacle.add(mGameData.getIdByPosition((int) x,
						(int) y));
			}
			if (selected == -1) {
				int id = mGameData.getIdByPosition((int) x, (int) y);
				if (mGameData.obstacle.contains(id))
					mGameData.obstacle.remove(Integer.valueOf(id));
			}
		}
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		super.exit();
	}

	private void drawBackground(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawColor(Color.BLACK);
	}

	private void drawObstacle(Canvas canvas) {
		for (int i = 0; i < mGameData.obstacle.size(); i++) {
			int id = mGameData.obstacle.get(i);
			Rect r = mGameData.getRect(id);
			canvas.drawCircle(r.centerX(), r.centerY(), r.height() / 2,
					mObstaclePaint);
		}
	}

	private void drawOption(Canvas canvas) {
		canvas.drawCircle(optionRect[0].centerX(), optionRect[0].centerY(),
				optionRect[0].height() / 2, mObstaclePaint);
		Paint p = new Paint();
		p.setColor(Color.CYAN);
		Rect r = optionRect[1];
		canvas.drawRect(r.left + 10, r.top + 20, r.right - 10, r.bottom - 20, p);
	}

	private void drawSelection(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setStyle(Paint.Style.STROKE);
		if (selected == -1) {
			canvas.drawRect(optionRect[1], p);
		}
		if (selected == 0) {
			canvas.drawRect(optionRect[0], p);
		}
	}

	private void drawSaveButton(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.argb(200, 0xcc, 0x66, 0x33));
		canvas.drawText("save", optionRect[2].centerX(),
				optionRect[2].centerY(), paint);
	}
}
