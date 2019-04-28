package top.fksoft.mediaSyncPlayer.utils;

import android.graphics.Bitmap;

public class BitmapUtils {
    public static Bitmap cropBitmap(Bitmap bitmap,double proportion) {
        double w = bitmap.getWidth(); // 得到图片的宽，高
        double h = bitmap.getHeight();
        double k = w > h ? h:w;
        int l = (int) (k / proportion);
        return Bitmap.createBitmap(bitmap, 0, ((int)(h/2  - (l/2))), (int)k, l, null, false);
    }
}
