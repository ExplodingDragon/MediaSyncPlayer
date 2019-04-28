package top.fksoft.mediaSyncPlayer.activity;

import android.view.View;
import android.widget.FrameLayout;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.mediaSyncPlayer.fragment.SoftPrefFragment;
import top.fksoft.mediaSyncPlayer.utils.base.BaseActionBarActivity;

public class SettingsActivity extends BaseActionBarActivity {
    private FrameLayout fragmentLayout;

    @Override
    protected int initTitle() {
        return R.string.setting;
    }

    @Override
    public void initView() {
        super.initView();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout,new SoftPrefFragment()).commit();
    }

    @Override
    protected int initChildLayout() {
        return R.layout.activity_settings;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
