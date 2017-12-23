package xiaogao.zjut.tabbaishuo.main.activity.my;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.InfoCommonAdapter;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.bean.PersonBaseInfoBean;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityBaseInfo;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityZobz;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivitySetNickname;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivitySetOccuption;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityBaseInfo;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityZobz;

/**
 * Created by Administrator on 2017/12/8.
 */

public class ActivityBaseInfo extends MyBaseBindPresentActivity<PresenterActivityBaseInfo> implements IUIActivityBaseInfo, View.OnClickListener {
    @Inject
    PresenterActivityBaseInfo mPresenter;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.caoZuo)
    ImageView caoZuo;
    @Bind(R.id.head)
    RelativeLayout head;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    InfoCommonAdapter mAdapter;
    List<String> lefts;
    List<String> rights;
    OptionsPickerView pvOptions;

    private final int CODE_CHANGE_NICKNAME = 0;
    private final int CODE_SET_OCCUPTION = 1;

    @Override
    public PresenterActivityBaseInfo getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_base_info;
    }

    @Override
    protected void initActivity(View var1) {
        ButterKnife.bind(this);
        hideTitleBar();
        lefts = Arrays.asList(getResources().getStringArray(R.array.base_info));
        rights = new ArrayList<>();
        for (int i = 0; i < lefts.size(); i++) {
            rights.add("");
        }
        rights.set(1, "女");
        initView();
        loadInfo();
    }

    private void loadInfo() {
        PersonBaseInfoBean bean = new PersonBaseInfoBean();
        bean.gender = "女";
        bean.nickname = "千千厥歌";

        rights.set(0, bean.nickname);
        rights.set(1, bean.gender);
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        back.setOnClickListener(this);
        caoZuo.setVisibility(View.GONE);
        title.setText(R.string.base_info);
        mAdapter = new InfoCommonAdapter(this, lefts, rights);
        mAdapter.setmOnItemClickListener(new InfoCommonAdapter.OnItemClickListener() {
            Intent intent = new Intent();

            @Override
            public void onItemClick(int index) {
                switch (index) {
                    case 0: //修改昵称
                        intent.setClass(ActivityBaseInfo.this, ActivitySetNickname.class);
                        startActivityForResult(intent, CODE_CHANGE_NICKNAME);
                        break;
                    case 1:  //无法修改性别
                        break;
                    case 2:  //出生日期
                        selectTime();
                        break;
                    case 3:  //身高
                        break;
                    case 4:  //职业
                        intent.setClass(ActivityBaseInfo.this, ActivitySetOccuption.class);
                        startActivityForResult(intent, CODE_SET_OCCUPTION);
                        break;
                    case 5:  //年收入

                        break;
                    case 6:  //现居住地
                        selectAddressNow();
                        break;
                    case 7:  //户籍
                        break;
                    case 8:  //婚姻状况
                        break;
                    case 9:  //期望结婚时间
                        break;
                    default:

                        break;
                }
            }
        });
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ms);
        recyclerView.setAdapter(mAdapter);
    }

    private void selectAddressNow() {
//        if (pvOptions == null) {
//            pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
//                @Override
//                public void onOptionsSelect(int options1, int option2, int options3, View v) {
//                    //返回的分别是三个级别的选中位置
//                    String tx = options1Items.get(options1).getPickerViewText()
//                            + options2Items.get(options1).get(option2)
//                            + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
//                    tvOptions.setText(tx);
//                }
//            })
//                    .setSubmitText("确定")//确定按钮文字
//                    .setCancelText("取消")//取消按钮文字
//                    .setTitleText("城市选择")//标题
//                    .setSubCalSize(18)//确定和取消文字大小
//                    .setTitleSize(20)//标题文字大小
//                    .setTitleColor(Color.BLACK)//标题文字颜色
//                    .setSubmitColor(R.color.color_3bc2ff)//确定按钮文字颜色
//                    .setCancelColor(R.color.color_3bc2ff)//取消按钮文字颜色
//                    .setTitleBgColor(R.color.color_f3f3f3)//标题背景颜色 Night mode
//                    .setBgColor(R.color.white)//滚轮背景颜色 Night mode
//                    .setContentTextSize(18)//滚轮文字大小
//                    .setLinkage(false)//设置是否联动，默认true
//                    .setLabels("省", "市", "区")//设置选择的三级单位
//                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                    .setCyclic(false, false, false)//循环与否
//                    .setSelectOptions(1, 1, 1)  //设置默认选中项
//                    .setOutSideCancelable(false)//点击外部dismiss default true
//                    .isDialog(true)//是否显示为对话框样式
//                    .build();
//        }
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
    }

    private void selectTime() {
        boolean[] type = {true, true, true, false, false, false};

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                rights.set(2, (date.getYear() + 1900) + "-" + date.getMonth() + "-" + date.getDate());
                mAdapter.notifyDataSetChanged();
            }
        })
                .setBackgroundId(R.color.white)
                .setCancelColor(R.color.color_white_0fffffff)
                .setTitleColor(R.color.color_3bc2ff)
                .setType(type)
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

