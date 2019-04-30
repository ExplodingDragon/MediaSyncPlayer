package top.fksoft.mediaSyncPlayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.mediaSyncPlayer.fragment.SoftPrefFragment;
import top.fksoft.mediaSyncPlayer.utils.AndroidUtils;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends Activity {

    private LinearLayout logo;
    private LinearLayout navigation;
    private SharedPreferences softSet;
    private boolean seek = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
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
        navigation.setPadding(0, 0, 0, AndroidUtils.getNavigationBarHeight(this));
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
                        runOnUiThread(() -> {
                            toMain();

                        });
                    }
                }, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void toMain() {
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

    private void initView() {
        logo = findViewById(R.id.logo);
        navigation = findViewById(R.id.NavigationBar);
    }
}
