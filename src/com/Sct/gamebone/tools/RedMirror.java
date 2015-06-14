package com.Sct.gamebone.tools;

import com.Sct.gamebone.Candy;
import com.Sct.gamebone.ToolFactory;
import com.Sct.gamebone.library.BitmapCache;
import com.Sct.gamebone.view.Sprite;

public class RedMirror extends BaseTool {
	int mDirection = 90;

	public RedMirror() {
		// TODO Auto-generated constructor stub
		s = new Sprite(BitmapCache.get(ToolFactory.RED_MIRROR_BITMAP));
		type = ToolFactory.RED_MIRROR;
		price = ToolFactory.getPrice(ToolFactory.RED_MIRROR);
	}

	@Override
	public void get(Candy c) {
		// TODO Auto-generated method stub
		super.get(c);
		int direction = c.getDirection();
		int outDirection = 2 * mDirection - direction;
		c.setDirection(outDirection);
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
		if (state == 0) {
			state = 1;
			mDirection = 0;
			s.setRotation(-90);
		} else {
			state = 0;
			mDirection = 90;
			s.setRotation(0);
		}
	}

}
