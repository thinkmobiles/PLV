package plv.estrella.com.plv.untils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import plv.estrella.com.plv.global.Constants;


public abstract class BitmapCreator {

    public static Bitmap getBitmap(String _path) {
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path);
    }

    public static Bitmap getCompressedBitmap(String _path, float _ratio, float _width){
        return tryCompressBitmap(_width, _ratio, _path, null, 0);
    }

    public static Bitmap getCompressedBitmap(Context _context, int _resId, float _width){
        return tryCompressBitmap(_width, Constants.RATIO_4_3, null, _context, _resId);
    }

    public static Bitmap tryCompressBitmap(float _width, float _ratio, String _path, Context _context, int _resId){

        String filePath = ApiManager.getPath() + _path;

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp;
        if(_context == null) {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } else {
            bmp = BitmapFactory.decodeResource(_context.getResources(), _resId,options);
        }

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        if(options.outHeight == 0)
            return null;

        float maxHeight = _width / _ratio;
        float imgRatio = actualWidth / actualHeight;

        if (actualHeight > maxHeight || actualWidth > _width) {
            if (imgRatio < _ratio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > _ratio) {
                imgRatio = _width / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) _width;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) _width;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        options.inJustDecodeBounds = false;

        try {
            if(_context == null) {
                bmp = BitmapFactory.decodeFile(filePath, options);
            } else {
                bmp = BitmapFactory.decodeResource(_context.getResources(), _resId,options);
            }
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
            Log.e("compressed bitmap", "OutOfMemoryError bmp");
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
            Log.e("compressed bitmap", "OutOfMemoryError scale");
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        if(scaledBitmap == null)
            return null;
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        try {
            canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        } catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }

        try {
            return Bitmap.createScaledBitmap(scaledBitmap, actualWidth, actualHeight, true);
        } catch (OutOfMemoryError error){
            error.printStackTrace();
            Log.e("compressed bitmap", "OutOfMemoryError scale");
            return null;
        }

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }
}
