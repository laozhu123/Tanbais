package xiaogao.zjut.tabbaishuo.main.activity.im;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.R;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ConversationFragmentSub extends ConversationFragment implements View.OnClickListener {

    private View mBack;
    private TextView mNickName;
    private View mCaoZuo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        mBack = view.findViewById(R.id.back);
        mNickName = (TextView) view.findViewById(R.id.nickName);
        mCaoZuo = view.findViewById(R.id.caoZuo);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.caoZuo:

                break;
            default:
                break;
        }
    }
}
