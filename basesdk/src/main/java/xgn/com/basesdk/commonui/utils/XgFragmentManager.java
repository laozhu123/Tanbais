package xgn.com.basesdk.commonui.utils;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import java.util.List;

import xgn.com.basesdk.R;
import xgn.com.basesdk.base.app.CoreApplication;

/**
 * @author huangwenqiang
 * @date on 2017/7/26
 * @desc fragment的管理类
 */


public class XgFragmentManager {

    private String lastFragmentTag;

    public void setLastFragmentTag(String lastFragmentTag) {
        this.lastFragmentTag = lastFragmentTag;
    }

    private FragmentManager mFragmentManager;

    public XgFragmentManager(FragmentActivity context) {
        this.mFragmentManager = context.getSupportFragmentManager();
    }

    public XgFragmentManager(Fragment fragment) {
        this.mFragmentManager = fragment.getChildFragmentManager();
    }


    public void reSet() {
        lastFragmentTag = null;
    }

    public void removeAllFragments() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (int i = 0; fragments != null && i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            removeFragment(fragment);
        }
    }

    /**
     * activity 只有一个fragment时 使用。不要判断上一个fragment
     *
     * @param key
     * @param bundle
     */
    public void switchFragment(String key, Bundle bundle) {
        Fragment fragment = mFragmentManager.findFragmentByTag(key);
        if (fragment == null) {
            fragment = Fragment.instantiate(CoreApplication.getInstance(), key, bundle);
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.fl_container, fragment, key);

        lastFragmentTag = key;
        ft.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    public void loadFragment(@IdRes
                                     int containerViewId, Fragment targetFragment) {
        Fragment fragment = targetFragment;
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(containerViewId, fragment, targetFragment.getClass().getName());
        ft.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }


    public void switchFragmentToBackStack(String key, Bundle bundle) {
        Fragment fragment = mFragmentManager.findFragmentByTag(key);
        if (fragment == null) {
            fragment = Fragment.instantiate(CoreApplication.getInstance(), key, bundle);
        }
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.fl_container, fragment, key);
        if (true)
            ft.addToBackStack(key);

        lastFragmentTag = key;
        ft.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    public void switchFragmentToBackStackNoCache(String key, Bundle bundle) {
        Fragment fragment = Fragment.instantiate(CoreApplication.getInstance(), key, bundle);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.fl_container, fragment, key);
        ft.addToBackStack(key);
        lastFragmentTag = key;
        ft.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    //不使用缓存，每次都是new新的
    public void switchFragmentWithoutCache(String key, Bundle bundle) {
        Fragment fragment = Fragment.instantiate(CoreApplication.getInstance(), key, bundle);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.fl_container, fragment, key);  //实际上就是remove()然后add()的合体~
        lastFragmentTag = key;
        ft.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }


    /**
     * fragment 会使用缓存
     *
     * @param key
     * @param bundle
     */
    public void switchFragmentWithCache(String key, Bundle bundle) {
        if (lastFragmentTag == null || !TextUtils.equals(lastFragmentTag, key)) {

            FragmentTransaction ft = mFragmentManager.beginTransaction();
            if (lastFragmentTag != null) {
                ft.hide(mFragmentManager.findFragmentByTag(lastFragmentTag)); //隐藏当前的Fragment，仅仅是设为不可见，并不会销毁
            }

            Fragment fragment = mFragmentManager.findFragmentByTag(key);

            if (fragment == null) {
                fragment = Fragment.instantiate(CoreApplication.getInstance(), key, bundle);
                ft.add(R.id.fl_container, fragment, key);
            } else {
                if (fragment.isHidden())
                    ft.show(fragment);  //显示之前隐藏的Fragment
            }

            lastFragmentTag = key;

            ft.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();
        }
    }


    public void removeFragment(String tag) {
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();
        }
    }

    public void removeFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();
        }
    }

    public void detachLastFragment() {
        if (lastFragmentTag == null || mFragmentManager.findFragmentByTag(lastFragmentTag) == null) {
            return;
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(lastFragmentTag);
        ft.detach(fragment);
        lastFragmentTag = null;
        ft.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    public Fragment getFragment(String key) {
        return mFragmentManager.findFragmentByTag(key);
    }

    public String getLastFragmentTag() {
        return lastFragmentTag;
    }

    public int getBackStackEntryCount() {
        return mFragmentManager.getBackStackEntryCount();
    }

    public String getBackStackEntryName(int Index) {
        return mFragmentManager.getBackStackEntryAt(Index).getName();
    }

}
