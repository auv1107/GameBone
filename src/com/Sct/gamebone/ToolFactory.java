package com.Sct.gamebone;

import com.Sct.gamebone.library.BitmapCache;
import com.Sct.gamebone.tools.BaseTool;
import com.Sct.gamebone.tools.BlueMirror;
import com.Sct.gamebone.view.Sprite;

public class ToolFactory {
	public static final int NONE = -1;
	public static final int BLUE_MIRROR = 0;
	public static final int RED_MIRROR = 1;
	public static final int DIRECTION_LEADER = 2;
	public static final int TRANSPORTER = 3;
	public static final int SHEILD = 4;
	public static final int BLOWER = 5;
	public static final String SHEILD_BITMAP = "game_tool_sheild";
	public static final String BLOWER_BITMAP = "game_tool_blower";
	public static final String BLUE_MIRROR_BITMAP = "blue_mirror";
	public static final String RED_MIRROR_BITMAP = "red_mirror";
	public static final String DIRECTION_LEADER_BITMAP = "direction_leader";
	public static final String TRANSPORTER_BITMAP = "transporter";
	public static final String LOCK_BITMAP = "game_lock";

	public static int[] price = new int[] { 20, 30, 30, 20, 40, 50 };

	public static int getPrice(int type) {
		return price[type];
	}

	public static BaseTool getTool(int type) {
		BaseTool t = new BaseTool();
		switch (type) {
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
		case BLUE_MIRROR:
			t = new BlueMirror();
			break;
		case RED_MIRROR:
			t.s = new Sprite(BitmapCache.get(RED_MIRROR_BITMAP));
			t.price = 20;
			t.type = RED_MIRROR;
			break;
		case DIRECTION_LEADER:
			t.s = new Sprite(BitmapCache.get(DIRECTION_LEADER_BITMAP));
			t.price = 20;
			t.type = DIRECTION_LEADER;
			break;
		case TRANSPORTER:
			t.s = new Sprite(BitmapCache.get(TRANSPORTER_BITMAP));
			t.price = 20;
			t.type = TRANSPORTER;
			break;
		case NONE:
			t.s = new Sprite(BitmapCache.get(LOCK_BITMAP));
			t.price = -1;
			t.type = NONE;
		}
		return t;
	}

}
