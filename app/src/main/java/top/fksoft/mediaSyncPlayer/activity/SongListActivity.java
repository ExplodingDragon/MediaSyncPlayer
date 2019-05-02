package top.fksoft.mediaSyncPlayer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.fksoft.mediaSyncPlayer.Config.SoftApplication;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.mediaSyncPlayer.bean.MusicListBean;
import top.fksoft.mediaSyncPlayer.fragment.SoftPrefFragment;
import top.fksoft.mediaSyncPlayer.utils.AndroidUtils;
import top.fksoft.mediaSyncPlayer.utils.BitmapUtils;
import top.fksoft.mediaSyncPlayer.utils.view.PlayFootView;
import top.fksoft.test.android.dao.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;

public class SongListActivity extends BaseActivity {
    private static final String TAG = "SongListActivity";

    private AppBarLayout appBar;
    private ImageView headerImage;
    private Toolbar toolbar;
    private RecyclerView listView;
    private PlayFootView playFoot;
    private TextView noneSong;
    private SharedPreferences softSet;
    private TextView navigationBar;


    @Override
    public void onStart() {
        super.onStart();
        configStatus2Nav();// 处理导航栏
        MusicListBean bean = getListBean();
        if (bean == null) {
            showToast(R.string.listActivity_load_error);
            finish();
        }
        setTitle(bean.getTitle());
        Observable.create((ObservableOnSubscribe<Bitmap>) emitter -> {
            File pictureFile = SoftApplication.getPictureCachePath(bean.getImageSha1());
            if (pictureFile.isFile()) {
                Bitmap bitmap;
                File file = new File(getCacheDir(), bean.getImageSha1());
                if (!file.isFile()) {
                    bitmap = BitmapUtils.rsBlur(getContext(), BitmapFactory.decodeFile(pictureFile.getAbsolutePath()), 20, 0.2f);
                    emitter.onNext(bitmap);
                    try {
                        FileOutputStream outputStream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    } catch (Exception e) {
                        Log.w(TAG, "initData: ", e);
                    }
                } else {
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                }
                emitter.onNext(bitmap);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(bitmap -> headerImage.setImageBitmap(bitmap));

    }

    private MusicListBean getListBean() {
        Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
       try{
           MusicListBean result = (MusicListBean) intent.getSerializableExtra("DATA");
           return result;
       }catch (Exception e){
        return null;
       }
    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        softSet = SoftPrefFragment.getSharedPreferences(getContext());
        AndroidUtils.immersive(getContext()); //沉浸体验

        appBar = findViewById(R.id.appBar);
        headerImage = findViewById(R.id.header_image);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        listView = findViewById(R.id.listView);
        playFoot = findViewById(R.id.playFoot);
        noneSong = findViewById(R.id.noneSong);

        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new RecyclerView.ViewHolder(new TextView(getContext())) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });
    }

    @Override
    public int initLayout() {
        return R.layout.activity_song_item;
    }

    @Override
    public void onClick(View v) {

    }


    private void configStatus2Nav() {
        navigationBar = findViewById(R.id.navigationBar);
        LinearLayout.LayoutParams navigationParams = (LinearLayout.LayoutParams) navigationBar.getLayoutParams();
        navigationParams.height = AndroidUtils.getNavigationBarHeight2(getContext());
        navigationBar.setLayoutParams(navigationParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            if (!softSet.getBoolean("status", false)) {
                CollapsingToolbarLayout.LayoutParams statusParams = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
                statusParams.topMargin = AndroidUtils.getStatusBarHeight(getContext());
                toolbar.setLayoutParams(statusParams);
            }
            if (!softSet.getBoolean("navigation", false)) {
                navigationBar.setVisibility(View.VISIBLE);
            }
        }



    } //处理状态栏和导航栏

    public static void startActivity(Context context, MusicListBean bean) {
        Intent intent = new Intent(context, SongListActivity.class);
        if (bean != null) {
            intent.putExtra("DATA", bean);
        } else {
            Log.w(TAG, "startActivity: null Data.");
        }
        context.startActivity(intent);
    }
}
