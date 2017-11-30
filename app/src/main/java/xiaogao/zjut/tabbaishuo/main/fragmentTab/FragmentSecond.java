package xiaogao.zjut.tabbaishuo.main.fragmentTab;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import javax.inject.Inject;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.fragment.MyBindPresentFragment;
import xiaogao.zjut.tabbaishuo.injecter.component.FragmentComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIFragmentSecond;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterFragmentSecond;

/**
 * Created by Administrator on 2017/11/18.
 */

public class FragmentSecond extends MyBindPresentFragment<PresenterFragmentSecond> implements IUIFragmentSecond {

    @Inject
    PresenterFragmentSecond mPresenter;

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void inject(FragmentComponent pFragmentComponent) {
        pFragmentComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initFragment(View view) {
        //会话列表
        ConversationListFragment conversationListFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist").appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//设置系统会话，该会话非聚合显示
                .build();
        conversationListFragment.setUri(uri);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.rong_container, conversationListFragment);
        transaction.commit();
    }


}
