package top.fksoft.mediaSyncPlayer.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.mediaSyncPlayer.bean.MusicListBean;
import top.fksoft.test.android.dao.BaseActivity;

public class ListActivity extends BaseActivity {


    private AppBarLayout appBar;
    private ImageView headerImage;
    private Toolbar toolbar;

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        appBar = findViewById(R.id.appBar);
        headerImage = findViewById(R.id.header_image);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_song_item;
    }

    @Override
    public void onClick(View v) {

    }

    public static void startActivity(Context context, MusicListBean bean){
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra("DATA",bean.toString());
        context.startActivity(intent);
    }
}
