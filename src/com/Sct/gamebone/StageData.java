package com.Sct.gamebone;

import java.util.ArrayList;
import java.util.List;

import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.framework.NGameData;
import com.Sct.gamebone.framework.NGameData.TileObject;

public class StageData {
	public static final int OPENING = 0;
	public static final int LOCKED = 1;
	public static final int PASSED = 2;

	public static final int PASSED_BACKGROUND_DRAWABLE = R.drawable.blue_btn;
	public static final int LOCKED_BACKGROUND_DRAWABLE = R.drawable.gray_btn;

	public static final String NMAP1 = "maps/nmap1.tmx";
	public static final String NMAP2 = "maps/nmap2.json";

	public static final int MAX_HEART = 5;
	public static final int HEART_RECOVERY_TIME = 60 * 60 * 1000;

	private static StageData instance = null;

	private int mCurrentLevel = 0;
	public int heart_num = 0;
	public int coin_num = 0;
	private int last_login_time = 0;

	public synchronized static StageData getInstance() {
		if (instance == null)
			instance = new StageData();
		return instance;
	}

	public int getLoginTimes() {
		return GameApp.getApplication().getPreferenceInt("LoginTimes");
	}

	public void updateStageInfo(int level, int state, int star) {
		mList.get(level).star = star;
		mList.get(level).state = state;
	}

	public void openNextState() {
		if (mCurrentLevel != mList.size() - 1) {
			mList.get(mCurrentLevel + 1).state = OPENING;
		}
	}

	private List<StageInfo> mList = new ArrayList<StageInfo>();
	private int loginTimes = 0;

	public StageData() {
		loginTimes = getLoginTimes();
		// if (loginTimes == 0) {
		initWhenTheFirstTime();
		// }
		readFromPreferences();
		GameApp.getApplication().putPreferenceInt("LoginTimes", ++loginTimes);
	}

	public void initWhenTheFirstTime() {
		mList.add(new StageInfo(0, "关卡一", OPENING, 0, NMAP1));
		mList.add(new StageInfo(1, "关卡二", LOCKED, 0, NMAP2));
		mList.add(new StageInfo(2, "关卡三", LOCKED, 0, NMAP1));
		mList.add(new StageInfo(3, "关卡四", LOCKED, 0, NMAP1));
		mList.add(new StageInfo(4, "关卡五", LOCKED, 0, NMAP1));

		GameApp app = GameApp.getApplication();
		app.putPreferenceInt("total_stage", mList.size());
		for (int i = 0; i < mList.size(); i++) {
			app.putPreferenceInt("stage" + i + "level", i);
			app.putPreference("stage" + i + "name", mList.get(i).name);
			app.putPreferenceInt("stage" + i + "state", mList.get(i).state);
			app.putPreferenceInt("stage" + i + "star", mList.get(i).star);
			app.putPreference("stage" + i + "map_name", mList.get(i).map_name);
		}

		// 剩余心数
		app.putPreferenceInt("heart_num", 5);
		// 剩余star
		app.putPreferenceInt("coin_num", 500);
		// 上次时间
		app.putPreferenceInt("lastLoginTime", 0);
	}

	public void readFromPreferences() {
		GameApp app = GameApp.getApplication();
		int total_stage = app.getPreferenceInt("total_stage");
		mList.clear();
		for (int i = 0; i < total_stage; i++) {
			StageInfo info = new StageInfo();
			info.level = app.getPreferenceInt("stage" + i + "level");
			info.name = app.getPreference("stage" + i + "name");
			info.state = app.getPreferenceInt("stage" + i + "state");
			info.star = app.getPreferenceInt("stage" + i + "star");
			info.map_name = app.getPreference("stage" + i + "map_name");
			mList.add(info);
		}

		// 剩余心数
		heart_num = app.getPreferenceInt("heart_num");
		// 剩余coin
		coin_num = app.getPreferenceInt("coin_num");
		// 上次时间
		last_login_time = app.getPreferenceInt("last_login_time");

		long t = System.currentTimeMillis();
		heart_num += (t - last_login_time) / HEART_RECOVERY_TIME;
		heart_num = heart_num > MAX_HEART ? MAX_HEART : heart_num;
	}

	public void writeToPreferences() {
		GameApp app = GameApp.getApplication();
		app.putPreferenceInt("total_stage", mList.size());
		for (int i = 0; i < mList.size(); i++) {
			app.putPreference("stage" + i + "name", mList.get(i).name);
			app.putPreferenceInt("stage" + i + "state", mList.get(i).state);
			app.putPreferenceInt("stage" + i + "star", mList.get(i).star);
			app.putPreference("stage" + i + "map_name", mList.get(i).map_name);
		}

		// 剩余心数
		app.putPreferenceInt("heart_num", heart_num);
		// 剩余star
		app.putPreferenceInt("coin_num", coin_num);
		// 上次时间
		app.putPreferenceInt("lastLoginTime", (int) System.currentTimeMillis());
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

	public boolean isLastLevel(int l) {
		if (l == mList.size() - 1)
			return true;
		return false;
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

		public StageInfo() {
		}

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
