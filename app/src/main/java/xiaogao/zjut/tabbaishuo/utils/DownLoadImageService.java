package xiaogao.zjut.tabbaishuo.utils;

/**
 * Created by Administrator on 2017/12/20.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownLoadImageService implements Runnable {
    private String url;
    private Context context;
    private ImageDownLoadCallBack callBack;
    private File currentFile;
    private String fileName;
    private boolean isSetMediaStore;

    public DownLoadImageService(Context context, String url, boolean isSetMediaStore, String fileName, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
        this.context = context;
        this.isSetMediaStore = isSetMediaStore;
        this.fileName = fileName;
    }

    public void run() {
        Bitmap bitmap = null;

        try {
            bitmap = (Bitmap) Glide.with(this.context).load(this.url).asBitmap().into(-2147483648, -2147483648).get();
            if(bitmap != null) {
                this.saveImageToGallery(this.context, bitmap);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        } finally {
            if(bitmap != null && this.currentFile.exists()) {
                this.callBack.onDownLoadSuccess(bitmap);
            } else {
                this.callBack.onDownLoadFailed();
            }

        }

    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        File appDir = new File(file, this.fileName);
        if(!appDir.exists()) {
            appDir.mkdirs();
        }

        this.fileName = System.currentTimeMillis() + ".jpg";
        this.currentFile = new File(appDir, this.fileName);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(this.currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException var17) {
            var17.printStackTrace();
        } catch (IOException var18) {
            var18.printStackTrace();
        } finally {
            try {
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException var16) {
                var16.printStackTrace();
            }

        }

        if(this.isSetMediaStore) {
            this.setMediaDtore(this.fileName);
        }

        context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.currentFile.getPath()))));
    }

    public void setMediaDtore(String fileName) {
        try {
            MediaStore.Images.Media.insertImage(this.context.getContentResolver(), this.currentFile.getAbsolutePath(), fileName, (String)null);
        } catch (FileNotFoundException var3) {
            var3.printStackTrace();
        }

    }
}

