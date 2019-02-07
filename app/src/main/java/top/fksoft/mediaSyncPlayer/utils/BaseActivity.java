package top.fksoft.mediaSyncPlayer.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import top.fksoft.mediaSyncPlayer.utils.dao.BaseWindow;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity implements BaseWindow {
    private static final int REQUEST_PERMISSION_CODE = 1;
    private SharedPreferences config;
    private SharedPreferences.Editor cfgEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        config = getContext().getSharedPreferences("Config", MODE_PRIVATE);
        cfgEdit = config.edit();
        initLayout();
        initView();
        initData();
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public <T extends View> T findViewById(int id) {
        T viewById = super.findViewById(id);
        if (viewById instanceof Button)
            viewById.setOnClickListener(this);
        return viewById;
    }

    public SharedPreferences getConfig() {
        return config;
    }

    public SharedPreferences.Editor getCfgEdit() {
        return cfgEdit;
    }

    public boolean sendPermissions(String[] permissionsName,String[] permissions){
        if (permissions.length != permissionsName.length || permissions.length ==0)
            return false;
        ArrayList<String> permissionList = new ArrayList<>(permissions.length);
        ArrayList<String> permissionNameList = new ArrayList<>(permissions.length);
        permissionNameList.add("由于安全原因，你需要手动允许以下授权：");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            for (int i = 0; i < permissions.length; i++) {
                if (ActivityCompat.checkSelfPermission(getContext(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permissions[i]);
                    permissionNameList.add(" +" + permissionsName[i]);
                }
            }
        }
        permissionsName = permissionNameList.toArray(new String[0]);
        permissions = permissionList.toArray(new String[0]);
        String[] finalPermissions = permissions;
        AlertDialog.Builder permissionDialog = new AlertDialog.Builder(getContext());
        permissionDialog.setTitle("申请授权");
        permissionDialog.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, permissionsName),null);
        permissionDialog.setPositiveButton("同意", (dialog, which) -> ActivityCompat.requestPermissions(this, finalPermissions, REQUEST_PERMISSION_CODE));
        permissionDialog.setNegativeButton("取消", (dialog, which) -> permissionError(finalPermissions));
        if (permissionList.size()!=0)
            permissionDialog.show();
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            ArrayList<String> arrayList = new ArrayList<>(permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                if (ActivityCompat.checkSelfPermission(getContext(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    arrayList.add(permissions[i]);
                }
            }
            if (arrayList.size() == 0) {
                permissionSuccessful();
            } else {
                permissionError(arrayList.toArray(new String[0]));
                }
        }
    }

    public void permissionSuccessful(){
        Toast.makeText(getContext(),"授权成功!",Toast.LENGTH_SHORT).show();
    }

    public void permissionError(String[] permissions){
        new AlertDialog.Builder(getContext()).setTitle("很抱歉").setMessage("此权限为软件的基础权限，如果不允许，可能导致软件崩溃，是否重新授权？")
                .setPositiveButton("跳转设置", (dialog, which) -> {
                    startActivity(getAppDetailSettingIntent());
                    System.exit(-1);
                }).setNegativeButton("关闭应用", (dialog, which) -> {
                    System.exit(-1);
        }).setCancelable(false)
                .show();
    }
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }

}
