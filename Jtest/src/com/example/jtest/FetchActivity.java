package com.example.jtest;

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
	  
	    return mRgba;
	}

	private final Runnable startActivityRunnable = new Runnable() {
		
	    @Override
	    public void run() {
	    	
	    	Intent intent = new Intent();
	    	intent.setClass(FetchActivity.this,MainActivity.class);
	   
	        startActivity(intent);
	            
	        
	    }

	    };


	
}
