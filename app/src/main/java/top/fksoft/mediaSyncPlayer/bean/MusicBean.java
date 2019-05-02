package top.fksoft.mediaSyncPlayer.bean;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.Serializable;

public class MusicBean extends DataSupport implements Serializable {

    private static final long serialVersionUID = 42342423423451L;

    private String musicSha1; //歌曲哈希值
    private String title;//歌曲标题
    private String author;//歌手
    private String musicPath;//歌曲位置
    private long length; //歌曲时长
    private String pictureSha1 = null;//图片哈希位置
    private String lyricPath = null; // 歌词位置

    public MusicBean(String title, String author, String musicPath, long length) {
        this.title = title;
        this.author = author;
        this.musicPath = musicPath;
        this.length = length;
    }

    public MusicBean(String title, String author, String musicPath, long length, String pictureSha1, String lyricPath) {
        this.title = title;
        this.author = author;
        this.musicPath = musicPath;
        this.length = length;
        this.pictureSha1 = pictureSha1;
        this.lyricPath = lyricPath;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public long getLength() {
        return length;
    }

    public String getPictureSha1() {
        return pictureSha1;
    }

    public String getLyricPath() {
        return lyricPath;
    }

    public boolean hasPicture() {
        if (pictureSha1 != null) {
            return true;
        }
        return false;
    }

    public boolean hasLyric() {
        if (lyricPath != null) {
            if (new File(lyricPath).isFile()) {
                return true;
            }
        }
        return false;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setPictureSha1(String pictureSha1) {
        this.pictureSha1 = pictureSha1;
    }

    public void setLyricPath(String lyricPath) {
        this.lyricPath = lyricPath;
    }

    public String getMusicSha1() {
        return musicSha1;
    }

    public void setMusicSha1(String musicSha1) {
        this.musicSha1 = musicSha1;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static MusicBean getInstance(String json) throws JsonSyntaxException {
        return new Gson().fromJson(json, MusicBean.class);
    }
}
