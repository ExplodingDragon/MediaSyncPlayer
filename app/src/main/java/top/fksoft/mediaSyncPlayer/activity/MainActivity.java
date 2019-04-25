package top.fksoft.mediaSyncPlayer.activity;

import android.Manifest;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.test.android.dao.BaseActivity;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private static final String TAG = "MainActivity";
    @Override
    public void initData() {
        //申请权限
        sendPermissions(new String[]
                {
                        "读取设备外部存储空间",
                        "写入设备外部存储空间",
                        "访问摄像头进行拍照"
                }
                ,new String[]
                {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                });

        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            for (int i = 0; i < 12; i++) {
                emitter.onNext(i);
                Log.e(TAG, "initData: " + i );
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(Integer integer) {
                if (integer > 5){
                    d.dispose();
                }
                Log.e(TAG, "onNext: " + integer );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: " );
            }
        });
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.down);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        findViewById(R.id.down).setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.down:
                showToast(R.string.agree);
                break;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.exit);
            builder.setMessage(R.string.exitMessage);
            builder.setPositiveButton(R.string.agree, (dialog, which) -> super.onBackPressed());
            builder.setNegativeButton(R.string.dis_agree,null);
            builder.show();
        }else {
            Snackbar.make(getCurrentFocus(), R.string.exitMessage, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.agree, v -> super.onBackPressed()).show();
        }

    }



}
