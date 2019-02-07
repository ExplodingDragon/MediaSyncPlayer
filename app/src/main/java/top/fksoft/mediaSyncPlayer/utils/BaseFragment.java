package top.fksoft.mediaSyncPlayer.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import top.fksoft.mediaSyncPlayer.utils.dao.BaseWindow;

public abstract class BaseFragment<T extends BaseActivity> extends Fragment implements BaseWindow,View.OnClickListener {
    private View contentView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = LayoutInflater.from(getContext()).inflate(initLayout(),container,false);
        return contentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initData();
    }
    @Nullable
    @Override
    public T getContext() {
        return (T) getActivity();
    }

    @Override
    public <T extends View> T findViewById(int id) {
        T viewById = contentView.findViewById(id);
        if(viewById instanceof Button)
            viewById.setOnClickListener(this);
        return viewById;
    }

}
