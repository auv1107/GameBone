package com.Sct.gamebone.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;

public class TextSprite extends BaseNode {
	private String mText = "";
	private Paint mPaint = null;
	private int mMaxLengthInLine = 0;

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
		updateFontHeight();
		return this;
	}

	public void updateFontHeight() {
		FontMetrics fm = mPaint.getFontMetrics();
		height = (int) (Math.ceil(fm.descent - fm.top) + 2);
	}

	public TextSprite setTextSize(float size) {
		mPaint.setTextSize(size);
		updateFontHeight();
		return this;
	}

	public TextSprite setTextColor(int c) {
		mPaint.setColor(c);
		return this;
	}

	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (mMaxLengthInLine <= 0)
			canvas.drawText(mText, getRealX(), getRealY(), mPaint);
		else {
			int length = mText.length();
			int s = (length - 1) / mMaxLengthInLine + 1;
			for (int i = 0; i < s; i++) {
				int start = i * mMaxLengthInLine;
				int end = (i + 1) * mMaxLengthInLine > length ? length
						: (i + 1) * mMaxLengthInLine;
				String tmp = mText.substring(start, end);
				int y = i * height + getRealY();
				canvas.drawText(tmp, getRealX(), y, mPaint);
			}
		}
	}

	public int getMaxLengthInLine() {
		return mMaxLengthInLine;
	}

	public void setMaxLengthInLine(int mMaxLengthInLine) {
		this.mMaxLengthInLine = mMaxLengthInLine;
	}

}
