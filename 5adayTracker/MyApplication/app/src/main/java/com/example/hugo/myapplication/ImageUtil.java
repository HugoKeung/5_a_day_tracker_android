package com.example.hugo.myapplication;

import android.graphics.Bitmap;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 03/07/17.
 */

public class ImageUtil {
//no overlapping middle region, changes ratio
    private static Bitmap quarterCrop(Bitmap bitmap, int x, int y, int input_size){
        Bitmap result = Bitmap.createBitmap(bitmap, x,y,(bitmap.getWidth()/2), (bitmap.getHeight()/2));

        //potentially screw up the dimension
        result = Bitmap.createScaledBitmap(result, input_size, input_size, true);


        return result;

    }
//overlapping middle region, changes ratio
    private static Bitmap twothirdCrop(Bitmap bitmap, int x, int y, int input_size){
        Bitmap result = Bitmap.createBitmap(bitmap, x,y,2*(bitmap.getWidth()/3), 2*(bitmap.getHeight()/3));
        result = Bitmap.createScaledBitmap(result, input_size, input_size, true);

        return result;
    }



//overlapping (partially), but cropping as square
    private static Bitmap quarterCropSaveRatio(Bitmap bitmap, int x, int y, int input_size, int longestEdge){

        Bitmap result = Bitmap.createBitmap(bitmap, x,y, longestEdge/2, longestEdge/2);
        result = Bitmap.createScaledBitmap(result, input_size, input_size, true);

        return result;
    }


    //biggest possible square in the middle
    public static Bitmap centerCrop(Bitmap bitmap, int input_size){
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int shortestEdge, x = 0, y = 0;

        if (height > width){
            shortestEdge = width;
            y = (height - shortestEdge)/2;
        }
        else {
            shortestEdge = height;
            x = (width - shortestEdge)/2;
        }

        Bitmap result = Bitmap.createBitmap(bitmap, x,y, shortestEdge, shortestEdge);

        result = Bitmap.createScaledBitmap(result, input_size, input_size, true);

        return  result;

    }



    public static List<Bitmap> quarterCropSaveRatioList(Bitmap bitmap, int input_size){
        int longestEdge;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        List<Bitmap> list = new ArrayList<>();
        if (height > width){
            longestEdge = height;
        }
        else longestEdge = width;


        Bitmap topLeft = quarterCropSaveRatio(bitmap, 0,0, input_size, longestEdge);
        Bitmap bottomLeft = quarterCropSaveRatio(bitmap, 0,height - longestEdge/2, input_size, longestEdge);
        Bitmap topRight = quarterCropSaveRatio(bitmap, width - longestEdge/2,0, input_size, longestEdge);
        Bitmap bottomRight = quarterCropSaveRatio(bitmap, width - longestEdge/2, height-longestEdge/2, input_size, longestEdge);

        list.add(topLeft);
        list.add(topRight);
        list.add(bottomLeft);
        list.add(bottomRight);

        return list;
    }



    public static List<Bitmap> twothirdCropList(Bitmap imageBitmap, int input_size){
        int originalHeight = imageBitmap.getHeight();
        int originalWidth = imageBitmap.getWidth();
        List<Bitmap> list = new ArrayList<>();

        Bitmap topLeft = twothirdCrop(imageBitmap,0,0, input_size);
        Bitmap bottomLeft = twothirdCrop(imageBitmap,0,originalHeight/3, input_size);
        Bitmap topRight = twothirdCrop(imageBitmap,originalWidth/3, 0, input_size);
        Bitmap bottomRight = twothirdCrop(imageBitmap, originalWidth/3, originalHeight/3, input_size);

        list.add(topLeft);
        list.add(topRight);
        list.add(bottomLeft);
        list.add(bottomRight);

        return list;

    }

    public static List<Bitmap> quarterCropList(Bitmap imageBitmap, int input_size){
        int originalHeight = imageBitmap.getHeight();
        int originalWidth = imageBitmap.getWidth();
        List<Bitmap> list = new ArrayList<>();
        Bitmap topLeft = quarterCrop(imageBitmap,0,0, input_size);
        Bitmap bottomLeft = quarterCrop(imageBitmap,0,originalHeight/2, input_size);
        Bitmap topRight = quarterCrop(imageBitmap,originalWidth/2, 0, input_size);
        Bitmap bottomRight = quarterCrop(imageBitmap, originalWidth/2, originalHeight/2, input_size);



        list.add(topLeft);
        list.add(topRight);
        list.add(bottomLeft);
        list.add(bottomRight);


        return list;
    }
    //make all image size the same (regardless big or small before), may mess up square image. assume 3:4, result image is 3:4, 672:896
    //only needed for sliding window methods
    private static Bitmap changeSize(Bitmap bitmap, int input_size){
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Bitmap result;

        //keep 4:3 ratio
            if (height > width) {

                result = Bitmap.createScaledBitmap(bitmap, input_size*3, input_size*4, true);
            } else {

                result = Bitmap.createScaledBitmap(bitmap, input_size*4, input_size*3, true);
            }
        return result;

    }

    public static List<Bitmap> tweleveWindows(Bitmap bitmap, int input_size){
        Bitmap result;
        List<Bitmap> resultList = new ArrayList<>();
        result = changeSize(bitmap, input_size);

//        for (int i = 0; i < result.getHeight()/input_size; i++){
//            for (int j = 0; j < result.getWidth()/input_size; j++){
//                Bitmap temp = Bitmap.createBitmap(result, j*input_size, i*input_size, input_size, input_size);
//                resultList.add(temp);
//                System.out.println("i: " + i + " j: " + j);
//            }
//
//        }

        resultList = slidingWindow(result, input_size, input_size, input_size);

        return resultList;

    }

    public static List<Bitmap> tweleveWindowsOverlap(Bitmap bitmap, int input_size){
        Bitmap result;
        List<Bitmap> resultList = new ArrayList<>();
        result = changeSize(bitmap, input_size);
        resultList = slidingWindow(result, input_size, input_size, input_size/2);

        return resultList;
    }

    public static List<Bitmap> slidingWindow(Bitmap bitmap, int windowH, int windowW, int stepSize){
        List<Bitmap> resultList = new ArrayList<>();
        for (int i = 0; i < bitmap.getHeight(); i+= stepSize){
            for (int j = 0; j < bitmap.getWidth(); j+= stepSize){
                if (j+windowW <= bitmap.getWidth() && i+windowH <= bitmap.getHeight()) {
                    Bitmap temp = Bitmap.createBitmap(bitmap, j, i, windowW, windowH);
                    resultList.add(temp);
                }

            }
        }
        return resultList;

    }

}
