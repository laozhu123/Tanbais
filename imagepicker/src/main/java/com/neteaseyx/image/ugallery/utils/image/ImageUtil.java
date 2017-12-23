package com.neteaseyx.image.ugallery.utils.image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.neteaseyx.image.ugallery.utils.ContextUtil;
import com.neteaseyx.image.ugallery.utils.FileUtil;
import com.neteaseyx.image.ugallery.utils.StorageUtil;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

/**
 * Created by zyl06 on 2/18/16.
 */
public class ImageUtil {
    private static String TAG = "htimagepicker_ImageUtil";

    private Context mContext;
    private ContextUtil mContextUtil;

    public ImageUtil(Context context) {
        mContext = context;
        mContextUtil = new ContextUtil(mContext);
    }

    // 见从AsyncTask中的设置
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(64);
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };
    private static RejectedExecutionHandler sRejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
    private static ThreadPoolExecutor sAysncTaskExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory, sRejectedExecutionHandler);

    public static void setImagePath(ImageView imageView, String imagePath) {
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();
        setImagePath(imageView, imagePath, targetW, targetH);
    }

    public static void setImagePath(final ImageView imageView, final String imagePath,
                                    final int desWidth, final int desHeight) {
        setImagePath(imageView, imagePath, desWidth, desHeight, false);
    }

    public static void setImagePath(final ImageView imageView, final String imagePath,
                                    final int desWidth, final int desHeight, boolean autoRotate) {
        Bitmap bm = BitmapCache.getDefault().getFromMemory(imagePath);
        if (bm != null && !bm.isRecycled()) {
            imageView.setImageBitmap(bm);
        } else {
            ImageAsyncTask task = new ImageAsyncTask(imageView, imagePath, desWidth, desHeight, autoRotate);
            task.executeOnExecutor(sAysncTaskExecutor);
        }
    }

    private BitmapCache mBitmapCache;

    /**
     * 设置图像
     * @param imageView
     * @param imagePath
     * @param desWidth
     * @param desHeight
     * @param autoRotate
     */

    public void setImageUrl(final ImageView imageView, final String imagePath,
                                    final int desWidth, final int desHeight, boolean autoRotate) {
        if (mBitmapCache ==null){
            mBitmapCache = new BitmapCache(mContext);
        }
        Bitmap bm = mBitmapCache.getFromMemory(imagePath);
        if (bm != null && !bm.isRecycled()) {
            imageView.setImageBitmap(bm);
        } else {
            ImageAsyncTask task = new ImageAsyncTask(imageView, imagePath, desWidth, desHeight, autoRotate);
            task.executeOnExecutor(sAysncTaskExecutor);
        }
    }

