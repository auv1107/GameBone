package com.Sct.gamebone.tools;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.Sct.gamebone.Candy;
import com.Sct.gamebone.view.Sprite;

public class BaseTool {
	public Sprite s = null;
	public int price = 0;
	public int type = -1;
	public int state = 0;
	public List<Candy> mCandyList = new ArrayList<Candy>();

	public void get(Candy c) {
		if (mCandyList.contains(c)) {
			mCandyList.remove(c);
		}
		mCandyList.add(c);
	}

	public void lose(Candy c) {
		mCandyList.remove(c);
	}

	// 在Tool的candy集合里有c
	public boolean has(Candy c) {
		if (mCandyList.contains(c)) {
			return true;
		} else {
			return false;
		}
	}

	// 在Tool的范围内包含C
	public boolean contains(Candy c, Rect gridBound) {
		int xdiff = c.getCenterX() - gridBound.centerX();
		int ydiff = c.getCenterY() - gridBound.centerY();
		int dist2 = xdiff * xdiff + ydiff * ydiff;
		if (dist2 <= gridBound.width() * gridBound.width() / 9)
			return true;
		return false;
	}

	public void rotate() {

	}
}