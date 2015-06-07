package com.Sct.gamebone.view;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TextSprite extends BaseNode {
	private String mText = "";
	private Paint mPaint = null;

	public TextSprite(String text) {
		this(text, new Paint());
	}

	public TextSprite(String text, Paint p) {
		setText(text);
		setPaint(p);
	}

	public TextSprite setText(String text) {
		mText = text;
		return this;
	}

	public TextSprite setPaint(Paint p) {
		mPaint = p;
		mPaint.setTextAlign(Paint.Align.CENTER);
		return this;
	}

	public TextSprite setTextSize(float size) {
		mPaint.setTextSize(size);
		return this;
	}

	public TextSprite setTextColor(int c) {
		mPaint.setColor(c);
		return this;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawText(mText, getRealX(), getRealY(), mPaint);
	}

}
