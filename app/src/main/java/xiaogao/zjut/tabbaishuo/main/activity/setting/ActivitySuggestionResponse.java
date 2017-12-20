package xiaogao.zjut.tabbaishuo.main.activity.setting;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neteaseyx.image.ugallery.UGallery;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xiaogao.zjut.tabbaishuo.R;
import xiaogao.zjut.tabbaishuo.adapter.AdapterImageList;
import xiaogao.zjut.tabbaishuo.adapter.viewholder.VHolderCommitImage;
import xiaogao.zjut.tabbaishuo.base.activity.MyBaseBindPresentActivity;
import xiaogao.zjut.tabbaishuo.injecter.component.ActivityComponent;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivitySuggestionResponse;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivityStoreImage;
import xiaogao.zjut.tabbaishuo.main.persenter.PresenterSuggestionResponse;
import xiaogao.zjut.tabbaishuo.views.SpaceBaseItemDecoration;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ActivitySuggestionResponse extends MyBaseBindPresentActivity<PresenterSuggestionResponse> implements IUIActivitySuggestionResponse {

    @Inject
    PresenterSuggestionResponse mPresenter;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.titleRightTv)
    TextView titleRightTv;
    @Bind(R.id.et_input)
    EditText etInput;
    @Bind(R.id.count_tx)
    TextView countTx;
    @Bind(R.id.rec_img)
    RecyclerView recImg;

    UGallery.Config config;

    public static final int SPAN_COUNT = 3;

    /**
     * 图片列表,MIME为image/jpeg
     */
    private List<Uri> mUris = new ArrayList<>();
    private List<String> mPaths = new ArrayList<>();
    private List<Uri> mSourceUris = new ArrayList<>();

    private AdapterImageList mAdapter;

    @Override
    public PresenterSuggestionResponse getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject(ActivityComponent pActivityComponent) {
        pActivityComponent.inject(this);
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.activity_suggestion_response;
    }

    @Override
    protected void initActivity(View var1) {
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        hideTitleBar();
        title.setText(R.string.ideaResponse);
        titleRightTv.setText(R.string.upload);
        titleRightTv.setVisibility(View.VISIBLE);
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                countTx.setText("" + etInput.getText().toString().length() + "/300");
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recImg.setLayoutManager(layoutManager);
        ((SimpleItemAnimator) recImg.getItemAnimator()).setSupportsChangeAnimations(false);
        recImg.addItemDecoration(new SpaceBaseItemDecoration(10));

        View.OnClickListener onAddClickListener = new AddImageListener();
        VHolderCommitImage.RemoveDeliveredImage onRemoveClickListener = new RemoveImageListener();
        VHolderCommitImage.ShowDeliverImage showDeliverImage = new ShowImageListener();
        mAdapter = new AdapterImageList(onAddClickListener, onRemoveClickListener, showDeliverImage, mUris);

        recImg.setAdapter(mAdapter);
    }


    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case UGallery.SELECT_MUTIL_IMAGE:
                    mSourceUris.clear();
                    List<Uri> uris = UGallery.getMultipleImage(data);
                    mSourceUris = UGallery.getMultipleSourceImage(data);
                    mUris.clear();
                    mUris.addAll(uris);
                    mAdapter.notifyDataSetChanged();
                    break;
                case UGallery.TAKE_IMAGE:
                    Uri uri = UGallery.getSingleImage(data);
                    Uri uriSource = UGallery.getSingleSourceImage(data);
                    mSourceUris.add(uriSource);
                    mUris.add(uri);
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    @OnClick({R.id.back, R.id.titleRightTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.titleRightTv:
                showToast(R.string.upload);
                break;
        }
    }


    private class AddImageListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            getPresenter().openGallery();
            if (config == null) {
                config = UGallery.getConfig();
                config.setImageSelectNumIsShow(false);
                config.setBarRightText("确定");
                config.setBarTitle("相机");
                config.setCameraImage(R.mipmap.camera_icon);
                config.setImageBackGround(R.color.color_979797);
                config.setTitleBarRightBtnTextColor(R.color.color_7dba50);
                config.setGalleryImageSelect(R.mipmap.choose_photo_selected, R.mipmap.choose_photo_unselected);
            }
            UGallery.getConfig().setBarTitle(getString(R.string.xiang_ce));
            if (mSourceUris.size() == 0) {
                UGallery.selectMultipleImageCompress(ActivitySuggestionResponse.this, SPAN_COUNT);

            } else {
                UGallery.selectMultipleImageCompress(ActivitySuggestionResponse.this, mSourceUris, SPAN_COUNT);
            }
        }
    }

    private class RemoveImageListener implements VHolderCommitImage.RemoveDeliveredImage {
        @Override
        public void remove(int position) {
            mUris.remove(position);
            mSourceUris.remove(position);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ShowImageListener implements VHolderCommitImage.ShowDeliverImage {

        @Override
        public void show(int position) {
            mPaths.clear();
            for (Uri uri : mUris)
                mPaths.add(uri.getPath());
            String[] s = new String[mPaths.size()];
            ActivityStoreImage.start(ActivitySuggestionResponse.this, mPaths.toArray(s), position);
        }
    }
}
