package com.Sct.gamebone;

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

		public void enter(Candy c) {

		}

		public void exit(Candy c) {

		}

		public boolean contains(Candy c) {
			return false;
		}
	}
}
