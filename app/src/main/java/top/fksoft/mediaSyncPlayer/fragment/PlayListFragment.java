package top.fksoft.mediaSyncPlayer.fragment;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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
    private ListBean[] bean;
    private ImageView extendedImage;
    private TextView playlistSize; //歌单文字选项
    private ImageView addSongList; //添加新歌单
    private ImageView songListConfig;//管理歌单
    private RecyclerView musicSongLists; //歌单列表

    @Override
    public int initLayout() {
        return R.layout.main_playlist;
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
        extendedImage = findViewById(R.id.ExtendedImage);
        playlistSize = findViewById(R.id.playlist_size);
        addSongList = findViewById(R.id.addSongList);
        songListConfig = findViewById(R.id.songList_config);
        musicSongLists = findViewById(R.id.music_song_lists);


        musicCategories.setAdapter(new ItemAdapter(getContext(),R.layout.playlist_item_song,bean));
        playlistSize.setOnClickListener(this::onClick);
        extendedImage.setOnClickListener(this::onClick);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playlist_size:
            case R.id.ExtendedImage:
                showSongLists();
                break;
        }
    }

    private void showSongLists() {
        AnimationDrawable frameAnim;
        if (musicSongLists.getVisibility() == View.VISIBLE){
            musicSongLists.setVisibility(View.GONE);
            frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.arrow_close);
        }else {
            musicSongLists.setVisibility(View.VISIBLE);
            frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.arrow_open);
        }
        extendedImage.setImageDrawable(frameAnim);
        frameAnim.start();
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
