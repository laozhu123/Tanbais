package xgn.com.basesdk.commonui.view;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import xgn.com.basesdk.base.app.CoreApplication;

/**
 * Created by huluzi on 2017/8/11.
 */

public class SPUtils {
    private static Map<String, SPUtils> sSPMap = new HashMap();
    private SharedPreferences sp;

    public static SPUtils getInstance() {
        return getInstance("");
    }

    public static SPUtils getInstance(String spName) {
        if(isSpace(spName)) {
            spName = "spUtils";
        }

        SPUtils sp = (SPUtils)sSPMap.get(spName);
        if(sp == null) {
            sp = new SPUtils(spName);
            sSPMap.put(spName, sp);
        }

        return sp;
    }

    private SPUtils(String spName) {
        this.sp = CoreApplication.getInstance().getSharedPreferences(spName, 0);
    }

    public void put(@NonNull String key, @NonNull String value) {
        this.sp.edit().putString(key, value).apply();
    }

    public String getString(@NonNull String key) {
        return this.getString(key, "");
    }

    public String getString(@NonNull String key, @NonNull String defaultValue) {
        return this.sp.getString(key, defaultValue);
    }

    public void put(@NonNull String key, int value) {
        this.sp.edit().putInt(key, value).apply();
    }

    public int getInt(@NonNull String key) {
        return this.getInt(key, -1);
    }

    public int getInt(@NonNull String key, int defaultValue) {
        return this.sp.getInt(key, defaultValue);
    }

    public void put(@NonNull String key, long value) {
        this.sp.edit().putLong(key, value).apply();
    }

    public long getLong(@NonNull String key) {
        return this.getLong(key, -1L);
    }

    public long getLong(@NonNull String key, long defaultValue) {
        return this.sp.getLong(key, defaultValue);
    }

    public void put(@NonNull String key, float value) {
        this.sp.edit().putFloat(key, value).apply();
    }

    public float getFloat(@NonNull String key) {
        return this.getFloat(key, -1.0F);
    }

    public float getFloat(@NonNull String key, float defaultValue) {
        return this.sp.getFloat(key, defaultValue);
    }

    public void put(@NonNull String key, boolean value) {
        this.sp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(@NonNull String key) {
        return this.getBoolean(key, false);
    }

    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return this.sp.getBoolean(key, defaultValue);
    }

    public void put(@NonNull String key, @NonNull Set<String> values) {
        this.sp.edit().putStringSet(key, values).apply();
    }

    public Set<String> getStringSet(@NonNull String key) {
        return this.getStringSet(key, Collections.<String>emptySet());
    }

    public Set<String> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        return this.sp.getStringSet(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return this.sp.getAll();
    }

    public boolean contains(@NonNull String key) {
        return this.sp.contains(key);
    }

    public void remove(@NonNull String key) {
        this.sp.edit().remove(key).apply();
    }

    public void clear() {
        this.sp.edit().clear().apply();
    }

    private static boolean isSpace(String s) {
        if(s == null) {
            return true;
        } else {
            int i = 0;

            for(int len = s.length(); i < len; ++i) {
                if(!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }
}
