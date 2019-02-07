package top.fksoft.mediaSyncPlayer.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import top.fksoft.mediaSyncPlayer.utils.dao.BaseWindow;

public abstract class BaseActivity extends AppCompatActivity implements BaseWindow {
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

    public void startPermissionsAllow(){

    }
}
