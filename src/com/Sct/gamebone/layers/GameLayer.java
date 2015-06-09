package com.Sct.gamebone.layers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.Sct.gamebone.CandyScene;
import com.Sct.gamebone.StageData.MapInfo;
import com.Sct.gamebone.ToolFactory;
import com.Sct.gamebone.ToolFactory.Tool;
import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.library.BitmapCache;
import com.Sct.gamebone.library.TileCache;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.BaseLayer.onTouchListener;

public class GameLayer extends BaseLayer implements onTouchListener {
	public CandyScene mScene = null;
	public int row = 0;
	public int col = 0;
	public MapInfo mMap = null;

	public List<Tool> mTool_layer;

	private int mDstId = -1;

	public GameLayer(CandyScene scene) {
		mScene = scene;
		row = mScene.info.row;
		col = mScene.info.col;
		mMap = mScene.info;
		setOnTouchListener(this);
		mTool_layer = new ArrayList<Tool>(Collections.nCopies(row * col,
				(Tool) null));
	}

	@Override
	public void onDraw(Canvas canvas) {
		drawBackground(canvas);
		drawObstacle(canvas);
		drawEntry(canvas);
		drawExit(canvas);
		drawTools(canvas);
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
		Rect dst = getRect(mMap.entry_pos);
		Bitmap b = BitmapCache.get("enter_btn");
		canvas.drawBitmap(b, new Rect(0, 0, b.getWidth(), b.getHeight()), dst,
				GameApp.getApplication().getTempPaint());
	}

	public void drawExit(Canvas canvas) {
		Rect dst = getRect(mMap.exit_pos);
		Bitmap b = BitmapCache.get("small_blue_btn");
		canvas.drawBitmap(b, new Rect(0, 0, b.getWidth(), b.getHeight()), dst,
				GameApp.getApplication().getTempPaint());
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
	public void doTouch(int x, int y) {
		// TODO Auto-generated method stub
		mDstId = getId(x, y);
		if (mDstId == -1)
			return;
		if (mMap.obstacle_layer.get(mDstId) != 0) {
			if (mScene.mIsMenuShown) {
				mDstId = -1;
				mScene.hideMenu();
			}
			return;
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
	}

	public void removeTool() {
		if (mDstId == -1)
			return;
		mTool_layer.set(mDstId, null);
		mDstId = -1;
	}
}
