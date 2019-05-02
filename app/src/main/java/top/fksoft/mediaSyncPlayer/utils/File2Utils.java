package top.fksoft.mediaSyncPlayer.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class File2Utils {
    private static final String TAG = "File2Utils";

    public static boolean copyResourceFile(Context context, int resId, File path,boolean copy){

        InputStream inputStream = context.getResources().openRawResource(resId);
        if (path.isDirectory()) {
            return false;
        }
        if (!copy && path.isFile()){
            return true;
        }else if (copy && path.isFile()){
            if (!path.delete()) {
                return false;
            }
        }
        FileOutputStream output = null;
        boolean result = false;
       try {
           output = new FileOutputStream(path);
           byte[] b = new byte[1024];
           int length;
           while (-1!=(length = inputStream.read(b,0,b.length))){
               output.write(b,0,length);
               output.flush();
           }
           result = true;
       }catch (Exception e){
           Log.w(TAG, "copyResourceFile: ",e);
       }finally {
           try {
               if (output != null) {
                   output.close();
               }
           }catch (Exception e){}
       }
        return  result;
    }

}
