package com.Sct.gamebone.tools;

import com.Sct.gamebone.Candy;
import com.Sct.gamebone.ToolFactory;
import com.Sct.gamebone.library.BitmapCache;
import com.Sct.gamebone.view.Sprite;

public class DirectionLeader extends BaseTool {
	int mDirection = 0;

	public DirectionLeader() {
		// TODO Auto-generated constructor stub
		s = new Sprite(BitmapCache.get(ToolFactory.DIRECTION_LEADER_BITMAP));
		type = ToolFactory.DIRECTION_LEADER;
		price = ToolFactory.getPrice(ToolFactory.DIRECTION_LEADER);
	}

	@Override
	public void get(Candy c) {
		// TODO Auto-generated method stub
		super.get(c);
		c.setDirection(mDirection);
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
		mDirection = state * 90;
		s.setRotation(mDirection);
	}
}
