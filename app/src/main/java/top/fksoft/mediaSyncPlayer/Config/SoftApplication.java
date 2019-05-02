package top.fksoft.mediaSyncPlayer.Config;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SoftApplication extends Application {
    private static Context context;
    private static List<Activity> activities = new ArrayList<>();
    private static File pictureCachePath = null;
    private static File tempPath = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context  = getApplicationContext();
        LitePal.initialize(context); //初始化LitePal
        File externalFilesDir = context.getExternalFilesDir(null);
        pictureCachePath = new File(externalFilesDir,"Picture");
        tempPath = new File(context.getExternalCacheDir(),"Temp");
    }

    public static Context getContext() {
        return context;
    }

    public static File getPictureCachePath(String sha1) {
        return new File(pictureCachePath,sha1);
    }
    public static File getPictureCachePath() {
        return pictureCachePath;
    }

    public static File getTempPath() {
        return tempPath;
    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void killAllActivity(){
        synchronized (activities) {
            Activity[] activities = SoftApplication.activities.toArray(new Activity[0]);
            for (Activity activity : activities) {
                activity.finish();
            }
        }
    }
}
