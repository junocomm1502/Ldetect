package com.example.ldetect;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
//import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;



import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.app.Dialog;
import android.app.Instrumentation;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.net.Uri;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
 
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.imgproc.Imgproc;











public class CameraPreview implements SurfaceHolder.Callback, Camera.PreviewCallback 
{
	
	
	static Camera mCamera = null;
	private ImageView MyCameraPreview = null;
	private Bitmap bitmap = null;
	private int[] pixels = null;
	private byte[] FrameData = null;
	//private CursorService cservice=new CursorService();
	int[] pt=null;
	private int imageFormat;
	private int PreviewSizeWidth;
 	private int PreviewSizeHeight;
 	private boolean bProcessing = false;
 	private Activity parent;
 	boolean m_bRunning = true;
 	boolean running = true;
 	int minh,maxh,mins,maxs,minv,maxv; 
 	int sminh,smaxh,smins,smaxs,sminv,smaxv;
 	int pminh,pmaxh,pmins,pmaxs,pminv,pmaxv;
 	private SurfaceTexture surfaceTexture;
 	int lx,ly,xpt=0,ypt=0;
 	int ostatus=0,status=0,cstatus=0,corner=0;
 	int p1x=0,p1y=0,p2x=0,p2y=0,p3x=0,p3y=0,p4x=0,p4y=0,x=1,y=1;
 	static				CursoeService			m_CurService				= null;
 	private int paintColor=0xFFFFFFFF;
 	int borderst=0 ;
 	int con=0;
 	private static final int BUFFER_COUNT = 60;
 	//private SecActivity sa=new SecActivity();
 	//CursorService m_CurService=new CursorService();
 	//private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
 	
    //public static final int MEDIA_TYPE_IMAGE = 1;
   
 
    // directory name to store captured images and videos
   // private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
 
   // private Uri fileUri; // file url to store image/video
 	
 	
 	
 //	int x,y;
 //	private boolean m_bDoInject = false, m_bRunning = true;
 	
 	Instrumentation m_Instrumentation = new Instrumentation();
 //	public IBinder onBind(Intent intent) {
      //  return null;
   // }
 	
 	int m_fd;
	final static int EV_KEY = 0x01,EV_ABS = 0x03,
			REL_X = 0x00,
			REL_Y = 0x01;
 	
 	
 	Handler mHandler = new Handler(Looper.getMainLooper());
 	
 	Handler mCameraHandler;
 	HandlerThread mCameraThread ;
 	
	public CameraPreview(int PreviewlayoutWidth, int PreviewlayoutHeight,
    		ImageView CameraPreview,Activity parent,int h1,int h2,int s1,int s2,int v1,int v2,int sh1,int sh2,int ss1,int ss2,int sv1,int sv2)
    {
		PreviewSizeWidth = PreviewlayoutWidth;
    	PreviewSizeHeight = PreviewlayoutHeight;
    	MyCameraPreview = CameraPreview;
    	bitmap = Bitmap.createBitmap(PreviewSizeWidth, PreviewSizeHeight, Bitmap.Config.ARGB_8888);
    	pixels = new int[PreviewSizeWidth * PreviewSizeHeight];
    	this.parent = parent;
    	
    	minh=h1;
    	maxh=h2;
    	mins=s1;
    	maxs=s2;
    	minv=v1;
    	maxv=v2;
    	 
    	sminh=sh1;
    	smaxh=sh2;
    	smins=ss1;
    	smaxs=ss2;
    	sminv=sv1;
    	smaxv=sv2;
    	
    	
    	
    	
    }
	
	 

	
	public void onPreviewFrame( byte[] arg0, Camera arg1) 
	{
		
		// con=con+1;
		//Log.e("MyRealTimeImageProcessing", "a="+con);	
		//Log.e("CameraPreview", "onPreviewFrame");
		// At preview mode, the frame data will push to here.
		
		
		if (imageFormat == ImageFormat.NV21)
        {
			//We only accept the NV21(YUV420) format.
			if ( !bProcessing )
			{
				FrameData = arg0;
				
				
			        
				
	/*	Mat Yuv = new Mat(PreviewSizeHeight + PreviewSizeHeight / 2, PreviewSizeWidth, CvType.CV_8UC1);	  
		Yuv.put(0, 0, arg0);
		//Mat mRgba=Yuv.rgba(); 
		//Mat mGray =new Mat(Yuv.height(),Yuv.rgba().width(),CvType.CV_8UC1);
		//Imgproc.cvtColor(FrameData, mGray,Imgproc.COLOR_RGB2GRAY);
		MinMaxLocResult mmResult = Core.minMaxLoc(Yuv);	
		org.opencv.core.Point center=mmResult.maxLoc;
		//Core.circle(Yuv, center, 5, new Scalar(0,0,255,255), 2);
		Log.e("CameraPreview", "onPreviewFrame center "+center);
		lx=(int) center.x;
		ly=(int) center.y;*/
		
				mHandler.post(DoImageProcessing);
		
		
	/*	parent.runOnUiThread(new Runnable(){
	        public void run() {
	        	
	        	mHandler.post(DoImageProcessing);
	        }
	    });*/
		
			   
			}
			
        }
		
		
	}
	
    
	
	
	
	
	
	
	public void onPause()
    {
		Log.e("CameraPreview", "onPause()");
    	//mCamera.stopPreview();
    	
    }

