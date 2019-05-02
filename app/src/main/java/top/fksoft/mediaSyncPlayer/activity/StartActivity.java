package top.fksoft.mediaSyncPlayer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.mediaSyncPlayer.fragment.SoftPrefFragment;
import top.fksoft.mediaSyncPlayer.io.FileIO;
import top.fksoft.mediaSyncPlayer.utils.AndroidUtils;
import top.fksoft.mediaSyncPlayer.utils.base.FBaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends FBaseActivity {

    private LinearLayout logo;
    private LinearLayout navigation;
    private SharedPreferences softSet;
    private boolean seek = false;

    @Override
    public void initData() {
        softSet = SoftPrefFragment.getSharedPreferences(this);
        if (softSet.getBoolean("seek_start",false)) {
            seek = true;
            toMain();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (seek)
            return;
        initView();
        int navigationBarHeight = AndroidUtils.getNavigationBarHeight2(this);
        if (navigationBarHeight>0){
            navigation.setPadding(0, 0, 0, navigationBarHeight);
            navigation.setVisibility(View.VISIBLE);
        }
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new AlphaAnimation(0.1f, 1.0f));
        Animation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_PARENT, 0,
                TranslateAnimation.RELATIVE_TO_PARENT, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, -0.1f
        );
        animationSet.addAnimation(translate);
        animationSet.setDuration(1000);
        animationSet.setRepeatCount(-1);
        animationSet.setFillAfter(true);
        logo.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> toMain());
                    }
                }, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void toMain() {
        final String[] permission = new String[]
                {
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                };
        sendPermissions(getResources().getStringArray(R.array.permission_allow_name),permission);
    }

    @Override
    public void permissionSuccessful(int i) {
        super.permissionSuccessful(i);
        FileIO.newInstance().initFileSystem();//释放相应的文件

        startActivity(new Intent(StartActivity.this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void initView() {
        logo = findViewById(R.id.logo);
        navigation = findViewById(R.id.NavigationBar);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_start;
    }

    @Override
    public void onClick(View v) {

    }
}
