package top.fksoft.mediaSyncPlayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import top.fksoft.mediaSyncPlayer.utils.AndroidUtils;
import top.fksoft.test.android.ToastUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class UdpBroadService extends Service {
    public UdpBroadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            ToastUtils.showToast(this, AndroidUtils.getIpV4BroadcastAddress(this).getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                sendUdpPack();
            }
        },0,3000);
    }

    private void sendUdpPack() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
