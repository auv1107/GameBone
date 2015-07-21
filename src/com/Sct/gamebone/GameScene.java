package com.Sct.gamebone;

import com.Sct.gamebone.framework.BaseGameEngine;
import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.Sprite;

public class GameScene extends BaseGameEngine {

	@Override
	public void enter() {
		super.enter();
	}

	@Override
	public void initGame() {
		super.initGame();
		BaseLayer bgLayer = new BaseLayer();
		bgLayer.addChild(new Sprite("loading"));
		bgLayer.x = GameApp.getApplication().getScreenWidth() / 2;
		bgLayer.y = GameApp.getApplication().getScreenHeight() / 2;

		addChild(bgLayer);
	}

}
