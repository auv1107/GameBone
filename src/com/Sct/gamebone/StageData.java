package com.Sct.gamebone;

import java.util.ArrayList;
import java.util.List;

import com.Sct.gamebone.framework.NGameData;
import com.Sct.gamebone.framework.NGameData.TileObject;

public class StageData {
	public static final int OPENING = 0;
	public static final int LOCKED = 1;
	public static final int PASSED = 2;

	public static final int PASSED_BACKGROUND_DRAWABLE = R.drawable.blue_btn;
	public static final int LOCKED_BACKGROUND_DRAWABLE = R.drawable.gray_btn;

	private static StageData instance = null;

	private int mCurrentLevel = 0;

	public synchronized static StageData getInstance() {
		if (instance == null)
			instance = new StageData();
		return instance;
	}

	private List<StageInfo> mList = new ArrayList<StageInfo>();

	public StageData() {
		mList.add(new StageInfo(0, "关卡一", PASSED, 3, ""));
		mList.add(new StageInfo(1, "关卡二", PASSED, 3, ""));
		mList.add(new StageInfo(2, "关卡三", PASSED, 3, ""));
		mList.add(new StageInfo(3, "关卡四", PASSED, 3, ""));
		mList.add(new StageInfo(4, "关卡五", OPENING, 3, NGameData.NMAP1));
	}

	public List<StageInfo> getStageList() {
		return mList;
	}

	public void setCurrentLevel(int l) {
		if (l >= mList.size())
			mCurrentLevel = 0;
		else
			mCurrentLevel = l;
	}

	public MapInfo getMapInfo() {
		return new MapInfo(mList.get(mCurrentLevel).map_name);
	}

	public String getStageName() {
		return mList.get(mCurrentLevel).name;
	}

	public int getCurrentLevel() {
		return mCurrentLevel;
	}

	public static class StageInfo {
		public int level = 0;
		public int star = 0;
		public int state = 0;

		public String name = "";
		public String map_name = "";

		public StageInfo(int level, String name, int state, int star,
				String map_name) {
			this.level = level;
			this.star = star;
			this.state = state;
			this.name = name;
			this.map_name = map_name;
		}
	}

	public static class MapInfo {
		public NGameData data = null;
		public int[] tools_list = new int[4];
		public List<Integer> bg_layer = null;
		public List<Integer> obstacle_layer = null;
		public int row = 0;
		public int col = 0;
		public int entry_pos = 0;
		public int exit_pos = 0;

		public MapInfo(String mapname) {
			data = NGameData.readFromFile(mapname);
			if (data != null) {
				init();
			}
		}

		private void init() {
			TileObject o = data.getTileObject("tool_list");
			tools_list[0] = Integer.parseInt(o.properties.get("t1"));
			tools_list[1] = Integer.parseInt(o.properties.get("t2"));
			tools_list[2] = Integer.parseInt(o.properties.get("t3"));
			tools_list[3] = Integer.parseInt(o.properties.get("t4"));

			bg_layer = data.getLayer(0);
			obstacle_layer = data.getLayer(1);
			row = data.getHeight();
			col = data.getWidth();

			TileObject o_exit = data.getTileObject("exit");
			exit_pos = Integer.parseInt(o_exit.properties.get("pos"));

			TileObject o_entry = data.getTileObject("entry");
			entry_pos = Integer.parseInt(o_entry.properties.get("pos"));
		}
	}
}
