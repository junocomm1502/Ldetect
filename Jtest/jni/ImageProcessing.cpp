/*
*  ImageProcessing.cpp
*/
#include <jni.h>

#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc_c.h>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/core/core_c.h>
//#include <OpenCV-android-sdk/sdk/java/bin/classes/org/opencv/core>

#include <string.h>


#include <math.h>


#include <stdlib.h>
#include <unistd.h>

#include <fcntl.h>
#include <stdio.h>

#include <sys/ioctl.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <time.h>

#include <linux/fb.h>
#include <linux/kd.h>


#include "uinput.h"

using namespace std;
using namespace cv;
#include <android/log.h>

#define TAG "JNI"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG  , TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
int g_debug = 0;


extern "C" {

jintArray Java_com_example_jtest_CameraPreview_ImageProcessing(
		JNIEnv* env, jobject thiz,
		    jint width, jint height,
		    jbyteArray NV21FrameData, jintArray outPixels,jint px1,jint py1,jint px2,jint py2,jint px3,jint py3,jint px4,jint py4);






jintArray Java_com_example_jtest_CameraPreview_ImageProcessing(
		JNIEnv* env, jobject thiz,
		    jint width, jint height,
		    jbyteArray NV21FrameData, jintArray outPixels,jint px1,jint py1,jint px2,jint py2,jint px3,jint py3,jint px4,jint py4)
		{



	jbyte * pNV21FrameData = env->GetByteArrayElements(NV21FrameData, 0);
	jint * poutPixels = env->GetIntArrayElements(outPixels, 0);

	jintArray newArray = env->NewIntArray(13);
	jint *narr = env->GetIntArrayElements(newArray, NULL);

	jintArray arr = env->NewIntArray(13);
	jint *jarr = env->GetIntArrayElements(arr, NULL);








	Mat mInput(height + height/2, width, CV_8UC1, (unsigned char *)pNV21FrameData);
	Mat mResult(height, width, CV_8UC4, (unsigned char *)poutPixels);

	Mat hsv,rgba,hsv1,rgba1,hsv2,rgba2,mGray,res,rgba3;
	cv::Point far,far1;
	int x=0,y=0,ch=0;

	cvtColor(mInput,rgba,CV_YUV2BGR_NV21);

		cvtColor(rgba,mGray,CV_BGR2GRAY);
		Canny(mGray,res,100,255,5,true);

	//	LOGE("corner");

		std::vector<std::vector<cv::Point> > contours3;
		std::vector<cv::Vec4i> hierarchy3;
		cv::findContours(res, contours3, hierarchy3, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cv::Point(0, 0));
		size_t largestContour3 = 0;
				  	   for (size_t i = 1; i < contours3.size(); i++)
				  	   {
				  	      if (cv::contourArea(contours3[i]) > cv::contourArea(contours3[largestContour3]))
				  	      largestContour3 = i;
				  	   }

		//cv::drawContours(mResult, contours3, largestContour3, cv::Scalar(0, 0, 255,255), 1);

		if (!contours3.empty())
			   	{
			          y=1;
			   		  std::vector<std::vector<cv::Point> > hull3(1);
			   		  cv::convexHull(cv::Mat(contours3[largestContour3]), hull3[0], false);
			   		 // cv::drawContours(mResult, hull3, 0, cv::Scalar(0, 0, 255,255), 3);
			   		vector<vector<Point> > contours_poly(1);
			   		approxPolyDP( hull3[0], contours_poly[0],25, true );
			   		//Rect boundRect=boundingRect(contours[largestContour]);
			   		//cout<<contours_poly[0].size();
			   		Point P1=contours_poly[0][0];
			   		Point P2=contours_poly[0][1];
			   		Point P3=contours_poly[0][2];
			   		Point P4=contours_poly[0][3];

			   	if(contours_poly[0].size()==4){


                ch=4;
			   // rectangle(mResult,P1,P1,Scalar (0,255,0,255),5);
			   // rectangle(mResult,P2,P2,Scalar (0,255,0,255),5);
			   // rectangle(mResult,P3,P3,Scalar (0,255,0,255),5);
			  // rectangle(mResult,P4,P4,Scalar (0,255,0,255),5);




           //  if(xp==0 && yp==0)
          //   {
			   px1=P1.x;
			   py1=P1.y;
			   px2=P2.x;
			   py2=P2.y;
			   px3=P3.x;
			   py3=P3.y;
			   px4=P4.x;
			   py4=P4.y;

			/*   cv::circle(mResult, Point(px1,py1), 4, cv::Scalar(0, 0, 255,255), 2);
			   cv::circle(mResult, Point(px2,py2), 4, cv::Scalar(0, 0, 255,255), 2);
			   cv::circle(mResult, Point(px3,py3), 4, cv::Scalar(0, 0, 255,255), 2);
			   cv::circle(mResult, Point(px4,py4), 4, cv::Scalar(0, 0, 255,255), 2);*/
          //   }

			  // LOGE("narr-i- %d",narr[0]);
			  // LOGE("narr-j- %d",narr[1]);
			 //  LOGE("narr-i- %d",narr[4]);
			 //  LOGE("narr-j- %d",narr[5]);


			   jarr[0]=px1;
			   narr[0]=jarr[0];
			   jarr[1]=py1;
			   narr[1]=jarr[1];
			   jarr[2]=px2;
			   narr[2]=jarr[2];
			   jarr[3]=py2;
			   narr[3]=jarr[3];
			   jarr[4]=px3;
			   narr[4]=jarr[4];
			   jarr[5]=py3;
			   narr[5]=jarr[5];
			   jarr[6]=px4;
			   narr[6]=jarr[6];
			   jarr[7]=py4;
			   narr[7]=jarr[7];
			   jarr[8]=4;
			   narr[8]=jarr[8];

			   	}

			   	else
			   	{



			   		jarr[0]=0;
			   		narr[0]=jarr[0];
			   	    jarr[1]=0;
			   	    narr[1]=jarr[1];
			   		jarr[2]=0;
			   		narr[2]=jarr[2];
			   		jarr[3]=0;
			   		narr[3]=jarr[3];
			   		jarr[4]=0;
			   		narr[4]=jarr[4];
			   		jarr[5]=0;
			   		narr[5]=jarr[5];
			   		jarr[6]=0;
			   		narr[6]=jarr[6];
			   		jarr[7]=0;
			   		narr[7]=jarr[7];
			   		jarr[8]=1;
			   	    narr[8]=jarr[8];

			   	}



			   // LOGE("corner %d",ch);
			  //  LOGE("p1- %d,%d",array[0],array[1]);
			   // LOGE("narr-j- %d",narr[1]);
			   // LOGE("narr-i- %d",narr[4]);
			  //  LOGE("narr-j- %d",narr[5]);

			   	}


		//............laser pt..............

		cvtColor(mInput,rgba1,CV_YUV2BGR_NV21);



		Mat chan[3];
		split(rgba1,chan);


		Mat bin_red = (chan[2]<250)|(chan[2]>255);  // note: single '|'
		Mat bin_grn = (chan[1]<250)|(chan[1]>255);
		Mat bin_blu = (chan[0]<250)|(chan[0]>255);

		rgba1 = bin_red & bin_grn & bin_blu;



	    cv::threshold (rgba1, rgba1, 100, 255, CV_THRESH_BINARY_INV);

	    int blurSize = 5;
	    	   int elementSize = 5;
	    	   cv::medianBlur(rgba1, rgba1, blurSize);
	    	   cv::Mat element = cv::getStructuringElement(cv::MORPH_ELLIPSE, cv::Size(2 * elementSize + 1, 2 * elementSize + 1), cv::Point(elementSize, elementSize));
	    	   cv::dilate(rgba1, rgba1, element);



	    	   // Contour detection
	    	  std::vector<std::vector<cv::Point> > contours;
	    	   std::vector<cv::Vec4i> hierarchy;
	    	   cv::findContours(rgba1, contours, hierarchy, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cv::Point(0, 0));
	    	   size_t largestContour = 0;
	    	   for (size_t i = 1; i < contours.size(); i++)
	    	   {
	    	      if (cv::contourArea(contours[i]) > cv::contourArea(contours[largestContour]))
	    	      largestContour = i;
	    	   }

	    	  // cv::drawContours(img, contours, largestContour, cv::Scalar(0, 0, 255), 1);
	    	   //cv::imshow("MyWindow2", img);


	    	   if (!contours.empty())
	    	   	{
	    	   		  std::vector<std::vector<cv::Point> > hull(1);
	    	   		  cv::convexHull(cv::Mat(contours[largestContour]), hull[0], false);
	    	   		 // cv::drawContours(mResult, hull, 0, cv::Scalar(0,255, 0, 255), 3);


	    	   		  if (hull[0].size() > 2)
	    	   		 {

	    	   			x=1;
	    	   		    std::vector<int> hullIndexes;
	    	   		    cv::convexHull(cv::Mat(contours[largestContour]), hullIndexes, true);



	    	   		   cv::Moments mo = cv::moments(hull[0]);
	    	   		   cv::Point result = cv::Point(mo.m10/mo.m00 , mo.m01/mo.m00);
	    	   		/*jarr[9]=result.x;          //........................
	    	   		narr[9]=jarr[9];
	    	   		jarr[10]=result.y;
	    	   		narr[10]=jarr[10];
	    	   		jarr[11]=1;
	    	   	    narr[11]=jarr[11];*/

	    	   	 if(px3<px4)
	    	   {
	    	   	  float a=px4-px3;  //...............
	    	   	  //float b=py2-py1;
	    	   	  float b=py1-py4;
	    	   	  float c=768/a;
	    	   	  float d=1280/b;
	    	   	  float xf=(result.x-px3)*c;
	    	   	  float yf=(result.y-py3)*d;
	    	   	  int xi=(int) xf;
	    	   	  int yi=(int) yf;
	    	   	  //cv::circle(mResult, Point(smx,smy), 4, cv::Scalar(255, 0, 0,255), 2);
	    	   	  jarr[9]=xi;          //........................
	    	   	  narr[9]=jarr[9];
	    	   	  jarr[10]=yi;
	    	   	  narr[10]=jarr[10];
	    	   	  jarr[11]=1;          //........................
	    	   	  narr[11]=jarr[11];


	    	   	      }
	    	   	            else if(py3>py4)
	    	   	      {
	    	   	         	   float a=px1-px4;  //...............
	    	   	         	  //float b=py2-py1;
	    	   	         	   float b=py3-py4;
	    	   	         	   float c=768/a;
	    	   	         	   float d=1280/b;
	    	   	         	   float xf=(result.x-px4)*c;
	    	   	         	   float yf=(result.y-py4)*d;

	    	   	         	   int xi=(int) xf;
	    	   	         	   int yi=(int) yf;
	    	   	         	  // cv::circle(mResult, Point(smx,smy), 4, cv::Scalar(255, 0, 0,255), 2);
	    	   	         	   jarr[9]=xi;          //........................
	    	   	         	   narr[9]=jarr[9];
	    	   	         	   jarr[10]=yi;
	    	   	         	   narr[10]=jarr[10];
	    	   	         	   jarr[11]=1;          //........................
	    	   	         	   narr[11]=jarr[11];

	    	   	    }




	    	   		 }

	    	   		if(x==0)
	    	   			    	       {
	    	   			    		   jarr[9]=0;
	    	   			    		   narr[9]=jarr[9];
	    	   			    		   jarr[10]=0;
	    	   			    		   narr[10]=jarr[10];
	    	   			    		   jarr[11]=0;
	    	   			    		   narr[11]=jarr[11];

	    	   			    	       }


	    	   	}

	    	   else
	    	   	    	   {
	    	   	    	   		            	   jarr[9]=0;
	    	   	    	   		            	   narr[9]=jarr[9];
	    	   	    	   		            	   jarr[10]=0;
	    	   	    	   		            	   narr[10]=jarr[10];
	    	   	    	   		            	   jarr[11]=0;
	    	   	    	   		            	   narr[11]=jarr[11];
	    	   	    	   }

	    	/*   */








		env->ReleaseByteArrayElements(NV21FrameData, pNV21FrameData, 0);
		env->ReleaseIntArrayElements(outPixels, poutPixels, 0);
		env->ReleaseIntArrayElements(newArray, narr,0);
		env->ReleaseIntArrayElements(arr, jarr,0);

	return newArray;

	//return true;




}






}

