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
	}

	@Override
	public void get(Candy c) {
		// TODO Auto-generated method stub
		super.get(c);
		if (state == 0) {
			if ((c.getDirection() + 360) % 360 > 360 - 80
					|| (c.getDirection() + 360) % 360 < 80) {
				c.setY(c.getY() + 256);
			}
		}
		if (state == 1) {
			if ((c.getDirection() + 360) % 360 > 100
					|| (c.getDirection() + 360) % 360 < 260) {
				c.setY(c.getY() - 256);
			}
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
		if (state == 0) {
			s.setRotation(180);
			s.y -= 256;
			state = 1;
		} else {
			s.setRotation(0);
			s.y += 256;
			state = 0;
		}
	}

}
