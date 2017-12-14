package com.neteaseyx.image.ugallery.activity;

import static com.neteaseyx.image.ugallery.UGallery.getSingleImage;
import static com.neteaseyx.image.ugallery.UGallery.getSingleImageFile;
import static com.neteaseyx.image.ugallery.activity.ActivityPreviewImage.EXTRA_KEY_SELECT_PHOTOS;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.neteaseyx.image.R;
import com.neteaseyx.image.ugallery.UGallery;
import com.neteaseyx.image.ugallery.adpter.AdapterGalleryFolder;
import com.neteaseyx.image.ugallery.adpter.AdapterGalleryImages;
import com.neteaseyx.image.ugallery.interfaces.IUIGallery;
import com.neteaseyx.image.ugallery.listener.FolderSelectListener;
import com.neteaseyx.image.ugallery.listener.ImageSelectListener;
import com.neteaseyx.image.ugallery.model.PhotoFolderInfo;
import com.neteaseyx.image.ugallery.model.PhotoInfo;
import com.neteaseyx.image.ugallery.presenters.PresenterGallery;
import com.neteaseyx.image.ugallery.utils.AnimationUtil;
import com.neteaseyx.image.ugallery.utils.GridSpacingDecoration;
import com.neteaseyx.image.ugallery.view.StateLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * 基于GalleryFinale重新封装选择页面 展示全部的图片
 *
 * @author yuhuibin                  s
 * @date 2016-04-22
 */
@SuppressWarnings("ALL")
public class ActivityGalleryImage extends ActivityUGalleryBase implements IUIGallery, FolderSelectListener, ImageSelectListener {

    public static final String IS_SINGLE_IMAGE_PICK = "si_single_image_pick";
    public static final String IS_CROP = "is_crop";
    public static final String IS_COMPRESS = "is_compress";

    /**
     * 是否单选图片
     */
    private boolean mIsSingleImagePick;
    /**
     * 是否截图
     */
    private boolean mIsCrop = true;
    /**
     * 是否压缩
     */
    private boolean mIsCompress = true;

    private static final String EXTRA_KEY_SELECT_SOURCE_PHOTOS = "extra_key_select_source_photos";
    private static final String EXTRA_KEY_SELECT_MAX_IMAGE_SIZE = "extra_key_select_max_image_size";

    private PresenterGallery mPresenterGallery;

    private RecyclerView mFolderRecyclerView;
    private RecyclerView mImageRecyclerView;
    private AdapterGalleryFolder mAdapterGalleryFolder;
    private AdapterGalleryImages mAdapterGalleryImages;
    private TextView mFolderName;
    private Context mContext;
    private LinearLayout mLinearLayoutFolder;

    private StateLayout mStateLayout;
    private boolean mIsReleaseStr = true;

    /**
     * 选择单张照片
     */
    public static void startActivityForSingleImage(Context context, boolean iscrop, boolean iscompress) {
        Intent intent = new Intent(context, ActivityGalleryImage.class);
        intent.putExtra(IS_SINGLE_IMAGE_PICK, true);
        intent.putExtra(IS_CROP, iscrop);
        intent.putExtra(IS_COMPRESS, iscompress);
        ((Activity) context).startActivityForResult(intent, UGallery.SELECT_IMAGE);
    }

