package com.Sct.gamebone;

import android.graphics.Rect;

public interface Collision {
	public Rect getCollisionArea();
	public void onCollision(Collision c);
	public boolean isCollisionEnabled();
	public boolean isStatic();
	public int getType();
}
