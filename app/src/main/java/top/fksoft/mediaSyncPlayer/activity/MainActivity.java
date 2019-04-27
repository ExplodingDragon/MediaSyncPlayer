package top.fksoft.mediaSyncPlayer.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.test.android.dao.BaseActivity;

public class MainActivity extends BaseActivity {


    private Toolbar toolbar;

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {

    }
}
