package top.fksoft.mediaSyncPlayer.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;


public class UdpBroadService extends Service {
    private SharedPreferences serverSet;

    public UdpBroadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serverSet = getSharedPreferences("server_config",MODE_PRIVATE);
        updateConfig();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                sendUdpPack();
            }
        },0,3000);
    }

    private void updateConfig() {
    }

    private void sendUdpPack() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
