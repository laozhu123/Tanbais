package com.neteaseyx.image.ugallery.activity;

import static com.neteaseyx.image.ugallery.UGallery.CROP_IMAGE;

import java.io.File;

import com.neteaseyx.image.R;
import com.neteaseyx.image.ugallery.UGallery;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * @author yuhuibin
 * @date 2016-05-06
 */
public class ActivityCropImage extends Activity{


    private static Uri mUri;
    private UGallery.Config mConfig;

    /**
     * 剪裁图像
     * @param context
     */
    public static void startActivity(Context context,  Uri uri){
        mUri = uri;
        Intent intent = new Intent(context, ActivityCropImage.class);
        ( (Activity)context).startActivityForResult(intent, CROP_IMAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfig = new UGallery.Config();
        startCropImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            //剪裁单张图像后，通过回调返回结果
            final Uri resultUri = UCrop.getOutput(data);
            setResult(RESULT_OK, new Intent()
                .putExtra(UGallery.PATH, resultUri.getPath())
            );
        } else if(resultCode == RESULT_CANCELED){    //取消
            setResult(RESULT_CANCELED, null);
        }else if(resultCode == UCrop.RESULT_ERROR) {  //错误
            setResult(UCrop.RESULT_ERROR, null);
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 开始剪裁图片
     */
    private void startCropImage(){
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpeg"));
        UCrop.of(mUri, destinationUri)
             // .withAspectRatio(mConfig.getWidthCropRatio() == 0 ? 16 : mConfig.getWidthCropRatio(), mConfig.getHeightCropRatio() == 0 ? 11 : mConfig.getHeightCropRatio())
              .withAspectRatio(mConfig.getWidthCropRatio(),mConfig.getHeightCropRatio())
              .withOptions(setCropOption())
              .start(ActivityCropImage.this);

    }

    /**
     * 剪裁图片参数配置
     * @return
     */
    private UCrop.Options setCropOption(){
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);
        options.setAllowedGestures(UCropActivity.SCALE,UCropActivity.SCALE, UCropActivity.SCALE);
        if (UGallery.getConfig().getTitleBarBgColor() != 0){
            options.setToolbarColor(UGallery.getConfig().getTitleBarBgColor());
        }else {
            options.setToolbarColor(getResources().getColor(R.color.selectToolBar));
        }
        if (UGallery.getConfig().getTitleTextColor() != 0){
            options.setToolbarWidgetColor(UGallery.getConfig().getTitleTextColor());
        }else {
            options.setToolbarWidgetColor(getResources().getColor(R.color.tool_bar_text));
        }

        options.setCropFrameColor(getResources().getColor(R.color.white));
        return options;
    }
}
