package com.neteaseyx.image.ugallery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.neteaseyx.image.R;
import com.neteaseyx.image.ugallery.activity.ActivityCropImage;
import com.neteaseyx.image.ugallery.activity.ActivityGalleryImage;
import com.neteaseyx.image.ugallery.activity.ActivityTakePhotos;
import com.neteaseyx.image.ugallery.model.PhotoInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.annotation.DrawableRes;

/**
 * 发起Gallery后，结果在 onActivityResult() 中返回，调用{@link UGallery#getSingleImage(Intent)}获得Uri
 * 裁剪图像、记得删除缓存图像{@link UGallery#deleteTmpImage(Context)}
 *
 * @author yuhuibin
 * @date 2016-04-28
 */
public class UGallery {

    /** 拍照 */
    public static final int TAKE_IMAGE = 1001;
    /** 选择单张图像 */
    public static final int SELECT_IMAGE = 1101;
    /** 选择多张图像 */
    public static final int SELECT_MUTIL_IMAGE = 1102;
    /** 裁剪图像 */
    public static final int CROP_IMAGE = 1200;
    /** 预览图像 */
    public static final int PREVIEW_IMAGE = 1300;
    /** 自定义裁剪图片 */
    public static final int CROP_CUSTOM_IMAGE = 1400;
    /** 返回值 */
    public static final int RESULT_ERROR= 1500;
    public static String PATH = "PATH";
    public static String SOURCE_PATH = "SOURCE_PATH";

    private static Config mConfig = new Config();

    /**
     * 选择单张图像
     */
    public static void selectSingleImage(Context context) {
        ActivityGalleryImage.startActivityForSingleImage(context, false, false);
    }

    /**
     * 选择单张图像并裁剪
     */
    public static void selectSingleImageCrop(Context context) {
        ActivityGalleryImage.startActivityForSingleImage(context, true, false);
    }

    /**
     * 选择单张图像并压缩
     */
    public static void selectSingleImageCompress(Context context) {
        ActivityGalleryImage.startActivityForSingleImage(context, false, true);
    }

    /**
     * 选择单张图像并压缩、裁剪
     */
    public static void selectSingleImageCropAndCompress(Context context) {
        ActivityGalleryImage.startActivityForSingleImage(context, true, true);
    }

    /**
     * 选择多张图像
     */
    public static void selectMultipleImage(Context context) {
        ActivityGalleryImage.startActivityForMutilImage(context, null, 9, false);
    }

    /**
     * 选择多张图像
     */
    public static void selectMultipleImageCompress(Context context) {
        ActivityGalleryImage.startActivityForMutilImage(context, null, 9, true);
    }

    /**
     * 选择多张图像
     *
     * @param selectedImages 已选择的图像
     */
    public static void selectMultipleImage(Context context, List<Uri> selectedImages) {
        List<PhotoInfo> photoInfoList = new ArrayList<>();
        for (Uri uri : selectedImages) {
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setPhotoPath(uri);
            photoInfoList.add(photoInfo);
        }
        ActivityGalleryImage.startActivityForMutilImage(context, photoInfoList, 9, false);
    }

    /**
     * 选择多张图像
     *
     * @param selectedSourceImages 已选择的图像
     */
    public static void selectMultipleImageCompress(Context context, List<Uri> selectedSourceImages) {
        List<PhotoInfo> photoInfoSourceList = new ArrayList<>();
        for (Uri uri : selectedSourceImages) {
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setPhotoPath(uri);
            photoInfoSourceList.add(photoInfo);
        }
        ActivityGalleryImage.startActivityForMutilImage(context, photoInfoSourceList, 9, true);
    }

    /**
     * 选择多张图像
     */
    public static void selectMultipleImage(Context context, int maxsize) {
        ActivityGalleryImage.startActivityForMutilImage(context, null, maxsize, false);
    }

    /**
     * 选择多张图像
     */
    public static void selectMultipleImageCompress(Context context, int maxsize) {
        ActivityGalleryImage.startActivityForMutilImage(context, null, maxsize, true);
    }

    /**
     * 选择多张图像
     *
     * @param selectedImages 已选择的图像
     */
    public static void selectMultipleImage(Context context, List<Uri> selectedImages, int maxsize) {
        List<PhotoInfo> photoInfoList = new ArrayList<>();
        for (Uri uri : selectedImages) {
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setPhotoPath(uri);
            photoInfoList.add(photoInfo);
        }
        ActivityGalleryImage.startActivityForMutilImage(context, photoInfoList, maxsize, false);
    }

