package com.example.turnlabyrinth;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
	private static double d = 0.99;
	private float gx,gy;
	private Paint paint;
	private SurfaceHolder holder;
	private Thread thread;
	private ArrayList<Circle> container;
	@SuppressWarnings("unused")
	private int width,height;
	private int flag = 0;
	private float x_date , y_date;

	public MainView(Context context)
	{
		super(context);
		holder = null;
		thread = null;
		container = new ArrayList<Circle>();
		paint = new Paint();
		paint.setAntiAlias(true);
		gx = gy = 0;

		getHolder().addCallback(this);
	}
	public void surfaceCreated(SurfaceHolder holder)
	{
		this.holder = holder;
		thread = new Thread(this);
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		if(thread != null )
		{
			this.width  = width;
			this.height = height;
			thread.start();
		}
	}
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		thread = null;
	}
	public void run()
	{
		x_date = y_date = 0;
		
		while (thread != null)
		{
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
			for(int i = 0 ; i < size ; i++)
			{
				Circle main_mine = container.get(i);
				
				main_mine.dx *= d;
				main_mine.dx += gx;
				x_date = main_mine.x;
				main_mine.x += main_mine.dx;
				
				main_mine.dy *= d;
				main_mine.dy += gy;
				y_date = main_mine.y;
				main_mine.y += main_mine.dy;

				if(main_mine.x <= 50 || main_mine.x >= 450)
				{
					if(main_mine.x <= 50)
					{
						main_mine.x = 50;
					}
					else
					{
						main_mine.x = 450;
					}
					main_mine.dx *= -1;
				}
				
				if(main_mine.y <= 50 || main_mine.y >= 800)
				{
					if(main_mine.y <= 50)
					{
						main_mine.y = 50;
					}
					else
					{
						main_mine.y = 800;
					}
					main_mine.dy *= -1;
				}
				else if((y_date < 200 && main_mine.y >= 200) || (y_date > 200 && main_mine.y <= 200))
				{
					if(x_date <= 300 && x_date >= 50)
					{
						main_mine.y = y_date;
						main_mine.dy *= -1;
					}
				}
				else if((y_date < 350 && main_mine.y >= 350)||(y_date > 350 && main_mine.y <= 350))
				{
					if(x_date <= 450 && x_date >= 200)
					{
						main_mine.y = y_date;
						main_mine.dy *= -1;
					}
				}
				else if((y_date < 500 && main_mine.y >= 500) || (y_date > 500 && main_mine.y <= 500))
				{
					if(x_date <= 300 && x_date >= 50)
					{
						main_mine.y = y_date;
						main_mine.dy *= -1;
					}
				}
				else if((y_date < 650 && main_mine.y >= 650)||(y_date > 650 && main_mine.y <= 650))
				{
					if(x_date <= 450 && x_date >= 200)
					{
						main_mine.y = y_date;
						main_mine.dy *= -1;
					}
				}
				
				x_date = main_mine.x;
				y_date = main_mine.y;
				
				paint.setColor(Color.argb(255, main_mine.cr, main_mine.cg, main_mine.cb));
				canvas.drawCircle(main_mine.x, main_mine.y, main_mine.radius, paint);
			}
			holder.unlockCanvasAndPost(canvas);
			
			if(y_date > 650 && x_date > 350)
			{
				flag = 2;
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			
			if(flag == 0){
				float x = 100;
				float y = 100;
				float dx = (float)5;
				float dy = (float)5;
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

	public void setAcce(float gx, float gy)
	{
		this.gx = gx;
		this.gy = gy;
	}

}