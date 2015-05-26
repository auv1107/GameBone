package com.Sct.gamebone;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;

public class TouchDispatchCenter {
	private static TouchDispatchCenter instance = null;

	private List<Touchable> list = new ArrayList<Touchable>();
	private boolean mTouchEnabled = true;

	public static synchronized TouchDispatchCenter getInstance() {
		if (instance == null)
			instance = new TouchDispatchCenter();
		return instance;
	}

	public void setTouchEnabled(boolean enabled) {
		mTouchEnabled = enabled;
	}

	public boolean getTouchEnabled() {
		return mTouchEnabled;
	}

	public void registerTouchEvent(Touchable t) {
		if (list.contains(t)) {
			list.remove(t);
		}
		list.add(t);
	}

	public void unregisterTouchEvent(Touchable t) {
		if (list.contains(t)) {
			list.remove(t);
		}
	}

	public void onTouch(MotionEvent e) {
		for (Touchable t : list) {
			if (t.isTouchEnabaled()
					&& t.getTouchArea()
							.contains((int) e.getX(), (int) e.getY())) {
				t.onTouch(e);
				if (t.isSwallow())
					break;
			}
		}
	}
}
