package top.fksoft.mediaSyncPlayer.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.test.android.dao.BaseActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private static final String TAG = "MainActivity";
    private Button down;

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


    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        down = findViewById(R.id.down);
        down.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.down:
                startActivity(new Intent(this,SetActivity.class));
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
