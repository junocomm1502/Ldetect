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

public class FetchActivity extends Activity implements CvCameraViewListener2 {
	
	private static final String TAG = null;
	// int hueChannelValue;
	// int saturationChannelValue;
	// int valueChannelValue;
	//int[] arh=new int[360];
	int minh=0,maxh=0,mins=0,maxs=0,minv=0,maxv=0;
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
	    setContentView(R.layout.activity_fetch);
	    mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloOpenCvView);
	    mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
	    mOpenCvCameraView.setCvCameraViewListener(this);
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
	    
		
		//Core.rectangle(mRgba, new Point(0,0),  new Point(100,100), new Scalar(0,0,255), 4);
		//Core.rectangle(mRgba,new Point(352,700), new Point(422,770), new Scalar(0,0,255,255), 4);
		
//..........................for hand		
		Mat HSV = new Mat(inputFrame.rgba().height(),inputFrame.rgba().width(),CvType.CV_8UC4);
		Imgproc.cvtColor(mRgba, HSV, Imgproc.COLOR_RGB2HSV, 4);
		
		/*Core.rectangle(mRgba, new Point(100,350),  new Point(150,400), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba,new Point(270,350), new Point(320,400), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba,new Point(440,350), new Point(490,400), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba,new Point(610,350), new Point(660,400), new Scalar(0,0,255), 4);
		
		Core.rectangle(mRgba,new Point(440,200), new Point(490,250), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba,new Point(440,50), new Point(490,100), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba,new Point(610,200), new Point(660,250), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba,new Point(610,50), new Point(660,100), new Scalar(0,0,255), 4);*/
		
		Core.rectangle(mRgba, new Point(180,315),  new Point(210,345), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba, new Point(260,315),  new Point(290,345), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba, new Point(340,315),  new Point(370,345), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba, new Point(420,315),  new Point(450,345), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba, new Point(500,315),  new Point(530,345), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba, new Point(600,315),  new Point(630,345), new Scalar(0,0,255), 4);
		
		Core.rectangle(mRgba, new Point(500,255),  new Point(530,285), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba, new Point(500,195),  new Point(530,225), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba, new Point(500,135),  new Point(530,165), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba, new Point(600,255),  new Point(630,285), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba, new Point(600,195),  new Point(630,225), new Scalar(0,0,255), 4);
		Core.rectangle(mRgba, new Point(600,135),  new Point(630,165), new Scalar(0,0,255), 4);
		
		
		
	/*	int[] arh=new int[360];
		int[] ars=new int[360];
		int[] arv=new int[360];
		int c=0;
		
		int x1=100,y1=350,fy=y1;
		for(int i=0;i<29;i++)
		{
	    	y1=fy;
	    	for(int j=0;j<4;j++)
	    	{

		//Core.rectangle(mRgba,new Point(x1,y1), new Point(x1,y1), new Scalar(255,0,0), 2);
		
		
		
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

		y1=y1+15;

		}

		x1=x1+20;

	}
		
		int x2=440,y2=50,ty=y2;
		for(int k=0;k<12;k++)
		{
	    	y2=ty;
	    	for(int l=0;l<20;l++)
	    	{

		//Core.rectangle(mRgba,new Point(x2,y2), new Point(x2,y2), new Scalar(0,255,0), 2);

		double[] pixelValue1 = HSV.get(y2,x2);
		int hueChannelValue1=(int) pixelValue1[0];
		int saturationChannelValue1=(int) pixelValue1[1];
		int valueChannelValue1=(int) pixelValue1[2];
		//Log.i("ch",  "H2: "+hueChannelValue1);
		//Log.i("ch",  "S: "+saturationChannelValue1);
		//Log.i("ch",  "V: "+valueChannelValue1);
		
		arh[c]=hueChannelValue1;
	    ars[c]=saturationChannelValue1;
	    arv[c]=valueChannelValue1;
	    c=c+1;
		
		y2=y2+15;

		}

		x2=x2+20;

	}*/
		
		
		
		int[] arh=new int[260];
		int[] ars=new int[260];
		int[] arv=new int[260];
		int c=0;
		
		int x1=180,y1=315,fy=y1;
		for(int i=0;i<31;i++)
		{
	    	y1=fy;
	    	for(int j=0;j<4;j++)
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

		y1=y1+10;

		}

		x1=x1+15;

	}
		
		int x2=500,y2=135,ty=y2;
		for(int k=0;k<9;k++)
		{
	    	y2=ty;
	    	for(int l=0;l<15;l++)
	    	{

		Core.rectangle(mRgba,new Point(x2,y2), new Point(x2,y2), new Scalar(0,255,0), 2);

		double[] pixelValue1 = HSV.get(y2,x2);
		int hueChannelValue1=(int) pixelValue1[0];
		int saturationChannelValue1=(int) pixelValue1[1];
		int valueChannelValue1=(int) pixelValue1[2];
		//Log.i("ch",  "H2: "+hueChannelValue1);
		//Log.i("ch",  "S: "+saturationChannelValue1);
		//Log.i("ch",  "V: "+valueChannelValue1);
		
		arh[c]=hueChannelValue1;
	    ars[c]=saturationChannelValue1;
	    arv[c]=valueChannelValue1;
	    c=c+1;
		
		y2=y2+12;

		}

		x2=x2+16;

	}
		
		
		// for h

	    for(int x=0; x<259; x++)
	   {
	       for(int y=0; y<259-1; y++)
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
	    for(int i=0;i<129;i++)
	    {
	    	count=0;
	      for(int j=1;j<=129;j++)
	      {

	    	if(arh[i]==arh[j])
	    	{
	    		count=count+1;


	    	}
	     }
	    	if(count>mcount)
	    	{
	    		minh=arh[i];
	    		mcount=count;
	    	}


	    }
	    //std::cout<<"--"<<max<<"--";
	    Log.i("ch",  "minh "+minh);

	    int count1,mcount1=0;
	    for(int k=130;k<259;k++)
	        {
	        	count1=0;
	          for(int l=130;l<=259;l++)
	          {

	        	if(arh[k]==arh[l])
	        	{
	        		count1=count1+1;


	        	}
	         }
	        	if(count1>mcount1)
	        	{
	        		maxh=arh[k];
	        		mcount1=count1;
	        	}


	        }
	        //std::cout<<"--"<<max1<<"--";
	          Log.i("ch",  "maxh "+maxh);


	    
	 // for s

	    for(int x=0; x<259; x++)
	   {
	       for(int y=0; y<259-1; y++)
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
	    for(int i=0;i<129;i++)
	    {
	    	counts=0;
	      for(int j=1;j<=129;j++)
	      {

	    	if(arh[i]==arh[j])
	    	{
	    		counts=counts+1;


	    	}
	     }
	    	if(counts>mcounts)
	    	{
	    		mins=ars[i];
	    		mcounts=counts;
	    	}


	    }
	    //std::cout<<"--"<<max<<"--";
	    Log.i("ch",  "mins "+mins);

	    int counts1,mcounts1=0;
	    for(int k=130;k<259;k++)
	        {
	        	counts1=0;
	          for(int l=130;l<=259;l++)
	          {

	        	if(ars[k]==ars[l])
	        	{
	        		counts1=counts1+1;


	        	}
	         }
	        	if(counts1>mcounts1)
	        	{
	        		maxs=ars[k];
	        		mcounts1=counts1;
	        	}


	        }
	        //std::cout<<"--"<<max1<<"--";
	          Log.i("ch",  "maxs "+maxs);
	    
	    
	    
	 // for v ...........................................................

	    for(int x=0; x<259; x++)
	   {
	       for(int y=0; y<259-1; y++)
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
	    for(int i=0;i<129;i++)
	    {
	    	countv=0;
	      for(int j=1;j<=129;j++)
	      {

	    	if(arv[i]==arv[j])
	    	{
	    		countv=countv+1;


	    	}
	     }
	    	if(countv>mcountv)
	    	{
	    		minv=arv[i];
	    		mcountv=countv;
	    	}


	    }
	    //std::cout<<"--"<<max<<"--";
	    Log.i("ch",  "minv "+minv);

	    int countv1,mcountv1=0;
	    for(int k=130;k<259;k++)
	        {
	        	countv1=0;
	          for(int l=130;l<=259;l++)
	          {

	        	if(arv[k]==arv[l])
	        	{
	        		countv1=countv1+1;


	        	}
	         }
	        	if(countv1>mcountv1)
	        	{
	        		maxv=arv[k];
	        		mcountv1=countv1;
	        	}


	        }
	        //std::cout<<"--"<<max1<<"--";
	          Log.i("ch",  "maxv "+maxv);


	    
	    
		
		//Log.i("ch",  "c: "+c);
		
		
		/*double[] pixelValue = mRgba.get(0,0);
		double redChannelValue=pixelValue[0];
		double greenChannelValue=pixelValue[1];
		double blueChannelValue=pixelValue[2];
		Log.i("ch",  "red channel value: "+redChannelValue);
		Log.i("ch",  "green channel value: "+greenChannelValue);
		Log.i("ch",  "blue channel value: "+blueChannelValue);
		
		double[] pixelValue1 = HSV.get(0,50);
		 hueChannelValue=pixelValue1[0];
		saturationChannelValue=pixelValue1[1];
		 valueChannelValue=pixelValue1[2];
		Log.i("ch",  "H: "+hueChannelValue);
		Log.i("ch",  "S: "+saturationChannelValue);
		Log.i("ch",  "V: "+valueChannelValue);*/
		
		
		
	    return mRgba;
	}

	private final Runnable startActivityRunnable = new Runnable() {
		
	    @Override
	    public void run() {
	    	
	    	Intent intent = new Intent();
	    	intent.setClass(FetchActivity.this,Shaval.class);
	    /*	Log.e("ch",  "fh: "+minh);
	    	Log.e("ch",  "fh: "+maxh);
	    	Log.e("ch",  "fs: "+mins);
	    	Log.e("ch",  "fs: "+maxs);
	    	Log.e("ch",  "fv: "+minv);
	    	Log.e("ch",  "fv: "+maxv);*/
	    	intent.putExtra("m1", minh);
	    	intent.putExtra("m2", maxh);
	    	intent.putExtra("m3", mins);
	    	intent.putExtra("m4", maxs);
	    	intent.putExtra("m5", minv);
	    	intent.putExtra("m6", maxv);
	    	Bundle extras = new Bundle();
	        extras.putString("status", "Data Received!");
	        intent.putExtras(extras);
	        startActivity(intent);
	            
	        
	    }

	    };


	
}
