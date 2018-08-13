package xiaogao.zjut.tabbaishuo.main.activity.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.contants.SharePrefrenceString;
import xiaogao.zjut.tabbaishuo.events.EventLoginSuccess;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityRegisterSecond;
import xiaogao.zjut.tabbaishuo.main.MainActivity;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterActivityRegisterSecond;
import xiaogao.zjut.tabbaishuo.utils.SPHelper;
import xiaogao.zjut.tabbaishuo.views.CircleImageView;

/**
 * Created by Administrator on 2017/12/23.
 */

public class ActivityRegisterSecond extends MyBaseBindPresentActivity<PresenterActivityRegisterSecond> implements IUIActivityRegisterSecond {

    @Inject
    PresenterActivityRegisterSecond mPresenter;
    @Bind(R.id.headIcon)
    CircleImageView headIcon;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.male)
    TextView male;
    @Bind(R.id.female)
    TextView female;
    @Bind(R.id.age)
    TextView age;
    @Bind(R.id.next)
    TextView next;
    TimePickerView pvCustomTime;

    @Override
    public PresenterActivityRegisterSecond getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_register_second;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        hideTitleBar();
        male.setBackground(getResources().getDrawable(R.drawable.selector_male));
        female.setBackground(getResources().getDrawable(R.drawable.selector_female));
        male.setSelected(true);
        female.setSelected(false);
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.back, R.id.male, R.id.female, R.id.age, R.id.next, R.id.headIcon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.headIcon:

                break;
            case R.id.back:
                finish();
                break;
            case R.id.male:
                male.setSelected(true);
                female.setSelected(false);
                break;
            case R.id.female:
                female.setSelected(true);
                male.setSelected(false);
                break;
            case R.id.age:
                if (pvCustomTime == null)
                    initCustomTimePicker();
                pvCustomTime.show();
                break;
            case R.id.next:
                //fixme upload info
                EventBus.getDefault().post(new EventLoginSuccess());
                SPHelper helper = new SPHelper(ActivityRegisterSecond.this, SharePrefrenceString.USER_LOGIN);
                helper.putValues(new SPHelper.ContentValue(SharePrefrenceString.IS_LOGIN, true));
                startActivity(new Intent(ActivityRegisterSecond.this, MainActivity.class));
                finish();
                break;
        }
    }

    private void initCustomTimePicker() {

        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        selectedDate.set(1990,0,1);
        Calendar startDate = Calendar.getInstance();
        startDate.set(1910, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2018, 11, 29);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                age.setText(getTime(date));
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /*.gravity(Gravity.RIGHT)// default is center*/
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setContentSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
