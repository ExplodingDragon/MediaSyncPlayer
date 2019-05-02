package top.fksoft.mediaSyncPlayer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import top.fksoft.mediaSyncPlayer.bean.MusicBean;
import top.fksoft.mediaSyncPlayer.utils.obser.BroadcastManager;
import top.fksoft.test.android.dao.BaseService;

import java.util.ArrayList;
import java.util.List;

public class PlayService extends BaseService {
    private static final String TAG = "PlayService";
    BroadcastManager<Key.PLAY_STATUS> bindManager = null;
    private List<MusicBean> playList = null;
    private int playIndex = 0;
    private MediaPlayer mediaPlayer;
    private LocalBroadcastManager localBroadcastManager;
    private PlayBroadCast receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        playList = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        bindManager = new BroadcastManager<>();
                receiver = new PlayBroadCast();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Key.PLAY_ID);
        localBroadcastManager.registerReceiver(receiver,intentFilter);
        mediaPlayer.setOnCompletionListener(mp -> nextSong());
        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            nextSong();
            return true;
        });
    }

    private void nextSong() {
        if (playIndex >= (playList.size()- 1)){
            playIndex = 0;
        }else {
            playIndex+=1;
        }
        openMedia();

    }

    private void openMedia() {
        if (mediaPlayer.isPlaying()){
            mediaStop();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void mediaPause() { //暂停播放
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void mediaStop() { //停止播放
        mediaPlayer.stop();
        mediaPlayer.release();
        bindManager.sendBroadCast(Key.PLAY_STATUS.STOP);
    }
    private void mediaPlay(int i) { //播放或者跳转播放
        if (i != -1){
            mediaPlayer.seekTo(i);
        }
    }


    private class PlayBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Key.PLAY_STATUS status = Key.PLAY_STATUS.NONE;
            try {
                status = Key.PLAY_STATUS.valueOf(intent.getStringExtra(Key.CFG_ID));
                if (status == null) {
                    status = Key.PLAY_STATUS.NONE;
                }
            }catch (Exception e){
                Log.w(TAG, "onReceive: ",e);
            }
            switch (status) {
                case STOP:
                    mediaStop();
                    break;
                case PAUSE:
                    mediaPause();
                    break;
                case PLAY:
                    mediaPlay( -1);
                    break;
            }
        }

    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(receiver);
        bindManager.destroyBroadcast();
    }

    public interface Key{
        String PLAY_ID = "top.fksoft.android.mediaPlayer.PLAY_BROAD";
        String CFG_ID = "CFG_ID";

        enum PLAY_STATUS{
            NONE,
            STOP, //停止
            PAUSE,//暂停
            PLAY,//播放
        }
    }
}
