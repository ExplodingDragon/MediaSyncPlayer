package top.fksoft.mediaSyncPlayer.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import top.fksoft.mediaSyncPlayer.Config.SoftApplication;
import top.fksoft.mediaSyncPlayer.R;
import top.fksoft.mediaSyncPlayer.activity.SongListActivity;
import top.fksoft.mediaSyncPlayer.bean.MusicListBean;
import top.fksoft.mediaSyncPlayer.io.SongSql;
import top.fksoft.mediaSyncPlayer.utils.AndroidUtils;
import top.fksoft.mediaSyncPlayer.utils.base.MainBaseFragment;

import java.util.ArrayList;
import java.util.List;

public class PlayListFragment extends MainBaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "PlayListFragment";
    private ListView musicCategories;
    private ListBean[] bean;
    private ImageView extendedImage;
    private TextView playlistSize; //歌单文字选项
    private ImageView addSongList; //添加新歌单
    private ImageView songListConfig;//管理歌单
    private ListView musicSongLists; //歌单列表

    private List<MusicListBean> listBeans = new ArrayList<>();
    private SongListAdapter songListAdapter;


    private boolean showDeleted = false;//显示删除按钮标志

    @Override
    public void initData() {
        songListAdapter = new SongListAdapter(getContext(), R.layout.music_list_item, listBeans);
        musicSongLists.setAdapter(songListAdapter);
        updateSongList();
    }

    @Override
    public void initView() {
        bean = new ListBean[]{
                new ListBean(R.mipmap.playlist_local, getString(R.string.playlist_localSong, 0)),
                new ListBean(R.mipmap.playlist_history, getString(R.string.playlist_history, 0)),
                new ListBean(R.mipmap.playlist_like, getString(R.string.playlist_like, 0))
        };
        musicCategories = findViewById(R.id.music_categories);
        extendedImage = findViewById(R.id.ExtendedImage);
        playlistSize = findViewById(R.id.playlist_size);
        addSongList = findViewById(R.id.addSongList);
        songListConfig = findViewById(R.id.songList_config);
        musicSongLists = findViewById(R.id.music_song_lists);

        musicCategories.setAdapter(new ItemAdapter(getContext(), R.layout.playlist_item_song, bean));
        musicCategories.setOnItemClickListener(this::onItemClick);
        playlistSize.setOnClickListener(this::onClick);
        extendedImage.setOnClickListener(this::onClick);
        addSongList.setOnClickListener(this::onClick);//添加歌单
        songListConfig.setOnClickListener(this::onClick);//管理歌单

    }

    private void updateSongList() {
        listBeans.clear();
        listBeans.addAll(SongSql.getSongList());
        songListAdapter.notifyDataSetChanged();
    } //刷新歌单列表


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playlist_size:
            case R.id.ExtendedImage:
                showSongLists();
                break;
            case R.id.addSongList:
                addSongList();
                break;
            case R.id.songList_config:
                showDeleted = !showDeleted;
                songListAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void addSongList() {//添加新歌单
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(R.string.music_list_add_title);
        EditText editText = new EditText(getContext());
        alert.setView(editText);
        alert.setPositiveButton(R.string.music_list_add_btn_1, (dialog, which) -> {
            String title = editText.getText().toString();
            if (!SongSql.hasSongList(title)) {
                SongSql.addSongList(title);
                updateSongList();
            } else {
                showToast(R.string.song_list_add_fail);
            }
        });
        alert.setNegativeButton(R.string.cancel, null);
        alert.setCancelable(false);
        alert.show();
    }//添加新歌单

    private void showSongLists() {//显示扩展列表
        AnimationDrawable frameAnim;
        if (musicSongLists.getVisibility() == View.VISIBLE) {
            musicSongLists.setVisibility(View.GONE);
            frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.arrow_close);
        } else {
            musicSongLists.setVisibility(View.VISIBLE);
            frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.arrow_open);
        }
        extendedImage.setImageDrawable(frameAnim);
        frameAnim.start();
    }//显示扩展列表

    @Override
    public int title() {
        return R.string.playList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                SongListActivity.startActivity(getContext(), new MusicListBean(getString(R.string.playlist_localSong_str), SongSql.LOCAL_TABLE, SongSql.LOCAL_IMG_SHA1));
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }


    private void songItemClick(int i) {
        if (showDeleted) {
            showDeleted = false;
            songListAdapter.notifyDataSetChanged();
        } else {
            SongListActivity.startActivity(getContext(), listBeans.get(i));
        }
    }

    private void songItemConfigClick(int i) {
        MusicListBean bean = listBeans.get(i);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(R.string.config_song_list);
        View view = View.inflate(getContext(), R.layout.playlist_songlist_config, null);
        dialog.setView(view);
        ImageView songImage = view.findViewById(R.id.song_image);
        EditText songListName = view.findViewById(R.id.songListName);
        songImage.setImageBitmap(BitmapFactory.decodeFile(SoftApplication.getPictureCachePath(bean.getImageSha1()).getAbsolutePath()));
        songListName.setText(bean.getTitle());
        dialog.setCancelable(false);
        dialog.setNeutralButton(R.string.delete, (dialog1, which) -> {
            deleteSongList(bean);
            updateSongList();
        });
        dialog.setNegativeButton(R.string.save, (dialog12, which) -> {
            String title = songListName.getText().toString().trim();
            if (title.length() == 0) {
                showToast(R.string.save_fail);
            } else {
                bean.setTitle(title);
                bean.save();
            }
            updateSongList();
        });
        songImage.setOnClickListener(v -> showToast(R.string.bye));
        dialog.setPositiveButton(R.string.exit, null);
        dialog.show();
    }

    private void deleteSongList(MusicListBean bean) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.delete_song_list)
                .setMessage(getString(R.string.delete_song_list_message, bean.getTitle()))
                .setNegativeButton(R.string.agree, (dialog1, which1) -> {
                    SongSql.deleteSongList(bean);
                    updateSongList();
                })
                .setPositiveButton(R.string.exit, null)
                .show();
    } //删除对话框

    public class SongListAdapter extends ArrayAdapter<MusicListBean> {
        private final List<MusicListBean> listBeans;

        public SongListAdapter(@NonNull Context context, int resource, List<MusicListBean> listBeans) {
            super(context, resource, listBeans);
            this.listBeans = listBeans;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.music_list_item, parent, false);
            ImageView listImage = convertView.findViewById(R.id.list_image);
            TextView listText = convertView.findViewById(R.id.list_text);
            TextView listTextSize = convertView.findViewById(R.id.list_text_size);
            ImageView itemDelete = convertView.findViewById(R.id.item_delete);
            MusicListBean bean = listBeans.get(position);
            listImage.setImageBitmap(BitmapFactory.decodeFile(SoftApplication.getPictureCachePath(bean.getImageSha1()).getAbsolutePath()));
            listText.setText(bean.getTitle());
            listTextSize.setText(getString(R.string.song_list_item_size, bean.getListSize()));
            convertView.findViewById(R.id.onClick).setOnClickListener(v -> songItemClick(position));
            if (showDeleted) {
                itemDelete.setVisibility(View.VISIBLE);
                itemDelete.setOnClickListener(v -> songItemConfigClick(position));
            } else {
                itemDelete.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            playlistSize.setText(getString(R.string.playlist_songList_1, listBeans.size()));
            ViewGroup.LayoutParams layoutParams = musicSongLists.getLayoutParams();
            layoutParams.height = AndroidUtils.dip2px(getContext(), 62 * listBeans.size());
            musicSongLists.setLayoutParams(layoutParams);
        }
    } //歌单适配器


    public class ItemAdapter extends ArrayAdapter<ListBean> {
        private ListBean[] item;

        public ItemAdapter(@NonNull Context context, int resource, ListBean[] item) {
            super(context, resource, item);
            this.item = item;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.playlist_item_song, parent, false);
            ImageView playlistItem1Image = inflate.findViewById(R.id.playlist_item1_image);
            TextView playlistItem1Text = inflate.findViewById(R.id.playlist_item1_text);
            ListBean listBean = item[position];
            playlistItem1Image.setImageResource(listBean.getResId());
            playlistItem1Text.setText(listBean.getTitle());
            return inflate;
        }

    } //菜单适配器

    public static class ListBean {
        private int resId;
        private String title;

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
    } //菜单实体类

    @Override
    public int initLayout() {
        return R.layout.main_playlist;
    }


}
