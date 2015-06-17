package com.Sct.gamebone.tools;

import com.Sct.gamebone.Candy;
import com.Sct.gamebone.ToolFactory;
import com.Sct.gamebone.view.Sprite;

public class Transporter extends BaseTool {
	protected int mDirection = 0;

	public Transporter() {
		s = new Sprite(ToolFactory.TRANSPORTER_BITMAP);
		type = ToolFactory.TRANSPORTER;
		price = ToolFactory.getPrice(ToolFactory.TRANSPORTER);
		icon = new Sprite(ToolFactory.TRANSPORTER_ICON);
		icon.anchorX = 0.5f;
		icon.anchorY = 1.0f;
		icon.width = 128;
		icon.height = 128;
	}

	@Override
	public void get(Candy c) {
		// TODO Auto-generated method stub
		super.get(c);
		if (state == 0) {
			c.setY(c.getY() + 256);
		}
		if (state == 1) {
			c.setX(c.getX() - 256);
		}
		if (state == 2) {
			c.setY(c.getY() - 256);
		}
		if (state == 3) {
			c.setX(c.getX() + 256);
		}
		c.setDirection((state * 90 + 90) % 360);
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
		s.setRotation(state * 90);
		if (state == 1) {
			x -= 128;
			y -= 128;
		}
		if (state == 2) {
			x += 128;
			y -= 128;
		}
		if (state == 3) {
			x += 128;
			y += 128;
		}
		if (state == 0) {
			x -= 128;
			y += 128;
		}
	}

}
