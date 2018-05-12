package com.example.hugo.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hugo.myapplication.ImageRecognition.Classifier;
import com.example.hugo.myapplication.ImageRecognition.TensorFlowImageClassifier;
import com.example.hugo.myapplication.data.RecordDbHelper;

//import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by hugo on 15/06/17.
 */

public class BaseActivityCamera extends BaseActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int INPUT_SIZE = 224;
    private static final int IMAGE_MEAN = 117;
    private static final float IMAGE_STD = 1;
    private static final String INPUT_NAME = "input";
    private static final String OUTPUT_NAME = "output";

    private static final String MODEL_FILE = "tensorflow_inception_graph.pb";
    private static final String LABEL_FILE = "imagenet_comp_graph_label_strings.txt";

    RecordDbHelper dbHelper;

    private String photoPath;
    private Uri photoUri;

    private static Classifier classifier = null;
    private boolean mShowDialog = false;

    ArrayList<String> result;
    Set<String> resultSet;
    ArrayList<Long> inferenceTime;




    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long loadModelTime = SystemClock.uptimeMillis();
        if (classifier == null) {
            classifier = TensorFlowImageClassifier.create(
                    getAssets(), MODEL_FILE, LABEL_FILE, INPUT_SIZE, IMAGE_MEAN, IMAGE_STD, INPUT_NAME, OUTPUT_NAME
            );
            long loadModelTimeEnd = SystemClock.uptimeMillis() - loadModelTime;
            Log.w("load model time", "load model time " + loadModelTimeEnd);

        }
    }




    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager())!=null){
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        System.out.println(photoFile);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        ex.printStackTrace();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoUri = Uri.fromFile(photoFile);
                        System.out.println("photoURI" + photoUri);
                        takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }

                }
                //else go back to main screen
                else {
                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(myIntent);
                }


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            dbHelper = new RecordDbHelper(this);

            Bitmap imageBitmapx = null;

                imageBitmapx = BitmapFactory.decodeFile(photoPath);


            Bitmap imageBitmap = null;

            imageBitmap = BitmapFactory.decodeFile(photoPath);
            System.out.println("height " + imageBitmap.getHeight() + "width: " + imageBitmap.getWidth());
            

            String foodName = "";
            Bitmap newBit = null;

            inferenceTime = new ArrayList<>();
//            //FOLLOWING ARE TESTS FOR INDIVIDUAL WINDOWS IN AN IMAGE
//            ArrayList<Bitmap> testList = new ArrayList<>();
//
//            testList.add(BitmapFactory.decodeResource(getResources(), R.drawable.three4));
//
//            for (Bitmap imageBitmap1: testList){
//                System.out.println("XXXXXXXXXXXXXXXXXXXXXX");
//                System.out.println((classify6(imageBitmap1) +" "+ inferenceTime.get(0) +classifyF(imageBitmap1)+ inferenceTime.get(1) +"\n"+classify(imageBitmap1)  + inferenceTime.get(2) +" "+
//                    classifyA(imageBitmap1)+inferenceTime.get(3) +"\n" + classify2(imageBitmap1) +inferenceTime.get(4) + " "+
//                    classifyB(imageBitmap1)+inferenceTime.get(5) +"\n" + classify3(imageBitmap1) + inferenceTime.get(6) +" "+
//                    classifyC(imageBitmap1)+inferenceTime.get(7) +"\n" + classify4(imageBitmap1) + inferenceTime.get(8) +" "+
//                    classifyD(imageBitmap1)+inferenceTime.get(9) +"\n" + classify5(imageBitmap1) +inferenceTime.get(10) + " "+classifyE(imageBitmap1)+inferenceTime.get(11) +"\n"));
//
//            }

            //BELOW IS TO PRINT OUT ALL THE RESULTS FROM DIFERENT CONFIDENCE LEVEL AND STRATEGIES ON THE SCREEN ON APPLICATION.