    /**
     * 选择多张照片
     */
    public static void startActivityForMutilImage(Context context, List<PhotoInfo> selectedSourceImages,
                                                  int maxImageSize, boolean isCompress) {
        Intent intent = new Intent(context, ActivityGalleryImage.class);
        intent.putExtra(IS_SINGLE_IMAGE_PICK, false);
        intent.putExtra(IS_COMPRESS, isCompress);
        intent.putExtra(EXTRA_KEY_SELECT_SOURCE_PHOTOS, (Serializable) selectedSourceImages);
        intent.putExtra(EXTRA_KEY_SELECT_MAX_IMAGE_SIZE, maxImageSize);
        ((Activity) context).startActivityForResult(intent, UGallery.SELECT_MUTIL_IMAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_image);

        Intent intent = getIntent();
        mIsSingleImagePick = intent.getBooleanExtra(IS_SINGLE_IMAGE_PICK, false);
        mIsCrop = intent.getBooleanExtra(IS_CROP, false);
        mIsCompress = intent.getBooleanExtra(IS_COMPRESS, false);

        UGallery.Config config = UGallery.getConfig();
        if (config.getBarTitle() != null) {
            setTitle(config.getBarTitle());
        } else {
            setTitle(getResources().getString(R.string.image));
        }
        if (config.getBarRightText() != null) {
            setTitleBarRightButton(config.getBarRightText());
        } else {
            setTitleBarRightButton(getResources().getString(R.string.select_finish));
        }
        setTitleBarRightButtonEnable(false);
        // setTextBack();
        //mSelectorAlbum.setVisibility(View.VISIBLE);

        mContext = this;
        //        ContextUtil.INSTANCE.init(mContext);
        mPresenterGallery = new PresenterGallery(this, mIsSingleImagePick,
                getIntent().getIntExtra(EXTRA_KEY_SELECT_MAX_IMAGE_SIZE, config.getGalleryMaxImageSize()));
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPresenterGallery.getImages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapterGalleryImages.release();
        mPresenterGallery.destroy();
        //        Context context = ContextUtil.INSTANCE.getContext();
        //        context = null;
        System.gc();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UGallery.PREVIEW_IMAGE) {
            if (resultCode == ActivityPreviewImage.FINISH) {
                List<PhotoInfo> selectPhotos = (List<PhotoInfo>) data.getSerializableExtra(EXTRA_KEY_SELECT_PHOTOS);
                mPresenterGallery.setSelectImages(selectPhotos);
                mPresenterGallery.next();
            }
            if (resultCode == ActivityPreviewImage.BACK) {
                List<PhotoInfo> selectPhotos = (List<PhotoInfo>) data.getSerializableExtra(EXTRA_KEY_SELECT_PHOTOS);
                mPresenterGallery.setSelectImages(selectPhotos);
            }
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == UGallery.CROP_IMAGE) {
            Uri uri = getSingleImage(data);
            //裁剪好了图片、判断是否还要进行压缩
            if (mIsCompress) {
                compressSingleWithRx(getSingleImageFile(uri));
            } else {
                returnSingleImage(uri);
            }
        }

        if (requestCode == UGallery.TAKE_IMAGE) {
            mPresenterGallery.getImages(null);
            if (mIsSingleImagePick) {
                Uri uri = getSingleImage(data);
                if (mIsCrop && mIsCompress) {
                    cropImage(uri);
                } else if (mIsCrop) {
                    cropImage(uri);
                } else if (mIsCompress) {
                    compressSingleWithRx(getSingleImageFile(uri));
                } else {
                    returnSingleImage(uri);
                }
            } else {
                //拿到需要裁剪的图片,再拍照之后返回的地址
                Uri uri = UGallery.getSingleImage(data);
                mPresenterGallery.setImageUris(uri);
            }
        }

    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onFolderSelectListener(int position) {
        mPresenterGallery.imageFolderSelect(position);
        mPresenterGallery.changeFolder();
    }

    /**
     * 点击图片上选择按钮，处理单选，复选
     */
    @Override
    public void onImageSelectListener(int position) {
        PhotoInfo info = mAdapterGalleryImages.getFolderInfo().getPhotoList().get(position);
        mPresenterGallery.imageSelectChangeState(position);
    }

    @Override
    public void onUpdateImageFolder(String folderName, PhotoFolderInfo folderInfo) {
        mFolderName.setText(folderName);
        mAdapterGalleryImages.setFolderInfo(folderInfo);
        mAdapterGalleryImages.notifyDataSetChanged();
    }

    @Override
    public void setMeadiaClick(boolean b, int selectMax) {
        mAdapterGalleryImages.setMediaClick(b, selectMax);
    }

    @Override
    public boolean getIsReleaseStr() {
        return mIsReleaseStr;
    }

    @Override
    public void onUpdateGallery(List<PhotoInfo> selectImages) {
        mAdapterGalleryImages.setSelectPhoto(selectImages);
        mAdapterGalleryImages.notifyDataSetChanged();
        if (selectImages.size() == 0) {
            //   setTitleBarRightButton(getResources().getString(R.string.select_finish));
            setTitleBarRightButtonEnable(false);
        } else {
            if (!mIsSingleImagePick) {
                //  setTitleBarRightButton(getResources().getString(R.string.select_finish) + "(" + selectImages.size() + ")");
                //       setTitleBarRightButton(getResources().getString(R.string.select_finish));
            }
            setTitleBarRightButtonEnable(true);
        }
    }

