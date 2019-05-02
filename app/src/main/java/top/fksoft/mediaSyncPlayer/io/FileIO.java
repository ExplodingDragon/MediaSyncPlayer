package top.fksoft.mediaSyncPlayer.io;

import top.fksoft.mediaSyncPlayer.Config.SoftApplication;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.mediaSyncPlayer.utils.File2Utils;

import java.io.File;

public class FileIO {
    private FileIO() {
    }

    private static FileIO fileIO;
    public static FileIO newInstance() {
        if (fileIO == null)
            fileIO = new FileIO();
        return fileIO;
    }

    public void initFileSystem(){
        File pictureCachePath = SoftApplication.getPictureCachePath();
        if (!pictureCachePath.isDirectory()) {
            pictureCachePath.delete();
            pictureCachePath.mkdirs();
        }//初始化图片位置
        File tempPath = SoftApplication.getTempPath();
        if (!tempPath.isDirectory()) {
            tempPath.delete();
            tempPath.mkdirs();
        }//初始化图片位置
        File2Utils.copyResourceFile(
                SoftApplication.getContext(),
                R.mipmap.bg,
                SoftApplication.getPictureCachePath(SongSql.LOCAL_IMG_SHA1)
                ,false);
        File2Utils.copyResourceFile(
                SoftApplication.getContext(),
                R.mipmap.bg2,
                SoftApplication.getPictureCachePath(SongSql.PLAY_LIST_IMG_SHA1)
                ,false);
    } //初始化所有文件系统
}
