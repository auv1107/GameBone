package com.Sct.gamebone.layers;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import com.Sct.gamebone.CandyScene;
import com.Sct.gamebone.StageData;
import com.Sct.gamebone.ToolFactory;
import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.library.SoundCache;
import com.Sct.gamebone.tools.BaseTool;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.Sprite;
import com.Sct.gamebone.view.TextSprite;

public class StatusLayer extends BaseLayer {
	public Point[] mToolsPos = new Point[] {
			new Point(208 + 50, 1920 - 57 - 20),
			new Point(392 + 50, 1920 - 57 - 20),
			new Point(569 + 50, 1920 - 57 - 20),
			new Point(740 + 50, 1920 - 57 - 20) };
	public Point[] mToolsPricePos = new Point[] { new Point(272, 1920 - 47),
			new Point(449, 1920 - 47), new Point(647, 1920 - 47),
			new Point(826, 1920 - 47) };

	public Point heart_pos = new Point(240, 124);
	public Point coin_pos = new Point(960, 124);
	public Point stage_name_pos = new Point(GameApp.getApplication()
			.getScreenWidth() / 2, 100);

	public Point mCurrentToolPos = new Point(85 + 10, 1660 + 5);
	public Sprite mCurrentToolSprite = null;
	public CandyScene mScene = null;
	public Sprite mToolBackground = null;

	private TextSprite label_stage = null;
	private TextSprite ts_heart_num = null;
	private TextSprite ts_coin_num = null;

	public StatusLayer(CandyScene scene) {
		mScene = scene;
		mToolBackground = new Sprite("dark_yellow_rect");
		mToolBackground.x = mCurrentToolPos.x;
		mToolBackground.y = mCurrentToolPos.y;
		mToolBackground.anchorX = 0.5f;
		mToolBackground.anchorY = 0.5f;
		addChild(mToolBackground);
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

	@Override
	public void initLayer() {
		for (int i = 0; i < 4; i++) {
			// add tool
			BaseTool t = ToolFactory.getTool(mScene.info.tools_list[i]);
			t.setX(mToolsPos[i].x);
			t.setY(mToolsPos[i].y);
			addChild(t.getIcon());

			// add price
			TextSprite price = new TextSprite(""
					+ (t.price < 0 ? "-" : t.price));
			price.x = mToolsPricePos[i].x;
			price.y = mToolsPricePos[i].y;
			price.setTextSize(25);
			price.setTextColor(Color.WHITE);
			addChild(price);
		}
		updateCurrentTool(ToolFactory.getTool(mScene.mSelectedToolType)
				.getIcon());

		label_stage = new TextSprite(StageData.getInstance().getStageName());
		label_stage.setTextColor(Color.WHITE);
		label_stage.setTextSize(50);
		label_stage.x = stage_name_pos.x;
		label_stage.y = stage_name_pos.y;
		addChild(label_stage);

		ts_heart_num = new TextSprite("" + mScene.heart_num);
		ts_heart_num.x = heart_pos.x;
		ts_heart_num.y = heart_pos.y;
		ts_heart_num.setTextSize(50);
		ts_heart_num.setTextColor(Color.WHITE);
		addChild(ts_heart_num);

		ts_coin_num = new TextSprite("" + mScene.coin_num);
		ts_coin_num.x = coin_pos.x;
		ts_coin_num.y = coin_pos.y;
		ts_coin_num.setTextSize(50);
		ts_coin_num.setTextColor(Color.WHITE);
		addChild(ts_coin_num);

		setOnTouchListener(new onTouchListener() {
			@Override
			public boolean doTouch(int x, int y) {
				// TODO Auto-generated method stub
				SoundCache.PlayAudio("click");
				if (mScene.mMenuLayer.mIsMoving)
					return false;
				Log.d("CandyScene", "onTouch: " + x + "," + y);
				if (y > 1660 && y < mToolsPos[0].y) {
					for (int i = 0; i < 4; i++) {
						if (mScene.info.tools_list[i] == ToolFactory.NONE
								|| mScene.mSelectedToolType == mScene.info.tools_list[i])
							continue;
						if (x > mToolsPos[i].x && x < mToolsPos[i].x + 110) {
							mScene.mSelectedToolType = mScene.info.tools_list[i];
							updateCurrentTool(ToolFactory.getTool(
									mScene.mSelectedToolType).getIcon());
							mScene.mMenuLayer.setToolSprite(ToolFactory
									.getTool(mScene.mSelectedToolType)
									.getIcon());
							return true;
						}
					}
				}
				return false;
			}
		});
	}

	public void cost(int type, boolean isMoving) {
		int price = ToolFactory.getPrice(type);
		int cost = isMoving ? price / 2 : price;
		mScene.coin_num -= cost;
		mScene.cost_coin += cost;
		ts_coin_num.setText("" + mScene.coin_num);
	}
}
