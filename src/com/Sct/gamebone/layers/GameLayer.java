package com.Sct.gamebone.layers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

import com.Sct.gamebone.Candy;
import com.Sct.gamebone.Candy.onCandyMovedListener;
import com.Sct.gamebone.CandyScene;
import com.Sct.gamebone.StageData.MapInfo;
import com.Sct.gamebone.ToolFactory;
import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.library.SoundCache;
import com.Sct.gamebone.library.TileCache;
import com.Sct.gamebone.tools.BaseTool;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.BaseLayer.onTouchListener;
import com.Sct.gamebone.view.FrameAnimation;
import com.Sct.gamebone.view.Sprite;

public class GameLayer extends BaseLayer implements onTouchListener {
	public CandyScene mScene = null;
	public int row = 0;
	public int col = 0;
	public MapInfo mMap = null;

	public List<BaseTool> mTool_layer;

	private int mDstId = -1;

	private List<Rect> mGridRects = new ArrayList<Rect>();
	private Rect mGameArea = null;

	private FrameAnimation mCandyAnimation = null;

	private Path mDetectPath = null;
	private Candy mDetectCandy = null;
	private Paint mDetectPaint = null;
	private DashPathEffect mDashPathEffect = null;

	public GameLayer(CandyScene scene) {
		mScene = scene;
		width = 1024;
		height = 1536;
		mGameArea = new Rect(0, 0, width, height);
		row = mScene.info.row;
		col = mScene.info.col;
		mMap = mScene.info;
		setOnTouchListener(this);
		mTool_layer = new ArrayList<BaseTool>(Collections.nCopies(row * col,
				(BaseTool) null));

		int pWidth = width / col;
		int pHeight = height / row;
		for (int i = 0; i < col * row; i++) {
			int c = i % col;
			int r = i / col;
			mGridRects.add(new Rect(c * pWidth, r * pHeight, c * pWidth
					+ pWidth, r * pHeight + pHeight));
		}

		Rect r = getRect(mMap.entry_pos);
		mCandyAnimation = new FrameAnimation();
		for (int i = 0; i < 10; i++) {
			Sprite s = new Sprite("ld_candy");
			s.setRotation(i * 36);
			s.width = width / col;
			s.height = height / row;
			mCandyAnimation.addFrame(s);
		}
		mCandyAnimation.setDuration(1000);
		mCandyAnimation.x = r.left;
		mCandyAnimation.y = r.top;
		mCandyAnimation.anchorX = 0.5f;
		mCandyAnimation.anchorY = 0.5f;

		mDetectPath = new Path();
		mDetectPath.moveTo(r.centerX(), r.centerY());
		mDashPathEffect = new DashPathEffect(new float[] { 30, 10 }, 10);

		mDetectPaint = new Paint();
		mDetectPaint.setAntiAlias(true); // 设置画笔为无锯齿
		mDetectPaint.setColor(Color.rgb(0xCC, 0x33, 0x00)); // 设置画笔颜色
		mDetectPaint.setStrokeWidth((float) 10.0); // 线宽
		mDetectPaint.setStyle(Style.STROKE);
		mDetectPaint.setPathEffect(mDashPathEffect);

		mDetectCandy = new Candy(this, new Sprite("game_candy1"));
		mDetectCandy.setRealX(r.left);
		mDetectCandy.setRealY(r.top);
		mDetectCandy.setSpeed(9);
		mDetectCandy.setDelta(50);
		mDetectCandy.setDirection(mScene.info.direction);
		mDetectCandy.setOnCandyMovedListener(new onCandyMovedListener() {

			@Override
			public void onCandyMoved(Candy c) {
				// TODO Auto-generated method stub
				// 若在范围内，获取id
				if (!mGameArea.contains(c.getCenterX(), c.getCenterY())) {
					c.stop();
					return;
				}
				int id = getId(c.getCenterX(), c.getCenterY());

				// 判断是否在道具上
				for (int i = 0; i < mGridRects.size(); i++) {
					BaseTool t = mTool_layer.get(i);
					if (t != null) {
						if (t.has(c) && !t.contains(c, mGridRects.get(i))) {
							t.lose(c);
						}
						if (!t.has(c) && t.contains(c, mGridRects.get(i))) {
							c.setX(getRect(i).centerX());
							c.setY(getRect(i).centerY());
							mDetectPath.lineTo(c.getCenterX(), c.getCenterY());
							t.get(c);
						}
					}
				}
				// 判断是否在障碍物上
				if (mMap.obstacle_layer.get(id) != 0) {
					c.stop();
					return;
				}
				// 是否在出口上
				if (getRect(mMap.exit_pos).contains(c.getCenterX(),
						c.getCenterY())) {
					c.stop();
					mScene.pass();
					return;
				}
				// 划线
				mDetectPath.lineTo(c.getCenterX(), c.getCenterY());
			}
		});
	}

	public void startCandy() {
	}

	@Override
	public void enter() {
		super.enter();
		// mCandy.start();
		mDetectCandy.start();
		mCandyAnimation.start();
	}

	@Override
	public void exit() {
		// mCandy.stop();
		mDetectCandy.stop();
	}

