package test;
import static com.googlecode.javacv.cpp.opencv_core.CV_AA;
import static com.googlecode.javacv.cpp.opencv_core.CV_FONT_HERSHEY_COMPLEX_SMALL;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvCloneImage;
import static com.googlecode.javacv.cpp.opencv_core.cvCopy;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvInitFont;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvRectangle;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_core.cvResetImageROI;
import static com.googlecode.javacv.cpp.opencv_core.cvSetImageROI;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_INTER_AREA;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_INTER_LINEAR;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvEqualizeHist;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvResize;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;

import com.googlecode.javacv.cpp.opencv_core.CvFont;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;


public class ProcessImg {
	private static CvHaarClassifierCascade cascade =
			new CvHaarClassifierCascade (cvLoad("D:\\haarcascade_frontalface_alt2.xml"));
	public static final int newWidth=60;
	public static final int newHeight=80;

	public ProcessImg(){
		
	}
	private IplImage resizeImage(IplImage origImg)
	  {
	  	IplImage outImg = null;
	  	int origWidth=0;
	  	int origHeight=0;
	  	if (origImg!=null) {
	  		origWidth = origImg.width();
	  		origHeight = origImg.height();
	  	}
	  	if (newWidth <= 0 || newHeight <= 0 || origImg == null || origWidth <= 0 || origHeight <= 0) {

	  		System.exit(1);
	  	}
	
	  	// Scale the image to the new dimensions, even if the aspect ratio will be changed.
	  	outImg = cvCreateImage(cvSize(newWidth, newHeight), origImg.depth(), origImg.nChannels());
	  	if (newWidth > origImg.width() && newHeight > origImg.height()) {
	  		// Make the image larger
	  		cvResetImageROI((IplImage)origImg);
	  		cvResize(origImg, outImg, CV_INTER_LINEAR);	// CV_INTER_CUBIC or CV_INTER_LINEAR is good for enlarging
	  	}
	  	else {
	  		// Make the image smaller
	  		cvResetImageROI((IplImage)origImg);
	  		cvResize(origImg, outImg, CV_INTER_AREA);	// CV_INTER_AREA is good for shrinking / decimation, but bad at enlarging.
	  	}
	
	  	return outImg;
	  }
	  
	
	 private IplImage cropImage(IplImage img, CvRect region)
	  {
		
	  	IplImage imageTmp;
	  	IplImage imageRGB;
	if (img.depth() != IPL_DEPTH_8U) {

	  		System.exit(1);
	  	}
	
	  	// First create a new (color or greyscale) IPL Image and copy contents of img into it.
	  	imageTmp = cvCreateImage(cvGetSize(img), IPL_DEPTH_8U, img.nChannels());
	  	cvCopy(img, imageTmp);
	  	
	  	// Create a new image of the detected region
	  	// Set region of interest to that surrounding the face
	  	cvSetImageROI(imageTmp, region);
	  	// Copy region of interest (i.e. face) into a new iplImage (imageRGB) and return it
	  	imageRGB = cvCreateImage(cvSize(region.width(),region.height()),  IPL_DEPTH_8U, img.nChannels());
	  	cvCopy(imageTmp, imageRGB);	// Copy just the region.
	
	      cvReleaseImage(imageTmp);
	      //region.setNull();
	  	return imageRGB;	
		}
	
	 
	 
	 private IplImage convertImageToGreyscale(IplImage imageSrc)
	  {
	  	IplImage imageGrey;
	  	// Either convert the image to greyscale, or make a copy of the existing greyscale image.
	  	// This is to make sure that the user can always call cvReleaseImage() on the output, whether it was greyscale or not.
	  	if (imageSrc.nChannels()==3) {
	  		imageGrey = cvCreateImage( cvGetSize(imageSrc), IPL_DEPTH_8U, 1 );
	  		cvCvtColor( imageSrc, imageGrey, CV_BGR2GRAY );
	  	}
	  	else {
	  		imageGrey = cvCloneImage(imageSrc);
	  	}
	  	return imageGrey;
	  }
	 
	 
	 public IplImage detectAndCrop(IplImage src)
	 {	
			IplImage equalizedImg=null;
			 if(!src.isNull())
			 {
			 	IplImage greyImg=null;
			 	IplImage faceImg=null;
			 	IplImage sizedImg=null;
			 
			 	CvRect r ;
			 	CvFont font = new CvFont(CV_FONT_HERSHEY_COMPLEX_SMALL, 1, 1); 
				cvInitFont(font,CV_FONT_HERSHEY_COMPLEX_SMALL, 1.0, 0.8,1,1,CV_AA);
			 	greyImg = cvCreateImage( cvGetSize(src), IPL_DEPTH_8U, 1 );	 	
				greyImg=convertImageToGreyscale(src);
				CvMemStorage storage = CvMemStorage.create();
				
				CvSeq sign = cvHaarDetectObjects(
						greyImg,
						cascade,
						storage,
						1.1,
						3,
						CV_HAAR_FIND_BIGGEST_OBJECT);
				cvClearMemStorage(storage);
				if(sign.total()>0)
				{
					
					for(int i=0;i<sign.total();i++)
					{
						r = new CvRect(cvGetSeqElem(sign, i));
					  	faceImg = cropImage(greyImg, r);	
						sizedImg = resizeImage(faceImg);
						if(i==0)
							equalizedImg = cvCreateImage(cvGetSize(sizedImg), 8, 1);	
						cvEqualizeHist(sizedImg, equalizedImg);				
						cvRectangle (
										src,
										cvPoint(r.x(), r.y()),
										cvPoint(r.width() + r.x(), r.height() + r.y()),
										CvScalar.WHITE,
										1,
										CV_AA,
										0);
						
						//cvPutText(src, String.valueOf(person),cvPoint(r.x()-10, r.y() + r.height() + 20), font, CvScalar.RED);
						cvReleaseImage(greyImg);
					  	cvReleaseImage(faceImg);
					  	cvReleaseImage(sizedImg);				
					}
						  	
				}		
			}
		 return equalizedImg;	
	 }
}
