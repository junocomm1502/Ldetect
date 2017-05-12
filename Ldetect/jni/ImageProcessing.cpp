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

jintArray Java_com_example_ldetect_CameraPreview_ImageProcessing(
		JNIEnv* env, jobject thiz,
		    jint width, jint height,
		    jbyteArray NV21FrameData, jintArray outPixels,jint h1,jint h2,jint s1,jint s2,jint v1,jint v2,jint px1,jint py1,jint px2,jint py2,jint px3,jint py3,jint px4,jint py4,jint xp,jint yp,jint sh1,jint sh2,jint ss1,jint ss2,jint sv1,jint sv2);






jintArray Java_com_example_ldetect_CameraPreview_ImageProcessing(
		JNIEnv* env, jobject thiz,
		    jint width, jint height,
		    jbyteArray NV21FrameData, jintArray outPixels,jint h1,jint h2,jint s1,jint s2,jint v1,jint v2,jint px1,jint py1,jint px2,jint py2,jint px3,jint py3,jint px4,jint py4,jint xp,jint yp,jint sh1,jint sh2,jint ss1,jint ss2,jint sv1,jint sv2)
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
	int x=0,y=0,ch=0,dp=0;
	//int px1=0,py1=0,px2=0,py2=0,px3=0,py3=0,px4=0,py4=0;
	//int array[8];
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
             /*   std::vector<Point2f> quad_pts;
                std::vector<Point2f> squre_pts;
                quad_pts.push_back(Point2f(contours_poly[0][0].x,contours_poly[0][0].y));
                quad_pts.push_back(Point2f(contours_poly[0][1].x,contours_poly[0][1].y));
                quad_pts.push_back(Point2f(contours_poly[0][3].x,contours_poly[0][3].y));
                quad_pts.push_back(Point2f(contours_poly[0][2].x,contours_poly[0][2].y));
                squre_pts.push_back(Point2f(768,0));

                squre_pts.push_back(Point2f(768,1280));
                squre_pts.push_back(Point2f(0,0));
                squre_pts.push_back(Point2f(0,768));




                Mat transmtx = getPerspectiveTransform(quad_pts,squre_pts);
                rgba1 =Mat::zeros((py3-py4), (px1-px4), CV_8UC3);
                warpPerspective(mResult, rgba1, transmtx, rgba1.size());*/


			/*   LOGE("p1_x- %d",P1.x);
			   LOGE("P1_y- %d",P1.y);
			   LOGE("p4_x- %d",P4.x);
			   LOGE("P4_y- %d",P4.y);
			   LOGE("p2_x- %d",P2.x);
			   LOGE("P2_y- %d",P2.y);
			   LOGE("p3_x- %d",P3.x);
			   LOGE("P3_y- %d",P3.y);*/

			 /*  px1=768-P1.y;
			   py1=P1.x;
               px2=768-P2.y;
			   py2=P2.x;
			   px3=768-P3.y;
			   py3=P3.x;
			   px4=768-P4.y;
			   py4=P4.x;*/
             if(xp==0 && yp==0)
             {
			   px1=P1.x;
			   py1=P1.y;
			   px2=P2.x;
			   py2=P2.y;
			   px3=P3.x;
			   py3=P3.y;
			   px4=P4.x;
			   py4=P4.y;
             }
			/*   float a=P1.x-P4.x;
			   float b=P2.y-P1.y;
			   float c=1280/a;
			   float d=768/b;*/
			   //int e=150,f=150;
			/*   jarr[0]=P1.x;
			   narr[0]=jarr[0];
			   jarr[1]=P1.y;
			   narr[1]=jarr[1];
			   jarr[2]=P2.x;
			   narr[2]=jarr[2];
			   jarr[3]=P2.y;
			   narr[3]=jarr[3];
			   jarr[4]=P3.x;
			   narr[4]=jarr[4];
			   jarr[5]=P3.y;
			   narr[5]=jarr[5];
			   jarr[6]=P4.x;
			   narr[6]=jarr[6];
			   jarr[7]=P4.y;
			   narr[7]=jarr[7];*/
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

	/*	cvtColor(mInput,rgba1,CV_YUV2BGR_NV21);



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
	    	   		jarr[9]=result.x;          //........................
	    	   		narr[9]=jarr[9];
	    	   		jarr[10]=result.y;
	    	   		narr[10]=jarr[10];*/
	    	   		  // cout<<result;
	    	   		 //  cv::circle(mResult, result, 4, cv::Scalar(0, 0, 255,255), 2);
	    	   		//float c=1280/800;
	    	   		//float d=768/480;
	    	   	/*	float xf=(result.x*1.6);
	    	   		float yf=(result.y*1.6);
	    	   		int xi=(int) xf;
	    	   		int yi=(int) yf;
	    	   		jarr[0]=xi;
	    	   	    narr[0]=jarr[0];
	    	   	    jarr[1]=yi;
	    	   		narr[1]=jarr[1];
	    	   		jarr[2]=1;
	    	   	    narr[2]=jarr[2];
	    	   	 LOGE("x %d",result.x);
	    	   	 LOGE("y %d",result.y)
	    	   	 LOGE("xf %f",xf);
	    	   	 LOGE("yf %f",yf);*/
             //  if(result.x>px4 && result.x<px1 && result.y>py4 && result.y<py3)
            //   {

	    	   	/*	float a=px4-px3;  //...............
	    	   	    //float b=py2-py1;
	    	   		float b=py1-py4;
	    	   		float c=768/a;
	    	   		float d=1280/b;
	    	   		float xf=(result.x-px3)*c;
	    	   		float yf=(result.y-py3)*d;
	    	   		//int xi=(int) (768-yf);
	    	   		//int yi=(int) xf;
	    	   		int xi=(int) xf;
	    	   		int yi=(int) yf;

	    	   		jarr[9]=xi;          //........................
	    	   	    narr[9]=jarr[9];
	    	   		jarr[10]=yi;
	    	   		narr[10]=jarr[10];
	    	   		LOGE("p1 %d,%d",px1,py1);
	    	   		LOGE("p2 %d,%d",px2,py2);
	    	   		LOGE("p3 %d,%d",px3,py3);
	    	   		LOGE("p4 %d,%d",px4,py4);
	    	   		LOGE("(x,y) %d,%d",result.x,result.y);
	    	   		//LOGE("p1 %d,%d",px1);
	    	   		float a=px1-px4;     //...............
	    	   	    //float b=py2-py1;
	    	   	    float b=py3-py4;
	    	   		float c=800/a;
	    	   		float d=480/b;
	    	   		float xf=(result.x-px4)*c;
	    	   		float yf=(result.y-py4)*d;
	    	   		float xo=(xf*1.6);
	    	   		float yo=(yf*1.6);
	    	   		int xi=(int) (768-yo);
	    	   		int yi=(int) xo;
	    	   		jarr[0]=xi;
	    	   		narr[0]=jarr[0];
	    	   	    jarr[1]=yi;
	    	   		narr[1]=jarr[1];
	    	   		jarr[2]=1;
	    	   		narr[2]=jarr[2]; *///...............
           //    }

	    	   /*else
               {
            	   jarr[0]=0;
            	   narr[0]=jarr[0];
            	   jarr[1]=0;
            	   narr[1]=jarr[1];
            	   jarr[2]=2;
            	   narr[2]=jarr[2];
               }*/

	//    	   		 }
	    	//   	}

	    	/*   if(x==0)
	    	       {
	    	       	jarr[9]=0;
	    	       	narr[9]=jarr[9];
	    	       	jarr[10]=0;
	    	       	narr[10]=jarr[10];

	    	       }*/







	    	   		//..............for shadow...................



	    /*	    cvtColor(mInput,rgba1,CV_YUV2BGR_NV21);

	    	   	cvtColor(rgba1,hsv1,CV_RGB2HSV);

	   	cv::inRange(hsv1, cv::Scalar(0,s1-30,v1-30), cv::Scalar(180,s2+30,v2+30), hsv1);
	   // cv::inRange(hsv1, cv::Scalar(0,10,160), cv::Scalar(180,70,190), hsv1); //......wall
	    	   //cv::inRange(hsv1, cv::Scalar(0,15,82), cv::Scalar(180,31,111), hsv1);  //......proj
	    	   //  cv::imshow("MyWindow1", hsv);

	    	   int blurSize1 = 5;
	    	   int elementSize1 = 5;
	    	   cv::medianBlur(hsv1, hsv1, blurSize1);
	    	   cv::Mat element1 = cv::getStructuringElement(cv::MORPH_ELLIPSE, cv::Size(2 * elementSize1 + 1, 2 * elementSize1 + 1), cv::Point(elementSize1, elementSize1));
	    	   cv::dilate(hsv1, hsv1, element1);

	    	   // Contour detection
	    	   std::vector<std::vector<cv::Point> > contours1;
	    	   std::vector<cv::Vec4i> hierarchy1;
	    	   cv::findContours(hsv1, contours1, hierarchy1, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cv::Point(0, 0));
	    	   size_t largestContour1 = 0;

	    	   for (size_t i = 1; i < contours1.size(); i++)
	    	  {
	    	   	 if (cv::contourArea(contours1[i]) > cv::contourArea(contours1[largestContour1]))
	    	     largestContour1 = i;

	    	  }


	    	 //   cv::drawContours(mResult, contours, largestContour, cv::Scalar(0,0,255,255), 1);


	    	// Convex hull
	    	   if (!contours1.empty())
	   {
	      std::vector<std::vector<cv::Point> > hull1(1);
	      cv::convexHull(cv::Mat(contours1[largestContour1]), hull1[0], false);
	    // cv::drawContours(mResult, hull1, 0, cv::Scalar( 0, 255,0,255), 3);


	    if (hull1[0].size() > 2)
	  {

	    std::vector<int> hullIndexes1;
	    cv::convexHull(cv::Mat(contours1[largestContour1]), hullIndexes1, true);

	    double res1,mres1=10000,nr,mnr=10000;
	    // cv::Point far;
	    for (size_t i = 0; i < hull1[0].size(); i++)
	   {


	    if(far.x>hull1[0][i].x)
	   {
	     res1=far.x-hull1[0][i].x;
	   }
	    else
	  {
	    res1=hull1[0][i].x-far.x;
	  }
	   // mres=hull1[0][i].x-hull1[0][i+1];

	  // cout<<"\n"<<res;
	   if(res1<=mres1)
	 {
	    mres1=res1;
	   if(hull1[0][i].y<mnr)
	 {

	    mnr=hull1[0][i].y;
	    far1=hull1[0][i];
	 }

	 }

	 }

	    float a=px4-px3;  //...............
		//float b=py2-py1;
	    float b=py1-py4;
		float c=480/a;
		float d=800/b;
		float xf=(far1.x-px3)*c;
		float yf=(far1.y-py3)*d;
		float xt=xf*1.6;
		float yt=yf*1.6;
		//float yt=yf*1.475;
		int xi=(int) xt;
		int yi=(int) yt;

		jarr[11]=xi;          //........................
        narr[11]=jarr[11];
		jarr[12]=yi;
		narr[12]=jarr[12];




	   }


	  }*/


	   // ............for shadow 2...................


	    	/*  cvtColor(mInput,rgba2,CV_YUV2BGR_NV21);

	    	  cvtColor(rgba2,hsv1,CV_RGB2HSV);

	    	 // LOGE("for shadow 2");
	    	   int ly=0,sm=10000,sx=0,sy=0,cont=0;

	    	   for(int i=px3;i<px4;)
	    	 {
	    		   //LOGE("1 for");

	    	   	 for(int j=py3;j<py2;)
	    	   	{
	    	   		//LOGE("2 for");
	    	      //rectangle(img,Point (x1,y1),Point (x1,y1),Scalar (0,0,255),2);
	    	      //  cout<<"--"<<x1<<"--"<<y1;
	    	   //	rectangle(mResult,Point (i,j),Point (i,j),Scalar (0,0,255,255),2);
	    	      int	h=hsv1.at<cv::Vec3b>(j,i)[0];
	    	      int	s=hsv1.at<cv::Vec3b>(j,i)[1];
	    	      int   v=hsv1.at<cv::Vec3b>(j,i)[2];

	    	       if( s>(ss1-20) && s<(ss2+20) && v>(sv1-20) && v<(sv2+20) )
	    	       {
	    	    	 // rectangle(mResult,Point (i,j),Point (i,j),Scalar (0,0,255,255),2);
	    	    	   dp=1;
	    	    	 //  LOGE("1 if");
                      // ly=j;
                       if(j<sm)
                       {
                    	  // LOGE("2 if");
                    	   sm=j;
                    	   sx=i;
                    	   sy=j;
                       }
	    	       }
	    	   	j=j+5;
	    	       cont=cont+1;

	    	      }

                  i=i+5;

	    	     }
	    	//   LOGE("cont= %d",cont);
	    	 //  cv::circle(mResult, Point(sx,sy), 4, cv::Scalar(255, 0, 0,255), 2);

	    	   if(dp==0)
	    	   {
	    	   float a=px4-px3;  //...............
	    	   	//float b=py2-py1;
	    	   float b=py1-py4;
	    	   float c=480/a;
	    	   float d=800/b;
	    	   float xf=(sx-px3)*c;
	    	   float yf=(sy-py3)*d;
	    	   float xt=xf*1.6;
	    	   float yt=yf*1.6;
	    	   //float yt=yf*1.475;
	    	  // int xi=(int) xf;
	    	   //int yi=(int) yf;
	    	   int xi=(int) xt;
	    	   int yi=(int) yt;

	    	   jarr[11]=xi;          //........................
	    	   narr[11]=jarr[11];
	    	   jarr[12]=yi;
	    	   narr[12]=jarr[12];


	    	   }

	    	   else
	    	   {
	    		   jarr[11]=0;          //........................
	    		   narr[11]=jarr[11];
	    		   jarr[12]=0;
	    	       narr[12]=jarr[12];
	    	   }*/




	    	  //..............for hand 2..................

	    	   cvtColor(mInput,rgba1,CV_YUV2BGR_NV21);
	    	   threshold(rgba1, rgba1, 100, 255, cv::THRESH_BINARY);
	    	   cvtColor(rgba1,hsv2,CV_RGB2HSV);

	    	   			    	 // LOGE("for shadow 2");
	    	   int hm=10000,hx=0,hy=0;

	    	   if(px3<px4)
	    	   {

	    	  for(int i=px3;i<px4;)
	    {
	    	 //LOGE("1 for");

	    	  for(int j=py3;j<py2;)
	    	{
	    	   	//LOGE("2 for");
	    	   //rectangle(img,Point (x1,y1),Point (x1,y1),Scalar (0,0,255),2);
	    	  //  cout<<"--"<<x1<<"--"<<y1;
	    	 //	rectangle(mResult,Point (i,j),Point (i,j),Scalar (0,0,255,255),2);
	    	 int	h=hsv2.at<cv::Vec3b>(j,i)[0];
	    	 int	s=hsv2.at<cv::Vec3b>(j,i)[1];
	    	 int    v=hsv2.at<cv::Vec3b>(j,i)[2];

	    	// int b=hsv2.at<cv::Vec3b>(j,i)[0];
	    	// int g=hsv2.at<cv::Vec3b>(j,i)[1];
	    	// int r=hsv2.at<cv::Vec3b>(j,i)[2];

	    	 if(h>0 && h<180 && s==255 && v==255)
	    	// if(b>250 && b<255 && g>250 && g<255 && r>250 && r<255)
	    {
	    		 x=1;
	    	 //  LOGE("1 if");
	    	// ly=j;
	    		// x=1;
	    	   		  if(j<hm)
	    	    {
	    	   		  // LOGE("2 if");
	    	   		     hm=j;
	    	   		     hx=i;
	    	   		     hy=j;
	    	     }
	     }
	    	   j=j+5;


	     }

	    	    i=i+5;

	    }





		}

	   else
 {
	   for(int i=px4;i<px1;)
	 {
	    //LOGE("1 for");

	    for(int j=py4;j<py3;)
	 {
	    //LOGE("2 for");
	    //rectangle(img,Point (x1,y1),Point (x1,y1),Scalar (0,0,255),2);
	    //  cout<<"--"<<x1<<"--"<<y1;
	   //	rectangle(mResult,Point (i,j),Point (i,j),Scalar (0,0,255,255),2);
	    	int	h=hsv2.at<cv::Vec3b>(j,i)[0];
	    	int	s=hsv2.at<cv::Vec3b>(j,i)[1];
	        int    v=hsv2.at<cv::Vec3b>(j,i)[2];

	      //  int b=hsv2.at<cv::Vec3b>(j,i)[0];
	      //  int g=hsv2.at<cv::Vec3b>(j,i)[1];
	      //  int r=hsv2.at<cv::Vec3b>(j,i)[2];


	    	if(h>0 && h<180 && s==255 && v==255)
	    	//	if(b>250 && b<255 && g>250 && g<255 && r>250 && r<255)
	     {
	    		x=1;
	    		//  LOGE("1 if");
	    		   	    	// ly=j;
	    		   	    		// x=1;
	    		    if(j<hm)
	    	 {
	    		   // LOGE("2 if");
	    		   	   hm=j;
	    		   	   hx=i;
	    		   	   hy=j;
	    	 }
	     }
	    		   	    	   j=j+5;


	    		   	     }

	    		   	    	    i=i+5;

	    		   	    }






	    	   }
	    	  //   LOGE("cont= %d",cont);
	    	  //  cv::circle(mResult, Point(hx,hy), 4, cv::Scalar(255, 0, 0,255), 2);
              /*  if(x==1)
                {
	    	   	float a=px4-px3;  //...............
	    	  	//float b=py2-py1;
	    	    float b=py1-py4;
	    	    float c=480/a;
	    	   	float d=800/b;
	    	   	float xf=(hx-px3)*c;
	    	    float yf=(hy-py3)*d;
	    	   	float xt=xf*1.6;
	    	   	float yt=yf*1.6;
	    	   	//float yt=yf*1.475;
	    	   // int xi=(int) xf;
	    	    //int yi=(int) yf;
	    	   	 int xi=(int) xt;
	    	   	int yi=(int) yt;

	    	   jarr[9]=hx;          //........................
	    	   narr[9]=jarr[9];
	    	   jarr[10]=hy;
	    	   narr[10]=jarr[10];
                }

                else
                {
                	jarr[9]=0;          //........................
                    narr[9]=jarr[9];
                	jarr[10]=0;
                	narr[10]=jarr[10];
                }*/



                //.................for hand....................


                	    	   	cvtColor(mInput,rgba2,CV_YUV2BGR_NV21);

                	    	   	cvtColor(rgba2,hsv,CV_RGB2HSV);

                	    	 //  	LOGE("for hand");
                	    	  // 	LOGE("h1,h2 %d,%d",h1,h2);
                	    	 //  	LOGE("s1,s2 %d,%d",s1,s2);
                	    	  // 	LOGE("v1,v2 %d,%d",v1,v2);
                	     //	cv::inRange(hsv, cv::Scalar(0,87,74), cv::Scalar(180,134,127), hsv);
                	     //	cv::inRange(hsv, cv::Scalar(0,s1-20,v1-20), cv::Scalar(180,s2+20,v2+20), hsv);
                	    //	cv::inRange(hsv, cv::Scalar(0,76,70), cv::Scalar(180,134,123), hsv);

                	if(px1!=0 && py1!=0 && px2!=0 && py2!=0 && px3!=0 && py3!=0 && px4!=0 && py4!=0)
                	{

                	//	cv::inRange(hsv, cv::Scalar(0,87,74), cv::Scalar(180,134,127), hsv);
                		cv::inRange(hsv, cv::Scalar(0,s1-20,v1-20), cv::Scalar(180,s2+20,v2+20), hsv);
                	// 	cv::inRange(hsv, cv::Scalar(0,76,70), cv::Scalar(180,134,123), hsv);
                   // cv::inRange(hsv, cv::Scalar(0,76,70), cv::Scalar(11,134,123), hsv);
                	    	   	 int blurSize = 5;
                	    	       int elementSize = 5;
                	    	   	cv::medianBlur(hsv,hsv, blurSize);
                	    	       cv::Mat element = cv::getStructuringElement(cv::MORPH_ELLIPSE, cv::Size(2 * elementSize + 1, 2 * elementSize + 1), cv::Point(elementSize, elementSize));
                	    	   	cv::dilate(hsv, hsv, element);
                	    	   		   // Contour detection
                	    	   		   std::vector<std::vector<cv::Point> > contours;
                	    	   		   std::vector<cv::Vec4i> hierarchy;
                	    	   		   cv::findContours(hsv, contours, hierarchy, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cv::Point(0, 0));
   /*   if(px3<px4)
      {
         cv::findContours(hsv(cv::Rect(px3,(py3),(px4-px3),(py2-py3))), contours, hierarchy, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cv::Point(px3,py3));
      }
      else if(py3>py4)
     {
         cv::findContours(hsv(cv::Rect(px4,(py4),(px1-px4),(py3-py4))), contours, hierarchy, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cv::Point(px4,py4));
     }*/
                	    	   		   size_t largestContour = 0;
                	    	   		   for (size_t i = 1; i < contours.size(); i++)
                	    	   		   {
                	    	   		      if (cv::contourArea(contours[i]) > cv::contourArea(contours[largestContour]))
                	    	   		      largestContour = i;
                	    	   		   }

                	    	   		 //  cv::drawContours(mResult, contours, largestContour, cv::Scalar(0,0,0,0), 1);

                	    	   		   // Convex hull

                	    	   		   if (!contours.empty())
                	    	   	{
                	    	   		  std::vector<std::vector<cv::Point> > hull(1);
                	    	   		  cv::convexHull(cv::Mat(contours[largestContour]), hull[0], false);
                	    	   	//	  cv::drawContours(mResult, hull, 0, cv::Scalar(0, 0, 255,255), 3);


                	    	   		  if (hull[0].size() > 2)
                	    	   		 {
                	    	   			x=1;
                	    	   		    std::vector<int> hullIndexes;
                	    	   		    cv::convexHull(cv::Mat(contours[largestContour]), hullIndexes, true);
                	    	   		  //  std::vector<cv::Vec4i> convexityDefects;
                	    	   		  //  cv::convexityDefects(cv::Mat(contours[largestContour]), hullIndexes, convexityDefects);


                	    	   		   cv::Moments mo = cv::moments(hull[0]);
                	    	   		  // cv::Point result = cv::Point(mo.m10/mo.m00 , mo.m01/mo.m00);
                	    	   		 //  cout<<result;
                	    	   		  // cv::circle(mResult, result, 4, cv::Scalar(0, 0, 0,0), 2);
                	    	   		 //cout<<hull[0];
                	    	   		   double res,mres=10000;
                	    	   		  // cv::Point far;
           if(px3<px4)
     {
        	   float a=px4-px3;  //...............
        	   //float b=py2-py1;
        	   float b=py1-py4;
        	   float c=480/a;
        	   float d=640/b;
        	   float xf=(hx-px3)*c;
        	   float yf=(hy-py3)*d;
        	   float xt=xf*1.6;
        	   float yt=yf*2;
        	   //float yt=yf*1.475;
        	   // int xi=(int) xf;
        	   //int yi=(int) yf;
        	   int xi=(int) xt;
        	   int yi=(int) yt;
        	   jarr[9]=hx;          //........................
        	   narr[9]=jarr[9];
        	   jarr[10]=hy;
        	   narr[10]=jarr[10];
        	//   cv::circle(mResult, Point(hx,hy), 4, cv::Scalar(255, 0, 0,255), 2);

     }
           else if(py3>py4)
     {
        	   float a=px1-px4;  //...............
        	   //float b=py2-py1;
        	   float b=py3-py4;
        	   float c=480/a;
        	   float d=640/b;
        	   float xf=(hx-px4)*c;
        	   float yf=(hy-py4)*d;
        	   float xt=xf*1.6;
        	   float yt=yf*2;
        	   //float yt=yf*1.475;
        	   // int xi=(int) xf;
        	  //int yi=(int) yf;
        	   int xi=(int) xt;
        	   int yi=(int) yt;
        	   jarr[9]=hx;          //........................
        	   narr[9]=jarr[9];
        	   jarr[10]=hy;
        	   narr[10]=jarr[10];
        	//   cv::circle(mResult, Point(hx,hy), 4, cv::Scalar(255, 0, 0,255), 2);
     }



                	    	   		//cv::circle(mResult, Point(far.x,far.y), 4, cv::Scalar(255, 0, 0,255), 2);

                	    	   		 }

                	    	   		  else
                	    	   		  {
                	    	   			jarr[9]=0;          //........................
                	    	   			narr[9]=jarr[9];
                	    	   			jarr[10]=0;
                	    	   			narr[10]=jarr[10];
                	    	   		  }

                	    	  // 	cv::circle(mResult, Point(far.x,far.y), 4, cv::Scalar(255, 0, 0,255), 2);

                	    	   	}
                	}


        //......................for shadow ....................................

           cvtColor(mInput,rgba3,CV_YUV2BGR_NV21);
         //  threshold(rgba3, rgba3, 190, 255, cv::THRESH_BINARY);
           cvtColor(rgba3,hsv1,CV_RGB2HSV);
          // cvtColor(hsv1,rgba3,CV_HSV2RGB);
         //  cvtColor(rgba3,mResult,CV_RGB2BGRA);
          // cvtColor(rgba3,mResult,CV_HSV2RGB);
          // cvtColor( mInput,mResult,CV_);

         //  LOGE("p1x,p1y %d,%d",px1,py1);
         //  LOGE("p2x,p2y %d,%d",px2,py2);
        //   LOGE("p3x,p3y %d,%d",px3,py3);
        //   LOGE("p4x,p4y %d,%d",px4,py4);

           if(px1!=0 && py1!=0 && px2!=0 && py2!=0 && px3!=0 && py3!=0 && px4!=0 && py4!=0)
       {

        	//   LOGE("up1x,p1y %d,%d",px1,py1);
        	 //  LOGE("up2x,p2y %d,%d",px2,py2);
        	 //  LOGE("up3x,p3y %d,%d",px3,py3);
        	 //  LOGE("up4x,p4y %d,%d",px4,py4);
      //  cv::inRange(hsv1, cv::Scalar(0,ss1-20,sv1-20), cv::Scalar(180,ss2+20,sv2+20), hsv1);
     	cv::inRange(hsv1, cv::Scalar(0,0,105), cv::Scalar(180,34,148), hsv1);
       // cv::inRange(hsv1(cv::Rect(px3,py3,(px4-px3),(py2-py3))), cv::Scalar(0,0,130), cv::Scalar(180,46,182), hsv1);

    /*    if(px3<px4)
    {
        	cv::inRange(hsv1(cv::Rect(px3,py3,(px4-px3),(py2-py3))), cv::Scalar(0,ss1-20,sv1-20), cv::Scalar(180,ss2+20,sv2+20), hsv1);
    }
       else if(py3>py4)
    {
    	   cv::inRange(hsv1(cv::Rect(px4,py4,(px1-px4),(py3-py4))), cv::Scalar(0,ss1-20,sv1-20), cv::Scalar(180,ss2+20,sv2+20), hsv1);
    }*/


        int blurSize1 = 5;
        int elementSize1 = 5;
        cv::medianBlur(hsv1,hsv1, blurSize1);
        cv::Mat element1 = cv::getStructuringElement(cv::MORPH_ELLIPSE, cv::Size(2 * elementSize1 + 1, 2 * elementSize1 + 1), cv::Point(elementSize1, elementSize1));
       	cv::dilate(hsv1, hsv1, element1);
        // Contour detection
        std::vector<std::vector<cv::Point> > contours1;
        std::vector<cv::Vec4i> hierarchy1;
      //  cv::findContours(hsv1, contours1, hierarchy1, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cv::Point(0, 0));
        if(px3<px4)
        {
        cv::findContours(hsv1(cv::Rect(px3,py3,(px4-px3),(py2-py3))), contours1, hierarchy1, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cv::Point(px3,py3));
        }
        else if(py3>py4)
        {
        	cv::findContours(hsv1(cv::Rect(px4,py4,(px1-px4),(py3-py4))), contours1, hierarchy1, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cv::Point(px4,py4));
        }
        // cv::findContours(hsv1, contours1, hierarchy1, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE, cv::Point(0,0));
        size_t largestContour1 = 0;
        for (size_t i = 1; i < contours1.size(); i++)
       {
         if (cv::contourArea(contours1[i]) > cv::contourArea(contours1[largestContour1]))
         largestContour1 = i;
       }

       //  cv::drawContours(mResult, contours, largestContour, cv::Scalar(0,0,0,0), 1);

      // Convex hull

        if (!contours1.empty())
      {
        std::vector<std::vector<cv::Point> > hull1(1);
        cv::convexHull(cv::Mat(contours1[largestContour1]), hull1[0], false);
        cv::drawContours(mResult, hull1, 0, cv::Scalar(0, 0, 255,255), 3);


        if (hull1[0].size() > 2)
      {
         x=1;
        std::vector<int> hullIndexes1;
        cv::convexHull(cv::Mat(contours1[largestContour1]), hullIndexes1, true);
        double res1,mres1=10000;
        	  	    	   		  // cv::Point far;
        for (size_t i = 0; i < hull1[0].size(); i++)
     {

       // cout<<hull[0][i]<<"next loop";
      // res = cv::norm(result-hull[0][i]);
       res1=hull1[0][i].y;


       // cout<<"\n"<<res;
       if(res1<mres1)
     {
        mres1=res1;
       far1=hull1[0][i];
     }



    }
        cv::circle(mResult, far1, 4, cv::Scalar(255, 0, 0,255), 2);

       if(px3<px4)
      {
   		float a=px4-px3;  //...............
   	   //float b=py2-py1;
   	    float b=py1-py4;
   	    float c=480/a;
   	    float d=640/b;
   	    float xf=(far1.x-px3)*c;
   	    float yf=(far1.y-py3)*d;
   	    float xt=xf*1.6;
   	   	float yt=yf*2;
   	    //float yt=yf*1.475;
   	   // int xi=(int) xf;
   	    //int yi=(int) yf;
   	    int xi=(int) xt;
   	    int yi=(int) yt;

   	     jarr[11]=xi;          //........................
   	     narr[11]=jarr[11];
   	     jarr[12]=yi;
   	     narr[12]=jarr[12];

     }
            else if(py3>py4)
               {
            	 float a=px1-px4;  //...............
                 //float b=py2-py1;
            	 float b=py3-py4;
            	 float c=480/a;
            	 float d=640/b;
            	 float xf=(far1.x-px4)*c;
            	 float yf=(far1.y-py4)*d;
            	 float xt=xf*1.6;
                 float yt=yf*2;
            	//float yt=yf*1.475;
                // int xi=(int) xf;
                //int yi=(int) yf;
            	 int xi=(int) xt;
            	 int yi=(int) yt;

            	 jarr[11]=xi;          //........................
            	 narr[11]=jarr[11];
            	 jarr[12]=yi;
            	 narr[12]=jarr[12];
               }


       }

        else
        {
             jarr[11]=0;          //........................
             narr[11]=jarr[11];
             jarr[12]=0;
             narr[12]=jarr[12];
        }


  	}
      }

       /*    int sm=10000,sx=0,sy=0;

           	    	  for(int i=px3;i<px4;)
           	    {
           	    	 //LOGE("1 for");

           	    	  for(int j=py3;j<py2;)
           	    	{
           	    	   	//LOGE("2 for");
           	    	   //rectangle(img,Point (x1,y1),Point (x1,y1),Scalar (0,0,255),2);
           	    	  //  cout<<"--"<<x1<<"--"<<y1;
           	    	 //	rectangle(mResult,Point (i,j),Point (i,j),Scalar (0,0,255,255),2);
           	    	 int	h=hsv1.at<cv::Vec3b>(j,i)[0];
           	    	 int	s=hsv1.at<cv::Vec3b>(j,i)[1];
           	    	 int    v=hsv1.at<cv::Vec3b>(j,i)[2];

           	    	 if(h==0 && s==0 && v==0)
           	    {
           	    	 //  LOGE("1 if");
           	    	// ly=j;
           	    		 x=1;
           	    	   		  if(j<sm)
           	    	    {
           	    	   		  // LOGE("2 if");
           	    	   		     sm=j;
           	    	   		     sx=i;
           	    	   		     sy=j;
           	    	     }
           	     }
           	    	   j=j+5;


           	     }

           	    	    i=i+5;

           	    }

           	    	if(x==1)
           	 {
           		float a=px4-px3;  //...............
           	   //float b=py2-py1;
           	    float b=py1-py4;
           	    float c=480/a;
           	    float d=800/b;
           	    float xf=(hx-px3)*c;
           	    float yf=(hy-py3)*d;
           	    float xt=xf*1.6;
           	   	float yt=yf*1.6;
           	    //float yt=yf*1.475;
           	   // int xi=(int) xf;
           	    //int yi=(int) yf;
           	    int xi=(int) xt;
           	    int yi=(int) yt;

           	     jarr[11]=sx;          //........................
           	     narr[11]=jarr[11];
           	     jarr[12]=sy;
           	     narr[12]=jarr[12];
           	  }

           	     else
           	  {
           	     jarr[11]=0;          //........................
           	     narr[11]=jarr[11];
           	     jarr[12]=0;
           	     narr[12]=jarr[12];
           	  }*/


		env->ReleaseByteArrayElements(NV21FrameData, pNV21FrameData, 0);
		env->ReleaseIntArrayElements(outPixels, poutPixels, 0);
		env->ReleaseIntArrayElements(newArray, narr,0);
		env->ReleaseIntArrayElements(arr, jarr,0);

	return newArray;

	//return true;




}






}

