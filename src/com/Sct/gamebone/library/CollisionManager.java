package com.Sct.gamebone.library;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
	private static CollisionManager instance = null;

	private List<Collision> list = new ArrayList<Collision>();

	public static synchronized CollisionManager getInstance() {
		if (instance == null)
			instance = new CollisionManager();
		return instance;
	}

	public void registerTouchEvent(Collision t) {
		if (list.contains(t)) {
			list.remove(t);
		}
		list.add(t);
	}

	public void unregisterTouchEvent(Collision t) {
		if (list.contains(t)) {
			list.remove(t);
		}
	}

	public void checkIfCollised() {
		for (Collision c : list) {
			if (!c.isStatic() && c.isCollisionEnabled()) {
				for (Collision c2 : list) {
					if (c == c2 || !c2.isCollisionEnabled())
						continue;
					if (c.getCollisionArea().contains(c2.getCollisionArea())) {
						c.onCollision(c2);
						c2.onCollision(c);
					}
				}
			}
		}
	}
}
