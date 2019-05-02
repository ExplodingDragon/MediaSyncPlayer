package top.fksoft.mediaSyncPlayer.utils.base;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.mediaSyncPlayer.fragment.SoftPrefFragment;
import top.fksoft.mediaSyncPlayer.utils.AndroidUtils;

public abstract class BaseActionBarActivity extends FBaseActivity{
    private Toolbar toolbar;
    private LinearLayout children;
    private TextView statusBar;
    private SharedPreferences softSet;


    @Override
    public void initView() {
        AndroidUtils.immersive(getContext());
        toolbar = findViewById(R.id.toolbar);
        children = findViewById(R.id.children);
        statusBar = findViewById(R.id.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        children.addView(View.inflate(getContext(),initChildLayout(),null));
        setTitle(initTitle());
        softSet = SoftPrefFragment.getSharedPreferences(getContext());
        setStatusSize();
    }

    protected abstract int initTitle();

    protected abstract int initChildLayout();

    @Override
    public int initLayout() {
        return R.layout.base_toolbar;
    }
    private void setStatusSize() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) statusBar.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            params.height = AndroidUtils.getStatusBarHeight(getContext());
            statusBar.setLayoutParams(params);
            if (!softSet.getBoolean("status",false)) {
                statusBar.setVisibility(View.VISIBLE);
            }
        }

    }


}
