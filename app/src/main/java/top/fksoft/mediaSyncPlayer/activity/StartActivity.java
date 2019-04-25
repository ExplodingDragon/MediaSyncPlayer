package top.fksoft.mediaSyncPlayer.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.test.android.dao.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends BaseActivity {

    @Override
    public int initLayout() {
        hide();
        return R.layout.activity_start;
    }

    @Override
    public void initData() {
        getCfgEdit().putBoolean("debug",false).commit();
        int delay = 1500;
        if (getConfig().contains("debug")){
            if (getConfig().getBoolean("debug",false)){
                delay = 0;
                Toast.makeText(getContext(),"软件已开启调试模式",Toast.LENGTH_SHORT).show();
            }
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getContext(),MainActivity.class));
                finish();
            }
        }, delay);
    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        hide();
    }

    private void hide(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getContext(),"软件启动中，无法退出！",Toast.LENGTH_SHORT).show();
        return;
    }
}
