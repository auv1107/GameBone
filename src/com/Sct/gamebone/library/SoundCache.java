package com.Sct.gamebone.library;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.Sct.gamebone.framework.GameApp;

public class SoundCache {
	protected static boolean mIsMute = false;
	protected static HashMap<String, MediaPlayer> mMusicMap = new HashMap<String, MediaPlayer>();
	protected static HashMap<String, Integer> mAudioMap = new HashMap<String, Integer>();
	protected static SoundPool mSoundPool = null;
	protected static float mVolume = 0;

	public static void AddMusic(String key, int res) {
		MediaPlayer mp = MediaPlayer.create(GameApp.getApplication(), res);
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mMusicMap.put(key, mp);
	}

	public static void AddAudio(String key, int res) {
		if (mSoundPool == null) {
			initSoundPool();
		}
		mAudioMap.put(key, mSoundPool.load(GameApp.getApplication(), res, 1));
	}

	public static void PlayMusic(String key, boolean loop) {
		if (mIsMute)
			return;

		MediaPlayer mp = mMusicMap.get(key);
		if (mp != null) {
			mp.setLooping(loop);
			// mp.seekTo(0);
			mp.start();
		}
	}

	public static void PlayAudio(String key) {
		if (mSoundPool == null)
			initSoundPool();
		mSoundPool.play(mAudioMap.get(key), mVolume, mVolume, 1, 0, 1.0f);
	}

	public static void initSoundPool() {
		mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
		AudioManager audioManager = (AudioManager) GameApp.getApplication()
				.getSystemService(Context.AUDIO_SERVICE);
		float currVolume = audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mVolume = currVolume / maxVolume;
	}

	public static void StopMusic(String key) {
		MediaPlayer mp = mMusicMap.get(key);
		if (mp != null && mp.isPlaying()) {
			mp.pause();
		}
	}

}
