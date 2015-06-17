package com.Sct.gamebone;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.Sct.gamebone.StageData.MapInfo;
import com.Sct.gamebone.framework.BaseGameEngine;
import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.layers.GameLayer;
import com.Sct.gamebone.layers.MenuLayer;
import com.Sct.gamebone.layers.ResultLayer;
import com.Sct.gamebone.layers.StatusLayer;
import com.Sct.gamebone.layers.TeachLayer;
import com.Sct.gamebone.library.SoundCache;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.Sprite;

public class CandyScene extends BaseGameEngine {
	public MapInfo info = null;
	public int mCurrentLevel = 0;

	public int heart_num = 0;
	public int coin_num = 0;
	public int cost_coin = 0;

	public int mSelectedToolIndex = 0;
	public int mSelectedToolType = -1;

	public GameLayer mGameLayer = null;
	public MenuLayer mMenuLayer = null;
	public ResultLayer mResultLayer = null;
	public StatusLayer mStatusLayer = null;

	public int state = 0;

	public boolean mIsMenuShown = false;

	@Override
	public void initGame() {
		// TODO Auto-generated method stub
		super.initGame();
		// 获取本关游戏数据
		mCurrentLevel = StageData.getInstance().getCurrentLevel();
		state = StageData.getInstance().getStageList().get(mCurrentLevel).state;

		info = StageData.getInstance().getMapInfo();
		heart_num = StageData.getInstance().heart_num;
		coin_num = info.sum_coin;
		mSelectedToolType = info.tools_list[0];

		BaseLayer l = new BaseLayer();
		l.addChild(new Sprite("game_bg_with_component2"));
		this.addChild(l);

		mGameLayer = new GameLayer(this);
		mGameLayer.x = 28;
		mGameLayer.y = 210;
		addChild(mGameLayer);

		mStatusLayer = new StatusLayer(this);
		mStatusLayer.initLayer();
		addChild(mStatusLayer);

		showGrid();

		mMenuLayer = new MenuLayer(this);
		mMenuLayer.isVisible = false;
		addChild(mMenuLayer);

		if (mCurrentLevel == 0 && state == StageData.OPENING) {
			TeachLayer tl = new TeachLayer(this);
			tl.initLayer();
			addChild(tl);
		}
	}

	@Override
	public void enter() {
		for (BaseLayer l : mChildrenList) {
			l.enter();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					SoundCache.PlayMusic("downfall30", true);
				}
			}
		}).start();
	}

	@Override
	public void exit() {
		SoundCache.StopMusic("downfall30");
		for (BaseLayer l : mChildrenList) {
			l.exit();
		}
		super.exit();
	}

	private void showGrid() {
		final int row = 12;
		final int col = 8;
		final float p_height = 1536f / row;
		final float p_width = 1024f / col;
		BaseLayer l = new BaseLayer() {
			@Override
			public void onDraw(Canvas canvas) {
				for (int i = 0; i < row; i++) {
					canvas.drawLine(0, i * p_height, 1024, i * p_height,
							GameApp.getApplication().getTempPaint());
				}
				for (int i = 0; i < col; i++) {
					canvas.drawLine(i * p_width, 0, i * p_width, 1536, GameApp
							.getApplication().getTempPaint());
				}
			}
		};
		l.width = 1024;
		l.height = 1536;
		l.x = 28;
		l.y = 210;
		addChild(l);
	}

	@Override
	public void onTouch(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.d("CandyScene", "onTouch");
		super.onTouch(e);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
	}

	public void hideMenu() {
		mMenuLayer.hide();
		mIsMenuShown = false;
	}

	public void showMenu(int x, int y, int mask) {
		mMenuLayer.x = x;
		mMenuLayer.y = y;
		mMenuLayer.setup(mask);
		mMenuLayer.show();
		mIsMenuShown = true;
	}

	public void placeTool(boolean isMoving) {
		mGameLayer.placeTool(mSelectedToolType);
		mStatusLayer.cost(mSelectedToolType, isMoving);
	}

	public void removeTool() {
		mGameLayer.removeTool();
	}

	public void rotateTool() {
		mGameLayer.rotateTool();
	}

	public void pass() {
		Log.d("candyscene", "passed");
		state = StageData.PASSED;
		int star = EvaluationSystem.evaluate(mCurrentLevel, coin_num, 0);
		if (coin_num < 0)
			heart_num--;
		writeDataToPreference();
		mResultLayer = new ResultLayer(this, state, star);
		mResultLayer.initLayer();
		addChild(mResultLayer);
	}

	public void writeDataToPreference() {
		int star = EvaluationSystem.evaluate(mCurrentLevel, cost_coin, 0);
		StageData.getInstance().updateStageInfo(mCurrentLevel, state, star);
		StageData.getInstance().openNextState();
		StageData.getInstance().coin_num += coin_num;
		StageData.getInstance().heart_num = heart_num;
		StageData.getInstance().writeToPreferences();
	}

	public void lose() {

	}

	public void nextStage() {
		reset();
		StageData.getInstance().setCurrentLevel(
				StageData.getInstance().getCurrentLevel() + 1);
		initGame();
		enter();
	}

	public void replay() {
		reset();
		initGame();
		enter();
	}

	@Override
	public void reset() {
		super.reset();

		info = null;
		mCurrentLevel = 0;
		heart_num = 0;
		coin_num = 0;
		cost_coin = 0;

		mSelectedToolIndex = 0;
		mSelectedToolType = -1;

		mStatusLayer = null;
		mGameLayer = null;
		mMenuLayer = null;
		mResultLayer = null;

		mIsMenuShown = false;
		state = StageData.getInstance().getStageList()
				.get(StageData.getInstance().getCurrentLevel()).state;
	}
}