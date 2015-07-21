package com.Sct.gamebone;

import java.lang.reflect.Field;

import com.Sct.gamebone.R.drawable;
import com.Sct.gamebone.framework.GameApp;
import com.Sct.gamebone.library.BitmapCache;

public class GameEntry {
	private static GameEntry instance = null;

	public synchronized static GameEntry getInstance() {
		if (instance == null)
			instance = new GameEntry();
		return instance;
	}

	public void prepare() {
		// prepare some resources for game
		prepareImages();
		prepareMusic();
		GameApp.getApplication().prepareString();
	}

	public void run() {
		// backup
	}

	public void app() {
		GameApp.getApplication().setStartupScene(GameScene.class.getName());
	}

	public void prepareImages() {
		Class<drawable> c = R.drawable.class;

		for (Field f : c.getFields()) {
			try {
				BitmapCache.put(f.getName(), f.getInt(f));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void prepareMusic() {
	}
}