	@SuppressWarnings("deprecation")
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
	{
		
		Log.e("CameraPreview", "surfaceChanged");
	    Parameters parameters;
		
	    parameters = mCamera.getParameters();
		// Set the camera preview size
		parameters.setPreviewSize(PreviewSizeWidth, PreviewSizeHeight);
		
		//parameters.set("fast-fps-mode", 2); // 2 for 120fps
		//parameters.setPreviewFpsRange(5000,30000);
		
		imageFormat = parameters.getPreviewFormat();
		
	/*	List<int[]> frameRates = parameters.getSupportedPreviewFpsRange();
		int last = frameRates.size();
		//Log.e("MyRealTimeImageProcessing","preview sz " +last );
		
	    int minFps = (frameRates.get(0))[Parameters.PREVIEW_FPS_MIN_INDEX];
	    int maxFps = (frameRates.get(0))[Parameters.PREVIEW_FPS_MAX_INDEX];
	    parameters.setPreviewFpsRange( minFps, maxFps);*/
	   
	  //  Log.e("MyRealTimeImageProcessing","preview fps: " + minFps + ", " + maxFps);
		
	    //Log.e("MyRealTimeImageProcessing", "a="+b[1]);
		
	//	parameters.setPreviewFrameRate(30);
		
		mCamera.setParameters(parameters);
		mCamera.startPreview();
		
		
		
	/*	Camera.Parameters parameters = mCamera.getParameters();
		List<Camera.Size> previewSizes =  parameters.getSupportedPreviewSizes();

		int a=previewSizes.size();
		Log.e("MyRealTimeImageProcessing", ""+previewSizes.get(0));
		
	    // You need to choose the most appropriate previewSize for your app
	    Camera.Size previewSize =previewSizes.get(0); // .... select one of previewSizes here

	    parameters.setPreviewSize(previewSize.width, previewSize.height);
	   // imageFormat = parameters.getPreviewFormat();
	    mCamera.setParameters(parameters);
	    mCamera.startPreview();*/
		
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) 
	{
		Log.e("CameraPreview", "surfaceCreated");
		surfaceTexture = new SurfaceTexture(10);
		mCamera = Camera.open();
		
		try
		{
			// If did not set the SurfaceHolder, the preview area will be black.
			//mCamera.setPreviewDisplay(arg0);
			mCamera.setPreviewTexture(surfaceTexture);
			
			//mCamera.setPreviewCallback(previewCallback);
			mCamera.setPreviewCallback(this);
		} 
		catch (IOException e)
		{
			mCamera.release();
			mCamera = null;
		}
		
	/*	parent.runOnUiThread(new Runnable(){
	        public void run() {
	        	
	        	mCamera = Camera.open();
	       
		
		
		try
		{
			// If did not set the SurfaceHolder, the preview area will be black.
			//mCamera.setPreviewDisplay(arg0);
			mCamera.setPreviewTexture(surfaceTexture);
			
			//mCamera.setPreviewCallback(previewCallback);
			mCamera.setPreviewCallback(new PreviewCallback() {
	        	
	        	public void onPreviewFrame( byte[] arg0, Camera arg1) 
	        	{
	        		
	        		
	        		mCamera.addCallbackBuffer(arg0);
	        		
	        		if (imageFormat == ImageFormat.NV21)
	                {
	        			//We only accept the NV21(YUV420) format.
	        			if ( !bProcessing )
	        			{
	        				FrameData = arg0;
	        				mHandler.post(DoImageProcessing);
	        			   
	        			}
	        			
	                }
	        		
	        		
	        	}
	        });
		} 
		catch (IOException e)
		{
			mCamera.release();
			mCamera = null;
		}
	        }
		
		});*/
		//mCameraThread = new HandlerThread();
       // mCameraThread.start();
     /*   mCameraHandler = new Handler(Looper.getMainLooper());
		
        mCameraHandler.post(new Runnable() {
	        @SuppressWarnings("deprecation")
			@Override
	        public void run() {
	            try {
	            	Log.e("CameraPreview", "surfaceCreated");
	                mCamera = Camera.open();
		
	                
	         // If did not set the SurfaceHolder, the preview area will be black.
			//mCamera.setPreviewDisplay(arg0);
			mCamera.setPreviewTexture(surfaceTexture);
			
	        mCamera.setPreviewCallback(new PreviewCallback() {
	        	
	        	public void onPreviewFrame( byte[] arg0, Camera arg1) 
	        	{
	        		
	        		
	        		mCamera.addCallbackBuffer(arg0);
	        		
	        		if (imageFormat == ImageFormat.NV21)
	                {
	        			//We only accept the NV21(YUV420) format.
	        			if ( !bProcessing )
	        			{
	        				FrameData = arg0;
	        				mHandler.post(DoImageProcessing);
	        			   
	        			}
	        			
	                }
	        		
	        		
	        	}
	        });
	        	
	        		//	mCamera.setPreviewCallbackWithBuffer(this);
	        		} 
	        		catch (IOException e)
	        		{
	        			mCamera.release();
	        			mCamera = null;
	        		}
	            	
	        }
		});*/
		
		
		
	}

	
	//@Override
	public void surfaceDestroyed(SurfaceHolder arg0) 
	{
		Log.e("CameraPreview", "surfaceDestroyed");
    	//mCamera.setPreviewCallback(null);
		//mCamera.stopPreview();
		//mCamera.release();
		//mCamera = null;
		
		
			 
		
		
	}
	
	
	
	

	
	
	
	
	
	public void onResume()
    {
		
		Log.e("CameraPreview", "onResume");
		
		mCamera.setPreviewCallback(null);
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
    	
    }