//    private final int imageMaxSize  = 16384;
    private final int imageMaxSize  = 16384;
    public boolean imageIsOverSize(String url){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(url, options);
        if (options.outWidth >= imageMaxSize || options.outHeight >= imageMaxSize){
            return true;
        }else {
            return false;
        }
    }

    public static void removeWorksInQuene() {
        sAysncTaskExecutor.purge();
    }

    private static void setImageBitmap(ImageView imageView, Bitmap bitmap) {
        if (imageView != null && bitmap != null && !bitmap.isRecycled()) {
            imageView.setImageBitmap(bitmap);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
            alphaAnimation.setDuration(300);                  //设置持续时间
            imageView.setAnimation(alphaAnimation);
            alphaAnimation.startNow();
        }
    }

    private static class ImageAsyncTask extends AsyncTask<Void, Void, Bitmap> {
        WeakReference<ImageView> imageViewRef;
        String imagePath;
        int desWidth, desHeight;
        boolean autoRotate;
        public ImageAsyncTask(ImageView imageView, String imagePath, int width, int height, boolean autoRotate) {
            imageView.setTag(imagePath);
            imageView.setImageBitmap(null);
            this.imageViewRef = new WeakReference<ImageView>(imageView);
            this.imagePath = imagePath;
            this.desWidth = width;
            this.desHeight = height;
            this.autoRotate = autoRotate;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                Bitmap bitmap = BitmapCache.getDefault().get(imagePath);
                if (bitmap == null) {
                    bitmap = BitmapDecoder.decodeFile(imagePath, desWidth, desHeight, autoRotate);
                    BitmapCache.getDefault().put(imagePath, bitmap);
                }
                return bitmap;
            }catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null && !result.isRecycled()) {
                ImageView imageView = imageViewRef.get();
                if (imageView != null) {
                    if (imagePath.equals(imageView.getTag())) {
                        setImageBitmap(imageView, result);
                    }
                }
            }
        }
    }

    public static float getImageRotate(String imageFilePath) {
        if (TextUtils.isEmpty(imageFilePath)) {
            return 0;
        }

        ExifInterface localExifInterface;
        try {
            localExifInterface = new ExifInterface(imageFilePath);
            int rotateInt = localExifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            return getImageRotate(rotateInt);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        return 0;
    }

    /**
     * 获得旋转角度
     *
     * @param rotate
     * @return
     */
    private static float getImageRotate(int rotate) {
        float f;
        if (rotate == 6) {
            f = 90.0F;
        } else if (rotate == 3) {
            f = 180.0F;
        } else if (rotate == 8) {
            f = 270.0F;
        } else {
            f = 0.0F;
        }

        return f;
    }

    /**
     * Image Save
     */
    private static String IMAGE_SAVE_PATH;

    private  String getSavePath() {
        if (IMAGE_SAVE_PATH == null) {
            IMAGE_SAVE_PATH = mContextUtil.getAppDir() + "image/save/";
        }

        return IMAGE_SAVE_PATH;
    }

    public static boolean saveBitmap(Bitmap bitmap, String path, boolean recycle) {
        if (bitmap == null || TextUtils.isEmpty(path)) {
            return false;
        }

        BufferedOutputStream bos = null;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);

            return true;
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
            if (recycle) {
                bitmap.recycle();
            }
        }
    }


    public static void saveImage(Context context, File fromFile, File toFile) {
        SaveImageAsyncTask task = new SaveImageAsyncTask(context, fromFile, toFile);
        task.execute();
    }

    public static void addImageToGallery(String filePath, Context context) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.MediaColumns.DATA, filePath);

        // 为了尝试更新，如果没有这个文件的话，就直接尝试添加
        int count = context.getContentResolver().update(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values, MediaStore.MediaColumns.DATA + "=?", new String[]{filePath});
        if (count == 0) {
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

//    public static void addImageToGallery(String imageFolderPath, Context context) {
//        if (TextUtils.isEmpty(imageFolderPath)) {
//            return;
//        }
//
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(imageFolderPath);
//        if (f.exists()) {
//            Uri contentUri = Uri.fromFile(f);
//            mediaScanIntent.setData(contentUri);
//            context.sendBroadcast(mediaScanIntent);
//        }
//    }

    public static void deleteImage(String filePath, Context context) {
        if (TextUtils.isEmpty(filePath)) {
            Log.i(TAG, "deleteImage filePath is empty");
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            Log.i(TAG, "deleteImage file does not exist");
            return;
        }

        file.delete();
        context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.MediaColumns.DATA + "=?", new String[]{filePath});
    }

    private static String sDefaultFolderPath = null;
    public  String getDefaultImageSaveFolderPath(boolean isToast) {
        if (!TextUtils.isEmpty(sDefaultFolderPath)) {
            return sDefaultFolderPath;
        }

        sDefaultFolderPath = StorageUtil.getAppWritePath("images", isToast, mContext);

        return sDefaultFolderPath;
    }

    static class SaveImageAsyncTask extends AsyncTask<Object, Integer, String> {
        Context mContext;
        File mFromFile;
        File mToFile;

        public SaveImageAsyncTask(Context context, File fromFile, File toFile) {
            mContext = context;
            mFromFile = fromFile;
            mToFile = toFile;
        }

        @Override
        protected String doInBackground(Object[] params) {
            FileUtil.copyFile(mFromFile, mToFile, true);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            addImageToGallery(mToFile.getAbsolutePath(), mContext);
        }
    }

}
