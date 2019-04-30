package top.fksoft.mediaSyncPlayer.utils;

import android.graphics.*;

public class BitmapUtils {


    public static Bitmap cropBitmap(Bitmap bitmap,double proportion) {
        double w = bitmap.getWidth(); // 得到图片的宽，高
        double h = bitmap.getHeight();
        double k = w > h ? h:w;
        int l = (int) (k / proportion);
        return Bitmap.createBitmap(bitmap, ((int)(w/2  - (k/2))), ((int)(h/2  - (l/2))), (int)k, l, null, false);
    }

//
//    public static Bitmap cropBitmap(Bitmap bitmap,double proportion) {
//        double w = bitmap.getWidth(); // 得到图片的宽，高
//        double h = bitmap.getHeight();
//        double k = w > h ? h:w;
//        int l = (int) (k / proportion);
//        return Bitmap.createBitmap(bitmap, 0, ((int)(h/2  - (l/2))), (int)k, l, null, false);
//    }

    public static Bitmap getOvalBitmap(Bitmap s){
        Bitmap squareBitmap = cropBitmap(s, 1);
        Bitmap output = Bitmap.createBitmap(squareBitmap.getWidth(), squareBitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, squareBitmap.getWidth(), squareBitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(squareBitmap, rect, rect, paint);
        return output;
    }
}
