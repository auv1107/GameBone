package com.Sct.gamebone;

import com.Sct.gamebone.library.BitmapCache;
import com.Sct.gamebone.tools.BaseTool;
import com.Sct.gamebone.tools.BlueMirror;
import com.Sct.gamebone.tools.DirectionLeader;
import com.Sct.gamebone.tools.RedMirror;
import com.Sct.gamebone.tools.Transporter;
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
	public static final String DIRECTION_LEADER_BITMAP = "tool2state";
	public static final String TRANSPORTER_BITMAP = "new_transporter1";
	public static final String LOCK_BITMAP = "game_lock";
	public static final String BLUE_MIRROR_ICON = "blue_mirror";
	public static final String RED_MIRROR_ICON = "red_mirror";
	public static final String DIRECTION_LEADER_ICON = "tool2state3";
	public static final String TRANSPORTER_ICON = "tool3icon";

	public static int[] price = new int[] { 20, 30, 30, 20, 40, 50 };

	public static int getPrice(int type) {
		if (type == -1)
			return 0;
		return price[type];
	}

	public static BaseTool getTool(int type) {
		BaseTool t = new BaseTool();
		switch (type) {
		case SHEILD:
			t.icon = new Sprite(BitmapCache.get(SHEILD_BITMAP));
			t.icon.anchorX = 0.5f;
			t.icon.anchorY = 1.0f;
			t.price = 15;
			t.type = SHEILD;
			break;
		case BLOWER:
			t.icon = new Sprite(BitmapCache.get(BLOWER_BITMAP));
			t.icon.anchorX = 0.5f;
			t.icon.anchorY = 1.0f;
			t.price = 20;
			t.type = BLOWER;
			break;
		case BLUE_MIRROR:
			t = new BlueMirror();
			break;
		case RED_MIRROR:
			t = new RedMirror();
			break;
		case DIRECTION_LEADER:
			t = new DirectionLeader();
			break;
		case TRANSPORTER:
			t = new Transporter();
			break;
		case NONE:
			t.icon = new Sprite(BitmapCache.get(LOCK_BITMAP));
			t.icon.anchorX = 0.5f;
			t.icon.anchorY = 1.0f;
			t.price = -1;
			t.type = NONE;
		}
		return t;
	}

}
