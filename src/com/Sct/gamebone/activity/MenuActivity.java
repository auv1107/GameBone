package com.Sct.gamebone.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.Sct.gamebone.R;
import com.Sct.gamebone.view.StageView;

public class MenuActivity extends BaseActivity implements OnClickListener {
	private List<StageInfo> mStageList = new ArrayList<StageInfo>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView
		setContentView(R.layout.activity_menu);

		mStageList.add(new StageInfo(1, 1, 0));
		mStageList.add(new StageInfo(2, 3, 0));
		mStageList.add(new StageInfo(3, 2, 1));
		mStageList.add(new StageInfo(4, 2, 2));
		
		ListView lv = (ListView)findViewById(R.id.stage_list);
		lv.setAdapter(new MenuAdapter());
		
		/* start of USELESS codes */
		// View v = new View(this);
		// v.setId(0);
		// onClick(v);
		/* end of USELESS codes */
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		// Rewrite here
		case 0:
			intent.setClass(this, GameActivity.class);
			break;
		case 1:
			intent.setClass(this, SetupActivity.class);
			break;
		case 2:
			intent.setClass(this, HelpActivity.class);
		case 3:
			killApp();
			break;
		}

		startActivity(intent);
	}

	public class MenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			StageInfo info = mStageList.get(arg0);
			if (arg1 == null) {
				arg1 = StageView.getView(MenuActivity.this, info.level,
						info.state, info.star);
			}
			return arg1;
		}
	}

	public class StageInfo {
		public int level = 0;
		public int star = 0;
		public int state = 0;

		public StageInfo(int level, int state, int star) {
			this.level = level;
			this.star = star;
			this.state = state;
		}
	}
}
