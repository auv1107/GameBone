package com.Sct.gamebone.tools;

import com.Sct.gamebone.Candy;
import com.Sct.gamebone.ToolFactory;
import com.Sct.gamebone.view.Sprite;

public class DirectionLeader extends BaseTool {
	int mDirection = 45;
	Sprite[] icons = null;

	public DirectionLeader() {
		// TODO Auto-generated constructor stub
		// s = new Sprite(BitmapCache.get(ToolFactory.DIRECTION_LEADER_BITMAP));
		state = 3;
		type = ToolFactory.DIRECTION_LEADER;
		price = ToolFactory.getPrice(ToolFactory.DIRECTION_LEADER);
		icon = new Sprite(ToolFactory.DIRECTION_LEADER_ICON);
		icon.anchorX = 0.5f;
		icon.anchorY = 1.0f;

		icons = new Sprite[4];
		for (int i = 0; i < 4; i++) {
			String image = ToolFactory.DIRECTION_LEADER_BITMAP + i;
			icons[i] = new Sprite(image);
		}
		s = icons[3];
	}

	@Override
	public void get(Candy c) {
		// TODO Auto-generated method stub
		super.get(c);
		int d = c.getDirection();
		if (state == 0) {
			c.setDirection(d + 135);
		}
		if (state == 1) {
			c.setDirection(d + 45);
		}
		if (state == 2) {
			c.setDirection(d - 45);
		}
		if (state == 3) {
			c.setDirection(d - 135);
		}
	}

	@Override
	public void lose(Candy c) {
		// TODO Auto-generated method stub
		super.lose(c);
	}

	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		super.rotate();
		state = (state + 1) % 4;
		s = icons[state];
	}
}
