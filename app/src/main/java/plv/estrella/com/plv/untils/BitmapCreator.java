package plv.estrella.com.plv.untils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;


public abstract class BitmapCreator {

    public static final Bitmap getBitmap(String _path) {
        return BitmapFactory.decodeFile(ApiManager.getPath() + _path);
    }

    public static String getAbsolutePath(String _path){
        return ApiManager.getPath() + _path;
    }

    public static final Drawable getDrawable(String _path) {
        return Drawable.createFromPath(ApiManager.getPath() + _path);
    }
}