	//
	// Native JNI 
	//
	
	public native int[] ImageProcessing(int width, int height, 
    		byte[] NV21FrameData, int [] pixels,int minh,int maxh,int mins,int maxs,int minv,int maxv,int px1,int py1,int px2,int py2,int px3,int py3,int px4,int py4,int x,int y,int sminh,int smaxh,int smins,int smaxs,int sminv,int smaxv);
	public native int  intEnableDebug(int enabled);
	public native int 		intCreate(String dev, int kb, int mouse);
	public native void		intClose(int fd);
	public native int		intSendEvent(int fd, int type, int code, int value);
    
    static 
    {
        System.loadLibrary("ImageProcessing");
       
    }
    
    private Runnable DoImageProcessing = new Runnable() 
    {
        public void run() 
        {
        	
        	//Log.e("MyRealTimeImageProcessing", "DoImageProcessing():");
        	bProcessing = true;
        	//ImageProcessing(PreviewSizeWidth, PreviewSizeHeight, FrameData, pixels,minh,maxh,mins,maxs,minv,maxv,sminh,smaxh,smins,smaxs,sminv,smaxv,pminh,pmaxh,pmins,pmaxs,pminv,pmaxv,lx,ly);
        	// ImageProcessing(PreviewSizeWidth, PreviewSizeHeight, FrameData, pixels);
       
        	
        	//int ostatus=0,status=0;
        	
        	final int[] r  =	ImageProcessing(PreviewSizeWidth, PreviewSizeHeight, FrameData, pixels,minh,maxh,mins,maxs,minv,maxv,p1x,p1y,p2x,p2y,p3x,p3y,p4x,p4y,x,y,sminh,smaxh,smins,smaxs,sminv,smaxv);
                
        	bitmap.setPixels(pixels, 0, PreviewSizeWidth, 0, 0, PreviewSizeWidth, PreviewSizeHeight);
        	MyCameraPreview.setImageBitmap(bitmap);
                
            bProcessing = false;
            
           // if(r[8]==4 )
            if(r[8]==4 && r[9]==0 && r[10]==0)
            {
            	//Log.e("CameraPreview", "in_if_block");
                p1x=r[0];
                p1y=r[1];
                p2x=r[2];
                p2y=r[3];
                p3x=r[4];
                p3y=r[5];
                p4x=r[6];
                p4y=r[7];
            }
            
            x=r[9];
            y=r[10];
          //  Log.e("CameraPreview", "out_if_block"); 
          /*  Canvas canvas=new Canvas(bitmap);
            Bitmap	img;
            img = BitmapFactory.decodeResource(parent.getResources(), R.drawable.ic_launcher);
            canvas.drawBitmap(img,r[0],r[1],null); 
            canvas.drawBitmap(img,r[2],r[3],null);
            canvas.drawBitmap(img,r[4],r[5],null);
            canvas.drawBitmap(img,r[6],r[7],null);*/
            
         /*   float a=p1x-p4x;
	   	    float b=p2y-p1y;
	   		float c=1180/a;
	   		float d=768/b;
	   		float xf=(r[9]-p4x)*c;
	      	float yf=(r[10]-p4y)*d;
	      	//int xi=(int) xf;
	      	//int yi=(int) yf;
	      	int xi=(int) (768-yf);
	      	int yi=(int) xf;*/
            
         /*   p1x=768-r[1];
            p1y=r[0];
            p2x=768-r[3];
            p2y=r[2];
            p3x=768-r[5];
            p3y=r[4];
            p4x=768-r[7];
            p4y=r[6];*/
            
         
            
         /*   float a=r[0]-r[6];
	   	    float b=r[3]-r[1];
	   		float c=1180/a;
	   		float d=768/b;
	   		float xf=(r[8]-r[6])*c;
	      	float yf=(r[9]-r[7])*d;
	      	int xi=(int) (768-yf);
	      	int yi=(int) xf;
	      	
	      	int xj=(xi*2);
	      	int yj=(yi*2);*/
	      	
	      //	int xt=768-r[9];
	      //	int yt=r[8];
	      
	      	
	      	   
	      /*	   status=r[2];
			if(status==1 && cstatus==1)
			{
				//......click
			//	Log.e("CameraPreview", "in_click_block");
				cstatus=0;
				int xp=xpt,yp=ypt;
			  //  int xp=1535-ypt,yp=xpt;
			//	Log.e("CameraPreview", "in_click_block...(xp,yp)"+xp+","+yp);
				try {
					//Runtime.getRuntime().exec("adb shell");
					//Runtime.getRuntime().exec("su");
					//Runtime.getRuntime().exec("chmod 666 /dev/input/event2");
					try {
    					doCmds("chmod 666 /dev/input/event2");
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 57 11");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 53 "+xp);
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 54 "+yp);
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 58 44");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 48 4");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 0 0 0");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 53 "+(xp+1));
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 58 45");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 0 0 0");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 57 4294967295");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 0 0 0");
					
					
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(status==0 && ostatus==2)
		    {
		    	//Log.e("CameraPreview", "in_hide_block");
		    	cstatus=1;
		    }
			else if(status==1)
		    {
		    	//Log.e("CameraPreview", "in_curshow_block");
		    	ostatus=2;
		   
		    	xpt=(r[0]*2);
		    	ypt=(r[1]*2);
		    //	Log.e("CameraPreview", "in_curshow_block...(x,y)"+r[0]+","+r[1]);
		    //	Log.e("CameraPreview", "in_curshow_block...(xpt,ypt)"+xpt+","+ypt);
		    	
		    	
		    }
			else if(status==0)
		    {
		    	//Log.e("CameraPreview", "in_init_block");
		    	ostatus=1;
		    }*/
            
           // TextView tv=(TextView)parent.findViewById(R.id.textView1);
           // tv.setText("(sh1,sh2)"+sminh+","+smaxh+"(ss1,ss2)"+smins+","+smaxs+"(sv1,sv2)"+sminv+","+smaxv);
            
            
            Button b2=(Button)parent.findViewById(R.id.test);
            b2.setOnClickListener(new OnClickListener() {
    			public void onClick(View v) {
    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    			
    				
    				borderst=1;
    				Intent newActivity = new Intent(Intent.ACTION_MAIN); 
    				newActivity.addCategory(Intent.CATEGORY_HOME);
    				newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    				parent.startActivity(newActivity);
    				
    					
    				
    			}
    			});
            
            Button b5=(Button)parent.findViewById(R.id.paint);
            b5.setOnClickListener(new OnClickListener() {
    			public void onClick(View v) {	
    				
    				//Callcamera.cmdHideCursor();
    				m_bRunning = false;
    				mCamera.setPreviewCallback(null);
    		    	mCamera.stopPreview();
    		    	mCamera.release();
    		    	mCamera = null;
    		    	cmdTurnCursorServiceOff();
    		    	
    				Intent intent = new Intent();
    				intent.setClassName("com.example.campaint", "com.example.campaint.MainActivity");
    				
    				intent.putExtra("h1", minh);
    		    	intent.putExtra("h2", maxh);
    		    	intent.putExtra("s1", mins);
    		    	intent.putExtra("s2", maxs);
    		    	intent.putExtra("v1", minv);
    		    	intent.putExtra("v2", maxv);
    				
    		    	intent.putExtra("sh1", sminh);
    		    	intent.putExtra("sh2", smaxh);
    		    	intent.putExtra("ss1", smins);
    		    	intent.putExtra("ss2", smaxs);
    		    	intent.putExtra("sv1", sminv);
    		    	intent.putExtra("sv2", smaxv);
    				
    		    	parent.startActivity(intent);
    				parent.finish();
    			}
    		});
            
            double cx = Math.sqrt(Math.pow( r[11]-r[9], 2) + Math.pow( r[12]-r[10], 2));
            
            int xp=(r[9]*2),yp=(r[10]*2);
            
            if(cx>100 && cx<250)
            {
            	try {
					//Runtime.getRuntime().exec("adb shell");
					//Runtime.getRuntime().exec("su");
					//Runtime.getRuntime().exec("chmod 666 /dev/input/event2");
					try {
    					doCmds("chmod 666 /dev/input/event2");
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 57 11");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 53 "+xp);
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 54 "+yp);
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 58 44");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 48 4");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 0 0 0");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 53 "+(xp+1));
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 58 45");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 0 0 0");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 3 57 4294967295");
					Runtime.getRuntime().exec("sendevent /dev/input/event2 0 0 0");
					
					
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            
            if(borderst==0)
            {
            	//Log.e("CameraPreview", "if");
            	
            	/*Runnable nthread = new Runnable()
                {
                    @Override
                    public void run()
                    {
                      //  while (true)
                      //  {
                        // TODO add code to refresh in background
                       m_CurService.Update(r[0],r[1], true,paintColor,1180,768,parent);
                            try
                            {

                                Thread.sleep(1000);// sleeps 1 second
                                //Do Your process here.
                            } catch (InterruptedException e){
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        }
                  //  }

                }
            };
            nthread.run();*/
            	
            
            m_CurService.Update(r[9],r[10],r[11],r[12], true,paintColor,1180,768,parent,cx,p1x,p1y,p2x,p2y,p3x,p3y,p4x,p4y);	
        //  m_CurService.Update(r[9],r[10],r[11],r[12], true,paintColor,1180,768,parent,cx,(768-p1y),p1x,(768-p2y),p2x,(768-p3y),p3x,(768-p4y),p4x);
            
            }
            else
            {
            	//Log.e("CameraPreview", "else");
            // m_CurService.Update(r[9],r[10],r[11],r[12], true,paintColor,768,1180,parent,cx,(768-p1y),p1x,(768-p2y),p2x,(768-p3y),p3x,(768-p4y),p4x);
            m_CurService.Update(r[9],r[10],r[11],r[12], true,paintColor,768,1180,parent,cx,p1x,p1y,p2x,p2y,p3x,p3y,p4x,p4y);	
            }
            
            
            
          //  Singleton.getInstance().m_CurService.Update(p1x,p1y,p2x,p2y,p3x,p3y,p4x,p4y, true);
           // Singleton.getInstance().m_CurService.Update(r[8],r[9],r[0],r[1],r[2],r[3],r[4],r[5],r[6],r[7], true);
          //  m_CurService.Update(xi,yi,p1x,p1y,p2x,p2y,p3x,p3y,p4x,p4y, true);
           // Singleton.getInstance().m_CurService.Update(463,324,p2x,p2y,p3x,p3y,p4x,p4y, true);
           // Singleton.getInstance().m_CurService.Update(r[4],r[5], true);
           // Singleton.getInstance().m_CurService.Update(r[6],r[7], true);
			//Singleton.getInstance().m_CurService.Update(768-ly,lx, true);
           //m_CurService.Update(768-r[1],r[0], true);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Button b3=(Button)parent.findViewById(R.id.border);
            b3.setOnClickListener(new OnClickListener() {
    			public void onClick(View v) {
    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    			
    				final Dialog brushDialog = new Dialog(parent);
    				
    				brushDialog.setContentView(R.layout.brush_chooser);
    				
    				Window window = brushDialog.getWindow();
    				WindowManager.LayoutParams wlp = window.getAttributes();

    				wlp.gravity = Gravity.LEFT;
    				
    				//wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
    				window.setAttributes(wlp);
    				//listen for clicks on size buttons
    				ImageButton a1=(ImageButton)brushDialog.findViewById(R.id.a1);
    	            a1.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFF660000;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            ImageButton a2=(ImageButton)brushDialog.findViewById(R.id.a2);
    	            a2.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFFFF0000;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            ImageButton a3=(ImageButton)brushDialog.findViewById(R.id.a3);
    	            a3.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFFFF6600;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            ImageButton a4=(ImageButton)brushDialog.findViewById(R.id.a4);
    	            a4.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    			
    	    				paintColor = 0xFFFFCC00;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            
    	            ImageButton a5=(ImageButton)brushDialog.findViewById(R.id.a5);
    	            a5.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFF009900;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            
    	            ImageButton a6=(ImageButton)brushDialog.findViewById(R.id.a6);
    	            a6.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFF009999;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            
    	            ImageButton b1=(ImageButton)brushDialog.findViewById(R.id.b1);
    	            b1.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFF0000FF;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            
    	            ImageButton b2=(ImageButton)brushDialog.findViewById(R.id.b2);
    	            b2.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFF990099;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            
    	            ImageButton b3=(ImageButton)brushDialog.findViewById(R.id.b3);
    	            b3.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFFFF6666;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            
    	            
    	            ImageButton b4=(ImageButton)brushDialog.findViewById(R.id.b4);
    	            b4.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFFFFFFFF;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            
    	            ImageButton b5=(ImageButton)brushDialog.findViewById(R.id.b5);
    	            b5.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFF787878;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            

    	            ImageButton b6=(ImageButton)brushDialog.findViewById(R.id.b6);
    	            b6.setOnClickListener(new OnClickListener() {
    	    			public void onClick(View v) {
    	    				//Toast.makeText(parent.getApplicationContext(),"button clicked",Toast.LENGTH_SHORT).show();
    	    			
    	    				
    	    				paintColor = 0xFF000000;
    	    				brushDialog.dismiss();
    	    				
    	    			}
    	    			});
    	            
    				//show and wait for user interaction
    				brushDialog.show();
    				
    					
    				
    			}
    			});	
            
           
		/*	try {
				//Runtime.getRuntime().exec("su");
				//Runtime.getRuntime().exec("chmod 666 /dev/input/event2");
				Runtime.getRuntime().exec("sendevent /dev/input/event2 3 57 11");
				Runtime.getRuntime().exec("sendevent /dev/input/event2 3 53 "+xj);
				Runtime.getRuntime().exec("sendevent /dev/input/event2 3 54 "+yj);
				Runtime.getRuntime().exec("sendevent /dev/input/event2 3 58 44");
				Runtime.getRuntime().exec("sendevent /dev/input/event2 3 48 4");
				Runtime.getRuntime().exec("sendevent /dev/input/event2 0 0 0");
				Runtime.getRuntime().exec("sendevent /dev/input/event2 3 53 "+(xj+1));
				Runtime.getRuntime().exec("sendevent /dev/input/event2 3 58 45");
				Runtime.getRuntime().exec("sendevent /dev/input/event2 0 0 0");
				Runtime.getRuntime().exec("sendevent /dev/input/event2 3 57 4294967295");
				Runtime.getRuntime().exec("sendevent /dev/input/event2 0 0 0");
				
				
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	*/
			
		
            
           
            
			
        }		
    };									 
    
    
       
    public static void doCmds(String string) throws Exception {
	    Process process = Runtime.getRuntime().exec("su");
	    DataOutputStream os = new DataOutputStream(process.getOutputStream());

	   // for (String tmpCmd : string) {
	            os.writeBytes(string+"\n");
	   // }

	    os.writeBytes("exit\n");
	    os.flush();
	    os.close();

	    process.waitFor();
	}




	/*@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}   */
   
  
    public void cmdTurnCursorServiceOff() {
		Intent i = new Intent();
     i.setAction("com.example.ldetect.CursoeService");
     parent.stopService(i);
	}
  





    
}
