package top.fksoft.mediaSyncPlayer.bean;

import java.io.File;

public class MusicBean {
    private String title;//歌曲标题
    private String author;//歌手
    private String musicPath;//歌曲位置
    private long length; //歌曲时长

    private String picturePath = null;//图片位置
    private String lyricPath = null; // 歌词位置

    public MusicBean(String title, String author, String musicPath, long length) {
        this.title = title;
        this.author = author;
        this.musicPath = musicPath;
        this.length = length;
    }

    public MusicBean(String title, String author, String musicPath, long length, String picturePath, String lyricPath) {
        this.title = title;
        this.author = author;
        this.musicPath = musicPath;
        this.length = length;
        this.picturePath = picturePath;
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

    public String getPicturePath() {
        return picturePath;
    }

    public String getLyricPath() {
        return lyricPath;
    }

    public boolean hasPicture(){
        if (picturePath!=null){
            if (new File(picturePath).isFile()){
                return true;
            }
        }
        return false;
    }
    public boolean hasLyric(){
        if (lyricPath!=null){
            if (new File(lyricPath).isFile()){
                return true;
            }
        }
        return false;
    }
}
