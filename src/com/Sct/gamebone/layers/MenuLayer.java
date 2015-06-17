package com.Sct.gamebone.layers;

import android.util.Log;

import com.Sct.gamebone.CandyScene;
import com.Sct.gamebone.ToolFactory;
import com.Sct.gamebone.view.BaseLayer;
import com.Sct.gamebone.view.BaseLayer.onTouchListener;
import com.Sct.gamebone.view.Sprite;

public class MenuLayer extends BaseLayer implements onTouchListener {
	public Sprite mOkBtn = null;
	public Sprite mRemoveBtn = null;
	public Sprite mMoveBtn = null;
	public CandyScene mScene = null;
	public Sprite mToolIcon = null;
	public Sprite mRotateBtn = null;

	public static final int OK_BUTTON = 0;
	public static final int MOVE_BUTTON = 1;
	public static final int REMOVE_BUTTON = 2;
	public static final int ROTATE_BUTTON = 3;
	public static final int TOOL_ICON = 4;

	public boolean mIsMoving = false;

	public MenuLayer(CandyScene scene) {
		mScene = scene;
		width = 256;
		height = 128 + 64;
		anchorX = 0.25f;
		anchorY = 0.33f;

		mOkBtn = new Sprite("btn_ok");
		mRemoveBtn = new Sprite("btn_remove");
		mMoveBtn = new Sprite("btn_move");
		mRotateBtn = new Sprite("btn_rotate");
		mToolIcon = ToolFactory.getTool(mScene.mSelectedToolType).getIcon();

		mOkBtn.x = 0;
		mOkBtn.y = height * 2 / 3;
		mOkBtn.anchorY = 1.0f;
		// mOkBtn.width = 64;
		// mOkBtn.height = 64;

		mRotateBtn.x = 0;
		mRotateBtn.y = height * 2 / 3;
		mRotateBtn.anchorY = 1.0f;

		mRemoveBtn.x = width;
		mRemoveBtn.y = height * 2 / 3;
		mRemoveBtn.anchorX = 1.0f;
		mRemoveBtn.anchorY = 1.0f;
		// mRemoveBtn.width = 64;
		// mRemoveBtn.height = 64;

		mMoveBtn.x = width / 2;
		mMoveBtn.y = 0;
		mMoveBtn.anchorX = 0.5f;
		mMoveBtn.anchorY = 0f;
		// mMoveBtn.width = 64;
		// mMoveBtn.height = 64;

		mToolIcon.x = width / 2;
		mToolIcon.y = height;
		mToolIcon.anchorX = 0.5f;
		mToolIcon.anchorY = 1f;

		addChild(mToolIcon);
		addChild(mOkBtn);
		addChild(mMoveBtn);
		addChild(mRemoveBtn);
		addChild(mRotateBtn);

		setOnTouchListener(this);
		isSwallow = true;
		hide();
	}

	public void hide() {
		isVisible = false;
		setOnTouchListener(null);
	}

	@Override
	public void enter() {
		// TODO Auto-generated method stub
		super.enter();
		isVisible = false;
	}

	public void show() {
		setOnTouchListener(this);
		isVisible = true;
	}

	public void setup(int mask) {
		mOkBtn.isVisible = (mask >> OK_BUTTON & 1) == 1;
		mRemoveBtn.isVisible = (mask >> REMOVE_BUTTON & 1) == 1;
		mMoveBtn.isVisible = (mask >> MOVE_BUTTON & 1) == 1;
		mToolIcon.isVisible = (mask >> TOOL_ICON & 1) == 1;
		mRotateBtn.isVisible = (mask >> ROTATE_BUTTON & 1) == 1;
	}

	public void setToolSprite(Sprite s) {
		mToolIcon.setBitmap(s);
		mToolIcon.setRotation(s.getRotation());
	}

	@Override
	public boolean doTouch(int x, int y) {
		// TODO Auto-generated method stub
		// if (new Rect(0, 64, 64, 128).contains(x, y) && mOkBtn.isVisible) {
		if (mOkBtn.getDestBound().contains(x, y) && mOkBtn.isVisible) {
			// do ok things
			Log.d("MenuLayer", "do ok things");
			mScene.placeTool(mIsMoving);
			if (mIsMoving) {
				mIsMoving = false;
			}
			hide();
			return true;
		}
		// if (new Rect(256 - 64, 64, 256, 128).contains(x, y)
		if (mRemoveBtn.getDestBound().contains(x, y) && mRemoveBtn.isVisible) {
			// do remove things
			Log.d("MenuLayer", "do remove things");
			mScene.removeTool();
			hide();
			return true;
		}
		// if (new Rect(128 - 32, 0, 128 + 32, 64).contains(x, y)
		if (mMoveBtn.getDestBound().contains(x, y) && mMoveBtn.isVisible) {
			// do move things
			Log.d("MenuLayer", "do move things");
			mScene.removeTool();
			setup(1 << OK_BUTTON | 1 << TOOL_ICON);
			mIsMoving = true;
			return true;
		}
		if (mRotateBtn.getDestBound().contains(x, y) && mRotateBtn.isVisible) {
			Log.d("MenuLayer", "do rotate things");
			mScene.rotateTool();
			return true;
		}
		return false;
	}
}
