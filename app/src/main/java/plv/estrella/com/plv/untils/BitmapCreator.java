package plv.estrella.com.plv.untils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;


public abstract class BitmapCreator {

    public static final Bitmap getBitmap(String _path) {
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path);
    }

    public static final Bitmap getBitmapCompressed(String _path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path, options);
    }

    public static final Bitmap getBitmapStrongCompressed(String _path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path, options);
    }

    public static String getAbsolutePath(String _path){
        return ApiManager.getPath() + _path;
    }

    public static final Drawable getDrawable(String _path) {
        return Drawable.createFromPath(ApiManager.getPath() + _path);
    }
}
