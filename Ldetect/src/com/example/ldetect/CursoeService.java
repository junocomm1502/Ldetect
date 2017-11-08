package com.example.ldetect;


import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar.LayoutParams;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;

import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CursoeService extends Service  {
	
	OverlayView mView;

    /**
     * @param x
     * @param y
     * @param autoenable if set, it will automatically show the cursor when movement is detected
     */
   // public void Update(final int x1, final int y1,final int x2, final int y2,final int x3, final int y3,final int x4, final int y4,final int x5, final int y5,final boolean autoenable) {
    //	mView.Update(x1,y1,x2,y2,x3,y3,x4,y4,x5,y5);
    	
  public void Update(final int x1, final int y1,final int x2, final int y2,final boolean autoenable,int col,int v1,int v2,Activity parent,double x,int bx1,int by1,int bx2,int by2,int bx3,int by3,int bx4,int by4) {
        	mView.Update(x1,y1,x2,y2,col,v1,v2,parent,x,bx1,by1,bx2,by2,bx3,by3,bx4,by4);
    	if (autoenable && !mView.isCursorShown() ) 
    		ShowCursor(true); //will also post invalidate
    	else
    		mView.postInvalidate();
    }
    public void ShowCursor(boolean status) {
    	mView.ShowCursor(status);
    	mView.postInvalidate();
    }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate() {
        super.onCreate();
        
        CameraPreview.m_CurService = this;

		Log.d("CursorService", "Service created");
		
        mView = new OverlayView(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,//TYPE_SYSTEM_ALERT,//TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, //will cover status bar as well!!!
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.TOP;
       //params.x = 100;
        //params.y = 100;
        params.setTitle("Cursor");
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(mView, params);
        //wm.
       
      /*  android.widget.LinearLayout.LayoutParams param = 
                new LinearLayout.LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT);

        //create a layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        Button btnAddARoom = new Button(this);
        btnAddARoom.setText("Add");
        btnAddARoom.setLayoutParams(param);
        
        wm.addView(mView,btnAddARoom);*/
  

        
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("CursorService", "Service destroyed");
        CameraPreview.m_CurService = null;
        if(mView != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mView);
            mView = null;
        }
    }
}

class OverlayView extends ViewGroup {
    private Paint mLoadPaint;
	private Activity parent1;
    Paint paint;
    boolean mShowCursor;
    private int paintColor;
    Bitmap	cursor,launcher,launcher2;
    Context contexts;
    private int    mFPS = 0;         // the value to show
    private int    mFPSCounter = 0;  // the value to count
    private long   mFPSTime = 0;     // last update time

    public int  px1 = 0,py1 = 0,px2 = 0,py2 = 0,px3 = 0,py3 = 0,px4 = 0,py4 = 0,px5 = 0,py5 = 0,px6 = 0,py6 = 0,val1,val2;
    public double xval=0;
    
    public void Update(int x1, int y1,int x2, int y2,int color,int v1,int v2,Activity parent,double x,int bx1,int by1,int bx2,int by2,int bx3,int by3,int bx4,int by4 ) {
    	px1 = x1; py1 = y1; 
    	px2 = x2; py2 = y2; 
    	val1=v1;
    	val2=v2;
    	xval=x;
    	px3 = bx1; py3 = by1; px4 = bx2; py4 = by2; px5 = bx3; py5 = by3;px6 = bx4; py6 = by4;
    	//px5 = x5; py5 = y5;
    	paintColor=color;
    	paint.setColor(paintColor);
    	 parent1=parent;
    	
    }
    public void ShowCursor(boolean status) {
    	mShowCursor = status;
	}
    public boolean isCursorShown() {
    	return mShowCursor;
    }
    
	public OverlayView(Context context) {
        super(context);

        
        cursor = BitmapFactory.decodeResource(context.getResources(), R.drawable.cursor);
        launcher = BitmapFactory.decodeResource(context.getResources(), R.drawable.h);
        launcher2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.s);
        
        mLoadPaint = new Paint();
      //  mLoadPaint.setAntiAlias(true);
        mLoadPaint.setTextSize(40);
        mLoadPaint.setARGB(255, 255, 0, 0);
        
        mLoadPaint.setColor(Color.BLUE);
      //  mLoadPaint.setStyle(Paint.Style.STROKE);
      //  mLoadPaint.setStrokeJoin(Paint.Join.ROUND);
      //  mLoadPaint.setStrokeWidth(10f);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(40);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // canvas.drawText("Hello World", 100,100, mLoadPaint);
        
      /*  if (SystemClock.uptimeMillis() - mFPSTime > 1000) {
            mFPSTime = SystemClock.uptimeMillis();
            mFPS = mFPSCounter;
            mFPSCounter = 0;
        } else {
            mFPSCounter++;
        }

        String s = "FPS: " + mFPS;*/
      //  canvas.drawText(""+xval, 100, 100, mLoadPaint);
       // Log.e("CursorService", "fps="+s);
        
        if (mShowCursor) 
        {
        	canvas.drawRect(0, 0,val1,val2,  paint);
        	//canvas.drawBitmap(cursor,0,0,null);
            canvas.drawBitmap(cursor,px1,py1,null);
          //  canvas.drawBitmap(cursor,px2,py2,null);
        	canvas.drawBitmap(launcher,px3,py3,null);
        	canvas.drawBitmap(launcher,px4,py4,null);
        	canvas.drawBitmap(launcher2,px5,py5,null);
        	canvas.drawBitmap(launcher,px6,py6,null);
        //	canvas.drawBitmap(cursor,270,350,null);
            
        }
    }

    @Override
    protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
		return true;
    }

}
