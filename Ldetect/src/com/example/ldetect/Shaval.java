package com.example.ldetect;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
//import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;



import org.opencv.core.Point;


import android.app.Activity;
import android.content.Intent;
//import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;


public class Shaval extends Activity implements CvCameraViewListener2 {
	
	private static final String TAG = null;
	// int hueChannelValue;
	// int saturationChannelValue;
	// int valueChannelValue;
	//int[] arh=new int[360];
	int sminh=0,smaxh=0,smins=0,smaxs=0,sminv=0,smaxv=0;
	int minh,maxh,mins,maxs,minv,maxv;
	
	 private final Handler handler = new Handler();

private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
    @Override
    public void onManagerConnected(int status) {
        switch (status) {
            case LoaderCallbackInterface.SUCCESS:
            {
                Log.e(TAG, "OpenCV loaded successfully");
                mOpenCvCameraView.enableView();
            } break;
            default:
            {
                super.onManagerConnected(status);
            } break;
        }
    }
};

@Override
public void onResume()
{
    super.onResume();
    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_6, this, mLoaderCallback);
    handler.postDelayed(startActivityRunnable,10000);
}


private CameraBridgeViewBase mOpenCvCameraView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		Log.e(TAG, "called onCreate");
		//setContentView(R.layout.activity_fetchvalue);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    setContentView(R.layout.activity_shaval);
	    mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloOpenCvView);
	    mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
	    mOpenCvCameraView.setCvCameraViewListener(this);
	    
        Intent intent1 = getIntent();
	    
	     minh = intent1.getIntExtra("m1",0);
	     maxh = intent1.getIntExtra("m2",0);
	     mins = intent1.getIntExtra("m3",0);
	     maxs = intent1.getIntExtra("m4",0);
	     minv = intent1.getIntExtra("m5",0);
	     maxv = intent1.getIntExtra("m6",0);
	     
	        
	}

	@Override
	public void onPause()
	{
	    super.onPause();
	    if (mOpenCvCameraView != null)
	        mOpenCvCameraView.disableView();
	}

	public void onDestroy() {
	    super.onDestroy();
	    if (mOpenCvCameraView != null)
	        mOpenCvCameraView.disableView();
	}

	public void onCameraViewStarted(int width, int height) {
	}

	public void onCameraViewStopped() {
	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {   
	    
Mat mRgba=inputFrame.rgba(); 
	    
		
		
		Mat HSV = new Mat(inputFrame.rgba().height(),inputFrame.rgba().width(),CvType.CV_8UC4);
		Imgproc.cvtColor(mRgba, HSV, Imgproc.COLOR_RGB2HSV, 4);
		
		
Core.rectangle(mRgba, new Point(180,315),  new Point(210,345), new Scalar(0,0,255), 4);
		
		
		
		
	
		
		
		
		int[] arh=new int[100];
		int[] ars=new int[100];
		int[] arv=new int[100];
		int c=0;
		
		int x1=180,y1=315,fy=y1;
		for(int i=0;i<10;i++)
		{
	    	y1=fy;
	    	for(int j=0;j<10;j++)
	    	{

		Core.rectangle(mRgba,new Point(x1,y1), new Point(x1,y1), new Scalar(255,0,0), 2);
		
		
		
		double[] pixelValue = HSV.get(y1,x1);
		int hueChannelValue=(int) pixelValue[0];
		int saturationChannelValue=(int) pixelValue[1];
		int valueChannelValue=(int) pixelValue[2];
		//Log.i("ch",  "H1: "+hueChannelValue);
		//Log.i("ch",  "S: "+saturationChannelValue);
		//Log.i("ch",  "V: "+valueChannelValue);
		
		arh[c]=hueChannelValue;
	    ars[c]=saturationChannelValue;
	    arv[c]=valueChannelValue;
	    c=c+1;

		y1=y1+3;

		}

		x1=x1+3;

	}
		//Log.e("ch",  "c: "+c);
		
		

		
		
		// for h

	    for(int x=0; x<99; x++)
	   {
	       for(int y=0; y<99-1; y++)
	      {
	           if(arh[y]>arh[y+1])
	          {
	              int temp = arh[y+1];
	              arh[y+1] = arh[y];
	              arh[y] = temp;

	          }
	       }
	    }                                   

	  /*  for(int i =0; i<356; i++)
	   {
	        //cout << arr[i] << " ";
	    	Log.i("ch",  "hn "+i);
	        Log.i("ch",  "h "+arh[i]);
	   }*/

		
	   int count,mcount=0;
	    for(int i=0;i<50;i++)
	    {
	    	count=0;
	      for(int j=1;j<=50;j++)
	      {

	    	if(arh[i]==arh[j])
	    	{
	    		count=count+1;


	    	}
	     }
	    	if(count>mcount)
	    	{
	    		sminh=arh[i];
	    		mcount=count;
	    	}


	    }
	    //std::cout<<"--"<<max<<"--";
	 //   Log.e("ch",  "pminh "+pminh);

	    int count1,mcount1=0;
	    for(int k=51;k<99;k++)
	        {
	        	count1=0;
	          for(int l=51;l<=99;l++)
	          {

	        	if(arh[k]==arh[l])
	        	{
	        		count1=count1+1;


	        	}
	         }
	        	if(count1>mcount1)
	        	{
	        		smaxh=arh[k];
	        		mcount1=count1;
	        	}


	        }
	        //std::cout<<"--"<<max1<<"--";
	        //  Log.e("ch",  "pmaxh "+pmaxh);


	    
	 // for s

	    for(int x=0; x<99; x++)
	   {
	       for(int y=0; y<99-1; y++)
	      {
	           if(ars[y]>ars[y+1])
	          {
	              int temp = ars[y+1];
	              ars[y+1] = ars[y];
	              ars[y] = temp;

	          }
	       }
	    }                            

	  /*  for(int i =0; i<356; i++)
	   {
	        //cout << arr[i] << " ";
	    	Log.i("ch",  "sn "+i);
	        Log.i("ch",  "s "+ars[i]);
	   }*/

		
	   int counts,mcounts=0;
	    for(int i=0;i<50;i++)
	    {
	    	counts=0;
	      for(int j=1;j<=50;j++)
	      {

	    	if(arh[i]==arh[j])
	    	{
	    		counts=counts+1;


	    	}
	     }
	    	if(counts>mcounts)
	    	{
	    		smins=ars[i];
	    		mcounts=counts;
	    	}


	    }
	    //std::cout<<"--"<<max<<"--";
	   // Log.e("ch",  "pmins "+pmins);

	    int counts1,mcounts1=0;
	    for(int k=51;k<99;k++)
	        {
	        	counts1=0;
	          for(int l=51;l<=99;l++)
	          {

	        	if(ars[k]==ars[l])
	        	{
	        		counts1=counts1+1;


	        	}
	         }
	        	if(counts1>mcounts1)
	        	{
	        		smaxs=ars[k];
	        		mcounts1=counts1;
	        	}


	        }
	        //std::cout<<"--"<<max1<<"--";
	        //  Log.e("ch",  "pmaxs "+pmaxs);
	    
	    
	    
	 // for v ...........................................................

	    for(int x=0; x<99; x++)
	   {
	       for(int y=0; y<99-1; y++)
	      {
	           if(arv[y]>arv[y+1])
	          {
	              int temp = arv[y+1];
	              arv[y+1] = arv[y];
	              arv[y] = temp;

	          }
	       }
	    }                          

	 /*   for(int i =0; i<356; i++)
	   {
	        //cout << arr[i] << " ";
	    	Log.i("ch",  "vn "+i);
	        Log.i("ch",  "v "+arv[i]);
	   }*/

		
	    int countv,mcountv=0;
	    for(int i=0;i<50;i++)
	    {
	    	countv=0;
	      for(int j=1;j<=50;j++)
	      {

	    	if(arv[i]==arv[j])
	    	{
	    		countv=countv+1;


	    	}
	     }
	    	if(countv>mcountv)
	    	{
	    		sminv=arv[i];
	    		mcountv=countv;
	    	}


	    }
	    //std::cout<<"--"<<max<<"--";
	   // Log.e("ch",  "pminv "+pminv);

	    int countv1,mcountv1=0;
	    for(int k=51;k<99;k++)
	        {
	        	countv1=0;
	          for(int l=51;l<=99;l++)
	          {

	        	if(arv[k]==arv[l])
	        	{
	        		countv1=countv1+1;


	        	}
	         }
	        	if(countv1>mcountv1)
	        	{
	        		smaxv=arv[k];
	        		mcountv1=countv1;
	        	}


	        }
	        //std::cout<<"--"<<max1<<"--";
	         // Log.e("ch",  "pmaxv "+pmaxv);
	    
	    return mRgba;
	}

	private final Runnable startActivityRunnable = new Runnable() {
		
	    @Override
	    public void run() {
	    	
	    	Intent intent = new Intent();
	    	intent.setClass(Shaval.this,MainActivity.class);
	    	/*Log.e("ch",  "sh: "+minh);
	    	Log.e("ch",  "sh: "+maxh);
	    	Log.e("ch",  "ss: "+mins);
	    	Log.e("ch",  "ss: "+maxs);
	    	Log.e("ch",  "sv: "+minv);
	    	Log.e("ch",  "sv: "+maxv);*/
	    	
	    /*	Log.e("ch",  "sh: "+sminh);
	    	Log.e("ch",  "sh: "+smaxh);
	    	Log.e("ch",  "ss: "+smins);
	    	Log.e("ch",  "ss: "+smaxs);
	    	Log.e("ch",  "sv: "+sminv);
	    	Log.e("ch",  "sv: "+smaxv);*/
	    	
	    	intent.putExtra("m1", minh);
	    	intent.putExtra("m2", maxh);
	    	intent.putExtra("m3", mins);
	    	intent.putExtra("m4", maxs);
	    	intent.putExtra("m5", minv);
	    	intent.putExtra("m6", maxv);
	    	
	    	intent.putExtra("m7", sminh);
	    	intent.putExtra("m8", smaxh);
	    	intent.putExtra("m9", smins);
	    	intent.putExtra("m10", smaxs);
	    	intent.putExtra("m11", sminv);
	    	intent.putExtra("m12", smaxv);
	    	Bundle extras = new Bundle();
	        extras.putString("status", "Data Received!");
	        intent.putExtras(extras);
	        startActivity(intent);
	            
	        
	    }

	    };
}
