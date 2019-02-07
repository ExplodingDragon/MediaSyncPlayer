package top.fksoft.mediaSyncPlayer.utils.dao;

import android.app.Activity;
import android.view.View;

public interface BaseWindow extends View.OnClickListener {

    /**
     * <p>
     *     用于得到渲染的界面ID，
     *     在界面初始化前调用
     * </p>
     * @return 界面ID
     */
    int initLayout();

    /**
     * <p>用于初始化界面的数据</p>
     */
    void initData();

    /**
     * <p>
     *     用于Layout与代码的绑定和处理，
     *     在initData（）前调用.
     * </p>
     */
    void initView();

    /**
     * <p>得到当前UI的上下文</p>
     * @return UI上下文
     */
    Activity getContext();

    <T extends View> T findViewById(int id);
}
