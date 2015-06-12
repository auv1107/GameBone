package com.Sct.gamebone.layers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

import com.Sct.gamebone.Candy;
import com.Sct.gamebone.Candy.onCandyMovedListener;
import com.Sct.gamebone.CandyScene;
import com.Sct.gamebone.StageData.MapInfo;
import com.Sct.gamebone.ToolFactory;
import com.Sct.gamebone.ToolFactory.Tool;
import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.library.SoundCache;
import com.Sct.gamebone.library.TileCache;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.BaseLayer.onTouchListener;
import com.Sct.gamebone.view.FrameAnimation;
import com.Sct.gamebone.view.Sprite;

public class GameLayer extends BaseLayer implements onTouchListener {
	public CandyScene mScene = null;
	public int row = 0;
	public int col = 0;
	public MapInfo mMap = null;

	public List<Tool> mTool_layer;

	private int mDstId = -1;

	private FrameAnimation mExitAnimation = null;
	private Candy mCandy = null;

	private Path mDetectPath = null;
	private Candy mDetectCandy = null;
	private Paint mDetectPaint = null;

	public GameLayer(CandyScene scene) {
		mScene = scene;
		width = 1024;
		height = 1536;
		row = mScene.info.row;
		col = mScene.info.col;
		mMap = mScene.info;
		setOnTouchListener(this);
		mTool_layer = new ArrayList<Tool>(Collections.nCopies(row * col,
				(Tool) null));

		int firstgid = TileCache.getFirstGid("light");
		Rect dst = getRect(mMap.exit_pos);
		mExitAnimation = new FrameAnimation();
		for (int i = 4; i < 14; i++)
			mExitAnimation.addFrame(TileCache.get(firstgid + i));
		mExitAnimation.x = dst.left;
		mExitAnimation.y = dst.top;
		mExitAnimation.start();

		mCandy = new Candy(this, new Sprite("game_candy1"));
		final Rect r = getRect(mMap.entry_pos);
		mCandy.setX(r.left);
		mCandy.setY(r.top);
		mCandy.setSpeed(3);
		mCandy.setOnCandyMovedListener(new onCandyMovedListener() {

			@Override
			public void onCandyMoved(Candy c) {
				// TODO Auto-generated method stub
				for (int i = 0; i < mTool_layer.size(); i++) {
					Tool t = mTool_layer.get(i);
					if (t != null) {
						if (t.has(c) && !t.contains(c, getRect(i))) {
							t.lose(c);
						}
						if (!t.has(c) && t.contains(c, getRect(i))) {
							t.get(c);
						}
					}
				}
			}
		});

		mDetectPath = new Path();
		mDetectPath.moveTo(r.left + r.width() / 2, r.top + r.height() / 2);
		Toast.makeText(
				GameApp.getApplication(),
				"r: " + dst.left + ", " + dst.top + ", " + dst.width() + ", "
						+ dst.height(), Toast.LENGTH_LONG).show();

		mDetectPaint = new Paint();
		mDetectPaint.setAntiAlias(true); // 设置画笔为无锯齿
		mDetectPaint.setColor(Color.BLACK); // 设置画笔颜色
		mDetectPaint.setStrokeWidth((float) 3.0); // 线宽
		mDetectPaint.setStyle(Style.STROKE);

		mDetectCandy = new Candy(this, new Sprite("game_candy1"));
		mDetectCandy.setX(r.left);
		mDetectCandy.setY(r.top);
		mDetectCandy.setSpeed(5);
		mDetectCandy.setOnCandyMovedListener(new onCandyMovedListener() {

			@Override
			public void onCandyMoved(Candy c) {
				// TODO Auto-generated method stub
				Log.d("gamelayer", "lineto: " + (c.getX() + r.width() / 2)
						+ ", " + (c.getY() + r.height() / 2));
				mDetectPath.lineTo(c.getX() + r.width() / 2,
						c.getY() + r.height() / 2);
				if (!new Rect(0, 0, GameLayer.this.width, GameLayer.this.height)
						.contains(c.getX(), c.getY())) {
					c.stop();
				}
				for (int i = 0; i < mTool_layer.size(); i++) {
					Tool t = mTool_layer.get(i);
					if (t != null) {
						if (t.has(c) && !t.contains(c, getRect(i))) {
							t.lose(c);
						}
						if (!t.has(c) && t.contains(c, getRect(i))) {
							t.get(c);
						}
					}
				}
				if (getRect(mMap.exit_pos).contains(c.getX() + r.width() / 2,
						c.getY() + r.height() / 2)) {
					mScene.pass();
				}
			}
		});
	}

	@Override
	public void enter() {
		mCandy.start();
		mDetectCandy.start();
	}

	@Override
	public void exit() {
		mCandy.stop();
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
		mExitAnimation.update(delta);
		mCandy.update(delta);
		mDetectCandy.update(delta);
	}

	public void drawDetectPath(Canvas canvas) {
		// canvas.drawPath(mDetectPath, mDetectPaint);
		canvas.drawPath(mDetectPath, mDetectPaint);
	}

	public void drawBackground(Canvas canvas) {
		List<Integer> l = mMap.bg_layer;
		for (int i = 0; i < l.size(); i++) {
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
		for (Tool t : mTool_layer) {
			if (t != null)
				t.s.onDraw(canvas);
		}
	}

	public void drawEntry(Canvas canvas) {
	}

	public void drawExit(Canvas canvas) {
		mExitAnimation.onDraw(canvas);
	}

	public void drawCandy(Canvas canvas) {
		mCandy.onDraw(canvas);
	}

	public Rect getRect(int id) {
		int pWidth = width / col;
		int pHeight = height / row;
		int c = id % col;
		int r = id / col;
		return new Rect(c * pWidth, r * pHeight, c * pWidth + pWidth, r
				* pHeight + pHeight);
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
			// 显示移动、移除按钮
			Rect r = getRect(mDstId);
			Point p = coordinateLayer2Screen(this, r.left, r.top);
			mScene.showMenu(p.x, p.y, 1 << MenuLayer.MOVE_BUTTON
					| 1 << MenuLayer.REMOVE_BUTTON);
		}
		return true;
	}

	public void placeTool(int tooltype) {
		if (mDstId == -1)
			return;
		Tool t = ToolFactory.getTool(tooltype);
		Rect r = getRect(mDstId);
		t.s.x = r.left;
		t.s.y = r.top;
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

	public void rebuildPath() {
		SoundCache.PlayAudio("follow06");
		mDetectCandy.stop();
		mDetectPath.reset();
		final Rect r = getRect(mMap.entry_pos);
		mDetectPath.moveTo(r.left + r.width() / 2, r.top + r.height() / 2);
		mDetectCandy.setX(r.left);
		mDetectCandy.setY(r.top);
		mDetectCandy.setSpeed(13);
		mDetectCandy.setDirection(0);
		mDetectCandy.start();
	}
}
