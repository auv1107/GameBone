package com.Sct.gamebone.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.Sct.gamebone.R;
import com.Sct.gamebone.view.StageView;

public class MenuActivity extends BaseActivity implements OnClickListener {
	private List<StageInfo> mStageList = new ArrayList<StageInfo>();
	private int[] mStageWordList = new int[] { R.drawable.stage1,
			R.drawable.stage2, R.drawable.stage3 };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView
		setContentView(R.layout.activity_menu);

		mStageList.add(new StageInfo(0, 1, 0));
		mStageList.add(new StageInfo(1, 0, 3));
		mStageList.add(new StageInfo(2, 2, 1));
		mStageList.add(new StageInfo(0, 1, 0));
		mStageList.add(new StageInfo(1, 0, 3));
		mStageList.add(new StageInfo(2, 2, 1));
		mStageList.add(new StageInfo(0, 1, 0));
		mStageList.add(new StageInfo(1, 0, 3));
		mStageList.add(new StageInfo(2, 2, 1));
		// mStageList.add(new StageInfo(4, 2, 2));

		ListView lv = (ListView) findViewById(R.id.stage_list);
		lv.setAdapter(new MenuAdapter(this));

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
		private LayoutInflater mInflater = null;

		public MenuAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mStageList == null ? 0 : mStageList.size();
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
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.stage_item, null);
				holder = new ViewHolder();
				holder.menu_stage = (ImageView) convertView
						.findViewById(R.id.menu_stage);
				holder.menu_state = (LinearLayout) convertView
						.findViewById(R.id.menu_state);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			StageInfo info = mStageList.get(position);
			convertView
					.setBackground(getResources()
							.getDrawable(
									info.state == StageView.PASSED ? StageView.PASSED_BACKGROUND_DRAWABLE
											: StageView.LOCKED_BACKGROUND_DRAWABLE));
			holder.menu_stage.setImageResource(mStageWordList[info.level]);

			holder.menu_state.removeAllViews();
			ImageView v = null;
			switch (info.state) {
			case StageView.PASSED:
				for (int i = 0; i < info.star; i++) {
					v = new ImageView(MenuActivity.this);
					v.setImageResource(R.drawable.star);
					holder.menu_state.addView(v);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
					v.setLayoutParams(lp);
				}
				break;
			case StageView.LOCKED:
				v = new ImageView(MenuActivity.this);
				v.setImageResource(R.drawable.lock);
				holder.menu_state.addView(v);
				break;
			case StageView.OPENING:
				v = new ImageView(MenuActivity.this);
				v.setImageResource(R.drawable.enter_btn);
				holder.menu_state.addView(v);
				break;
			}

			return convertView;
		}
	}

	public class ViewHolder {
		public ImageView menu_stage;
		public LinearLayout menu_state;
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
