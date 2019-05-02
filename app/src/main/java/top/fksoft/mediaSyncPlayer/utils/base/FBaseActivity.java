package top.fksoft.mediaSyncPlayer.utils.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import top.fksoft.mediaSyncPlayer.Config.SoftApplication;
import top.fksoft.test.android.dao.BaseActivity;

public abstract class FBaseActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SoftApplication.addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoftApplication.removeActivity(this);
    }
}