    /**
     * 点击图片
     */
    @Override
    public void onImageClickListener(int position) {
        mPresenterGallery.imageClick(position);
    }

    /**
     * 点击文件夹，改变状态
     * 注意动画与setVisibility的时序
     */
    @Override
    public void onChangeFolderListStatus() {
        if (mLinearLayoutFolder.getVisibility() == View.VISIBLE) {
            new AnimationUtil(getApplicationContext(), R.anim.translate_down)
                    .setLinearInterpolator().startAnimation(mLinearLayoutFolder);
            mLinearLayoutFolder.setVisibility(View.GONE);
        } else {
            mLinearLayoutFolder.setVisibility(View.VISIBLE);
            new AnimationUtil(getApplicationContext(), R.anim.translate_up_current)
                    .setLinearInterpolator().startAnimation(mLinearLayoutFolder);
        }
    }

    @Override
    public void onNext(List<PhotoInfo> selectImages) {
        //        mImageRecyclerView.removeAllViews();
        //        mAdapterGalleryImages.release();
        if (mIsSingleImagePick) {
            Uri photoPath = selectImages.get(0).getPhotoPath();
            if (mIsCrop && mIsCompress) {
                cropImage(photoPath);
            } else if (mIsCrop) {
                cropImage(photoPath);
            } else if (mIsCompress) {
                compressSingleWithRx(getSingleImageFile(photoPath));
            } else {
                returnSingleImage(photoPath);
            }
        } else {
            if (mIsCompress) {
                compressMutileWithRx(selectImages);
            } else {
                returnMutilImage(selectImages);
            }
        }
    }

    @Override
    public void onInitGallery(List<PhotoFolderInfo> allImage, List<PhotoInfo> selectImage) {
        if (allImage.size() > 0) {
            if (allImage.get(0).getPhotoList().size() == 0) {
                //  mStateLayout.onEmpty();
                //需要显示拍摄的图标
                //如果相册没有图片的话就不需要释放内存了
                mIsReleaseStr = false;
                mStateLayout.setVisibility(View.GONE);
            } else {
                mIsReleaseStr = true;
                mFolderName.setText(allImage.get(0).getFolderName());
                mAdapterGalleryImages.setFolderInfo(allImage.get(0));
                mAdapterGalleryImages.setSelectPhoto(selectImage);

                /** 当图像数量较多提示正在加载 */
                //                if (allImage.get(0).getPhotoList().size() > 20){
                //                    Handler handler = new Handler();
                //                    handler.postDelayed(new Runnable() {
                //                        @Override
                //                        public void run() {
                //                            mStateLayout.setVisibility(View.GONE);
                //                        }
                //                    }, 1000);
                //                }else {
                mStateLayout.setVisibility(View.GONE);
                //                }
            }
        }

        // mAdapterGalleryFolder.setAllFolder(allImage);
        mAdapterGalleryImages.notifyDataSetChanged();
        //mAdapterGalleryFolder.notifyDataSetChanged();
    }

    @Override
    public void toast(String string) {
        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
    }

    private void cropImage(Uri uri) {
        UGallery.cropImage(mContext, uri);
    }

    private RelativeLayout mRootLayoutRL;

    private void initView() {
        //设置图片文件夹
        mRootLayoutRL = (RelativeLayout) findViewById(R.id.layout);
        mFolderRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_folder);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFolderRecyclerView.setLayoutManager(layoutManager);
        mAdapterGalleryFolder = new AdapterGalleryFolder(mContext);
        mAdapterGalleryFolder.setFolderSelectListener(this);