//            TextView showResult = (TextView) findViewById(R.id.portion_fraction);
//            inferenceTime = new ArrayList<>();
//
//            showResult.setText(classify6(imageBitmap) +" "+ inferenceTime.get(0) +classifyF(imageBitmap)+ inferenceTime.get(1) +"\n"+classify(imageBitmap)  + inferenceTime.get(2) +" "+
//                    classifyA(imageBitmap)+inferenceTime.get(3) +"\n" + classify2(imageBitmap) +inferenceTime.get(4) + " "+
//                    classifyB(imageBitmap)+inferenceTime.get(5) +"\n" + classify3(imageBitmap) + inferenceTime.get(6) +" "+
//                    classifyC(imageBitmap)+inferenceTime.get(7) +"\n" + classify4(imageBitmap) + inferenceTime.get(8) +" "+
//                    classifyD(imageBitmap)+inferenceTime.get(9) +"\n" + classify5(imageBitmap) +inferenceTime.get(10) + " "+classifyE(imageBitmap)+inferenceTime.get(11) +"\n");
//
//


        //    uncomment when done testing
            result = classify(imageBitmap);

            if (result.size() > 0) {
                mShowDialog = true;

            }
            else {
                Toast.makeText(getApplicationContext(), "Item cannot be detected", Toast.LENGTH_LONG).show();
            }



        }
    }

    List<Classifier.Recognition> classifyImage(Bitmap bitmap, Classifier classifier){


        long startTime = SystemClock.uptimeMillis();

        final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);


        long endTime = SystemClock.uptimeMillis()-startTime;



        Log.w("inference_time", String.valueOf(endTime));
        return results;

    }

    
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    //    String imageFileName = "JPEG_" + timeStamp + "_";

        String imageFileName = "temp";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        photoPath = image.getAbsolutePath();

        return image;

    }



    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mShowDialog){
            mShowDialog = false;
            System.out.println("SHOWING");
            if (getSupportFragmentManager().findFragmentByTag("confirmation_dialog") == null) {
                System.out.println("SHOWING");

                ConfirmationDialogFragment newDialog = new ConfirmationDialogFragment();

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("result_list", result);

                newDialog.setArguments(bundle);
                newDialog.show(getSupportFragmentManager(), "confirmation_dialog");
            }
        }
    }

    private String findFood(List<Classifier.Recognition> cnnResult){

        String foodName = "";


        for (Classifier.Recognition recognition: cnnResult){
            if (recognition.getConfidence() >=0.3) {
                foodName = recognition.getTitle();
                if (dbHelper.foodExistInDB(foodName)) {


                    return foodName;
                }
            }

        }
        return "";

    }

    ArrayList<String> classify(Bitmap bitmap){

        Set set;

        ArrayList<String> result;
        List<Bitmap> bitmapList = ImageUtil.twothirdCropList(bitmap, INPUT_SIZE);


        set = new HashSet<>();

        long startTime = SystemClock.uptimeMillis();


        for (Bitmap bm: bitmapList){

            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
            String foodFound = findFood(cnnResult);
            if (!foodFound.equals("")){
                set.add(foodFound);
            }

        }

        long endTime = SystemClock.uptimeMillis()-startTime;
        inferenceTime.add(endTime);

        Log.w("inference_time", String.valueOf(endTime));
        result = new ArrayList<>();
        result.addAll(set);

        return result;


    }
    //BELOW ARE ALL COPY AND PASTE METHODS OF classify WITH DIFFERENT STRATEGIES AND CONFIDENCE LEVEL FOR TESTING.
