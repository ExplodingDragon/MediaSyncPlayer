package top.fksoft.mediaSyncPlayer.io;

import android.database.sqlite.SQLiteDatabase;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import top.fksoft.mediaSyncPlayer.bean.MusicListBean;

import java.util.List;

public class SongSql {

    public static final String LOCAL_TABLE = "music_local";
    public static final String LOCAL_IMG_SHA1 = "5C8AB007380159C2AF950ADDD24E7B853A3F5507";
    public static final String PLAY_LIST_IMG_SHA1 = "B78174DAFB0DE04D9D1D4312FCB43D8512AB5019";

    public static List<MusicListBean> getSongList(){
        return DataSupport.findAll(MusicListBean.class);
    }
    public static boolean hasSongList(String title){
        if (title == null) {
            return true;
        }
        title = title.trim();
        if (title.length() == 0){
            return true;
        }
        List<MusicListBean> list = DataSupport.select("title").where("title = ?", title).find(MusicListBean.class);
        if (list.size() == 0) {
            return false;
        }else {
            return true;
        }
    }

    public static String addSongList(String title) { //添加
        SQLiteDatabase database = LitePal.getDatabase();
        String tableName = "songList_" + System.currentTimeMillis();
        MusicListBean bean = new MusicListBean(title,tableName,PLAY_LIST_IMG_SHA1);
        bean.save();
        database.execSQL("create table if not exists " + tableName + "(" +
                "_id integer primary key autoincrement," +
                "_music_sha1 text" +
                ")");
        database.close();
        return tableName;
    }

    public static void deleteSongList(MusicListBean bean) {
        SQLiteDatabase database = LitePal.getDatabase();
        database.execSQL("DROP TABLE  "+ bean.getSqlName());
        bean.delete();
        database.close();
    }
}