        mFolderRecyclerView.setAdapter(mAdapterGalleryFolder);
        mFolderName = (TextView) findViewById(R.id.folder_name);
        mFolderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenterGallery.changeFolder();
            }
        });

        mLinearLayoutFolder = (LinearLayout) findViewById(R.id.ly_folder_layer);
        mLinearLayoutFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenterGallery.changeFolder();
            }
        });
        mImageRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_image);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, UGallery.getConfig().getGalleryColumnSize());
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mImageRecyclerView.setLayoutManager(gridLayoutManager);
        mImageRecyclerView.setHasFixedSize(true);
        mImageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapterGalleryImages = new AdapterGalleryImages(mContext, this);
        mImageRecyclerView.setAdapter(mAdapterGalleryImages);
        ((SimpleItemAnimator) mImageRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mImageRecyclerView.addItemDecoration(new GridSpacingDecoration(9));

        setTitleBarRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenterGallery.next();
            }
        });

        List<PhotoInfo> selectedImages = (List<PhotoInfo>) getIntent().getSerializableExtra(EXTRA_KEY_SELECT_PHOTOS);
        List<PhotoInfo> selectedSourceImages = (List<PhotoInfo>) getIntent().getSerializableExtra(EXTRA_KEY_SELECT_SOURCE_PHOTOS);
        mPresenterGallery.getImages(selectedSourceImages);

        mStateLayout = getStateLayout();
        // mStateLayout.onLoading();
    }

    private void returnSingleImage(Uri uri) {
        try {
            Intent intent = new Intent();
            if (uri == null) {
                intent.putExtra(UGallery.PATH, "");
                setResult(UGallery.RESULT_ERROR, intent);
            } else {
                intent.putExtra(UGallery.PATH, uri.getPath());
                setResult(RESULT_OK, intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    private void returnMutilImage(List<PhotoInfo> selectImages) {
        try {
            Intent intent = new Intent();
            ArrayList<String> listUri = new ArrayList<>();
            for (int i = 0; i < selectImages.size(); i++) {
                listUri.add(selectImages.get(i).getPhotoPath().getPath());
            }
            intent.putStringArrayListExtra(UGallery.PATH, listUri);
            setResult(RESULT_OK, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    private void returnMutilCompressImage(List<File> fileList, List<PhotoInfo> sourceList) {
        try {
            Intent intent = new Intent();
            if (fileList == null || sourceList == null) {
                intent.putStringArrayListExtra(UGallery.PATH, null);
                intent.putStringArrayListExtra(UGallery.SOURCE_PATH, null);
                setResult(UGallery.RESULT_ERROR, intent);
            } else {
                ArrayList<String> listUri = new ArrayList<>();
                ArrayList<String> sourceListUri = new ArrayList<>();
                for (int i = 0; i < fileList.size(); i++) {
                    listUri.add(fileList.get(i).getPath());
                }
                for (int i = 0; i < sourceList.size(); i++) {
                    sourceListUri.add(sourceList.get(i).getPhotoPath().getPath());
                }
                intent.putStringArrayListExtra(UGallery.PATH, listUri);
                intent.putStringArrayListExtra(UGallery.SOURCE_PATH, sourceListUri);
                setResult(RESULT_OK, intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    private void compressSingleWithRx(File file) {
        Flowable.just(file).observeOn(Schedulers.io()).map(new Function<File, File>() {
            @Override
            public File apply(@NonNull File file) throws Exception {
                return Luban.with(ActivityGalleryImage.this).load(file).get();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<File>() {
            @Override
            public void accept(@NonNull File file) throws Exception {
                returnSingleImage(getSingleImage(file));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                returnSingleImage(null);
            }
        });
    }

    private void compressTakePhotoWithRx(File file) {
        Flowable.just(file).observeOn(Schedulers.io()).map(new Function<File, File>() {
            @Override
            public File apply(@NonNull File file) throws Exception {
                return Luban.with(ActivityGalleryImage.this).load(file).get();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<File>() {
            @Override
            public void accept(
                    @NonNull
                            File file) throws Exception {
                returnSingleImage(getSingleImage(file));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                returnSingleImage(null);
            }
        });
    }

    private void compressMutileWithRx(final List<PhotoInfo> selectImages) {
        Flowable.fromIterable(selectImages).map(new Function<PhotoInfo, String>() {
            @Override
            public String apply(@NonNull PhotoInfo photoInfo) throws Exception {
                return photoInfo.getPhotoPath().getPath();
            }
        }).map(new Function<String, File>() {
            @Override
            public File apply(@NonNull String s) throws Exception {
                return new File(s);
            }
        }).map(new Function<File, File>() {
            @Override
            public File apply(@NonNull File file) throws Exception {
                return Luban.with(ActivityGalleryImage.this).load(file).get();
            }
        }).toList().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(List<File> fileList) throws Exception {
                        //图片压缩完成得到list
                        returnMutilCompressImage(fileList, selectImages);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        returnMutilCompressImage(null, null);
                    }
                });
    }
}