    /**
     * 选择多张图像
     *
     * @param selectedImages 已选择的图像
     */
    public static void selectMultipleImageCompress(Context context, List<Uri> selectedImages, int maxsize) {
        List<PhotoInfo> photoInfoList = new ArrayList<>();
        for (Uri uri : selectedImages) {
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setPhotoPath(uri);
            photoInfoList.add(photoInfo);
        }
        ActivityGalleryImage.startActivityForMutilImage(context, photoInfoList, maxsize, true);
    }

    /**
     * 裁剪图像
     */
    public static void cropImage(Context context, Uri uri) {
        ActivityCropImage.startActivity(context, uri);
    }

    /**
     * 拍照
     */
    public static void takeImage(Activity context) {
        ActivityTakePhotos.startActivityForTakePhoto(context, false);
    }

    /**
     * 拍照并裁剪
     */
    public static void takeImageAndCrop(Activity context) {
        ActivityTakePhotos.startActivityForTakePhoto(context, true);
    }

    /**
     * 拍照、压缩
     */
    public static void takeImageAndCompress(Activity context) {
        ActivityTakePhotos.startActivityForTakePhoto(context, false, true);
    }

    /**
     * 拍照，裁剪、压缩
     */
    public static void takeImageAndCropCompress(Activity context) {
        ActivityTakePhotos.startActivityForTakePhoto(context, true, true);
    }

    /**
     * 对于onActivityResult中返回单张图像结果，从intent(data)中解析数据
     */
    public static Uri getSingleImage(Intent data) {
        String path = data.getStringExtra(PATH);
        return Uri.parse("file://" + path);
    }

    public static Uri getSingleSourceImage(Intent data) {
        String path = data.getStringExtra(SOURCE_PATH);
        return Uri.parse("file://" + path);
    }

    /**
     * 对于onActivityResult中返回单张图像结果，从intent(data)中解析数据
     */
    public static Uri getSingleImage(File file) {
        String path = file.getPath();
        Uri uri = Uri.parse("file://" + path);
        return uri;
    }

    public static File getSingleImageFile(Uri uri) {
        String path = uri.getPath();
        return new File(path);
    }

    public static File getSingleImageFile(String imagePath) {
        return new File(imagePath);
    }

    /**
     * 对于onActivityResult中返回多张图像结果，从intent(data)中解析数据
     */
    public static List<Uri> getMultipleImage(Intent data) {
        List<Uri> uriArrayList = new ArrayList<>();
        List<String> pathList = data.getStringArrayListExtra(PATH);
        for (int i = 0; i < pathList.size(); i++) {
            Uri uri = Uri.parse("file://" + pathList.get(i));
            uriArrayList.add(uri);
        }
        return uriArrayList;
    }

    public static List<Uri> getMultipleSourceImage(Intent data) {
        List<Uri> uriArrayList = new ArrayList<>();
        List<String> pathList = data.getStringArrayListExtra(SOURCE_PATH);
        if (pathList == null || pathList.size() == 0) {
            return uriArrayList;
        }
        for (int i = 0; i < pathList.size(); i++) {
            Uri uri = Uri.parse("file://" + pathList.get(i));
            uriArrayList.add(uri);
        }
        return uriArrayList;
    }

