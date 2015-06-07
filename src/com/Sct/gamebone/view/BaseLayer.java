package com.Sct.gamebone.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

import com.Sct.gamebone.framework.GameApp;

public class BaseLayer extends BaseNode {
	protected List<BaseNode> mChildrenList = new ArrayList<BaseNode>();
	public int width = GameApp.getApplication().getScreenWidth();
	public int height = GameApp.getApplication().getScreenHeight();
	private onTouchListener mListener = null;

	public interface onTouchListener {
		public void onTouch(MotionEvent e);
	}

	@Override
	public void onDraw(Canvas canvas) {
		for (BaseNode n : mChildrenList) {
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

	public void onTouch(MotionEvent e) {
		if (mListener != null)
			mListener.onTouch(e);
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
}
