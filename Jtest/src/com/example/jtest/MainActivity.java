package com.example.jtest;

import java.util.List;



import android.os.StrictMode;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
//import android.os.ServiceManager;
import android.util.Log;
//import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
//import net.pocketmagic.keyinjector.AndroidKeyInjectorActivity;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private CameraPreview camPreview;
	private ImageView MyCameraPreview = null;
	private FrameLayout mainLayout;
	//private int PreviewSizeWidth = 1280;
 	//private int PreviewSizeHeight=720;
	private int PreviewSizeWidth = 640;
    private int PreviewSizeHeight= 480;
 	LayoutInflater controlInflater = null;
// int valuex,valuey;
 	 static boolean m_bDoInject = false;
	static boolean m_bRunning = true;
	static int method = 2;
	int cont=0;
	private final Handler handler = new Handler();
	
	
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        Log.e("Activity", "onCreate()");
        //Set this APK Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				 			WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Set this APK no title
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.activity_main);
        cmdTurnCursorServiceOn();
        
        /*Intent i = new Intent();
        i.setAction("com.example.ldetect.CameraPreview");
        startService(i); */
        
        
        
       
        //
        // Create my camera preview 
        //
        MyCameraPreview = new ImageView(this);
      //  MyCameraPreview.setBackgroundResource(R.drawable.and);
        SurfaceView camView = new SurfaceView(this);
        SurfaceHolder camHolder = camView.getHolder();
        camPreview = new CameraPreview(PreviewSizeWidth, PreviewSizeHeight, MyCameraPreview,this);
        
        camHolder.addCallback(camPreview);
        camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        
        mainLayout = (FrameLayout) findViewById(R.id.frameLayout1);
        mainLayout.addView(camView, new LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
        mainLayout.addView(MyCameraPreview, new LayoutParams(PreviewSizeWidth, PreviewSizeHeight));
      //  Intent intent3 = getIntent();
        
				
			
        controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.over, null);
        LayoutParams layoutParamsControl
         = new LayoutParams(LayoutParams.FILL_PARENT,
         LayoutParams.FILL_PARENT);
        this.addContentView(viewControl, layoutParamsControl);
       // viewControl.setRotation(90);
        
        
        
     
        
        
        
    }
   
   
   
   private void cmdTurnCursorServiceOn() {	
		
		Intent i = new Intent();
       i.setAction("com.example.jtest.CursoeService");
       startService(i);	
   
		
	}
	
	
	public void cmdTurnCursorServiceOff() {
		Intent i = new Intent();
    i.setAction("com.example.jtest.CursoeService");
    stopService(i);
	}
	
	
	
	/*public static void cmdShowCursor() {
		if (Singleton.getInstance().m_CurService != null)
			Singleton.getInstance().m_CurService.ShowCursor(true);
	}
	
	public static void cmdHideCursor() {
		if (Singleton.getInstance().m_CurService != null)
			Singleton.getInstance().m_CurService.ShowCursor(false);
	}*/
	
   
   
   
   
   
 	
   
   public  void onDestroy() {
		super.onDestroy();
		Log.e("Activity", "onDestroy()");
		//m_bRunning = false;
		//CameraPreview.mCamera.setPreviewCallback(null);
    	//CameraPreview.mCamera.stopPreview();
    	//CameraPreview.mCamera.release();
    	//CameraPreview.mCamera = null;
    	//CameraPreview.cmdTurnCursorServiceOff();
		
	}
   
    protected void onPause()
	{
		//if ( camPreview != null)
			//camPreview.onPause();
    	
		super.onPause();
		Log.e("Activity", "onPause()");
	}
    public void onResume() {
    	super.onResume();
    	Log.e("Activity", "onResume()");
    //	handler.postDelayed(startActivityRunnable,10000);
    	
    }
    
    
    
/*private final Runnable startActivityRunnable = new Runnable() {
		
	    @Override
	    public void run() {
	    	
	    	
	    	Intent intent = new Intent();
	    	intent.setClass(MainActivity.this,SecActivity.class);
			startActivity(intent);
	            
	        
	    }

	    };*/


    
    public void onStop() {
    	super.onStop();
    	Log.e("Activity", "onStop() ");
    }
    
    
}
