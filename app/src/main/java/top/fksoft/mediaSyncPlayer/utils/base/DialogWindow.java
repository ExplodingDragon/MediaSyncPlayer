package top.fksoft.mediaSyncPlayer.utils.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import top.fksoft.mediaSyncPlayer.R;

public abstract class DialogWindow<T> {
    private final AlertDialog.Builder alert;
    private final Context context;
    private final Intent intent;
    private final View view;
    private Toolbar toolbar;
    private LinearLayout rootView;
    private AlertDialog dialog;
    private T listener;

    public DialogWindow(Context context) {
        this(context, null);
    }

    public DialogWindow(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        alert = new AlertDialog.Builder(context, R.style.FileChooseDialogStyle);
        view = View.inflate(context, R.layout.base_dialog, null);
        toolbar = view.findViewById(R.id.toolbar);
        rootView = view.findViewById(R.id.rootView);
        rootView.addView(View.inflate(context, initLayout(), null));
        toolbar.setTitle(initTitle());
        alert.setView(view);
        toolbar.setNavigationOnClickListener(v -> dialog.dismiss());
        alert.setOnCancelListener(dialog -> onDestroy());
    }

    protected void onDestroy(){

    }

    public void setTitle(String title){
        toolbar.setTitle(title);
    }

    protected abstract int initLayout();

    protected  String initTitle(){
        return "Alert Dialog";
    }

    protected abstract void initData();

    protected abstract void initView();

    public void setDialogListener(T listener){
        this.listener = listener;
    }

    public T getDialogListener() {
        return listener;
    }

    public void show(boolean cancel){
        alert.setCancelable(cancel);
        dialog = alert.show();
        initView();
        initData();

    }



    public Intent getIntent() {
        return intent;
    }


}
