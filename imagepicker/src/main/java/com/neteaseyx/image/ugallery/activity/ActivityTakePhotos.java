package com.neteaseyx.image.ugallery.activity;

import static com.neteaseyx.image.ugallery.UGallery.getSingleImage;
import static com.neteaseyx.image.ugallery.UGallery.getSingleImageFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.neteaseyx.image.ugallery.UGallery;
import com.neteaseyx.image.ugallery.utils.ProviderUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * 拍照的Activity
 *
 * @author liangbin
 * @date 2016-05-05
 */
public class ActivityTakePhotos extends Activity {
    private static final String TAG = "ActivityTakePhotos";
    /** test */
    private Uri mTakePhotoUri = null;
    private static boolean mIsCrop;
    private static boolean mIsCompress;
    private File mImagePath;

    public static void startActivityForTakePhoto(Activity activity, boolean isCrop) {
        mIsCrop = isCrop;
        Intent intent = new Intent(activity, ActivityTakePhotos.class);
        activity.startActivityForResult(intent, UGallery.TAKE_IMAGE);
    }

    public static void startActivityForTakePhoto(Activity activity, boolean isCrop, boolean isCompress) {
        mIsCrop = isCrop;
        mIsCompress = isCompress;
        Intent intent = new Intent(activity, ActivityTakePhotos.class);
        activity.startActivityForResult(intent, UGallery.TAKE_IMAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhotoAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 拍照让MediaStore添加数据
     */
    private void noticeTakePicture() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(mTakePhotoUri);
        sendBroadcast(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            finish();
            return;
        }
        if (requestCode == UGallery.TAKE_IMAGE) {
            if (mTakePhotoUri != null) {
                if (mIsCrop) {   //裁剪
                    UGallery.cropImage(ActivityTakePhotos.this, mTakePhotoUri);
                } else if (mIsCompress) {  //压缩
                    compressSingleWithRx(getSingleImageFile(mImagePath.getPath()), mImagePath.getPath());
                } else {
                    returnSingleImagePath(mImagePath.getPath());
                }
            }
        }
        // 裁剪完图片,判断是否要进行压缩
        if (requestCode == UGallery.CROP_IMAGE) {
            Uri uri = getSingleImage(data);
            if (mIsCompress) {  //压缩
                compressSingleWithRx(getSingleImageFile(uri), uri.getPath());
            } else {
                returnSingleImage(uri, null);
            }
        }
    }

    private void returnSingleImage(Uri uri, String sourceUri) {
        try {
            if (uri == null) {
                Intent intent = new Intent();
                intent.putExtra(UGallery.PATH, "");
                intent.putExtra(UGallery.SOURCE_PATH, "");
                setResult(UGallery.RESULT_ERROR, intent);
            } else {
                final String path = uri.getPath();
                if (new File(path).exists()) {
                    noticeTakePicture();
                    Intent intent = new Intent();
                    intent.putExtra(UGallery.PATH, uri.getPath());
                    intent.putExtra(UGallery.SOURCE_PATH, sourceUri);
                    setResult(RESULT_OK, intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    private void returnSingleImagePath(String imagePath) {
        try {
            if (new File(imagePath).exists()) {
                noticeTakePicture();
                Intent intent = new Intent();
                intent.putExtra(UGallery.PATH, imagePath);
                setResult(RESULT_OK, intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    protected void takePhotoAction() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File mTakePhotoFolder = null;
        //判断包名不为空
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            if (existSDCard()) {
                mTakePhotoFolder = new File(Environment.getExternalStorageDirectory(), "/DCIM/camera/");
            } else {
                mTakePhotoFolder = Environment.getDataDirectory();
            }
            String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date(System.currentTimeMillis()));
            mImagePath = new File(mTakePhotoFolder, fileName + ".jpg");

            if (!mTakePhotoFolder.isDirectory()) {
                mTakePhotoFolder.mkdirs();
            }

            if (mTakePhotoFolder != null) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    if (mTakePhotoUri == null) {
                        mTakePhotoUri = Uri.fromFile(mImagePath);
                    }
                } else {
                    if (mTakePhotoUri == null) {
                        mTakePhotoUri = FileProvider.getUriForFile(this, ProviderUtil.getFileProviderName(), mImagePath);
                    }
                }
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);//将拍取的照片保存到指定URI

                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, mTakePhotoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                //  takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);
            }
        }
        startActivityForResult(takePictureIntent, UGallery.TAKE_IMAGE);
    }

    /**
     * 判断SDCard是否可用
     */
    public boolean existSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private void compressSingleWithRx(File file, final String uri) {
        Flowable.just(file).observeOn(Schedulers.io()).map(new Function<File, File>() {
            @Override
            public File apply(@NonNull File file) throws Exception {
                return Luban.with(ActivityTakePhotos.this).load(file).get();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(@NonNull File file) throws Exception {
                        returnSingleImage(getSingleImage(file), uri);
                    }
                }, new Consumer<Throwable>() {
                    @Override public void accept(@NonNull Throwable throwable) throws Exception {
                        returnSingleImage(null, null);
                        Toast.makeText(ActivityTakePhotos.this, "拍照失败", Toast.LENGTH_LONG).show();
                    }

                });
    }

}
