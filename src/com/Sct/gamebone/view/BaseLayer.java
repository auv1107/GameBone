package com.Sct.gamebone.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Point;

import com.Sct.gamebone.framework.GameApp;

public class BaseLayer extends BaseNode {
	protected List<BaseNode> mChildrenList = new ArrayList<BaseNode>();
	private onTouchListener mListener = null;
	public boolean isSwallow = false;

	public BaseLayer() {
		width = GameApp.getApplication().getScreenWidth();
		height = GameApp.getApplication().getScreenHeight();
	}

	public interface onTouchListener {
		public boolean doTouch(int x, int y);
	}

	public void initLayer() {

	}

	public void enter() {
	}

	public void exit() {

	}

	@Override
	public void onDraw(Canvas canvas) {
		for (BaseNode n : mChildrenList) {
			if (n.isVisible)
				n.onDraw(canvas);
		}
	}

	@Override
	public void update(float delta) {
		for (BaseNode n : mChildrenList) {
			n.update(delta);
		}
	}

	public void setOnTouchListener(onTouchListener l) {
		mListener = l;
	}

	public boolean onTouch(int x, int y) {
		if (mListener != null)
			return mListener.doTouch(x, y);
		return false;
	}

	public void addChild(BaseNode n) {
		if (n == this)
			return;

		if (mChildrenList.contains(n)) {
			mChildrenList.remove(n);
		}
		mChildrenList.add(n);
	}

	public void removeChild(BaseNode n) {
		if (mChildrenList.contains(n)) {
			mChildrenList.remove(n);
		}
	}

	public void removeAllChildren() {
		mChildrenList.clear();
	}

	public static Point coordinateLayer2Screen(BaseLayer layer, int x, int y) {
		return new Point(layer.getRealX() + x, layer.getRealY() + y);
	}

	public static Point coordinateScreen2Layer(BaseLayer layer, int x, int y) {
		return new Point(x - layer.getRealX(), y - layer.getRealY());
	}
}
