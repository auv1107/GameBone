package com.Sct.gamebone.framwork;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.Sct.gamebone.BitmapCache;
import com.Sct.gamebone.CollisionManager;
import com.Sct.gamebone.R;
import com.Sct.gamebone.TileCache;
import com.Sct.gamebone.TouchDispatchCenter;
import com.Sct.gamebone.framwork.NGameData.TileObject;
import com.Sct.gamebone.framwork.NGameData.Tileset;
import com.Sct.gamebone.view.Mirror;
import com.Sct.gamebone.view.NAnimator;

public class NGameEngine extends BaseGameEngine {
	private NGameData mNGameData = null;
	private Context mContext = null;

	private int width = 0;
	private int height = 0;
	private int tilewidth = 0;
	private int tileheight = 0;
	private float screenscale_x = 0f;
	private float screenscale_y = 0f;

	private int[] margin = new int[] { 0, 0, 0, 128 };
	private NAnimator mShooter = null;
	private List<NAnimator> mReceiver = new ArrayList<NAnimator>();

	private List<NAnimator> mEnergy = new ArrayList<NAnimator>();

	private int state = PAUSE;
	public static final int PAUSE = 1;
	public static final int GAME = 0;

	public Mirror m = new Mirror(getContext(), new int[] {}, 400, 600, 96, 96);

	public NGameEngine(Context context) {
		mContext = context;
		Tileset ts = new Tileset(960, 960, "light", 192, 192, "#000000");
		TileCache.AddTileset(ts);
		BitmapCache.put("ui1", R.drawable.ui1);
	}

	public Context getContext() {
		return mContext;
	}

	@Override
	public void initGame() {
		// TODO Auto-generated method stub
		mNGameData = NGameData.readFromFile(getContext().getAssets(),
				NGameData.MAP1);
		makeupdata();
		addShooter();
		addEnergy();

		super.initGame();
	}

	public void makeupdata() {
		width = mNGameData.getWidth();
		height = mNGameData.getHeight();
		int screenwidth = GameApp.getApplication().getScreenWidth();
		int screenheight = GameApp.getApplication().getScreenHeight();
		tilewidth = (screenwidth - margin[1] - margin[3]) / width;
		tileheight = (screenheight - margin[0] - margin[2]) / height;

		screenscale_x = tilewidth * 1.0f / mNGameData.getTileWidth();
		screenscale_y = tileheight * 1.0f / mNGameData.getTileHeight();
	}

	public void addEnergy() {
		int firstgid = TileCache.getFirstGid("light");
		mEnergy.add(new NAnimator(getContext(), new int[] { firstgid + 2 },
				mShooter.getX(), mShooter.getY(), 192, 192).setSpeed(90));
	}

	public void addShooter() {
		int firstgid = TileCache.getFirstGid("light");
		List<TileObject> list = mNGameData.getTileObject();
		for (TileObject to : list) {
			if (to.type.equals("0")) {
				mShooter = new NAnimator(getContext(),
						new int[] { firstgid + 4, firstgid + 5, firstgid + 6,
								firstgid + 7 }, (int) (to.x * screenscale_x),
						(int) (to.y * screenscale_y), 192, 192);
			}
			if (to.type.equals("1")) {
				NAnimator na = new NAnimator(getContext(),
						new int[] { firstgid }, (int) (to.x * screenscale_x),
						(int) (to.y * screenscale_y), 192, 192);
				na.setRotateSpeed(360);
				mReceiver.add(na);
			}
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.save();
		canvas.clipRect(margin[3], margin[0], GameApp.getApplication()
				.getScreenWidth() - margin[1], GameApp.getApplication()
				.getScreenHeight() - margin[2]);
		canvas.translate(margin[3], margin[0]);
		super.onDraw(canvas);
		drawMap(canvas);
		drawObjects(canvas);
		drawEnergy(canvas);

		m.onDraw(canvas);

		canvas.restore();
		drawToolbar(canvas);
	}
	
	private void drawToolbar(Canvas canvas) {
		Bitmap b = BitmapCache.get("ui1");
		Paint p = new Paint();
		p.setShadowLayer(10, 8, 8, Color.DKGRAY);
		canvas.drawBitmap(b, new Rect(0,0,64,160), new Rect(0,0,margin[3],GameApp.getApplication().getScreenHeight()), p);
	}

	private void drawEnergy(Canvas canvas) {
		for (NAnimator e : mEnergy) {
			e.onDraw(canvas);
		}
	}

	private void drawObjects(Canvas canvas) {
		if (mShooter != null)
			mShooter.onDraw(canvas);
		for (NAnimator na : mReceiver) {
			na.onDraw(canvas);
		}
	}

	private void drawMap(Canvas canvas) {
		int count = mNGameData.getLayerCount();
		for (int i = 0; i < count; i++) {
			List<Integer> layer = mNGameData.getLayer(i);
			if (layer != null) {
				for (int j = 0; j < layer.size(); j++) {
					if (layer.get(j) == 0)
						continue;
					TileCache.Draw(canvas, layer.get(j), getRect(j),
							new Paint());
				}
			}
		}
	}

	@Override
	public void onTouch(MotionEvent e) {
		// TODO Auto-generated method stub
		super.onTouch(e);
		if (state == PAUSE) {
			state = GAME;
			resumeAllChild();
		}
		if (TouchDispatchCenter.getInstance().getTouchEnabled()) {
			TouchDispatchCenter.getInstance().onTouch(e);
		}
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
		CollisionManager.getInstance().checkIfCollised();
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		super.exit();
	}

	public Rect getRect(int id) {
		int row = id / width;
		int col = id % width;

		int left = tilewidth * col;
		int top = tileheight * row;
		return new Rect(left, top, left + tilewidth, top + tileheight);
	}

	public void pauseAllChild() {
		for (NAnimator na : mEnergy)
			na.pause();
		for (NAnimator na : mReceiver)
			na.pause();
		mShooter.pause();
	}

	public void resumeAllChild() {
		for (NAnimator na : mEnergy)
			na.resume();
		for (NAnimator na : mReceiver)
			na.resume();
		mShooter.resume();
	}

	public int getIdByPoint(int x, int y) {
		int left = x - margin[3];
		int top = y - margin[0];
		return left / tilewidth + top / tileheight * width;
	}

}