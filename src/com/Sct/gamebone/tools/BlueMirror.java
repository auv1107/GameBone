package com.Sct.gamebone.tools;

import com.Sct.gamebone.Candy;
import com.Sct.gamebone.ToolFactory;
import com.Sct.gamebone.library.BitmapCache;
import com.Sct.gamebone.view.Sprite;

public class BlueMirror extends BaseTool {
	protected int mDirection = 45;

	public BlueMirror() {
		s = new Sprite(BitmapCache.get(ToolFactory.BLUE_MIRROR_BITMAP));
		price = ToolFactory.getPrice(ToolFactory.BLUE_MIRROR);
		type = ToolFactory.BLUE_MIRROR;
		icon = new Sprite(ToolFactory.BLUE_MIRROR_ICON);
		icon.anchorX = 0.5f;
		icon.anchorY = 1.0f;
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
			mDirection = 135;
			s.setRotation(-90);
		} else {
			state = 0;
			mDirection = 45;
			s.setRotation(0);
		}
	}

}
