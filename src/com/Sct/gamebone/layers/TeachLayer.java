package com.Sct.gamebone.layers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

import com.Sct.gamebone.CandyScene;
import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.BaseLayer.onTouchListener;
import com.Sct.gamebone.view.TextSprite;

public class TeachLayer extends BaseLayer implements onTouchListener {
	public int state = 0;

	public static final int STATUS_AREA = 0;
	public static final int DETECT_LINE = 1;
	public static final int ENTRY = 2;
	public static final int EXIT = 3;
	public static final int OBSTACLE = 4;
	public static final int TOOLS = 5;
	public static final int PRICE = 6;
	public static final int CURRENT_TOOL = 7;
	public static final int MISSION = 8;

	public RectF mStatusArea = null;
	public RectF mDetectLine = null;
	public Circle mEntry = null;
	public Circle mExit = null;
	public Circle mObstacle = null;
	public RectF mTools = null;
	public RectF mCurrentTool = null;
	public RectF mPrice = null;

	public TextSprite mDescription = null;
	public Path mPath = null;

	public CandyScene mScene = null;

	public TeachLayer(CandyScene s) {
		mScene = s;
	}

	@Override
	public void initLayer() {
		// TODO Auto-generated method stub
		super.initLayer();
		mStatusArea = new RectF(40, 40, width - 40, 160);
		mDetectLine = new RectF(60, 300 - 64, width - 60, 340 - 32);
		mEntry = new Circle(140 + 20, 274, 128);
		mExit = new Circle(930, 1720 - 40, 128);
		mObstacle = new Circle(500 + 250 - 3 * 128, 700 + 256, 230);
		mTools = new RectF(150, 1720, 1080 - 200, 1820 + 20);
		mPrice = new RectF(150, 1820, 1080 - 200, 1900);
		mCurrentTool = new RectF(5, 1572, 150 + 30, 1920 - 50);

		mPath = new Path();
		mDescription = new TextSprite("");
		mDescription.anchorX = 0.5f;
		mDescription.x = width / 2;
		mDescription.y = height / 3 * 2;
		mDescription.setTextColor(Color.WHITE);
		mDescription.setTextSize(70);
		mDescription.setMaxLengthInLine(10);

		updateStatus();
		setOnTouchListener(this);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.save();
		canvas.clipPath(mPath, Region.Op.DIFFERENCE);
		// canvas.clipRectF(0, 0, width, 200, Region.Op.DIFFERENCE);
		drawColor(canvas);
		mDescription.onDraw(canvas);
		canvas.restore();
	}

	public void drawColor(Canvas canvas) {
		canvas.drawColor(Color.argb(200, 0, 0, 52));
	}

	@Override
	public void enter() {
		// TODO Auto-generated method stub
		super.enter();
		mScene.mGameLayer.stopDetectCandy();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		super.exit();
	}

	public static class Circle {
		int x = 0;
		int y = 0;
		int radius = 0;

		public Circle() {

		}

		public Circle(int x, int y, int raduis) {
			this.x = x;
			this.y = y;
			this.radius = raduis;
		}
	}

	@Override
	public boolean doTouch(int x, int y) {
		// TODO Auto-generated method stub
		state++;
		updateStatus();
		return true;
	}

	public void updateStatus() {
		mPath.reset();
		GameApp app = GameApp.getApplication();
		String text = null;
		switch (state) {
		case STATUS_AREA:
			text = app.getString("status_area");
			mDescription.setText(text);
			mPath.addRect(mStatusArea, Path.Direction.CCW);
			break;
		case DETECT_LINE:
			mScene.mGameLayer.startDetectCandy();
			text = app.getString("detect_line");
			mDescription.setText(text);
			mPath.addRect(mDetectLine, Path.Direction.CCW);
			break;
		case ENTRY:
			text = app.getString("entry");
			mDescription.setText(text);
			mPath.addCircle(mEntry.x, mEntry.y, mEntry.radius,
					Path.Direction.CCW);
			break;
		case EXIT:
			text = app.getString("exit");
			mDescription.setText(text);
			mPath.addCircle(mExit.x, mExit.y, mExit.radius, Path.Direction.CCW);
			break;
		case OBSTACLE:
			text = app.getString("obstacle");
			mDescription.setText(text);
			mPath.addCircle(mObstacle.x, mObstacle.y, mObstacle.radius,
					Path.Direction.CCW);
			break;
		case TOOLS:
			text = app.getString("tools");
			mDescription.setText(text);
			mPath.addRect(mTools, Path.Direction.CCW);
			break;
		case PRICE:
			text = app.getString("price");
			mDescription.setText(text);
			mPath.addRect(mPrice, Path.Direction.CCW);
			break;
		case CURRENT_TOOL:
			text = app.getString("current_tool");
			mDescription.setText(text);
			mPath.addRect(mCurrentTool, Path.Direction.CCW);
			break;
		case MISSION:
			text = app.getString("mission");
			mDescription.setText(text);
			break;
		default:
			isVisible = false;
			break;
		}
	}

}
