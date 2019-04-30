package top.fksoft.mediaSyncPlayer.utils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.mediaSyncPlayer.utils.BitmapUtils;

public class PlayFootView extends LinearLayout {
    private Context context;
    private OvalProgressBar ovalProgress;
    private TextView musicImageId;

    public PlayFootView(Context context) {
        super(context);
        init(context);
    }

    public PlayFootView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayFootView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayFootView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        addView(PlayFootView.inflate(context, R.layout.play_foot, null));
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private void initView() {
        ovalProgress = findViewById(R.id.ovalProgress);
        musicImageId = findViewById(R.id.musicImageId);
        ovalProgress.setProgress(30);
        setMusicImage(((BitmapDrawable)getResources().getDrawable(R.mipmap.bg)).getBitmap());
    }

    private void setMusicImage(Bitmap bitmap) {
        if (bitmap==null) {
            bitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.icon)).getBitmap();
        }
        BitmapDrawable background = new BitmapDrawable(BitmapUtils.getOvalBitmap(bitmap));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            musicImageId.setBackground(background);
        }else {
            musicImageId.setBackgroundDrawable(background);
        }
    }
}
