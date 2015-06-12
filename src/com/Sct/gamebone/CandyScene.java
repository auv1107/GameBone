package com.Sct.gamebone;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.Sct.gamebone.StageData.MapInfo;
import com.Sct.gamebone.ToolFactory.Tool;
import com.Sct.gamebone.framework.BaseGameEngine;
import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.layers.GameLayer;
import com.Sct.gamebone.layers.MenuLayer;
import com.Sct.gamebone.layers.ResultLayer;
import com.Sct.gamebone.layers.StatusLayer;
import com.Sct.gamebone.library.SoundCache;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.BaseLayer.onTouchListener;
import com.Sct.gamebone.view.Sprite;
import com.Sct.gamebone.view.TextSprite;

public class CandyScene extends BaseGameEngine {
	public MapInfo info = null;
	public Point[] mToolsPos = new Point[] { new Point(208, 1920 - 57 - 20),
			new Point(392, 1920 - 57 - 20), new Point(569, 1920 - 57 - 20),
			new Point(740, 1920 - 57 - 20) };
	public Point[] mToolsPricePos = new Point[] { new Point(272, 1920 - 47),
			new Point(449, 1920 - 47), new Point(647, 1920 - 47),
			new Point(826, 1920 - 47) };
	public Point heart_pos = new Point(240, 124);
	public Point star_pos = new Point(960, 124);
	public Point stage_name_pos = new Point(GameApp.getApplication()
			.getScreenWidth() / 2, 100);
	public int heart_num = 0;
	public int star_num = 0;

	public int mSelectedToolIndex = 0;
	public int mSelectedToolType = -1;

	private TextSprite label_stage = null;
	private TextSprite ts_heart_num = null;
	private TextSprite ts_star_num = null;
	private StatusLayer mStatusLayer = null;
	private GameLayer mGameLayer = null;
	private MenuLayer mMenuLayer = null;
	private ResultLayer mResultLayer = null;

	public boolean mIsMenuShown = false;

	@Override
	public void initGame() {
		// TODO Auto-generated method stub
		super.initGame();
		// 获取本关游戏数据
		info = StageData.getInstance().getMapInfo();
		heart_num = GameApp.getApplication().getPreferenceInt("heart_num");
		star_num = GameApp.getApplication().getPreferenceInt("star_num");
		mSelectedToolType = info.tools_list[0];

		BaseLayer l = new BaseLayer();
		l.addChild(new Sprite("game_bg_with_component2"));
		this.addChild(l);

		mGameLayer = new GameLayer(this);
		mGameLayer.x = 28;
		mGameLayer.y = 210;
		this.addChild(mGameLayer);

		mStatusLayer = new StatusLayer(this);
		for (int i = 0; i < 4; i++) {
			// add tool
			Tool t = ToolFactory.getTool(info.tools_list[i]);
			t.s.anchorX = 0f;
			t.s.anchorY = 1f;
			t.s.x = mToolsPos[i].x;
			t.s.y = mToolsPos[i].y;
			mStatusLayer.addChild(t.s);

			// add price
			TextSprite price = new TextSprite(""
					+ (t.price < 0 ? "-" : t.price));
			price.x = mToolsPricePos[i].x;
			price.y = mToolsPricePos[i].y;
			price.setTextSize(25);
			price.setTextColor(Color.WHITE);
			mStatusLayer.addChild(price);
		}
		mStatusLayer
				.updateCurrentTool(ToolFactory.getTool(mSelectedToolType).s);

		label_stage = new TextSprite(StageData.getInstance().getStageName());
		label_stage.setTextColor(Color.WHITE);
		label_stage.setTextSize(50);
		label_stage.x = stage_name_pos.x;
		label_stage.y = stage_name_pos.y;
		mStatusLayer.addChild(label_stage);

		ts_heart_num = new TextSprite("" + heart_num);
		ts_heart_num.x = heart_pos.x;
		ts_heart_num.y = heart_pos.y;
		ts_heart_num.setTextSize(50);
		ts_heart_num.setTextColor(Color.WHITE);
		mStatusLayer.addChild(ts_heart_num);

		ts_star_num = new TextSprite("" + star_num);
		ts_star_num.x = star_pos.x;
		ts_star_num.y = star_pos.y;
		ts_star_num.setTextSize(50);
		ts_star_num.setTextColor(Color.WHITE);
		mStatusLayer.addChild(ts_star_num);

		this.addChild(mStatusLayer);

		mStatusLayer.setOnTouchListener(new onTouchListener() {
			@Override
			public boolean doTouch(int x, int y) {
				// TODO Auto-generated method stub
				SoundCache.PlayAudio("click");
				if (CandyScene.this.mMenuLayer.mIsMoving)
					return false;
				Log.d("CandyScene", "onTouch: " + x + "," + y);
				if (y > 1660 && y < mToolsPos[0].y) {
					for (int i = 0; i < 4; i++) {
						if (info.tools_list[i] == ToolFactory.NONE
								|| mSelectedToolType == info.tools_list[i])
							continue;
						if (x > mToolsPos[i].x && x < mToolsPos[i].x + 110) {
							mSelectedToolType = info.tools_list[i];
							mStatusLayer.updateCurrentTool(ToolFactory
									.getTool(mSelectedToolType).s);
							mMenuLayer.setToolSprite(ToolFactory
									.getTool(mSelectedToolType).s);
							return true;
						}
					}
				}
				return false;
			}
		});

		showGrid();

		mMenuLayer = new MenuLayer(this);
		addChild(mMenuLayer);
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

	public void placeTool() {
		mGameLayer.placeTool(mSelectedToolType);
	}

	public void removeTool() {
		mGameLayer.removeTool();
	}

	public void pass() {
		Log.d("candyscene", "passed");
		mResultLayer = new ResultLayer(this, StageData.PASSED, 3);
		mResultLayer.initLayer();
		addChild(mResultLayer);
	}

	public void lose() {

	}

	public void nextStage() {
		reset();
		StageData.getInstance().setCurrentLevel(
				StageData.getInstance().getCurrentLevel() + 0);
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

		heart_num = 0;
		star_num = 0;

		mSelectedToolIndex = 0;
		mSelectedToolType = -1;

		label_stage = null;
		ts_heart_num = null;
		ts_star_num = null;
		mStatusLayer = null;
		mGameLayer = null;
		mMenuLayer = null;
		mResultLayer = null;

		mIsMenuShown = false;
	}
}