package com.Sct.gamebone.activity;

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
import android.widget.TextView;

import com.Sct.gamebone.R;
import com.Sct.gamebone.StageData;
import com.Sct.gamebone.StageData.StageInfo;
import com.Sct.gamebone.library.BitmapCache;
import com.Sct.gamebone.library.SoundCache;

public class MenuActivity extends BaseActivity implements OnClickListener {
	private List<StageInfo> mStageList = null;
	private ListView lv = null;
	private MenuAdapter mAdapter = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView
		setContentView(R.layout.activity_menu);
		mStageList = StageData.getInstance().getStageList();

		lv = (ListView) findViewById(R.id.stage_list);
		mAdapter = new MenuAdapter(this);
		lv.setAdapter(mAdapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SoundCache.PlayMusic("menu", true);
		mStageList = StageData.getInstance().getStageList();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		// Rewrite here
		case -1:
			intent.setClass(this, SetupActivity.class);
			break;
		case -2:
			intent.setClass(this, HelpActivity.class);
		case -3:
			killApp();
			break;
		default:
			StageData.getInstance().setCurrentLevel(v.getId());
			intent.setClass(this, GameActivity.class);
			break;
		}

		SoundCache.StopMusic("menu");
		SoundCache.PlayAudio("click");
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SoundCache.StopMusic("menu");
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
				holder.menu_stage = (TextView) convertView
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
									info.state == StageData.PASSED ? StageData.PASSED_BACKGROUND_DRAWABLE
											: StageData.LOCKED_BACKGROUND_DRAWABLE));
			holder.menu_stage.setText(info.name);

			holder.menu_state.removeAllViews();
			ImageView v = null;
			switch (info.state) {
			case StageData.PASSED:
				v = new ImageView(MenuActivity.this);
				v.setImageBitmap(BitmapCache.get("star" + info.star));
				holder.menu_state.addView(v);
				break;
			case StageData.LOCKED:
				v = new ImageView(MenuActivity.this);
				v.setImageResource(R.drawable.lock);
				holder.menu_state.addView(v);
				break;
			case StageData.OPENING:
				v = new ImageView(MenuActivity.this);
				v.setImageResource(R.drawable.enter_btn);
				v.setId(info.level);
				v.setOnClickListener(MenuActivity.this);
				holder.menu_state.addView(v);
				break;
			}

			return convertView;
		}
	}

	public class ViewHolder {
		public TextView menu_stage;
		public LinearLayout menu_state;
	}
}
