package com.Sct.gamebone.layers;

import android.graphics.Point;

import com.Sct.gamebone.CandyScene;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.Sprite;

public class StatusLayer extends BaseLayer {
	public Point mCurrentToolPos = new Point(85 + 10, 1660 + 5);
	public Sprite mCurrentToolSprite = null;
	public CandyScene mScene = null;

	public StatusLayer(CandyScene scene) {
		mScene = scene;
	}

	public void updateCurrentTool(Sprite s) {
		if (mCurrentToolSprite == null) {
			mCurrentToolSprite = s;
			mCurrentToolSprite.anchorX = 0.5f;
			mCurrentToolSprite.anchorY = 0.5f;
			mCurrentToolSprite.x = mCurrentToolPos.x;
			mCurrentToolSprite.y = mCurrentToolPos.y;
			addChild(mCurrentToolSprite);
		} else {
			mCurrentToolSprite.setBitmap(s);
		}
	}
}
