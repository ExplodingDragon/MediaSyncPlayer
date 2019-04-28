package top.fksoft.mediaSyncPlayer.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import top.fksoft.mediaSyncPlayer.R;

import static android.content.Context.MODE_PRIVATE;

public class SoftPrefFragment extends PreferenceFragmentCompat {

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences("soft_set", MODE_PRIVATE);
    }
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        getPreferenceManager().setSharedPreferencesName("soft_set");
        addPreferencesFromResource(R.xml.pref_soft_setting);
    }
}
