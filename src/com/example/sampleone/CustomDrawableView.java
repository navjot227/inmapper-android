package com.example.sampleone;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

public class CustomDrawableView extends View {
	private ShapeDrawable mDrawable;
	private Random rand = new Random();

	public CustomDrawableView(Context context) {
		super(context);

		int x = 10;
		int y = 10;
		int width = 300;
		int height = 50;

		mDrawable = new ShapeDrawable(new OvalShape());
		mDrawable.getPaint().setColor(0xff74AC23);
		mDrawable.setBounds(x, y, x + width, y + height);
	}

	protected void onDraw(Canvas canvas) {
		mDrawable.draw(canvas);
	}

	public void changeShape() {
//		while (true) {
	/*		try {
				Thread.currentThread();
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			mDrawable.setBounds(10,10,300+rand.nextInt(500),50+rand.nextInt(200));
			
		}
	//}
}
