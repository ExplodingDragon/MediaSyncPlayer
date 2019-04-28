package top.fksoft.mediaSyncPlayer.fragment;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.mediaSyncPlayer.utils.base.MainBaseFragment;

public class PlayListFragment extends MainBaseFragment {
    private static final String TAG = "PlayListFragment";
    private ListView musicCategories;
    private ImageView image;
    private AnimationDrawable drawable;
    private ListBean[] bean;

    @Override
    public int initLayout() {
        return R.layout.main_fragment_playlist;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        bean = new ListBean[]{
                new ListBean(R.mipmap.playlist_local,getString(R.string.playlist_localSong,0)),
                new ListBean(R.mipmap.playlist_history,getString(R.string.playlist_history,0)),
                new ListBean(R.mipmap.playlist_like,getString(R.string.playlist_like,0))
        };
        musicCategories = findViewById(R.id.music_categories);
        image = findViewById(R.id.image);
        drawable = (AnimationDrawable) getResources().getDrawable(R.drawable.arrow_open);
        image.setImageDrawable(drawable);
        image.setOnClickListener(this::onClick);
        musicCategories.setAdapter(new ItemAdapter(getContext(),R.layout.playlist_item_song,bean));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image:
                drawable.start();
                break;
        }
    }

    @Override
    public int title() {
        return R.string.playList;
    }
    public class ItemAdapter extends ArrayAdapter<ListBean>{
        private ListBean[] item;
        public ItemAdapter(@NonNull Context context, int resource,ListBean[] item) {
            super(context, resource,item);
            this.item = item;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.playlist_item_song, parent, false);
            ImageView playlistItem1Image = inflate.findViewById(R.id.playlist_item1_image);
            TextView playlistItem1Text   = inflate.findViewById(R.id.playlist_item1_text);
            ListBean listBean = item[position];
            playlistItem1Image.setImageResource(listBean.getResId());
            playlistItem1Text.setText(listBean.getTitle());
            return inflate;
        }

    }
    public static class ListBean{
        private int resId;
        private String  title;

        public ListBean(int resId, String title) {
            this.resId = resId;
            this.title = title;
        }

        public int getResId() {
            return resId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
