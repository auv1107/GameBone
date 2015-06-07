package com.Sct.gamebone;

import java.util.ArrayList;
import java.util.List;

public class StageData {
	public static final int OPENING = 0;
	public static final int LOCKED = 1;
	public static final int PASSED = 2;

	public static final int PASSED_BACKGROUND_DRAWABLE = R.drawable.blue_btn;
	public static final int LOCKED_BACKGROUND_DRAWABLE = R.drawable.gray_btn;

	private static StageData instance = null;

	public synchronized static StageData getInstance() {
		if (instance == null)
			instance = new StageData();
		return instance;
	}

	private List<StageInfo> mList = new ArrayList<StageInfo>();

	public StageData() {
		mList.add(new StageInfo(0, "关卡一", PASSED, 2));
		mList.add(new StageInfo(1, "关卡二", OPENING, 0));
		mList.add(new StageInfo(2, "关卡三", LOCKED, 0));
		mList.add(new StageInfo(3, "关卡四", LOCKED, 0));
		mList.add(new StageInfo(4, "关卡五", LOCKED, 0));
	}

	public List<StageInfo> getStageList() {
		return mList;
	}

	public static class StageInfo {
		public int level = 0;
		public int star = 0;
		public int state = 0;

		public String name = "";

		public StageInfo(int level, String name, int state, int star) {
			this.level = level;
			this.star = star;
			this.state = state;
			this.name = name;
		}
	}
}