//    private String findFoodB(List<Classifier.Recognition> cnnResult){
//
//        String foodName = "";
//
//
//        for (Classifier.Recognition recognition: cnnResult){
//            if (recognition.getConfidence() >=0.5) {
//                foodName = recognition.getTitle();
//                if (dbHelper.foodExistInDB(foodName)) {
//
//
//                    return foodName;
//                }
//            }
//
//        }
//        return "";
//
//    }
//    ArrayList<String> classify2(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = ImageUtil.quarterCropList(bitmap, INPUT_SIZE);
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//            String foodFound = findFood(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }
//    ArrayList<String> classify3(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = ImageUtil.quarterCropSaveRatioList(bitmap, INPUT_SIZE);
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//            String foodFound = findFood(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }
//    ArrayList<String> classify4(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = ImageUtil.tweleveWindows(bitmap, INPUT_SIZE);
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//            String foodFound = findFood(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }
//    ArrayList<String> classify6(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = new ArrayList<>();
//        bitmapList.add(bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true));
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//            String foodFound = findFood(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        System.out.println("END TIME:" + endTime);
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }
//    ArrayList<String> classify5(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = ImageUtil.tweleveWindowsOverlap(bitmap, INPUT_SIZE);
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//            //BELOW SHOW IMAGES THAT ARE IDENTIFIED AS SPAGHETTI SQUASH ON SCREEN (FOR PERSONAL TESTING ONLY)
////            for (Classifier.Recognition recognition : cnnResult){
////                System.out.println(recognition.getTitle() + recognition.getConfidence());
////                if (recognition.getTitle().equals("spaghetti squash") && recognition.getConfidence() > 0.3){
////                    ImageView iv1 = (ImageView) findViewById(R.id.iv1);
////                    ImageView iv2 = (ImageView) findViewById(R.id.iv2);
////                    ImageView iv3 = (ImageView) findViewById(R.id.iv3);
////                    ImageView iv4 = (ImageView) findViewById(R.id.iv4);
////                    ImageView iv5 = (ImageView) findViewById(R.id.iv5);
////                    ImageView iv6 = (ImageView) findViewById(R.id.iv6);
////
////                    if (iv1.getDrawable()==null) {
////                        iv1.setImageBitmap(bm);
////                    }
////                    else if (iv2.getDrawable()==null){
////                        iv2.setImageBitmap(bm);
////                    }
////                    else if (iv3.getDrawable()==null){
////                        iv3.setImageBitmap(bm);
////                    }
////                    else if (iv4.getDrawable()==null){
////                        iv4.setImageBitmap(bm);
////                    }
////                    else if (iv5.getDrawable()==null){
////                        iv5.setImageBitmap(bm);
////                    }
////                    else if (iv6.getDrawable()==null){
////                        iv6.setImageBitmap(bm);
////                    }
////
////                }
////            }
//
//            String foodFound = findFood(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }
//
//    ArrayList<String> classifyA(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = ImageUtil.twothirdCropList(bitmap, INPUT_SIZE);
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//            String foodFound = findFoodB(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }
//    ArrayList<String> classifyB(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = ImageUtil.quarterCropList(bitmap, INPUT_SIZE);
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//            String foodFound = findFoodB(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }
//    ArrayList<String> classifyC(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = ImageUtil.quarterCropSaveRatioList(bitmap, INPUT_SIZE);
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//            String foodFound = findFoodB(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }
//    ArrayList<String> classifyD(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = ImageUtil.tweleveWindows(bitmap, INPUT_SIZE);
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//            String foodFound = findFoodB(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }
//    ArrayList<String> classifyF(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = new ArrayList<>();
//        bitmapList.add(bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true));
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//            String foodFound = findFoodB(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }
//    ArrayList<String> classifyE(Bitmap bitmap){
//
//        Set set;
//
//        ArrayList<String> result;
//        List<Bitmap> bitmapList = ImageUtil.tweleveWindowsOverlap(bitmap, INPUT_SIZE);
//
//
//        set = new HashSet<>();
//
//        long startTime = SystemClock.uptimeMillis();
//
//
//        for (Bitmap bm: bitmapList){
//
//            List<Classifier.Recognition> cnnResult = classifyImage(bm, classifier);
//
//            String foodFound = findFoodB(cnnResult);
//            if (!foodFound.equals("")){
//                set.add(foodFound);
//            }
//
//        }
//
//        long endTime = SystemClock.uptimeMillis()-startTime;
//        inferenceTime.add(endTime);
//        Log.w("inference_time", String.valueOf(endTime));
//        result = new ArrayList<>();
//        result.addAll(set);
//
//        return result;
//
//
//    }




}


