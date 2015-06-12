package com.Sct.gamebone;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

import com.Sct.gamebone.library.BitmapCache;
import com.Sct.gamebone.view.Sprite;

public class ToolFactory {
	public static final int NONE = -1;
	public static final int SHEILD = 0;
	public static final int BLOWER = 1;
	public static final String SHEILD_BITMAP = "game_tool_sheild";
	public static final String BLOWER_BITMAP = "game_tool_blower";
	public static final String LOCK_BITMAP = "game_lock";

	public static Tool getTool(int id) {
		Tool t = new Tool();
		switch (id) {
		case SHEILD:
			t.s = new Sprite(BitmapCache.get(SHEILD_BITMAP));
			t.price = 15;
			t.type = SHEILD;
			break;
		case BLOWER:
			t.s = new Sprite(BitmapCache.get(BLOWER_BITMAP));
			t.price = 20;
			t.type = BLOWER;
			break;
		case NONE:
			t.s = new Sprite(BitmapCache.get(LOCK_BITMAP));
			t.price = -1;
			t.type = NONE;
		}
		return t;
	}

	public static class Tool {
		public Sprite s = null;
		public int price = 0;
		public int type = -1;
		public List<Candy> mCandyList = new ArrayList<Candy>();

		public void get(Candy c) {
			if (mCandyList.contains(c)) {
				mCandyList.remove(c);
			}
			mCandyList.add(c);

			if (type == SHEILD) {
				c.setDirection(c.getDirection() + 90);
			}
			if (type == BLOWER) {
				c.setDirection(c.getDirection() + 45);
			}
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
			if (type == SHEILD) {
				int xdiff = c.getCenterX() - gridBound.centerX();
				int ydiff = c.getCenterY() - gridBound.centerY();
				int dist2 = xdiff * xdiff + ydiff * ydiff;
				if (dist2 <= gridBound.width() * gridBound.width() / 9)
					return true;
			}
			if (type == BLOWER) {
				int xdiff = c.getCenterX() - gridBound.centerX();
				int ydiff = c.getCenterY() - gridBound.centerY();
				int dist2 = xdiff * xdiff + ydiff * ydiff;
				if (dist2 <= gridBound.width() * gridBound.width() / 9)
					return true;
			}
			return false;
		}
	}
}
