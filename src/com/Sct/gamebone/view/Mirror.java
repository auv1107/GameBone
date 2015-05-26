package com.Sct.gamebone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;

public class Mirror extends NAnimator {
	private Paint mBluePaint = null;
	private Paint mRedPaint = null;
	private Paint mYellowPaint = null;
	private Paint mGreenPaint = null;

	public Mirror(Context context, int[] stateIds, int x, int y, int width,
			int height) {
		super(context, stateIds, x, y, width, height);
		initPaint();
	}

	private void initPaint() {
		Paint p = new Paint();
		p.setStrokeWidth(40);
		p.setStyle(Paint.Style.FILL);
		MaskFilter mf = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6,
				3.5f);
		p.setMaskFilter(mf);

		mBluePaint = new Paint(p);
		mBluePaint.setColor(Color.BLUE);
		mRedPaint = new Paint(p);
		mRedPaint.setColor(Color.RED);
		mYellowPaint = new Paint(p);
		mYellowPaint.setColor(Color.YELLOW);
		mGreenPaint = new Paint(p);
		mGreenPaint.setColor(Color.GREEN);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawLine(x, y, x + width, y + height, mBluePaint);
	}

	@Override
	protected void update(int delta) {

	}
}
