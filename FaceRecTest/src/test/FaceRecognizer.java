package test;
import com.googlecode.javacpp.FloatPointer;
import com.googlecode.javacpp.Pointer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvDestroyWindow;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.CvFont;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_legacy.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_FIND_BIGGEST_OBJECT;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_DO_ROUGH_SEARCH;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY; 
import static com.googlecode.javacv.cpp.opencv_imgproc.cvEqualizeHist;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;	
import static com.googlecode.javacv.cpp.opencv_core.CvMat;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_core.CV_FONT_HERSHEY_COMPLEX_SMALL;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.CV_32FC1;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateMat;
import static com.googlecode.javacv.cpp.opencv_core.cvCloneImage;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.CvSize;
import static com.googlecode.javacv.cpp.opencv_core.cvResetImageROI;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_core.cvCopy;
import static com.googlecode.javacv.cpp.opencv_core.cvSetImageROI;
import static com.googlecode.javacv.cpp.opencv_core.CV_AA;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvInitFont;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvRectangle;
import static com.googlecode.javacv.cpp.opencv_core.cvPutText;
import static com.googlecode.javacv.cpp.opencv_core.CV_32SC1;
import static com.googlecode.javacv.cpp.opencv_core.CvTermCriteria;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_32F;
import static com.googlecode.javacv.cpp.opencv_core.CV_L1;
import static com.googlecode.javacv.cpp.opencv_core.CV_TERMCRIT_ITER;
import static com.googlecode.javacv.cpp.opencv_core.cvNormalize;
import static com.googlecode.javacv.cpp.opencv_core.CvFileStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvWriteInt;
import static com.googlecode.javacv.cpp.opencv_core.cvTermCriteria;
import static com.googlecode.javacv.cpp.opencv_core.CV_STORAGE_WRITE;
import static com.googlecode.javacv.cpp.opencv_core.cvOpenFileStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvWrite;
import static com.googlecode.javacv.cpp.opencv_core.cvWriteString;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseFileStorage;
import static com.googlecode.javacv.cpp.opencv_core.CV_STORAGE_READ;
import static com.googlecode.javacv.cpp.opencv_core.cvReadIntByName;
import static com.googlecode.javacv.cpp.opencv_core.cvReadStringByName;
import static com.googlecode.javacv.cpp.opencv_core.cvReadByName;
import static com.googlecode.javacv.cpp.opencv_core.cvRect;
import static com.googlecode.javacv.cpp.opencv_core.cvConvertScale;
import static com.googlecode.javacv.cpp.opencv_core.cvMinMaxLoc;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_INTER_LINEAR;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_INTER_AREA;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvResize;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

	
import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
	
	public class FaceRecognizer{
	
	  private static final Logger LOGGER = Logger.getLogger(FaceRecognizer.class.getName());
	  private int nTrainFaces = 0;
	  private int nPersons=0;
	  private int nEigens = 0;
	  private int countSavedFace=1;
	  private CvMat personNumTruthMat;
	  private  CvMat eigenValMat;
	  private CvMat projectedTrainFaceMat;
	  private CvMat trainPersonNumMat=null; 
	  final static List<String> personNames = new ArrayList<String>();
	  private CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad("data\\haarcascade_frontalface_alt2.xml"));
	  private static final int newWidth=50;
	  private static final int newHeight=50;
	  
	  IplImage[] eigenVectArr;
	  IplImage[] trainingFaceImgArr;
	  IplImage[] testFaceImgArr;
	  IplImage pAvgTrainImg;
	 
	  
	  public static String personName;
	  private static String textName="unknow";
	  
	  public static double g_confidence=0;  
	  
	  
	  public FaceRecognizer() {
		  trainPersonNumMat = loadTrainingData();
	  }
	  
	  
	  private void learn(final String trainingFileName) {
	    int i;
	
	    // load training data
	    LOGGER.info("===========================================");
	    LOGGER.info("Loading the training images in " + trainingFileName);
	    trainingFaceImgArr = loadFaceImgArray(trainingFileName);
	    nTrainFaces = trainingFaceImgArr.length;
	    LOGGER.info("Got " + nTrainFaces + " training images");
	    if (nTrainFaces < 3) {
	      LOGGER.severe("Need 3 or more training faces\n"
	              + "Input file contains only " + nTrainFaces);
	      return;
	    }
	
	    // do Principal Component Analysis on the training faces
	    doPCA();
	
	    LOGGER.info("projecting the training images onto the PCA subspace");
	    // project the training images onto the PCA subspace
	    projectedTrainFaceMat = cvCreateMat(
	            nTrainFaces, // rows
	            nEigens, // cols
	            CV_32FC1); // type, 32-bit float, 1 channel
	
	    // initialize the training face matrix - for ease of debugging
	    for (int i1 = 0; i1 < nTrainFaces; i1++) {
	      for (int j1 = 0; j1 < nEigens; j1++) {
	        projectedTrainFaceMat.put(i1, j1, 0.0);
	      }
	    }
	
	    LOGGER.info("created projectedTrainFaceMat with " + nTrainFaces + " (nTrainFaces) rows and " + nEigens + " (nEigens) columns");
	    if (nTrainFaces < 5) {
	      LOGGER.info("projectedTrainFaceMat contents:\n" + oneChannelCvMatToString(projectedTrainFaceMat));
	    }
	
	    final FloatPointer floatPointer = new FloatPointer(nEigens);
	    for (i = 0; i < nTrainFaces; i++) {
	      cvEigenDecomposite(
	              trainingFaceImgArr[i], // obj
	              nEigens, // nEigObjs
	              eigenVectArr, // eigInput (Pointer)
	              0, // ioFlags
	              null, // userData (Pointer)
	              pAvgTrainImg, // avg
	              floatPointer); // coeffs (FloatPointer)
	
	      if (nTrainFaces < 5) {
	        LOGGER.info("floatPointer: " + floatPointerToString(floatPointer));
	      }
	      for (int j1 = 0; j1 < nEigens; j1++) {
	        projectedTrainFaceMat.put(i, j1, floatPointer.get(j1));
	      }
	    }
	    if (nTrainFaces < 5) {
	      LOGGER.info("projectedTrainFaceMat after cvEigenDecomposite:\n" + projectedTrainFaceMat);
	    }
	
	    // store the recognition data as an xml file
	    storeTrainingData();
	
	    // Save all the eigenvectors as images, so that they can be checked.
	    storeEigenfaceImages();
	  }

	
	/**
	 * 将图像转换为灰度图
	 * @param imageSrc
	 * @return
	 */
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
	  
	
	/**
	 * 重新设置图像大小
	 * @param origImg
	 * @return
	 */
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
	  		LOGGER.info("ERROR in resizeImage: Bad desired image size of");
	  		LOGGER.info(String.valueOf(newWidth)+","+String.valueOf(newHeight));
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
	
	
	
	 /**
	  * 裁剪图像
	  * @param img原始图像
	  * @param region，裁剪的位置和大小
	  * @return
	  */
	 private IplImage cropImage(IplImage img, CvRect region)
	  {
		
	  	IplImage imageTmp;
	  	IplImage imageRGB;
	
	  //	size.height()=img.height();
	  //	size.width() = img.width();
	  	
	  	if (img.depth() != IPL_DEPTH_8U) {
	  		LOGGER.info("ERROR in cropImage: Unknown image depth of");
	  		LOGGER.info(String.valueOf(img.depth()));
	  		LOGGER.info(" given in cropImage() instead of 8 bits per pixel.");
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
	
	   
	 
	 public boolean recognizeFromCam() throws Exception
	  {
		 	OpenCVFrameGrabber  grabber = null;
		 	FileDialog fd;
		  	IplImage pFrame=null;
		  	int keypress = 0;
		  	if(LoginShell.video_flag)//如果被选，刚用视频
		  	{	
		  		fd=new FileDialog(LoginShell.sShell,SWT.OPEN);  
		  		fd.setFilterExtensions(new String[]{"*.avi","*.wmv","*.mp4","*.*"});
		  		fd.setFilterNames(new String[]{".avi",".wmv",".mp4"});
		  		String filename=fd.open();
		  		grabber = OpenCVFrameGrabber.createDefault(filename);
		  	}
		  	else
		  		grabber = OpenCVFrameGrabber.createDefault(0);
			grabber.start();
	        pFrame = grabber.grab();//每次获取一帧图像
	        while( pFrame!=null ){
        		detectAndCropAndPre( pFrame,cascade,CV_HAAR_DO_CANNY_PRUNING | CV_HAAR_DO_ROUGH_SEARCH);			        
	        	cvShowImage("Press 'Esc' to Stop!",pFrame);
		  		pFrame = grabber.grab();
		  		keypress=cvWaitKey(24);
		  		System.out.println(g_confidence);
		  		if( keypress== 27){	
		  			grabber.release();
		  			cvDestroyWindow("Press 'Esc' to Stop!");
		  			break;
		  		}
	        }
	        cvWaitKey(1000);
	        cvReleaseImage(pFrame);
	        //cvDestroyWindow("BP_FaceRecognizer_FaceLogin");
	        grabber.release();
			return false;
	  }
	 
	 public void recongizeFormImage(String filePath){
		 	IplImage signleImage=null;
		 	System.out.println(filePath);
		 	signleImage=cvLoadImage(filePath);
		 	if(!signleImage.isNull());
		 		detectAndCropFromImg(signleImage,cascade,CV_HAAR_DO_CANNY_PRUNING | CV_HAAR_DO_ROUGH_SEARCH);			        
	        cvShowImage("Press 'Esc' to exit",signleImage);
		 	cvWaitKey(0);
		 	cvDestroyWindow("Press 'Esc' to exit");
	 }
	 
	 
	 
	
	 public boolean register(String name)throws Exception
	 {	 
		 	boolean flag=true;
		 	OpenCVFrameGrabber  grabber = null;
		 	FileDialog fd;
		 	IplImage pFrame=null;
		  	int keypress = 0;
		  	int countSecond=0;
		  	
			for(int i=0;i<personNames.size();i++){
				if(name.equals(personNames.get(i).toString()))
				{
					MessageBox messageBox = new MessageBox(RegisterShell.sShell, SWT.ICON_QUESTION |SWT.YES | SWT.NO);
					messageBox.setMessage("此用户已经被使用！！！连续按 “否” 返回");	
					messageBox.open();
					flag=false;
				}
			}
			  
		  	if(LoginShell.video_flag)//用视频文件进行注册
		  	{
		  		fd=new FileDialog(LoginShell.sShell,SWT.OPEN);  
		  		fd.setFilterExtensions(new String[]{"*.avi","*.wmv","*.mp4","*.*"});
		  		fd.setFilterNames(new String[]{".avi",".wmv",".mp4"});
		  		String filename=fd.open();
		  		grabber = OpenCVFrameGrabber.createDefault(filename);
		  	}
		  	else
		  		grabber = OpenCVFrameGrabber.createDefault(0);
	  		grabber.start();
	        pFrame = grabber.grab();		        	        
	        while( pFrame!=null )
	        {
	        	countSecond++;
	        	detectForRegister(pFrame,cascade,CV_HAAR_FIND_BIGGEST_OBJECT | CV_HAAR_DO_ROUGH_SEARCH,name);
	        	cvShowImage("Press 'Esc' to Stop",pFrame);
		  		pFrame = grabber.grab();
		  		keypress=cvWaitKey(24);
		  		if( keypress== 27 ||countSecond==100||countSavedFace==6)//||second==60
		  		{	
		  			cvReleaseImage(pFrame);
		  			grabber.release();
		  			break;  			
		  		}
	        }

	        personNames.add(name);
	        writeNameToTXT(name);
	        learn("data\\ForTraining.txt");
	        cvDestroyWindow("Press 'Esc' to Stop");
		 	return flag;
	 }
	 
 
	 
	 
	 private void detectForRegister(IplImage src,CvHaarClassifierCascade cascade,int flag,String name){

	 	IplImage greyImg=null;
	 	IplImage faceImg=null;
	 	IplImage sizedImg=null;
	 	IplImage equalizedImg=null;

	 	
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
				flag);
		cvClearMemStorage(storage);
		if(sign.total()==1)//只会有一个脸部
		{	
			r = new CvRect(cvGetSeqElem(sign, 0));
		  	faceImg = cropImage(greyImg, r);		
			sizedImg = resizeImage(faceImg);
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
			cvPutText(src, "This is your No."+String.valueOf(countSavedFace)+" photos. " ,cvPoint(r.x()-30, r.y() + r.height() + 30), font, CvScalar.RED);				
			cvSaveImage("img\\"+name+countSavedFace+".jpg",equalizedImg);
			cvWaitKey(1000);
			countSavedFace++;	
			cvReleaseImage(greyImg);
		  	cvReleaseImage(faceImg);
		  	cvReleaseImage(sizedImg);
		  	cvReleaseImage(equalizedImg);	
		}
		
			
	
		
}
		
		  			 
	 
	 private  void writeNameToTXT(String name){
			String text=null;
			int temp;
			temp=personNames.size();
			if(temp==0)
				temp=temp+1;
				
			try {
			     File file = new File("data\\ForTraining.txt");
			     FileOutputStream fos = new FileOutputStream(file,true);
			     OutputStreamWriter osw = new OutputStreamWriter(fos);
			     BufferedWriter bw = new BufferedWriter(osw);
			    // if(personNames.size()==0)
			    	 
			     for(int i=1;i<6;i++){
				     text=temp+" "+name+" "+"img\\"+name+i+".jpg";
				     bw.write(text);
				     bw.newLine();
			     }
			     
			     bw.flush();
			     bw.close();
			     osw.close();
			     fos.close();

			}
			catch (FileNotFoundException e1) {
			     e1.printStackTrace();
			    } catch (IOException e2) {
			     e2.printStackTrace();
			    }
	 }
	 
	 
	 private void eigenDecomImg(IplImage src){
	  	//CvMat trainPersonNumMat=null; 
	  	float confidence = 0.0f;
	  	int  nearest=0;
	  	int iNearest=0;
		
		
	  	LOGGER.info("=====================================Waiting For the camera .....");
	  //	
	  	if( trainPersonNumMat==null) {
	  		LOGGER.info("ERROR in recognizeFromCam(): Couldn't load the training data!\n");
	  		System.exit(1);
	  	}
		float[] projectedTestFace = new float[nEigens];
			
		 	/**
		 	 * 将采集图片映射至 PCA 子空间，利用最近距离匹配方法 SquaredEuclidean Distance, 
		 	 * 计算要识别图片同每一个训练结果的距离，找出距离最近的即可
		 	 * 结果保存在矩阵数组中
		 	*/
			cvEigenDecomposite(
				src,
				nEigens,
				eigenVectArr,
				0,
				null,
				pAvgTrainImg,
				projectedTestFace);
	
	    final FloatPointer pConfidence = new FloatPointer(confidence);
	    iNearest = findNearestNeighbor(projectedTestFace, new FloatPointer(pConfidence));
	    confidence = pConfidence.get();
	    nearest = trainPersonNumMat.data_i().get(iNearest);
	    personName="";
		textName=personNames.get(nearest-1);
		personName=personNames.get(nearest-1);
	    g_confidence=confidence;
		
	}
		   
	 
	 @SuppressWarnings("unused")
	private void detectAndCropAndPre(IplImage src,CvHaarClassifierCascade cascade,int flag)
	 {
		  	int nearest=0;
		 	IplImage greyImg=null;
		 	IplImage faceImg=null;
		 	IplImage sizedImg=null;
		 	IplImage equalizedImg=null;
		 	boolean faceIsTrue=false;
		 	CvRect r ;
		 	CvFont font = new CvFont(CV_FONT_HERSHEY_COMPLEX_SMALL, 1, 1); 
			cvInitFont(font,CV_FONT_HERSHEY_COMPLEX_SMALL, 1.0, 0.8,1,1,CV_AA);
			greyImg=convertImageToGreyscale(src);
			CvMemStorage storage = CvMemStorage.create();
			
			/**
			 * cvHaarDetectObjects，先将图像灰度化，根据传入参数判断是否进行canny边缘处理(默认不使用)，再进行匹配。
			 * 匹配后收集找出的匹配块,过滤噪声，计算相邻个数如果超过了规定值（传入的min_neighbors）就当成输出结果，否则删去。
			 * cvHaarDetectObjects( const CvArr* _img, 
                     CvHaarClassifierCascade* cascade,
                     CvMemStorage* storage, double scale_factor,
                     int min_neighbors, int flags, CvSize min_size )

			*/
			CvSeq sign = cvHaarDetectObjects(
					greyImg,
					cascade,
					storage,
					1.1,
					3,
					flag);
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
					cvEqualizeHist(sizedImg, equalizedImg);//使灰度图象直方图均衡化	
					
					//在原始图上画一个框
					cvRectangle (
									src,
									cvPoint(r.x(), r.y()),
									cvPoint(r.width() + r.x(), r.height() + r.y()),
									CvScalar.WHITE,
									1,
									CV_AA,
									0);
				
					eigenDecomImg(equalizedImg);
					if(g_confidence*100>50){
						cvPutText(src, textName,cvPoint(r.x()-10, r.y() + r.height() + 20), font, CvScalar.WHITE);
						cvPutText(src, " conf="+Integer.valueOf((int) (g_confidence*100))+"%",cvPoint(r.x()-10, r.y() + r.height() + 40), font, CvScalar.GREEN);
						textName="unknow";
					}
					else {
						cvPutText(src, "unknow",cvPoint(r.x()-10, r.y() + r.height() + 20), font, CvScalar.WHITE);
						cvPutText(src, " conf="+Integer.valueOf((int) (g_confidence*100))+"%",cvPoint(r.x()-10, r.y() + r.height() + 40), font, CvScalar.GREEN);
					}
				}
				cvReleaseImage(greyImg);
			  	cvReleaseImage(faceImg);
			  	cvReleaseImage(sizedImg);
			  	cvReleaseImage(equalizedImg);
			}	
				
	
		}

	 private void detectAndCropFromImg(IplImage src,CvHaarClassifierCascade cascade,int flag)
	 {
		 	IplImage greyImg=null;
		 	IplImage faceImg=null;
		 	IplImage sizedImg=null;
		 	IplImage equalizedImg=null;
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
					flag);
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
				
					eigenDecomImg(equalizedImg);
					if(g_confidence*100>50){
						cvPutText(src, textName,cvPoint(r.x()-10, r.y() + r.height() + 20), font, CvScalar.WHITE);
						cvPutText(src, " conf="+Integer.valueOf((int) (g_confidence*100))+"%",cvPoint(r.x()-10, r.y() + r.height() + 40), font, CvScalar.GREEN);
						textName="unknow";
					}
					else{
						cvPutText(src, "Unknow",cvPoint(r.x()-10, r.y() + r.height() + 20), font, CvScalar.WHITE);
						cvPutText(src, " conf="+Integer.valueOf((int) (g_confidence*100))+"%",cvPoint(r.x()-10, r.y() + r.height() + 40), font, CvScalar.GREEN);
					}
				}
			}
			else
				cvPutText(src, "can't find any face!",cvPoint(src.width()/2, src.height()/2), font, CvScalar.GREEN);
			//cvReleaseImage(greyImg);
			//if(!faceImg.isNull())
			//	cvReleaseImage(faceImg);
		  	//cvReleaseImage(sizedImg);
		  	//cvReleaseImage(equalizedImg);
		}	

			

	 
	 private IplImage[] loadFaceImgArray(final String filename) {
	    IplImage[] faceImgArr;
	    BufferedReader imgListFile;
	    String imgFilename;
	    int iFace = 0;
	    int nFaces = 0;
	    int i;
	    try {
	      // open the input file
	      imgListFile = new BufferedReader(new FileReader(filename));
	
	      // count the number of faces
	      while (true) {
	        final String line = imgListFile.readLine();
	        if (line == null || line.isEmpty()) {
	          break;
	        }
	        nFaces++;
	      }
	      LOGGER.info("nFaces: " + nFaces);
	      imgListFile = new BufferedReader(new FileReader(filename));
	
	      // allocate the face-image array and person number matrix
	      faceImgArr = new IplImage[nFaces];
	      personNumTruthMat = cvCreateMat(
	              1, // rows
	              nFaces, // cols
	              CV_32SC1); // type, 32-bit unsigned, one channel
	
	      // initialize the person number matrix - for ease of debugging
	      for (int j1 = 0; j1 < nFaces; j1++) {
	        personNumTruthMat.put(0, j1, 0);
	      }
	
	      personNames.clear();        // Make sure it starts as empty.
	      nPersons = 0;
	
	      // store the face images in an array
	      for (iFace = 0; iFace < nFaces; iFace++) {
	        String personName;
	        String sPersonName;
	        int personNumber;
	
	        // read person number (beginning with 1), their name and the image filename.
	        final String line = imgListFile.readLine();
	        if (line.isEmpty()) {
	          break;
	        }
	        final String[] tokens = line.split(" ");
	        personNumber = Integer.parseInt(tokens[0]);
	        personName = tokens[1];
	        imgFilename = tokens[2];
	        sPersonName = personName;
	        LOGGER.info("Got " + iFace + " " + personNumber + " " + personName + " " + imgFilename);
	
	        // Check if a new person is being loaded.
	        if (personNumber > nPersons) {
	          // Allocate memory for the extra person (or possibly multiple), using this new person's name.
	          personNames.add(sPersonName);
	          nPersons = personNumber;
	          LOGGER.info("Got new person " + sPersonName + " -> nPersons = " + nPersons + " [" + personNames.size() + "]");
	        }
	
	        // Keep the data
	        personNumTruthMat.put(
	                0, // i
	                iFace, // j
	                personNumber); // v
	
	        // load the face image
	        faceImgArr[iFace] = cvLoadImage(
	                imgFilename, // filename
	                CV_LOAD_IMAGE_GRAYSCALE); // isColor
	
	        if (faceImgArr[iFace] == null) {
	          throw new RuntimeException("Can't load image from " + imgFilename);
	        }
	      }
	
	      imgListFile.close();
	
	    } catch (IOException ex) {
	      throw new RuntimeException(ex);
	    }
	
	    LOGGER.info("Data loaded from '" + filename + "': (" + nFaces + " images of " + nPersons + " people).");
	    final StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder.append("People: ");
	    if (nPersons > 0) {
	      stringBuilder.append("<").append(personNames.get(0)).append(">");
	    }
	    for (i = 1; i < nPersons && i < personNames.size(); i++) {
	      stringBuilder.append(", <").append(personNames.get(i)).append(">");
	    }
	    LOGGER.info(stringBuilder.toString());
	
	    return faceImgArr;
	  }
	
	  
	  private void doPCA() {
	    int i;
	    CvTermCriteria calcLimit;
	    CvSize faceImgSize = new CvSize();
	
	    // set the number of eigenvalues to use
	    nEigens = nTrainFaces-1 ;
	
	    LOGGER.info("allocating images for principal component analysis, using " + nEigens + (nEigens == 1 ? " eigenvalue" : " eigenvalues"));
	
	    // allocate the eigenvector images
	    faceImgSize.width(trainingFaceImgArr[0].width());
	    faceImgSize.height(trainingFaceImgArr[0].height());
	    eigenVectArr = new IplImage[nEigens];
	    for (i = 0; i < nEigens; i++) {
	      eigenVectArr[i] = cvCreateImage(
	              faceImgSize, // size
	              IPL_DEPTH_32F, // depth
	              1); // channels
	    }
	
	    // allocate the eigenvalue array
	    eigenValMat = cvCreateMat(
	            1, // rows
	            nEigens, // cols
	            CV_32FC1); // type, 32-bit float, 1 channel
	
	    // allocate the averaged image
	    pAvgTrainImg = cvCreateImage(
	            faceImgSize, // size
	            IPL_DEPTH_32F, // depth
	            1); // channels
	
	    // set the PCA termination criterion
	    calcLimit = cvTermCriteria(
	            CV_TERMCRIT_ITER, // type
	            nEigens, // max_iter
	            1); // epsilon
	
	    LOGGER.info("computing average image, eigenvalues and eigenvectors");
	    // compute average image, eigenvalues, and eigenvectors
	    cvCalcEigenObjects(
	            nTrainFaces, // nObjects
	            trainingFaceImgArr, // input
	            eigenVectArr, // output
	            CV_EIGOBJ_NO_CALLBACK, // ioFlags
	            0, // ioBufSize
	            null, // userData
	            calcLimit,
	            pAvgTrainImg, // avg
	            eigenValMat.data_fl()); // eigVals
	
	    LOGGER.info("normalizing the eigenvectors");
	    cvNormalize(
	            eigenValMat, // src (CvArr)
	            eigenValMat, // dst (CvArr)
	            1, // a
	            0, // b
	            CV_L1, // norm_type
	            null); // mask
	  }
	
	 
	  private void storeTrainingData() {
	    CvFileStorage fileStorage;
	    int i;
	
	    LOGGER.info("writing data/facedata.xml");
	
	    // create a file-storage interface
	    fileStorage = cvOpenFileStorage(
	            "data\\facedata.xml", // filename
	            null, // memstorage
	            CV_STORAGE_WRITE, // flags
	            null); // encoding
	
	    // Store the person names. Added by Shervin.
	    cvWriteInt(
	            fileStorage, // fs
	            "nPersons", // name
	            nPersons); // value
	
	    for (i = 0; i < nPersons; i++) {
	      String varname = "personName_" + (i + 1);
	      cvWriteString(
	              fileStorage, // fs
	              varname, // name
	              personNames.get(i), // string
	              0); // quote
	    }
	
	    // store all the data
	    cvWriteInt(
	            fileStorage, // fs
	            "nEigens", // name
	            nEigens); // value
	
	    cvWriteInt(
	            fileStorage, // fs
	            "nTrainFaces", // name
	            nTrainFaces); // value
	
	    cvWrite(
	            fileStorage, // fs
	            "trainPersonNumMat", // name
	            personNumTruthMat); // value
	
	    cvWrite(
	            fileStorage, // fs
	            "eigenValMat", // name
	            eigenValMat); // value
	
	    cvWrite(
	            fileStorage, // fs
	            "projectedTrainFaceMat", // name
	            projectedTrainFaceMat);
	
	    cvWrite(fileStorage, // fs
	            "avgTrainImg", // name
	            pAvgTrainImg); // value
	
	    for (i = 0; i < nEigens; i++) {
	      String varname = "eigenVect_" + i;
	      cvWrite(
	              fileStorage, // fs
	              varname, // name
	              eigenVectArr[i]); // value
	    }
	
	    // release the file-storage interface
	    cvReleaseFileStorage(fileStorage);
	  }
	
	
	  private CvMat loadTrainingData() {
	    LOGGER.info("loading training data");
	    CvMat pTrainPersonNumMat = null; // the person numbers during training
	    CvFileStorage fileStorage;
	    int i;
	
	    // create a file-storage interface
	    fileStorage = cvOpenFileStorage(
	            "data\\facedata.xml", // filename
	            null, // memstorage
	            CV_STORAGE_READ, // flags
	            null); // encoding
	    if (fileStorage == null) {
	      LOGGER.severe("Can't open training database file 'data/facedata.xml'.");
	      return null;
	    }
	
	    // Load the person names.
	    personNames.clear();        // Make sure it starts as empty.
	    nPersons = cvReadIntByName(
	            fileStorage, // fs
	            null, // map
	            "nPersons", // name
	            0); // default_value
	    if (nPersons == 0) {
	      LOGGER.severe("No people found in the training database 'data/facedata.xml'.");
	      return null;
	    } else {
	      LOGGER.info(nPersons + " persons read from the training database");
	    }
	
	    // Load each person's name.
	    for (i = 0; i < nPersons; i++) {
	      String sPersonName;
	      String varname = "personName_" + (i + 1);
	      sPersonName = cvReadStringByName(
	              fileStorage, // fs
	              null, // map
	              varname,
	              "");
	      personNames.add(sPersonName);
	    }
	    LOGGER.info("person names: " + personNames);
	
	    // Load the data
	    nEigens = cvReadIntByName(
	            fileStorage, // fs
	            null, // map
	            "nEigens",
	            0); // default_value
	    nTrainFaces = cvReadIntByName(
	            fileStorage,
	            null, // map
	            "nTrainFaces",
	            0); // default_value
	    Pointer pointer = cvReadByName(
	            fileStorage, // fs
	            null, // map
	            "trainPersonNumMat"); // name
	    pTrainPersonNumMat = new CvMat(pointer);
	
	    pointer = cvReadByName(
	            fileStorage, // fs
	            null, // map
	            "eigenValMat"); // name
	    eigenValMat = new CvMat(pointer);
	
	    pointer = cvReadByName(
	            fileStorage, // fs
	            null, // map
	            "projectedTrainFaceMat"); // name
	    projectedTrainFaceMat = new CvMat(pointer);
	
	    pointer = cvReadByName(
	            fileStorage,
	            null, // map
	            "avgTrainImg");
	    pAvgTrainImg = new IplImage(pointer);
	
	    eigenVectArr = new IplImage[nTrainFaces];
	    for (i = 0; i <= nEigens; i++) {
	      String varname = "eigenVect_" + i;
	      pointer = cvReadByName(
	              fileStorage,
	              null, // map
	              varname);
	      eigenVectArr[i] = new IplImage(pointer);
	    }
	
	    // release the file-storage interface
	    cvReleaseFileStorage(fileStorage);
	
	    LOGGER.info("Training data loaded (" + nTrainFaces + " training images of " + nPersons + " people)");
	    final StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder.append("People: ");
	    if (nPersons > 0) {
	      stringBuilder.append("<").append(personNames.get(0)).append(">");
	    }
	    for (i = 1; i < nPersons; i++) {
	      stringBuilder.append(", <").append(personNames.get(i)).append(">");
	    }
	    LOGGER.info(stringBuilder.toString());
	
	    return pTrainPersonNumMat;
	  }
	

	  private void storeEigenfaceImages() {
	    // Store the average image to a file
	    LOGGER.info("Saving the image of the average face as 'data/out_averageImage.bmp'");
	    cvSaveImage("img\\out_averageImage.jpg", pAvgTrainImg);
	
	    // Create a large image made of many eigenface images.
	    // Must also convert each eigenface image to a normal 8-bit UCHAR image instead of a 32-bit float image.
	    LOGGER.info("Saving the " + nEigens + " eigenvector images as 'data/out_eigenfaces.bmp'");
	
	    if (nEigens > 0) {
	      // Put all the eigenfaces next to each other.
	      int COLUMNS = 8;        // Put upto 8 images on a row.
	      int nCols = Math.min(nEigens, COLUMNS);
	      int nRows = 1 + (nEigens / COLUMNS);        // Put the rest on new rows.
	      int w = eigenVectArr[0].width();
	      int h = eigenVectArr[0].height();
	      CvSize size = cvSize(nCols * w, nRows * h);
	      final IplImage bigImg = cvCreateImage(
	              size,
	              IPL_DEPTH_8U, // depth, 8-bit Greyscale UCHAR image
	              1);        // channels
	      for (int i = 0; i < nEigens; i++) {
	        // Get the eigenface image.
	        IplImage byteImg = convertFloatImageToUcharImage(eigenVectArr[i]);
	        // Paste it into the correct position.
	        int x = w * (i % COLUMNS);
	        int y = h * (i / COLUMNS);
	        CvRect ROI = cvRect(x, y, w, h);
	        cvSetImageROI(
	                bigImg, // image
	                ROI); // rect
	        cvCopy(
	                byteImg, // src
	                bigImg, // dst
	                null); // mask
	        cvResetImageROI(bigImg);
	        cvReleaseImage(byteImg);
	      }
	      cvSaveImage(
	              "img\\out_eigenfaces.jpg", // filename
	              bigImg); // image
	      cvReleaseImage(bigImg);
	    }
	  }
	
	
	  private IplImage convertFloatImageToUcharImage(IplImage srcImg) {
		  IplImage dstImg;
		  if ((srcImg != null) && (srcImg.width() > 0 && srcImg.height() > 0)) {
		      // Spread the 32bit floating point pixels to fit within 8bit pixel range.
		      CvPoint minloc = new CvPoint();
		      CvPoint maxloc = new CvPoint();
		      double[] minVal = new double[1];
		      double[] maxVal = new double[1];
		      cvMinMaxLoc(srcImg, minVal, maxVal, minloc, maxloc, null);
		      // Deal with NaN and extreme values, since the DFT seems to give some NaN results.
		      if (minVal[0] < -1e30) {
		        minVal[0] = -1e30;
		      }
		      if (maxVal[0] > 1e30) {
		        maxVal[0] = 1e30;
		      }
		      if (maxVal[0] - minVal[0] == 0.0f) {
		        maxVal[0] = minVal[0] + 0.001;  // remove potential divide by zero errors.
		      }                        // Convert the format
		      dstImg = cvCreateImage(cvSize(srcImg.width(), srcImg.height()), 8, 1);
		      cvConvertScale(srcImg, dstImg, 255.0 / (maxVal[0] - minVal[0]), -minVal[0] * 255.0 / (maxVal[0] - minVal[0]));
		      return dstImg;
	    }
	    return null;
	  }
	
	
	  /**
	   * 最近距离匹配方法 SquaredEuclidean Distance, 计算要识别图片同每一个训练结果的距离，找出距离最近的即可
	   * @param projectedTestFace
	   * @param pConfidencePointer
	   * @return
	   */
	  private int findNearestNeighbor(float projectedTestFace[], FloatPointer pConfidencePointer) {
	    double leastDistSq = Double.MAX_VALUE;
	    int i = 0;
	    int iTrain = 0;
	    int iNearest = 0;
	
	    LOGGER.info("................");
	    LOGGER.info("find nearest neighbor from " + nTrainFaces + " training faces");
	    //遍历projectedTrainFaceMat矩阵
	    for (iTrain = 0; iTrain < nTrainFaces; iTrain++) {
	      //LOGGER.info("considering training face " + (iTrain + 1));
	      double distSq = 0;
	
	      for (i = 0; i < nEigens; i++) {
	        //LOGGER.debug("  projected test face distance from eigenface " + (i + 1) + " is " + projectedTestFace[i]);
	
	        float projectedTrainFaceDistance = (float) projectedTrainFaceMat.get(iTrain, i);
	        float d_i = projectedTestFace[i] - projectedTrainFaceDistance;
	        distSq += d_i * d_i; // / eigenValMat.data_fl().get(i);  // Mahalanobis distance (might give better results than Eucalidean distance)
	//          if (iTrain < 5) {
	//            LOGGER.info("    ** projected training face " + (iTrain + 1) + " distance from eigenface " + (i + 1) + " is " + projectedTrainFaceDistance);
	//            LOGGER.info("    distance between them " + d_i);
	//            LOGGER.info("    distance squared " + distSq);
	//          }
	      }
	
	      if (distSq < leastDistSq) {
	        leastDistSq = distSq;
	        iNearest = iTrain;
	        LOGGER.info("  training face " + (iTrain + 1) + " is the new best match, least squared distance: " + leastDistSq);
	      }
	    }
	
	    // Return the confidence level based on the Euclidean distance,
	    // so that similar images should give a confidence between 0.5 to 1.0,
	    // and very different images should give a confidence between 0.0 to 0.5.
	    float pConfidence = (float) (1.0f - Math.sqrt(leastDistSq / (float) (nTrainFaces * nEigens)) / 255.0f);
	    pConfidencePointer.put(pConfidence);
	
	    LOGGER.info("training face " + (iNearest + 1) + " is the final best match, confidence " + pConfidence);
	    return iNearest;
	  }
	
	  
	  @SuppressWarnings("unused")
	private String floatArrayToString(final float[] floatArray) {
	    final StringBuilder stringBuilder = new StringBuilder();
	    boolean isFirst = true;
	    stringBuilder.append('[');
	    for (int i = 0; i < floatArray.length; i++) {
	      if (isFirst) {
	        isFirst = false;
	      } 
	      else {
	        stringBuilder.append(", ");
	      }
	      stringBuilder.append(floatArray[i]);
	    }
	    stringBuilder.append(']');
	
	    return stringBuilder.toString();
	  }
	

	  private String floatPointerToString(final FloatPointer floatPointer) {
	    final StringBuilder stringBuilder = new StringBuilder();
	    boolean isFirst = true;
	    stringBuilder.append('[');
	    for (int i = 0; i < floatPointer.capacity(); i++) {
	      if (isFirst) {
	        isFirst = false;
	      } else {
	        stringBuilder.append(", ");
	      }
	      stringBuilder.append(floatPointer.get(i));
	    }
	    stringBuilder.append(']');
	
	    return stringBuilder.toString();
	  }
	

	 private String oneChannelCvMatToString(final CvMat cvMat) {
	    //Preconditions
	    if (cvMat.channels() != 1) {
	      throw new RuntimeException("illegal argument - CvMat must have one channel");
	    }
	
	    final int type = cvMat.type();
	    StringBuilder s = new StringBuilder("[ ");
	    for (int i = 0; i < cvMat.rows(); i++) {
	      for (int j = 0; j < cvMat.cols(); j++) {
	        if (type == CV_32FC1 || type == CV_32SC1) {
	          s.append(cvMat.get(i, j));
	        } else {
	          throw new RuntimeException("illegal argument - CvMat must have one channel and type of float or signed integer");
	        }
	        if (j < cvMat.cols() - 1) {
	          s.append(", ");
	        }
	      }
	      if (i < cvMat.rows() - 1) {
	        s.append("\n  ");
	      }
	    }
	    s.append(" ]");
	    return s.toString();
	  }
	}
