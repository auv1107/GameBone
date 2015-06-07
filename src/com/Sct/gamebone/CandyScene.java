package com.Sct.gamebone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.Sct.gamebone.framework.BaseGameEngine;
import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.Sprite;
import com.Sct.gamebone.view.TextSprite;

public class CandyScene extends BaseGameEngine {

	@Override
	public void initGame() {
		// TODO Auto-generated method stub
		super.initGame();
		BaseLayer l = new BaseLayer();
		l.addChild(new Sprite("game_bg_with_component"));
		this.addChild(l);

		BaseLayer l1 = new BaseLayer();
		Sprite tool_blower = new Sprite("game_tool_blower");
		tool_blower.x = 208;
		tool_blower.y = 1920 - 228;
		l1.addChild(tool_blower);

		Sprite tool_sheild = new Sprite("game_tool_sheild");
		tool_sheild.x = 392;
		tool_sheild.y = 1920 - 228 + 8 + 4;
		l1.addChild(tool_sheild);

		Sprite tool_lock1 = new Sprite("game_lock");
		tool_lock1.x = 585 - 16;
		tool_lock1.y = 1920 - 228 + 18;
		l1.addChild(tool_lock1);

		Sprite tool_lock2 = new Sprite("game_lock");
		tool_lock2.x = 752 - 8 - 4;
		tool_lock2.y = 1920 - 228 + 18;
		l1.addChild(tool_lock2);

		Sprite label_stage = new Sprite("stage1");
		label_stage.x = (GameApp.getApplication().getScreenWidth() - label_stage.width) / 2;
		label_stage.y = 53;
		l1.addChild(label_stage);

		TextSprite heart_num = new TextSprite("" + 10);
		heart_num.x = 240;
		heart_num.y = 106 + 18;
		heart_num.setTextSize(50);
		heart_num.setTextColor(Color.WHITE);
		l1.addChild(heart_num);

		TextSprite star_num = new TextSprite("" + 123458);
		star_num.x = 960;
		star_num.y = 106 + 18;
		star_num.setTextSize(50);
		star_num.setTextColor(Color.WHITE);
		l1.addChild(star_num);

		TextSprite price1 = new TextSprite("" + 12);
		price1.x = 282 - 10;
		price1.y = 1920 - 50 + 3;
		price1.setTextSize(25);
		price1.setTextColor(Color.WHITE);
		l1.addChild(price1);

		TextSprite price2 = new TextSprite("" + 12);
		price2.x = 466 - 20 + 3;
		price2.y = 1920 - 50 + 3;
		price2.setTextSize(25);
		price2.setTextColor(Color.WHITE);
		l1.addChild(price2);

		TextSprite price3 = new TextSprite("-");
		price3.x = 659 - 15 + 3;
		price3.y = 1920 - 50 + 3;
		price3.setTextSize(25);
		price3.setTextColor(Color.WHITE);
		l1.addChild(price3);

		TextSprite price4 = new TextSprite("-");
		price4.x = 826;
		price4.y = 1920 - 50 + 3;
		price4.setTextSize(25);
		price4.setTextColor(Color.WHITE);
		l1.addChild(price4);

		this.addChild(l1);

		showGrid();
	}

	private void showGrid() {
		final int row = 12;
		final int col = 8;
		final float p_height = 1358f / row;
		final float p_width = 1030f / col;
		BaseLayer l = new BaseLayer() {
			@Override
			public void onDraw(Canvas canvas) {
				for (int i = 0; i < row; i++) {
					canvas.drawLine(0, i * p_height, 1030, i * p_height,
							GameApp.getApplication().getTempPaint());
				}
				for (int i = 0; i < col; i++) {
					canvas.drawLine(i * p_width, 0, i * p_width, 1358, GameApp
							.getApplication().getTempPaint());
				}
			}
		};
		l.width = 1030;
		l.height = 1358;
		l.x = 18;
		l.y = 220;
		addChild(l);
	}

	@Override
	public void onTouch(MotionEvent e) {
		// TODO Auto-generated method stub
		super.onTouch(e);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
	}

}