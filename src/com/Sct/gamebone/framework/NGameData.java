package com.Sct.gamebone.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.AssetManager;
import android.graphics.Rect;

import com.Sct.gamebone.library.TileCache;

public class NGameData {
	private int height = 15;
	private int width = 30;
	private int tilewidth = 32;
	private int tileheight = 32;
	private List<List<Integer>> layers = new ArrayList<List<Integer>>();
	private List<TileObject> tileObject = new ArrayList<TileObject>();
	private Map<String, TileObject> mapTileObject = new HashMap<String, TileObject>();

	public int getTileWidth() {
		return tilewidth;
	}

	public int getTileHeight() {
		return tileheight;
	}

	public TileObject getTileObject(String name) {
		if (mapTileObject.containsKey(name))
			return mapTileObject.get(name);
		return null;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<TileObject> getTileObject() {
		return tileObject;
	}

	public int getLayerCount() {
		return layers.size();
	}

	public List<Integer> getLayer(int index) {
		if (layers.size() > index)
			return layers.get(index);
		return null;
	}

	public Rect getRect(int id) {
		int row = (id - 1) / width;
		int col = (id - 1) % width;

		int left = tilewidth * col;
		int top = tileheight * row;
		return new Rect(left, top, left + tilewidth, top + tileheight);
	}

	public static NGameData readFromFile(String filename) {
		String jsonString = "";
		try {
			AssetManager assetManager = GameApp.getApplication().getAssets();
			InputStream in = assetManager.open(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				jsonString += tmp;
			}
			return readFromJson(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static NGameData readFromJson(String jsonString) {
		NGameData data = new NGameData();
		try {
			JSONObject json = new JSONObject(jsonString);
			data.height = json.getInt("height");
			data.width = json.getInt("width");
			data.tileheight = json.getInt("tileheight");
			data.tilewidth = json.getInt("tilewidth");

			// layers
			JSONArray layers = json.getJSONArray("layers");

			for (int i = 0; i < layers.length(); i++) {
				JSONObject layer = layers.getJSONObject(i);
				if (layer.getString("type").equals("objectgroup")) {
					JSONArray d = layer.getJSONArray("objects");
					for (int j = 0; j < d.length(); j++) {
						TileObject o = TileObject.parseFromJson(d
								.getJSONObject(j));
						if (o != null) {
							data.tileObject.add(o);
							data.mapTileObject.put(o.name, o);
						}
					}
				} else {
					JSONArray d = layer.getJSONArray("data");
					List<Integer> list = new ArrayList<Integer>();
					for (int j = 0; j < d.length(); j++) {
						list.add(d.getInt(j));
					}
					data.layers.add(list);
				}
			}

			// tilesets
			JSONArray tilesets = json.getJSONArray("tilesets");
			for (int i = 0; i < tilesets.length(); i++) {
				JSONObject tileset = tilesets.getJSONObject(i);
				TileCache.AddTileset(new Tileset(tileset));
			}
			return data;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static class TileObject {
		public String type = "";
		private String name = "";
		public int x;
		public int y;
		public Map<String, String> properties = new HashMap<String, String>();

		public static TileObject parseFromJson(JSONObject obj) {
			TileObject to = new TileObject();
			try {
				to.name = obj.getString("name");
				to.type = obj.getString("type");
				to.x = obj.getInt("x");
				to.y = obj.getInt("y");

				JSONObject o = obj.getJSONObject("properties");
				for (Iterator k = o.keys(); k.hasNext();) {
					String key = (String) k.next();
					to.properties.put(key, o.getString(key));
				}
				return to;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	}

	public static class Tileset {
		public static int Lastgid = 0xffffff;
		public static final int JSON = 0;
		public static final int CONSTRUCTOR = 1;
		public static final int CONSTRUCTOR_START_GID = 0xffffff;
		private int imageheight = 0;
		private int imagewidth = 0;
		private String name = "";
		private int tileheight = 0;
		private int tilewidth = 0;
		private String image = "";
		private String transparentcolor = "";
		private int width = 0;
		private int height = 0;
		private int firstgid = 0;
		private int from = 0;

		public Tileset(int imagewidth, int imageheight, String name,
				int tilewidth, int tileheight, String transparentcolor) {
			this.from = CONSTRUCTOR;
			this.imageheight = imageheight;
			this.imagewidth = imagewidth;
			this.name = name.toLowerCase();
			this.tileheight = tileheight;
			this.tilewidth = tilewidth;
			this.transparentcolor = transparentcolor;

			makeupdata();

			firstgid = Lastgid + 1;
			Lastgid = getLastgid();
		}

		public String getTransparentColor() {
			return transparentcolor;
		}

		public int getLastgid() {
			return firstgid + width * height - 1;
		}

		private void makeupdata() {
			width = imagewidth / tilewidth;
			height = imageheight / tileheight;
		}

		public int getFirstgid() {
			return firstgid;
		}

		public String getName() {
			return name;
		}

		public Rect getRect(int id) {
			int row = (id - firstgid) / width;
			int col = (id - firstgid) % width;

			int left = tilewidth * col;
			int top = tileheight * row;
			return new Rect(left, top, left + tilewidth, top + tileheight);
		}

		public Tileset(JSONObject json) {
			try {
				from = JSON;
				imageheight = json.getInt("imageheight");
				imagewidth = json.getInt("imagewidth");
				name = json.getString("name").toLowerCase();
				tileheight = json.getInt("tileheight");
				tilewidth = json.getInt("tilewidth");
				image = json.getString("image").toLowerCase();
				firstgid = json.getInt("firstgid");
				if (json.has("transparentcolor"))
					transparentcolor = json.getString("transparentcolor");

				makeupdata();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public Tileset(String jsonString) throws JSONException {
			this(new JSONObject(jsonString));
		}
	}
}
