package com.example.turnlabyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback, Runnable
{


	private static double e = 0.8;
	private static double d = 0.99;
	private float gx;
	private float gy;
	private Paint paint;
	private SurfaceHolder holder;
	private Thread thread;
	private ArrayList<Circle> container;
	private int width;
	private int height;
	private int flag = 0;
	private float x_date = 0;
	private float y_date = 0;

	public MainView(Context context)
	{
		super(context);
		holder = null;
		thread = null;
		container = new ArrayList<Circle>();
		paint = new Paint();
		paint.setAntiAlias(true);
		gx = gy = 0;

		// getHolder()メソッドでSurfaceHolderを取得。さらにコールバックを登録
		getHolder().addCallback(this);
	}


	//SurfaceView生成時に呼び出される
	public void surfaceCreated(SurfaceHolder holder)
	{
		this.holder = holder;
		thread = new Thread(this);
	}

	//SurfaceView変更時に呼び出される
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		if(thread != null )
		{
			this.width  = width;
			this.height = height;
			thread.start();
		}
	}

	//SurfaceView破棄時に呼び出される
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		thread = null;
	}

	//スレッドによるSurfaceView更新処理
	public void run()
	{
		
		while (thread != null)
		{
			//描画処理
			Canvas canvas = holder.lockCanvas();
			canvas.drawColor(Color.argb(255, 0, 0, 0));
			int size = container.size();


		    Paint mpaint = new Paint();
		    mpaint.setColor(Color.argb(255,255, 255, 255));
			canvas.drawLine(50, 200, 300, 200, mpaint);
			float[] pts1 = {450, 50, 450, 800};
			canvas.drawLines(pts1, mpaint);
			float[] pts2 = {200, 350, 450, 350};
			canvas.drawLines(pts2, mpaint);
			float[] pts3 = {50, 50, 50, 800};
			canvas.drawLines(pts3, mpaint);
			float[] pts4 = {50, 500, 300, 500};
			canvas.drawLines(pts4, mpaint);
			float[] pts5 = {200, 650, 450, 650};
			canvas.drawLines(pts5, mpaint);
			float[] pts6 = {50, 800, 450, 800};
			canvas.drawLines(pts6, mpaint);
			float[] pts7 = {50, 50, 450, 50};
			canvas.drawLines(pts7, mpaint);
			

			if(flag == 1)
			{
			    Paint goalpaint = new Paint();
			    goalpaint.setColor(Color.argb(255,255,100,100));
				float[] goal = {350, 650, 350, 800};
				canvas.drawLines(goal, goalpaint);
			}
			
			if(flag == 2)
			{
			    Paint goalpaint = new Paint();
			    goalpaint.setColor(Color.argb(255,100,100,255));
				float[] goal = {350, 650, 350, 800};
				canvas.drawLines(goal, goalpaint);
			}

			// 計算をボールに反映
			for(int i = 0 ; i < size ; i++)
			{
				Circle a = container.get(i);
				
				a.dx *= d;
				a.dx += gx;
				x_date = a.x;
				a.x += a.dx;
				
				a.dy *= d;
				a.dy += gy;
				y_date = a.y;
				a.y += a.dy;

				if(a.x <= 50)
				{
					a.x = 50;
					a.dx *= -1;
				}
				if(a.y <= 50)
				{
					a.y = 50;
					a.dy *= -1;
				}
				if(a.x >= 450)
				{
					a.x = 450;
					a.dx *= -1;
				}
				if(a.y >= 800)
				{
					a.y = 800;
					a.dy *= -1;
				}
				
				
				
				if(y_date < 200 && a.y >= 200)
				{
					if(x_date <= 300 && x_date >= 50)
					{
						a.y = y_date;
						a.dy *= -1;
					}
				}
				if(y_date > 200 && a.y <= 200)
				{
					if(x_date <= 300 && x_date >= 50)
					{
						a.y = y_date;
						a.dy *= -1;
					}
				}

				if(y_date < 350 && a.y >= 350)
				{
					if(x_date <= 450 && x_date >= 200)
					{
						a.y = y_date;
						a.dy *= -1;
					}
				}
				if(y_date > 350 && a.y <= 350)
				{
					if(x_date <= 450 && x_date >= 200)
					{
						a.y = y_date;
						a.dy *= -1;
					}
				}

				if(y_date < 500 && a.y >= 500)
				{
					if(x_date <= 300 && x_date >= 50)
					{
						a.y = y_date;
						a.dy *= -1;
					}
				}
				if(y_date > 500 && a.y <= 500)
				{
					if(x_date <= 300 && x_date >= 50)
					{
						a.y = y_date;
						a.dy *= -1;
					}
				}
				
				if(y_date < 650 && a.y >= 650)
				{
					if(x_date <= 450 && x_date >= 200)
					{
						a.y = y_date;
						a.dy *= -1;
					}
				}
				if(y_date > 650 && a.y <= 650)
				{
					if(x_date <= 450 && x_date >= 200)
					{
						a.y = y_date;
						a.dy *= -1;
					}
				}
				
				
				x_date = a.x;
				y_date = a.y;
				
				paint.setColor(Color.argb(255, a.cr, a.cg, a.cb));
				canvas.drawCircle(a.x, a.y, a.radius, paint);
			}
			holder.unlockCanvasAndPost(canvas);
			
			if(y_date > 650)
			{
				if(x_date > 350)
				{
					flag = 2;
				}
			}
		}
	}

	// クリック時のイベント
	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			
			if(flag == 0){
				float x = 100;
				float y = 100;
				float dx = (float)(5);
				float dy = (float)(5);
				float ran = (float)0.5;
				float r = ran*20+5;
				float m = ran*10+10;
				int cr = 255;
				int cb = 128;
				int cg = 128;
				container.add(new Circle(r, x, y, dx, dy, m, cr, cg, cb));
				flag = 1;
			}
		}
		return true;
	}

	// 加速度の更新
	public void setAcce(float gx, float gy)
	{
		this.gx = gx;
		this.gy = gy;
	}

}