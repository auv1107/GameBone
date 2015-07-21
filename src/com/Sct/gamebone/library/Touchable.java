package com.Sct.gamebone.library;

import android.graphics.Rect;
import android.view.MotionEvent;

public interface Touchable {
	public Rect getTouchArea();

	public void onTouch(MotionEvent e);

	public boolean isTouchEnabaled();

	public boolean isSwallow();
}
