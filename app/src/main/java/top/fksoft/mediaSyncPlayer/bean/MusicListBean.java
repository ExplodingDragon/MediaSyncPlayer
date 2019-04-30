package top.fksoft.mediaSyncPlayer.bean;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.litepal.crud.DataSupport;

import java.io.Serializable;

public class MusicListBean extends DataSupport implements Serializable {

    private static final long serialVersionUID = 42342423423451L;

    private String title;// 标题
    private String sqlName; //数据表名称
    private String imageSha1; //图片哈希值 ，在 /sdcard/Android/data/${package}/cache 下

    public MusicListBean( String title, String sqlName, String imageSha1) {
        this.title = title;
        this.sqlName = sqlName;
        this.imageSha1 = imageSha1;
    }

    public String getTitle() {
        return title;
    }

    public String getSqlName() {
        return sqlName;
    }

    public String getImageSha1() {
        return imageSha1;
    }

    public boolean hasImage(){
        if (imageSha1!=null){
            return true;
        }else {
            return false;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public void setImageSha1(String imageSha1) {
        this.imageSha1 = imageSha1;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static MusicListBean getInstance(String json)throws JsonSyntaxException {
        return new Gson().fromJson(json, MusicListBean.class);
    }
}
