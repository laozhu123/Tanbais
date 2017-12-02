package xiaogao.zjut.tabbaishuo.main.activity.im;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.R;
import io.rong.imlib.model.UserInfo;
import xiaogao.zjut.tabbaishuo.interfaces.IUIFragmentConversation;
import xiaogao.zjut.tabbaishuo.main.activity.ActivityJuBao;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterFragmentConversation;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ConversationFragmentSub extends ConversationFragment implements View.OnClickListener, IUIFragmentConversation {

    private View mBack;
    private TextView mNickName;
    private View mCaoZuo;
    private PresenterFragmentConversation mPresenter;

    private ViewStub mVsDialog1;
    private ViewStub mVsDialog2;
    private View mDialog1;
    private View mDialog2;

    final int JUBAOREQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        mBack = view.findViewById(R.id.back);
        mNickName = (TextView) view.findViewById(R.id.nickName);
        mCaoZuo = view.findViewById(R.id.caoZuo);
        mVsDialog1 = (ViewStub) view.findViewById(R.id.dialog1);
        mVsDialog2 = (ViewStub) view.findViewById(R.id.dialog2);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBack.setOnClickListener(this);
        mCaoZuo.setOnClickListener(this);
        mPresenter = new PresenterFragmentConversation(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.caoZuo:
                showFirstDialog();
                break;
            case R.id.seeInfo:
                break;
            case R.id.lahei1:
                showSecondDialog();
                break;
            case R.id.jubao:
                startActivityForResult(new Intent(getActivity(), ActivityJuBao.class),JUBAOREQUEST);
                mDialog1.setVisibility(View.GONE);
                break;
            case R.id.bg1:
            case R.id.cancel1:
                mDialog1.setVisibility(View.GONE);
                break;
            case R.id.lahei2:
                //Fixme 真的拉黑了哦
                getActivity().finish();
                break;
            case R.id.bg2:
            case R.id.cancel2:
                mDialog2.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void showFirstDialog() {
        if (mDialog2 != null && mDialog2.getVisibility() == View.VISIBLE) {
            mDialog2.setVisibility(View.GONE);
        }
        if (mDialog1 != null) {
            mDialog1.setVisibility(View.VISIBLE);
        } else {
            mDialog1 = mVsDialog1.inflate();
            initDialog1();
        }
    }

    private void showSecondDialog() {
        if (mDialog1 != null && mDialog1.getVisibility() == View.VISIBLE) {
            mDialog1.setVisibility(View.GONE);
        }
        if (mDialog2 != null) {
            mDialog2.setVisibility(View.VISIBLE);
        } else {
            mDialog2 = mVsDialog2.inflate();
            initDialog2();
        }
    }

    private void initDialog1() {
        mDialog1.findViewById(R.id.seeInfo).setOnClickListener(this);
        mDialog1.findViewById(R.id.lahei1).setOnClickListener(this);
        mDialog1.findViewById(R.id.jubao).setOnClickListener(this);
        mDialog1.findViewById(R.id.cancel1).setOnClickListener(this);
        mDialog1.findViewById(R.id.bg1).setOnClickListener(this);
    }

    private void initDialog2() {
        mDialog2.findViewById(R.id.bg2).setOnClickListener(this);
        mDialog2.findViewById(R.id.lahei2).setOnClickListener(this);
        mDialog2.findViewById(R.id.cancel2).setOnClickListener(this);
    }


    @Override
    protected void initFragment(Uri uri) {
        super.initFragment(uri);
        if (uri != null) {
            String mTargetId = uri.getQueryParameter("targetId");
//            mPresenter.getNickName(mTargetId);  //Fixme 请求获取nickname

        }

        mNickName.setText("小糕");  //Fixme 上面那个改好之后将我删了
    }

    @Override
    public void setNickName(String nickName) {
        mNickName.setText(nickName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case JUBAOREQUEST:
                //FIXME 显示举报成功
                break;
            default:
                break;
        }
    }
}
