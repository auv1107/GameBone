package com.Sct.gamebone.layers;

import com.Sct.gamebone.CandyScene;
import com.Sct.gamebone.StageData;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.Sprite;

public class ResultLayer extends BaseLayer {
	public CandyScene mScene = null;
	protected int mState = 0;
	protected int mStar = 0;

	private Sprite mBtnChoice = null;
	private Sprite mBtnClose = null;

	public ResultLayer(CandyScene scene, int state, int star) {
		mScene = scene;
		mState = state;
		mStar = star;
	}

	@Override
	public void initLayer() {
		// TODO Auto-generated method stub
		super.initLayer();
		Sprite bg = new Sprite("go_bg");
		bg.width = width;
		bg.height = height;
		bg.setAlpha(100);
		addChild(bg);

		Sprite board = new Sprite("go_board");
		board.anchorX = 0.5f;
		board.anchorY = 0.5f;
		board.x = width / 2;
		board.y = height / 2;
		addChild(board);

		Sprite head = new Sprite("go_head");
		head.anchorX = 0.5f;
		head.anchorY = 0.5f;
		head.x = width / 2;
		head.y = board.getRealY();
		addChild(head);

		mBtnClose = new Sprite("btn_close");
		mBtnClose.anchorX = 0.5f;
		mBtnClose.anchorY = 0.5f;
		mBtnClose.x = board.getRealX() + board.width;
		mBtnClose.y = board.getRealY();
		addChild(mBtnClose);

		Sprite label = null;
		Sprite star = null;
		if (mState == StageData.PASSED) {
			label = new Sprite("go_success_label");
			mBtnChoice = new Sprite("next_stage");
			star = new Sprite("star");
		} else {
			label = new Sprite("go_fail_label");
			mBtnChoice = new Sprite("replay");
			star = new Sprite();
		}
		label.anchorX = 0.5f;
		mBtnChoice.anchorX = 0.5f;
		star.anchorX = 0.5f;
		label.x = width / 2;
		label.y = head.y + 200;
		star.x = width / 2;
		star.y = head.y + 300;
		mBtnChoice.x = width / 2;
		mBtnChoice.y = head.y + 600;

		addChild(label);
		addChild(mBtnChoice);
		addChild(star);

		setOnTouchListener(new onTouchListener() {

			@Override
			public boolean doTouch(int x, int y) {
				// TODO Auto-generated method stub
				if (mBtnChoice.getDestBound().contains(x, y)) {
					if (mState == StageData.PASSED)
						mScene.nextStage();
					else
						mScene.replay();
				}
				if (mBtnClose.getDestBound().contains(x, y)) {
					mScene.exit();
				}
				return true;
			}
		});
	}

	@Override
	public void enter() {
		// TODO Auto-generated method stub
		super.enter();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		super.exit();
	}
}
