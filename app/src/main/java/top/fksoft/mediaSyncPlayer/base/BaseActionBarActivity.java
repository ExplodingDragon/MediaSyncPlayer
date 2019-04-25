package top.fksoft.mediaSyncPlayer.base;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.test.android.dao.BaseActivity;

public abstract class BaseActionBarActivity extends BaseActivity {
    private Toolbar toolbar;
    private LinearLayout children;



    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        children = findViewById(R.id.children);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        children.addView(View.inflate(getContext(),initChildLayout(),null));
        setTitle(initTitle());
    }

    protected abstract int initTitle();

    protected abstract int initChildLayout();

    @Override
    public int initLayout() {
        return R.layout.base_toolbar;
    }



}