    /**
     * 裁剪图像、记得删除缓存图像
     */
    public static void deleteTmpImage(Context context) {
        Uri destinationUri = Uri.fromFile(new File(context.getCacheDir(), "SampleCropImage.jpeg"));
        File file = new File(destinationUri.getPath());
        file.delete();
        MediaScannerConnection.scanFile(context, new String[] {file.getAbsolutePath()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    public static void setConfig(Config config) {
        mConfig = config;
    }

    public static Config getConfig() {
        return mConfig;
    }

    public static class Config {
        private int title_bar_bg_color;
        private String bar_title;
        private String bar_right_text;
        private int title_text_color;
        private int title_text_size;
        private int right_btn_text_size;
        private int title_bar_right_btn_color;
        private int title_bar_right_btn_text_color;
        private int title_bar_right_btn_size;
        private int title_bar_back_btn_image;
        private int title_bar_height;
        private int place_holder_image = R.drawable.ic_gf_default_photo;
        private int gallery_column_size = 3;
        private int gallery_image_select;
        private int gallery_image_unselect;
        private int gallery_empty_image;
        private int gallery_max_image_size = 9;
        private int gallery_camera_image;
        private boolean image_select_num_show = true;
        private int image_background;
        private static int width_ratio;
        private static int height_ratio;
        private String pack_name;

        /** 标题栏背景颜色 */
        public Config setTitleBarBgColor(int color) {
            this.title_bar_bg_color = color;
            return this;
        }



        public int getTitleBarBgColor() {
            return this.title_bar_bg_color;
        }

        /** 标题栏的title */
        public Config setBarTitle(String title) {
            this.bar_title = title;
            return this;
        }

        public String getBarTitle() {
            return this.bar_title;
        }

        /** 标题栏右边的文字 */
        public Config setBarRightText(String text) {
            this.bar_right_text = text;
            return this;
        }

        public String getBarRightText() {
            return this.bar_right_text;
        }

        /** 标题栏 标题 文本颜色 */
        public Config setTitleTextColor(int color) {
            this.title_text_color = color;
            return this;
        }

        public int getTitleTextColor() {
            return this.title_text_color;
        }

        /** 标题栏 高度 */
        public Config setTitleBarHeight(int color) {
            this.title_bar_height = color;
            return this;
        }

        public int getTitleBarHeight() {
            return this.title_bar_height;
        }

        /** 标题栏 标题 文本大小 */
        public Config setTitleTextSize(int size) {
            this.title_text_size = size;
            return this;
        }

        public int getTitleTextSize() {
            return this.title_text_size;
        }

        /** 右边btn的文字 */
        public Config setRightBtnTextSize(int size) {
            this.right_btn_text_size = size;
            return this;
        }

        public int getRightBtnTextSize() {
            return this.right_btn_text_size;
        }

        /** 标题栏 右边按钮 文本颜色 */
        public Config setTitleBarRightBtnColor(int color) {
            this.title_bar_right_btn_color = color;
            return this;
        }

        public int getTitleBarRightBtnColor() {
            return this.title_bar_right_btn_color;
        }

        /** 标题栏 右边按钮 文字颜色 */
        public Config setTitleBarRightBtnTextColor(int color) {
            this.title_bar_right_btn_text_color = color;
            return this;
        }

        public int getTitleBarRightBtnTextColor() {
            return this.title_bar_right_btn_text_color;
        }

        /** 标题栏 右边按钮 文本颜色 */
        public Config setTitleBarRightBtnSize(int color) {
            this.title_bar_right_btn_size = color;
            return this;
        }

        public int getTitleBarRightBtnSize() {
            return this.title_bar_right_btn_size;
        }

        /** 标题栏 返回按钮 */
        public Config setTitleBarBackBtnImage(int res) {
            this.title_bar_back_btn_image = res;
            return this;
        }

        public int getTitleBarBackBtnImage() {
            return this.title_bar_back_btn_image;
        }

        /** 占位 图 */
        public Config setImagePlaceholder(int res) {
            this.place_holder_image = res;
            return this;
        }

        public int getImagePlaceholder() {
            return this.place_holder_image;
        }

        /** 设置Gallery中图像的列数 */
        public Config setGalleryColumnSize(int columnSize) {
            this.gallery_column_size = columnSize;
            return this;
        }

        public int getGalleryColumnSize() {
            return this.gallery_column_size;
        }

        public Config setGalleryImageSelect(int select, int unselect) {
            this.gallery_image_select = select;
            this.gallery_image_unselect = unselect;
            return this;
        }

        public int getGalleryImageSelectImage() {
            return this.gallery_image_select;
        }

        public int getGalleryImageUnSelectImage() {
            return this.gallery_image_unselect;
        }

        public Config setGalleryEmptyImage(int image) {
            this.gallery_empty_image = image;
            return this;
        }

        public int getGalleryEmptyImage() {
            return this.gallery_empty_image;
        }

        public Config setGalleryMaxImageSize(int size) {
            this.gallery_max_image_size = size;
            return this;
        }

        public int getGalleryMaxImageSize() {
            return gallery_max_image_size;
        }

        public Config setCameraImage(
                @DrawableRes
                        int res) {
            this.gallery_camera_image = res;
            return this;
        }

        public int getCameraImage() {
            return gallery_camera_image;
        }

        public Config setImageSelectNumIsShow(boolean isShow) {
            this.image_select_num_show = isShow;
            return this;
        }

        public boolean getImageSelectNumIsShow() {
            return image_select_num_show;
        }

        public Config setImageBackGround(
                @DrawableRes
                        int res) {
            this.image_background = res;
            return this;
        }

        public int getImageBackGround() {
            return image_background;
        }

        public Config setCropRatio(int width, int height) {
            this.width_ratio = width;
            this.height_ratio = height;
            return this;
        }

        public int getWidthCropRatio() {
            return width_ratio;
        }

        public int getHeightCropRatio() {
            return height_ratio;
        }

        public Config setPackName(String packName) {
            this.pack_name = packName;
            return this;
        }

        public String getPackName() {
            return pack_name;
        }
    }
}