	@Override
	public void onDraw(Canvas canvas) {
		drawBackground(canvas);
		drawObstacle(canvas);
		drawDetectPath(canvas);
		drawEntry(canvas);
		drawExit(canvas);
		drawCandy(canvas);
		drawTools(canvas);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
		// mExitAnimation.update(delta);
		// mCandy.update(delta);
		mDetectCandy.update(delta);
		mCandyAnimation.update(delta);
	}

	public void drawDetectPath(Canvas canvas) {
		// canvas.drawPath(mDetectPath, mDetectPaint);
		canvas.drawPath(mDetectPath, mDetectPaint);
	}

	public void drawBackground(Canvas canvas) {
		List<Integer> l = mMap.bg_layer;
		for (int i = 0; i < row * col; i++) {
			Rect dst = getRect(i);
			TileCache.Draw(canvas, l.get(i), dst, GameApp.getApplication()
					.getTempPaint());
		}
	}

	public void drawObstacle(Canvas canvas) {
		List<Integer> l = mMap.obstacle_layer;
		for (int i = 0; i < l.size(); i++) {
			Rect dst = getRect(i);
			TileCache.Draw(canvas, l.get(i), dst, GameApp.getApplication()
					.getTempPaint());
		}
	}

	public void drawTools(Canvas canvas) {
		for (BaseTool t : mTool_layer) {
			if (t != null)
				t.onDraw(canvas);
		}
	}

	public void drawEntry(Canvas canvas) {
	}

	public void drawExit(Canvas canvas) {
		// mExitAnimation.onDraw(canvas);
	}

	public void drawCandy(Canvas canvas) {
		// mCandy.onDraw(canvas);
		mCandyAnimation.onDraw(canvas);
	}

	public Rect getRect(int id) {
		return mGridRects.get(id);
	}

	public int getId(float x, float y) {
		return getId((int) x, (int) y);
	}

	public int getId(int x, int y) {
		int pWidth = width / col;
		int pHeight = height / row;
		int c = x / pWidth;
		int r = y / pHeight;
		return c + r * col;
	}

	@Override
	public boolean doTouch(int x, int y) {
		// TODO Auto-generated method stub
		SoundCache.PlayAudio("click");
		mDstId = getId(x, y);
		if (mDstId == -1)
			return false;
		if (mMap.obstacle_layer.get(mDstId) != 0) {
			if (mScene.mIsMenuShown) {
				mDstId = -1;
				mScene.hideMenu();
			}
			return false;
		}
		if (mTool_layer.get(mDstId) == null) {
			// 显示放下按钮
			Rect r = getRect(mDstId);
			Point p = coordinateLayer2Screen(this, r.left, r.top);
			mScene.showMenu(p.x, p.y, 1 << MenuLayer.OK_BUTTON
					| 1 << MenuLayer.TOOL_ICON);
		} else {
			mScene.mSelectedToolType = mTool_layer.get(mDstId).type;
			mScene.mStatusLayer.updateCurrentTool(ToolFactory.getTool(
					mScene.mSelectedToolType).getIcon());
			mScene.mMenuLayer.setToolSprite(ToolFactory.getTool(
					mScene.mSelectedToolType).getIcon());
			// 显示移动、移除按钮
			Rect r = getRect(mDstId);
			Point p = coordinateLayer2Screen(this, r.left, r.top);
			mScene.showMenu(p.x, p.y, 1 << MenuLayer.MOVE_BUTTON
					| 1 << MenuLayer.REMOVE_BUTTON
					| 1 << MenuLayer.ROTATE_BUTTON);
		}
		return true;
	}

	public void placeTool(int tooltype) {
		if (mDstId == -1)
			return;
		BaseTool t = ToolFactory.getTool(tooltype);
		Rect r = getRect(mDstId);
		t.setX(r.left);
		t.setY(r.top);
		mTool_layer.set(mDstId, t);
		mDstId = -1;

		SoundCache.PlayAudio("ensure003");

		rebuildPath();
	}

	public void removeTool() {
		if (mDstId == -1)
			return;
		mTool_layer.set(mDstId, null);
		mDstId = -1;

		SoundCache.PlayAudio("cancel001");
		rebuildPath();
	}

	public void rotateTool() {
		if (mDstId == -1) {
			return;
		}
		BaseTool t = mTool_layer.get(mDstId);
		if (t != null) {
			t.rotate();
			rebuildPath();
		}
	}

	public void rebuildPath() {
		// SoundCache.PlayAudio("follow06");
		mDetectCandy.stop();
		mDetectPath.reset();
		Rect r = getRect(mMap.entry_pos);
		mDetectPath.moveTo(r.centerX(), r.centerY());
		mDetectCandy.setRealX(r.left);
		mDetectCandy.setRealY(r.top);
		mDetectCandy.setSpeed(13);
		mDetectCandy.setDirection(mScene.info.direction);
		mDetectCandy.start();
	}

	public void startDetectCandy() {
		mDetectCandy.start();
	}

	public void stopDetectCandy() {
		mDetectCandy.stop();
	}
}
